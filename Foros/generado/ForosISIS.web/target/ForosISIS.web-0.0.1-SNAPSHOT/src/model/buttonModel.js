/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

define([], function(){
    App.Model.ButtonModel = Backbone.Model.extend({
	defaults:{
	    displayName: '',
	    name: '',
	    icon: '',
	    show: false
	}
    });
    return App.Model.ButtonModel;
});