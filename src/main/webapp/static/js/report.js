var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
function getReportUrl(){

	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/report";
}
function getInventoryReport(){
    $('#sales-table-div').hide();
    $('#brand-table-div').hide();
    $('#inventory-table-div').show();
    var brandString = $('#inputBrand').val();
    var categoryString = $('#inputCategory').val();
    var url = getReportUrl() + "/inventory?brand="+brandString+"&category="+categoryString;
    var $tbody = $('#inventory-table').find('tbody');
    $tbody.empty();
    $.ajax({
       url: url,
       type: 'POST',
       headers: {
        'Content-Type': 'application/json'
       },
       success:async function(inventoryReportData) {
            for(var row of inventoryReportData){
                 var row = '<tr>'
                    + '<td>' + row.brand + '</td>'
                    + '<td>' + row.category + '</td>'
                    + '<td>'  + row.quantity + '</td>'
                    + '</tr>';
                 $tbody.append(row);
            }
       },
       error: handleAjaxError
    });
}
function getBrandReport(){
    $('#sales-table-div').hide();
    $('#brand-table-div').show();
    $('#inventory-table-div').hide();
    var dateJson = getDateJson();
    var brandString = $('#inputBrand').val();
    var categoryString = $('#inputCategory').val();
    var url = getReportUrl() + "/brand?brand="+brandString+"&category="+categoryString;
    var $tbody = $('#brand-table').find('tbody');
    $tbody.empty();
     $.ajax({
       url: url,
       type: 'POST',
       data: dateJson,
       headers: {
        'Content-Type': 'application/json'
       },
       success:async function(brandReportData) {
            for(var row of brandReportData){
                 var row = '<tr>'
                    + '<td>' + row.brand + '</td>'
                    + '<td>' + row.quantity + '</td>'
                    + '<td>'  + row.revenue + '</td>'
                    + '</tr>';
                 $tbody.append(row);
            }
       },
       error: handleAjaxError
    });
}
function getSalesReport(){
    $('#sales-table-div').show();
    $('#brand-table-div').hide();
    $('#inventory-table-div').hide();
    var dateJson = getDateJson();
    var brandString = $('#inputBrand').val();
    var categoryString = $('#inputCategory').val();
    var url = getReportUrl() + "/sales?brand="+brandString+"&category="+categoryString;
    var $tbody = $('#sales-table').find('tbody');
    $tbody.empty();
        $.ajax({
           url: url,
           type: 'POST',
           data: dateJson,
           headers: {
            'Content-Type': 'application/json'
           },
           success:async function(salesReportData) {
                for(var row of salesReportData){
                     var row = '<tr>'
                        + '<td>' + row.brand + '</td>'
                        + '<td>' + row.category + '</td>'
                        + '<td>' + row.quantity + '</td>'
                        + '<td>'  + row.revenue + '</td>'
                        + '</tr>';
                     $tbody.append(row);
                }
           },
           error: handleAjaxError
        });
}
function getSalesReportCsv(){
    tableToCSV("#sales-table","sales_report"+new Date()+".csv")
}
function getInventoryReportCsv(){
    tableToCSV("#inventory-table","inventory_report"+new Date()+".csv")
}
function getBrandReportCsv(){
    tableToCSV("#brand-table","brnad_report"+new Date()+".csv")
}
function tableToCSV(tableName,fileName) {

            // Variable to store the final csv data
            var csv_data = [];

            // Get each row data
            var rows = $(tableName).find('tr')
            for (var i = 0; i < rows.length; i++) {

                // Get each column data
                var cols = rows[i].querySelectorAll('td,th');

                // Stores each csv row data
                var csvrow = [];
                for (var j = 0; j < cols.length; j++) {

                    // Get the text data of each cell
                    // of a row and push it to csvrow
                    csvrow.push(cols[j].innerHTML);
                }

                // Combine each column value with comma
                csv_data.push(csvrow.join(","));
            }

            // Combine each row data with new line character
            csv_data = csv_data.join('\n');

            // Call this function to download csv file
            downloadCSVFile(csv_data,fileName);

        }

        function downloadCSVFile(csv_data,fileName) {

            // Create CSV file object and feed
            // our csv_data into it
            CSVFile = new Blob([csv_data], {
                type: "text/csv"
            });

            // Create to temporary link to initiate
            // download process
            var temp_link = document.createElement('a');

            // Download csv file
            temp_link.download = fileName;
            var url = window.URL.createObjectURL(CSVFile);
            temp_link.href = url;

            // This link should not be displayed
            temp_link.style.display = "none";
            document.body.appendChild(temp_link);

            // Automatically click the link to
            // trigger download
            temp_link.click();
            document.body.removeChild(temp_link);
        }
function getDateJson(){
    let startDate = $('#startDate').val();
    let endDate = $('#endDate').val();
    brandString = $('#inputBrand').val();
    categoryString = $('#inputCategory').val();
    var startDateTimestamp = Date.parse(startDate);
    var endDateTimestamp = Date.parse(endDate);
    var startDateMilliSeconds = new Date(startDateTimestamp).getTime();
    var endDateMilliSeconds = new Date(endDateTimestamp).getTime();
    var DateObject = {"endDate":endDateMilliSeconds,"startDate":startDateMilliSeconds};
    var DateJson = JSON.stringify(DateObject);
    return DateJson;
}
//INITIALIZATION CODE

function init(){
    var today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    var nextDate = new Date();
    nextDate.setDate(today.getDate()+1);
    var minimumStartDate = new Date();
    minimumStartDate.setDate(today.getDate()-365);
    var initialStartDate = new Date();
    var initialEndDate = new Date();
    initialStartDate.setDate(today.getDate() - 90);
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
$('#getBrandReport').click(getBrandReport);
$('#getSalesReport').click(getSalesReport);
$('#getInventoryReport').click(getInventoryReport);
$('#getInventoryReportCsv').click(getInventoryReportCsv);
$('#getSalesReportCsv').click(getSalesReportCsv);
$('#getBrandReportCsv').click(getBrandReportCsv);
$('#endDate').val((initialEndDate.getMonth()+1)+"/"+initialEndDate.getDate()+"/"+initialEndDate.getFullYear());
$('#startDate').val(initialStartDate.getMonth()+1+"/"+initialStartDate.getDate()+"/"+initialStartDate.getFullYear());
$('#sales-table-div').hide();
$('#brand-table-div').hide();
$('#inventory-table-div').hide();
}
$(document).ready(init);