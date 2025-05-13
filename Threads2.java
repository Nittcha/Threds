import java.util.ArrayList;
import java.util.List;

public class FlipCase2 {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("ระบุตัวอักษรนะจ๊ะ");
      return;
    }

    String input = args[0];
    char[] result = new char[input.length()];
    Thread[] threads = new Thread[input.length()];
    List<Integer> joinOrder = new ArrayList<>();
    long startTime = System.currentTimeMillis();

    // สร้าง thread สำหรับแต่ละตัวอักษร
    for (int i = 0; i < input.length(); i++) {
      final int index = i;
      final char c = input.charAt(i);

      threads[i] = new Thread(() -> {
        try {
          // สลับ case: ตัวพิมพ์เล็กเป็นใหญ่, ตัวพิมพ์ใหญ่เป็นเล็ก
          char flipped = Character.isLowerCase(c)
              ? Character.toUpperCase(c)
              : Character.toLowerCase(c);

          // รอตามค่า ASCII * 5
          long sleepTime = (int) c * 2;
          Thread.sleep(sleepTime);

          // เก็บผลลัพธ์และแสดงผล
          result[index] = flipped;
          System.out.println(c + " -> " + flipped + ", sleep " + sleepTime + " ms.");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
      threads[i].start();
    }

    // รอ thread ที่เสร็จก่อน
    int remaining = threads.length;
    while (remaining > 0) {
      for (int i = 0; i < threads.length; i++) {
        // ถ้า thread นี้ยังไม่ถูก join และทำงานเสร็จแล้ว
        if (threads[i] != null && !threads[i].isAlive()) {
          try {
            threads[i].join(); // join thread ที่เสร็จ
            joinOrder.add(i); // บันทึกลำดับ
            threads[i] = null; // ทำเครื่องหมายว่า join แล้ว
            remaining--; // ลดจำนวน thread ที่เหลือ
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }

    // แสดงผลลัพธ์
    System.out.print("Thread Join: ");
    for (int i = 0; i < joinOrder.size(); i++) {
      System.out.print(joinOrder.get(i));
      if (i < joinOrder.size() - 1)
        System.out.print(" ");
    }
    System.out.println();
    System.out.println("Result: " + new String(result));
    System.out.println("Total Wall Clock Time: " + (System.currentTimeMillis() - startTime) + " ms.");
  }
}