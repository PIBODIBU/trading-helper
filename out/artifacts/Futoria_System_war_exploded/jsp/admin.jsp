<!DOCTYPE HTML>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <jsp:include page="jsp_include/angular-lib.jsp"/>
    <jsp:include page="jsp_include/angular-app.jsp"/>
</head>

<style>
    .image-cropper {
        width: 36px;
        height: 36px;
        position: relative;
        overflow: hidden;
        border-radius: 50%;
    }

    .avatar {
        width: 100%;
        height: auto;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        cursor: pointer;
    }

    body {
        max-width: 100%;
        min-width: 100%;
    }
</style>

<body ng-app="BaseApp"
      ng-cloak>

<div layout="column"
     flex
     layout-fill
     ng-cloak
     ng-controller="AppCtrl"
     md-colors="background">
    <%--Toolbars--%>
    <md-toolbar md-whiteframe="8">
        <div class="md-toolbar-tools">
            <div flex layout="row" layout-align="start center">
                <span class="md-headline" style="color: #2196F3">Futoria</span>
                <span>&nbsp;</span>
                <span class="md-headline"
                      style="color: #757575; font-family: RobotoLight; font-weight: bolder">Tests</span>

                <md-autocomplete
                        style="margin-left:16px;"
                        flex="40"
                        hide-xs
                        md-selected-item="ctrl.selectedItem"
                        md-search-text-change="ctrl.searchTextChange(ctrl.searchText)"
                        md-search-text="ctrl.searchText"
                        md-selected-item-change="ctrl.selectedItemChange(item)"
                        md-items="item in ctrl.querySearch(ctrl.searchText)"
                        md-item-text="item.display"
                        md-min-length="0"
                        placeholder="Введіть пошуковий запит...">
                    <md-item-template>
                        <span md-highlight-text="ctrl.searchText" md-highlight-flags="^i">{{item.display}}</span>
                    </md-item-template>
                    <md-not-found>
                        No states matching "{{ctrl.searchText}}" were found.
                        <a ng-click="ctrl.newState(ctrl.searchText)">Create a new one!</a>
                    </md-not-found>
                </md-autocomplete>
            </div>

            <%--<h2 flex md-truncate> Welcome, ${pageContext.request.userPrincipal.name}</h2>--%>
            <md-button class="md-icon-button"
                       aria-label="Settings">
                <md-icon md-svg-icon="view-grid"
                         style="fill: #757575;!important;"></md-icon>
            </md-button>
        </div>
    </md-toolbar>

    <%--Main content--%>
    <div layout="row"
         layout-fill
         flex>
        <%--Side navigation--%>
        <div layout="column"
             style="margin-right: 8px">
            <jsp:include page="template/sidenav.jsp"/>
        </div>

        <md-content layout="column"
                    flex>
            <%--Content--%>
            <div flex
                 layout="row"
                 layout-wrap
                 layout-align="center">
                <md-list flex="50">
                    <md-subheader class="md-no-sticky">Roles</md-subheader>
                    <md-list-item class="md-2-line" ng-repeat="role in myRoles | orderBy:'id'" ng-click="null">
                        <div class="md-list-item-text" layout="column">
                            <h4>{{ role.name }}</h4>
                            <p>{{ role.description }}</p>
                        </div>
                    </md-list-item>
                </md-list>

                <md-list flex="50">
                    <md-subheader class="md-no-sticky">Permissions</md-subheader>
                    <md-list-item class="md-2-line" ng-repeat="perm in myPermissions" ng-click="null">
                        <div class="md-list-item-text" layout="column">
                            <h4>{{ perm.name }}</h4>
                            <p>{{ perm.description }}</p>
                        </div>
                    </md-list-item>
                </md-list>
            </div>
        </md-content>
    </div>
</div>

<script type="text/javascript">
    app.controller('AppCtrl', function ($scope) {
        $scope.myRoles = ${roles};
        $scope.myPermissions = ${my_permissions};
        $scope.borodaPermissions = [];
        $scope.user = ${user};

        $scope.avatarClick = function () {
        }
    });

    app.controller('SearchBarController', function ($scope) {
    });

    app.controller('LeftCtrl', function ($scope, $timeout, $mdSidenav, $log) {
        $scope.close = function () {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav('left').close()
                .then(function () {
                    $log.debug("close LEFT is done");
                });

        };
    })
</script>
</body>
</html>