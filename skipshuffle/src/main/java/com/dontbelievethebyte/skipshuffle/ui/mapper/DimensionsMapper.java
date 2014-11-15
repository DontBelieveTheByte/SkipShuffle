package com.dontbelievethebyte.skipshuffle.ui.mapper;

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

        public final static class Padding {
            public final static class Landscape {
                public final static double top = .25;
                public final static double right = .15;
                public final static double left = .15;
            }
            public final static class Portrait {
                public final static double top = .1;
                public final static double right = .05;
                public final static double left = .05;
            }
        }

        //Top buttons
        public final static class Top {
            public final static class Landscape {
                public final static double buttonSize = .2;
            }
            public final static class Portrait {
                public final static double buttonSize = .4;
            }
        }

        //Bottom center buttons
        public final static class Center {
            public final static class Landscape {
                public final static double width = .7;
                public final static double textSize = .06;
                public final static double textHeight = 2;

            }
            public final static class Portrait {
                public final static double width = .8;
                public final static double textSize = .05;
                public final static double textHeight = 4;

            }
        }

        //Bottom buttons
        public final static class Bottom {
            public final static class Landscape {
                public final static double buttonSideSize = Top.Landscape.buttonSize * .9;
                public final static double buttonCenterSize = Top.Landscape.buttonSize * .8;
            }
            public final static class Portrait {
                public final static double buttonSideSize = Top.Portrait.buttonSize * .65;
                public final static double buttonCenterSize = Top.Portrait.buttonSize * .4;
            }
        }

        public final static class SongLabel {
            public final static class Landscape {
                public final static int textSize = 34;
            }
            public final static class Portrait {
                public final static int textSize = 44;            }
        }
    }
}
