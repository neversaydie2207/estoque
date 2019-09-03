$().ready(function(){
    $sidebar = $('.sidebar');
    $sidebar_img_container = $sidebar.find('.sidebar-background');

    $full_page = $('.full-page');

    $sidebar_responsive = $('body > .navbar-collapse');

    window_width = $(window).width();

    fixed_plugin_open = $('.sidebar .sidebar-wrapper .nav li.active a p').html();

    if( window_width > 767 && fixed_plugin_open == 'Dashboard' ){
        if($('.fixed-plugin .dropdown').hasClass('show-dropdown')){
            $('.fixed-plugin .dropdown').addClass('open');
        }

    }

    $('.fixed-plugin a').click(function(event){
      // Alex if we click on switch, stop propagation of the event, so the dropdown will not be hide, otherwise we set the  section active
        if($(this).hasClass('switch-trigger')){
            if(event.stopPropagation){
                event.stopPropagation();
            }
            else if(window.event){
               window.event.cancelBubble = true;
            }
        }
    });

    $('.fixed-plugin .active-color span').click(function(){
        $full_page_background = $('.full-page-background');

        $(this).siblings().removeClass('active');
        $(this).addClass('active');

        var new_color = $(this).data('color');

        if($sidebar.length != 0){
            $sidebar.attr('data-active-color',new_color);
        }

        if($full_page.length != 0){
            $full_page.attr('filter-color',new_color);
        }

        if($sidebar_responsive.length != 0){
            $sidebar_responsive.attr('data-color',new_color);
        }
    });

    $('.fixed-plugin .background-color span').click(function(){
        $(this).siblings().removeClass('active');
        $(this).addClass('active');

        var new_color = $(this).data('color');

        if($sidebar.length != 0){
            $sidebar.attr('data-background-color',new_color);
        }
    });

    $('.fixed-plugin .img-holder').click(function(){
        $full_page_background = $('.full-page-background');

        $(this).parent('li').siblings().removeClass('active');
        $(this).parent('li').addClass('active');


        var new_image = $(this).find("img").attr('src');

        if( $sidebar_img_container.length !=0 && $('.switch-sidebar-image input:checked').length != 0 ){
            $sidebar_img_container.fadeOut('fast', function(){
               $sidebar_img_container.css('background-image','url("' + new_image + '")');
               $sidebar_img_container.fadeIn('fast');
            });
        }

        if($full_page_background.length != 0 && $('.switch-sidebar-image input:checked').length != 0 ) {
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $full_page_background.fadeOut('fast', function(){
               $full_page_background.css('background-image','url("' + new_image_full_page + '")');
               $full_page_background.fadeIn('fast');
            });
        }

        if( $('.switch-sidebar-image input:checked').length == 0 ){
            var new_image = $('.fixed-plugin li.active .img-holder').find("img").attr('src');
            var new_image_full_page = $('.fixed-plugin li.active .img-holder').find('img').data('src');

            $sidebar_img_container.css('background-image','url("' + new_image + '")');
            $full_page_background.css('background-image','url("' + new_image_full_page + '")');
        }

        if($sidebar_responsive.length != 0){
            $sidebar_responsive.css('background-image','url("' + new_image + '")');
        }
    });

    $('.switch-sidebar-image input').change(function(){
        $full_page_background = $('.full-page-background');

        $input = $(this);

        if($input.is(':checked')){
            if($sidebar_img_container.length != 0){
                $sidebar_img_container.fadeIn('fast');
                $sidebar.attr('data-image','#');
            }

            if($full_page_background.length != 0){
                $full_page_background.fadeIn('fast');
                $full_page.attr('data-image','#');
            }

            background_image = true;
        } else {
            if($sidebar_img_container.length != 0){
                $sidebar.removeAttr('data-image');
                $sidebar_img_container.fadeOut('fast');
            }

            if($full_page_background.length != 0){
                $full_page.removeAttr('data-image','#');
                $full_page_background.fadeOut('fast');
            }

            background_image = false;
        }
    });

    $('.switch-sidebar-mini input').change(function(){
        $body = $('body');

        $input = $(this);

        if(md.misc.sidebar_mini_active == true){
            $('body').removeClass('sidebar-mini');
            md.misc.sidebar_mini_active = false;

            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar();

        }else{

            $('.sidebar .collapse').collapse('hide').on('hidden.bs.collapse',function(){
                $(this).css('height','auto');
            });

            $('.sidebar .sidebar-wrapper, .main-panel').perfectScrollbar('destroy');

            setTimeout(function(){
                $('body').addClass('sidebar-mini');

                $('.sidebar .collapse').css('height','auto');
                md.misc.sidebar_mini_active = true;
            },300);
        }

        // we simulate the window Resize so the charts will get updated in realtime.
        var simulateWindowResize = setInterval(function(){
            window.dispatchEvent(new Event('resize'));
        },180);

        // we stop the simulation of Window Resize after the animations are completed
        setTimeout(function(){
            clearInterval(simulateWindowResize);
        },1000);

    });

    /*
    $('#twitter').sharrre({
      share: {
        twitter: true
      },
      enableHover: false,
      enableTracking: false,
      buttons: { twitter: {via: 'CreativeTim'}},
      click: function(api, options){
        api.simulateClick();
        api.openPopup('twitter');
      },
      template: '<i class="fa fa-twitter"></i> &middot; 45',
      url: 'http://demos.creative-tim.com/material-dashboard-pro/examples/dashboard.html'
    });

    $('#facebook').sharrre({
      share: {
        facebook: true
      },
      enableHover: false,
      enableTracking: false,
      click: function(api, options){
        api.simulateClick();
        api.openPopup('facebook');
      },
      template: '<i class="fa fa-facebook-square"></i> &middot; 50',
      url: 'http://demos.creative-tim.com/material-dashboard-pro/examples/dashboard.html'
    });

	*/
});
    

