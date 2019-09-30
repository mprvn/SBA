/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 99.52857142857142, "KoPercent": 0.4714285714285714};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.95775, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [0.97675, 500, 1500, "Get - All Projects "], "isController": false}, {"data": [0.977, 500, 1500, "Get - Task"], "isController": false}, {"data": [0.9625, 500, 1500, "Get - All tasks "], "isController": false}, {"data": [0.976, 500, 1500, "Get - ParentTasks"], "isController": false}, {"data": [0.90675, 500, 1500, "Get - All Project tasks "], "isController": false}, {"data": [0.92875, 500, 1500, "Get - All Users "], "isController": false}, {"data": [0.9765, 500, 1500, "Get - task "], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 14000, 66, 0.4714285714285714, 247.79528571428568, 2, 1696, 471.0, 576.0, 840.9699999999993, 114.9481912081055, 164.48431880880833, 21.376386562556448], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Throughput", "Received", "Sent"], "items": [{"data": ["Get - All Projects ", 2000, 7, 0.35, 192.5805000000001, 2, 1488, 365.8000000000002, 477.84999999999945, 796.4700000000005, 16.50886939007982, 22.602963754777253, 3.079291067876216], "isController": false}, {"data": ["Get - Task", 2000, 6, 0.3, 197.56249999999983, 3, 1500, 377.9000000000001, 482.9499999999998, 747.8500000000001, 16.514867509475405, 10.192769791004352, 3.0642820574221945], "isController": false}, {"data": ["Get - All tasks ", 2000, 9, 0.45, 267.73649999999935, 5, 1462, 459.9000000000001, 567.0, 848.95, 16.433313613356997, 33.95790195885098, 3.0170536712022615], "isController": false}, {"data": ["Get - ParentTasks", 2000, 5, 0.25, 191.2194999999997, 2, 1333, 364.9000000000001, 478.6999999999989, 715.8500000000001, 16.5174589541145, 5.242357578210168, 3.048632560866836], "isController": false}, {"data": ["Get - All Project tasks ", 2000, 20, 1.0, 358.0005000000003, 10, 1468, 583.9000000000001, 699.0, 1002.95, 16.490629199957123, 21.998241686661554, 3.1725136253823765], "isController": false}, {"data": ["Get - All Users ", 2000, 15, 0.75, 334.04800000000006, 10, 1318, 545.9000000000001, 658.7499999999991, 940.94, 16.498519257896604, 60.96718444519605, 3.029025020004455], "isController": false}, {"data": ["Get - task ", 2000, 4, 0.2, 193.41950000000008, 3, 1696, 369.0, 476.89999999999964, 720.9300000000001, 16.485871608031914, 10.1748738830822, 3.058901958521547], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Percentile 1
            case 8:
            // Percentile 2
            case 9:
            // Percentile 3
            case 10:
            // Throughput
            case 11:
            // Kbytes/s
            case 12:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["The operation lasted too long: It took 1,081 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,055 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,033 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,163 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,029 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,014 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 2, 3.0303030303030303, 0.014285714285714285], "isController": false}, {"data": ["The operation lasted too long: It took 1,096 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,074 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,271 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,103 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,026 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,004 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,201 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,091 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,124 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 2, 3.0303030303030303, 0.014285714285714285], "isController": false}, {"data": ["The operation lasted too long: It took 1,135 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,168 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,050 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,696 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,224 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,094 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,441 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,078 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 2, 3.0303030303030303, 0.014285714285714285], "isController": false}, {"data": ["The operation lasted too long: It took 1,147 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,012 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,250 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,462 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,003 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,044 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,018 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,450 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,047 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,130 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,200 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,208 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,260 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,054 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,309 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,335 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,076 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,468 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,488 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,020 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 2, 3.0303030303030303, 0.014285714285714285], "isController": false}, {"data": ["The operation lasted too long: It took 1,005 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,239 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,031 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,217 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,176 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,180 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,500 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,165 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,116 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,127 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,039 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,220 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,175 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,333 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,318 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,006 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,131 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,117 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}, {"data": ["The operation lasted too long: It took 1,086 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, 1.5151515151515151, 0.007142857142857143], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 14000, 66, "The operation lasted too long: It took 1,014 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 2, "The operation lasted too long: It took 1,081 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["Get - All Projects ", 2000, 7, "The operation lasted too long: It took 1,488 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Get - Task", 2000, 6, "The operation lasted too long: It took 1,239 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Get - All tasks ", 2000, 9, "The operation lasted too long: It took 1,020 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Get - ParentTasks", 2000, 5, "The operation lasted too long: It took 1,054 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Get - All Project tasks ", 2000, 20, "The operation lasted too long: It took 1,020 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Get - All Users ", 2000, 15, "The operation lasted too long: It took 1,124 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["Get - task ", 2000, 4, "The operation lasted too long: It took 1,005 milliseconds, but should not have lasted longer than 1,000 milliseconds.", 1, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
