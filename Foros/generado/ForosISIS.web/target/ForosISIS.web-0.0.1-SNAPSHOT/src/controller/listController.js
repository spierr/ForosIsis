/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

define(['model/listModel'], function () {
	App.Controller.ListController = Backbone.View.extend({
		initialize: function () {
			App.Utils.loadTemplate('list');
			this.template = _.template($('#list-template').html());
			Backbone.on(this.model.get('componentId') + "-" + this.model.get('name') + "-select", this.updateSelectedItems, this);
		},
		updateSelectedItems: function (params) {
			var idField = this.model.get('idField');
			var item;
			if (params.isChecked) {
				item = _.find(this.model.get('data'), function (i) {
					return i[idField] === params.id;
				});
				if (item) {
					this.model.get('selectedItems')[item[idField]] = item;
				}
			} else {
				item = _.find(this.model.get('selectedItems'), function (i) {
					return i[idField] === params.id;
				});
				if (item) {
					delete this.model.get('selectedItems')[item[idField]];
				}
			}
		},
		getSelectedItems: function () {
			return this.model.get('selectedItems');
		},
		render: function () {
			if (this.model.get("show")) {
				this.$el.html(this.template(this.model.toJSON()));
			}
			return this;
		},
		setTemplate: function (templateName) {
			this.template = _.template($('#' + templateName).html());
		},
		setData: function (args) {
			if (args.data) {
				this.model.set('data', args.data.models);
			}
			if (args.page) {
				this.model.set('page', args.page);
			}
			if (args.pages) {
				this.model.set('pages', args.pages);
			}
			if (args.pagination !== undefined) {
				this.model.set('pagination', args.pagination);
			}
		},
		addAction: function (params, callBack, context) {
			var i = this.model.get('actions').length;
			var id = 1;
			if (i != 0) {
				id = this.model.get('actions')[i - 1].get('id') + 1;
			}
			var button = new App.Model.ButtonModel({
				id: id,
				name: params.name,
				displayName: params.displayName,
				icon: params.icon,
				show: params.show
			});

			this.model.get('actions')[i] = button;

			Backbone.on(this.model.get('componentId') + '-' + this.model.get('name') + '-' + params.name, function (args) {
				callBack.call(context, args)
			});
		},
		removeAction: function (actionName) {
			for (var i = 0; i < this.model.get('actions').length; i++) {
				if (actionName == this.model.get('actions')[i].get('name')) {
					Backbone.off(this.model.get('componentId') + '-' + this.model.get('name') + '-' + actionName);
					this.model.get('actions').splice(i, 1);
				}
			}
		},
		showAction: function (name) {
			for (i in this.model.get('actions')) {
				if (name == this.model.get('actions')[i].get('name')) {
					this.model.get('actions')[i].set('show', true);
				}
			}
		},
		hideAction: function (name) {
			for (i in this.model.get('actions')) {
				if (name == this.model.get('actions')[i].get('name')) {
					this.model.get('actions')[i].set('show', false);
				}
			}
		},
		addColumn: function (columnName, displayName, formula) {
			this.model.get('columns').push(
					{name: columnName,
						displayName: displayName,
						formula: formula
					});
		},
		removeColumn: function (columnName) {
			for (var i = 0; i < this.model.get('columns').length; i++) {
				if (columnName === this.model.get('columns')[i].name) {
					this.model.get('columns').splice(i, 1);
				}
			}
		},
		enableMultipleSelection: function (enabled) {
			if (typeof (enabled) == "boolean") {
				this.model.set('select', enabled);
			}
		},
		clearSelectedRecords: function () {
			this.model.set('selectedItems', []);
		},
		display: function (flag) {
			if (typeof (flag) === "boolean") {
				if (flag) {
					this.model.set("show",true);
					this.$el.show();
				} else {
					this.model.set("show",false);
					this.$el.hide();
				}
			} else {
				console.log("parameter value must be boolean type");
			}
		}
	});

	return App.Controller.ListController;
});