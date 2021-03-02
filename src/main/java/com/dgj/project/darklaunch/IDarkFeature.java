package com.dgj.project.darklaunch;

/**
 * @version: v1.0
 * @date: 2021/3/2
 * @author: dgj
 */
public interface IDarkFeature {
    boolean enable();

    boolean dark(long darkTarget);

    boolean dark(String darkTarget);
}
