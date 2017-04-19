package org.syaku;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 19.
 */
class Test extends Thread {

	private String name;

	public Test(String name) {
		this.name = name;
	}

	@Override
	public synchronized void run() {
		for (int i = 0; i < 5; i++)
			System.out.println("저는 " + i + name + " 과일입니다..");
		try {
			sleep(10000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}

public class TestThread {
	public static void main(String[] args) {
		Test apple = new Test("Apple");
		Test banana = new Test("banana");
		apple.start();
		banana.start();

	}
}