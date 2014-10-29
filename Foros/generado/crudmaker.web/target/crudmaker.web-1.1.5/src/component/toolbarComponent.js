/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


define(['controller/toolbarController'], function () {
	/*App.Enum.toolbarTemplate = { 
	 list: 'toolbar-list-template',
	 dropdown: 'toolbar-dropdown-template'
	 };*/
	App.Component.ToolbarComponent = Backbone.View.extend({
		initialize: function (options) {
			if (options.componentId) {
				this.componentId = options.componentId;
			}
			var model = new App.Model.ToolbarModel({componentId: this.componentId, name: options.name});
			this.toolbarController = new App.Controller.ToolbarController({
				model: model
			});
		},
		addButton: function (params, callBack, context) {
			this.toolbarController.addButton(params, callBack, context);
		},
		removeButton: function (name) {
			this.toolbarController.removeButton(name);
		},
		addMenu: function (params) {
			this.toolbarController.addMenu(params);
		},
		removeMenu: function (name) {
			this.toolbarController.removeMenu(name);
		},
		showButton: function (name) {
			this.toolbarController.showButton(name);
		},
		hideButton: function (name) {
			this.toolbarController.hideButton(name);
		},
		selectTemplate: function (templateName) {
			this.toolbarController.setTemplate(templateName);
		},
		render: function () {
			this.toolbarController.render();
		},
		display: function (flag) {
			this.toolbarController.display(flag);
		}
	});

	return App.Component.ToolbarComponent;
});