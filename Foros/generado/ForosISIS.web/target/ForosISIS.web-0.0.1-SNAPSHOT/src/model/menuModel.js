/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

define(['model/buttonModel'], function(){
    App.Model.MenuModel = Backbone.Model.extend({
	defaults:{
	    name: '',
	    displayName: '',
	    show: true,
	    buttons: []
	},
	initialize: function(){
	    this.set('buttons',new Array());
	}
    });
    return App.Model.MenuModel;
});