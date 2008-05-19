package org.pentaho.experimental.chart.plugin.jfreechart.chart.area;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartAreaStyle;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Factory class that will return a complete area chart ready for output/rendering,
 * </p>
 * Author: Ravi Hasija
 * Date: May 16, 2008
 * Time: 12:44:33 PM
 */
public class JFreeAreaChartGeneratorFactory {

  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    boolean stacked = false;
    boolean xy = false;

    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    //TODO: ask if we need to break out of this loop instead of going through the entire series.
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.AREA_STYLE);
      stacked |= value.equals(ChartAreaStyle.STACKED);
      xy |= value.equals(ChartAreaStyle.XY);
    }

    JFreeAreaChartGenerator areaChartGenerator = null;

    if (stacked) {
    } else if (xy) {
    } else {
      areaChartGenerator = new JFreeDefaultAreaChartGenerator();
    }
    
    return areaChartGenerator.createChart(chartDocContext, data);
  }
}