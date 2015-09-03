/**
 * 
 */
package xyz.jninja.threads.impl;

import xyz.jninja.ds.impl.BlockingQueue;

/**
 * @author viswa
 *
 */
public class PooledThread extends Thread {
  private BlockingQueue bQueue;
  private boolean isStopped = false;

  public PooledThread(BlockingQueue bQueue) {
    this.bQueue = bQueue;
  }

  @Override
  public void run() {    
    while (!this.isStopped) {
      try {
        Runnable runnable = (Runnable) this.bQueue.dequeue();
        runnable.run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void doStop() {
    Thread.interrupted();
    this.isStopped = true;
  }
}
