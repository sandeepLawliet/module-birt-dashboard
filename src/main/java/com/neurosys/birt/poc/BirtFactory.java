
package com.neurosys.birt.poc;

import java.io.IOException;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.ServletContext;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
 

public class BirtFactory {
	
	/**
	 * The ReportEngine class represents the BIRT Report Engine. 
	 * There is a significant cost associated with creating an engine instance, due primarily to the cost of loading extensions. 
	 * Therefore, each application should create just one ReportEngine instance and use it to run multiple reports. 
	 * 
	 */
	private static IReportEngine birtEngine = null;

	private static Properties configProps = new Properties();

	private final static String configFile = "birtconfig.properties";

	/* Initializing Engine configurations
	 */
	public static synchronized void initBirtConfig() {
	 loadEngineProps();
	}

	/**
	 * This method is responsible for configuring engine's Log Level, retrieving the new IReportEngine instance.
	 * 
	 * @param sc
	 * @return IReportEngine instance
	 */
	public static synchronized IReportEngine getBirtEngine(ServletContext sc) {
	 if (birtEngine == null) 
	 {
	  EngineConfig config = new EngineConfig();
	  
	  if( configProps != null){
	   String logLevel = configProps.getProperty("logLevel");
	   Level level = Level.OFF;
	   if ("SEVERE".equalsIgnoreCase(logLevel)) 
	   {
	    level = Level.SEVERE;
	   } else if ("WARNING".equalsIgnoreCase(logLevel))
	   {
	    level = Level.WARNING;
	   } else if ("INFO".equalsIgnoreCase(logLevel)) 
	   {
	    level = Level.INFO;
	   } else if ("CONFIG".equalsIgnoreCase(logLevel))
	   {
	    level = Level.CONFIG;
	   } else if ("FINE".equalsIgnoreCase(logLevel)) 
	   {
	    level = Level.FINE;
	   } else if ("FINER".equalsIgnoreCase(logLevel)) 
	   {
	    level = Level.FINER;
	   } else if ("FINEST".equalsIgnoreCase(logLevel)) 
	   {
	    level = Level.FINEST;
	   } else if ("OFF".equalsIgnoreCase(logLevel)) 
	   {
	    level = Level.OFF;
	   }

	   config.setLogConfig(configProps.getProperty("logDirectory"), level);
	  }

	  /** Set parent classloader for engine */
	  config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, Thread.currentThread().getContextClassLoader()); 
	  
	  IPlatformContext context = new PlatformServletContext( sc );
	  config.setPlatformContext( context );


	  /**
	   * The report engine is created through a factory supplied by the Platform. Before creating the engine, 
	   * you should start the Platform, which will load the appropriate plug-ins.
	   */
	  try
	  {
	   Platform.startup( config );
	  }
	  catch ( BirtException e )
	  {
	   e.printStackTrace( );
	  }

	  /**
	   * Creating new ReportEngine
	   */
	  IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
	  birtEngine = factory.createReportEngine( config );


	 }
	 return birtEngine;
	}

	/**
	 * After using the engine, function to do clean up work, which includes unloading the extensions.
	 */
	public static synchronized void destroyBirtEngine() {
	 if (birtEngine == null) {
	  return;
	 }  
	 birtEngine.shutdown();
	 Platform.shutdown();
	 birtEngine = null;
	}

	
	public Object clone() throws CloneNotSupportedException {
	 throw new CloneNotSupportedException();
	}

	/**
	 * load the configurations file.
	 */
	private static void loadEngineProps() {
	 try {
	  //Config File must be in classpath
	  ClassLoader cl = Thread.currentThread ().getContextClassLoader();
	  InputStream in = null;
	  in = cl.getResourceAsStream (configFile);
	  configProps.load(in);
	  in.close();


	 } catch (IOException e) {
	  e.printStackTrace();
	 }

	}
}