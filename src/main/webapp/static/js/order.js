var searchString = "";
var orderIds = [];
function getOrderUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getInvoice(id){
    var orderId = orderIds[id].id;
    var orderDate = new Date(orderIds[id].date).toString();
    var url = getOrderUrl()+"/"+orderId;
    $.ajax({
       url: url,
       type: 'GET',
       success: function(billedOrder) {
            var url = getOrderUrl()+"/pdf";
            var total = billedOrder.reduce((total,transaction)=>total+transaction.sellingPrice,0);
            var orderObject = {"billForm":billedOrder,"total":total,"orderId":orderId,"date":orderDate};
            var orderJson = JSON.stringify(orderObject);
            console.log(orderJson)
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
                        console.log(blob.size);
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
	for(var id in orderIds){
	    console.log(id)
		var buttonHtml = ' <button onclick="getInvoice(' + id + ')">invoice</button>'
		var orderDate = new Date(orderIds[id].date).toString();
		var row = '<tr>'
		+ '<td>' + orderIds[id].id + '</td>'
		+ '<td>' + orderDate + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//INITIALIZATION CODE
function init(){
    console.log("pool")
    var url = getOrderUrl() + "/all/id";
            $.ajax({
               url: url,
               type: 'GET',
               success: function(data) {
                    orderIds = data;
                    console.log(orderIds)
                    displayOrderList();
               },
               error: handleAjaxError
            });
}

$(document).ready(init);
