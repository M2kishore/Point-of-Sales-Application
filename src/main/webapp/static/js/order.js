var searchString = "";
var orderIds = [];
function getOrderUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}
function showOrder(id){
    var orderId = orderIds[id].id;
    var orderDate = new Date(orderIds[id].date).toString().substring(0,25);
        var url = getOrderUrl()+"/"+orderId;
        $.ajax({
           url: url,
           type: 'GET',
           success: function(currentOrder) {
                //display in the modal
                $('#order-show-modal').modal('toggle');
                $('#orderId').text(orderId);
                var total = 0;
                var $tbody = $('#order-show-table').find('tbody');
                    $tbody.empty();
                    for(var product of currentOrder){
                        total+= product.sellingPrice;
                        var row = '<tr>'
                        + '<td>' + product.name + '</td>'
                        + '<td>'  + product.quantity + '</td>'
                        + '<td>' + product.sellingPrice + '</td>'
                        + '</tr>';
                        $tbody.append(row);
                    }
                $('#total').text(total);
           },
           error: handleAjaxError
        });
}
function getInvoice(id){
    var orderId = orderIds[id].id;
    var url = getOrderUrl()+"/pdf/"+orderId;
    $.ajax({
       url: url,
       type: 'GET',
         xhrFields: {
        responseType: 'blob'
     },
       success: function(blob) {
            var link=document.createElement('a');
            link.href=window.URL.createObjectURL(blob);
            link.download="Invoice_" + new Date() + ".pdf";
            link.click();
            toastr.info("Your Invoice is Downloading","Downloading",{"timeOut": 5000,});
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
		var buttonHtml = ' <button class="btn btn-info btn-sm" onclick="getInvoice(' + id + ')">invoice</button>'
		buttonHtml += ' <button class="btn btn-primary btn-sm" onclick="showOrder(' + id + ')">details</button>'
        var orderDateString = new Date(orderDate).toLocaleDateString('en-us', { weekday:"long", year:"numeric", month:"short", day:"numeric"}) ;
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
    $('#add-order').focus();
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    var nextDate = new Date();
    nextDate.setDate(today.getDate()+1);
    var minimumStartDate = new Date();
    minimumStartDate.setDate(today.getDate()-365);
    var initialStartDate = new Date();
    var initialEndDate = new Date();
    initialStartDate.setDate(today.getDate() - 90);
    initialEndDate.setDate(today.getDate() + 1);
    $('#startDate').datepicker({
        uiLibrary: 'bootstrap4',
        iconsLibrary: 'fontawesome',
        format: 'mm/dd/yyyy',
        maxDate: today,
        minDate: minimumStartDate
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
    $('#startDate').val(initialStartDate.getMonth()+1+"/"+initialStartDate.getDate()+"/"+initialStartDate.getFullYear());
    $('#endDate').on('change',displayOrderList);
    $('#endDate').val((initialEndDate.getMonth()+1)+"/"+initialEndDate.getDate()+"/"+initialEndDate.getFullYear());
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
