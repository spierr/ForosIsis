/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

define([], function() {
	App.Model.ListModel = Backbone.Model.extend({
		defaults : {
			componentId : '',
			name: '',
			show: true,
			select: false,
			pagination: true,
			page: '',
			pages: '',
			columns: [], //debe tener name, displayName, formula
			data: [],
			actions : [], //corresponde a buttonModel
			idField: 'id',
			selectedItems: {}
		},
		initialize : function() {
			this.set('actions', []);
			this.set('columns', []);
			this.set('data', []);
			this.set('selectedItems', {});
		}
	});
	return App.Model.ListModel;
});