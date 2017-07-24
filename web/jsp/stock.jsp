<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <jsp:include page="jsp_include/angular-lib.jsp"/>
    <jsp:include page="jsp_include/angular-app.jsp"/>
</head>
<body ng-app="BlankApp"
      ng-cloak
      layout="row">

<div layout="column"
     flex>
    <md-toolbar md-whiteframe="4"
                ng-controller="ToolbarCtrl">
        <div class="md-toolbar-tools">
            <md-button class="md-icon-button"
                       aria-label="Menu"
                       ng-click="toggleLeftSideNav()"
                       hide-gt-md>
                <md-icon md-svg-icon="menu"></md-icon>
            </md-button>

            <h2 flex>Trading helper</h2>

            <md-button class="md-icon-button"
                       aria-label="More">
                <md-icon md-svg-icon="dots-vertical"></md-icon>
            </md-button>
        </div>
    </md-toolbar>

    <md-progress-linear md-mode="query"
                        ng-controller="LoadingController"
                        ng-show="loading"></md-progress-linear>

    <section layout="row"
             flex>
        <md-sidenav class="md-sidenav-left"
                    md-component-id="left"
                    md-is-locked-open="$mdMedia('gt-md')"
                    style="background: transparent">
            <md-card md-whiteframe="2"
                     ng-controller="MenuLeftController">
                <md-card-title>
                    <md-card-title-text>
                        <span class="md-headline">Navigation</span>
                    </md-card-title-text>
                </md-card-title>

                <md-card-content>
                    <div ng-repeat="item in menuItems"
                         flex
                         layout="column">
                        <md-button style="text-align: left; margin: 0"
                                   href="{{item.link}}"
                                   ng-click="loading = true; toggleLeftSideNav()">
                            {{item.title}}
                        </md-button>
                    </div>
                </md-card-content>
            </md-card>
        </md-sidenav>

        <md-content ng-controller="ViewCtrl"
                    flex>
            <ng-view></ng-view>
        </md-content>
    </section>
</div>

<script type="text/javascript">
    app.controller('ToolbarCtrl', function ($scope, $rootScope, $timeout, $mdSidenav) {
        $rootScope.toggleLeftSideNav = buildToggler('left');

        function buildToggler(componentId) {
            return function () {
                $mdSidenav(componentId).toggle();
            };
        }
    });

    app.controller('ViewCtrl', function ($scope, $rootScope) {
        $scope.$on('$routeChangeStart', function (next, current) {
            $rootScope.loading = true;
        });

        $scope.$on('$viewContentLoaded', function () {
            $rootScope.loading = false;
        });
    });

    app.controller('LoadingController', function ($scope, $rootScope) {
    });

    app.controller('MenuLeftController', function ($scope, $rootScope, $http) {
        $http.get("/resources/data/menu.json")
            .success(function (data) {
                $scope.menuItems = data;
            })
            .finally(function () {
            });
    });

    app.controller('StockController', function ($scope) {
        <c:if test="${ticker}">
        $scope.ticker = ${ticker};
        </c:if>

        <c:if test="${ticker_gen}">
        $scope.tickerGen = ${ticker_gen};
        </c:if>
    });

    app.controller('TXListController', function ($scope) {
        $scope.selected = [];

        $scope.query = {
            order: 'idToLower',
            limit: 1,
            page: 2
        };

        $scope.onSelect = function (item) {
        };

        $scope.onDeselect = function (item) {
        };

        $scope.getTransactions = function () {
            $scope.transactions = ${transactions};
        };

        $scope.getTransactions();
    })
</script>

</body>
</html>