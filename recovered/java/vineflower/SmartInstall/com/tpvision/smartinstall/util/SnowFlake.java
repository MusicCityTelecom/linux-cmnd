package com.tpvision.smartinstall.util;

class SnowFlake {
   private static final long START_STMP = 1480166465631L;
   private static final long SEQUENCE_BIT = 12L;
   private static final long MACHINE_BIT = 5L;
   private static final long DATACENTER_BIT = 5L;
   private static final long MAX_DATACENTER_NUM = 31L;
   private static final long MAX_MACHINE_NUM = 31L;
   private static final long MAX_SEQUENCE = 4095L;
   private static final long MACHINE_LEFT = 12L;
   private static final long DATACENTER_LEFT = 17L;
   private static final long TIMESTMP_LEFT = 22L;
   private long datacenterId;
   private long machineId;
   private long sequence = 0L;
   private long lastStmp = -1L;

   public SnowFlake(long datacenterId, long machineId) {
      if (datacenterId > 31L || datacenterId < 0L) {
         throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
      }

      if (machineId <= 31L && machineId >= 0L) {
         this.datacenterId = datacenterId;
         this.machineId = machineId;
      } else {
         throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
      }
   }

   public synchronized long nextId() {
      long currStmp = this.getNewstmp();
      if (currStmp < this.lastStmp) {
         throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
      }

      if (currStmp == this.lastStmp) {
         this.sequence = this.sequence + 1L & 4095L;
         if (this.sequence == 0L) {
            currStmp = this.getNextMill();
         }
      } else {
         this.sequence = 0L;
      }

      this.lastStmp = currStmp;
      return currStmp - 1480166465631L << 22 | this.datacenterId << 17 | this.machineId << 12 | this.sequence;
   }

   private long getNextMill() {
      long mill = this.getNewstmp();

      while (mill <= this.lastStmp) {
         mill = this.getNewstmp();
      }

      return mill;
   }

   private long getNewstmp() {
      return System.currentTimeMillis();
   }

   public static void main(String[] args) {
      SnowFlake snowFlake = new SnowFlake(2L, 3L);
      long start = System.currentTimeMillis();

      for (int i = 0; i < 1; i++) {
         System.out.println(snowFlake.nextId());
      }

      System.out.println(System.currentTimeMillis() - start);
   }
}
