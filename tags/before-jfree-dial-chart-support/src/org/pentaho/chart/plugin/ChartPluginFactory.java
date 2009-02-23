/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * @created Feb 25, 2008 
 * @author wseyler
 */


package org.pentaho.chart.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.base.util.ObjectUtilities;

/**
 * @author wseyler
 *
 * This is a factory class that returns the desired implementation of IChartPlugin based either
 * on the chart.properties document or on the requested class.
 */
public class ChartPluginFactory  {
  private static final Log logger = LogFactory.getLog(ChartPluginFactory.class);

  private ChartPluginFactory() {
  }

  /**
   * Creates an instance of the IChartPlugin as defined in the chart.properties document if one
   * doesn't already exist.  If one exists it will return that one.
   * 
   * @return an implementation of the IChartPlugin
   */
  public static synchronized IChartPlugin getInstance() throws ChartProcessingException
  {
    return getInstance(null);
  }

  /**
   * Returns an instance of the IChartPlugin from the className.  Logs errors and
   * returns null if the class couldn't be created.
   * 
   * @param className
   * @return
   */
  public static IChartPlugin getInstance(String className) throws ChartProcessingException
  {
    if (className == null)
    {
      final Configuration config = ChartBoot.getInstance().loadConfiguration();
      className = config.getConfigProperty("org.pentaho.chart.plugin.Default-IChartPlugin"); //$NON-NLS-1$
      if (className == null)
      {
        throw new ChartProcessingException("No ChartPlugin defined as default");
      }
    }
    try {
      final IChartPlugin o = (IChartPlugin)
          ObjectUtilities.loadAndInstantiate(className, ChartPluginFactory.class, IChartPlugin.class);
      if (o == null)
      {
        throw new ChartProcessingException("Unable to instantiate the requested chart-plugin: " + className);
      }
      return o;
    } catch (Exception e) {
      ChartPluginFactory.logger.error(e);
      throw new ChartProcessingException("Error while instantiating the Chart-Plugin " + className, e);
    }
  }
//
//  /**
//   * Creates an new instance of IOutput as defined in the chart.properties document.
//   *
//   * @return an implementation of IOutput
//   */
//  public static IOutput getChartOutput() throws ChartProcessingException
//  {
//    final Configuration config = ChartBoot.getInstance().loadConfiguration();
//    final String className = config.getConfigProperty("org.pentaho.chart.plugin.Default-IOutput"); //$NON-NLS-1$
//    return ChartPluginFactory.getChartOutput(className);
//  }
//
//  /**
//   * Creates an new instance of IOutput as defined by the classname parameter.  Logs
//   * any errors and returns null of class couldn't be created.
//   *
//   * @param className
//   * @return an implementation of IOutput
//   */
//  public static IOutput getChartOutput(final String className) throws ChartProcessingException
//  {
//    try {
//      final IOutput o = (IOutput)
//          ObjectUtilities.loadAndInstantiate(className, ChartPluginFactory.class, IOutput.class);
//      if (o == null)
//      {
//        throw new ChartProcessingException("Unable to instantiate the requested chart-output: " + className);
//      }
//      return o;
//    } catch (Exception e) {
//      ChartPluginFactory.logger.error(e);
//      throw new ChartProcessingException("Unable to instantiate the requested chart-plugin: " + className, e);
//    }
//  }

}