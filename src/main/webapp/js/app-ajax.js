function show() {
			$.ajax({
				url : 'run',
				data : {
					ReportName : $('#ReportName').val(),
					startDate : $('#startDate').val(),
					endDate : $('#endDate').val()
				},
				success : function(responseText) {
					$('#ajaxGetUserServletResponse').html(responseText);
				}
			});
}