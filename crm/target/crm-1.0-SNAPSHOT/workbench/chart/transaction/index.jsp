<%--
  Created by IntelliJ IDEA.
  User: 86157
  Date: 2022/7/22
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath()+"/";
        // 根据交易表不同阶段进行统计  最后形成一个漏斗图

%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script type="text/javascript" src="ECharts/echarts.min.js"></script>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script>

        $(function () {

            //页面加载完毕后，绘制统计图表
            getCharts();

        })

        function getCharts() {

            $.ajax({

                url : "workbench/transaction/getCharts.do",
                type : "get",
                dataType : "json",
                success : function (data) {

                    /*

                        data
                            {"total":100,"dataList":[{value: 60, name: '01资质审查'},{value: 114, name: '02需求分析'},{...}]}

                     */

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 我们要画的图
                    var option = {
                        title: {
                            text: '交易漏斗图',
                            subtext: '统计交易阶段数量的漏斗图'
                        },

                        series: [
                            {
                                name:'交易漏斗图',
                                type:'funnel',
                                left: '10%',
                                top: 60,
                                //x2: 80,
                                bottom: 60,
                                width: '80%',
                                // height: {totalHeight} - y - y2,
                                min: 0,
                                max: data.total,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,
                                label: {
                                    show: true,
                                    position: 'inside'
                                },
                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },
                                data: data.dataList
                                /*
                                    [
                                        {value: 60, name: '01资质审查'},
                                        {value: 114, name: '02需求分析'},
                                        {value: 220, name: '03价值建议'},
                                        {value: 80, name: '06谈判复审'},
                                        {value: 100, name: '07成交'}
                                    ]
                                */
                            }
                        ]
                    };

                    myChart.setOption(option);


                }

            })





        }

    </script>
    <%--<script type="text/javascript">
        $(function () {
            // 页面加载完毕绘制统计图表
            getCharts();
        })
        function getCharts() {
            var myChart = echarts.init(document.getElementById('main'));

            // option  就是要画的图
            var option = {
                title: {
                    text: 'ECharts 入门示例'
                },
                tooltip: {},
                legend: {
                    data: ['销量']
                },
                xAxis: {
                    data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
                },
                yAxis: {},
                series: [
                    {
                        name: '销量',
                        type: 'bar',
                        data: [5, 20, 36, 10, 10, 20]
                    }
                ]
            };

            myChart.setOption(option);
        }


    </script>--%>

</head>
<body>

        <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
        <div id="main" style="width: 600px;height:400px;"></div>

</body>
</html>
