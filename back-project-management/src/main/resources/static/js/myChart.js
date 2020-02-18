var chartDataStr = decodeHtml(chartData);

var chartJsonArray = JSON.parse(chartDataStr);
var arrayLength = chartJsonArray.length;

var numericData=[];
var labelData=[];

for(var i=0; i<arrayLength; i++){
	numericData[i] = chartJsonArray[i].value;
	labelData[i] = chartJsonArray[i].label;
}

// For a pie chart
new Chart(document.getElementById("myPieChart"), {
    type: 'pie',
    
    // The data for our dataset
    data: {
        labels: labelData,
        datasets: [{
            label: 'My First dataset',
            backgroundColor: ["#3e04dc", "#8eac5c", "#3ec4a4"],
//            borderColor: 'rgb(255, 99, 132)',
            data: numericData
        }]
    },

    // Configuration options go here
    options: {
    	title:{
    		display: true,
    		text: 'Project Statuses'
    	}
    }
});

// [{"value":1, "label":"NOTSTARTED"}, {"value":2, "label":"INPROGRESS"}, {"value":1, "label":"COMPLETED"}]
function decodeHtml(html){
	
	var txt = document.createElement("textarea")
	txt.innerHTML = html;
	
	return txt.value;
}