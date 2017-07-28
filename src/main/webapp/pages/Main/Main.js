Application.$controller("MainPageController", ["$scope", function($scope) {
    "use strict";

    /* perform any action on widgets/variables within this block */
    $scope.onPageReady = function() {
        /*
         * variables can be accessed through '$scope.Variables' property here
         * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
         * $scope.Variables.loggedInUser.getData()
         *
         * widgets can be accessed through '$scope.Widgets' property here
         * e.g. to get value of text widget named 'username' use following script
         * '$scope.Widgets.username.datavalue'
         */
    };


    $scope.container5Tap = function($event, $isolateScope, item, currentItemWidgets) {



        if ("Tree Enumeration" == item.dataValue) {
            //navigate to Tree enumeration page
            $scope.Variables.goToPage_TreeEnumeration.invoke();

            //Variables.goToPage_TreeEnumeration.invoke()


        }
    };

}]);