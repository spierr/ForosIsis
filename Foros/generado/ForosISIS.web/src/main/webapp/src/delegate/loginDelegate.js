/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
define([], function() {
    App.Delegate.LoginDelegate = {
        //Función que obtiene el "user Stack" del servicio rest.
        getLoggedUserStack: function(sdata, callback, callbackError) {
            $.ajax({
                url: '/forosisis.web/webresources/auth/session/userstack',
                method: 'GET',
                dataType: "html",
                contentType: "application/json"
            }).done(function(data) {
                callback(data);
            }).fail(function(error) {
                callbackError(error);
            });
        }
 
    };
 
});

