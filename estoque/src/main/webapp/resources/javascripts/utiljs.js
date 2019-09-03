$().ready(function()
{
	utiljs = 
	{	
		
		gerarChartBar: function(context)
		{
			
			//console.log("Labls: ")

			var data = OmniFaces.Ajax.data;
	        
	        var labels = [];
	        var qtdServidoresLotados = [];
	        var qtdServidoresNecessarios = [];
	        
	        var chLotada = [];
	        var chNecessaria = [];
	        
	        for (var i in data.dados) 
	        {
	            
	        	if(data.dados[i].funcao !== 'AGT COMUNITARIO DE SAUDE')
	        	{
	        		labels.push(data.dados[i].funcao);
	        		
	        		qtdServidoresLotados.push(data.dados[i].quantidadeServidorLotado);
		        	qtdServidoresNecessarios.push(data.dados[i].quantidadeServidorNecessario);
		        	
		        	chLotada.push(data.dados[i].cargaHorariaLotada);
		        	chNecessaria.push(data.dados[i].cargaHorariaNecessaria);
	        	}
	        	
	        	
             
	        }
	         
	        //console.log("Labls Dados OK: "+ labels)
	        
			
			var myChart = new Chart(context, 
			{
				type: data.typeChart,
				
				//type: 'horizontalBar',
			    data: 
			    {
			    	labels: labels,
			    	
			    	datasets: 
			    	[
			    		{
			    			//label: 'QTD SERVIDORES LOTADOS',
			    			label: 'CH LOTADA',
			    			
			    			backgroundColor: 'rgb(0,155,219,0.8)',
							borderColor: 'rgb(0,155,219,0.8)',
			    			
			    			borderWidth: 1,
			    			
			    			//data: qtdServidoresLotados,
			    			data: chLotada,
			    			
			    			datalabels: 
			    			{
			    				anchor: 'end',
								align: 'right',
							}

			    		},
			    		{
				            //label: 'QTD SERVIDORES NECESSÁRIOS',
			    			label: 'CH NECESSÁRIA',
				            
							backgroundColor: 'rgb(255,96,0,0.8)',
							borderColor: 'rgb(255,96,0,0.8)',
							borderWidth: 1,
				            
				            //data: qtdServidoresNecessarios,
							data: chNecessaria,
				            
				            datalabels: 
				            {
								anchor: 'end',
								align: 'right',
								
							}

				        }
				    ]
			    },
			    options: 
			    {
			    	layout: {
			            padding: {
			                left: 10,
			                right: 50,
			                top: 0,
			                bottom: 0
			            }
			        },
			   
			    	
			    	scales: 
			    	{
			            yAxes: 
			            [{
			                ticks: 
			                {
			                	
			                	categoryPercentage: 0.9,
								barPercentage: 0.7,
								
								gridLines: 
								{ 
									display: false,
									drawTicks: false,
								},
			            
			                },
			                
			                scaleLabel: 
			                {
				                display: false,
				                labelString: " LOTUS - SISTEMA INTEGRADO DE GESTÃO E LOTAÇÃO DE SERVIDORES",
				                fontColor: "green"
				            }
			            }],
			            
			            xAxes: 
			            [{
			            	ticks:
							{
			            		beginAtZero:true,
			                    steps: 20,
		                        stepValue:10 ,
		                        //max: 200
		                        
		                      
							},
							
							scaleLabel: 
			                {
				                display: true,
				                labelString: "LOTUS - SISTEMA INTEGRADO DE GESTÃO E LOTAÇÃO DE SERVIDORES",
				                fontColor: "green"
				            }
							
			            }],
			            
			            
			        },
			        
			    	plugins: 
			    	{
						datalabels: 
						{
							//color: 'black',
							display: function(context) 
							{
								return context.dataset.data[context.dataIndex] > 0;
							},
							
							font: 
							{
								weight: 'bold'
							},
							
							formatter: Math.round
						}
					},
			    
			    	elements: 
			    	{
						rectangle: 
						{
							borderWidth: 2,
						}
					},
					
					responsive: true,
					
					legend: 
					{
			            display: true,

						position: 'right',
						
						labels: {
		                	fontSize: 10,
		                	
			            },
			            
					},
					
					title: 
					{
						display: true,
						text: data.textDisplay,
						lineHeight: 2.8, // Espaçamento de linha do titulo
					},
					
			        tooltips: 
			        {
			        	backgroundColor: 'rgba(67, 160, 71,0.8)',
			        },
					
			    }
			});
			
			
		}, //fim chartBar
		
		initChartBar: function(context)
		{
			
	        var data = OmniFaces.Ajax.data;
	        
	        var labels = [];
	        var coloR = [];
	        var qtdServidores = [];
	        
	        var dynamicColors = function() {
	            var r = Math.floor(Math.random() * 255);
	            var g = Math.floor(Math.random() * 255);
	            var b = Math.floor(Math.random() * 255);
	            return "rgb(" + r + "," + g + "," + b + ", 0.2 )";
	         };
			
	         for (var i in data.dados) {
	             
	        	 labels.push(data.dados[i].funcao);
	        	 qtdServidores.push(data.dados[i].quantidadeServidorLotado);
	             coloR.push(dynamicColors());
	          }
	         
			
			var myChart = new Chart(context, 
			{
			    type: 'bar',
			    data: 
			    {
			        
			    	labels: labels,
			    	
			    	datasets: 
			    	[{
			            label: data.labelTitle,
			            
			            data: qtdServidores,
			            
			            backgroundColor: coloR,
			            
			            borderColor: coloR,
			            
			            borderWidth: 2
			        }]
			    },
			    
			    options: 
			    {
			        scales: 
			        {
			        	yAxes: [{
							type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
							display: false,
							position: 'left',
						}],
			        },
			        
			        legend: 
					{
			            display: false,

						position: 'right',
						
						labels: {
		                	fontSize: 10,
			            },
					},
					
					title: 
					{
						display: true,
						text: 'QUADRO DE PROFISSIONAIS',
						lineHeight: 2.8, // Espaçamento de linha do titulo
					},
					
			        tooltips: 
			        {
			        	backgroundColor: 'rgba(67, 160, 71,0.8)',
			        },
			        
			        responsive: true,
			        
			    }
			});
			
		},//fim chart
		
		/* salvar pdf */
		
		exportarPdf: function (elemento, myPageTitle)
		{
			$('#'+elemento).printThis({
				
				debug: false,               // show the iframe for debugging
			    importCSS: true,            // import parent page css
			    importStyle: false,         // import style tags
			    printContainer: true,       // print outer container/$.selector
			    loadCSS: "",                // path to additional css file - use an array [] for multiple
			    pageTitle: "lotus_quadro_profissional",              // add title to print page
			    removeInline: false,        // remove inline styles from print elements
			    removeInlineSelector: "*",  // custom selectors to filter inline styles. removeInline must be true
			    printDelay: 333,            // variable print delay
			    header: "<h4 class='center black-text' style='font-weight: bold;'>" + myPageTitle + "</h3>",               // prefix to html
			    footer: null,               // postfix to html
			    base: false,                // preserve the BASE tag or accept a string for the URL
			    formValues: true,           // preserve input/form values
			    canvas: true,              // copy canvas content
			    doctypeString: '...',       // enter a different doctype for older markup
			    removeScripts: false,       // remove script tags from print content
			    copyTagClasses: false,      // copy classes from the html & body tag
			    beforePrintEvent: null,     // function for printEvent in iframe
			    beforePrint: null,          // function called before iframe is filled
			    afterPrint: null            // function called before iframe is removed
				
			});
		},
		
		gerarPdf: function (codigoHTML) 
		{
			var doc = new jsPDF('portrait', 'pt', 'a4'),
			
			data = new Date();
		
			margins = 
			{
			    top: 40,
			    bottom: 60,
			    left: 40,
			    width: 1000
			};
			
			doc.fromHTML($('#'+codigoHTML).get(0),
               	margins.left, // x coord
               	margins.top, { pagesplit: true },
               	function(dispose)
               	{
               		doc.save("Relatorio - "+data.getDate()+"/"+data.getMonth()+"/"+data.getFullYear()+".pdf");
               	});
		}
	
		
		
	} // fim utiljs
	
});