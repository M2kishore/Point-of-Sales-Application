var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
function getReportUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}

function getReport(){
    let startDate = $('#startDate').val();
    let endDate = $('#endDate').val();
    var startDateTimestamp = Date.parse(startDate);
    var endDateTimestamp = Date.parse(endDate);
    var startDateMilliSeconds = new Date(startDateTimestamp).getTime();
    var endDateMilliSeconds = new Date(endDateTimestamp).getTime();
    var DateObject = {"endDate":endDateMilliSeconds,"startDate":startDateMilliSeconds};
    console.log(DateObject);
    var DateJson = JSON.stringify(DateObject);
    var url = getReportUrl();

    $.ajax({
       url: url,
       type: 'POST',
       data: DateJson,
       headers: {
        'Content-Type': 'application/json'
       },
       success: function(response) {
            console.log(response);
            $("#order-count").text(response.length);
            getQuantityAndSellingPrice(response);
       },
       error: handleAjaxError
    });
}
function getQuantityAndSellingPrice(orderArray){
    var url = getReportUrl()+"/order";
    var startId = orderArray.shift();
    var endId = orderArray.pop();
    var idObject = {"endId":endId,"startId":startId};
    var idJson = JSON.stringify(idObject);
    $.ajax({
       url: url,
       type: 'POST',
       data: idJson,
       headers: {
        'Content-Type': 'application/json'
       },
       success: function(orderItems) {
        console.log(orderItems);
            var quantity = 0;
            var revenue = 0;
            orderItems.map(item=>{
                quantity += item.quantity;
                revenue += item.sellingPrice;
            });
            $("#item-count").text(quantity);
            $("#total-revenue").text(revenue);
       },
       error: handleAjaxError
    });
}
//INITIALIZATION CODE

function init(){
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    today.setDate(today.getDate()+1);
    $('#startDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        format: 'mm/dd/yyyy',
        maxDate: function () {
            return $('#endDate').val();
        }
    });
    $('#endDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        format: 'mm/dd/yyyy',
        minDate: function () {
            return $('#startDate').val();
        },
        maxDate: today
    });
$('#getReport').click(getReport);
}
$(document).ready(init);

