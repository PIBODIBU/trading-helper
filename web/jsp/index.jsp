<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <jsp:include page="jsp_include/head.jsp"/>
</head>

<style>
    .hiddenOverflow {
        overflow: hidden;
    }
</style>

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

    <md-content layout="row"
                flex>
        <md-sidenav class="md-sidenav-left"
                    md-component-id="left"
                    md-is-locked-open="$mdMedia('gt-md')"
                    ng-style="{hiddenOverflow: $mdMedia('gt-md')}"
                    style="background: transparent; height: 100%">
            <div ng-controller="MenuLeftController">
                <md-card md-whiteframe="2">
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

                <md-card md-whiteframe="2">
                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-headline">Stocks</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-card-content>
                        <div ng-repeat="item in stocks"
                             flex
                             layout="column">
                            <md-button style="text-align: left; margin: 0"
                                       href="{{item.link}}"
                                       ng-click="loading = true; toggleLeftSideNav()">
                                {{item.name}}
                            </md-button>
                        </div>
                    </md-card-content>
                </md-card>

                <md-card md-whiteframe="2">
                    <md-card-title>
                        <md-card-title-text>
                            <span class="md-headline">Currency pairs</span>
                        </md-card-title-text>
                    </md-card-title>

                    <md-card-content>
                        <div ng-repeat="item in currPairs"
                             flex
                             layout="column">
                            <md-button style="text-align: left; margin: 0">
                                {{item.name}} - {{item.ticker.rate}} {{item.counter}} ({{item.stock}})
                            </md-button>
                        </div>
                    </md-card-content>
                </md-card>
            </div>
        </md-sidenav>

        <div ng-controller="ViewCtrl"
             flex>
            <ng-view></ng-view>
        </div>
    </md-content>
</div>

<jsp:include page="/jsp/jsp_include/controllers.jsp"/>

</body>
</html>