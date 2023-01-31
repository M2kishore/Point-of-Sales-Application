var searchString = "";
var orderIds = [];
function getOrderUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getInvoice(id){
    var orderId = orderIds[id].id;
    var orderDate = new Date(orderIds[id].date).toString().substring(0,24);
    var url = getOrderUrl()+"/"+orderId;
    $.ajax({
       url: url,
       type: 'GET',
       success: function(billedOrder) {
            var url = getOrderUrl()+"/pdf";
            var total = billedOrder.reduce((total,transaction)=>total+transaction.sellingPrice,0);
            var orderObject = {"billForm":billedOrder,"total":total,"orderId":orderId,"date":orderDate};
            var orderJson = JSON.stringify(orderObject);
                $.ajax({
                   url: url,
                   type: 'POST',
                     data: orderJson,
                     headers: {
                    'Content-Type': 'application/json'
                   },
                     xhrFields: {
                    responseType: 'blob'
                 },
                   success: function(blob) {
                        var link=document.createElement('a');
                        link.href=window.URL.createObjectURL(blob);
                        link.download="Invoice_" + new Date() + ".pdf";
                        link.click();
                   },
                   error: handleAjaxError
                });
       },
       error: handleAjaxError
    });
}

//UI DISPLAY METHODS

function displayOrderList(){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var searchString = $("#inputSearch").val();
	var startDateString = $('#startDate').val();
	var endDateString = $('#endDate').val();
	var startDate = new Date(Date.parse(startDateString)).getTime();
	var endDate = new Date(Date.parse(endDateString)).getTime();
	for(var id in orderIds){
	    var idString = orderIds[id].id.toString();
	    var orderDate = orderIds[id].date
	    if(!idString.includes(searchString) || endDate < orderDate || startDate > orderDate){
	        continue;
	    }
		var buttonHtml = ' <button onclick="getInvoice(' + id + ')">invoice</button>'
		var orderDateString = new Date(orderDate).toString().substring(0,25);
		var row = '<tr>'
		+ '<td>' + idString + '</td>'
		+ '<td>' + orderDateString + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
    $("#inputSearch").on('input',displayOrderList);
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
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
    $('#startDate').on("change",displayOrderList);
    $('#endDate').on('change',displayOrderList);
    var url = getOrderUrl() + "/all/id";
            $.ajax({
               url: url,
               type: 'GET',
               success: function(data) {
                    orderIds = data;
                    displayOrderList();
               },
               error: handleAjaxError
            });
}

$(document).ready(init);
