package top.starp.util;
 public
class NumberInfo {
//        private String chineseNumber;
//        private int arabicNumber;
//        private int index;
//
//        public NumberInfo(String chineseNumber, int arabicNumber, int index) {
//            this.chineseNumber = chineseNumber;
//            this.arabicNumber = arabicNumber;
//            this.index = index;
//        }
//
//        public String getChineseNumber() {
//            return chineseNumber;
//        }
//
//        public int getArabicNumber() {
//            return arabicNumber;
//        }
//
//        public int getIndex() {
//            return index;
//        }


     private String chineseNumber;
     private int arabicNumber;
     private int startIndex;
     private int endIndex;

     public NumberInfo(String chineseNumber, int arabicNumber, int startIndex, int endIndex) {
         this.chineseNumber = chineseNumber;
         this.arabicNumber = arabicNumber;
         this.startIndex = startIndex;
         this.endIndex = endIndex;
     }

     public String getChineseNumber() {
         return chineseNumber;
     }

     public int getArabicNumber() {
         return arabicNumber;
     }

     public int getStartIndex() {
         return startIndex;
     }

     public int getEndIndex() {
         return endIndex;
     }
    }