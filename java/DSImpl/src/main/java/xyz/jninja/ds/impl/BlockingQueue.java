/**
 * 
 */
package xyz.jninja.ds.impl;

import java.util.LinkedList;
import java.util.List;

/**
 * @author viswa
 *
 */
public class BlockingQueue {
  private List<Runnable> queue;
  private int limit = 10;

  public BlockingQueue(int limit) {
    this.queue = new LinkedList<Runnable>();
    this.limit = limit;
  }

  public boolean isEmpty() {
    return queue.size() == 0;
  }

  public synchronized void enqueue(Runnable item) throws InterruptedException {
    if (this.queue.size() == limit) {
      this.wait();
    }

    if (this.queue.size() == 0) {
      this.notifyAll();
    }

    this.queue.add(item);
  }

  public synchronized Runnable dequeue() throws InterruptedException {
    if (this.queue.size() == 0) {
      this.wait();
    }

    if (this.queue.size() == limit) {
      this.notifyAll();
    }
    return this.queue.remove(0);
  }


  /**
   * @param args
   */
  public static void main(String[] args) {}
}
