var schedules = [];
function getSchedulerUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/scheduler";
}
//UI DISPLAY METHODS

function displaySchedulerList(){
	var $tbody = $('#scheduler-table').find('tbody');
	$tbody.empty();
	var startDateString = $('#startDate').val();
	var endDateString = $('#endDate').val();
	var startDate = new Date(Date.parse(startDateString)).getTime();
	var endDate = new Date(Date.parse(endDateString)).getTime();
	for(var id in schedules){
	    var scheduleDate = schedules[id].date
	    console.log(scheduleDate,endDate,startDate)
	    console.log(endDate > scheduleDate,startDate < scheduleDate)
	    if(endDate >= scheduleDate && startDate <= scheduleDate){
	        var schedulerDateString = formattedDate(new Date(scheduleDate));
            var row = '<tr>'
            + '<td>' + schedulerDateString + '</td>'
            + '<td>' + schedules[id].count + '</td>'
            + '<td>' + schedules[id].quantity + '</td>'
            + '<td>' + schedules[id].revenue + '</td>'
            + '</tr>';
            $tbody.append(row);
	    }
	}
}


//INITIALIZATION CODE
function init(){
    $("#inputSearch").on('input',displaySchedulerList);
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    var initialStartDate = new Date();
    var initialEndDate = new Date();
    initialStartDate.setDate(today.getDate() - 90);
        var nextDate = new Date();
        nextDate.setDate(today.getDate()+1);
        $('#startDate').datepicker({
            uiLibrary: 'bootstrap4',
            iconsLibrary: 'fontawesome',
            format: 'mm/dd/yyyy',
            maxDate: today
        });
        $('#endDate').datepicker({
            uiLibrary: 'bootstrap4',
            iconsLibrary: 'fontawesome',
            format: 'mm/dd/yyyy',
            minDate: function () {
                return $('#startDate').val();
            },
            maxDate: nextDate
        });
    var url = getSchedulerUrl();
    $.ajax({
       url: url,
       type: 'GET',
       success: function(schedulerData) {
            console.log(schedulerData);
            schedules = schedulerData;
            displaySchedulerList();
       },
       error: handleAjaxError
    });
    $('#startDate').on("change",displaySchedulerList);
    $('#startDate').val(initialStartDate.getMonth()+1+"/"+initialStartDate.getDate()+"/"+initialStartDate.getFullYear());
    $('#endDate').on('change',displaySchedulerList);
    $('#endDate').val((initialEndDate.getMonth()+1)+"/"+initialEndDate.getDate()+"/"+initialEndDate.getFullYear());
}
//HELPER FUNCTION
function formattedDate(d = new Date) {
  return [d.getDate(), d.getMonth()+1, d.getFullYear()]
      .map(n => n < 10 ? `0${n}` : `${n}`).join('/');
}
$(document).ready(init);
