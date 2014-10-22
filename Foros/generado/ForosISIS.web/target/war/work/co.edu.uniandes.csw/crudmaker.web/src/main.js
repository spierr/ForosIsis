//To work on IE
if (!window.console)
    window.console = {};
if (!window.console.log)
    window.console.log = function() {
    };

require.config({
    baseUrl: "src"
});

define(['App', 'utils'], function(App, utils) {
//Call the default function to start the modules
    $(document).ready(function() {
        App.Utils.loadComponents();
    });
}
);