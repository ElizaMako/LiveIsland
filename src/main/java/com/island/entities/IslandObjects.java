package main.java.com.island.entities;


    public enum IslandObjects {
        PLANT("\uD83C\uDF31"),
        WOLF ("\uD83D\uDC3A");

        private String oneUnicode;


        IslandObjects(String oneUnicode) {
            this.oneUnicode = oneUnicode;
        }

        public String getOneUnicode(){
            return oneUnicode;
        }
    }

