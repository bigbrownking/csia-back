    package org.agro.agrohack.utils;

    public class TimeChecker {
        public static void main(String[] args) {
            TimeTranslator timeTranslator = new TimeTranslator();
            System.out.println(timeTranslator.parseTimeToDays("Once every 2 days"));
            System.out.println(timeTranslator.parseTimeToDays("Once every 1-2 weeks"));
            System.out.println(timeTranslator.parseTimeToDays("Once a week"));
            System.out.println(timeTranslator.parseTimeToDays("Every day"));
            System.out.println(timeTranslator.parseTimeToDays("Once a day"));
            System.out.println(timeTranslator.parseTimeToDays("Once every 1-2 days"));
            System.out.println(timeTranslator.parseTimeToDays("Every days"));
            System.out.println(timeTranslator.parseTimeToDays("2 times a week"));
            System.out.println(timeTranslator.parseTimeToDays("3 times a week"));
            System.out.println(timeTranslator.parseTimeToDays("5 times a week"));
        }
    }
