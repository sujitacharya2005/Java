class Print
{
	boolean isOdd = false;
	synchronized void printEven(int number)
	{
		while(isOdd == false)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Even" +number);
		isOdd = false;
		notifyAll();
	}
	synchronized void printOdd(int number)
	{
		while(isOdd == true)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Odd " +number);
		isOdd = true;
		notifyAll();
	}
}

class TaskEvenOdd implements Runnable {
	boolean isEven;
	Print mPrint;
	public TaskEvenOdd(boolean isEven,  Print p) {
		this.isEven = isEven;
		mPrint = p;
	}
	@Override
	public void run() {
		int i = isEven ? 2 : 1;
		for(; i <= 10; i+=2)
		{
			if(isEven)
				mPrint.printEven(i);
			else
				mPrint.printOdd(i);
		}
	}
}

public class OddEvenTwoThread {
	public static void main(String[] args) {
		Print p  = new Print();
		Thread t1 = new Thread(new TaskEvenOdd(true, p));
		Thread t2 = new Thread(new TaskEvenOdd(false, p));
		t1.start();
		t2.start();
		System.out.println("Main thread");
	}
}
