<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="business.stock.DataRender" %>
<div class="col-sm-10 col-sm-offset-1">
				<div class="panel panel-primary">
								<div class="panel-heading text-center">
												${param.name} Price History
												<button type="button" class="close pull-right" target="overlay" aria-label="Close">
																<span aria-hidden="true">&times;</span>
												</button>
								</div>
								<div class="panel-body">
												<div class="col-sm-8 col-sm-offset-2 marginalise">
																<div class="form-group">
																				<label for="visualize" class="col-xs-3 control-label">View By:</label>
																				<div class="col-xs-9">
																								<select name="visualize" id="visualize" class="form-control">
																												<option value="1">Price vs Date</option>
																												<option value="2">Demand vs Price</option>
																								</select>
																				</div>
																</div>
												</div>
												<div id="plot" class="col-sm-10 col-sm-offset-1 marginalise"></div>
												<hr class="col-sm-12">
												<div class="col-sm-10 col-sm-offset-1 marginalise">
																<c:url var="url" value="Action"></c:url>
																<form class="form-horizontal" action="${url}" method="POST" close="overlay" post-target="view_item_config">
																				<div class="form-group">
																								<label for="price" class="col-xs-2 control-label col-xs-offset-2">New Price:</label>
																								<div class="col-xs-6">
																												<input type="number" step="0.01" class="form-control" placeholder="add new price" min="0.01" name="price" required/>
																								</div>
																				</div>
																				<input type="hidden" name="code" value="${param.code}" />
																				<input type="hidden" name="param" value="change_price" />
																				<div class="form-group">
																								<div class="col-xs-8 col-xs-offset-2">
																												<button class="btn btn-primary btn-group-justified" type="submit">Change Price</button>
																								</div>
																				</div>
																</form>
												</div>
												<hr class="col-sm-12">
												<div class="col-xs-10 col-xs-offset-1">
																<table class="table table-striped">
																				<caption class="text-center">Raw Data</caption>
																				<thead>
																								<tr>
																												<th>Price</th>
																												<th>Effective date</th>
																												<th>Buy count</th>
																												<th>Added By</th>
																								</tr>
																				</thead>
																				<tbody>
																								<c:forEach var="data" items="${list}">
																												<tr>
																																<td>${data.floatData}</td>
																																<td>${data.formattedDate}</td>
																																<td>${data.longData}</td>
																																<td>${data.string}</td>
																												</tr>
																								</c:forEach>
																				</tbody>
																</table>
												</div>				
								</div>
				</div>
</div>
</div>
<script type="text/javascript">
				var dateData = [];
				var demandData = [];
				var tooltipDate = [];
				var tooltipDemand = [];
				<c:forEach var="data" items="${list}" >
				dateData.push([${data.date}, ${data.floatData}]);
				demandData.push([${data.floatData}, ${data.doubleData}]);
				tooltipDate.push(${data.doubleData});
				tooltipDemand.push('${data.formattedDate}');
				</c:forEach>
				dateRenderer(dateData, tooltipDate, '%b %#d');
				$('#visualize').bind('change', function () {
								$('#plot').empty();
								switch (parseInt($(this).val())) {
												case 1:
																dateRenderer(dateData, tooltipDate, '%b %#d');
																break;
												case 2:
																demandRenderer(demandData, tooltipDemand);
																break;
								}
				});
				function dateRenderer(data, tooltip, format) {
								$.jqplot('plot', [data],
																{
																				title: 'Price Variation',
																				axes: {
																								xaxis: {
																												renderer: $.jqplot.DateAxisRenderer,
																												tickOptions: {
																																formatString: format
																												}
																								}
																				},
																				highlighter: {
																								show: true,
																								sizeAdjust: 7.5,
																								useAxesFormatters: true,
																								tooltipContentEditor: function (str, sereisIndex, pointIndex) {
																												return 'Count: ' + tooltip[pointIndex];
																								}
																				},
																				series: [{neighborThreshold: -1}],
																				cursor: {
																								show: true,
																								zoom: true
																				}
																});
				}
				function demandRenderer(data, tooltip) {
								$.jqplot('plot', [data],
																{
																				title: 'Demand Variation',
																				highlighter: {
																								show: true,
																								sizeAdjust: 7.5,
																								useAxesFormatters: false,
																								tooltipContentEditor: function (str, sereisIndex, pointIndex) {
																												return '' + tooltip[pointIndex];
																								}
																				},
																				series: [{color: '#5FAB78'}],
																				cursor: {
																								show: true,
																								zoom: true
																				}
																});
				}
</script>