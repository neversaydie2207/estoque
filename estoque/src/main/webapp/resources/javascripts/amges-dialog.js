$().ready(function()
{
	amges = 
	{	
		showDialog: function(type)
		{
			if(type == 'processando')
	    	{
	        	swal({
	                title: "Processando...",
	                html:  
	                	'<div class="progress">' +
	                	  ' <div class="indeterminate" style="background-color: #67ceff !important"></div>' + 
                    	'</div>',
	                buttonsStyling: false,
	                showConfirmButton: false,
	                allowOutsideClick: false
	            });

	    	}
	        else if(type == 'error-message')
	        {
	        	swal({
	                title: "Ops! Ocorreu algo inesperado",
	                text: "Não foi possível continuar. <br/>Verifique os erros e tente novamente.",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success",
	                type: "error"
	            });

	    	}
	        else if(type == 'registro-usuario-success')
	        {
	        	swal({
	                title: "Obrigado por se cadastrar!",
	                text: "Em alguns instantes você receberá um e-mail para ativação de sua conta.",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success",
	                type: "success"
	            });

	    	}
	        else if(type == 'solicita-senha-success')
	        {
	        	swal({
	                title: "Olá! Agora é com a gente :)",
	                text: "Em alguns instantes você receberá um e-mail com o passo a passo para criação de sua nova senha.",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success",
	                type: "success"
	            });

	    	}
	        else if(type == 'recupera-senha-success')
	        {
	        	swal({
	                title: "Está Tudo Pronto!)",
	                text: "Sua senha foi alterada com sucesso e você já pode voltar a utilizar nossa plataforma.",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success",
	                type: "success"
	            });

	    	}
	        else if(type == 'registro-success')
	        {
	        	swal({
	                title: "Registro realizado com sucesso",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success",
	                type: "success"
	            });
	        }
	        else if(type == "registro-error")
	        {
	        	swal({
	                title: "Registro Não Realizado",
	                text: "Por favor, tente novamente.",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success",
	                type: "error"
	            });
	        }
	        else if(type == 'close')
	        {
	        	swal.close();
	        }
		},
		
		showNotification: function(from, align, msg, type){
	        //type = ['','info','success','warning','danger','rose','primary'];

	        //color = Math.floor((Math.random() * 6) + 1);

	    	$.notify({
	        	icon: "notifications",
	        	message: msg

	        },{
	            type: type,
	            timer: 2000,
	            placement: {
	                from: from,
	                align: align
	            }
	        });
		},
	
		closeModal: function(modal)
		{
			$('#'+ modal).modal('hide');
		},
		
		openModal: function(modal)
		{
			$('#'+ modal).modal('show');
		},
		
		nextTab: function (grupotab, tab, campofoco){
			$(grupotab + ' a[href="' + tab + '"]').tab('show'); // Select tab by name 
			$(campofoco).focus();
		},
		
		initFormExtendedDatetimepickers: function(){
	        $('.datetimepicker').datetimepicker({
	        	icons: {
	                time: "fa fa-clock-o",
	                date: "fa fa-calendar",
	                up: "fa fa-chevron-up",
	                down: "fa fa-chevron-down",
	                previous: 'fa fa-chevron-left',
	                next: 'fa fa-chevron-right',
	                today: 'fa fa-screenshot',
	                clear: 'fa fa-trash',
	                close: 'fa fa-remove',
	                inline: true
	            }
	         });

	         $('.datepicker').datetimepicker({
	            format: 'DD/MM/YYYY',
	            icons: {
	                time: "fa fa-clock-o",
	                date: "fa fa-calendar",
	                up: "fa fa-chevron-up",
	                down: "fa fa-chevron-down",
	                previous: 'fa fa-chevron-left',
	                next: 'fa fa-chevron-right',
	                today: 'fa fa-screenshot',
	                clear: 'fa fa-trash',
	                close: 'fa fa-remove',
	                inline: true
	            }
	         	
	         });

	         $('.timepicker').datetimepicker({
  	            format: 'H:mm',    // use this format if you want the 24hours timepicker
	            //format: 'h:mm A',    //use this format if you want the 12hours timpiecker with AM/PM toggle
  	            icons: {
	                time: "fa fa-clock-o",
	                date: "fa fa-calendar",
	                up: "fa fa-chevron-up",
	                down: "fa fa-chevron-down",
	                previous: 'fa fa-chevron-left',
	                next: 'fa fa-chevron-right',
	                today: 'fa fa-screenshot',
	                clear: 'fa fa-trash',
	                close: 'fa fa-remove',
	                inline: true

	            }
	         });
	         
	         $('.datepickerBr').datetimepicker({
	        	 language:  'pt-BR',
	        	 
	        	 icons: {
		                time: "fa fa-clock-o",
		                date: "fa fa-calendar",
		                up: "fa fa-chevron-up",
		                down: "fa fa-chevron-down",
		                previous: 'fa fa-chevron-left',
		                next: 'fa fa-chevron-right',
		                today: 'fa fa-screenshot',
		                clear: 'fa fa-trash',
		                close: 'fa fa-remove',
		                inline: true
		            }
		         	
	            
	         });
	         
		}, // fim initFormExtendedDatetimepickers
		
		
		eventStatusAjax: function(data)
		{
			switch (data.status) {
	        case "begin": // Before the ajax request is sent.
	        	maximus.showDialog('processando');
	            break;

	        case "complete": // After the ajax response is arrived.
	        	maximus.showDialog('close');
	            break;

	        case "success": // After update of HTML DOM based on ajax response..

	            break;
			}
			
		} // fim eventStatusAjax
		
	}
	
});