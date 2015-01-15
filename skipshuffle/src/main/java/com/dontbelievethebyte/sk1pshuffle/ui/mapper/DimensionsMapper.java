/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.mapper;

public final class DimensionsMapper {

    public final static class Drawer {
        public final static class Landscape {
            public final static double width = .4;
        }
        public final static class Portrait {
            public final static double width = .55;
        }
    }

    public final static class List {
        public final static int dividerHeight = 1;
    }

    public final static class Player {

        //Bottom center buttons
        public final static class Center {
            public final static class Landscape {
                public final static double width = .7;
                public final static double textSize = .035;

            }
            public final static class Portrait {
                public final static double width = .8;
                public final static double textSize = .05;

            }
        }
    }
}