demo = {	
	/*
	initPickColor: function(){
        $('.pick-class-label').click(function(){
            var new_class = $(this).attr('new-class');
            var old_class = $('#display-buttons').attr('data-class');
            var display_div = $('#display-buttons');
            if(display_div.length) {
            var display_buttons = display_div.find('.btn');
            display_buttons.removeClass(old_class);
            display_buttons.addClass(new_class);
            display_div.attr('data-class', new_class);
            }
        });
    },
	*/
		
		showSwal: function(type)
		{

	        if(type == 'basic')
	        {
	        	swal({
	                title: "Here's a message!",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success"
	            });

	    	}
	        else if(type == 'title-and-text')
	    	{
	        	swal({
	                title: "Here's a message!",
	                text: "It's pretty, isn't it?",
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-info",
	                allowOutsideClick: false
	            });

	    	}
	        else if(type == 'warning-message-and-confirmation')
	        {
	            swal({
	                    title: 'Are you sure?',
	                    text: "You won't be able to revert this!",
	                    type: 'warning',
	                    showCancelButton: true,
	                    confirmButtonClass: 'btn btn-success',
	                    cancelButtonClass: 'btn btn-danger',
	                    confirmButtonText: 'Yes, delete it!',
	                    buttonsStyling: false
	                }).then(function() {
	                  swal({
	                    title: 'Deleted!',
	                    text: 'Your file has been deleted.',
	                    type: 'success',
	                    confirmButtonClass: "btn btn-success",
	                    buttonsStyling: false
	                    })
	                });
	    	}
	        else if(type == 'warning-message-and-cancel')
	    	{
	            swal({
	                    title: 'Are you sure?',
	                    text: 'You will not be able to recover this imaginary file!',
	                    type: 'warning',
	                    showCancelButton: true,
	                    confirmButtonText: 'Yes, delete it!',
	                    cancelButtonText: 'No, keep it',
	                    confirmButtonClass: "btn btn-success",
	                    cancelButtonClass: "btn btn-danger",
	                    buttonsStyling: false
	                }).then(function() {
	                  swal({
	                    title: 'Deleted!',
	                    text: 'Your imaginary file has been deleted.',
	                    type: 'success',
	                    confirmButtonClass: "btn btn-success",
	                    buttonsStyling: false
	                    })
	                }, function(dismiss) {
	                  // dismiss can be 'overlay', 'cancel', 'close', 'esc', 'timer'
	                  if (dismiss === 'cancel') {
	                    swal({
	                      title: 'Cancelled',
	                      text: 'Your imaginary file is safe :)',
	                      type: 'error',
	                      confirmButtonClass: "btn btn-info",
	                      buttonsStyling: false
	                    })
	                  }
	                })

	    	}
	        else if(type == 'custom-html')
	        {
	        	swal({
	                title: 'HTML example',
	                buttonsStyling: false,
	                confirmButtonClass: "btn btn-success",
	                html:
	                        'You can use <b>bold text</b>, ' +
	                        '<a href="http://github.com">links</a> ' +
	                        'and other HTML tags'
	                });

	    	}
	        else if(type == 'auto-close')
	    	{
	        	swal({ title: "Auto close alert!",
	            	   text: "I will close in 2 seconds.",
	            	   timer: 2000,
	            	   showConfirmButton: false
	                });
	    	}
	        else if(type == 'input-field')
	        {
	            swal({
	                    title: 'Input something',
	                    html: '<div class="form-group">' +
	                              '<input id="input-field" type="text" class="form-control" />' +
	                          '</div>',
	                    showCancelButton: true,
	                    confirmButtonClass: 'btn btn-success',
	                    cancelButtonClass: 'btn btn-danger',
	                    buttonsStyling: false
	                }).then(function(result) {
	                    swal({
	                        type: 'success',
	                        html: 'You entered: <strong>' +
	                                $('#input-field').val() +
	                              '</strong>',
	                        confirmButtonClass: 'btn btn-success',
	                        buttonsStyling: false

	                    })
	                }).catch(swal.noop)
	        }
	        else if(type == 'processando')
	    	{
	        	swal({
	                title: "Processando...",
	                html:  
	                	'<div class="progress">' +
	                	  ' <div class="indeterminate"></div>' + 
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
	        else if(type == 'close')
	        {
	        	swal.close();
	        }
		}, //fim showSwal
		
    checkFullPageBackgroundImage: function(){
        $page = $('.full-page');
        image_src = $page.data('image');

        if(image_src !== undefined){
            image_container = '<div class="full-page-background" style="background-image: url(' + image_src + ') "/>'
            $page.append(image_container);
        }
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
            format: 'MM/DD/YYYY',
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
//          format: 'H:mm',    // use this format if you want the 24hours timepicker
            format: 'h:mm A',    //use this format if you want the 12hours timpiecker with AM/PM toggle
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
    },

    initMaterialWizard: function(){
        // Code for the Validator
        var $validator = $('.wizard-card form').validate({
    		  rules: {
    		    firstname: {
    		      required: true,
    		      minlength: 3
    		    },
    		    lastname: {
    		      required: true,
    		      minlength: 3
    		    },
    		    email: {
    		      required: true,
    		      minlength: 3,
    		    }
            },

            errorPlacement: function(error, element) {
                $(element).parent('div').addClass('has-error');
             }
    	});

        // Wizard Initialization
      	$('.wizard-card').bootstrapWizard({
            'tabClass': 'nav nav-pills',
            'nextSelector': '.btn-next',
            'previousSelector': '.btn-previous',

            onNext: function(tab, navigation, index) {
            	var $valid = $('.wizard-card form').valid();
            	if(!$valid) {
            		$validator.focusInvalid();
            		return false;
            	}
            },

            onInit : function(tab, navigation, index){

              //check number of tabs and fill the entire row
              var $total = navigation.find('li').length;
              $width = 100/$total;
              var $wizard = navigation.closest('.wizard-card');

              $display_width = $(document).width();

              if($display_width < 600 && $total > 3){
                  $width = 50;
              }

               navigation.find('li').css('width',$width + '%');
               $first_li = navigation.find('li:first-child a').html();
               $moving_div = $('<div class="moving-tab">' + $first_li + '</div>');
               $('.wizard-card .wizard-navigation').append($moving_div);
               refreshAnimation($wizard, index);
               $('.moving-tab').css('transition','transform 0s');
           },

            onTabClick : function(tab, navigation, index){
                var $valid = $('.wizard-card form').valid();

                if(!$valid){
                    return false;
                } else{
                    return true;
                }
            },

            onTabShow: function(tab, navigation, index) {
                var $total = navigation.find('li').length;
                var $current = index+1;

                var $wizard = navigation.closest('.wizard-card');

                // If it's the last tab then hide the last button and show the finish instead
                if($current >= $total) {
                    $($wizard).find('.btn-next').hide();
                    $($wizard).find('.btn-finish').show();
                } else {
                    $($wizard).find('.btn-next').show();
                    $($wizard).find('.btn-finish').hide();
                }

                button_text = navigation.find('li:nth-child(' + $current + ') a').html();

                setTimeout(function(){
                    $('.moving-tab').text(button_text);
                }, 150);

                var checkbox = $('.footer-checkbox');

                if( !index == 0 ){
                    $(checkbox).css({
                        'opacity':'0',
                        'visibility':'hidden',
                        'position':'absolute'
                    });
                } else {
                    $(checkbox).css({
                        'opacity':'1',
                        'visibility':'visible'
                    });
                }

                refreshAnimation($wizard, index);
            }
      	});


        // Prepare the preview for profile picture
        $("#wizard-picture").change(function(){
            readURL(this);
        });

        $('[data-toggle="wizard-radio"]').click(function(){
            wizard = $(this).closest('.wizard-card');
            wizard.find('[data-toggle="wizard-radio"]').removeClass('active');
            $(this).addClass('active');
            $(wizard).find('[type="radio"]').removeAttr('checked');
            $(this).find('[type="radio"]').attr('checked','true');
        });

        $('[data-toggle="wizard-checkbox"]').click(function(){
            if( $(this).hasClass('active')){
                $(this).removeClass('active');
                $(this).find('[type="checkbox"]').removeAttr('checked');
            } else {
                $(this).addClass('active');
                $(this).find('[type="checkbox"]').attr('checked','true');
            }
        });

        $('.set-full-height').css('height', 'auto');

         //Function to show image before upload

        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#wizardPicturePreview').attr('src', e.target.result).fadeIn('slow');
                }
                reader.readAsDataURL(input.files[0]);
            }
        }

        $(window).resize(function(){
            $('.wizard-card').each(function(){
                $wizard = $(this);
                index = $wizard.bootstrapWizard('currentIndex');
                refreshAnimation($wizard, index);

                $('.moving-tab').css({
                    'transition': 'transform 0s'
                });
            });
        });

        function refreshAnimation($wizard, index){
            total_steps = $wizard.find('li').length;
            move_distance = $wizard.width() / total_steps;
            step_width = move_distance;
            move_distance *= index;

            $current = index + 1;

            if($current == 1){
                move_distance -= 8;
            } else if($current == total_steps){
                move_distance += 8;
            }

            $wizard.find('.moving-tab').css('width', step_width);
            $('.moving-tab').css({
                'transform':'translate3d(' + move_distance + 'px, 0, 0)',
                'transition': 'all 0.5s cubic-bezier(0.29, 1.42, 0.79, 1)'

            });
        }
         
    }

}
