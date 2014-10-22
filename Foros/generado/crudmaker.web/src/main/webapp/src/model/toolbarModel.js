define([], function() {
	App.Model.ToolbarModel = Backbone.Model.extend({
		defaults : {
			componentId : '',
			name : '-',
			title : '',
			icon : 'globe',
			showTitle : true,
			createName : 'Create',
			showCreate : true,
			saveName : 'Save',
			showSave : false,
			cancelName : 'Cancel',
			showCancel : false,
			refreshName : 'Refresh',
			showRefresh : true,
			searchName : 'Search',
			searchIcon : 'glyphicon-search',
			showSearch : true,
			printName : 'Print',
			printIcon : 'glyphicon-print',
			showPrint : true,
			showAddButton : false,
			addName : 'Add',
			buttons : []
		},
		initialize : function() {
			this.set('buttons', new Array());
		}
	});
	return App.Model.ToolbarModel;
});