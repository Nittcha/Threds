public class FlipCase {
    public static void Threads(String[] args) {
        if (args.length == 0) {
            System.out.println("ระบุตัวอักษรนะจ๊ะ");
            return;
        }

        String input = args[0];
        Thread[] threads = new Thread[input.length()];
        char[] result = new char[input.length()];
        
        // สร้าง thread สำหรับแต่ละตัวอักษร
        for (int i = 0; i < input.length(); i++) {
            final int index = i;
            final char c = input.charAt(i);
            
            threads[i] = new Thread(() -> {
                try {
                    char flipped;
                    
                    // ทำการกลับเคสของตัวอักษร (พิมพ์เล็ก -> พิมพ์ใหญ่, พิมพ์ใหญ่ -> พิมพ์เล็ก)
                    if (Character.isLowerCase(c)) {
                        flipped = Character.toUpperCase(c);
                    } else {
                        flipped = Character.toLowerCase(c);
                    }
                    
                    // คำนวณเวลา sleep โดยใช้ค่า ASCII คูณด้วย 10
                    long sleepTime = (int)c * 10;
                    
                    // นอนตามเวลาที่คำนวณได้
                    Thread.sleep(sleepTime);
                    
                    // เก็บผลลัพธ์และแสดงข้อความ
                    result[index] = flipped;
                    System.out.println(c + " -> " + flipped + ", sleep " + sleepTime + " ms.");
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            
            // เริ่มการทำงานของ thread
            threads[i].start();
        }
        
        // จับเวลาการทำงานทั้งหมด
        long startTime = System.currentTimeMillis();
        
        // รอให้ทุก thread ทำงานเสร็จตามลำดับ
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // คำนวณเวลาที่ใช้ทั้งหมด
        long totalTime = System.currentTimeMillis() - startTime;
        
        // แสดงผลลัพธ์สุดท้าย
        System.out.println("Result: " + new String(result));
        System.out.println("Total Wall Clock Time: " + totalTime + " ms.");
    }
}