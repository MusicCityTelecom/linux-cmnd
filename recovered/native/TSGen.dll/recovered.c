/* Reconstructed pseudocode; not original or buildable C. */

/* 10001000 FUN_10001000 */

undefined4 FUN_10001000(void)

{
  return 0;
}



/* 10001010 FUN_10001010 */

undefined4 __cdecl FUN_10001010(undefined2 param_1,undefined2 param_2,undefined2 param_3)

{
  undefined2 *puVar1;
  undefined1 *puVar2;
  undefined2 *puVar3;
  undefined1 *puVar4;
  int *in_EAX;
  undefined4 *puVar5;
  void *pvVar6;
  void *pvVar7;
  int *piVar8;
  int iVar9;
  
  puVar5 = malloc(0xc);
  *in_EAX = (int)puVar5;
  pvVar6 = malloc(0x14);
  in_EAX[4] = (int)pvVar6;
  pvVar7 = malloc(0x10);
  in_EAX[3] = (int)pvVar7;
  if (((puVar5 != (undefined4 *)0x0) && (pvVar6 != (void *)0x0)) && (pvVar7 != (void *)0x0)) {
    *puVar5 = 0;
    puVar5[1] = 0;
    puVar5[2] = 0;
    puVar5 = (undefined4 *)in_EAX[4];
    *puVar5 = 0;
    puVar5[1] = 0;
    puVar5[2] = 0;
    puVar5[3] = 0;
    puVar5[4] = 0;
    puVar5 = (undefined4 *)in_EAX[3];
    *puVar5 = 0;
    puVar5[1] = 0;
    puVar5[2] = 0;
    puVar5[3] = 0;
    piVar8 = in_EAX + 1;
    *piVar8 = (int)piVar8;
    in_EAX[2] = (int)piVar8;
    piVar8 = in_EAX + 5;
    *piVar8 = (int)piVar8;
    in_EAX[6] = (int)piVar8;
    piVar8 = in_EAX + 7;
    *piVar8 = (int)piVar8;
    in_EAX[8] = (int)piVar8;
    piVar8 = in_EAX + 9;
    *piVar8 = (int)piVar8;
    in_EAX[10] = (int)piVar8;
    piVar8 = in_EAX + 0xb;
    *piVar8 = (int)piVar8;
    in_EAX[0xc] = (int)piVar8;
    puVar3 = (undefined2 *)*in_EAX;
    *puVar3 = param_1;
    puVar1 = puVar3 + 2;
    *(undefined1 *)(puVar3 + 1) = 1;
    *(undefined2 **)puVar1 = puVar1;
    *(undefined2 **)(puVar3 + 4) = puVar1;
    iVar9 = 0;
    piVar8 = malloc(0xc);
    if (piVar8 == (int *)0x0) {
      iVar9 = 0x16;
    }
    else {
      piVar8[2] = 0x100000;
      *piVar8 = (int)piVar8;
      piVar8[1] = (int)piVar8;
    }
    if (iVar9 == 0) {
      iVar9 = *in_EAX;
      puVar5 = *(undefined4 **)(iVar9 + 8);
      *(int **)(iVar9 + 8) = piVar8;
      piVar8[1] = (int)puVar5;
      *piVar8 = iVar9 + 4;
      *puVar5 = piVar8;
      puVar4 = (undefined1 *)in_EAX[4];
      puVar2 = puVar4 + 4;
      *(undefined1 **)(puVar4 + 8) = puVar2;
      *(undefined1 **)puVar2 = puVar2;
      *puVar4 = 1;
      *(undefined2 *)(puVar4 + 2) = param_3;
      puVar2 = puVar4 + 0xc;
      *(undefined1 **)(puVar4 + 0x10) = puVar2;
      *(undefined1 **)puVar2 = puVar2;
      puVar3 = (undefined2 *)in_EAX[3];
      *puVar3 = param_1;
      puVar1 = puVar3 + 4;
      *(undefined2 **)(puVar3 + 6) = puVar1;
      *(undefined2 **)puVar1 = puVar1;
      *(undefined1 *)(puVar3 + 1) = 1;
      puVar3[2] = param_2;
      return 0;
    }
  }
  free((void *)*in_EAX);
  free((void *)in_EAX[4]);
  free((void *)in_EAX[3]);
  return 0x16;
}



/* 10001160 FUN_10001160 */

undefined4 FUN_10001160(void)

{
  int *piVar1;
  int *piVar2;
  int *piVar3;
  int *in_EAX;
  int *piVar4;
  int *piVar5;
  undefined4 *puVar6;
  bool bVar7;
  int *local_8;
  
  if (*in_EAX != 0) {
    piVar5 = (int *)(*in_EAX + 4);
    piVar4 = (int *)*piVar5;
    puVar6 = (undefined4 *)*piVar4;
    if (piVar4 != piVar5) {
      do {
        free(piVar4);
        bVar7 = puVar6 != (undefined4 *)(*in_EAX + 4);
        piVar4 = puVar6;
        puVar6 = (undefined4 *)*puVar6;
      } while (bVar7);
    }
    free((void *)*in_EAX);
  }
  piVar5 = (int *)in_EAX[1];
  piVar4 = (int *)*piVar5;
  if (piVar5 != in_EAX + 1) {
    do {
      FUN_1000c160(piVar5);
      bVar7 = piVar4 != in_EAX + 1;
      piVar5 = piVar4;
      piVar4 = (int *)*piVar4;
    } while (bVar7);
  }
  if (in_EAX[4] != 0) {
    FUN_1000a110(in_EAX[4]);
    free((void *)in_EAX[4]);
  }
  if (in_EAX[3] != 0) {
    FUN_1000aed0(in_EAX[3]);
    free((void *)in_EAX[3]);
  }
  piVar5 = (int *)in_EAX[5];
  local_8 = (int *)*piVar5;
  if (piVar5 != in_EAX + 5) {
    do {
      FUN_1000ae50();
      free(piVar5);
      bVar7 = local_8 != in_EAX + 5;
      piVar5 = local_8;
      local_8 = (int *)*local_8;
    } while (bVar7);
  }
  piVar5 = (int *)in_EAX[7];
  local_8 = (int *)*piVar5;
  if (piVar5 != in_EAX + 7) {
    do {
      FUN_10009ff0((int)piVar5);
      free(piVar5);
      bVar7 = local_8 != in_EAX + 7;
      piVar5 = local_8;
      local_8 = (int *)*local_8;
    } while (bVar7);
  }
  piVar5 = (int *)in_EAX[0xb];
  local_8 = (int *)*piVar5;
  if (piVar5 != in_EAX + 0xb) {
    do {
      FUN_100098c0((int)piVar5);
      free(piVar5);
      bVar7 = local_8 != in_EAX + 0xb;
      piVar5 = local_8;
      local_8 = (int *)*local_8;
    } while (bVar7);
  }
  piVar4 = *(int **)in_EAX[9];
  piVar5 = (int *)in_EAX[9];
  while (piVar2 = piVar4, piVar5 != in_EAX + 9) {
    piVar3 = *(int **)piVar5[0x44];
    piVar4 = (int *)piVar5[0x44];
    while (piVar1 = piVar3, piVar4 != piVar5 + 0x44) {
      free(piVar4);
      piVar3 = (int *)*piVar1;
      piVar4 = piVar1;
    }
    free(piVar5);
    piVar4 = (int *)*piVar2;
    piVar5 = piVar2;
  }
  return 0;
}



/* 100012e0 FUN_100012e0 */

undefined4 __cdecl FUN_100012e0(undefined2 param_1,undefined2 param_2,int param_3)

{
  undefined4 *puVar1;
  int *piVar2;
  int iVar3;
  
  iVar3 = 0;
  piVar2 = malloc(0xc);
  if (piVar2 == (int *)0x0) {
    iVar3 = 0x16;
  }
  else {
    *(undefined2 *)(piVar2 + 2) = param_1;
    *(undefined2 *)((int)piVar2 + 10) = param_2;
    *piVar2 = (int)piVar2;
    piVar2[1] = (int)piVar2;
  }
  if (iVar3 != 0) {
    return 0x1a;
  }
  puVar1 = *(undefined4 **)(param_3 + 8);
  *(int **)(param_3 + 8) = piVar2;
  *piVar2 = param_3 + 4;
  piVar2[1] = (int)puVar1;
  *puVar1 = piVar2;
  return 0;
}



/* 10001340 FUN_10001340 */

undefined4 __cdecl
FUN_10001340(undefined2 param_1,undefined1 param_2,undefined4 param_3,undefined1 param_4,int param_5
            )

{
  int *piVar1;
  undefined4 *puVar2;
  int *piVar3;
  int iVar4;
  undefined4 **local_2c;
  undefined4 **local_28;
  undefined4 local_24;
  undefined1 local_20;
  undefined2 local_1f;
  short local_1c [2];
  undefined4 **local_18;
  undefined4 **local_14;
  int *local_10;
  undefined1 local_9 [5];
  
  iVar4 = 0;
  piVar3 = malloc(0x18);
  if (piVar3 == (int *)0x0) {
    iVar4 = 0x16;
  }
  else {
    piVar3[2] = 0;
    piVar3[4] = 0;
    piVar3[5] = 0;
    *(undefined2 *)((int)piVar3 + 0x12) = param_1;
    piVar1 = piVar3 + 2;
    *(undefined1 *)(piVar3 + 4) = 0xb;
    *piVar3 = (int)piVar3;
    piVar3[1] = (int)piVar3;
    *piVar1 = (int)piVar1;
    piVar3[3] = (int)piVar1;
  }
  if (iVar4 != 0) {
    return 0x1a;
  }
  local_9[0] = param_2;
  FUN_10008070(local_9,&local_10);
  puVar2 = (undefined4 *)piVar3[3];
  piVar3[3] = (int)local_10;
  local_10[1] = (int)puVar2;
  *local_10 = (int)(piVar3 + 2);
  *puVar2 = local_10;
  local_24 = param_3;
  local_2c = &local_18;
  local_20 = param_4;
  local_1c[0] = 10;
  local_18 = &local_2c;
  local_1f = 0;
  local_28 = local_2c;
  local_14 = local_18;
  FUN_100080e0(local_1c,&local_10);
  puVar2 = (undefined4 *)piVar3[3];
  piVar3[3] = (int)local_10;
  *local_10 = (int)(piVar3 + 2);
  local_10[1] = (int)puVar2;
  *puVar2 = local_10;
  puVar2 = *(undefined4 **)(param_5 + 0x1c);
  *(int **)(param_5 + 0x1c) = piVar3;
  *piVar3 = param_5 + 0x18;
  piVar3[1] = (int)puVar2;
  *puVar2 = piVar3;
  return 0;
}



/* 10001430 FUN_10001430 */

void __cdecl FUN_10001430(undefined2 param_1,undefined1 param_2,undefined4 param_3,int param_4)

{
  int *piVar1;
  undefined4 *puVar2;
  int *piVar3;
  int iVar4;
  undefined2 local_334 [128];
  undefined1 local_233;
  int local_12c;
  int *local_128;
  undefined2 local_124;
  undefined1 local_122;
  undefined1 local_121;
  undefined4 local_120;
  undefined4 local_11c;
  undefined4 local_118;
  undefined4 local_114;
  char local_110 [4];
  undefined1 local_10c;
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_12c = param_4;
  piVar3 = malloc(0x18);
  if (piVar3 == (int *)0x0) {
    printf("OUT OF MEMORY\n");
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  piVar3[2] = 0;
  piVar3[3] = 0;
  piVar3[4] = 0;
  piVar1 = piVar3 + 4;
  *(undefined2 *)(piVar3 + 2) = param_1;
  *(undefined4 *)((int)piVar3 + 10) = 0x40000;
  *piVar1 = (int)piVar1;
  piVar3[5] = (int)piVar1;
  *piVar3 = (int)piVar3;
  piVar3[1] = (int)piVar3;
  local_233 = 0;
  local_334[0] = 0xc;
  iVar4 = FUN_10008400((undefined1 *)local_334,&local_128);
  if (iVar4 == 0) {
    puVar2 = (undefined4 *)piVar3[5];
    piVar3[5] = (int)local_128;
    local_128[1] = (int)puVar2;
    *local_128 = (int)piVar1;
    *puVar2 = local_128;
    local_124 = 6;
    local_120 = param_3;
    local_122 = param_2;
    local_121 = 2;
    local_11c = 0x1194;
    local_118 = 0x1194;
    local_114 = 0xa00;
    strcpy_s(local_110,4,"eng");
    local_10c = 0;
    iVar4 = FUN_100084e0(&local_128);
    if (iVar4 != 0) {
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
    puVar2 = (undefined4 *)piVar3[5];
    piVar3[5] = (int)local_128;
    *local_128 = (int)piVar1;
    local_128[1] = (int)puVar2;
    *puVar2 = local_128;
    puVar2 = *(undefined4 **)(local_12c + 0xc);
    *(int **)(local_12c + 0xc) = piVar3;
    *piVar3 = local_12c + 8;
    piVar3[1] = (int)puVar2;
    *puVar2 = piVar3;
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 100015b0 FUN_100015b0 */

void __cdecl
FUN_100015b0(undefined2 param_1,undefined2 param_2,undefined4 param_3,undefined1 param_4,
            undefined1 param_5,undefined1 param_6,undefined1 param_7,undefined1 param_8)

{
  int *piVar1;
  undefined4 *puVar2;
  int iVar3;
  int *piVar4;
  int unaff_EBX;
  char *local_24;
  undefined2 local_20;
  int *local_1c;
  char local_18 [16];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  builtin_strncpy(local_18,"SSU SMART C ",0xc);
  builtin_strncpy(local_18 + 0xc,"UPG",4);
  local_24 = local_18;
  local_20 = 0xf;
  iVar3 = FUN_100087b0(&local_24,&local_1c);
  if (iVar3 != 0) {
    printf("DESCRw_ConvertStreamIdDescriptor failed\n");
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  puVar2 = *(undefined4 **)(unaff_EBX + 8);
  *(int **)(unaff_EBX + 8) = local_1c;
  *local_1c = unaff_EBX + 4;
  local_1c[1] = (int)puVar2;
  *puVar2 = local_1c;
  piVar4 = malloc(0x18);
  if (piVar4 != (int *)0x0) {
    piVar4[2] = 0;
    piVar4[3] = 0;
    piVar4[4] = 0;
    *(undefined2 *)((int)piVar4 + 10) = param_2;
    *(undefined2 *)(piVar4 + 2) = param_1;
    *piVar4 = (int)piVar4;
    piVar1 = piVar4 + 4;
    local_20 = CONCAT11(local_20._1_1_,param_8);
    piVar4[1] = (int)piVar4;
    *piVar1 = (int)piVar1;
    piVar4[5] = (int)piVar1;
    local_24 = (char *)CONCAT13(param_7,CONCAT21(CONCAT11(param_6,param_5),param_4));
    iVar3 = FUN_10008830(&local_1c);
    if (iVar3 == 0) {
      puVar2 = (undefined4 *)piVar4[5];
      piVar4[5] = (int)local_1c;
      *local_1c = (int)piVar1;
      local_1c[1] = (int)puVar2;
      *puVar2 = local_1c;
      puVar2 = *(undefined4 **)(unaff_EBX + 0x10);
      *(int **)(unaff_EBX + 0x10) = piVar4;
      *piVar4 = unaff_EBX + 0xc;
      piVar4[1] = (int)puVar2;
      *puVar2 = piVar4;
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 10001700 FUN_10001700 */

undefined4 __cdecl FUN_10001700(int *param_1,int param_2)

{
  undefined4 *puVar1;
  int iVar2;
  
  iVar2 = FUN_100086d0(&param_1);
  if (iVar2 != 0) {
    return 0x1a;
  }
  puVar1 = *(undefined4 **)(param_2 + 8);
  *(int **)(param_2 + 8) = param_1;
  param_1[1] = (int)puVar1;
  *param_1 = param_2 + 4;
  *puVar1 = param_1;
  return 0;
}



/* 10001750 FUN_10001750 */

undefined4 __cdecl FUN_10001750(int param_1,ushort param_2)

{
  int iVar1;
  int unaff_EBX;
  char *pcVar2;
  undefined4 *puVar3;
  
  if (unaff_EBX == 1) {
    pcVar2 = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\0218.dcf";
LAB_1000176b:
    puVar3 = &DAT_1005aa48;
    for (iVar1 = 0xb; iVar1 != 0; iVar1 = iVar1 + -1) {
      *puVar3 = *(undefined4 *)pcVar2;
      pcVar2 = pcVar2 + 4;
      puVar3 = puVar3 + 1;
    }
  }
  else if (unaff_EBX == 2) {
    pcVar2 = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\021b.dcf";
    goto LAB_1000176b;
  }
  DAT_1006c000 = fopen((char *)&DAT_1005aa48,"w");
  if (unaff_EBX == 1) {
    pcVar2 = "Target=/00903e/0218.dcf";
  }
  else {
    if (unaff_EBX != 2) goto LAB_100017bb;
    pcVar2 = "Target=/00903e/021b.dcf";
  }
  fprintf(DAT_1006c000,"%s\n",pcVar2);
LAB_100017bb:
  fprintf(DAT_1006c000,"%s%d\n","Version=",(uint)param_2);
  fprintf(DAT_1006c000,"%s\n","HWVersion=290");
  fprintf(DAT_1006c000,"%s\n","SWModel=240");
  fprintf(DAT_1006c000,"%s\n",&DAT_10011482);
  fprintf(DAT_1006c000,"%s%d\n","CodeSize=",param_1);
  fprintf(DAT_1006c000,"%s\n",&DAT_10011482);
  fprintf(DAT_1006c000,"%s\n",
          "LangReason=dnk:Important software upgrade for MTK2K15 - New Version 0.240.240.0");
  fprintf(DAT_1006c000,"%s\n",
          "LangReason=eng:Important software upgrade for MTK2K15 - New Version 0.240.240.0");
  fprintf(DAT_1006c000,"%s\n","Permission=1");
  fprintf(DAT_1006c000,"%s\n",&DAT_10011482);
  fprintf(DAT_1006c000,"%s\n","Load=/00903e/images/part_0.bin");
  fprintf(DAT_1006c000,"%s\n","Address=0");
  fprintf(DAT_1006c000,"%s\n","Authentication=");
  fprintf(DAT_1006c000,"%s\n",&DAT_10011482);
  fprintf(DAT_1006c000,"%s\n","SourceStart=0");
  fprintf(DAT_1006c000,"%s%d\n","CodeSize=",param_1 + -1);
  fprintf(DAT_1006c000,"%s\n","SourceAuthentication=none");
  fprintf(DAT_1006c000,"%s\n","Target=0");
  fprintf(DAT_1006c000,"%s\n",&DAT_10011482);
  fprintf(DAT_1006c000,"%s\n",&DAT_100116b4);
  fclose(DAT_1006c000);
  return 0;
}



/* 10001960 FUN_10001960 */

void __fastcall
FUN_10001960(int param_1,char *param_2,ushort param_3,undefined4 param_4,uint param_5,int param_6,
            int param_7)

{
  int *piVar1;
  undefined2 uVar2;
  char *pcVar3;
  errno_t eVar4;
  char *pcVar6;
  int *piVar7;
  int iVar8;
  undefined4 *puVar9;
  uint uVar10;
  FILE *pFVar11;
  undefined4 *puVar12;
  FILE **ppFVar13;
  int *local_58;
  int local_54;
  int local_50;
  char *local_4c;
  undefined4 local_48;
  undefined2 local_44;
  undefined1 local_42;
  uint local_40;
  FILE *local_3c;
  undefined4 local_38 [11];
  uint local_c;
  undefined4 uVar5;
  
  local_c = DAT_1001409c ^ (uint)&stack0xfffffffc;
  puVar9 = (undefined4 *)&stack0x00000018;
  puVar12 = local_38;
  for (iVar8 = 10; iVar8 != 0; iVar8 = iVar8 + -1) {
    *puVar12 = *puVar9;
    puVar9 = puVar9 + 1;
    puVar12 = puVar12 + 1;
  }
  local_48 = 0x50b;
  local_44 = 0;
  local_42 = 0;
  local_50 = 0;
  local_54 = param_1;
  local_4c = param_2;
  pcVar3 = strstr(param_2,".upg");
  if (pcVar3 != (char *)0x0) {
    local_50 = 1;
    pcVar3 = strstr(param_2,"2K15_MediaSuite");
    if (pcVar3 != (char *)0x0) {
      local_50 = 0;
    }
  }
  local_3c = (FILE *)0x0;
  local_40 = 0;
  eVar4 = fopen_s(&local_3c,param_2,"rb");
  uVar10 = 0;
  if (eVar4 == 0) {
    fseek(local_3c,0,2);
    local_40 = ftell(local_3c);
    fclose(local_3c);
    uVar10 = local_40;
  }
  printf("=======================================================datasize = %ld\r\n",uVar10);
  printf("=======================================================filename = %s\r\n",param_2);
  pcVar3 = strstr(param_2,"2K15_MediaSuite");
  if (pcVar3 != (char *)0x0) {
    printf("================2K15_MediaSuite datasize = %ld\r\n",uVar10);
    printf("================2K15_MediaSuite filename = %s\r\n",param_2);
  }
  if (uVar10 == 0) {
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
  if (1000000000 < uVar10) {
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
  local_48 = CONCAT13((char)((uint)param_4 >> 8),
                      CONCAT12((char)((uint)param_4 >> 0x10),(undefined2)local_48));
  local_44 = CONCAT11((char)(param_5 >> 8),(char)param_4);
  local_42 = (undefined1)param_5;
  iVar8 = FUN_1000a830(param_5,uVar10,&local_48,&local_58);
  if (iVar8 != 0) {
LAB_10001b40:
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
  ppFVar13 = &local_3c;
  uVar5 = FUN_1000c070();
  uVar2 = (undefined2)uVar5;
  uVar5 = FUN_1000c070();
  iVar8 = FUN_10008670(1,1,param_4,(short)uVar5,uVar2,ppFVar13);
  if (iVar8 != 0) goto LAB_10001b40;
  puVar9 = (undefined4 *)local_58[3];
  local_58[3] = (int)local_3c;
  local_3c->_cnt = (int)puVar9;
  local_3c->_ptr = (char *)(local_58 + 2);
  *puVar9 = local_3c;
  ppFVar13 = &local_3c;
  uVar5 = FUN_1000c070();
  uVar2 = (undefined2)uVar5;
  uVar5 = FUN_1000c070();
  iVar8 = FUN_10008670(2,1,param_4,(short)uVar5,uVar2,ppFVar13);
  if (iVar8 != 0) goto LAB_10001b40;
  puVar9 = (undefined4 *)local_58[3];
  local_58[3] = (int)local_3c;
  local_3c->_cnt = (int)puVar9;
  local_3c->_ptr = (char *)(local_58 + 2);
  *puVar9 = local_3c;
  puVar9 = *(undefined4 **)(param_6 + 0x14);
  *(int **)(param_6 + 0x14) = local_58;
  *local_58 = param_6 + 0x10;
  local_58[1] = (int)puVar9;
  *puVar9 = local_58;
  iVar8 = FUN_100099f0(param_3,param_5,param_5,&local_3c);
  pcVar3 = local_4c;
  if (iVar8 != 0) goto LAB_10001c36;
  pcVar6 = strstr(local_4c,"2K15_MediaSuite");
  pFVar11 = local_3c;
  if (pcVar6 == (char *)0x0) {
    iVar8 = FUN_100036e0(pcVar3,local_40,(int)local_3c);
  }
  else {
    uVar5 = FUN_1000c070();
    FUN_10001750(local_40,(ushort)uVar5);
    if (param_7 == 1) {
      pcVar3 = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\0218.dcf";
LAB_10001bdc:
      puVar9 = &DAT_1005aa48;
      for (iVar8 = 0xb; iVar8 != 0; iVar8 = iVar8 + -1) {
        *puVar9 = *(undefined4 *)pcVar3;
        pcVar3 = pcVar3 + 4;
        puVar9 = puVar9 + 1;
      }
    }
    else if (param_7 == 2) {
      pcVar3 = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\021b.dcf";
      goto LAB_10001bdc;
    }
    DAT_1005aa44 = FUN_10004220();
    pFVar11 = local_3c;
    DAT_1005aa40 = 0;
    iVar8 = FUN_100039e0(local_4c,local_40,param_5,(int)local_3c,param_7);
    pcVar3 = local_4c;
  }
  if (iVar8 == 0) {
    piVar7 = *(int **)(local_54 + 0x20);
    *(FILE **)(local_54 + 0x20) = pFVar11;
    pFVar11->_ptr = (char *)(local_54 + 0x1c);
    pFVar11->_cnt = (int)piVar7;
    *piVar7 = (int)pFVar11;
    piVar7 = malloc(0x118);
    if (piVar7 == (int *)0x0) {
      __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
      return;
    }
    piVar1 = piVar7 + 0x44;
    *piVar1 = (int)piVar1;
    piVar7[0x45] = (int)piVar1;
    piVar7[2] = (uint)param_3;
    if (local_50 == 1) {
      iVar8 = FUN_10004110(param_5,(int)piVar7);
    }
    else {
      pcVar3 = strstr(pcVar3,"2K15_MediaSuite");
      if (pcVar3 == (char *)0x0) {
        iVar8 = FUN_10003fe0(local_40,param_5,(int)piVar7);
      }
      else {
        iVar8 = FUN_10003db0(local_40,param_5,(int)piVar7);
      }
    }
    if (iVar8 == 0) {
      strcpy_s((char *)(piVar7 + 4),0x100,local_4c);
      piVar7[3] = param_5;
      puVar9 = *(undefined4 **)(local_54 + 0x28);
      *(int **)(local_54 + 0x28) = piVar7;
      piVar7[1] = (int)puVar9;
      *piVar7 = local_54 + 0x24;
      *puVar9 = piVar7;
      __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
      return;
    }
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
LAB_10001c36:
  __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
  return;
}



/* 10001d50 FUN_10001d50 */

void __thiscall FUN_10001d50(void *this,int param_1,undefined4 param_2,char *param_3)

{
  int *piVar1;
  int *piVar2;
  undefined2 uVar3;
  int iVar4;
  uint uVar5;
  undefined4 uVar6;
  char *pcVar7;
  int *piVar8;
  char *_Format;
  undefined4 *puVar9;
  int *local_120;
  int *local_11c;
  char *local_118;
  undefined4 local_114;
  undefined1 local_110;
  char local_10c [4];
  char local_108 [256];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  piVar8 = (int *)((int)this + 0x18);
  local_118 = param_3;
  if ((int *)*piVar8 == piVar8) {
    local_114 = (int *)0x91000a;
    iVar4 = FUN_100089d0(&local_11c);
    if (iVar4 != 0) goto LAB_10002002;
    puVar9 = *(undefined4 **)((int)this + 0x1c);
    *(int **)((int)this + 0x1c) = local_11c;
    local_11c[1] = (int)puVar9;
    *local_11c = (int)piVar8;
    *puVar9 = local_11c;
    local_114 = (int *)CONCAT13(local_114._3_1_,0x501);
    iVar4 = FUN_10008960(&local_11c);
    if (iVar4 != 0) goto LAB_10002002;
    puVar9 = *(undefined4 **)((int)this + 0x1c);
    *(int **)((int)this + 0x1c) = local_11c;
    *local_11c = (int)piVar8;
    local_11c[1] = (int)puVar9;
    *puVar9 = local_11c;
  }
  piVar8 = malloc(0x18);
  if (piVar8 == (int *)0x0) {
LAB_10002002:
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  *piVar8 = (int)piVar8;
  piVar1 = piVar8 + 4;
  piVar2 = piVar8 + 2;
  piVar8[1] = (int)piVar8;
  *piVar2 = (int)piVar2;
  piVar8[3] = (int)piVar2;
  *piVar1 = (int)piVar1;
  piVar8[5] = (int)piVar1;
  uVar5 = FUN_1000c070();
  local_120 = (int *)(uVar5 & 0xffff);
  uVar6 = FUN_1000c070();
  local_114 = malloc(0x18);
  if (local_114 == (int *)0x0) goto LAB_10002065;
  local_114[2] = 0;
  local_114[4] = 0;
  local_114[5] = 0;
  *(undefined1 *)(local_114 + 2) = 1;
  *(undefined1 *)((int)local_114 + 10) = 1;
  local_114[3] = param_1;
  *(short *)(local_114 + 4) = (short)uVar6;
  *(undefined2 *)((int)local_114 + 0x12) = local_120._0_2_;
  *(undefined1 *)((int)local_114 + 9) = 9;
  *local_114 = (int)local_114;
  local_114[1] = (int)local_114;
  puVar9 = (undefined4 *)piVar8[3];
  piVar8[3] = (int)local_114;
  *local_114 = (int)piVar2;
  local_114[1] = (int)puVar9;
  *puVar9 = local_114;
  puVar9 = &local_114;
  uVar6 = FUN_1000c070();
  uVar3 = (undefined2)uVar6;
  uVar6 = FUN_1000c070();
  iVar4 = FUN_10008670(2,1,param_1,(short)uVar6,uVar3,puVar9);
  if (iVar4 != 0) goto LAB_10002065;
  puVar9 = (undefined4 *)piVar8[3];
  piVar8[3] = (int)local_114;
  *local_114 = (int)piVar2;
  local_114[1] = (int)puVar9;
  *puVar9 = local_114;
  iVar4 = FUN_10008c50(&local_120);
  if (iVar4 != 0) goto LAB_10002065;
  local_114._0_2_ = CONCAT11((char)((uint)param_1 >> 8),(char)((uint)param_1 >> 0x10));
  local_110 = (undefined1)param_2;
  local_114 = (int *)CONCAT13((char)((uint)param_2 >> 8),
                              (int3)CONCAT22((short)param_1,(undefined2)local_114));
  iVar4 = FUN_10008a60(&local_114,&local_11c);
  piVar1 = local_120;
  if (iVar4 != 0) goto LAB_10002065;
  puVar9 = (undefined4 *)local_120[5];
  piVar2 = local_120 + 4;
  local_120[5] = (int)local_11c;
  local_11c[1] = (int)puVar9;
  *local_11c = (int)piVar2;
  *puVar9 = local_11c;
  strcpy_s(local_10c,4,"eng");
  pcVar7 = strstr(local_118,"2K14");
  if (pcVar7 == (char *)0x0) {
    pcVar7 = strstr(local_118,"2K15");
    if (pcVar7 != (char *)0x0) goto LAB_10002017;
    pcVar7 = strstr(local_118,"2K16");
    if (pcVar7 != (char *)0x0) goto LAB_10002017;
    pcVar7 = *(char **)(local_118 + 0x104);
    _Format = "0%04X%s%s";
  }
  else {
LAB_10002017:
    pcVar7 = local_118 + 0x108;
    _Format = "0%s %s%s";
  }
  sprintf_s(local_108,0x100,_Format,pcVar7,local_118 + 0x208,local_118 + 0x219);
  iVar4 = FUN_10008ad0((undefined2 *)local_10c,&local_11c);
  if (iVar4 == 0) {
    puVar9 = (undefined4 *)piVar1[5];
    piVar1[5] = (int)local_11c;
    *local_11c = (int)piVar2;
    local_11c[1] = (int)puVar9;
    *puVar9 = local_11c;
    puVar9 = (undefined4 *)piVar8[5];
    piVar8[5] = (int)piVar1;
    *piVar1 = (int)(piVar8 + 4);
    piVar1[1] = (int)puVar9;
    *puVar9 = piVar1;
    puVar9 = *(undefined4 **)((int)this + 0x24);
    *(int **)((int)this + 0x24) = piVar8;
    *piVar8 = (int)this + 0x20;
    piVar8[1] = (int)puVar9;
    *puVar9 = piVar8;
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
LAB_10002065:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 100020d0 FUN_100020d0 */

void __thiscall FUN_100020d0(void *this,int param_1,int param_2,int param_3)

{
  int *piVar1;
  undefined4 *puVar2;
  int iVar3;
  int *piVar4;
  char *pcVar5;
  int *piVar6;
  undefined4 uVar7;
  char *pcVar8;
  undefined2 local_130;
  int local_12c;
  uint local_128;
  uint local_124;
  undefined1 local_120;
  undefined1 local_11f;
  undefined1 local_11e;
  undefined1 local_11d;
  undefined1 local_11c;
  undefined4 local_118;
  int *local_114;
  char *local_110;
  char local_10c [4];
  char local_108 [256];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  piVar4 = (int *)(param_3 + 0x18);
  if ((int *)*piVar4 == piVar4) {
    local_118 = 0x90000a;
    iVar3 = FUN_100089d0(&local_114);
    if (iVar3 != 0) goto LAB_10002525;
    puVar2 = *(undefined4 **)(param_3 + 0x1c);
    *(int **)(param_3 + 0x1c) = local_114;
    *local_114 = (int)piVar4;
    local_114[1] = (int)puVar2;
    *puVar2 = local_114;
    local_110 = (char *)CONCAT13(local_110._3_1_,0x501);
    iVar3 = FUN_10008960(&local_114);
    if (iVar3 != 0) goto LAB_10002525;
    puVar2 = *(undefined4 **)(param_3 + 0x1c);
    *(int **)(param_3 + 0x1c) = local_114;
    *local_114 = (int)piVar4;
    local_114[1] = (int)puVar2;
    *puVar2 = local_114;
  }
  param_2 = param_2 + -1;
  local_110 = (char *)((int)this + 0x26e);
  local_12c = 0;
  while( true ) {
    piVar4 = malloc(0x18);
    if (piVar4 == (int *)0x0) break;
    *piVar4 = (int)piVar4;
    piVar6 = piVar4 + 4;
    piVar1 = piVar4 + 2;
    pcVar8 = local_110 + 10;
    piVar4[1] = (int)piVar4;
    *piVar1 = (int)piVar1;
    piVar4[3] = (int)piVar1;
    *piVar6 = (int)piVar6;
    piVar4[5] = (int)piVar6;
    local_128 = 0;
    pcVar5 = strstr(pcVar8,"0x");
    if (pcVar5 == (char *)0x0) {
      pcVar5 = strstr(pcVar8,"0X");
      if (pcVar5 != (char *)0x0) goto LAB_100021f3;
      pcVar5 = "%d";
    }
    else {
LAB_100021f3:
      pcVar5 = "%x";
    }
    sscanf_s(pcVar8,pcVar5,&local_128,4);
    pcVar5 = local_110;
    local_124 = local_128;
    local_118 = 0;
    pcVar8 = strstr(local_110,"0x");
    if (pcVar8 == (char *)0x0) {
      pcVar8 = strstr(pcVar5,"0X");
      if (pcVar8 != (char *)0x0) goto LAB_1000225d;
      pcVar8 = "%d";
    }
    else {
LAB_1000225d:
      pcVar8 = "%x";
    }
    sscanf_s(pcVar5,pcVar8,&local_118,4);
    uVar7 = local_118;
    piVar6 = malloc(0x18);
    if (piVar6 == (int *)0x0) break;
    local_130 = (undefined2)uVar7;
    piVar6[2] = 0;
    piVar6[4] = 0;
    piVar6[5] = 0;
    piVar6[3] = param_1;
    *(undefined1 *)(piVar6 + 2) = 1;
    *(undefined2 *)(piVar6 + 4) = local_130;
    *(undefined2 *)((int)piVar6 + 0x12) = (undefined2)local_124;
    *(undefined2 *)((int)piVar6 + 9) = 0x109;
    *piVar6 = (int)piVar6;
    piVar6[1] = (int)piVar6;
    puVar2 = (undefined4 *)piVar4[3];
    piVar4[3] = (int)piVar6;
    *piVar6 = (int)piVar1;
    piVar6[1] = (int)puVar2;
    *puVar2 = piVar6;
    local_124 = FUN_1000c070();
    local_124 = local_124 & 0xffff;
    uVar7 = FUN_1000c070();
    piVar6 = malloc(0x18);
    if (piVar6 == (int *)0x0) break;
    piVar6[2] = 0;
    piVar6[4] = 0;
    piVar6[5] = 0;
    *(undefined1 *)(piVar6 + 2) = 2;
    piVar6[3] = param_1;
    *(short *)(piVar6 + 4) = (short)uVar7;
    *(undefined2 *)((int)piVar6 + 0x12) = (undefined2)local_124;
    *(undefined2 *)((int)piVar6 + 9) = 0x109;
    *piVar6 = (int)piVar6;
    piVar6[1] = (int)piVar6;
    puVar2 = (undefined4 *)piVar4[3];
    piVar4[3] = (int)piVar6;
    *piVar6 = (int)piVar1;
    piVar6[1] = (int)puVar2;
    *puVar2 = piVar6;
    piVar6 = malloc(0x18);
    if (piVar6 == (int *)0x0) break;
    *piVar6 = (int)piVar6;
    piVar1 = piVar6 + 2;
    *piVar1 = (int)piVar1;
    piVar6[3] = (int)piVar1;
    local_11e = (undefined1)param_1;
    local_120 = (undefined1)((uint)param_1 >> 0x10);
    local_11f = (undefined1)((uint)param_1 >> 8);
    local_11c = (undefined1)param_2;
    piVar1 = piVar6 + 4;
    piVar6[1] = (int)piVar6;
    *piVar1 = (int)piVar1;
    piVar6[5] = (int)piVar1;
    local_11d = (undefined1)((uint)param_2 >> 8);
    iVar3 = FUN_10008a60((undefined4 *)&local_120,&local_114);
    if (iVar3 != 0) break;
    puVar2 = (undefined4 *)piVar6[5];
    piVar6[5] = (int)local_114;
    local_114[1] = (int)puVar2;
    *local_114 = (int)piVar1;
    *puVar2 = local_114;
    strcpy_s(local_10c,4,"eng");
    pcVar5 = strstr(local_110 + -0x26e,"2K14");
    if (pcVar5 == (char *)0x0) {
      pcVar5 = strstr(local_110 + -0x26e,"2K15");
      if (pcVar5 != (char *)0x0) goto LAB_10002470;
      pcVar5 = strstr(local_110 + -0x26e,"2K16");
      if (pcVar5 != (char *)0x0) goto LAB_10002470;
      pcVar5 = *(char **)(local_110 + -0x16a);
      pcVar8 = "0%04X%s%s";
    }
    else {
LAB_10002470:
      pcVar5 = local_110 + -0x166;
      pcVar8 = "0%s %s%s";
    }
    sprintf_s(local_108,0x100,pcVar8,pcVar5,local_110 + -0x66,local_110 + -0x55);
    iVar3 = FUN_10008ad0((undefined2 *)local_10c,&local_114);
    if (iVar3 != 0) break;
    puVar2 = (undefined4 *)piVar6[5];
    piVar6[5] = (int)local_114;
    *local_114 = (int)piVar1;
    local_114[1] = (int)puVar2;
    param_2 = param_2 + 1;
    local_110 = local_110 + 0x298;
    *puVar2 = local_114;
    puVar2 = (undefined4 *)piVar4[5];
    piVar4[5] = (int)piVar6;
    *piVar6 = (int)(piVar4 + 4);
    piVar6[1] = (int)puVar2;
    *puVar2 = piVar6;
    puVar2 = *(undefined4 **)(param_3 + 0x24);
    *(int **)(param_3 + 0x24) = piVar4;
    *piVar4 = param_3 + 0x20;
    local_12c = local_12c + 1;
    piVar4[1] = (int)puVar2;
    *puVar2 = piVar4;
    if (1 < local_12c) {
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
  }
LAB_10002525:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 10002540 FUN_10002540 */

void __thiscall
FUN_10002540(void *this,char *param_1,ushort param_2,undefined4 param_3,int param_4,int param_5)

{
  int *piVar1;
  undefined2 uVar2;
  errno_t eVar3;
  uint uVar4;
  int *piVar5;
  int iVar7;
  int *piVar8;
  undefined4 *puVar9;
  undefined4 *puVar10;
  FILE **ppFVar11;
  int *local_48;
  undefined4 local_44;
  undefined2 local_40;
  undefined1 local_3e;
  FILE *local_3c;
  undefined4 local_38 [11];
  uint local_c;
  undefined4 uVar6;
  
  local_c = DAT_1001409c ^ (uint)&stack0xfffffffc;
  puVar9 = (undefined4 *)&stack0x00000018;
  puVar10 = local_38;
  for (iVar7 = 10; iVar7 != 0; iVar7 = iVar7 + -1) {
    *puVar10 = *puVar9;
    puVar9 = puVar9 + 1;
    puVar10 = puVar10 + 1;
  }
  local_44 = 0x50b;
  local_40 = 0;
  local_3e = 0;
  local_3c = (FILE *)0x0;
  eVar3 = fopen_s(&local_3c,param_1,"rb");
  if (eVar3 == 0) {
    fseek(local_3c,0,2);
    uVar4 = ftell(local_3c);
    fclose(local_3c);
    if (uVar4 != 0) {
      if (1000000000 < uVar4) {
        __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
        return;
      }
      piVar5 = malloc(0x18);
      if (piVar5 != (int *)0x0) {
        piVar5[2] = (uint)param_2;
        piVar5[3] = param_4;
        piVar1 = piVar5 + 4;
        *piVar1 = (int)piVar1;
        piVar5[5] = (int)piVar1;
        local_44 = CONCAT13((char)((uint)param_3 >> 8),
                            CONCAT12((char)((uint)param_3 >> 0x10),(undefined2)local_44));
        local_40 = CONCAT11((char)((uint)param_5 >> 8),(char)param_3);
        local_3e = (undefined1)param_5;
        iVar7 = FUN_1000a830(param_5,uVar4,&local_44,&local_48);
        if (iVar7 == 0) {
          ppFVar11 = &local_3c;
          uVar6 = FUN_1000c070();
          uVar2 = (undefined2)uVar6;
          uVar6 = FUN_1000c070();
          iVar7 = FUN_10008670(1,1,param_3,(short)uVar6,uVar2,ppFVar11);
          piVar1 = local_48;
          if (iVar7 == 0) {
            puVar9 = (undefined4 *)local_48[3];
            piVar8 = local_48 + 2;
            local_48[3] = (int)local_3c;
            local_3c->_cnt = (int)puVar9;
            local_3c->_ptr = (char *)piVar8;
            *puVar9 = local_3c;
            ppFVar11 = &local_3c;
            uVar6 = FUN_1000c070();
            uVar2 = (undefined2)uVar6;
            uVar6 = FUN_1000c070();
            iVar7 = FUN_10008670(2,1,param_3,(short)uVar6,uVar2,ppFVar11);
            if (iVar7 == 0) {
              puVar9 = (undefined4 *)piVar1[3];
              piVar1[3] = (int)local_3c;
              local_3c->_cnt = (int)puVar9;
              local_3c->_ptr = (char *)piVar8;
              *puVar9 = local_3c;
              puVar9 = (undefined4 *)piVar5[5];
              piVar5[5] = (int)local_48;
              *local_48 = (int)(piVar5 + 4);
              local_48[1] = (int)puVar9;
              *puVar9 = local_48;
              puVar9 = *(undefined4 **)((int)this + 0x18);
              *(int **)((int)this + 0x18) = piVar5;
              *piVar5 = (int)this + 0x14;
              piVar5[1] = (int)puVar9;
              *puVar9 = piVar5;
              iVar7 = FUN_100099f0(param_2,param_5,param_5,&local_48);
              if (iVar7 == 0) {
                iVar7 = FUN_100036e0(param_1,uVar4,(int)local_48);
                if (iVar7 == 0) {
                  puVar9 = *(undefined4 **)((int)this + 0x20);
                  *(int **)((int)this + 0x20) = local_48;
                  *local_48 = (int)this + 0x1c;
                  local_48[1] = (int)puVar9;
                  *puVar9 = local_48;
                  piVar5 = malloc(0x118);
                  if (piVar5 == (int *)0x0) {
                    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
                    return;
                  }
                  piVar1 = piVar5 + 0x44;
                  *piVar1 = (int)piVar1;
                  piVar5[0x45] = (int)piVar1;
                  piVar5[2] = (uint)param_2;
                  iVar7 = FUN_10003fe0(uVar4,param_5,(int)piVar5);
                  if (iVar7 != 0) {
                    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
                    return;
                  }
                  strcpy_s((char *)(piVar5 + 4),0x100,param_1);
                  piVar5[3] = param_5;
                  puVar9 = *(undefined4 **)((int)this + 0x28);
                  *(int **)((int)this + 0x28) = piVar5;
                  piVar5[1] = (int)puVar9;
                  *piVar5 = (int)this + 0x24;
                  *puVar9 = piVar5;
                  __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
                  return;
                }
              }
              __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
              return;
            }
          }
        }
      }
      __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
      return;
    }
  }
  __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
  return;
}



/* 10002830 FUN_10002830 */

void __fastcall
FUN_10002830(int param_1,undefined2 param_2,undefined4 param_3,undefined4 param_4,char *param_5)

{
  int *piVar1;
  int *piVar2;
  undefined4 *puVar3;
  undefined2 uVar4;
  int iVar5;
  int *piVar6;
  char *pcVar8;
  char *_Format;
  int **ppiVar9;
  undefined1 local_120;
  undefined1 local_11f;
  undefined1 local_11e;
  undefined1 local_11d;
  undefined1 local_11c;
  int *local_118;
  char *local_114;
  int *local_110;
  char local_10c [4];
  char local_108 [256];
  uint local_8;
  undefined4 uVar7;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_114 = param_5;
  local_110 = (int *)(CONCAT22(param_2,10) & 0xffffff);
  iVar5 = FUN_100089d0(&local_118);
  if (iVar5 != 0) {
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  puVar3 = *(undefined4 **)(param_1 + 0x1c);
  *(int **)(param_1 + 0x1c) = local_118;
  local_118[1] = (int)puVar3;
  *local_118 = param_1 + 0x18;
  *puVar3 = local_118;
  local_110 = (int *)CONCAT13(local_110._3_1_,0x501);
  iVar5 = FUN_10008960(&local_118);
  if (iVar5 != 0) {
LAB_10002abe:
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  puVar3 = *(undefined4 **)(param_1 + 0x1c);
  *(int **)(param_1 + 0x1c) = local_118;
  *local_118 = param_1 + 0x18;
  local_118[1] = (int)puVar3;
  *puVar3 = local_118;
  piVar6 = malloc(0x18);
  if (piVar6 == (int *)0x0) goto LAB_10002abe;
  *piVar6 = (int)piVar6;
  piVar1 = piVar6 + 4;
  ppiVar9 = &local_110;
  piVar2 = piVar6 + 2;
  piVar6[1] = (int)piVar6;
  *piVar2 = (int)piVar2;
  piVar6[3] = (int)piVar2;
  *piVar1 = (int)piVar1;
  piVar6[5] = (int)piVar1;
  uVar7 = FUN_1000c070();
  uVar4 = (undefined2)uVar7;
  uVar7 = FUN_1000c070();
  iVar5 = FUN_10008670(1,1,param_3,(short)uVar7,uVar4,ppiVar9);
  if (iVar5 != 0) goto LAB_10002b21;
  puVar3 = (undefined4 *)piVar6[3];
  piVar6[3] = (int)local_110;
  local_110[1] = (int)puVar3;
  *local_110 = (int)piVar2;
  *puVar3 = local_110;
  ppiVar9 = &local_110;
  uVar7 = FUN_1000c070();
  uVar4 = (undefined2)uVar7;
  uVar7 = FUN_1000c070();
  iVar5 = FUN_10008670(2,1,param_3,(short)uVar7,uVar4,ppiVar9);
  if (iVar5 != 0) goto LAB_10002b21;
  puVar3 = (undefined4 *)piVar6[3];
  piVar6[3] = (int)local_110;
  *local_110 = (int)piVar2;
  local_110[1] = (int)puVar3;
  *puVar3 = local_110;
  iVar5 = FUN_10008c50(&local_110);
  if (iVar5 != 0) goto LAB_10002b21;
  local_11e = (undefined1)param_3;
  local_120 = (undefined1)((uint)param_3 >> 0x10);
  local_11f = (undefined1)((uint)param_3 >> 8);
  local_11c = (undefined1)param_4;
  local_11d = (undefined1)((uint)param_4 >> 8);
  iVar5 = FUN_10008a60((undefined4 *)&local_120,&local_118);
  piVar1 = local_110;
  if (iVar5 != 0) goto LAB_10002b21;
  puVar3 = (undefined4 *)local_110[5];
  piVar2 = local_110 + 4;
  local_110[5] = (int)local_118;
  local_118[1] = (int)puVar3;
  *local_118 = (int)piVar2;
  *puVar3 = local_118;
  strcpy_s(local_10c,4,"eng");
  pcVar8 = strstr(local_114,"2K14");
  if (pcVar8 == (char *)0x0) {
    pcVar8 = strstr(local_114,"2K16");
    if (pcVar8 != (char *)0x0) goto LAB_10002ad3;
    pcVar8 = *(char **)(local_114 + 0x104);
    _Format = "0%04X%s%s";
  }
  else {
LAB_10002ad3:
    pcVar8 = local_114 + 0x108;
    _Format = "0%s %s%s";
  }
  sprintf_s(local_108,0x100,_Format,pcVar8,local_114 + 0x208,local_114 + 0x219);
  iVar5 = FUN_10008ad0((undefined2 *)local_10c,&local_118);
  if (iVar5 == 0) {
    puVar3 = (undefined4 *)piVar1[5];
    piVar1[5] = (int)local_118;
    *local_118 = (int)piVar2;
    local_118[1] = (int)puVar3;
    *puVar3 = local_118;
    puVar3 = (undefined4 *)piVar6[5];
    piVar6[5] = (int)piVar1;
    *piVar1 = (int)(piVar6 + 4);
    piVar1[1] = (int)puVar3;
    *puVar3 = piVar1;
    puVar3 = *(undefined4 **)(param_1 + 0x24);
    *(int **)(param_1 + 0x24) = piVar6;
    *piVar6 = param_1 + 0x20;
    piVar6[1] = (int)puVar3;
    *puVar3 = piVar6;
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
LAB_10002b21:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 10002b90 FUN_10002b90 */

undefined4 __cdecl FUN_10002b90(undefined4 param_1,char *param_2)

{
  int iVar1;
  errno_t eVar2;
  size_t sVar3;
  void *local_10;
  size_t local_c;
  FILE *local_8;
  
  local_8 = (FILE *)0x0;
  iVar1 = FUN_1000b210(&local_10,&local_c);
  if (iVar1 != 0) {
    printf("PATw_Convert_2_Section failed(%d)\n",iVar1);
    return 0x1a;
  }
  eVar2 = fopen_s(&local_8,param_2,"wb");
  if (eVar2 != 0) {
    printf("Error opening output file: %x\n",0);
    return 0x1a;
  }
  sVar3 = fwrite(local_10,1,local_c,local_8);
  if (sVar3 != local_c) {
    printf("Error writing to output file\n");
    return 0x1a;
  }
  fclose(local_8);
  free(local_10);
  return 0;
}



/* 10002c60 FUN_10002c60 */

int __cdecl FUN_10002c60(undefined4 param_1,char *param_2)

{
  int iVar1;
  errno_t eVar2;
  size_t sVar3;
  void *local_10;
  size_t local_c;
  FILE *local_8;
  
  local_8 = (FILE *)0x0;
  iVar1 = FUN_1000c480(&local_10,&local_c);
  if (iVar1 == 0) {
    eVar2 = fopen_s(&local_8,param_2,"wb");
    if (eVar2 != 0) {
      return 0x1a;
    }
    sVar3 = fwrite(local_10,1,local_c,local_8);
    if (sVar3 != local_c) {
      return 0x1a;
    }
    fclose(local_8);
    free(local_10);
  }
  return iVar1;
}



/* 10002cf0 FUN_10002cf0 */

undefined4 __cdecl FUN_10002cf0(char *param_1)

{
  int iVar1;
  errno_t eVar2;
  size_t sVar3;
  void *local_10;
  size_t local_c;
  FILE *local_8;
  
  local_8 = (FILE *)0x0;
  iVar1 = FUN_1000a1b0(&local_10,&local_c);
  if (iVar1 == 0) {
    eVar2 = fopen_s(&local_8,param_1,"wb");
    if (eVar2 == 0) {
      sVar3 = fwrite(local_10,1,local_c,local_8);
      if (sVar3 == local_c) {
        fclose(local_8);
        free(local_10);
        return 0;
      }
    }
  }
  return 0x1a;
}



/* 10002d80 FUN_10002d80 */

undefined4 __cdecl FUN_10002d80(undefined4 param_1,char *param_2)

{
  int iVar1;
  errno_t eVar2;
  size_t sVar3;
  void *local_10;
  size_t local_c;
  FILE *local_8;
  
  local_8 = (FILE *)0x0;
  iVar1 = FUN_1000b070(&local_10,&local_c);
  if (iVar1 == 0) {
    eVar2 = fopen_s(&local_8,param_2,"wb");
    if (eVar2 == 0) {
      sVar3 = fwrite(local_10,1,local_c,local_8);
      if (sVar3 == local_c) {
        fclose(local_8);
        free(local_10);
        return 0;
      }
    }
  }
  return 0x1a;
}



/* 10002e10 FUN_10002e10 */

undefined4 __thiscall FUN_10002e10(void *this,char *param_1)

{
  int iVar1;
  errno_t eVar2;
  size_t sVar3;
  void *local_10;
  size_t local_c;
  FILE *local_8;
  
  local_8 = (FILE *)0x0;
  iVar1 = FUN_1000a8d0(this,&local_10);
  if (iVar1 == 0) {
    eVar2 = fopen_s(&local_8,param_1,"wb");
    if (eVar2 == 0) {
      sVar3 = fwrite(local_10,1,local_c,local_8);
      if (sVar3 == local_c) {
        fclose(local_8);
        free(local_10);
        return 0;
      }
    }
  }
  return 0x1a;
}



/* 10002ea0 FUN_10002ea0 */

undefined4 __cdecl FUN_10002ea0(int param_1)

{
  char *in_EAX;
  errno_t eVar1;
  int iVar2;
  size_t sVar3;
  void *local_10;
  size_t local_c;
  FILE *local_8;
  
  local_8 = (FILE *)0x0;
  eVar1 = fopen_s(&local_8,in_EAX,"wb");
  if (eVar1 != 0) {
    return 0x1a;
  }
  iVar2 = FUN_10009a40(param_1,&local_10,&local_c);
  if (iVar2 != 0) {
    return 0x1a;
  }
  sVar3 = fwrite(local_10,1,local_c,local_8);
  if (sVar3 != local_c) {
    return 0x1a;
  }
  fclose(local_8);
  free(local_10);
  return 0;
}



/* 10002f50 FUN_10002f50 */

undefined4 __cdecl FUN_10002f50(char *param_1)

{
  byte *pbVar1;
  byte bVar2;
  ushort uVar3;
  uint uVar4;
  undefined4 uVar5;
  size_t sVar6;
  int in_EAX;
  errno_t eVar7;
  uint uVar8;
  size_t sVar9;
  int iVar10;
  int *piVar11;
  undefined4 *puVar12;
  size_t sVar13;
  undefined4 *puVar14;
  undefined4 local_54;
  undefined1 local_50 [2];
  undefined1 uStack_4e;
  undefined1 uStack_4d;
  undefined3 local_4c;
  undefined1 uStack_49;
  undefined1 local_48 [3];
  undefined1 uStack_45;
  undefined1 local_44;
  undefined1 auStack_43 [2];
  char cStack_41;
  undefined1 local_40;
  undefined1 uStack_3f;
  undefined1 uStack_3e;
  undefined1 uStack_3d;
  undefined4 local_3c;
  undefined2 local_38;
  int *local_34;
  FILE *local_30;
  uint local_2c;
  FILE *local_28;
  FILE *local_24;
  int local_20;
  uint local_1c;
  int local_18;
  uint local_14;
  size_t local_10;
  void *local_c;
  undefined4 local_8;
  
  local_28 = (FILE *)0x0;
  local_24 = (FILE *)0x0;
  eVar7 = fopen_s(&local_28,(char *)(in_EAX + 0x10),"rb");
  if (eVar7 != 0) {
    return 7;
  }
  eVar7 = fopen_s(&local_24,param_1,"wb");
  if (eVar7 != 0) {
    return 0x1a;
  }
  piVar11 = *(int **)(in_EAX + 0x110);
  local_34 = (int *)(in_EAX + 0x110);
  if (piVar11 != local_34) {
    do {
      local_30 = local_28;
      uVar8 = (uint)(piVar11[8] * 0x1e) / (uint)*(ushort *)((int)piVar11 + 0x1e);
      local_20 = 0;
      local_8 = 0xfe01ff00;
      local_18 = 0;
      if ((uint)(piVar11[8] * 0x1e) % (uint)*(ushort *)((int)piVar11 + 0x1e) != 0) {
        uVar8 = uVar8 + 0x1e;
      }
      uVar4 = piVar11[8];
      local_c = malloc(uVar4 + uVar8);
      if (local_c == (void *)0x0) {
        printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
        local_20 = 0x16;
      }
      else {
        local_38 = 0;
        uVar8 = *(ushort *)((int)piVar11 + 0x1e) + 0x1b;
        local_54 = (uVar8 & 0xff) << 0x10;
        local_54 = CONCAT13(*(undefined1 *)((int)piVar11 + 0x15),(undefined3)local_54);
        local_50 = (undefined1  [2])
                   CONCAT11((*(byte *)((int)piVar11 + 0x16) & 0x1f) * '\x02' + -0x3f,
                            (char)piVar11[5]);
        uVar3 = *(ushort *)(piVar11 + 6);
        local_1c = (uint)uVar3;
        _local_50 = CONCAT12(0,local_50);
        _local_4c = CONCAT13(*(undefined1 *)((int)piVar11 + 10),
                             CONCAT12(*(undefined1 *)((int)piVar11 + 0xb),(short)piVar11[2]));
        local_48._0_2_ =
             CONCAT11(*(undefined1 *)((int)piVar11 + 0xe),*(undefined1 *)((int)piVar11 + 0xf));
        _local_48 = CONCAT13((char)piVar11[3],
                             CONCAT12(*(undefined1 *)((int)piVar11 + 0xd),local_48._0_2_));
        local_54 = CONCAT31(CONCAT21(local_54._2_2_,((byte)(uVar8 >> 8) & 0xf) + 0xb0),0x3c);
        _local_50 = CONCAT13((char)(uVar4 / *(ushort *)((int)piVar11 + 0x1e)),_local_50);
        _local_44 = CONCAT31((uint3)CONCAT11(*(undefined1 *)((int)piVar11 + 0x13),(char)piVar11[4]),
                             0xff);
        _local_44 = CONCAT13(*(undefined1 *)((int)piVar11 + 0x12),_local_44);
        _local_40 = CONCAT13(0xff,CONCAT12(*(undefined1 *)((int)piVar11 + 0x16),
                                           CONCAT11((char)piVar11[5],
                                                    *(undefined1 *)((int)piVar11 + 0x15))));
        local_3c = (uint)CONCAT11((char)uVar3,(char)(uVar3 >> 8));
        local_10 = 0;
        if (uVar3 <= *(ushort *)(piVar11 + 7)) {
          do {
            sVar6 = local_10;
            uVar5 = local_54;
            uVar8 = (uint)*(ushort *)((int)piVar11 + 0x1e);
            local_14 = piVar11[8] - local_18;
            if (local_14 < uVar8) {
            }
            else {
              local_14 = uVar8;
            }
            local_2c = local_14 + 0x1b & 0xffff;
            uVar8 = local_2c;
            local_54._3_1_ = SUB41(uVar5,3);
            local_54._0_3_ =
                 CONCAT12((undefined1)local_2c,
                          CONCAT11(((byte)(local_2c >> 8) & 0xf) + 0xb0,(undefined1)local_54));
            _local_44 = CONCAT12((char)(local_14 + 6 >> 8),_local_44);
            _local_44 = CONCAT13((char)local_14 + '\x06',_local_44);
            _local_50 = CONCAT12((char)local_1c,local_50);
            local_3c = CONCAT31(CONCAT21(local_3c._2_2_,(char)local_1c),(char)(local_1c >> 8));
            puVar12 = &local_54;
            puVar14 = (undefined4 *)((int)local_c + local_10);
            for (iVar10 = 6; iVar10 != 0; iVar10 = iVar10 + -1) {
              *puVar14 = *puVar12;
              puVar12 = puVar12 + 1;
              puVar14 = puVar14 + 1;
            }
            *(undefined2 *)puVar14 = *(undefined2 *)puVar12;
            sVar13 = local_10 + 0x1a;
            local_2c = uVar8;
            local_10 = sVar13;
            sVar9 = fread((void *)((int)local_c + sVar13),1,local_14,local_30);
            if (sVar9 != local_14) {
              printf("Cannot read required amount of bytes(%d) after %d bytes\n",local_14,local_18);
              local_20 = 0x1a;
              break;
            }
            local_18 = local_18 + local_14;
            pbVar1 = (byte *)((int)local_c + sVar6);
            uVar8 = 0xffffffff;
            for (iVar10 = local_14 + 0x1a; iVar10 != 0; iVar10 = iVar10 + -1) {
              bVar2 = *pbVar1;
              pbVar1 = pbVar1 + 1;
              uVar8 = uVar8 << 8 ^ *(uint *)(&DAT_10014628 + (uVar8 >> 0x18 ^ (uint)bVar2) * 4);
            }
            local_8 = CONCAT13((char)uVar8,
                               CONCAT12((char)(uVar8 >> 8),
                                        CONCAT11((char)(uVar8 >> 0x10),(char)(uVar8 >> 0x18))));
            *(undefined4 *)((int)local_c + sVar13 + local_14) = local_8;
            local_1c = local_1c + 1;
            local_10 = sVar13 + local_14 + 4;
          } while ((ushort)local_1c <= *(ushort *)(piVar11 + 7));
        }
      }
      sVar6 = local_10;
      if (local_20 != 0) {
        return 0x1a;
      }
      sVar9 = fwrite(local_c,1,local_10,local_24);
      if (sVar9 != sVar6) {
        return 0x1a;
      }
      free(local_c);
      piVar11 = (int *)*piVar11;
    } while (piVar11 != local_34);
  }
  fclose(local_24);
  fclose(local_28);
  return 0;
}



/* 100032a0 FUN_100032a0 */

undefined4 __cdecl FUN_100032a0(char *param_1,int param_2)

{
  undefined4 *puVar1;
  void *_Memory;
  size_t sVar2;
  int in_EAX;
  errno_t eVar3;
  int iVar4;
  size_t sVar5;
  void *local_14;
  size_t local_10;
  FILE *local_c;
  FILE *local_8;
  
  local_c = (FILE *)0x0;
  local_8 = (FILE *)0x0;
  eVar3 = fopen_s(&local_c,(char *)(in_EAX + 0x10),"rb");
  if (eVar3 != 0) {
    return 7;
  }
  eVar3 = fopen_s(&local_8,param_1,"wb");
  if (eVar3 == 0) {
    puVar1 = *(undefined4 **)(in_EAX + 0x110);
    while( true ) {
      if (puVar1 == (undefined4 *)(in_EAX + 0x110)) {
        fclose(local_8);
        fclose(local_c);
        return 0;
      }
      iVar4 = FUN_1000a3c0(local_c,(int *)&local_14,(int *)&local_10,param_2);
      sVar2 = local_10;
      _Memory = local_14;
      if ((iVar4 != 0) || (sVar5 = fwrite(local_14,1,local_10,local_8), sVar5 != sVar2)) break;
      free(_Memory);
      puVar1 = (undefined4 *)*puVar1;
    }
  }
  return 0x1a;
}



/* 10003380 FUN_10003380 */

undefined4 __cdecl FUN_10003380(char *param_1)

{
  byte *pbVar1;
  byte bVar2;
  ushort uVar3;
  short sVar4;
  int in_EAX;
  errno_t eVar5;
  int iVar6;
  void *pvVar7;
  size_t sVar8;
  size_t sVar9;
  int extraout_ECX;
  uint uVar10;
  uint uVar11;
  int *piVar12;
  undefined4 *puVar13;
  size_t sVar14;
  undefined4 *puVar15;
  undefined8 uVar16;
  undefined4 local_58;
  undefined2 local_54;
  undefined2 uStack_52;
  undefined2 local_50;
  undefined1 uStack_4e;
  undefined1 uStack_4d;
  undefined1 local_4c;
  undefined1 uStack_4b;
  undefined1 uStack_4a;
  undefined1 uStack_49;
  undefined1 local_48;
  undefined1 auStack_47 [2];
  char cStack_45;
  undefined2 local_44;
  undefined1 uStack_42;
  undefined1 uStack_41;
  undefined4 local_40;
  undefined2 local_3c;
  int *local_38;
  FILE *local_34;
  FILE *local_30;
  FILE *local_2c;
  int local_28;
  uint local_20;
  int local_1c;
  void *local_18;
  int *local_14;
  size_t local_10;
  undefined4 local_c;
  
  local_30 = (FILE *)0x0;
  local_2c = (FILE *)0x0;
  eVar5 = fopen_s(&local_30,(char *)(in_EAX + 0x10),"rb");
  if (eVar5 != 0) {
    return 7;
  }
  eVar5 = fopen_s(&local_2c,param_1,"wb");
  if (eVar5 != 0) {
    return 0x1a;
  }
  local_38 = (int *)(in_EAX + 0x110);
  piVar12 = (int *)*local_38;
  local_14 = piVar12;
  if (piVar12 != local_38) {
    do {
      uVar11 = piVar12[8];
      local_28 = 0;
      local_1c = 0;
      local_34 = local_30;
      uVar10 = ((uVar11 >> 0x1c) - (uint)(uVar11 * 0x10 < uVar11)) * 2 | uVar11 * 0xf >> 0x1f;
      local_c = 0xfe01ff00;
      local_14 = piVar12;
      uVar16 = __aulldvrm(uVar11 * 0x1e,uVar10,(uint)*(ushort *)((int)piVar12 + 0x1e),0);
      local_20 = (uint)((ulonglong)uVar16 >> 0x20);
      iVar6 = (int)uVar16;
      if ((uVar10 != 0) || (extraout_ECX != 0)) {
        iVar6 = iVar6 + 0x1e;
      }
      pvVar7 = malloc(uVar11 + iVar6);
      local_18 = pvVar7;
      if (pvVar7 == (void *)0x0) {
        printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
        local_28 = 0x16;
      }
      else {
        uVar3 = *(ushort *)(piVar12 + 6);
        local_20 = (uint)uVar3;
        _local_50 = CONCAT13(*(undefined1 *)((int)piVar12 + 10),
                             CONCAT12(*(undefined1 *)((int)piVar12 + 0xb),(short)piVar12[2]));
        local_3c = 0;
        sVar4 = *(short *)((int)piVar12 + 0x1e) + 0x1b;
        _local_4c = CONCAT13((char)piVar12[3],
                             CONCAT12(*(undefined1 *)((int)piVar12 + 0xd),
                                      CONCAT11(*(undefined1 *)((int)piVar12 + 0xe),
                                               *(undefined1 *)((int)piVar12 + 0xf))));
        local_58 = CONCAT31(CONCAT12(*(undefined1 *)((int)piVar12 + 0x15),
                                     CONCAT11((char)sVar4,((byte)((ushort)sVar4 >> 8) & 0xf) + 0xb0)
                                    ),0x3c);
        _local_54 = CONCAT22(0xff00,CONCAT11((*(byte *)((int)piVar12 + 0x16) & 0x1f) * '\x02' +
                                             -0x3f,(char)piVar12[5]));
        _local_48 = CONCAT31((uint3)CONCAT11(*(undefined1 *)((int)piVar12 + 0x13),(char)piVar12[4]),
                             0xff);
        _local_48 = CONCAT13(*(undefined1 *)((int)piVar12 + 0x12),_local_48);
        _local_44 = CONCAT13(0xff,CONCAT12(*(undefined1 *)((int)piVar12 + 0x16),
                                           CONCAT11((char)piVar12[5],
                                                    *(undefined1 *)((int)piVar12 + 0x15))));
        local_40 = (uint)CONCAT11((char)uVar3,(char)(uVar3 >> 8));
        local_10 = 0;
        if (uVar3 <= *(ushort *)(piVar12 + 7)) {
          do {
            sVar9 = local_10;
            pvVar7 = local_18;
            uVar11 = (uint)*(ushort *)((int)piVar12 + 0x1e);
            if ((uint)(piVar12[8] - local_1c) < (uint)*(ushort *)((int)piVar12 + 0x1e)) {
              uVar11 = piVar12[8] - local_1c;
            }
            uVar10 = uVar11 + 0x1b & 0xffff;
            _local_48 = CONCAT12((char)(uVar11 + 6 >> 8),_local_48);
            local_58 = CONCAT22(CONCAT11(local_58._3_1_,(char)uVar10),
                                CONCAT11(((byte)(uVar10 >> 8) & 0xf) + 0xb0,(undefined1)local_58));
            _local_54 = CONCAT12((char)local_20,local_54);
            _local_48 = CONCAT13((char)uVar11 + '\x06',_local_48);
            local_40 = CONCAT31(CONCAT21(local_40._2_2_,(char)local_20),(char)(local_20 >> 8));
            puVar13 = &local_58;
            puVar15 = (undefined4 *)((int)local_18 + local_10);
            for (iVar6 = 6; iVar6 != 0; iVar6 = iVar6 + -1) {
              *puVar15 = *puVar13;
              puVar13 = puVar13 + 1;
              puVar15 = puVar15 + 1;
            }
            *(undefined2 *)puVar15 = *(undefined2 *)puVar13;
            sVar14 = local_10 + 0x1a;
            local_10 = sVar14;
            sVar8 = fread((void *)((int)local_18 + sVar14),1,uVar11,local_34);
            if (sVar8 != uVar11) {
              printf("Cannot read required amount of bytes(%d) after %d bytes\n",uVar11,local_1c);
              local_28 = 0x1a;
              piVar12 = local_14;
              pvVar7 = local_18;
              break;
            }
            local_1c = local_1c + uVar11;
            pbVar1 = (byte *)((int)pvVar7 + sVar9);
            uVar10 = 0xffffffff;
            for (iVar6 = uVar11 + 0x1a; iVar6 != 0; iVar6 = iVar6 + -1) {
              bVar2 = *pbVar1;
              pbVar1 = pbVar1 + 1;
              uVar10 = uVar10 << 8 ^ *(uint *)(&DAT_10014628 + (uVar10 >> 0x18 ^ (uint)bVar2) * 4);
              pvVar7 = local_18;
            }
            local_c = CONCAT22(CONCAT11((char)uVar10,(char)(uVar10 >> 8)),
                               CONCAT11((char)(uVar10 >> 0x10),(char)(uVar10 >> 0x18)));
            *(undefined4 *)((int)pvVar7 + sVar14 + uVar11) = local_c;
            local_20 = local_20 + 1;
            local_10 = sVar14 + uVar11 + 4;
            piVar12 = local_14;
            pvVar7 = local_18;
          } while ((ushort)local_20 <= *(ushort *)(local_14 + 7));
        }
      }
      if (local_28 != 0) {
        return 0x1a;
      }
      sVar9 = fwrite(pvVar7,1,local_10,local_2c);
      if (sVar9 != local_10) {
        return 0x1a;
      }
      free(pvVar7);
      piVar12 = (int *)*piVar12;
      local_14 = piVar12;
    } while (piVar12 != local_38);
  }
  fclose(local_2c);
  fclose(local_30);
  return 0;
}



/* 100036e0 FUN_100036e0 */

void __fastcall FUN_100036e0(char *param_1,uint param_2,int param_3)

{
  int *piVar1;
  undefined4 *puVar2;
  void *_Memory;
  int iVar3;
  uint uVar4;
  errno_t eVar5;
  size_t sVar6;
  int *piVar7;
  int iVar8;
  FILE *local_138;
  int local_134;
  undefined4 local_130;
  undefined1 local_12c;
  undefined2 local_12a;
  uint local_128;
  void *local_124;
  int local_120;
  int local_11c;
  undefined1 local_115;
  int *local_114;
  char local_110 [260];
  uint local_c;
  
  local_c = DAT_1001409c ^ (uint)&stack0xfffffffc;
  uVar4 = param_2 / 0xfa00000;
  local_134 = 0;
  if (param_2 != uVar4 * 0xfa00000) {
    uVar4 = uVar4 + 1;
  }
  local_120 = 0;
  local_128 = uVar4;
  strcpy_s(local_110,0x100,"F6_sw");
  eVar5 = fopen_s(&local_138,param_1,"rb");
  if (eVar5 != 0) {
LAB_100039ab:
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
  local_124 = malloc(param_2);
  if (local_124 == (void *)0x0) {
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
  local_11c = 0;
  if (uVar4 != 0) {
    do {
      uVar4 = param_2 - local_120;
      if (param_2 < param_2 - local_120) {
        uVar4 = param_2;
      }
      sVar6 = fread(local_124,1,uVar4,local_138);
      if (sVar6 != uVar4) goto LAB_100039ab;
      iVar8 = 0;
      piVar7 = malloc(0x1c);
      if (piVar7 == (int *)0x0) {
        printf("Insufficient memory!! Failed to create module_info\n");
        iVar8 = 0x16;
      }
      else {
        piVar7[2] = 0;
        piVar7[4] = 0;
        piVar7[6] = 0;
        *(undefined1 *)(piVar7 + 6) = 0;
        piVar1 = piVar7 + 2;
        *(undefined2 *)(piVar7 + 4) = 0x300;
        piVar7[5] = uVar4;
        *piVar1 = (int)piVar1;
        piVar7[3] = (int)piVar1;
        *piVar7 = (int)piVar7;
        piVar7[1] = (int)piVar7;
      }
      iVar3 = local_11c;
      local_134 = iVar8;
      if (iVar8 != 0) {
        __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
        return;
      }
      if (local_11c == 0) {
        FUN_10008200(local_110,&local_114);
        puVar2 = (undefined4 *)piVar7[3];
        piVar7[3] = (int)local_114;
        *local_114 = (int)(piVar7 + 2);
        local_114[1] = (int)puVar2;
        *puVar2 = local_114;
      }
      if (iVar3 < (int)(local_128 - 1)) {
        local_12c = 0;
        local_12a = 0x301;
        FUN_10008290(&local_114);
        puVar2 = (undefined4 *)piVar7[3];
        piVar7[3] = (int)local_114;
        *local_114 = (int)(piVar7 + 2);
        local_114[1] = (int)puVar2;
        *puVar2 = local_114;
      }
      local_130 = FUN_1000b3b0(uVar4);
      FUN_10008300(&local_114);
      puVar2 = (undefined4 *)piVar7[3];
      piVar7[3] = (int)local_114;
      *local_114 = (int)(piVar7 + 2);
      local_114[1] = (int)puVar2;
      *puVar2 = local_114;
      if (local_11c == 0) {
        local_115 = 0;
        FUN_10008390(&local_115,&local_114);
        puVar2 = (undefined4 *)piVar7[3];
        piVar7[3] = (int)local_114;
        *local_114 = (int)(piVar7 + 2);
        local_114[1] = (int)puVar2;
        *puVar2 = local_114;
      }
      puVar2 = *(undefined4 **)(param_3 + 0x1c);
      local_120 = local_120 + uVar4;
      *(int **)(param_3 + 0x1c) = piVar7;
      *piVar7 = param_3 + 0x18;
      piVar7[1] = (int)puVar2;
      local_11c = local_11c + 1;
      *puVar2 = piVar7;
      *(short *)(param_3 + 0x16) = *(short *)(param_3 + 0x16) + 1;
    } while (local_11c < (int)local_128);
  }
  _Memory = local_124;
  fclose(local_138);
  free(_Memory);
  __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
  return;
}



/* 100039e0 FUN_100039e0 */

void __thiscall FUN_100039e0(void *this,uint param_1,int param_2,int param_3,int param_4)

{
  int *piVar1;
  undefined4 *puVar2;
  void *pvVar3;
  errno_t eVar4;
  uint uVar5;
  size_t sVar6;
  int *piVar7;
  FILE *pFVar8;
  uint uVar9;
  uint _Count;
  char *_Filename;
  FILE *local_134;
  void *local_130;
  uint local_12c;
  uint local_128;
  int local_124;
  undefined1 local_120;
  short local_11e;
  uint local_11c;
  FILE *local_118;
  undefined1 local_111;
  int *local_110;
  char local_10c [260];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  uVar9 = param_1 / 0xfa00000;
  local_118 = (FILE *)0x0;
  if (param_1 != uVar9 * 0xfa00000) {
    uVar9 = uVar9 + 1;
  }
  local_124 = 0;
  strcpy_s(local_10c,0x100,"F6_sw");
  eVar4 = fopen_s(&local_134,this,"rb");
  if (eVar4 == 0) {
    local_130 = malloc(0xfa00000);
    if (local_130 == (void *)0x0) {
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
    local_11c = 0;
    if (uVar9 != 0xffffffff) {
      do {
        pvVar3 = local_130;
        _Count = param_1 - local_124;
        if (0xfa00000 < _Count) {
          _Count = 0xfa00000;
        }
        uVar5 = local_11c & 0xff ^ param_2 << 8;
        local_128 = uVar5 & 0xffff;
        pFVar8 = local_134;
        local_12c = uVar9;
        if (local_11c == uVar9) {
          param_1 = DAT_1005aa44;
          local_124 = 0;
          _Count = DAT_1005aa44;
          if (0xfa00000 < DAT_1005aa44) {
            _Count = 0xfa00000;
          }
          local_128 = uVar5 & 0xffff;
          local_118 = (FILE *)0x0;
          DAT_1005aa40 = local_11c;
          if (param_4 == 1) {
            _Filename = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\0218.dcf";
          }
          else {
            pFVar8 = local_118;
            if (param_4 != 2) goto LAB_10003b86;
            _Filename = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\021b.dcf";
          }
          eVar4 = fopen_s(&local_118,_Filename,"rb");
          pFVar8 = local_118;
          if (eVar4 != 0) goto LAB_10003d84;
        }
LAB_10003b86:
        sVar6 = fread(pvVar3,1,_Count,pFVar8);
        if (sVar6 != _Count) goto LAB_10003d84;
        pFVar8 = (FILE *)0x0;
        piVar7 = malloc(0x1c);
        if (piVar7 == (int *)0x0) {
          printf("Insufficient memory!! Failed to create module_info\n");
          pFVar8 = (FILE *)0x16;
        }
        else {
          piVar7[2] = 0;
          piVar7[4] = 0;
          piVar7[6] = 0;
          *(undefined1 *)(piVar7 + 6) = 0;
          piVar1 = piVar7 + 2;
          *(undefined2 *)(piVar7 + 4) = (undefined2)local_128;
          piVar7[5] = _Count;
          *piVar1 = (int)piVar1;
          piVar7[3] = (int)piVar1;
          *piVar7 = (int)piVar7;
          piVar7[1] = (int)piVar7;
        }
        uVar5 = local_11c;
        local_118 = pFVar8;
        if (pFVar8 != (FILE *)0x0) {
          __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
          return;
        }
        if (local_11c == 0) {
          FUN_10008200(local_10c,&local_110);
          puVar2 = (undefined4 *)piVar7[3];
          piVar7[3] = (int)local_110;
          *local_110 = (int)(piVar7 + 2);
          local_110[1] = (int)puVar2;
          *puVar2 = local_110;
        }
        if ((1 < (int)local_12c) && ((int)uVar5 <= (int)(local_12c - 1))) {
          local_11e = (short)local_128 + 1;
          if (uVar5 == 0) {
            local_120 = 0;
          }
          else if (uVar5 == local_12c - 1) {
            local_120 = 2;
            local_11e = 0;
          }
          else {
            local_120 = 1;
          }
          FUN_10008290(&local_110);
          puVar2 = (undefined4 *)piVar7[3];
          piVar7[3] = (int)local_110;
          *local_110 = (int)(piVar7 + 2);
          local_110[1] = (int)puVar2;
          *puVar2 = local_110;
        }
        local_12c = FUN_1000b3b0(_Count);
        FUN_10008300(&local_110);
        puVar2 = (undefined4 *)piVar7[3];
        piVar7[3] = (int)local_110;
        *local_110 = (int)(piVar7 + 2);
        local_110[1] = (int)puVar2;
        *puVar2 = local_110;
        if (local_11c == 0) {
          local_111 = 0;
          FUN_10008390(&local_111,&local_110);
          puVar2 = (undefined4 *)piVar7[3];
          piVar7[3] = (int)local_110;
          *local_110 = (int)(piVar7 + 2);
          local_110[1] = (int)puVar2;
          *puVar2 = local_110;
        }
        puVar2 = *(undefined4 **)(param_3 + 0x1c);
        local_124 = local_124 + _Count;
        *(int **)(param_3 + 0x1c) = piVar7;
        *piVar7 = param_3 + 0x18;
        piVar7[1] = (int)puVar2;
        local_11c = local_11c + 1;
        *puVar2 = piVar7;
        *(short *)(param_3 + 0x16) = *(short *)(param_3 + 0x16) + 1;
      } while ((int)local_11c < (int)(uVar9 + 1));
    }
    pvVar3 = local_130;
    fclose(local_134);
    free(pvVar3);
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
LAB_10003d84:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 10003db0 FUN_10003db0 */

undefined4 __cdecl FUN_10003db0(uint param_1,uint param_2,int param_3)

{
  undefined4 *puVar1;
  int *piVar2;
  uint uVar3;
  int iVar4;
  uint uVar5;
  uint uVar6;
  int iVar7;
  undefined4 local_14;
  uint3 local_10;
  undefined1 uStack_d;
  uint local_c;
  
  uVar3 = param_1 / 0xfa00000;
  iVar7 = 0;
  if (param_1 != uVar3 * 0xfa00000) {
    uVar3 = uVar3 + 1;
  }
  uVar5 = 0;
  if (uVar3 != 0) {
    do {
      piVar2 = malloc(0x28);
      if (piVar2 == (int *)0x0) {
        return 0x16;
      }
      *piVar2 = 0;
      piVar2[1] = 0;
      piVar2[2] = 0;
      piVar2[3] = 0;
      piVar2[4] = 0;
      piVar2[5] = 0;
      piVar2[6] = 0;
      piVar2[7] = 0;
      piVar2[8] = 0;
      piVar2[9] = 0;
      local_14._0_2_ = (ushort)local_14 & 0xff00;
      local_c = local_c & 0xffff0000;
      local_14 = CONCAT22(0xfe8,(ushort)local_14);
      local_10 = (uint3)(ushort)((ushort)uVar5 & 0xff ^ (ushort)(param_2 << 8));
      uVar6 = 0xfa00000;
      if (param_1 - iVar7 < 0xfa00001) {
        uVar6 = param_1 - iVar7;
      }
      piVar2[2] = (int)&DAT_10030311;
      piVar2[3] = param_2;
      piVar2[4] = local_14;
      piVar2[5] = _local_10;
      piVar2[6] = local_c;
      *(undefined2 *)((int)piVar2 + 0x1e) = 0xfe2;
      piVar2[9] = iVar7;
      *(short *)(piVar2 + 7) = (short)(uVar6 / 0xfe2);
      piVar2[8] = uVar6;
      puVar1 = *(undefined4 **)(param_3 + 0x114);
      *(int **)(param_3 + 0x114) = piVar2;
      uVar5 = uVar5 + 1;
      iVar7 = iVar7 + uVar6;
      *piVar2 = param_3 + 0x110;
      piVar2[1] = (int)puVar1;
      *puVar1 = piVar2;
    } while ((int)uVar5 < (int)uVar3);
  }
  uVar3 = DAT_1005aa44 / 0xfa00000;
  iVar7 = 0;
  if (DAT_1005aa44 != uVar3 * 0xfa00000) {
    uVar3 = uVar3 + 1;
  }
  iVar4 = uVar3 + uVar5;
  DAT_1005aa40 = (param_2 & 0xff) * 0x100 + (uVar5 & 0xff);
  while( true ) {
    if (iVar4 <= (int)uVar5) {
      return 0;
    }
    piVar2 = malloc(0x28);
    if (piVar2 == (int *)0x0) break;
    *piVar2 = 0;
    piVar2[1] = 0;
    piVar2[2] = 0;
    piVar2[3] = 0;
    piVar2[4] = 0;
    piVar2[5] = 0;
    piVar2[6] = 0;
    piVar2[7] = 0;
    piVar2[8] = 0;
    piVar2[9] = 0;
    local_14._0_2_ = (ushort)local_14 & 0xff00;
    local_c = local_c & 0xffff0000;
    local_14 = CONCAT22(0xfe8,(ushort)local_14);
    uStack_d = (undefined1)((uint)_local_10 >> 0x18);
    local_10 = (uint3)(ushort)((ushort)uVar5 & 0xff ^ (ushort)(param_2 << 8));
    uVar3 = 0xfa00000;
    if (DAT_1005aa44 - iVar7 < 0xfa00001) {
      uVar3 = DAT_1005aa44 - iVar7;
    }
    piVar2[2] = (int)&DAT_10030311;
    piVar2[3] = param_2;
    piVar2[4] = local_14;
    piVar2[5] = _local_10;
    piVar2[6] = local_c;
    *(undefined2 *)((int)piVar2 + 0x1e) = 0xfe2;
    piVar2[9] = iVar7;
    *(short *)(piVar2 + 7) = (short)(uVar3 / 0xfe2);
    piVar2[8] = uVar3;
    puVar1 = *(undefined4 **)(param_3 + 0x114);
    *(int **)(param_3 + 0x114) = piVar2;
    uVar5 = uVar5 + 1;
    iVar7 = iVar7 + uVar3;
    *piVar2 = param_3 + 0x110;
    piVar2[1] = (int)puVar1;
    *puVar1 = piVar2;
  }
  return 0x16;
}



/* 10003fe0 FUN_10003fe0 */

undefined4 __cdecl FUN_10003fe0(uint param_1,int param_2,int param_3)

{
  undefined4 *puVar1;
  ushort uVar2;
  int *piVar3;
  uint uVar4;
  int iVar5;
  uint uVar6;
  int iVar7;
  undefined4 local_14;
  uint3 local_10;
  undefined1 uStack_d;
  uint local_c;
  
  uVar4 = param_1 / 0xfa00000;
  iVar7 = 0;
  if (param_1 != uVar4 * 0xfa00000) {
    uVar4 = uVar4 + 1;
  }
  iVar5 = 0;
  if (uVar4 != 0) {
    do {
      piVar3 = malloc(0x28);
      if (piVar3 == (int *)0x0) {
        return 0x16;
      }
      *piVar3 = 0;
      piVar3[1] = 0;
      piVar3[2] = 0;
      piVar3[3] = 0;
      piVar3[4] = 0;
      piVar3[5] = 0;
      piVar3[6] = 0;
      piVar3[7] = 0;
      piVar3[8] = 0;
      piVar3[9] = 0;
      uVar2 = (ushort)local_14;
      local_c = local_c & 0xffff0000;
      local_14 = CONCAT22(0xfe8,uVar2 & 0xff00);
      local_10 = (uint3)(ushort)((ushort)iVar5 & 0xff ^ (ushort)(param_2 << 8));
      uVar6 = 0xfa00000;
      if (param_1 - iVar7 < 0xfa00001) {
        uVar6 = param_1 - iVar7;
      }
      piVar3[2] = (int)&DAT_10030311;
      piVar3[3] = param_2;
      piVar3[4] = local_14;
      piVar3[5] = _local_10;
      piVar3[6] = local_c;
      *(undefined2 *)((int)piVar3 + 0x1e) = 0xfe2;
      piVar3[9] = iVar7;
      *(short *)(piVar3 + 7) = (short)(uVar6 / 0xfe2);
      piVar3[8] = uVar6;
      puVar1 = *(undefined4 **)(param_3 + 0x114);
      *(int **)(param_3 + 0x114) = piVar3;
      iVar5 = iVar5 + 1;
      iVar7 = iVar7 + uVar6;
      *piVar3 = param_3 + 0x110;
      piVar3[1] = (int)puVar1;
      *puVar1 = piVar3;
    } while (iVar5 < (int)uVar4);
    return 0;
  }
  return 0;
}



/* 10004110 FUN_10004110 */

undefined4 __cdecl FUN_10004110(int param_1,int param_2)

{
  undefined4 *puVar1;
  ushort uVar2;
  uint uVar3;
  int *piVar4;
  uint unaff_EBX;
  uint uVar5;
  int iVar6;
  undefined4 local_18;
  int local_14;
  uint local_10;
  int local_8;
  
  uVar3 = unaff_EBX / unaff_EBX;
  iVar6 = 0;
  if (unaff_EBX % unaff_EBX != 0) {
    uVar3 = uVar3 + 1;
  }
  local_8 = 0;
  if ((int)uVar3 < 1) {
    return 0;
  }
  do {
    piVar4 = malloc(0x28);
    if (piVar4 == (int *)0x0) {
      return 0x16;
    }
    *piVar4 = 0;
    piVar4[1] = 0;
    piVar4[2] = 0;
    piVar4[3] = 0;
    piVar4[4] = 0;
    piVar4[5] = 0;
    piVar4[6] = 0;
    piVar4[7] = 0;
    piVar4[8] = 0;
    piVar4[9] = 0;
    uVar2 = (ushort)local_18;
    local_14 = CONCAT13(local_14._3_1_,0x300);
    local_10 = local_10 & 0xffff0000;
    local_18 = CONCAT22(0xfe8,uVar2 & 0xff00);
    uVar5 = unaff_EBX - iVar6;
    if (unaff_EBX < unaff_EBX - iVar6) {
      uVar5 = unaff_EBX;
    }
    piVar4[2] = (int)&DAT_10030311;
    piVar4[3] = param_1;
    piVar4[4] = local_18;
    piVar4[5] = local_14;
    piVar4[6] = local_10;
    *(short *)(piVar4 + 7) = (short)(uVar5 / 0xfe2);
    piVar4[9] = iVar6;
    *(undefined2 *)((int)piVar4 + 0x1e) = 0xfe2;
    piVar4[8] = uVar5;
    puVar1 = *(undefined4 **)(param_2 + 0x114);
    *(int **)(param_2 + 0x114) = piVar4;
    *piVar4 = param_2 + 0x110;
    local_8 = local_8 + 1;
    iVar6 = iVar6 + uVar5;
    piVar4[1] = (int)puVar1;
    *puVar1 = piVar4;
  } while (local_8 < (int)uVar3);
  return 0;
}



/* 10004220 FUN_10004220 */

long FUN_10004220(void)

{
  char *in_EAX;
  errno_t eVar1;
  long lVar2;
  FILE *local_8;
  
  lVar2 = 0;
  local_8 = (FILE *)0x0;
  eVar1 = fopen_s(&local_8,in_EAX,"rb");
  if (eVar1 == 0) {
    fseek(local_8,0,2);
    lVar2 = ftell(local_8);
    fclose(local_8);
  }
  return lVar2;
}



/* 10004270 FUN_10004270 */

undefined4 * __thiscall FUN_10004270(void *this,byte param_1)

{
  *(undefined ***)this = BabyObject::vftable;
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  return this;
}



/* 100042a0 FUN_100042a0 */

void __fastcall FUN_100042a0(undefined4 *param_1)

{
  *param_1 = BabyObject::vftable;
  return;
}



/* 100042b0 FUN_100042b0 */

void __fastcall FUN_100042b0(undefined4 param_1,int param_2,uint param_3)

{
  char cVar1;
  int in_EAX;
  byte bVar2;
  int iVar3;
  
  cVar1 = (char)param_2;
  if ((*(int *)(in_EAX + 0x14) != 0) && (0 < param_2)) {
    do {
      *(char *)(in_EAX + 0xc) = *(char *)(in_EAX + 0xc) << 1;
      *(int *)(in_EAX + 4) = *(int *)(in_EAX + 4) + 1;
      iVar3 = *(int *)(in_EAX + 8) + -1;
      bVar2 = *(byte *)(in_EAX + 0xc) | (1 << (cVar1 - 1U & 0x1f) & param_3) != 0;
      *(int *)(in_EAX + 8) = iVar3;
      *(byte *)(in_EAX + 0xc) = bVar2;
      if (iVar3 == 0) {
        *(byte *)(*(int *)(in_EAX + 0x10) + *(int *)(in_EAX + 0x14)) = bVar2;
        *(int *)(in_EAX + 0x10) = *(int *)(in_EAX + 0x10) + 1;
        *(undefined1 *)(in_EAX + 0xc) = 0;
        *(undefined4 *)(in_EAX + 8) = 8;
      }
      param_3 = param_3 << 1;
      param_2 = param_2 + -1;
    } while (param_2 != 0);
  }
  return;
}



/* 10004320 FUN_10004320 */

void FUN_10004320(void)

{
  undefined4 *in_EAX;
  
  in_EAX[1] = 0;
  in_EAX[2] = 8;
  *(undefined1 *)(in_EAX + 3) = 0;
  in_EAX[4] = 0;
  in_EAX[5] = 0;
  in_EAX[6] = 0;
  *in_EAX = TransportPacket::vftable;
  in_EAX[7] = 0x47;
  *(undefined2 *)(in_EAX + 8) = 0;
  *(undefined1 *)((int)in_EAX + 0x22) = 0;
  in_EAX[9] = 0xffffffff;
  *(undefined2 *)(in_EAX + 10) = 0x100;
  *(undefined1 *)((int)in_EAX + 0x2a) = 0;
  in_EAX[0xb] = 0;
  in_EAX[0xe] = 0;
  *(undefined2 *)(in_EAX + 0xd) = 0;
  *(undefined1 *)((int)in_EAX + 0x36) = 0;
  in_EAX[0x11] = 0;
  in_EAX[0x12] = 0;
  in_EAX[0x10] = 0;
  in_EAX[0x15] = 0;
  in_EAX[0x16] = 0;
  in_EAX[0x17] = 0;
  in_EAX[0x13] = 0;
  in_EAX[0x14] = 0;
  in_EAX[0xf] = 3;
  return;
}



/* 10004390 FUN_10004390 */

void FUN_10004390(void)

{
  undefined4 *puVar1;
  void *pvVar2;
  undefined4 uVar3;
  undefined4 *unaff_ESI;
  
  *unaff_ESI = CPrvsTSGenerator::vftable;
  puVar1 = operator_new(0x30);
  if (puVar1 == (undefined4 *)0x0) {
    puVar1 = (undefined4 *)0x0;
  }
  else {
    puVar1[1] = 0;
    puVar1[2] = 8;
    *(undefined1 *)(puVar1 + 3) = 0;
    puVar1[4] = 0;
    puVar1[5] = 0;
    puVar1[6] = 0;
    *puVar1 = PrivateSection::vftable;
    *(undefined2 *)(puVar1 + 7) = 0x100;
    *(undefined1 *)((int)puVar1 + 0x1e) = 1;
    puVar1[8] = 0;
    puVar1[9] = 0x1000000;
    *(undefined2 *)(puVar1 + 10) = 0;
    puVar1[0xb] = 0;
  }
  unaff_ESI[1] = puVar1;
  pvVar2 = operator_new(0x60);
  if (pvVar2 != (void *)0x0) {
    uVar3 = FUN_10004320();
    unaff_ESI[2] = uVar3;
    *(undefined1 *)(unaff_ESI + 3) = 0;
    unaff_ESI[4] = 0;
    return;
  }
  unaff_ESI[2] = 0;
  *(undefined1 *)(unaff_ESI + 3) = 0;
  unaff_ESI[4] = 0;
  return;
}



/* 10004420 FUN_10004420 */

undefined4 * __thiscall FUN_10004420(void *this,byte param_1)

{
  *(undefined ***)this = CPrvsTSGenerator::vftable;
  if (*(undefined4 **)((int)this + 4) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)((int)this + 4))(1);
  }
  *(undefined4 *)((int)this + 4) = 0;
  if (*(undefined4 **)((int)this + 8) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)((int)this + 8))(1);
  }
  *(undefined4 *)((int)this + 8) = 0;
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  return this;
}



/* 10004470 FUN_10004470 */

void __fastcall FUN_10004470(undefined4 *param_1)

{
  *param_1 = CPrvsTSGenerator::vftable;
  if ((undefined4 *)param_1[1] != (undefined4 *)0x0) {
    (*(code *)**(undefined4 **)param_1[1])(1);
  }
  param_1[1] = 0;
  if ((undefined4 *)param_1[2] != (undefined4 *)0x0) {
    (*(code *)**(undefined4 **)param_1[2])(1);
  }
  param_1[2] = 0;
  return;
}



/* 100044b0 FUN_100044b0 */

undefined4 * FUN_100044b0(undefined4 *param_1)

{
  void *_Dst;
  undefined4 extraout_ECX;
  undefined4 extraout_ECX_00;
  undefined4 uVar1;
  void *local_10;
  undefined1 *puStack_c;
  undefined4 local_8;
  
  puStack_c = &LAB_1000fc78;
  local_10 = ExceptionList;
  ExceptionList = &local_10;
  param_1[1] = 0;
  param_1[2] = 8;
  *(undefined1 *)(param_1 + 3) = 0;
  param_1[4] = 0;
  param_1[5] = 0;
  param_1[6] = 0;
  local_8 = 0;
  *param_1 = CSimpleMultiplex::vftable;
  _Dst = operator_new__(0xbc);
  param_1[5] = _Dst;
  uVar1 = extraout_ECX;
  if (_Dst != (void *)0x0) {
    memset(_Dst,0,0xbc);
    param_1[6] = 0xbc;
    uVar1 = extraout_ECX_00;
  }
  FUN_10004660(uVar1);
  param_1[10] = 0;
  param_1[0xc] = 0;
  param_1[0xb] = 0;
  param_1[7] = 0;
  param_1[8] = 0;
  param_1[9] = 0;
  ExceptionList = local_10;
  return param_1;
}



/* 10004560 FUN_10004560 */

undefined4 * __thiscall FUN_10004560(void *this,byte param_1)

{
  void *local_10;
  undefined1 *puStack_c;
  undefined4 local_8;
  
  puStack_c = &LAB_1000fc48;
  local_10 = ExceptionList;
  ExceptionList = &local_10;
  *(undefined ***)this = CSimpleMultiplex::vftable;
  local_8 = 0;
  if (*(void **)((int)this + 0x14) != (void *)0x0) {
    operator_delete(*(void **)((int)this + 0x14));
  }
  local_8 = 0xffffffff;
  *(undefined4 *)((int)this + 0x14) = 0;
  *(undefined4 *)((int)this + 0x10) = 0;
  *(undefined4 *)((int)this + 0x18) = 0;
  *(undefined ***)this = BabyObject::vftable;
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  ExceptionList = local_10;
  return this;
}



/* 100045f0 FUN_100045f0 */

void __fastcall FUN_100045f0(undefined4 *param_1)

{
  void *local_10;
  undefined1 *puStack_c;
  undefined4 local_8;
  
  puStack_c = &LAB_1000fc18;
  local_10 = ExceptionList;
  ExceptionList = &local_10;
  *param_1 = CSimpleMultiplex::vftable;
  local_8 = 0;
  if ((void *)param_1[5] != (void *)0x0) {
    operator_delete((void *)param_1[5]);
  }
  param_1[5] = 0;
  param_1[4] = 0;
  param_1[6] = 0;
  *param_1 = BabyObject::vftable;
  ExceptionList = local_10;
  return;
}



/* 10004660 FUN_10004660 */

void __fastcall FUN_10004660(undefined4 param_1)

{
  int in_EAX;
  int iVar1;
  undefined4 extraout_ECX;
  undefined4 extraout_ECX_00;
  undefined4 extraout_ECX_01;
  undefined4 extraout_ECX_02;
  undefined4 extraout_ECX_03;
  undefined4 extraout_ECX_04;
  undefined4 extraout_ECX_05;
  
  if (*(int *)(in_EAX + 0x14) != 0) {
    FUN_100042b0(param_1,8,0x47);
    FUN_100042b0(extraout_ECX,1,0);
    FUN_100042b0(extraout_ECX_00,1,0);
    FUN_100042b0(extraout_ECX_01,1,0);
    FUN_100042b0(extraout_ECX_02,0xd,0x1fff);
    FUN_100042b0(extraout_ECX_03,2,0);
    FUN_100042b0(extraout_ECX_04,2,1);
    iVar1 = FUN_100042b0(extraout_ECX_05,4,0);
    memset((void *)(*(int *)(iVar1 + 0x10) + *(int *)(iVar1 + 0x14)),0xff,0xb8);
  }
  return;
}



/* 100046f0 FUN_100046f0 */

void __fastcall FUN_100046f0(undefined4 *param_1)

{
  *param_1 = CPsiGenerator::vftable;
  if ((undefined4 *)param_1[1] != (undefined4 *)0x0) {
    (*(code *)**(undefined4 **)param_1[1])(1);
  }
  if ((undefined4 *)param_1[2] != (undefined4 *)0x0) {
    (*(code *)**(undefined4 **)param_1[2])(1);
  }
  if ((undefined4 *)param_1[3] != (undefined4 *)0x0) {
    (*(code *)**(undefined4 **)param_1[3])(1);
  }
  return;
}



/* 10004730 FUN_10004730 */

undefined4 * __thiscall FUN_10004730(void *this,byte param_1)

{
  *(undefined ***)this = CPsiGenerator::vftable;
  if (*(undefined4 **)((int)this + 4) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)((int)this + 4))(1);
  }
  if (*(undefined4 **)((int)this + 8) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)((int)this + 8))(1);
  }
  if (*(undefined4 **)((int)this + 0xc) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)((int)this + 0xc))(1);
  }
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  return this;
}



/* 10004780 FUN_10004780 */

void FUN_10004780(CDialog *param_1)

{
  uint uVar1;
  void *local_10;
  undefined1 *puStack_c;
  uint local_8;
  
  puStack_c = &LAB_1000fdb1;
  local_10 = ExceptionList;
  uVar1 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  ExceptionList = &local_10;
  local_8 = 0;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x2dc));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x2d8));
  *(undefined ***)(param_1 + 0x270) = CSimpleMultiplex::vftable;
  local_8._0_1_ = 0xf;
  if (*(void **)(param_1 + 0x284) != (void *)0x0) {
    operator_delete(*(void **)(param_1 + 0x284));
  }
  *(undefined4 *)(param_1 + 0x284) = 0;
  *(undefined4 *)(param_1 + 0x280) = 0;
  *(undefined4 *)(param_1 + 0x288) = 0;
  *(undefined ***)(param_1 + 0x270) = BabyObject::vftable;
  local_8 = CONCAT31(local_8._1_3_,0xd);
  *(undefined ***)(param_1 + 0x260) = CPsiGenerator::vftable;
  if (*(undefined4 **)(param_1 + 0x264) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)(param_1 + 0x264))(1,uVar1);
  }
  if (*(undefined4 **)(param_1 + 0x268) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)(param_1 + 0x268))(1);
  }
  if (*(undefined4 **)(param_1 + 0x26c) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)(param_1 + 0x26c))(1);
  }
  local_8 = CONCAT31(local_8._1_3_,0xc);
  *(undefined ***)(param_1 + 0x24c) = CPrvsTSGenerator::vftable;
  if (*(undefined4 **)(param_1 + 0x250) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)(param_1 + 0x250))(1);
  }
  *(undefined4 *)(param_1 + 0x250) = 0;
  if (*(undefined4 **)(param_1 + 0x254) != (undefined4 *)0x0) {
    (**(code **)**(undefined4 **)(param_1 + 0x254))(1);
  }
  *(undefined4 *)(param_1 + 0x254) = 0;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x230));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x214));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x1f8));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x1a8));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x1a4));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x1a0));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x160));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x15c));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x158));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x154));
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)(param_1 + 0x150));
  local_8 = local_8 & 0xffffff00;
  CComboBox::~CComboBox((CComboBox *)(param_1 + 0x94));
  local_8 = 0xffffffff;
  CDialog::~CDialog(param_1);
  ExceptionList = local_10;
  return;
}



/* 10004940 _TS_Generate@68 */

void _TS_Generate_68(undefined *param_1,undefined4 param_2,int *param_3,int param_4,
                    undefined2 param_5,int param_6,undefined4 param_7,undefined4 param_8,
                    undefined4 param_9,ushort param_10,int *param_11,uint param_12,
                    undefined4 param_13,undefined4 param_14)

{
  int ***pppiVar1;
  int ***pppiVar2;
  FILE *pFVar3;
  int iVar4;
  char *pcVar5;
  int ****ppppiVar6;
  int ****ppppiVar7;
  errno_t eVar8;
  size_t sVar9;
  ushort uVar10;
  int iVar11;
  uint uVar12;
  code *pcVar13;
  int *piVar14;
  undefined4 *puVar15;
  int *piVar16;
  undefined4 *puVar17;
  bool bVar18;
  undefined4 auStack_598 [2];
  undefined4 local_564;
  int ***local_560;
  int ***local_55c;
  int ***pppiStack_558;
  FILE *pFStack_554;
  int *piStack_550;
  int *piStack_54c;
  int *local_548;
  FILE *pFStack_544;
  uint uStack_540;
  int local_53c;
  size_t sStack_538;
  int local_534;
  int local_530;
  int ***pppiStack_52c;
  int *piStack_528;
  int iStack_524;
  int iStack_520;
  int ***pppiStack_51c;
  FILE *pFStack_518;
  int ***apppiStack_514 [2];
  int ***apppiStack_50c [2];
  int ***pppiStack_504;
  int ***pppiStack_500;
  undefined4 local_4fc;
  int aiStack_4f8 [64];
  int iStack_3f8;
  undefined4 auStack_28a [10];
  char acStack_260 [128];
  char acStack_1e0 [208];
  char acStack_110 [260];
  uint local_c;
  
                    /* 0x4940  1  _TS_Generate@68 */
  local_c = DAT_1001409c ^ (uint)&local_564;
  local_4fc = param_2;
  local_548 = param_3;
  local_564 = 0;
  DAT_1006bcd0 = param_1;
  local_534 = 0;
  local_53c = 0;
  if (param_1 != (undefined *)0x0) {
    (*(code *)param_1)();
  }
  if ((param_3 != (int *)0x0) && (0 < param_4)) {
    local_560 = (int ***)&local_560;
    piStack_550 = (int *)0x0;
    piStack_54c = (int *)0x0;
    pFStack_544 = (FILE *)0x0;
    pFStack_554 = (FILE *)0x0;
    pppiStack_558 = (int ***)0x0;
    local_55c = local_560;
    iVar4 = FUN_10001010(param_14._2_2_,param_5,(short)param_14);
    if (iVar4 == 0) {
      auStack_598[1] = 0x10004a18;
      iVar4 = FUN_100015b0(param_14._2_2_,param_5,param_6 * 10000,(char)param_7,
                           (char)((uint)param_7 >> 0x10),(char)param_8,(char)((uint)param_8 >> 0x10)
                           ,(char)param_9);
      if (iVar4 == 0) {
        uVar12 = (param_12 & 0xffff0000) + 3;
        uStack_540 = uVar12;
        if (param_4 == 2) {
          pFStack_554 = (FILE *)(uint)param_9._2_2_;
          iVar4 = FUN_100012e0(param_9._2_2_,param_10,local_530);
          if (iVar4 != 0) {
            local_564 = 3;
            goto LAB_10005601;
          }
          iVar4 = FUN_1000c100(param_10,param_9._2_2_,&piStack_550);
          piVar14 = piStack_550;
          if (iVar4 != 0) {
            local_564 = 3;
            goto LAB_10005601;
          }
          iVar4 = FUN_10001340(param_9._2_2_,0x90,param_11,2,(int)piStack_550);
          piVar16 = piStack_528;
          if (iVar4 != 0) {
            local_564 = 3;
            goto LAB_10005601;
          }
          piStack_528 = piVar14;
          *piVar14 = (int)&pppiStack_52c;
          piVar14[1] = (int)piVar16;
          *piVar16 = (int)piVar14;
          iVar4 = FUN_10001430(param_9._2_2_,0x90,param_12,iStack_524);
          if (iVar4 != 0) {
            local_564 = 3;
            goto LAB_10005601;
          }
          iVar4 = FUN_10001700(param_11,iStack_520);
          if (iVar4 != 0) {
            local_564 = 3;
            goto LAB_10005601;
          }
          iVar4 = FUN_1000a7f0(param_9._2_2_,param_12,&pFStack_544);
          pFVar3 = pFStack_518;
          if (iVar4 != 0) {
            local_564 = 3;
            goto LAB_10005601;
          }
          pFStack_518 = pFStack_544;
          pFStack_544->_cnt = (int)pFVar3;
          pFStack_544->_ptr = (char *)&pppiStack_51c;
          pFVar3->_ptr = (char *)pFStack_544;
          piStack_54c = (int *)0x0;
          piStack_550 = local_548;
          do {
            piVar14 = piStack_550;
            piVar16 = aiStack_4f8;
            for (iVar4 = 0xa6; iVar4 != 0; iVar4 = iVar4 + -1) {
              *piVar16 = *piVar14;
              piVar14 = piVar14 + 1;
              piVar16 = piVar16 + 1;
            }
            pcVar5 = strstr((char *)aiStack_4f8,".upg");
            if (pcVar5 != (char *)0x0) {
              local_534 = 1;
              pcVar5 = strstr((char *)aiStack_4f8,"2K15_MediaSuite");
              if (pcVar5 != (char *)0x0) {
                local_534 = 0;
                local_53c = 1;
              }
            }
            iVar4 = local_53c;
            pFVar3 = pFStack_554;
            puVar15 = auStack_28a;
            puVar17 = auStack_598;
            for (iVar11 = 10; iVar11 != 0; iVar11 = iVar11 + -1) {
              *puVar17 = *puVar15;
              puVar15 = puVar15 + 1;
              puVar17 = puVar17 + 1;
            }
            iVar4 = FUN_10001960(&local_530,(char *)aiStack_4f8,(ushort)pFVar3,param_11,uVar12,
                                 (int)pFStack_544,iVar4);
            if (iVar4 != 0) goto LAB_100051fb;
            if (iStack_3f8 == 0) {
              iVar4 = FUN_10008bf0((ushort)pFStack_554,param_11,&pppiStack_558);
              pppiVar1 = pppiStack_500;
              if (iVar4 != 0) goto LAB_100051fb;
              pppiStack_500 = pppiStack_558;
              pppiStack_558[1] = (int **)pppiVar1;
              *pppiStack_558 = (int **)&pppiStack_504;
              *pppiVar1 = (int **)pppiStack_558;
              FUN_100020d0(local_548,(int)param_11,uVar12,(int)pppiStack_558);
            }
            piStack_550 = piStack_550 + 0xa6;
            piStack_54c = (int *)((int)piStack_54c + 1);
            uVar12 = uVar12 + 1;
          } while ((int)piStack_54c < 2);
        }
        else {
          sStack_538 = 0;
          if (0 < param_4) {
            do {
              piVar14 = local_548;
              piVar16 = aiStack_4f8;
              for (iVar4 = 0xa6; iVar4 != 0; iVar4 = iVar4 + -1) {
                *piVar16 = *piVar14;
                piVar14 = piVar14 + 1;
                piVar16 = piVar16 + 1;
              }
              uStack_540 = uVar12;
              pcVar5 = strstr((char *)aiStack_4f8,".upg");
              if (pcVar5 != (char *)0x0) {
                local_534 = 1;
                pcVar5 = strstr((char *)aiStack_4f8,"2K15_MediaSuite");
                if (pcVar5 != (char *)0x0) {
                  local_534 = 0;
                  pcVar5 = strstr((char *)aiStack_4f8,"021b");
                  local_53c = (pcVar5 != (char *)0x0) + 1;
                }
              }
              if (iStack_3f8 == 1) {
                if (piStack_550 == (int *)0x0) {
                  iVar4 = FUN_100012e0(param_9._2_2_,param_10,local_530);
                  if (((iVar4 != 0) ||
                      (iVar4 = FUN_1000c100(param_10,param_9._2_2_,&piStack_550),
                      piVar14 = piStack_550, iVar4 != 0)) ||
                     (iVar4 = FUN_10001340(param_9._2_2_,0x90,param_11,1,(int)piStack_550),
                     piVar16 = piStack_528, iVar4 != 0)) goto LAB_100051fb;
                  piStack_528 = piVar14;
                  *piVar14 = (int)&pppiStack_52c;
                  piVar14[1] = (int)piVar16;
                  *piVar16 = (int)piVar14;
                  iVar4 = FUN_10001430(param_9._2_2_,0x90,param_12,iStack_524);
                  if (((iVar4 != 0) || (iVar4 = FUN_10001700(param_11,iStack_520), iVar4 != 0)) ||
                     (iVar4 = FUN_1000a7f0(param_9._2_2_,param_12,&pFStack_544),
                     pFVar3 = pFStack_518, iVar4 != 0)) goto LAB_100051fb;
                  pFStack_518 = pFStack_544;
                  pFStack_544->_ptr = (char *)&pppiStack_51c;
                  pFStack_544->_cnt = (int)pFVar3;
                  pFVar3->_ptr = (char *)pFStack_544;
                }
                uVar12 = uStack_540;
                pFVar3 = pFStack_544;
                puVar15 = auStack_28a;
                puVar17 = auStack_598;
                for (iVar4 = 10; iVar4 != 0; iVar4 = iVar4 + -1) {
                  *puVar17 = *puVar15;
                  puVar15 = puVar15 + 1;
                  puVar17 = puVar17 + 1;
                }
                iVar4 = FUN_10001960(&local_530,(char *)aiStack_4f8,param_9._2_2_,param_11,uVar12,
                                     (int)pFVar3,local_53c);
                uVar12 = uStack_540;
                if (iVar4 != 0) goto LAB_100051fb;
              }
              else if (iStack_3f8 == 0) {
                uVar10 = param_9._2_2_ + 1;
                if (piStack_54c == (int *)0x0) {
                  iVar4 = FUN_100012e0(uVar10,param_10 + 1,local_530);
                  if (((iVar4 != 0) ||
                      (iVar4 = FUN_1000c100(param_10 + 1,uVar10,&piStack_54c), piVar14 = piStack_54c
                      , iVar4 != 0)) ||
                     (iVar4 = FUN_10001340(uVar10,0x91,param_11,2,(int)piStack_54c),
                     piVar16 = piStack_528, iVar4 != 0)) goto LAB_100051fb;
                  piStack_528 = piVar14;
                  *piVar14 = (int)&pppiStack_52c;
                  piVar14[1] = (int)piVar16;
                  *piVar16 = (int)piVar14;
                  iVar4 = FUN_10001430(uVar10,0x91,param_12,iStack_524);
                  if (((iVar4 != 0) || (iVar4 = FUN_10001700(param_11,iStack_520), iVar4 != 0)) ||
                     (iVar4 = FUN_1000a7f0(uVar10,param_12,&pFStack_554), pFVar3 = pFStack_518,
                     iVar4 != 0)) goto LAB_100051fb;
                  pFStack_518 = pFStack_554;
                  pFStack_554->_ptr = (char *)&pppiStack_51c;
                  pFStack_554->_cnt = (int)pFVar3;
                  pFVar3->_ptr = (char *)pFStack_554;
                  iVar4 = FUN_10008bf0(uVar10,param_11,&pppiStack_558);
                  pppiVar1 = pppiStack_500;
                  if (iVar4 != 0) goto LAB_100051fb;
                  pppiStack_500 = pppiStack_558;
                  *pppiStack_558 = (int **)&pppiStack_504;
                  pppiStack_558[1] = (int **)pppiVar1;
                  *pppiVar1 = (int **)pppiStack_558;
                }
                iVar4 = local_53c;
                uVar12 = uStack_540;
                puVar15 = auStack_28a;
                puVar17 = auStack_598;
                for (iVar11 = 10; iVar11 != 0; iVar11 = iVar11 + -1) {
                  *puVar17 = *puVar15;
                  puVar15 = puVar15 + 1;
                  puVar17 = puVar17 + 1;
                }
                iVar4 = FUN_10001960(&local_530,(char *)aiStack_4f8,uVar10,param_11,uVar12,
                                     (int)pFStack_554,iVar4);
                if (iVar4 != 0) goto LAB_100051fb;
                FUN_10001d50(pppiStack_558,(int)param_11,uStack_540,(char *)aiStack_4f8);
                uVar12 = uStack_540;
              }
              local_548 = local_548 + 0xa6;
              sStack_538 = sStack_538 + 1;
              uVar12 = uVar12 + 1;
              uStack_540 = uVar12;
            } while ((int)sStack_538 < param_4);
          }
        }
        pcVar13 = sprintf_s_exref;
        sprintf_s(acStack_260,0x80,"2k11es_pat.dat");
        FUN_10006db0(acStack_260,0,100,0xffffffff);
        pppiVar2 = pppiStack_558;
        iVar4 = FUN_10002b90(local_530,(char *)(pppiStack_558 + 2));
        pppiVar1 = local_55c;
        if (iVar4 == 0) {
          local_55c = pppiVar2;
          pppiVar2[1] = (int **)pppiVar1;
          *pppiVar2 = (int **)&local_560;
          *pppiVar1 = (int **)pppiVar2;
          ppppiVar7 = (int ****)pppiStack_52c;
          if ((int ****)pppiStack_52c != &pppiStack_52c) {
            do {
              sprintf_s(acStack_260,0x80,"2k11es_pmt_0x%x.dat");
              pppiVar1 = ppppiVar7[2];
              ppppiVar6 = malloc(0x94);
              pppiStack_558 = (int ***)ppppiVar6;
              if (ppppiVar6 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar6 + 2),0x80,acStack_260);
                ppppiVar6[0x22] = pppiVar1;
                ppppiVar6[0x23] = (int ***)0xffffffff;
                ppppiVar6[0x24] = (int ***)0x64;
              }
              iVar4 = FUN_10002c60(ppppiVar7,(char *)(ppppiVar6 + 2));
              if (iVar4 != 0) goto LAB_100051fb;
              *ppppiVar6 = (int ***)&local_560;
              ppppiVar6[1] = local_55c;
              *local_55c = (int **)ppppiVar6;
              ppppiVar7 = (int ****)*ppppiVar7;
              pcVar13 = sprintf_s_exref;
              local_55c = (int ***)ppppiVar6;
            } while (ppppiVar7 != &pppiStack_52c);
          }
          if (iStack_520 != 0) {
            (*pcVar13)();
            ppppiVar7 = malloc(0x94);
            pppiStack_558 = (int ***)ppppiVar7;
            if (ppppiVar7 != (int ****)0x0) {
              strcpy_s((char *)(ppppiVar7 + 2),0x80,acStack_260);
              ppppiVar7[0x22] = (int ***)0x10;
              ppppiVar7[0x23] = (int ***)0xffffffff;
              ppppiVar7[0x24] = (int ***)0x64;
            }
            iVar4 = FUN_10002cf0((char *)(ppppiVar7 + 2));
            if (iVar4 != 0) {
              local_564 = 3;
              goto LAB_10005601;
            }
            *ppppiVar7 = (int ***)&local_560;
            ppppiVar7[1] = local_55c;
            *local_55c = (int **)ppppiVar7;
            local_55c = (int ***)ppppiVar7;
          }
          if (iStack_524 != 0) {
            (*pcVar13)();
            FUN_10006db0(acStack_260,0x11,100,0xffffffff);
            pppiVar2 = pppiStack_558;
            iVar4 = FUN_10002d80(iStack_524,(char *)(pppiStack_558 + 2));
            pppiVar1 = local_55c;
            if (iVar4 != 0) {
LAB_100051fb:
              local_564 = 3;
              goto LAB_10005601;
            }
            local_55c = pppiVar2;
            *pppiVar2 = (int **)&local_560;
            pppiVar2[1] = (int **)pppiVar1;
            *pppiVar1 = (int **)pppiVar2;
          }
          ppppiVar7 = (int ****)pppiStack_51c;
          if ((int ****)pppiStack_51c != &pppiStack_51c) {
            do {
              sprintf_s(acStack_260,0x80,"2k11es_dsi_0x%x.dat");
              pppiVar1 = ppppiVar7[2];
              ppppiVar6 = malloc(0x94);
              if (ppppiVar6 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar6 + 2),0x80,acStack_260);
                ppppiVar6[0x22] = pppiVar1;
                ppppiVar6[0x23] = (int ***)0xffffffff;
                ppppiVar6[0x24] = (int ***)0x1f4;
              }
              iVar4 = FUN_10002e10(ppppiVar7,(char *)(ppppiVar6 + 2));
              if (iVar4 != 0) goto LAB_100051fb;
              ppppiVar6[1] = local_55c;
              *ppppiVar6 = (int ***)&local_560;
              *local_55c = (int **)ppppiVar6;
              ppppiVar7 = (int ****)*ppppiVar7;
              local_55c = (int ***)ppppiVar6;
            } while (ppppiVar7 != &pppiStack_51c);
          }
          ppppiVar7 = (int ****)apppiStack_514[0];
          if ((int ****)apppiStack_514[0] != apppiStack_514) {
            do {
              sprintf_s(acStack_260,0x80,"2k11es_dii_0x%x.dat");
              pppiVar1 = ppppiVar7[2];
              ppppiVar6 = malloc(0x94);
              if (ppppiVar6 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar6 + 2),0x80,acStack_260);
                ppppiVar6[0x22] = pppiVar1;
                ppppiVar6[0x23] = (int ***)0xffffffff;
                ppppiVar6[0x24] = (int ***)0x1f4;
              }
              iVar4 = FUN_10002ea0((int)ppppiVar7);
              sprintf(acStack_1e0,"generate_dii_section   %d\n");
              if (DAT_1005bc04 != 0) {
                fprintf((FILE *)DAT_1005bc04,"%s : %s");
              }
              if (iVar4 != 0) goto LAB_10005601;
              ppppiVar6[1] = local_55c;
              *ppppiVar6 = (int ***)&local_560;
              *local_55c = (int **)ppppiVar6;
              ppppiVar7 = (int ****)*ppppiVar7;
              local_55c = (int ***)ppppiVar6;
            } while (ppppiVar7 != apppiStack_514);
          }
          ppppiVar7 = (int ****)apppiStack_50c[0];
          if ((int ****)apppiStack_50c[0] != apppiStack_50c) {
            do {
              sprintf_s(acStack_260,0x80,"2k11es_ddb_0x%x.dat");
              pppiVar1 = ppppiVar7[2];
              ppppiVar6 = malloc(0x94);
              if (ppppiVar6 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar6 + 2),0x80,acStack_260);
                ppppiVar6[0x22] = pppiVar1;
                ppppiVar6[0x23] = (int ***)0x7d0;
                ppppiVar6[0x24] = (int ***)0xffffffff;
              }
              if (local_534 == 1) {
                iVar4 = FUN_10003380((char *)(ppppiVar6 + 2));
              }
              else if ((local_53c == 1) || (local_53c == 2)) {
                iVar4 = FUN_100032a0((char *)(ppppiVar6 + 2),local_53c);
              }
              else {
                iVar4 = FUN_10002f50((char *)(ppppiVar6 + 2));
              }
              if (iVar4 != 0) goto LAB_10005601;
              *ppppiVar6 = (int ***)&local_560;
              ppppiVar6[1] = local_55c;
              *local_55c = (int **)ppppiVar6;
              ppppiVar7 = (int ****)*ppppiVar7;
              local_55c = (int ***)ppppiVar6;
            } while (ppppiVar7 != apppiStack_50c);
          }
          ppppiVar7 = (int ****)pppiStack_504;
          if ((int ****)pppiStack_504 != &pppiStack_504) {
            do {
              sprintf_s(acStack_260,0x80,"2k11es_unt_0x%x.dat");
              pppiVar1 = ppppiVar7[2];
              ppppiVar6 = malloc(0x94);
              if (ppppiVar6 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar6 + 2),0x80,acStack_260);
                ppppiVar6[0x22] = pppiVar1;
                ppppiVar6[0x23] = (int ***)0xffffffff;
                ppppiVar6[0x24] = (int ***)0x1f4;
              }
              pFStack_554 = (FILE *)0x0;
              iVar4 = FUN_10008c90((int *)&piStack_54c);
              if (((iVar4 != 0) ||
                  (eVar8 = fopen_s(&pFStack_554,(char *)(ppppiVar6 + 2),"wb"), piVar14 = piStack_54c
                  , eVar8 != 0)) ||
                 (sVar9 = fwrite(piStack_54c,1,sStack_538,pFStack_554), sVar9 != sStack_538))
              goto LAB_100051fb;
              fclose(pFStack_554);
              free(piVar14);
              ppppiVar6[1] = local_55c;
              *ppppiVar6 = (int ***)&local_560;
              *local_55c = (int **)ppppiVar6;
              ppppiVar7 = (int ****)*ppppiVar7;
              local_55c = (int ***)ppppiVar6;
            } while (ppppiVar7 != &pppiStack_504);
          }
          FUN_10006c80(&local_560,"input_sections_2k11es.txt");
          sprintf(acStack_110,"%s %s %s");
          system(acStack_110);
        }
        else {
          local_564 = 3;
        }
      }
      else {
        local_564 = 3;
      }
    }
    else {
      local_564 = 3;
    }
  }
LAB_10005601:
  FUN_10001160();
  ppppiVar7 = (int ****)*local_560;
  ppppiVar6 = (int ****)local_560;
  if ((int ****)local_560 != &local_560) {
    do {
      free(ppppiVar6);
      bVar18 = ppppiVar7 != &local_560;
      ppppiVar6 = ppppiVar7;
      ppppiVar7 = (int ****)*ppppiVar7;
    } while (bVar18);
  }
  DAT_1006bcd0 = (undefined *)0x0;
  __security_check_cookie(local_c ^ (uint)&local_564);
  return;
}



/* 10005660 _TS_Generate_MultipleService@68 */

void _TS_Generate_MultipleService_68
               (undefined *param_1,undefined4 param_2,FILE *param_3,int param_4,undefined2 param_5,
               int param_6,undefined4 param_7,undefined4 param_8,undefined4 param_9,short param_10,
               int *param_11,uint param_12,undefined4 param_13,undefined4 param_14)

{
  int ***pppiVar1;
  int **ppiVar2;
  int ***pppiVar3;
  int *piVar4;
  int **ppiVar5;
  int *piVar6;
  int iVar7;
  int ****ppppiVar8;
  int ****ppppiVar9;
  errno_t eVar10;
  size_t sVar11;
  char cVar12;
  int iVar13;
  FILE *pFVar14;
  undefined4 *puVar15;
  char *pcVar16;
  undefined4 *puVar17;
  code *pcVar18;
  undefined4 auStack_4c0 [2];
  undefined1 uVar19;
  ushort uVar20;
  ushort uVar21;
  undefined1 auStack_48c [4];
  undefined4 local_488;
  int ***pppiStack_484;
  int ***pppiStack_480;
  int **ppiStack_47c;
  int ***pppiStack_478;
  FILE *pFStack_474;
  size_t sStack_470;
  int local_46c;
  int ***pppiStack_468;
  int *piStack_464;
  int iStack_460;
  int iStack_45c;
  int ***apppiStack_458 [2];
  int ***apppiStack_450 [2];
  int ***apppiStack_448 [2];
  int ***pppiStack_440;
  int **ppiStack_43c;
  int iStack_438;
  char cStack_434;
  undefined3 uStack_433;
  undefined4 local_430;
  int *piStack_42c;
  char acStack_428 [256];
  int iStack_328;
  undefined4 auStack_1ba [10];
  char acStack_190 [128];
  char acStack_110 [260];
  uint local_c;
  
                    /* 0x5660  2  _TS_Generate_MultipleService@68 */
  local_c = DAT_1001409c ^ (uint)auStack_48c;
  local_430 = param_2;
  local_488 = 0;
  DAT_1006bcd0 = param_1;
  if (param_1 != (undefined *)0x0) {
    (*(code *)param_1)();
  }
  if ((param_3 == (FILE *)0x0) || (param_4 < 1)) goto LAB_10005e70;
  iVar7 = FUN_10001010(param_14._2_2_,param_5,(short)param_14);
  if (iVar7 == 0) {
    auStack_4c0[1] = 0x10005701;
    iVar7 = FUN_100015b0(param_14._2_2_,param_5,param_6 * 10000,(char)param_7,
                         (char)((uint)param_7 >> 0x10),(char)param_8,(char)((uint)param_8 >> 0x10),
                         (char)param_9);
    if (iVar7 == 0) {
      pppiStack_478 = (int ***)0x0;
      if (0 < param_4) {
        pFStack_474 = param_3;
        do {
          pFVar14 = pFStack_474;
          pcVar16 = acStack_428;
          for (iVar7 = 0xa6; iVar7 != 0; iVar7 = iVar7 + -1) {
            *(char **)pcVar16 = pFVar14->_ptr;
            pFVar14 = (FILE *)&pFVar14->_cnt;
            pcVar16 = pcVar16 + 4;
          }
          uVar20 = param_9._2_2_ + (short)pppiStack_478;
          uVar21 = (short)pppiStack_478 + param_10;
          sStack_470 = CONCAT31(sStack_470._1_3_,(char)pppiStack_478 + -0x70);
          iVar7 = FUN_100012e0(uVar20,uVar21,local_46c);
          if ((iVar7 != 0) ||
             (iVar7 = FUN_1000c100(uVar21,uVar20,&piStack_42c), piVar6 = piStack_42c, iVar7 != 0))
          goto LAB_10005e1f;
          cVar12 = (iStack_328 == 0) + '\x01';
          _cStack_434 = CONCAT31(uStack_433,cVar12);
          uVar19 = (undefined1)sStack_470;
          iVar7 = FUN_10001340(uVar20,uVar19,param_11,cVar12,(int)piStack_42c);
          piVar4 = piStack_464;
          if (iVar7 != 0) goto LAB_10005e1f;
          piStack_464 = piVar6;
          *piVar6 = (int)&pppiStack_468;
          piVar6[1] = (int)piVar4;
          *piVar4 = (int)piVar6;
          iVar7 = FUN_10001430(uVar20,uVar19,param_12,iStack_460);
          if ((iVar7 != 0) || (iVar7 = FUN_10001700(param_11,iStack_45c), iVar7 != 0))
          goto LAB_10005e1f;
          iVar7 = (param_12 & 0xffff0000) + 3;
          puVar15 = auStack_1ba;
          puVar17 = auStack_4c0;
          iStack_438 = iVar7;
          for (iVar13 = 10; iVar13 != 0; iVar13 = iVar13 + -1) {
            *puVar17 = *puVar15;
            puVar15 = puVar15 + 1;
            puVar17 = puVar17 + 1;
          }
          iVar7 = FUN_10002540(&local_46c,acStack_428,uVar20,param_11,param_12,iVar7);
          if (iVar7 != 0) goto LAB_10005e1f;
          if (iStack_328 == 0) {
            iVar7 = FUN_10008bf0(uVar20,param_11,&ppiStack_47c);
            ppiVar2 = ppiStack_47c;
            if (iVar7 != 0) goto LAB_10005e1f;
            FUN_10002830((int)ppiStack_47c,
                         (short)CONCAT31((int3)((uint)iStack_438 >> 8),(undefined1)sStack_470),
                         param_11,iStack_438,acStack_428);
            ppiVar5 = ppiStack_43c;
            ppiStack_43c = ppiVar2;
            *ppiVar2 = (int *)&pppiStack_440;
            ppiVar2[1] = (int *)ppiVar5;
            *ppiVar5 = (int *)ppiVar2;
          }
          pFStack_474 = (FILE *)&pFStack_474[0x14]._bufsiz;
          pppiStack_478 = (int ***)((int)pppiStack_478 + 1);
        } while ((int)pppiStack_478 < param_4);
      }
      iVar7 = FUN_10006e10("*.dat");
      pcVar18 = sprintf_s_exref;
      if (iVar7 == 0) {
        pppiStack_484 = (int ***)&pppiStack_484;
        pppiStack_480 = pppiStack_484;
        sprintf_s(acStack_190,0x80,"pat.dat");
        FUN_10006db0(acStack_190,0,100,0xffffffff);
        pppiVar3 = pppiStack_478;
        iVar7 = FUN_10002b90(local_46c,(char *)(pppiStack_478 + 2));
        pppiVar1 = pppiStack_480;
        if (iVar7 == 0) {
          pppiStack_480 = pppiVar3;
          pppiVar3[1] = (int **)pppiVar1;
          *pppiVar3 = (int **)&pppiStack_484;
          *pppiVar1 = (int **)pppiVar3;
          ppppiVar9 = (int ****)pppiStack_468;
          if ((int ****)pppiStack_468 != &pppiStack_468) {
            do {
              sprintf_s(acStack_190,0x80,"pmt_0x%x.dat");
              ppiStack_47c = (int **)ppppiVar9[2];
              ppppiVar8 = malloc(0x94);
              pppiStack_478 = (int ***)ppppiVar8;
              if (ppppiVar8 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar8 + 2),0x80,acStack_190);
                ppppiVar8[0x22] = (int ***)ppiStack_47c;
                ppppiVar8[0x23] = (int ***)0xffffffff;
                ppppiVar8[0x24] = (int ***)0x64;
              }
              iVar7 = FUN_10002c60(ppppiVar9,(char *)(ppppiVar8 + 2));
              if (iVar7 != 0) goto LAB_10005e1f;
              ppppiVar8[1] = pppiStack_480;
              *ppppiVar8 = (int ***)&pppiStack_484;
              *pppiStack_480 = (int **)ppppiVar8;
              ppppiVar9 = (int ****)*ppppiVar9;
              pppiStack_480 = (int ***)ppppiVar8;
            } while (ppppiVar9 != &pppiStack_468);
          }
          if (iStack_45c != 0) {
            sprintf_s(acStack_190,0x80,"nit.dat");
            ppppiVar9 = malloc(0x94);
            pppiStack_478 = (int ***)ppppiVar9;
            if (ppppiVar9 != (int ****)0x0) {
              strcpy_s((char *)(ppppiVar9 + 2),0x80,acStack_190);
              ppppiVar9[0x22] = (int ***)0x10;
              ppppiVar9[0x23] = (int ***)0xffffffff;
              ppppiVar9[0x24] = (int ***)0x64;
            }
            iVar7 = FUN_10002cf0((char *)(ppppiVar9 + 2));
            if (iVar7 != 0) goto LAB_10005e1f;
            *ppppiVar9 = (int ***)&pppiStack_484;
            ppppiVar9[1] = pppiStack_480;
            *pppiStack_480 = (int **)ppppiVar9;
            pppiStack_480 = (int ***)ppppiVar9;
          }
          if (iStack_460 != 0) {
            sprintf_s(acStack_190,0x80,"sdt.dat");
            FUN_10006db0(acStack_190,0x11,100,0xffffffff);
            pppiVar3 = pppiStack_478;
            iVar7 = FUN_10002d80(iStack_460,(char *)(pppiStack_478 + 2));
            pppiVar1 = pppiStack_480;
            if (iVar7 != 0) goto LAB_10005e1f;
            pppiStack_480 = pppiVar3;
            *pppiVar3 = (int **)&pppiStack_484;
            pppiVar3[1] = (int **)pppiVar1;
            *pppiVar1 = (int **)pppiVar3;
          }
          ppppiVar9 = (int ****)apppiStack_458[0];
          if ((int ****)apppiStack_458[0] != apppiStack_458) {
            do {
              sprintf_s(acStack_190,0x80,"dsi_0x%x.dat");
              ppiStack_47c = (int **)ppppiVar9[2];
              ppppiVar8 = malloc(0x94);
              if (ppppiVar8 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar8 + 2),0x80,acStack_190);
                ppppiVar8[0x22] = (int ***)ppiStack_47c;
                ppppiVar8[0x23] = (int ***)0xffffffff;
                ppppiVar8[0x24] = (int ***)0x1f4;
              }
              iVar7 = FUN_10002e10(ppppiVar9,(char *)(ppppiVar8 + 2));
              if (iVar7 != 0) goto LAB_10005e1f;
              *ppppiVar8 = (int ***)&pppiStack_484;
              ppppiVar8[1] = pppiStack_480;
              *pppiStack_480 = (int **)ppppiVar8;
              ppppiVar9 = (int ****)*ppppiVar9;
              pppiStack_480 = (int ***)ppppiVar8;
            } while (ppppiVar9 != apppiStack_458);
          }
          ppppiVar9 = (int ****)apppiStack_450[0];
          if ((int ****)apppiStack_450[0] != apppiStack_450) {
            do {
              sprintf_s(acStack_190,0x80,"dii_0x%x.dat");
              ppiStack_47c = (int **)ppppiVar9[2];
              ppppiVar8 = malloc(0x94);
              if (ppppiVar8 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar8 + 2),0x80,acStack_190);
                ppppiVar8[0x22] = (int ***)ppiStack_47c;
                ppppiVar8[0x23] = (int ***)0xffffffff;
                ppppiVar8[0x24] = (int ***)0x1f4;
              }
              iVar7 = FUN_10002ea0((int)ppppiVar9);
              if (iVar7 != 0) goto LAB_10005e70;
              *ppppiVar8 = (int ***)&pppiStack_484;
              ppppiVar8[1] = pppiStack_480;
              *pppiStack_480 = (int **)ppppiVar8;
              ppppiVar9 = (int ****)*ppppiVar9;
              pppiStack_480 = (int ***)ppppiVar8;
            } while (ppppiVar9 != apppiStack_450);
          }
          ppppiVar9 = (int ****)apppiStack_448[0];
          if ((int ****)apppiStack_448[0] != apppiStack_448) {
            do {
              sprintf_s(acStack_190,0x80,"ddb_0x%x.dat");
              ppiStack_47c = (int **)ppppiVar9[2];
              ppppiVar8 = malloc(0x94);
              if (ppppiVar8 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar8 + 2),0x80,acStack_190);
                ppppiVar8[0x22] = (int ***)ppiStack_47c;
                ppppiVar8[0x23] = (int ***)0x7d0;
                ppppiVar8[0x24] = (int ***)0xffffffff;
              }
              iVar7 = FUN_10002f50((char *)(ppppiVar8 + 2));
              if (iVar7 != 0) goto LAB_10005e70;
              ppppiVar8[1] = pppiStack_480;
              *ppppiVar8 = (int ***)&pppiStack_484;
              *pppiStack_480 = (int **)ppppiVar8;
              ppppiVar9 = (int ****)*ppppiVar9;
              pppiStack_480 = (int ***)ppppiVar8;
            } while (ppppiVar9 != apppiStack_448);
          }
          ppppiVar9 = (int ****)pppiStack_440;
          if ((int ****)pppiStack_440 != &pppiStack_440) {
            do {
              (*pcVar18)();
              pppiVar1 = ppppiVar9[2];
              ppppiVar8 = malloc(0x94);
              if (ppppiVar8 != (int ****)0x0) {
                strcpy_s((char *)(ppppiVar8 + 2),0x80,acStack_190);
                ppppiVar8[0x22] = pppiVar1;
                ppppiVar8[0x23] = (int ***)0xffffffff;
                ppppiVar8[0x24] = (int ***)0x1f4;
              }
              pFStack_474 = (FILE *)0x0;
              iVar7 = FUN_10008c90((int *)&ppiStack_47c);
              if (((iVar7 != 0) ||
                  (eVar10 = fopen_s(&pFStack_474,(char *)(ppppiVar8 + 2),"wb"),
                  ppiVar2 = ppiStack_47c, eVar10 != 0)) ||
                 (sVar11 = fwrite(ppiStack_47c,1,sStack_470,pFStack_474), sVar11 != sStack_470))
              goto LAB_10005e1f;
              fclose(pFStack_474);
              free(ppiVar2);
              *ppppiVar8 = (int ***)&pppiStack_484;
              ppppiVar8[1] = pppiStack_480;
              *pppiStack_480 = (int **)ppppiVar8;
              ppppiVar9 = (int ****)*ppppiVar9;
              pcVar18 = sprintf_s_exref;
              pppiStack_480 = (int ***)ppppiVar8;
            } while (ppppiVar9 != &pppiStack_440);
          }
          FUN_10006c80(&pppiStack_484,"input_sections.txt");
          sprintf(acStack_110,"%s %s %s");
          system(acStack_110);
          goto LAB_10005e70;
        }
      }
    }
  }
LAB_10005e1f:
  local_488 = 3;
LAB_10005e70:
  DAT_1006bcd0 = (undefined *)0x0;
  __security_check_cookie(local_c ^ (uint)auStack_48c);
  return;
}



/* 10005ea0 _TS_Merge@68 */

void _TS_Merge_68(undefined *param_1,undefined4 param_2,char *param_3,int param_4,undefined2 param_5
                 ,int param_6,undefined4 param_7,undefined4 param_8,undefined1 param_9)

{
  undefined1 *puVar1;
  int ****ppppiVar2;
  int *piVar3;
  int iVar4;
  undefined1 *puVar5;
  undefined2 *puVar6;
  int iVar7;
  int iVar8;
  int *****pppppiVar9;
  errno_t eVar10;
  void *pvVar11;
  char *pcVar12;
  size_t sVar13;
  HANDLE hFindFile;
  DWORD DVar14;
  BOOL BVar15;
  undefined2 *extraout_ECX;
  undefined2 *extraout_ECX_00;
  undefined2 *extraout_ECX_01;
  undefined2 *extraout_ECX_02;
  undefined2 *puVar16;
  FILE *pFVar17;
  undefined4 *puVar18;
  undefined4 *puVar19;
  int *****pppppiVar20;
  int iVar21;
  size_t sVar22;
  bool bVar23;
  undefined2 in_stack_00000038;
  undefined2 in_stack_0000003a;
  char *pcVar24;
  undefined4 local_344;
  int ****local_340;
  int ****local_33c;
  FILE *pFStack_338;
  char *local_334;
  FILE *pFStack_330;
  void *pvStack_32c;
  int local_328;
  int *piStack_324;
  undefined4 *puStack_320;
  FILE *pFStack_31c;
  undefined4 *puStack_318;
  FILE *pFStack_314;
  void *pvStack_310;
  undefined4 local_30c;
  void *pvStack_308;
  size_t sStack_304;
  undefined1 auStack_300 [8];
  char *pcStack_2f8;
  int iStack_2f4;
  errno_t eStack_2f0;
  undefined4 uStack_2ec;
  ushort uStack_2e4;
  _WIN32_FIND_DATAA _Stack_2d0;
  char acStack_190 [128];
  char acStack_110 [260];
  uint local_c;
  
                    /* 0x5ea0  3  _TS_Merge@68 */
  local_c = DAT_1001409c ^ (uint)&local_344;
  local_30c = param_2;
  local_334 = param_3;
  local_328 = param_4;
  local_344 = 0;
  DAT_1006bcd0 = (code *)param_1;
  if (DAT_1005bc04 == 0) {
    DAT_1005bc04 = (int)fopen("tsutils_log.txt","w");
    if ((FILE *)DAT_1005bc04 == (FILE *)0x0) {
      pcVar24 = 
      "Error, no log file could not be created, check input parameters, check there is enough disk space\n"
      ;
    }
    else {
      pcVar24 = "Created default log file tsutils_log.txt in current directory\n";
    }
    printf(pcVar24);
    printf("Error in opening log file\n");
  }
  if (DAT_1006bcd0 != (code *)0x0) {
    (*DAT_1006bcd0)(5);
  }
  sVar22 = local_328 * 4;
  local_340 = (int ****)&local_340;
  local_33c = (int ****)&local_340;
  piVar3 = malloc(sVar22);
  piStack_324 = piVar3;
  if (piVar3 == (int *)0x0) {
    local_344 = 3;
    pppppiVar9 = &local_340;
    goto LAB_10006c31;
  }
  memset(piVar3,0,sVar22);
  iVar21 = 0;
  puVar16 = extraout_ECX;
  pcVar24 = local_334;
  if (0 < local_328) {
    do {
      iVar4 = FUN_1000c630(pcVar24);
      if (iVar4 != 0) goto LAB_1000615d;
      iVar21 = iVar21 + 1;
      puVar16 = extraout_ECX_00;
      piVar3 = piStack_324;
      pcVar24 = pcVar24 + 0x298;
    } while (iVar21 < local_328);
  }
  iVar21 = *piVar3;
  if (*(int *)(iVar21 + 0x14) == 0) {
    puVar5 = malloc(0x14);
    *(undefined1 **)(iVar21 + 0x14) = puVar5;
    if (puVar5 == (undefined1 *)0x0) {
      local_344 = 3;
    }
    else {
      *puVar5 = 1;
      *(undefined2 *)(puVar5 + 2) = in_stack_00000038;
      puVar1 = puVar5 + 4;
      *(undefined1 **)puVar1 = puVar1;
      *(undefined1 **)(puVar5 + 8) = puVar1;
      puVar1 = puVar5 + 0xc;
      *(undefined1 **)puVar1 = puVar1;
      *(undefined1 **)(puVar5 + 0x10) = puVar1;
      iVar4 = FUN_100015b0(in_stack_0000003a,param_5,param_6 * 10000,(char)param_7,
                           (char)((uint)param_7 >> 0x10),(char)param_8,(char)((uint)param_8 >> 0x10)
                           ,param_9);
      puVar16 = extraout_ECX_01;
      if (iVar4 == 0) goto LAB_10006037;
      local_344 = 3;
    }
  }
  else {
LAB_10006037:
    if (*(int *)(iVar21 + 0x18) == 0) {
      puVar6 = malloc(0x10);
      *(undefined2 **)(iVar21 + 0x18) = puVar6;
      if (puVar6 == (undefined2 *)0x0) {
        local_344 = 3;
        goto LAB_10006c00;
      }
      *puVar6 = in_stack_0000003a;
      *(undefined1 *)(puVar6 + 1) = 1;
      puVar16 = puVar6 + 4;
      puVar6[2] = param_5;
      *(undefined2 **)puVar16 = puVar16;
      *(undefined2 **)(puVar6 + 6) = puVar16;
    }
    iVar4 = 1;
    if (1 < local_328) {
      do {
        iVar8 = piStack_324[iVar4];
        iVar7 = FUN_1000dad0(puVar16,*(int *)(iVar8 + 8));
        if (((iVar7 != 0) || (iVar7 = FUN_1000db50(iVar21,iVar8), iVar7 != 0)) ||
           ((*(short **)(iVar8 + 0x18) != (short *)0x0 &&
            (iVar7 = FUN_1000dc50(*(short **)(iVar8 + 0x18),*(short **)(iVar21 + 0x18)), iVar7 != 0)
            ))) goto LAB_1000615d;
        puVar16 = (undefined2 *)0x0;
        if ((*(int *)(iVar8 + 0x14) != 0) &&
           (iVar8 = FUN_1000dd40(*(int *)(iVar8 + 0x14)), puVar16 = extraout_ECX_02, iVar8 != 0))
        goto LAB_1000615d;
        iVar4 = iVar4 + 1;
      } while (iVar4 < local_328);
    }
    sprintf_s(acStack_190,0x80,"pat.dat");
    pppppiVar9 = malloc(0x94);
    if (pppppiVar9 != (int *****)0x0) {
      strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
      pppppiVar9[0x22] = (int ****)0x0;
      pppppiVar9[0x23] = (int ****)0xffffffff;
      pppppiVar9[0x24] = (int ****)0x64;
    }
    iVar4 = FUN_10002b90(*(undefined4 *)(iVar21 + 8),(char *)(pppppiVar9 + 2));
    if (iVar4 == 0) {
      pppppiVar9[1] = local_33c;
      *pppppiVar9 = (int ****)&local_340;
      *local_33c = (int ***)pppppiVar9;
      local_33c = (int ****)pppppiVar9;
      pFVar17 = *(FILE **)(iVar21 + 0xc);
      pFStack_31c = (FILE *)(iVar21 + 0xc);
      if (pFVar17 != pFStack_31c) {
        do {
          sprintf_s(acStack_190,0x80,"pmt_0x%x.dat",pFVar17->_base);
          ppppiVar2 = (int ****)pFVar17->_base;
          pppppiVar9 = malloc(0x94);
          if (pppppiVar9 != (int *****)0x0) {
            strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
            pppppiVar9[0x22] = ppppiVar2;
            pppppiVar9[0x23] = (int ****)0xffffffff;
            pppppiVar9[0x24] = (int ****)0x64;
          }
          pFStack_338 = (FILE *)0x0;
          iVar21 = FUN_1000c480(&local_334,(size_t *)&pvStack_32c);
          if (((iVar21 != 0) ||
              (eVar10 = fopen_s(&pFStack_338,(char *)(pppppiVar9 + 2),"wb"), pcVar24 = local_334,
              eVar10 != 0)) ||
             (pvVar11 = (void *)fwrite(local_334,1,(size_t)pvStack_32c,pFStack_338),
             pvVar11 != pvStack_32c)) goto LAB_1000615d;
          fclose(pFStack_338);
          free(pcVar24);
          *pppppiVar9 = (int ****)&local_340;
          pppppiVar9[1] = local_33c;
          *local_33c = (int ***)pppppiVar9;
          pFVar17 = (FILE *)pFVar17->_ptr;
          local_33c = (int ****)pppppiVar9;
        } while (pFVar17 != pFStack_31c);
      }
      iVar21 = *piStack_324;
      piVar3 = *(int **)(*(int *)(iVar21 + 8) + 4);
      if (piVar3 != (int *)(*(int *)(iVar21 + 8) + 4)) {
        do {
          if ((short)piVar3[2] == 0) goto LAB_100062ef;
          piVar3 = (int *)*piVar3;
        } while (piVar3 != (int *)(*(int *)(iVar21 + 8) + 4));
      }
      FUN_1000a110(*(int *)(iVar21 + 0x14));
      free(*(void **)(iVar21 + 0x14));
      *(undefined4 *)(iVar21 + 0x14) = 0;
      FUN_1000aed0(*(int *)(iVar21 + 0x18));
      free(*(void **)(iVar21 + 0x18));
      *(undefined4 *)(iVar21 + 0x18) = 0;
LAB_100062ef:
      if (*(int *)(iVar21 + 0x14) != 0) {
        sprintf_s(acStack_190,0x80,"nit.dat");
        pppppiVar9 = malloc(0x94);
        if (pppppiVar9 != (int *****)0x0) {
          strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
          pppppiVar9[0x22] = (int ****)0x10;
          pppppiVar9[0x23] = (int ****)0xffffffff;
          pppppiVar9[0x24] = (int ****)0x64;
        }
        pFStack_338 = (FILE *)0x0;
        iVar4 = FUN_1000a1b0(&pvStack_32c,(size_t *)&local_334);
        if (((iVar4 != 0) ||
            (eVar10 = fopen_s(&pFStack_338,(char *)(pppppiVar9 + 2),"wb"), pcVar24 = local_334,
            eVar10 != 0)) ||
           (pcVar12 = (char *)fwrite(pvStack_32c,1,(size_t)local_334,pFStack_338),
           pcVar12 != pcVar24)) goto LAB_1000615d;
        fclose(pFStack_338);
        free(pvStack_32c);
        *pppppiVar9 = (int ****)&local_340;
        pppppiVar9[1] = local_33c;
        *local_33c = (int ***)pppppiVar9;
        local_33c = (int ****)pppppiVar9;
      }
      if (*(int *)(iVar21 + 0x18) != 0) {
        sprintf_s(acStack_190,0x80,"sdt.dat");
        pppppiVar9 = malloc(0x94);
        if (pppppiVar9 != (int *****)0x0) {
          strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
          pppppiVar9[0x22] = (int ****)0x11;
          pppppiVar9[0x23] = (int ****)0xffffffff;
          pppppiVar9[0x24] = (int ****)0x64;
        }
        pFStack_338 = (FILE *)0x0;
        iVar21 = FUN_1000b070(&pFStack_31c,(size_t *)&local_334);
        if (((iVar21 != 0) ||
            (eVar10 = fopen_s(&pFStack_338,(char *)(pppppiVar9 + 2),"wb"), pFVar17 = pFStack_31c,
            pcVar24 = local_334, eVar10 != 0)) ||
           (pcVar12 = (char *)fwrite(pFStack_31c,1,(size_t)local_334,pFStack_338),
           pcVar12 != pcVar24)) goto LAB_1000615d;
        fclose(pFStack_338);
        free(pFVar17);
        *pppppiVar9 = (int ****)&local_340;
        pppppiVar9[1] = local_33c;
        *local_33c = (int ***)pppppiVar9;
        local_33c = (int ****)pppppiVar9;
      }
      pvStack_32c = (void *)0x0;
      if (0 < local_328) {
        do {
          puVar19 = (undefined4 *)piStack_324[(int)pvStack_32c];
          puStack_318 = puVar19;
          fseek((FILE *)*puVar19,0,0);
          FUN_1000de10();
          puVar18 = (undefined4 *)puVar19[7];
          if (puVar18 != puVar19 + 7) {
            do {
              sprintf_s(acStack_190,0x80,"dsi_0x%x.dat",puVar18[2]);
              ppppiVar2 = (int ****)puVar18[2];
              pppppiVar9 = malloc(0x94);
              if (pppppiVar9 != (int *****)0x0) {
                strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
                pppppiVar9[0x22] = ppppiVar2;
                pppppiVar9[0x23] = (int ****)0xffffffff;
                pppppiVar9[0x24] = (int ****)0x1f4;
              }
              pFStack_330 = (FILE *)0x0;
              puStack_320 = (undefined4 *)0x0;
              FUN_1000ab60((int)auStack_300);
              sVar22 = uStack_2e4 + 3;
              pFStack_338 = malloc(sVar22);
              if (pFStack_338 == (FILE *)0x0) {
                printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
                puStack_320 = (undefined4 *)0x16;
              }
              else {
                memset(pFStack_338,0,sVar22);
                puStack_320 = (undefined4 *)
                              FUN_1000bc70((int)pFStack_338,sVar22,(uint *)&puStack_320,
                                           (uint)auStack_300,0x19,0x10015150);
                if (puStack_320 != (undefined4 *)0x0) {
                  printf("WriteToSection failed for DSI(%d)\n",puStack_320);
                }
              }
              FUN_1000adc0();
              if (((puStack_320 != (undefined4 *)0x0) ||
                  (eVar10 = fopen_s(&pFStack_330,(char *)(pppppiVar9 + 2),"wb"), eVar10 != 0)) ||
                 (sVar13 = fwrite(pFStack_338,1,sVar22,pFStack_330), sVar13 != sVar22))
              goto LAB_1000615d;
              fclose(pFStack_330);
              free(pFStack_338);
              pppppiVar9[1] = local_33c;
              *pppppiVar9 = (int ****)&local_340;
              *local_33c = (int ***)pppppiVar9;
              puVar18 = (undefined4 *)*puVar18;
              local_33c = (int ****)pppppiVar9;
            } while (puVar18 != puStack_318 + 7);
          }
          hFindFile = FindFirstFileA("dii_0x*.dat",&_Stack_2d0);
          if (hFindFile == (HANDLE)0xffffffff) {
            DVar14 = GetLastError();
            bVar23 = DVar14 == 2;
          }
          else {
            do {
              DeleteFileA(_Stack_2d0.cFileName);
              BVar15 = FindNextFileA(hFindFile,&_Stack_2d0);
            } while (BVar15 != 0);
            DVar14 = GetLastError();
            bVar23 = DVar14 == 0x12;
          }
          if ((!bVar23) && (DVar14 != 0)) goto LAB_1000615d;
          puVar19 = (undefined4 *)puStack_318[9];
          if (puVar19 != puStack_318 + 9) {
            do {
              sprintf_s(acStack_190,0x80,"dii_0x%x.dat",puVar19[2]);
              ppppiVar2 = (int ****)puVar19[2];
              pppppiVar9 = malloc(0x94);
              if (pppppiVar9 != (int *****)0x0) {
                strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
                pppppiVar9[0x22] = ppppiVar2;
                pppppiVar9[0x23] = (int ****)0xffffffff;
                pppppiVar9[0x24] = (int ****)0x1f4;
              }
              eVar10 = fopen_s(&pFStack_31c,acStack_190,"ab");
              pFVar17 = pFStack_31c;
              if (((eVar10 != 0) ||
                  (iVar21 = FUN_10009a40((int)puVar19,&pvStack_310,&sStack_304), sVar22 = sStack_304
                  , iVar21 != 0)) ||
                 (sVar13 = fwrite(pvStack_310,1,sStack_304,pFVar17), sVar13 != sVar22))
              goto LAB_1000615d;
              free(pvStack_310);
              fclose(pFStack_31c);
              pppppiVar20 = (int *****)local_340;
              if ((int *****)local_340 != &local_340) {
                do {
                  iVar21 = strncmp((char *)(pppppiVar20 + 2),(char *)(pppppiVar9 + 2),0x80);
                  if (iVar21 == 0) goto LAB_1000686c;
                  pppppiVar20 = (int *****)*pppppiVar20;
                } while (pppppiVar20 != &local_340);
              }
              *pppppiVar9 = (int ****)&local_340;
              pppppiVar9[1] = local_33c;
              *local_33c = (int ***)pppppiVar9;
              local_33c = (int ****)pppppiVar9;
LAB_1000686c:
              puVar19 = (undefined4 *)*puVar19;
            } while (puVar19 != puStack_318 + 9);
          }
          puVar19 = (undefined4 *)puStack_318[0xb];
          puStack_320 = puStack_318 + 0xb;
          if (puVar19 != puStack_320) {
            do {
              sprintf_s(acStack_190,0x80,"unt_0x%x.dat",puVar19[2]);
              ppppiVar2 = (int ****)puVar19[2];
              pppppiVar9 = malloc(0x94);
              if (pppppiVar9 != (int *****)0x0) {
                strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
                pppppiVar9[0x22] = ppppiVar2;
                pppppiVar9[0x23] = (int ****)0xffffffff;
                pppppiVar9[0x24] = (int ****)0x1f4;
              }
              pFStack_330 = (FILE *)0x0;
              iVar21 = FUN_10008c90((int *)&pvStack_308);
              if (((iVar21 != 0) ||
                  (eVar10 = fopen_s(&pFStack_330,(char *)(pppppiVar9 + 2),"wb"),
                  pvVar11 = pvStack_308, eVar10 != 0)) ||
                 (pcVar24 = (char *)fwrite(pvStack_308,1,(size_t)local_334,pFStack_330),
                 pcVar24 != local_334)) goto LAB_1000615d;
              fclose(pFStack_330);
              free(pvVar11);
              *pppppiVar9 = (int ****)&local_340;
              pppppiVar9[1] = local_33c;
              *local_33c = (int ***)pppppiVar9;
              puVar19 = (undefined4 *)*puVar19;
              local_33c = (int ****)pppppiVar9;
            } while (puVar19 != puStack_320);
          }
          pFVar17 = (FILE *)puStack_318[0xd];
          pFStack_330 = (FILE *)(puStack_318 + 0xd);
          if (pFVar17 != pFStack_330) {
            do {
              if (pFVar17->_flag == 2) {
                sprintf_s(acStack_190,0x80,"ddb_0x%x.dat",pFVar17->_base);
                ppppiVar2 = (int ****)pFVar17->_base;
                pppppiVar9 = malloc(0x94);
                if (pppppiVar9 != (int *****)0x0) {
                  strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
                  pppppiVar9[0x22] = ppppiVar2;
                  pppppiVar9[0x23] = (int ****)0x7d0;
                  pppppiVar9[0x24] = (int ****)0xffffffff;
                }
                pppppiVar20 = (int *****)local_340;
                if ((int *****)local_340 != &local_340) {
                  do {
                    iVar21 = strncmp((char *)(pppppiVar20 + 2),(char *)(pppppiVar9 + 2),0x80);
                    if (iVar21 == 0) goto LAB_10006b90;
                    pppppiVar20 = (int *****)*pppppiVar20;
                  } while (pppppiVar20 != &local_340);
                }
                pppppiVar9[1] = local_33c;
                *pppppiVar9 = (int ****)&local_340;
                *local_33c = (int ***)pppppiVar9;
                local_33c = (int ****)pppppiVar9;
                eVar10 = fopen_s(&pFStack_314,(char *)(pppppiVar9 + 2),"wb");
                if (eVar10 != 0) goto LAB_1000615d;
                pcStack_2f8 = pFVar17->_base;
                iStack_2f4 = pFVar17->_flag;
                uStack_2ec = 0xffffffff;
                eStack_2f0 = eVar10;
                iVar21 = FUN_1000c9e0();
                if (iVar21 != 0) goto LAB_1000615d;
                fclose(pFStack_314);
              }
              else if (pFVar17->_flag == 1) {
                sprintf_s(acStack_190,0x80,"pvtdata_0x%x.dat",pFVar17->_base);
                ppppiVar2 = (int ****)pFVar17->_base;
                pppppiVar9 = malloc(0x94);
                if (pppppiVar9 != (int *****)0x0) {
                  strcpy_s((char *)(pppppiVar9 + 2),0x80,acStack_190);
                  pppppiVar9[0x22] = ppppiVar2;
                  pppppiVar9[0x23] = (int ****)0x7d0;
                  pppppiVar9[0x24] = (int ****)0xffffffff;
                }
                eVar10 = fopen_s(&pFStack_314,(char *)(pppppiVar9 + 2),"wb");
                if ((eVar10 != 0) || (iVar21 = FUN_1000cb70(pFVar17,pFStack_314), iVar21 != 0))
                goto LAB_1000615d;
                fclose(pFStack_314);
                *pppppiVar9 = (int ****)&local_340;
                pppppiVar9[1] = local_33c;
                *local_33c = (int ***)pppppiVar9;
                local_33c = (int ****)pppppiVar9;
              }
LAB_10006b90:
              pFVar17 = (FILE *)pFVar17->_ptr;
            } while (pFVar17 != pFStack_330);
          }
          pvStack_32c = (void *)((int)pvStack_32c + 1);
        } while ((int)pvStack_32c < local_328);
      }
      FUN_10006c80(&local_340,"input_sections.txt");
      sprintf(acStack_110,"%s %s %s","TsGenUtil.exe",local_30c,"input_sections.txt");
      system(acStack_110);
      DAT_1006bcd0 = (code *)0x0;
    }
    else {
LAB_1000615d:
      local_344 = 3;
    }
  }
LAB_10006c00:
  iVar21 = 0;
  if (0 < local_328) {
    do {
      FUN_1000c880();
      iVar21 = iVar21 + 1;
    } while (iVar21 < local_328);
  }
  free(piStack_324);
  pppppiVar9 = (int *****)local_340;
LAB_10006c31:
  pppppiVar20 = (int *****)*pppppiVar9;
  if (pppppiVar9 != &local_340) {
    do {
      free(pppppiVar9);
      bVar23 = pppppiVar20 != &local_340;
      pppppiVar9 = pppppiVar20;
      pppppiVar20 = (int *****)*pppppiVar20;
    } while (bVar23);
  }
  __security_check_cookie(local_c ^ (uint)&local_344);
  return;
}



/* 10006c80 FUN_10006c80 */

void __thiscall FUN_10006c80(void *this,char *param_1)

{
  char cVar1;
  undefined4 *puVar2;
  int iVar3;
  errno_t eVar4;
  char *pcVar5;
  size_t sVar6;
  int iVar7;
  FILE *local_10c;
  char local_108 [256];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  eVar4 = fopen_s(&local_10c,param_1,"w");
  if (eVar4 == 0) {
    for (puVar2 = *(undefined4 **)this; puVar2 != this; puVar2 = (undefined4 *)*puVar2) {
      iVar3 = puVar2[0x24];
      iVar7 = iVar3;
      if (iVar3 == -1) {
        iVar7 = puVar2[0x23];
      }
      sprintf_s(local_108,0x100,"[0x%x, -%c %d, %s]\n",puVar2[0x22],(uint)(iVar3 == -1) * 2 + 0x70,
                iVar7,puVar2 + 2);
      pcVar5 = local_108;
      do {
        cVar1 = *pcVar5;
        pcVar5 = pcVar5 + 1;
      } while (cVar1 != '\0');
      sVar6 = fwrite(local_108,1,(int)pcVar5 - (int)(local_108 + 1),local_10c);
      if (sVar6 != (int)pcVar5 - (int)(local_108 + 1)) {
        __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
        return;
      }
    }
    fclose(local_10c);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 10006db0 FUN_10006db0 */

undefined4 __cdecl
FUN_10006db0(char *param_1,undefined4 param_2,undefined4 param_3,undefined4 param_4)

{
  int iVar1;
  void *pvVar2;
  int *unaff_ESI;
  
  pvVar2 = malloc(0x94);
  *unaff_ESI = (int)pvVar2;
  if (pvVar2 == (void *)0x0) {
    return 0xffffffff;
  }
  strcpy_s((char *)((int)pvVar2 + 8),0x80,param_1);
  iVar1 = *unaff_ESI;
  *(undefined4 *)(iVar1 + 0x88) = param_2;
  *(undefined4 *)(iVar1 + 0x8c) = param_4;
  *(undefined4 *)(iVar1 + 0x90) = param_3;
  return 0;
}



/* 10006e10 FUN_10006e10 */

void __cdecl FUN_10006e10(LPCSTR param_1)

{
  HANDLE hFindFile;
  BOOL BVar1;
  _WIN32_FIND_DATAA local_150;
  uint local_c;
  
  local_c = DAT_1001409c ^ (uint)&stack0xfffffffc;
  hFindFile = FindFirstFileA(param_1,&local_150);
  if (hFindFile == (HANDLE)0xffffffff) {
    GetLastError();
  }
  else {
    do {
      DeleteFileA(local_150.cFileName);
      BVar1 = FindNextFileA(hFindFile,&local_150);
    } while (BVar1 != 0);
    GetLastError();
  }
  __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
  return;
}



/* 10006e90 FUN_10006e90 */

void FUN_10006e90(void *param_1)

{
  operator_delete(param_1);
  return;
}



/* 10006eb0 FUN_10006eb0 */

void FUN_10006eb0(void)

{
  return;
}



/* 10006ec0 FUN_10006ec0 */

void FUN_10006ec0(void)

{
  return;
}



/* 10006ed0 FUN_10006ed0 */

void __fastcall FUN_10006ed0(int param_1)

{
  EnableWindow(*(HWND *)(param_1 + 0x20),0);
  return;
}



/* 10006ee0 FUN_10006ee0 */

void __fastcall FUN_10006ee0(int param_1)

{
  EnableWindow(*(HWND *)(param_1 + 0x20),1);
  return;
}



/* 10006ef0 FUN_10006ef0 */

void __thiscall FUN_10006ef0(void *this,ushort param_1,CWnd *param_2)

{
  CDialog::Create(this,(char *)(uint)param_1,param_2);
  return;
}



/* 10006f10 FUN_10006f10 */

CComboBox * __thiscall FUN_10006f10(void *this,byte param_1)

{
  CComboBox::~CComboBox(this);
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  return this;
}



/* 10006f40 FUN_10006f40 */

undefined4 * __thiscall FUN_10006f40(void *this,byte param_1)

{
  undefined4 *puVar1;
  uint uVar2;
  void *local_10;
  undefined1 *puStack_c;
  undefined4 local_8;
  
  puStack_c = &LAB_1000fca8;
  local_10 = ExceptionList;
  uVar2 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  ExceptionList = &local_10;
  *(undefined ***)this = Pat::vftable;
  local_8 = 0;
  puVar1 = *(undefined4 **)((int)this + 0x34);
  if (puVar1 != (undefined4 *)0x0) {
    if (puVar1[-1] == 0) {
      operator_delete__(puVar1 + -1);
    }
    else {
      (**(code **)*puVar1)(3,uVar2);
    }
  }
  local_8 = 0xffffffff;
  *(undefined ***)this = BabyObject::vftable;
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  ExceptionList = local_10;
  return this;
}



/* 10006fd0 FUN_10006fd0 */

undefined4 * __thiscall FUN_10006fd0(void *this,byte param_1)

{
  undefined4 *puVar1;
  uint uVar2;
  void *local_10;
  undefined1 *puStack_c;
  undefined4 local_8;
  
  puStack_c = &LAB_1000fca8;
  local_10 = ExceptionList;
  uVar2 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  ExceptionList = &local_10;
  *(undefined ***)this = Pmt::vftable;
  local_8 = 0;
  puVar1 = *(undefined4 **)((int)this + 0x3c);
  if (puVar1 != (undefined4 *)0x0) {
    if (puVar1[-1] == 0) {
      operator_delete__(puVar1 + -1);
    }
    else {
      (**(code **)*puVar1)(3,uVar2);
    }
  }
  local_8 = 0xffffffff;
  *(undefined ***)this = BabyObject::vftable;
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  ExceptionList = local_10;
  return this;
}



/* 10007060 FUN_10007060 */

void FUN_10007060(void)

{
  undefined4 *puVar1;
  void *pvVar2;
  undefined4 uVar3;
  undefined4 *unaff_ESI;
  
  *unaff_ESI = CPsiGenerator::vftable;
  puVar1 = operator_new(0x38);
  if (puVar1 == (undefined4 *)0x0) {
    puVar1 = (undefined4 *)0x0;
  }
  else {
    puVar1[1] = 0;
    puVar1[2] = 8;
    *(undefined1 *)(puVar1 + 3) = 0;
    puVar1[4] = 0;
    puVar1[5] = 0;
    puVar1[6] = 0;
    *puVar1 = Pat::vftable;
    *(undefined2 *)(puVar1 + 7) = 0x100;
    puVar1[8] = 0;
    puVar1[9] = 0;
    puVar1[10] = 0x100;
    puVar1[0xb] = 0;
    puVar1[0xc] = 0;
    puVar1[0xd] = 0;
  }
  unaff_ESI[1] = puVar1;
  puVar1 = operator_new(0x40);
  if (puVar1 == (undefined4 *)0x0) {
    puVar1 = (undefined4 *)0x0;
  }
  else {
    puVar1[1] = 0;
    puVar1[2] = 8;
    *(undefined1 *)(puVar1 + 3) = 0;
    puVar1[4] = 0;
    puVar1[5] = 0;
    puVar1[6] = 0;
    *puVar1 = Pmt::vftable;
    *(undefined2 *)(puVar1 + 7) = 0x100;
    puVar1[8] = 0;
    puVar1[9] = 0;
    puVar1[10] = 0x100;
    puVar1[0xb] = 0;
    puVar1[0xc] = 0;
    puVar1[0xd] = 0;
    puVar1[0xe] = 0;
    puVar1[0xf] = 0;
  }
  unaff_ESI[2] = puVar1;
  pvVar2 = operator_new(0x60);
  if (pvVar2 != (void *)0x0) {
    uVar3 = FUN_10004320();
    unaff_ESI[3] = uVar3;
    return;
  }
  unaff_ESI[3] = 0;
  return;
}



/* 10007130 FUN_10007130 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void FUN_10007130(void)

{
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> local_140 [300];
  uint local_14;
  void *local_10;
  undefined1 *puStack_c;
  undefined4 local_8;
  
  local_8 = 0xffffffff;
  puStack_c = &LAB_1000ff0b;
  local_10 = ExceptionList;
  local_14 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  ExceptionList = &local_10;
  CDialog::CDialog((CDialog *)&DAT_1006bd10,0x65,(CWnd *)0x0);
  local_8 = 0;
  _DAT_1006bd10 = TSGeneDlg::vftable;
  CWnd::CWnd((CWnd *)&DAT_1006bda4);
  _DAT_1006bda4 = CComboBox::vftable;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be60);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be64);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be68);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be6c);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be70);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006beb0);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006beb4);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006beb8);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bf08);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bf24);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bf40);
  local_8._0_1_ = 0xc;
  FUN_10004390();
  local_8._0_1_ = 0xd;
  FUN_10007060();
  local_8._0_1_ = 0xe;
  FUN_100044b0((undefined4 *)&DAT_1006bf80);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bfe8);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bfec);
  local_8 = CONCAT31(local_8._1_3_,0x11);
  _DAT_1006bff4 = 0;
  _DAT_1006bff8 = 1;
  _DAT_1006bfe4 = 4;
  _DAT_1006bfb8 = 0;
  _DAT_1006bfbc = 0;
  _DAT_1006bfb4 = 0;
  _DAT_1006bfc0 = 0;
  _DAT_1006bfc4 = 0;
  _DAT_1006bfc8 = 0;
  _DAT_1006bfcc = 0;
  _DAT_1006bfd0 = 0;
  _DAT_1006bfd4 = 0;
  _DAT_1006bfd8 = 0;
  _DAT_1006bfdc = 0;
  GetCurrentDirectoryA(300,(LPSTR)local_140);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::Format
            (local_140,&DAT_1006bfe8);
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bfec,
             "RFClone_UpdateImage.trp");
  _DAT_1006be18 = 0x127e88f;
  _DAT_1006be1c = 1;
  _DAT_1006be20 = 1;
  _DAT_1006be24 = 2;
  _DAT_1006be28 = 10;
  _DAT_1006be2c = 2;
  _DAT_1006be30 = 2;
  _DAT_1006be34 = 0;
  _DAT_1006be38 = 0x1eed;
  _DAT_1006be3c = 0x1eef;
  _DAT_1006be40 = 0x1ff0;
  _DAT_1006be44 = 0x1ff1;
  _DAT_1006be48 = 0x1fee;
  _DAT_1006be4c = 0xe9;
  _DAT_1006be50 = 0xed;
  _DAT_1006be54 = 0xfc;
  _DAT_1006be58 = 0xfe;
  _DAT_1006be5c = 0xfb;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be60,"");
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be64,"");
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be68,"");
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be6c,"");
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006be70,"");
  _DAT_1006be74 = 0;
  _DAT_1006be78 = 0;
  _DAT_1006be7c = 0;
  _DAT_1006be80 = 0;
  _DAT_1006be84 = 0;
  _DAT_1006be88 = 1;
  _DAT_1006be8c = 1;
  _DAT_1006be90 = 1;
  _DAT_1006be94 = 1;
  _DAT_1006be98 = 1;
  _DAT_1006be9c = 0;
  _DAT_1006bea0 = 0;
  _DAT_1006bea4 = 0;
  _DAT_1006bea8 = 0;
  _DAT_1006beac = 0;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006beb0,"");
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006beb4,"");
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006beb8,"");
  _DAT_1006bebc = 0;
  _DAT_1006bec0 = 0;
  _DAT_1006bec4 = 0;
  _DAT_1006bec8 = 0;
  _DAT_1006becc = 0;
  _DAT_1006bed0 = 0;
  _DAT_1006bed4 = 0;
  _DAT_1006bed8 = 0;
  _DAT_1006bedc = 0;
  _DAT_1006bee0 = 0x1ee5;
  _DAT_1006bee4 = 0x1ee4;
  _DAT_1006bee8 = 0x1ee3;
  _DAT_1006beec = 0xe8;
  _DAT_1006bef0 = 0xe7;
  _DAT_1006bef4 = 0xe6;
  _DAT_1006bef8 = 0;
  _DAT_1006befc = 0;
  _DAT_1006bf00 = 0;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bf08,"");
  _DAT_1006bf0c = 0x1fe6;
  _DAT_1006bf10 = 0xf0;
  _DAT_1006bf14 = 0;
  _DAT_1006bf18 = 0;
  _DAT_1006bf1c = 0;
  _DAT_1006bf20 = 0;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bf24,"");
  _DAT_1006bf28 = 0x1ef2;
  _DAT_1006bf2c = 0;
  _DAT_1006bf30 = 1;
  _DAT_1006bf34 = 0xea;
  _DAT_1006bf38 = 0;
  _DAT_1006bf3c = 0;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bf40,"");
  _DAT_1006bf44 = 0;
  _DAT_1006bf48 = 0xf1;
  _DAT_1006bf4c = 0;
  _DAT_1006bf50 = 0;
  _DAT_1006bf54 = 0x81;
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::operator=
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bf24,"");
  _DAT_1006bf30 = 0;
  _DAT_1006bf58 = 0;
  ExceptionList = local_10;
  __security_check_cookie(local_14 ^ (uint)&stack0xfffffffc);
  return;
}



/* 100075e0 FUN_100075e0 */

CDialog * __thiscall FUN_100075e0(void *this,byte param_1)

{
  FUN_10004780(this);
  if ((param_1 & 1) != 0) {
    operator_delete(this);
  }
  return this;
}



/* 10007610 FUN_10007610 */

void __thiscall FUN_10007610(void *this,CDataExchange *param_1)

{
  DDX_Control(param_1,0x40a,(CWnd *)((int)this + 0x94));
  DDX_Text(param_1,0x3ea,(uint *)((int)this + 0x108));
  DDX_Check(param_1,0x412,(int *)((int)this + 0x10c));
  DDX_Check(param_1,0x411,(int *)((int)this + 0x110));
  DDX_Text(param_1,0x3eb,(uint *)((int)this + 0x114));
  DDX_Text(param_1,0x3fb,(uint *)((int)this + 0x118));
  DDX_Text(param_1,0x403,(uint *)((int)this + 0x11c));
  DDX_Text(param_1,0x422,(uint *)((int)this + 0x120));
  DDX_Text(param_1,0x42a,(uint *)((int)this + 0x124));
  DDX_Text(param_1,0x3ee,(uint *)((int)this + 0x128));
  DDX_Text(param_1,0x3fe,(uint *)((int)this + 300));
  DDX_Text(param_1,0x406,(uint *)((int)this + 0x130));
  DDX_Text(param_1,0x42d,(uint *)((int)this + 0x134));
  DDX_Text(param_1,0x425,(uint *)((int)this + 0x138));
  DDX_Text(param_1,0x3ed,(uint *)((int)this + 0x13c));
  DDX_Text(param_1,0x3fd,(uint *)((int)this + 0x140));
  DDX_Text(param_1,0x405,(uint *)((int)this + 0x144));
  DDX_Text(param_1,0x424,(uint *)((int)this + 0x148));
  DDX_Text(param_1,0x42c,(uint *)((int)this + 0x14c));
  DDX_Text(param_1,0x3f6,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x150));
  DDX_Text(param_1,0x401,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x154));
  DDX_Text(param_1,0x408,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x158));
  DDX_Text(param_1,0x42f,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x15c));
  DDX_Text(param_1,0x427,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x160));
  DDX_Text(param_1,0x3f9,(uint *)((int)this + 0x164));
  DDX_Text(param_1,0x402,(uint *)((int)this + 0x168));
  DDX_Text(param_1,0x409,(uint *)((int)this + 0x16c));
  DDX_Text(param_1,0x428,(uint *)((int)this + 0x170));
  DDX_Text(param_1,0x430,(uint *)((int)this + 0x174));
  DDX_Text(param_1,0x40b,(uint *)((int)this + 0x178));
  DDX_Text(param_1,0x40d,(uint *)((int)this + 0x17c));
  DDX_Text(param_1,0x431,(uint *)((int)this + 0x180));
  DDX_Text(param_1,0x429,(uint *)((int)this + 0x184));
  DDX_Text(param_1,0x40e,(uint *)((int)this + 0x188));
  DDX_Text(param_1,0x3ef,(uint *)((int)this + 0x18c));
  DDX_Text(param_1,0x407,(uint *)((int)this + 0x194));
  DDX_Text(param_1,0x426,(uint *)((int)this + 0x198));
  DDX_Text(param_1,0x42e,(uint *)((int)this + 0x19c));
  DDX_Text(param_1,0x3f8,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x1a0));
  DDX_Text(param_1,0x417,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x1a4));
  DDX_Text(param_1,0x41e,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x1a8));
  DDX_Text(param_1,0x3ec,(uint *)((int)this + 0x1ac));
  DDX_Text(param_1,0x404,(uint *)((int)this + 0x1b0));
  DDX_Text(param_1,0x41a,(uint *)((int)this + 0x1b4));
  DDX_Text(param_1,0x3fc,(uint *)((int)this + 0x1b8));
  DDX_Text(param_1,0x418,(uint *)((int)this + 0x1bc));
  DDX_Text(param_1,0x41f,(uint *)((int)this + 0x1c0));
  DDX_Text(param_1,0x40f,(uint *)((int)this + 0x1c4));
  DDX_Text(param_1,0x419,(uint *)((int)this + 0x1c8));
  DDX_Text(param_1,0x420,(uint *)((int)this + 0x1cc));
  DDX_Text(param_1,0x3f2,(uint *)((int)this + 0x1d0));
  DDX_Text(param_1,0x414,(uint *)((int)this + 0x1d4));
  DDX_Text(param_1,0x41c,(uint *)((int)this + 0x1d8));
  DDX_Text(param_1,0x3f0,(uint *)((int)this + 0x1dc));
  DDX_Text(param_1,0x410,(uint *)((int)this + 0x1e0));
  DDX_Text(param_1,0x41b,(uint *)((int)this + 0x1e4));
  DDX_Text(param_1,0x3f7,(uint *)((int)this + 0x1e8));
  DDX_Text(param_1,0x416,(uint *)((int)this + 0x1ec));
  DDX_Text(param_1,0x41d,(uint *)((int)this + 0x1f0));
  DDX_Text(param_1,0x421,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x1f8));
  DDX_Text(param_1,0x423,(uint *)((int)this + 0x1fc));
  DDX_Text(param_1,0x42b,(uint *)((int)this + 0x200));
  DDX_Text(param_1,0x432,(uint *)((int)this + 0x204));
  DDX_Text(param_1,0x434,(uint *)((int)this + 0x208));
  DDX_Text(param_1,0x435,(uint *)((int)this + 0x20c));
  DDX_Text(param_1,0x3f1,(uint *)((int)this + 0x210));
  DDX_Text(param_1,0x400,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x214));
  DDX_Text(param_1,0x3f4,(uint *)((int)this + 0x218));
  DDX_Text(param_1,0x3fa,(uint *)((int)this + 0x21c));
  DDX_Text(param_1,0x40c,(uint *)((int)this + 0x220));
  DDX_Text(param_1,0x3f3,(uint *)((int)this + 0x224));
  DDX_Text(param_1,0x3f5,(uint *)((int)this + 0x228));
  DDX_Text(param_1,0x3ff,(uint *)((int)this + 0x22c));
  DDX_Text(param_1,0x436,
           (CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)((int)this + 0x230));
  DDX_Text(param_1,0x43a,(uint *)((int)this + 0x234));
  DDX_Text(param_1,0x437,(uint *)((int)this + 0x238));
  DDX_Text(param_1,0x43b,(uint *)((int)this + 0x23c));
  DDX_Text(param_1,0x43c,(uint *)((int)this + 0x240));
  DDX_Text(param_1,0x438,(uint *)((int)this + 0x244));
  DDX_Text(param_1,0x439,(uint *)((int)this + 0x248));
  return;
}



/* 10007b10 FUN_10007b10 */

undefined ** FUN_10007b10(void)

{
  return &PTR_LAB_10011c38;
}



/* 10007b20 FUN_10007b20 */

undefined4 __fastcall FUN_10007b20(CDialog *param_1)

{
  int iVar1;
  CDialog *local_8;
  
  local_8 = param_1;
  CDialog::OnInitDialog(param_1);
  CWnd::UpdateData((CWnd *)param_1,1);
  iVar1 = 0;
  do {
    sprintf((char *)&local_8,"%d",iVar1 + 4);
    SendMessageA(*(HWND *)(param_1 + 0xb4),0x143,0,(LPARAM)&local_8);
    iVar1 = iVar1 + 1;
  } while (iVar1 < 8);
  SendMessageA(*(HWND *)(param_1 + 0xb4),0x14e,0,0);
  iVar1 = *(int *)(param_1 + 0x2ec);
  *(undefined4 *)(param_1 + 0x2e0) = 0;
  if (((iVar1 == 0) || (iVar1 == 1)) || (iVar1 == 2)) {
    *(undefined4 *)(param_1 + 0x108) = 0x127e88f;
  }
  CWnd::UpdateData((CWnd *)param_1,0);
  return 1;
}



/* 10008040 FUN_10008040 */

void __fastcall FUN_10008040(CWnd *param_1)

{
  *(undefined4 *)(param_1 + 0x2e0) = 1;
  CWnd::UpdateData(param_1,1);
                    /* WARNING: Could not recover jumptable at 0x10008058. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  CDialog::OnOK((CDialog *)param_1);
  return;
}



/* 10008060 OnCancel */

void __thiscall CDialog::OnCancel(CDialog *this)

{
                    /* WARNING: Could not recover jumptable at 0x10008060. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  OnCancel(this);
  return;
}



/* 10008070 FUN_10008070 */

undefined4 __cdecl FUN_10008070(undefined1 *param_1,undefined4 *param_2)

{
  undefined4 *_Memory;
  undefined1 *puVar1;
  
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (undefined4 *)0x0) {
    return 0x16;
  }
  *_Memory = 0;
  _Memory[1] = 0;
  _Memory[2] = 0;
  *(undefined2 *)(_Memory + 2) = 0x152;
  puVar1 = malloc(1);
  _Memory[3] = puVar1;
  if (puVar1 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar1 = *param_1;
  *_Memory = _Memory;
  _Memory[1] = _Memory;
  return 0;
}



/* 100080e0 FUN_100080e0 */

undefined4 __cdecl FUN_100080e0(short *param_1,undefined4 *param_2)

{
  short sVar1;
  short *psVar2;
  void *_Memory;
  void *pvVar3;
  int iVar4;
  
  iVar4 = 0;
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined4 *)((int)_Memory + 8) = 0;
  *(undefined4 *)((int)_Memory + 0xc) = 0;
  sVar1 = *param_1;
  *(undefined1 *)((int)_Memory + 8) = 0x66;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  if (sVar1 == 10) {
    for (psVar2 = *(short **)(param_1 + 2); psVar2 != param_1 + 2; psVar2 = *(short **)psVar2) {
      iVar4 = iVar4 + 6;
    }
  }
  *(char *)((int)_Memory + 9) = (char)(iVar4 + 3U);
  pvVar3 = malloc(iVar4 + 3U);
  *(void **)((int)_Memory + 0xc) = pvVar3;
  if (pvVar3 == (void *)0x0) {
    free(_Memory);
    return 0x16;
  }
  **(undefined1 **)((int)_Memory + 0xc) = (char)((ushort)*param_1 >> 8);
  *(char *)(*(int *)((int)_Memory + 0xc) + 1) = (char)*param_1;
  sVar1 = *param_1;
  *(char *)(*(int *)((int)_Memory + 0xc) + 2) = (char)iVar4;
  iVar4 = 3;
  if (sVar1 == 10) {
    for (psVar2 = *(short **)(param_1 + 2); psVar2 != param_1 + 2; psVar2 = *(short **)psVar2) {
      *(char *)(iVar4 + *(int *)((int)_Memory + 0xc)) = (char)psVar2[5];
      *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 1 + iVar4) = *(undefined1 *)((int)psVar2 + 9);
      *(char *)(*(int *)((int)_Memory + 0xc) + 2 + iVar4) = (char)psVar2[4];
      *(byte *)(iVar4 + 3 + *(int *)((int)_Memory + 0xc)) = *(byte *)(psVar2 + 6) | 0xf0;
      *(byte *)(iVar4 + 4 + *(int *)((int)_Memory + 0xc)) =
           *(byte *)((int)psVar2 + 0xd) & 0xfe | *(byte *)(psVar2 + 7) & 1 | 0xc0;
      iVar4 = iVar4 + 5;
      *(undefined1 *)(iVar4 + *(int *)((int)_Memory + 0xc)) = 0;
    }
  }
  return 0;
}



/* 10008200 FUN_10008200 */

undefined4 __cdecl FUN_10008200(char *param_1,undefined4 *param_2)

{
  char cVar1;
  undefined4 *_Memory;
  char *pcVar2;
  size_t _Size;
  void *_Dst;
  
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (undefined4 *)0x0) {
    return 0x16;
  }
  *_Memory = 0;
  _Memory[1] = 0;
  _Memory[2] = 0;
  _Memory[3] = 0;
  *(undefined1 *)(_Memory + 2) = 2;
  pcVar2 = param_1;
  do {
    cVar1 = *pcVar2;
    pcVar2 = pcVar2 + 1;
  } while (cVar1 != '\0');
  _Size = (int)pcVar2 - (int)(param_1 + 1);
  _Dst = malloc(_Size);
  _Memory[3] = _Dst;
  if (_Dst == (void *)0x0) {
    free(_Memory);
    return 0x16;
  }
  memcpy(_Dst,param_1,_Size);
  *(char *)((int)_Memory + 9) = (char)_Size;
  *_Memory = _Memory;
  _Memory[1] = _Memory;
  return 0;
}



/* 10008290 FUN_10008290 */

undefined4 __cdecl FUN_10008290(undefined4 *param_1)

{
  void *_Memory;
  undefined1 *puVar1;
  undefined1 *unaff_EBX;
  
  _Memory = malloc(0x10);
  *param_1 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 4;
  puVar1 = malloc(3);
  *(undefined1 **)((int)_Memory + 0xc) = puVar1;
  if (puVar1 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar1 = *unaff_EBX;
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 1) = unaff_EBX[3];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 2) = unaff_EBX[2];
  *(undefined1 *)((int)_Memory + 9) = 3;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  return 0;
}



/* 10008300 FUN_10008300 */

undefined4 __cdecl FUN_10008300(undefined4 *param_1)

{
  undefined1 *in_EAX;
  void *_Memory;
  undefined1 *puVar1;
  
  _Memory = malloc(0x10);
  *param_1 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 5;
  puVar1 = malloc(4);
  *(undefined1 **)((int)_Memory + 0xc) = puVar1;
  if (puVar1 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar1 = in_EAX[3];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 1) = in_EAX[2];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 2) = in_EAX[1];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 3) = *in_EAX;
  *(undefined1 *)((int)_Memory + 9) = 4;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  return 0;
}



/* 10008390 FUN_10008390 */

undefined4 __cdecl FUN_10008390(undefined1 *param_1,undefined4 *param_2)

{
  void *_Memory;
  undefined1 *puVar1;
  
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 10;
  puVar1 = malloc(1);
  *(undefined1 **)((int)_Memory + 0xc) = puVar1;
  if (puVar1 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar1 = *param_1;
  *(undefined1 *)((int)_Memory + 9) = 1;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  return 0;
}



/* 10008400 FUN_10008400 */

undefined4 __cdecl FUN_10008400(undefined1 *param_1,undefined4 *param_2)

{
  size_t _Size;
  char cVar1;
  void *_Memory;
  char *pcVar2;
  size_t _Size_00;
  size_t _Size_01;
  undefined1 *puVar3;
  undefined1 local_c;
  
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 0x48;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  pcVar2 = param_1 + 0x101;
  do {
    cVar1 = *pcVar2;
    pcVar2 = pcVar2 + 1;
  } while (cVar1 != '\0');
  _Size_00 = (int)pcVar2 - (int)(param_1 + 0x102);
  pcVar2 = param_1 + 1;
  do {
    cVar1 = *pcVar2;
    pcVar2 = pcVar2 + 1;
  } while (cVar1 != '\0');
  _Size_01 = (int)pcVar2 - (int)(param_1 + 2);
  _Size = _Size_01 + 3 + _Size_00;
  puVar3 = malloc(_Size);
  *(undefined1 **)((int)_Memory + 0xc) = puVar3;
  if (puVar3 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar3 = *param_1;
  *(char *)(*(int *)((int)_Memory + 0xc) + 1) = (char)_Size_01;
  memcpy((void *)(*(int *)((int)_Memory + 0xc) + 2),param_1 + 1,_Size_01);
  *(char *)(_Size_01 + 2 + *(int *)((int)_Memory + 0xc)) = (char)_Size_00;
  memcpy((void *)(*(int *)((int)_Memory + 0xc) + 3 + _Size_01),param_1 + 0x101,_Size_00);
  local_c = (undefined1)_Size;
  *(undefined1 *)((int)_Memory + 9) = local_c;
  return 0;
}



/* 100084e0 FUN_100084e0 */

undefined4 __cdecl FUN_100084e0(undefined4 *param_1)

{
  char cVar1;
  byte bVar2;
  undefined4 uVar3;
  undefined1 *in_EAX;
  void *_Memory;
  char *pcVar4;
  size_t _Size;
  undefined1 *puVar5;
  undefined1 local_8;
  
  _Memory = malloc(0x10);
  *param_1 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 100;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  pcVar4 = in_EAX + 0x18;
  do {
    cVar1 = *pcVar4;
    pcVar4 = pcVar4 + 1;
  } while (cVar1 != '\0');
  _Size = (int)pcVar4 - (int)(in_EAX + 0x19);
  puVar5 = malloc(_Size + 0x18);
  *(undefined1 **)((int)_Memory + 0xc) = puVar5;
  if (puVar5 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar5 = in_EAX[1];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 1) = *in_EAX;
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 2) = in_EAX[2];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 3) = 0x10;
  *(byte *)(*(int *)((int)_Memory + 0xc) + 4) = in_EAX[3] << 6 | 0x3f;
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 5) = in_EAX[7];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 6) = in_EAX[6];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 7) = in_EAX[5];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 8) = in_EAX[4];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 9) = in_EAX[0xb];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 10) = in_EAX[10];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0xb) = in_EAX[9];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0xc) = in_EAX[8];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0xd) = in_EAX[0xf];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0xe) = in_EAX[0xe];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0xf) = in_EAX[0xd];
  bVar2 = in_EAX[0x12];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0x10) = in_EAX[0xc];
  uVar3 = *(undefined4 *)(in_EAX + 0x10);
  *(byte *)(*(int *)((int)_Memory + 0xc) + 0x11) = bVar2 | 0xc0;
  *(char *)(*(int *)((int)_Memory + 0xc) + 0x12) = (char)((uint)uVar3 >> 8);
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0x13) = in_EAX[0x10];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0x14) = in_EAX[0x14];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0x15) = in_EAX[0x15];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 0x16) = in_EAX[0x16];
  *(char *)(*(int *)((int)_Memory + 0xc) + 0x17) = (char)_Size;
  memcpy((void *)(*(int *)((int)_Memory + 0xc) + 0x18),in_EAX + 0x18,_Size);
  local_8 = (undefined1)(_Size + 0x18);
  *(undefined1 *)((int)_Memory + 9) = local_8;
  return 0;
}



/* 10008670 FUN_10008670 */

undefined4 __cdecl
FUN_10008670(undefined1 param_1,undefined1 param_2,undefined4 param_3,undefined2 param_4,
            undefined2 param_5,undefined4 *param_6)

{
  void *pvVar1;
  
  pvVar1 = malloc(0x18);
  *param_6 = pvVar1;
  if (pvVar1 == (void *)0x0) {
    return 0x16;
  }
  *(undefined4 *)((int)pvVar1 + 8) = 0;
  *(undefined4 *)((int)pvVar1 + 0x10) = 0;
  *(undefined4 *)((int)pvVar1 + 0x14) = 0;
  *(undefined1 *)((int)pvVar1 + 8) = param_1;
  *(undefined1 *)((int)pvVar1 + 10) = param_2;
  *(undefined4 *)((int)pvVar1 + 0xc) = param_3;
  *(undefined2 *)((int)pvVar1 + 0x10) = param_4;
  *(undefined2 *)((int)pvVar1 + 0x12) = param_5;
  *(undefined1 *)((int)pvVar1 + 9) = 9;
  *(void **)pvVar1 = pvVar1;
  *(void **)((int)pvVar1 + 4) = pvVar1;
  return 0;
}



/* 100086d0 FUN_100086d0 */

undefined4 __cdecl FUN_100086d0(undefined4 *param_1)

{
  undefined1 uVar1;
  undefined1 *in_EAX;
  undefined4 *_Memory;
  undefined1 *puVar2;
  
  _Memory = malloc(0x10);
  *param_1 = _Memory;
  if (_Memory == (undefined4 *)0x0) {
    return 0x16;
  }
  *_Memory = 0;
  _Memory[1] = 0;
  _Memory[2] = 0;
  *(undefined2 *)(_Memory + 2) = 0xc4a;
  puVar2 = malloc(0xc);
  _Memory[3] = puVar2;
  if (puVar2 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar2 = in_EAX[1];
  *(undefined1 *)(_Memory[3] + 1) = *in_EAX;
  *(undefined1 *)(_Memory[3] + 2) = in_EAX[3];
  *(undefined1 *)(_Memory[3] + 3) = in_EAX[2];
  *(undefined1 *)(_Memory[3] + 4) = in_EAX[5];
  *(undefined1 *)(_Memory[3] + 5) = in_EAX[4];
  *(undefined1 *)(_Memory[3] + 6) = 9;
  uVar1 = in_EAX[10];
  *(undefined1 *)(_Memory[3] + 7) = 4;
  *(undefined1 *)(_Memory[3] + 8) = uVar1;
  *(undefined1 *)(_Memory[3] + 9) = in_EAX[9];
  *(undefined1 *)(_Memory[3] + 10) = in_EAX[8];
  *(undefined1 *)(_Memory[3] + 0xb) = 0;
  *_Memory = _Memory;
  _Memory[1] = _Memory;
  return 0;
}



/* 100087b0 FUN_100087b0 */

undefined4 __cdecl FUN_100087b0(undefined4 *param_1,undefined4 *param_2)

{
  ushort uVar1;
  undefined4 *_Memory;
  void *_Dst;
  
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (undefined4 *)0x0) {
    return 0x16;
  }
  uVar1 = *(ushort *)(param_1 + 1);
  *_Memory = 0;
  _Memory[1] = 0;
  _Memory[2] = 0;
  *(undefined1 *)(_Memory + 2) = 0x40;
  _Dst = malloc((uint)uVar1);
  _Memory[3] = _Dst;
  if (_Dst == (void *)0x0) {
    free(_Memory);
    return 0x16;
  }
  memcpy(_Dst,(void *)*param_1,(uint)uVar1);
  *(char *)((int)_Memory + 9) = (char)uVar1;
  *_Memory = _Memory;
  _Memory[1] = _Memory;
  return 0;
}



/* 10008830 FUN_10008830 */

undefined4 __cdecl FUN_10008830(undefined4 *param_1)

{
  byte *pbVar1;
  char cVar2;
  byte bVar3;
  undefined1 *in_EAX;
  undefined4 *_Memory;
  undefined1 *puVar4;
  
  _Memory = malloc(0x10);
  *param_1 = _Memory;
  if (_Memory == (undefined4 *)0x0) {
    return 0x16;
  }
  *_Memory = 0;
  _Memory[1] = 0;
  _Memory[2] = 0;
  *(undefined2 *)(_Memory + 2) = 0xb5a;
  puVar4 = malloc(0xb);
  _Memory[3] = puVar4;
  if (puVar4 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar4 = in_EAX[3];
  *(undefined1 *)(_Memory[3] + 1) = in_EAX[2];
  *(undefined1 *)(_Memory[3] + 2) = in_EAX[1];
  *(undefined1 *)(_Memory[3] + 3) = *in_EAX;
  cVar2 = in_EAX[5];
  *(undefined1 *)(_Memory[3] + 4) = in_EAX[4] << 5;
  *(byte *)(_Memory[3] + 4) = *(byte *)(_Memory[3] + 4) | 0x1f;
  bVar3 = in_EAX[8];
  *(char *)(_Memory[3] + 5) = cVar2 << 6;
  *(byte *)(_Memory[3] + 5) = *(byte *)(_Memory[3] + 5) & 0xc0;
  pbVar1 = (byte *)(_Memory[3] + 5);
  *pbVar1 = *pbVar1 ^ (bVar3 ^ *(byte *)(_Memory[3] + 5)) & 7;
  bVar3 = in_EAX[6];
  *(undefined1 *)(_Memory[3] + 6) = 0xe0;
  *(byte *)(_Memory[3] + 6) = (bVar3 & 3) * '\b' | *(byte *)(_Memory[3] + 6) & 0xe0;
  *(byte *)(_Memory[3] + 6) = (in_EAX[7] & 3) * '\x02' | *(byte *)(_Memory[3] + 6) & 0xf8;
  *(byte *)(_Memory[3] + 6) = *(byte *)(_Memory[3] + 6) & 0xfe;
  *(undefined1 *)(_Memory[3] + 7) = 0xff;
  *(undefined1 *)(_Memory[3] + 8) = 0xff;
  *(undefined1 *)(_Memory[3] + 9) = 0xff;
  *(undefined1 *)(_Memory[3] + 10) = 0xff;
  *_Memory = _Memory;
  _Memory[1] = _Memory;
  return 0;
}



/* 10008960 FUN_10008960 */

undefined4 __cdecl FUN_10008960(undefined4 *param_1)

{
  char cVar1;
  byte bVar2;
  byte bVar3;
  void *pvVar4;
  byte *pbVar5;
  char *unaff_EDI;
  
  pvVar4 = malloc(0x10);
  *param_1 = pvVar4;
  if (pvVar4 == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)pvVar4 + 8) = 2;
  *(void **)pvVar4 = pvVar4;
  *(void **)((int)pvVar4 + 4) = pvVar4;
  pbVar5 = malloc(1);
  cVar1 = *unaff_EDI;
  bVar2 = unaff_EDI[1];
  bVar3 = unaff_EDI[2];
  *(byte **)((int)pvVar4 + 0xc) = pbVar5;
  *pbVar5 = (bVar2 & 0xf | cVar1 << 4) * '\x04' | bVar3 & 3;
  *(undefined1 *)((int)pvVar4 + 9) = 1;
  return 0;
}



/* 100089d0 FUN_100089d0 */

undefined4 __cdecl FUN_100089d0(undefined4 *param_1)

{
  undefined1 *in_EAX;
  void *_Memory;
  undefined1 *puVar1;
  
  _Memory = malloc(0x10);
  *param_1 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 3;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  puVar1 = malloc(4);
  *(undefined1 **)((int)_Memory + 0xc) = puVar1;
  if (puVar1 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar1 = in_EAX[1];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 1) = *in_EAX;
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 2) = in_EAX[3];
  *(undefined1 *)(*(int *)((int)_Memory + 0xc) + 3) = in_EAX[2];
  *(undefined1 *)((int)_Memory + 9) = 4;
  return 0;
}



/* 10008a60 FUN_10008a60 */

undefined4 __cdecl FUN_10008a60(undefined4 *param_1,undefined4 *param_2)

{
  undefined1 uVar1;
  void *_Memory;
  undefined4 *puVar2;
  
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 0xb;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  puVar2 = malloc(5);
  *(undefined4 **)((int)_Memory + 0xc) = puVar2;
  if (puVar2 == (undefined4 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  uVar1 = *(undefined1 *)(param_1 + 1);
  *puVar2 = *param_1;
  *(undefined1 *)(puVar2 + 1) = uVar1;
  *(undefined1 *)((int)_Memory + 9) = 5;
  return 0;
}



/* 10008ad0 FUN_10008ad0 */

undefined4 __cdecl FUN_10008ad0(undefined2 *param_1,undefined4 *param_2)

{
  size_t _Size;
  char cVar1;
  int iVar2;
  void *_Memory;
  char *pcVar3;
  size_t _Size_00;
  undefined1 *puVar4;
  
  _Memory = malloc(0x10);
  *param_2 = _Memory;
  if (_Memory == (void *)0x0) {
    return 0x16;
  }
  *(undefined1 *)((int)_Memory + 8) = 4;
  *(void **)_Memory = _Memory;
  *(void **)((int)_Memory + 4) = _Memory;
  pcVar3 = (char *)(param_1 + 2);
  do {
    cVar1 = *pcVar3;
    pcVar3 = pcVar3 + 1;
  } while (cVar1 != '\0');
  _Size_00 = (int)pcVar3 - ((int)param_1 + 5);
  _Size = _Size_00 + 4;
  puVar4 = malloc(_Size);
  *(undefined1 **)((int)_Memory + 0xc) = puVar4;
  if (puVar4 == (undefined1 *)0x0) {
    free(_Memory);
    return 0x16;
  }
  *puVar4 = 0;
  iVar2 = *(int *)((int)_Memory + 0xc);
  *(undefined2 *)(iVar2 + 1) = *param_1;
  *(undefined1 *)(iVar2 + 3) = *(undefined1 *)(param_1 + 1);
  memcpy((void *)(*(int *)((int)_Memory + 0xc) + 4),param_1 + 2,_Size_00);
  *(char *)((int)_Memory + 9) = (char)_Size;
  return 0;
}



/* 10008b70 FUN_10008b70 */

undefined4 __cdecl FUN_10008b70(undefined4 *param_1)

{
  int in_EAX;
  undefined4 *_Memory;
  void *_Dst;
  
  _Memory = malloc(0x10);
  *param_1 = _Memory;
  if (_Memory == (undefined4 *)0x0) {
    return 0x16;
  }
  *_Memory = 0;
  _Memory[1] = 0;
  _Memory[2] = 0;
  _Memory[3] = 0;
  *(undefined1 *)(_Memory + 2) = *(undefined1 *)(in_EAX + 8);
  *(undefined1 *)((int)_Memory + 9) = *(undefined1 *)(in_EAX + 9);
  _Dst = malloc((uint)*(byte *)(in_EAX + 9));
  _Memory[3] = _Dst;
  if (_Dst == (void *)0x0) {
    free(_Memory);
    return 0x16;
  }
  memcpy(_Dst,*(void **)(in_EAX + 0xc),(uint)*(byte *)(in_EAX + 9));
  return 0;
}



/* 10008bf0 FUN_10008bf0 */

undefined4 __cdecl FUN_10008bf0(ushort param_1,undefined4 param_2,undefined4 *param_3)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  
  puVar2 = malloc(0x28);
  *param_3 = puVar2;
  if (puVar2 == (undefined4 *)0x0) {
    return 0x16;
  }
  *puVar2 = 0;
  puVar2[1] = 0;
  puVar2[3] = 0;
  puVar2[5] = 0;
  puVar2[6] = 0;
  puVar2[8] = 0;
  puVar2[2] = (uint)param_1;
  *(undefined1 *)(puVar2 + 3) = 1;
  puVar2[4] = param_2;
  *(undefined1 *)(puVar2 + 5) = 0xff;
  puVar1 = puVar2 + 6;
  *puVar1 = puVar1;
  puVar2[7] = puVar1;
  puVar1 = puVar2 + 8;
  *puVar1 = puVar1;
  puVar2[9] = puVar1;
  return 0;
}



/* 10008c50 FUN_10008c50 */

undefined4 __cdecl FUN_10008c50(undefined4 *param_1)

{
  int iVar1;
  void *pvVar2;
  
  pvVar2 = malloc(0x18);
  *param_1 = pvVar2;
  if (pvVar2 == (void *)0x0) {
    return 0x16;
  }
  *(void **)pvVar2 = pvVar2;
  *(void **)((int)pvVar2 + 4) = pvVar2;
  iVar1 = (int)pvVar2 + 8;
  *(int *)iVar1 = iVar1;
  *(int *)((int)pvVar2 + 0xc) = iVar1;
  iVar1 = (int)pvVar2 + 0x10;
  *(int *)iVar1 = iVar1;
  *(int *)((int)pvVar2 + 0x14) = iVar1;
  return 0;
}



/* 10008c90 FUN_10008c90 */

int __cdecl FUN_10008c90(int *param_1)

{
  size_t _Size;
  void *_Dst;
  int iVar1;
  int iVar2;
  size_t *unaff_EDI;
  undefined1 local_34 [8];
  undefined4 ***local_2c;
  undefined4 ***local_28;
  undefined4 ***local_24;
  undefined4 ***local_20;
  short local_1a;
  undefined2 local_18;
  uint local_c [2];
  
  local_2c = &local_2c;
  local_24 = &local_24;
  local_c[0] = 0;
  local_18 = 0;
  local_28 = local_2c;
  local_20 = local_24;
  _Size = FUN_10008e10();
  *unaff_EDI = _Size;
  _Dst = malloc(_Size);
  *param_1 = (int)_Dst;
  if (_Dst == (void *)0x0) {
    printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
    iVar2 = 0x16;
  }
  else {
    memset(_Dst,0,*unaff_EDI);
    iVar2 = FUN_10008ed0(local_34);
    if (iVar2 == 0) {
      local_1a = (short)*unaff_EDI + -3;
      iVar1 = FUN_1000bc70(*param_1,*unaff_EDI,local_c,(uint)local_34,0x13,0x10015ac8);
      iVar2 = 0;
      if (iVar1 != 0) {
        printf("WriteToSection for PMT failed(%d)\n",iVar1);
        iVar2 = 0x1a;
      }
    }
    else {
      printf("create_local_unt failed(%d)\n",iVar2);
    }
  }
  FUN_10009790((int)local_34);
  if (iVar2 != 0) {
    free((void *)*param_1);
  }
  return iVar2;
}



/* 10008d90 FUN_10008d90 */

undefined4 __fastcall FUN_10008d90(undefined4 param_1,int *param_2,int param_3)

{
  int iVar1;
  undefined4 uVar2;
  char *_Format;
  int local_34 [10];
  undefined4 local_c [2];
  
  local_c[0] = 0;
  iVar1 = FUN_1000b3f0((int *)&DAT_1005ab48,param_2,local_c,local_34,0x13,0x10015ac8);
  if (iVar1 == 0) {
    iVar1 = FUN_10009390((undefined1 *)local_34,param_3);
    uVar2 = 0;
    if (iVar1 == 0) goto LAB_10008df1;
    _Format = "create_from_local_unt failed(%d)\n";
  }
  else {
    _Format = "ReadFromSection for UNT failed(%d)\n";
  }
  printf(_Format,iVar1);
  uVar2 = 0x1a;
LAB_10008df1:
  FUN_10009790((int)local_34);
  return uVar2;
}



/* 10008e10 FUN_10008e10 */

int FUN_10008e10(void)

{
  int *piVar1;
  int *piVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  undefined4 *puVar5;
  int in_EAX;
  int iVar6;
  int iVar7;
  
  piVar1 = (int *)(in_EAX + 0x18);
  iVar6 = 0xc;
  if ((int *)*piVar1 != piVar1) {
    iVar6 = 0xe;
  }
  for (piVar2 = (int *)*piVar1; piVar2 != piVar1; piVar2 = (int *)*piVar2) {
    iVar6 = iVar6 + 2 + (uint)*(byte *)((int)piVar2 + 9);
  }
  for (puVar3 = *(undefined4 **)(in_EAX + 0x20); puVar3 != (undefined4 *)(in_EAX + 0x20);
      puVar3 = (undefined4 *)*puVar3) {
    piVar1 = puVar3 + 2;
    iVar7 = iVar6 + 4;
    if ((int *)*piVar1 != piVar1) {
      iVar7 = iVar6 + 6;
    }
    for (piVar2 = (int *)*piVar1; piVar2 != piVar1; piVar2 = (int *)*piVar2) {
      iVar7 = iVar7 + 0xb;
    }
    iVar6 = iVar7;
    for (puVar4 = (undefined4 *)puVar3[4]; puVar4 != puVar3 + 4; puVar4 = (undefined4 *)*puVar4) {
      iVar6 = iVar6 + 4;
      for (puVar5 = (undefined4 *)puVar4[2]; puVar5 != puVar4 + 2; puVar5 = (undefined4 *)*puVar5) {
        iVar6 = iVar6 + 2 + (uint)*(byte *)((int)puVar5 + 9);
      }
      for (puVar5 = (undefined4 *)puVar4[4]; puVar5 != puVar4 + 4; puVar5 = (undefined4 *)*puVar5) {
        iVar6 = iVar6 + 2 + (uint)*(byte *)((int)puVar5 + 9);
      }
    }
  }
  return iVar6 + 4;
}



/* 10008ed0 FUN_10008ed0 */

int __cdecl FUN_10008ed0(undefined1 *param_1)

{
  undefined1 *puVar1;
  undefined1 uVar2;
  undefined2 uVar3;
  int iVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  int iVar7;
  int iVar8;
  short sVar9;
  int in_EAX;
  int *piVar10;
  int *piVar11;
  undefined4 *puVar12;
  undefined4 *puVar13;
  void *pvVar14;
  short sVar15;
  short sVar16;
  int *piVar17;
  int *piVar18;
  int *piVar19;
  int local_20;
  int local_14;
  
  *(undefined4 *)(param_1 + 4) = *(undefined4 *)(in_EAX + 0x10);
  *param_1 = *(undefined1 *)(in_EAX + 0xc);
  param_1[1] = *(undefined1 *)(in_EAX + 0x14);
  param_1[0x24] = *(byte *)(in_EAX + 0x12) ^ *(byte *)(in_EAX + 0x11) ^ *(byte *)(in_EAX + 0x10);
  puVar1 = param_1 + 8;
  *(undefined1 **)puVar1 = puVar1;
  *(undefined1 **)(param_1 + 0xc) = puVar1;
  piVar19 = *(int **)(in_EAX + 0x18);
  sVar15 = 0;
  sVar9 = 0;
  local_14 = 0;
  if (piVar19 != (int *)(in_EAX + 0x18)) {
    do {
      sVar9 = sVar15 + 2 + (ushort)*(byte *)((int)piVar19 + 9);
      local_20 = 0;
      piVar10 = malloc(0x10);
      if (piVar10 == (int *)0x0) {
        local_20 = 0x16;
      }
      else {
        *piVar10 = 0;
        piVar10[1] = 0;
        piVar10[2] = 0;
        piVar10[3] = 0;
        *(char *)(piVar10 + 2) = (char)piVar19[2];
        *(undefined1 *)((int)piVar10 + 9) = *(undefined1 *)((int)piVar19 + 9);
        pvVar14 = malloc((uint)*(byte *)((int)piVar19 + 9));
        piVar10[3] = (int)pvVar14;
        if (pvVar14 == (void *)0x0) {
          local_20 = 0x16;
          free(piVar10);
        }
        else {
          memcpy(pvVar14,(void *)piVar19[3],(uint)*(byte *)((int)piVar19 + 9));
        }
      }
      local_14 = local_20;
      if (local_20 != 0) goto LAB_10009339;
      puVar12 = *(undefined4 **)(param_1 + 0xc);
      *(int **)(param_1 + 0xc) = piVar10;
      *piVar10 = (int)(param_1 + 8);
      piVar10[1] = (int)puVar12;
      *puVar12 = piVar10;
      piVar19 = (int *)*piVar19;
      sVar15 = sVar9;
    } while (piVar19 != (int *)(in_EAX + 0x18));
  }
  *(short *)(param_1 + 0x18) = sVar9;
  puVar1 = param_1 + 0x10;
  *(undefined1 **)puVar1 = puVar1;
  *(undefined1 **)(param_1 + 0x14) = puVar1;
  piVar19 = (int *)(in_EAX + 0x20);
  piVar10 = (int *)*piVar19;
  if (piVar10 != piVar19) {
    do {
      piVar11 = malloc(0x20);
      if (piVar11 == (int *)0x0) {
        FUN_10009790((int)param_1);
        return 0x16;
      }
      *piVar11 = 0;
      piVar11[1] = 0;
      piVar11[2] = 0;
      piVar11[3] = 0;
      piVar11[4] = 0;
      piVar11[5] = 0;
      piVar11[6] = 0;
      piVar18 = piVar11 + 6;
      *piVar18 = (int)piVar18;
      piVar11[7] = (int)piVar18;
      piVar18 = piVar10 + 2;
      if ((int *)*piVar18 != piVar18) {
        *(undefined2 *)(piVar11 + 3) = 2;
      }
      piVar17 = (int *)*piVar18;
      if (piVar17 != piVar18) {
        do {
          *(short *)(piVar11 + 2) = (short)piVar11[2] + 1;
          iVar7 = piVar17[4];
          iVar4 = piVar17[3];
          uVar2 = *(undefined1 *)((int)piVar17 + 10);
          uVar3 = *(undefined2 *)((int)piVar17 + 0x12);
          iVar8 = piVar17[2];
          puVar12 = malloc(0x18);
          if (puVar12 == (undefined4 *)0x0) {
            local_14 = 0x16;
          }
          else {
            puVar12[2] = 0;
            puVar12[4] = 0;
            puVar12[5] = 0;
            *(char *)(puVar12 + 2) = (char)iVar8;
            *(undefined1 *)((int)puVar12 + 10) = uVar2;
            puVar12[3] = iVar4;
            local_14 = 0;
            *(short *)(puVar12 + 4) = (short)iVar7;
            *(undefined2 *)((int)puVar12 + 0x12) = uVar3;
            *(undefined1 *)((int)puVar12 + 9) = 9;
            *puVar12 = puVar12;
            puVar12[1] = puVar12;
          }
          if (local_14 != 0) goto LAB_10009339;
          *(short *)(piVar11 + 3) = (short)piVar11[3] + 0xb;
          puVar5 = (undefined4 *)piVar11[7];
          piVar11[7] = (int)puVar12;
          *puVar12 = piVar11 + 6;
          puVar12[1] = puVar5;
          *puVar5 = puVar12;
          piVar17 = (int *)*piVar17;
        } while (piVar17 != piVar10 + 2);
      }
      piVar18 = piVar11 + 4;
      *piVar18 = (int)piVar18;
      piVar11[5] = (int)piVar18;
      piVar18 = (int *)piVar10[4];
      sVar9 = 0;
      if (piVar18 != piVar10 + 4) {
        do {
          puVar12 = malloc(0x1c);
          if (puVar12 == (undefined4 *)0x0) {
            local_14 = 0x16;
            goto LAB_10009339;
          }
          puVar5 = puVar12 + 2;
          *puVar5 = puVar5;
          puVar12[3] = puVar5;
          sVar15 = 0;
          for (piVar17 = (int *)piVar18[2]; piVar17 != piVar18 + 2; piVar17 = (int *)*piVar17) {
            sVar15 = sVar15 + 2 + (ushort)*(byte *)((int)piVar17 + 9);
            local_14 = 0;
            puVar13 = malloc(0x10);
            if (puVar13 == (undefined4 *)0x0) {
              local_14 = 0x16;
            }
            else {
              *puVar13 = 0;
              puVar13[1] = 0;
              puVar13[2] = 0;
              puVar13[3] = 0;
              *(char *)(puVar13 + 2) = (char)piVar17[2];
              *(undefined1 *)((int)puVar13 + 9) = *(undefined1 *)((int)piVar17 + 9);
              pvVar14 = malloc((uint)*(byte *)((int)piVar17 + 9));
              puVar13[3] = pvVar14;
              if (pvVar14 == (void *)0x0) {
                local_14 = 0x16;
                free(puVar13);
              }
              else {
                memcpy(pvVar14,(void *)piVar17[3],(uint)*(byte *)((int)piVar17 + 9));
              }
            }
            if (local_14 != 0) goto LAB_10009339;
            puVar6 = (undefined4 *)puVar12[3];
            puVar12[3] = puVar13;
            *puVar13 = puVar5;
            puVar13[1] = puVar6;
            *puVar6 = puVar13;
          }
          puVar5 = puVar12 + 4;
          *(short *)(puVar12 + 6) = sVar15;
          *puVar5 = puVar5;
          puVar12[5] = puVar5;
          piVar17 = (int *)piVar18[4];
          sVar16 = 0;
          if (piVar17 != piVar18 + 4) {
            do {
              sVar16 = sVar16 + 2 + (ushort)*(byte *)((int)piVar17 + 9);
              local_14 = 0;
              puVar13 = malloc(0x10);
              if (puVar13 == (undefined4 *)0x0) {
                local_14 = 0x16;
              }
              else {
                *puVar13 = 0;
                puVar13[1] = 0;
                puVar13[2] = 0;
                puVar13[3] = 0;
                *(char *)(puVar13 + 2) = (char)piVar17[2];
                *(undefined1 *)((int)puVar13 + 9) = *(undefined1 *)((int)piVar17 + 9);
                pvVar14 = malloc((uint)*(byte *)((int)piVar17 + 9));
                puVar13[3] = pvVar14;
                if (pvVar14 == (void *)0x0) {
                  local_14 = 0x16;
                  free(puVar13);
                }
                else {
                  memcpy(pvVar14,(void *)piVar17[3],(uint)*(byte *)((int)piVar17 + 9));
                }
              }
              if (local_14 != 0) goto LAB_10009339;
              puVar6 = (undefined4 *)puVar12[5];
              puVar12[5] = puVar13;
              puVar13[1] = puVar6;
              *puVar13 = puVar5;
              *puVar6 = puVar13;
              piVar17 = (int *)*piVar17;
            } while (piVar17 != piVar18 + 4);
          }
          sVar9 = sVar9 + 4 + sVar15 + sVar16;
          *(short *)((int)puVar12 + 0x1a) = sVar16;
          puVar5 = (undefined4 *)piVar11[5];
          piVar11[5] = (int)puVar12;
          *puVar12 = piVar11 + 4;
          puVar12[1] = puVar5;
          *puVar5 = puVar12;
          piVar18 = (int *)*piVar18;
        } while (piVar18 != piVar10 + 4);
      }
      *(short *)((int)piVar11 + 10) = sVar9;
      puVar12 = *(undefined4 **)(param_1 + 0x14);
      *(int **)(param_1 + 0x14) = piVar11;
      *piVar11 = (int)(param_1 + 0x10);
      piVar11[1] = (int)puVar12;
      *puVar12 = piVar11;
      piVar10 = (int *)*piVar10;
    } while (piVar10 != piVar19);
    if (local_14 != 0) {
LAB_10009339:
      FUN_10009790((int)param_1);
    }
  }
  return local_14;
}



/* 10009390 FUN_10009390 */

int __cdecl FUN_10009390(undefined1 *param_1,int param_2)

{
  int iVar1;
  int iVar2;
  undefined1 uVar3;
  undefined2 uVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  int iVar7;
  int *piVar8;
  undefined4 *puVar9;
  undefined4 *puVar10;
  void *pvVar11;
  int *piVar12;
  int *piVar13;
  int *piVar14;
  int local_10;
  int local_c;
  
  *(undefined4 *)(param_2 + 0x10) = *(undefined4 *)(param_1 + 4);
  *(undefined1 *)(param_2 + 0xc) = *param_1;
  *(undefined1 *)(param_2 + 0x14) = param_1[1];
  iVar1 = param_2 + 0x20;
  iVar2 = param_2 + 0x18;
  *(int *)iVar1 = iVar1;
  *(int *)(param_2 + 0x24) = iVar1;
  *(int *)iVar2 = iVar2;
  *(int *)(param_2 + 0x1c) = iVar2;
  piVar12 = *(int **)(param_1 + 8);
  local_c = 0;
  if (piVar12 != (int *)(param_1 + 8)) {
    do {
      local_10 = 0;
      piVar8 = malloc(0x10);
      if (piVar8 == (int *)0x0) {
        local_10 = 0x16;
      }
      else {
        *piVar8 = 0;
        piVar8[1] = 0;
        piVar8[2] = 0;
        piVar8[3] = 0;
        *(char *)(piVar8 + 2) = (char)piVar12[2];
        *(undefined1 *)((int)piVar8 + 9) = *(undefined1 *)((int)piVar12 + 9);
        pvVar11 = malloc((uint)*(byte *)((int)piVar12 + 9));
        piVar8[3] = (int)pvVar11;
        if (pvVar11 == (void *)0x0) {
          local_10 = 0x16;
          free(piVar8);
        }
        else {
          memcpy(pvVar11,(void *)piVar12[3],(uint)*(byte *)((int)piVar12 + 9));
        }
      }
      local_c = local_10;
      if (local_10 != 0) goto LAB_10009755;
      puVar9 = *(undefined4 **)(param_2 + 0x1c);
      *(int **)(param_2 + 0x1c) = piVar8;
      piVar8[1] = (int)puVar9;
      *piVar8 = iVar2;
      *puVar9 = piVar8;
      piVar12 = (int *)*piVar12;
    } while (piVar12 != (int *)(param_1 + 8));
  }
  piVar12 = *(int **)(param_1 + 0x10);
  if (piVar12 != (int *)(param_1 + 0x10)) {
    do {
      piVar8 = malloc(0x18);
      if (piVar8 == (int *)0x0) {
LAB_10009782:
        local_c = 0x16;
        goto LAB_10009755;
      }
      *piVar8 = 0;
      piVar8[1] = 0;
      piVar8[2] = 0;
      piVar8[4] = 0;
      piVar13 = piVar8 + 2;
      *piVar13 = (int)piVar13;
      piVar8[3] = (int)piVar13;
      piVar13 = piVar8 + 4;
      *piVar13 = (int)piVar13;
      piVar8[5] = (int)piVar13;
      piVar13 = (int *)piVar12[6];
      if (piVar13 != piVar12 + 6) {
        do {
          iVar2 = piVar13[4];
          iVar1 = piVar13[3];
          uVar3 = *(undefined1 *)((int)piVar13 + 10);
          uVar4 = *(undefined2 *)((int)piVar13 + 0x12);
          iVar7 = piVar13[2];
          puVar9 = malloc(0x18);
          if (puVar9 == (undefined4 *)0x0) {
            local_c = 0x16;
          }
          else {
            puVar9[2] = 0;
            puVar9[4] = 0;
            puVar9[5] = 0;
            *(char *)(puVar9 + 2) = (char)iVar7;
            *(undefined1 *)((int)puVar9 + 10) = uVar3;
            puVar9[3] = iVar1;
            local_c = 0;
            *(short *)(puVar9 + 4) = (short)iVar2;
            *(undefined2 *)((int)puVar9 + 0x12) = uVar4;
            *(undefined1 *)((int)puVar9 + 9) = 9;
            *puVar9 = puVar9;
            puVar9[1] = puVar9;
          }
          if (local_c != 0) goto LAB_10009755;
          puVar5 = (undefined4 *)piVar8[3];
          piVar8[3] = (int)puVar9;
          *puVar9 = piVar8 + 2;
          puVar9[1] = puVar5;
          *puVar5 = puVar9;
          piVar13 = (int *)*piVar13;
        } while (piVar13 != piVar12 + 6);
      }
      piVar13 = (int *)piVar12[4];
      if (piVar13 != piVar12 + 4) {
        do {
          puVar9 = malloc(0x18);
          if (puVar9 == (undefined4 *)0x0) goto LAB_10009782;
          puVar5 = puVar9 + 2;
          *puVar5 = puVar5;
          puVar9[3] = puVar5;
          for (piVar14 = (int *)piVar13[2]; piVar14 != piVar13 + 2; piVar14 = (int *)*piVar14) {
            local_c = 0;
            puVar10 = malloc(0x10);
            if (puVar10 == (undefined4 *)0x0) {
              local_c = 0x16;
            }
            else {
              *puVar10 = 0;
              puVar10[1] = 0;
              puVar10[2] = 0;
              puVar10[3] = 0;
              *(char *)(puVar10 + 2) = (char)piVar14[2];
              *(undefined1 *)((int)puVar10 + 9) = *(undefined1 *)((int)piVar14 + 9);
              pvVar11 = malloc((uint)*(byte *)((int)piVar14 + 9));
              puVar10[3] = pvVar11;
              if (pvVar11 == (void *)0x0) {
                local_c = 0x16;
                free(puVar10);
              }
              else {
                memcpy(pvVar11,(void *)piVar14[3],(uint)*(byte *)((int)piVar14 + 9));
              }
            }
            if (local_c != 0) goto LAB_10009755;
            puVar6 = (undefined4 *)puVar9[3];
            puVar9[3] = puVar10;
            *puVar10 = puVar5;
            puVar10[1] = puVar6;
            *puVar6 = puVar10;
          }
          puVar5 = puVar9 + 4;
          *puVar5 = puVar5;
          puVar9[5] = puVar5;
          piVar14 = (int *)piVar13[4];
          if (piVar14 != piVar13 + 4) {
            do {
              local_c = 0;
              puVar10 = malloc(0x10);
              if (puVar10 == (undefined4 *)0x0) {
                local_c = 0x16;
              }
              else {
                *puVar10 = 0;
                puVar10[1] = 0;
                puVar10[2] = 0;
                puVar10[3] = 0;
                *(char *)(puVar10 + 2) = (char)piVar14[2];
                *(undefined1 *)((int)puVar10 + 9) = *(undefined1 *)((int)piVar14 + 9);
                pvVar11 = malloc((uint)*(byte *)((int)piVar14 + 9));
                puVar10[3] = pvVar11;
                if (pvVar11 == (void *)0x0) {
                  local_c = 0x16;
                  free(puVar10);
                }
                else {
                  memcpy(pvVar11,(void *)piVar14[3],(uint)*(byte *)((int)piVar14 + 9));
                }
              }
              if (local_c != 0) goto LAB_10009755;
              puVar6 = (undefined4 *)puVar9[5];
              puVar9[5] = puVar10;
              puVar10[1] = puVar6;
              *puVar10 = puVar5;
              *puVar6 = puVar10;
              piVar14 = (int *)*piVar14;
            } while (piVar14 != piVar13 + 4);
          }
          puVar5 = (undefined4 *)piVar8[5];
          piVar8[5] = (int)puVar9;
          *puVar9 = piVar8 + 4;
          puVar9[1] = puVar5;
          *puVar5 = puVar9;
          piVar13 = (int *)*piVar13;
        } while (piVar13 != piVar12 + 4);
      }
      puVar9 = *(undefined4 **)(param_2 + 0x24);
      *(int **)(param_2 + 0x24) = piVar8;
      *piVar8 = param_2 + 0x20;
      piVar8[1] = (int)puVar9;
      *puVar9 = piVar8;
      piVar12 = (int *)*piVar12;
    } while (piVar12 != (int *)(param_1 + 0x10));
    if (local_c != 0) {
LAB_10009755:
      FUN_100098c0(param_2);
    }
  }
  return local_c;
}



/* 10009790 FUN_10009790 */

undefined4 __cdecl FUN_10009790(int param_1)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  undefined4 *puVar5;
  int *_Memory;
  undefined4 *puVar6;
  bool bVar7;
  undefined4 *local_c;
  
  _Memory = *(int **)(param_1 + 8);
  puVar3 = (undefined4 *)*_Memory;
  if (_Memory != (int *)(param_1 + 8)) {
    do {
      puVar4 = puVar3;
      free((void *)_Memory[3]);
      free(_Memory);
      puVar3 = (undefined4 *)*puVar4;
      _Memory = puVar4;
    } while (puVar4 != (undefined4 *)(param_1 + 8));
  }
  puVar4 = (undefined4 *)**(undefined4 **)(param_1 + 0x10);
  puVar3 = *(undefined4 **)(param_1 + 0x10);
  while (puVar1 = puVar4, puVar3 != (undefined4 *)(param_1 + 0x10)) {
    puVar6 = *(undefined4 **)puVar3[6];
    puVar4 = (undefined4 *)puVar3[6];
    while (puVar2 = puVar6, puVar4 != puVar3 + 6) {
      free(puVar4);
      puVar6 = (undefined4 *)*puVar2;
      puVar4 = puVar2;
    }
    puVar4 = (undefined4 *)puVar3[4];
    local_c = (undefined4 *)*puVar4;
    if (puVar4 != puVar3 + 4) {
      do {
        puVar2 = *(undefined4 **)puVar4[2];
        puVar6 = (undefined4 *)puVar4[2];
        while (puVar5 = puVar2, puVar6 != puVar4 + 2) {
          free((void *)puVar6[3]);
          free(puVar6);
          puVar2 = (undefined4 *)*puVar5;
          puVar6 = puVar5;
        }
        puVar6 = (undefined4 *)puVar4[4];
        puVar2 = (undefined4 *)*puVar6;
        if (puVar6 != puVar4 + 4) {
          do {
            puVar5 = puVar2;
            free((void *)puVar6[3]);
            free(puVar6);
            puVar2 = (undefined4 *)*puVar5;
            puVar6 = puVar5;
          } while (puVar5 != puVar4 + 4);
        }
        free(puVar4);
        bVar7 = local_c != puVar3 + 4;
        puVar4 = local_c;
        local_c = (undefined4 *)*local_c;
      } while (bVar7);
    }
    free(puVar3);
    puVar4 = (undefined4 *)*puVar1;
    puVar3 = puVar1;
  }
  return 0;
}



/* 100098c0 FUN_100098c0 */

undefined4 __cdecl FUN_100098c0(int param_1)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  undefined4 *puVar5;
  int *_Memory;
  undefined4 *puVar6;
  bool bVar7;
  undefined4 *local_c;
  
  _Memory = *(int **)(param_1 + 0x18);
  puVar3 = (undefined4 *)*_Memory;
  if (_Memory != (int *)(param_1 + 0x18)) {
    do {
      puVar4 = puVar3;
      free((void *)_Memory[3]);
      free(_Memory);
      puVar3 = (undefined4 *)*puVar4;
      _Memory = puVar4;
    } while (puVar4 != (undefined4 *)(param_1 + 0x18));
  }
  puVar4 = (undefined4 *)**(undefined4 **)(param_1 + 0x20);
  puVar3 = *(undefined4 **)(param_1 + 0x20);
  while (puVar1 = puVar4, puVar3 != (undefined4 *)(param_1 + 0x20)) {
    puVar6 = *(undefined4 **)puVar3[2];
    puVar4 = (undefined4 *)puVar3[2];
    while (puVar2 = puVar6, puVar4 != puVar3 + 2) {
      free(puVar4);
      puVar6 = (undefined4 *)*puVar2;
      puVar4 = puVar2;
    }
    puVar4 = (undefined4 *)puVar3[4];
    local_c = (undefined4 *)*puVar4;
    if (puVar4 != puVar3 + 4) {
      do {
        puVar2 = *(undefined4 **)puVar4[4];
        puVar6 = (undefined4 *)puVar4[4];
        while (puVar5 = puVar2, puVar6 != puVar4 + 4) {
          free((void *)puVar6[3]);
          free(puVar6);
          puVar2 = (undefined4 *)*puVar5;
          puVar6 = puVar5;
        }
        puVar6 = (undefined4 *)puVar4[2];
        puVar2 = (undefined4 *)*puVar6;
        if (puVar6 != puVar4 + 2) {
          do {
            puVar5 = puVar2;
            free((void *)puVar6[3]);
            free(puVar6);
            puVar2 = (undefined4 *)*puVar5;
            puVar6 = puVar5;
          } while (puVar5 != puVar4 + 2);
        }
        free(puVar4);
        bVar7 = local_c != puVar3 + 4;
        puVar4 = local_c;
        local_c = (undefined4 *)*local_c;
      } while (bVar7);
    }
    free(puVar3);
    puVar4 = (undefined4 *)*puVar1;
    puVar3 = puVar1;
  }
  return 0;
}



/* 100099f0 FUN_100099f0 */

undefined4 __cdecl
FUN_100099f0(ushort param_1,undefined4 param_2,undefined4 param_3,undefined4 *param_4)

{
  int iVar1;
  void *pvVar2;
  
  pvVar2 = malloc(0x28);
  *param_4 = pvVar2;
  if (pvVar2 == (void *)0x0) {
    return 0x16;
  }
  *(uint *)((int)pvVar2 + 8) = (uint)param_1;
  *(undefined4 *)((int)pvVar2 + 0xc) = param_2;
  *(undefined4 *)((int)pvVar2 + 0x10) = param_3;
  *(undefined4 *)((int)pvVar2 + 0x14) = 0xfe2;
  iVar1 = (int)pvVar2 + 0x18;
  *(int *)iVar1 = iVar1;
  *(int *)((int)pvVar2 + 0x1c) = iVar1;
  *(undefined2 *)((int)pvVar2 + 0x20) = 0;
  *(undefined4 *)((int)pvVar2 + 0x24) = 0;
  return 0;
}



/* 10009a40 FUN_10009a40 */

undefined4 __cdecl FUN_10009a40(int param_1,undefined4 *param_2,size_t *param_3)

{
  byte *pbVar1;
  int *piVar2;
  void *_Dst;
  int *piVar3;
  undefined4 uVar4;
  int iVar5;
  int iVar6;
  size_t _Size;
  int iVar7;
  int *local_8;
  
  iVar6 = 0x2a;
  piVar2 = *(int **)(param_1 + 0x18);
  if (*(int **)(param_1 + 0x18) != (int *)(param_1 + 0x18)) {
    do {
      local_8 = piVar2;
      iVar5 = 0;
      for (piVar2 = (int *)local_8[2]; piVar2 != local_8 + 2; piVar2 = (int *)*piVar2) {
        iVar5 = iVar5 + 2 + (uint)*(byte *)((int)piVar2 + 9);
      }
      *(char *)((int)local_8 + 0x19) = (char)iVar5;
      iVar6 = iVar6 + 8 + iVar5;
      piVar2 = (int *)*local_8;
    } while ((int *)*local_8 != (int *)(param_1 + 0x18));
  }
  _Size = iVar6 + 4;
  _Dst = malloc(_Size);
  *param_2 = _Dst;
  if (_Dst != (void *)0x0) {
    *param_3 = _Size;
    memset(_Dst,0,_Size);
    param_2 = (undefined4 *)0x1;
    iVar7 = 0;
    iVar5 = param_1;
    do {
      piVar2 = local_8;
      switch(param_2) {
      case (undefined4 *)0x1:
        *(undefined1 *)(iVar7 + (int)_Dst) = 0x3b;
        *(char *)((int)_Dst + iVar7 + 2) = (char)(iVar6 + 1);
        *(byte *)((int)_Dst + iVar7 + 1) = (byte)((uint)(iVar6 + 1) >> 8) & 0xf | 0xb0;
        *(undefined1 *)((int)_Dst + iVar7 + 3) = *(undefined1 *)(param_1 + 0xd);
        *(undefined1 *)((int)_Dst + iVar7 + 4) = *(undefined1 *)(param_1 + 0xc);
        *(undefined2 *)(iVar7 + 5 + (int)_Dst) = 0xc1;
        *(undefined2 *)(iVar7 + 7 + (int)_Dst) = 0x1100;
        *(undefined1 *)(iVar7 + 9 + (int)_Dst) = 3;
        *(undefined2 *)((int)_Dst + iVar7 + 10) = 0x210;
        *(undefined1 *)((int)_Dst + iVar7 + 0xc) = *(undefined1 *)(param_1 + 0xf);
        *(undefined1 *)((int)_Dst + iVar7 + 0xd) = *(undefined1 *)(param_1 + 0xe);
        *(undefined1 *)((int)_Dst + iVar7 + 0xe) = *(undefined1 *)(param_1 + 0xd);
        *(undefined1 *)((int)_Dst + iVar7 + 0xf) = *(undefined1 *)(param_1 + 0xc);
        *(undefined2 *)(iVar7 + 0x10 + (int)_Dst) = 0xff;
        *(char *)((int)_Dst + iVar7 + 0x12) = (char)((uint)(iVar6 + -0x14) >> 8);
        *(char *)((int)_Dst + iVar7 + 0x13) = (char)(iVar6 + -0x14);
        iVar5 = 0x14;
        break;
      case (undefined4 *)0x2:
        *(undefined1 *)(iVar7 + (int)_Dst) = *(undefined1 *)(param_1 + 0x13);
        *(undefined1 *)(iVar7 + 1 + (int)_Dst) = *(undefined1 *)(param_1 + 0x12);
        *(undefined1 *)(iVar7 + 2 + (int)_Dst) = *(undefined1 *)(param_1 + 0x11);
        *(undefined1 *)(iVar7 + 3 + (int)_Dst) = *(undefined1 *)(param_1 + 0x10);
        iVar5 = 4;
        break;
      case (undefined4 *)0x3:
        *(undefined1 *)(iVar7 + (int)_Dst) = *(undefined1 *)(param_1 + 0x15);
        *(undefined1 *)(iVar7 + 1 + (int)_Dst) = *(undefined1 *)(param_1 + 0x14);
        iVar5 = 2;
        break;
      case (undefined4 *)0x4:
      case (undefined4 *)0x5:
        *(undefined1 *)(iVar7 + (int)_Dst) = 0;
        iVar5 = 1;
        break;
      case (undefined4 *)0x6:
      case (undefined4 *)0x7:
        *(undefined4 *)(iVar7 + (int)_Dst) = 0;
        iVar5 = 4;
        break;
      case (undefined4 *)0x8:
        *(undefined2 *)(iVar7 + (int)_Dst) = 0;
        iVar5 = 2;
        break;
      case (undefined4 *)0x9:
        *(undefined1 *)(iVar7 + (int)_Dst) = *(undefined1 *)(param_1 + 0x17);
        *(undefined1 *)(iVar7 + 1 + (int)_Dst) = *(undefined1 *)(param_1 + 0x16);
        piVar2 = *(int **)(param_1 + 0x18);
        iVar5 = 2;
        if (piVar2 == (int *)(param_1 + 0x18)) {
          param_2 = (undefined4 *)0xf;
          piVar2 = local_8;
        }
        break;
      case (undefined4 *)0xa:
        *(undefined1 *)(iVar7 + (int)_Dst) = *(undefined1 *)((int)local_8 + 0x11);
        *(char *)(iVar7 + 1 + (int)_Dst) = (char)local_8[4];
        iVar5 = 2;
        break;
      case (undefined4 *)0xb:
        *(undefined1 *)(iVar7 + (int)_Dst) = *(undefined1 *)((int)local_8 + 0x17);
        *(undefined1 *)(iVar7 + 1 + (int)_Dst) = *(undefined1 *)((int)local_8 + 0x16);
        *(undefined1 *)(iVar7 + 2 + (int)_Dst) = *(undefined1 *)((int)local_8 + 0x15);
        *(char *)(iVar7 + 3 + (int)_Dst) = (char)local_8[5];
        iVar5 = 4;
        break;
      case (undefined4 *)0xc:
        *(char *)(iVar7 + (int)_Dst) = (char)local_8[6];
        iVar5 = 1;
        break;
      case (undefined4 *)0xd:
        *(undefined1 *)(iVar7 + (int)_Dst) = *(undefined1 *)((int)local_8 + 0x19);
        iVar5 = 1;
        break;
      case (undefined4 *)0xe:
        piVar3 = (int *)local_8[2];
        iVar5 = 0;
        if (piVar3 != local_8 + 2) {
          do {
            *(char *)((int)_Dst + iVar5 + iVar7) = (char)piVar3[2];
            *(undefined1 *)((int)_Dst + iVar5 + iVar7 + 1) = *(undefined1 *)((int)piVar3 + 9);
            memcpy((void *)((int)_Dst + iVar5 + 2 + iVar7),(void *)piVar3[3],
                   (uint)*(byte *)((int)piVar3 + 9));
            pbVar1 = (byte *)((int)piVar3 + 9);
            piVar3 = (int *)*piVar3;
            iVar5 = iVar5 + 2 + (uint)*pbVar1;
          } while (piVar3 != local_8 + 2);
        }
        break;
      case (undefined4 *)0xf:
        if ((int *)*local_8 != (int *)(param_1 + 0x18)) {
          param_2 = (undefined4 *)0x9;
          local_8 = (int *)*local_8;
        }
      case (undefined4 *)0x11:
        iVar5 = 0;
        piVar2 = local_8;
        break;
      case (undefined4 *)0x10:
        *(undefined1 *)(iVar7 + (int)_Dst) = *(undefined1 *)(param_1 + 0x21);
        *(undefined1 *)(iVar7 + 1 + (int)_Dst) = *(undefined1 *)(param_1 + 0x20);
        iVar5 = 2;
        break;
      case (undefined4 *)0x12:
        uVar4 = FUN_1000b3b0(*param_3 - 4);
        *(char *)(iVar7 + (int)_Dst) = (char)((uint)uVar4 >> 0x18);
        *(char *)(iVar7 + 1 + (int)_Dst) = (char)((uint)uVar4 >> 0x10);
        *(char *)(iVar7 + 2 + (int)_Dst) = (char)((uint)uVar4 >> 8);
        *(char *)(iVar7 + 3 + (int)_Dst) = (char)uVar4;
        param_2 = (undefined4 *)0x13;
        break;
      case (undefined4 *)0x14:
        printf("Wont reach here!!!\n");
      }
      local_8 = piVar2;
      param_2 = (undefined4 *)((int)param_2 + 1);
      iVar7 = iVar7 + iVar5;
    } while (param_2 != (undefined4 *)0x14);
    return 0;
  }
  printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
  return 0x16;
}



/* 10009dd0 FUN_10009dd0 */

undefined4 __fastcall FUN_10009dd0(undefined4 param_1,int *param_2,int param_3)

{
  int iVar1;
  undefined4 uVar2;
  int local_34 [11];
  undefined4 local_8;
  
  uVar2 = 0;
  local_8 = 0;
  iVar1 = FUN_1000b3f0((int *)&DAT_1005ab48,param_2,&local_8,local_34,0x1e,0x10015648);
  if (iVar1 != 0) {
    printf("ReadFromSection for DSI failed(%d)\n",iVar1);
    uVar2 = 0x1a;
  }
  iVar1 = FUN_10009e40(param_3);
  if (iVar1 != 0) {
    uVar2 = 0x1a;
  }
  FUN_1000a080((int)local_34);
  return uVar2;
}



/* 10009e40 FUN_10009e40 */

int __cdecl FUN_10009e40(int param_1)

{
  int *piVar1;
  ushort uVar2;
  undefined4 *puVar3;
  undefined4 *in_EAX;
  void *pvVar4;
  int *piVar5;
  int *_Memory;
  int iVar6;
  int *piVar7;
  int *piVar8;
  int *piVar9;
  int local_8;
  
  *(undefined4 *)(param_1 + 0xc) = *in_EAX;
  *(undefined2 *)(param_1 + 0x14) = *(undefined2 *)(in_EAX + 2);
  *(undefined4 *)(param_1 + 0x10) = in_EAX[1];
  *(undefined2 *)(param_1 + 0x16) = *(undefined2 *)((int)in_EAX + 10);
  iVar6 = param_1 + 0x18;
  *(int *)iVar6 = iVar6;
  *(int *)(param_1 + 0x1c) = iVar6;
  uVar2 = *(ushort *)(in_EAX + 5);
  local_8 = 0;
  *(ushort *)(param_1 + 0x20) = uVar2;
  if (uVar2 != 0) {
    pvVar4 = malloc((uint)uVar2);
    *(void **)(param_1 + 0x24) = pvVar4;
    if (pvVar4 == (void *)0x0) goto LAB_10009e94;
    memcpy(pvVar4,(void *)in_EAX[6],(uint)*(ushort *)(param_1 + 0x20));
  }
  piVar1 = in_EAX + 3;
  piVar7 = (int *)*piVar1;
  if (piVar7 == piVar1) {
    return 0;
  }
  while (piVar5 = malloc(0x1c), piVar5 != (int *)0x0) {
    piVar8 = piVar7;
    piVar9 = piVar5;
    for (iVar6 = 7; iVar6 != 0; iVar6 = iVar6 + -1) {
      *piVar9 = *piVar8;
      piVar8 = piVar8 + 1;
      piVar9 = piVar9 + 1;
    }
    piVar8 = piVar5 + 2;
    *piVar8 = (int)piVar8;
    piVar5[3] = (int)piVar8;
    piVar9 = (int *)piVar7[2];
    if (piVar9 != piVar7 + 2) {
      do {
        local_8 = 0;
        _Memory = malloc(0x10);
        if (_Memory == (int *)0x0) {
          local_8 = 0x16;
        }
        else {
          *_Memory = 0;
          _Memory[1] = 0;
          _Memory[2] = 0;
          _Memory[3] = 0;
          *(char *)(_Memory + 2) = (char)piVar9[2];
          *(undefined1 *)((int)_Memory + 9) = *(undefined1 *)((int)piVar9 + 9);
          pvVar4 = malloc((uint)*(byte *)((int)piVar9 + 9));
          _Memory[3] = (int)pvVar4;
          if (pvVar4 == (void *)0x0) {
            local_8 = 0x16;
            free(_Memory);
          }
          else {
            memcpy(pvVar4,(void *)piVar9[3],(uint)*(byte *)((int)piVar9 + 9));
          }
        }
        if (local_8 != 0) goto LAB_10009fbd;
        puVar3 = (undefined4 *)piVar5[3];
        piVar5[3] = (int)_Memory;
        _Memory[1] = (int)puVar3;
        *_Memory = (int)piVar8;
        *puVar3 = _Memory;
        piVar9 = (int *)*piVar9;
      } while (piVar9 != piVar7 + 2);
    }
    puVar3 = *(undefined4 **)(param_1 + 0x1c);
    *(int **)(param_1 + 0x1c) = piVar5;
    piVar5[1] = (int)puVar3;
    *piVar5 = param_1 + 0x18;
    *puVar3 = piVar5;
    piVar7 = (int *)*piVar7;
    if (piVar7 == piVar1) {
      if (local_8 != 0) {
LAB_10009fbd:
        FUN_10009ff0(param_1);
      }
      return local_8;
    }
  }
LAB_10009e94:
  FUN_10009ff0(param_1);
  return -1;
}



/* 10009ff0 FUN_10009ff0 */

undefined4 __cdecl FUN_10009ff0(int param_1)

{
  undefined4 *puVar1;
  undefined4 *_Memory;
  undefined4 *puVar2;
  undefined4 *_Memory_00;
  bool bVar3;
  undefined4 *local_c;
  
  _Memory_00 = *(undefined4 **)(param_1 + 0x18);
  local_c = (undefined4 *)*_Memory_00;
  if (_Memory_00 != (undefined4 *)(param_1 + 0x18)) {
    do {
      puVar2 = *(undefined4 **)_Memory_00[2];
      _Memory = (undefined4 *)_Memory_00[2];
      while (puVar1 = puVar2, _Memory != _Memory_00 + 2) {
        free((void *)_Memory[3]);
        free(_Memory);
        puVar2 = (undefined4 *)*puVar1;
        _Memory = puVar1;
      }
      free(_Memory_00);
      bVar3 = local_c != (undefined4 *)(param_1 + 0x18);
      _Memory_00 = local_c;
      local_c = (undefined4 *)*local_c;
    } while (bVar3);
  }
  if (*(void **)(param_1 + 0x24) != (void *)0x0) {
    free(*(void **)(param_1 + 0x24));
  }
  return 0;
}



/* 1000a080 FUN_1000a080 */

undefined4 __cdecl FUN_1000a080(int param_1)

{
  undefined4 *puVar1;
  undefined4 *_Memory;
  undefined4 *puVar2;
  undefined4 *_Memory_00;
  bool bVar3;
  undefined4 *local_c;
  
  _Memory_00 = *(undefined4 **)(param_1 + 0xc);
  local_c = (undefined4 *)*_Memory_00;
  if (_Memory_00 != (undefined4 *)(param_1 + 0xc)) {
    do {
      puVar2 = *(undefined4 **)_Memory_00[2];
      _Memory = (undefined4 *)_Memory_00[2];
      while (puVar1 = puVar2, _Memory != _Memory_00 + 2) {
        free((void *)_Memory[3]);
        free(_Memory);
        puVar2 = (undefined4 *)*puVar1;
        _Memory = puVar1;
      }
      free(_Memory_00);
      bVar3 = local_c != (undefined4 *)(param_1 + 0xc);
      _Memory_00 = local_c;
      local_c = (undefined4 *)*local_c;
    } while (bVar3);
  }
  if (*(void **)(param_1 + 0x18) != (void *)0x0) {
    free(*(void **)(param_1 + 0x18));
  }
  return 0;
}



/* 1000a110 FUN_1000a110 */

undefined4 __cdecl FUN_1000a110(int param_1)

{
  undefined4 *puVar1;
  int *_Memory;
  undefined4 *puVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  bool bVar5;
  
  _Memory = *(int **)(param_1 + 4);
  puVar3 = (undefined4 *)*_Memory;
  if (_Memory != (int *)(param_1 + 4)) {
    do {
      free((void *)_Memory[3]);
      free(_Memory);
      bVar5 = puVar3 != (undefined4 *)(param_1 + 4);
      _Memory = puVar3;
      puVar3 = (undefined4 *)*puVar3;
    } while (bVar5);
  }
  puVar2 = (undefined4 *)**(undefined4 **)(param_1 + 0xc);
  puVar3 = *(undefined4 **)(param_1 + 0xc);
  while (puVar1 = puVar2, puVar3 != (undefined4 *)(param_1 + 0xc)) {
    puVar2 = (undefined4 *)puVar3[4];
    puVar4 = (undefined4 *)*puVar2;
    if (puVar2 != puVar3 + 4) {
      do {
        free((void *)puVar2[3]);
        free(puVar2);
        bVar5 = puVar4 != puVar3 + 4;
        puVar2 = puVar4;
        puVar4 = (undefined4 *)*puVar4;
      } while (bVar5);
    }
    free(puVar3);
    puVar2 = (undefined4 *)*puVar1;
    puVar3 = puVar1;
  }
  return 0;
}



/* 1000a1b0 FUN_1000a1b0 */

int __cdecl FUN_1000a1b0(undefined4 *param_1,size_t *param_2)

{
  byte *pbVar1;
  int ***pppiVar2;
  int iVar3;
  void *_Dst;
  int ****ppppiVar4;
  undefined1 *unaff_ESI;
  int iVar5;
  size_t _Size;
  undefined1 local_2c [2];
  undefined2 local_2a;
  int ***local_28;
  int *local_24;
  int ***local_20;
  int *local_1c;
  undefined2 local_18;
  short local_16;
  short local_14;
  undefined2 local_12;
  uint local_c;
  int ***local_8;
  
  local_2a = *(undefined2 *)(unaff_ESI + 2);
  local_2c[0] = *unaff_ESI;
  local_24 = *(int **)(unaff_ESI + 8);
  local_28 = *(int ****)(unaff_ESI + 4);
  local_12 = 0;
  local_28[1] = (int **)&local_28;
  **(int **)(unaff_ESI + 8) = (int)&local_28;
  local_20 = *(int ****)(unaff_ESI + 0xc);
  local_1c = *(int **)(unaff_ESI + 0x10);
  local_20[1] = (int **)&local_20;
  **(int **)(unaff_ESI + 0x10) = (int)&local_20;
  iVar3 = 0;
  local_c = 0;
  ppppiVar4 = (int ****)local_28;
  if ((int ****)local_28 != &local_28) {
    do {
      pbVar1 = (byte *)((int)ppppiVar4 + 9);
      ppppiVar4 = (int ****)*ppppiVar4;
      iVar3 = iVar3 + 2 + (uint)*pbVar1;
    } while (ppppiVar4 != &local_28);
  }
  iVar5 = iVar3 + 0xc;
  local_18 = (undefined2)iVar3;
  local_16 = 0;
  local_8 = local_20;
  if ((int ****)local_20 != &local_20) {
    do {
      iVar3 = 0;
      for (ppppiVar4 = (int ****)local_8[4]; ppppiVar4 != (int ****)(local_8 + 4);
          ppppiVar4 = (int ****)*ppppiVar4) {
        iVar3 = iVar3 + 2 + (uint)*(byte *)((int)ppppiVar4 + 9);
      }
      *(short *)(local_8 + 3) = (short)iVar3;
      iVar5 = iVar5 + 6 + iVar3;
      local_16 = local_16 + (short)iVar3 + 6;
      local_8 = (int ***)*local_8;
    } while ((int ****)local_8 != &local_20);
  }
  _Size = iVar5 + 4;
  _Dst = malloc(_Size);
  *param_1 = _Dst;
  if (_Dst == (void *)0x0) {
    iVar3 = 0x16;
  }
  else {
    local_14 = (short)iVar5 + 1;
    *param_2 = _Size;
    memset(_Dst,0,_Size);
    iVar5 = FUN_1000bc70((int)_Dst,_Size,&local_c,(uint)local_2c,0x12,0x10015420);
    iVar3 = 0;
    if (iVar5 != 0) {
      printf("WriteToSection for NIT failed(%d)\n",iVar5);
      iVar3 = 0x1a;
    }
  }
  *(int ****)(unaff_ESI + 4) = local_28;
  *(int **)(unaff_ESI + 8) = local_24;
  local_28[1] = (int **)(unaff_ESI + 4);
  *local_24 = (int)(unaff_ESI + 4);
  pppiVar2 = (int ***)(unaff_ESI + 0xc);
  *pppiVar2 = (int **)local_20;
  *(int **)(unaff_ESI + 0x10) = local_1c;
  local_20[1] = (int **)pppiVar2;
  *local_1c = (int)pppiVar2;
  if (iVar3 != 0) {
    free((void *)*param_1);
  }
  return iVar3;
}



/* 1000a330 FUN_1000a330 */

undefined4 __fastcall FUN_1000a330(undefined4 param_1,int *param_2)

{
  int *piVar1;
  int iVar2;
  undefined1 *unaff_ESI;
  undefined1 local_28 [2];
  undefined2 local_26;
  int local_24;
  int *local_20;
  int local_1c;
  int *local_18;
  undefined4 local_8;
  
  local_8 = 0;
  iVar2 = FUN_1000b3f0((int *)&DAT_1005ab48,param_2,&local_8,(int *)local_28,0x12,0x10015420);
  if (iVar2 != 0) {
    printf("ReadFromSection for SDT failed(%d)\n",iVar2);
    return 0x1a;
  }
  *(undefined2 *)(unaff_ESI + 2) = local_26;
  *(int *)(unaff_ESI + 4) = local_24;
  *unaff_ESI = local_28[0];
  *(int **)(unaff_ESI + 8) = local_20;
  *(undefined1 **)(local_24 + 4) = unaff_ESI + 4;
  *local_20 = (int)(unaff_ESI + 4);
  piVar1 = (int *)(unaff_ESI + 0xc);
  *piVar1 = local_1c;
  *(int **)(unaff_ESI + 0x10) = local_18;
  *(int **)(local_1c + 4) = piVar1;
  *local_18 = (int)piVar1;
  return 0;
}



/* 1000a3c0 FUN_1000a3c0 */

undefined4 __cdecl FUN_1000a3c0(FILE *param_1,int *param_2,int *param_3,int param_4)

{
  ushort uVar1;
  ushort uVar2;
  FILE *_File;
  undefined1 uVar3;
  uint uVar4;
  void *pvVar5;
  uint uVar6;
  size_t sVar7;
  errno_t eVar8;
  undefined4 uVar9;
  int iVar10;
  int iVar11;
  int iVar12;
  byte *pbVar13;
  int unaff_EBX;
  undefined4 *puVar14;
  undefined4 *puVar15;
  char *_Filename;
  undefined4 local_40;
  undefined1 local_3c [2];
  undefined1 uStack_3a;
  undefined1 uStack_39;
  undefined3 local_38;
  undefined1 uStack_35;
  undefined1 local_34 [3];
  undefined1 uStack_31;
  undefined1 local_30;
  undefined1 auStack_2f [2];
  char cStack_2d;
  undefined1 local_2c;
  undefined1 uStack_2b;
  undefined1 uStack_2a;
  undefined1 uStack_29;
  undefined4 local_28;
  undefined2 local_24;
  uint local_20;
  FILE *local_1c;
  undefined4 local_18;
  uint local_14;
  int local_10;
  undefined4 local_c;
  undefined4 local_8;
  
  uVar6 = *(int *)(unaff_EBX + 0x20) * 0x1e;
  uVar4 = uVar6 / *(ushort *)(unaff_EBX + 0x1e);
  local_18 = 0;
  local_c = 0xfe01ff00;
  local_10 = 0;
  if (uVar6 % (uint)*(ushort *)(unaff_EBX + 0x1e) != 0) {
    uVar4 = uVar4 + 0x1e;
  }
  pvVar5 = malloc(*(int *)(unaff_EBX + 0x20) + (uint)*(ushort *)(unaff_EBX + 0x1c) * 0x1e + uVar4);
  *param_2 = (int)pvVar5;
  if (pvVar5 == (void *)0x0) {
    printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
    return 0x16;
  }
  uVar1 = *(ushort *)(unaff_EBX + 0x14);
  local_24 = 0;
  uVar6 = *(ushort *)(unaff_EBX + 0x1e) + 0x1b;
  local_40 = (uVar6 & 0xff) << 0x10;
  local_8 = CONCAT22(uVar1 >> 8,(undefined2)local_8);
  uVar3 = (undefined1)(uVar1 >> 8);
  local_40 = CONCAT13(uVar3,(undefined3)local_40);
  local_3c = (undefined1  [2])
             CONCAT11((*(byte *)(unaff_EBX + 0x16) & 0x1f) * '\x02' + -0x3f,
                      *(undefined1 *)(unaff_EBX + 0x14));
  uVar2 = *(ushort *)(unaff_EBX + 0x18);
  local_14 = (uint)uVar2;
  _local_3c = CONCAT12(0,local_3c);
  _local_38 = CONCAT13(*(undefined1 *)(unaff_EBX + 10),
                       CONCAT12(*(undefined1 *)(unaff_EBX + 0xb),*(undefined2 *)(unaff_EBX + 8)));
  local_34._0_2_ = CONCAT11(*(undefined1 *)(unaff_EBX + 0xe),*(undefined1 *)(unaff_EBX + 0xf));
  _local_34 = CONCAT13(*(undefined1 *)(unaff_EBX + 0xc),
                       CONCAT12(*(undefined1 *)(unaff_EBX + 0xd),local_34._0_2_));
  local_40 = CONCAT31(CONCAT21(local_40._2_2_,((byte)(uVar6 >> 8) & 0xf) + 0xb0),0x3c);
  _local_3c = CONCAT13((char)(*(uint *)(unaff_EBX + 0x20) / (uint)*(ushort *)(unaff_EBX + 0x1e)),
                       _local_3c);
  _local_30 = CONCAT31(CONCAT12(*(undefined1 *)(unaff_EBX + 0x12),
                                CONCAT11(*(undefined1 *)(unaff_EBX + 0x13),
                                         *(undefined1 *)(unaff_EBX + 0x10))),0xff);
  _local_2c = CONCAT13(0xff,CONCAT12(*(undefined1 *)(unaff_EBX + 0x16),
                                     CONCAT11(*(undefined1 *)(unaff_EBX + 0x14),uVar3)));
  local_28 = (uint)CONCAT11((char)uVar2,(char)(uVar2 >> 8));
  if (uVar1 < DAT_1005aa40) {
    uVar1 = *(ushort *)(unaff_EBX + 0x1c);
    *param_3 = 0;
    if (uVar1 < uVar2) {
      return local_18;
    }
    while( true ) {
      uVar6 = *(int *)(unaff_EBX + 0x20) - local_10;
      local_8 = (uint)*(ushort *)(unaff_EBX + 0x1e);
      if (uVar6 < *(ushort *)(unaff_EBX + 0x1e)) {
        local_8 = uVar6;
      }
      uVar6 = local_8 + 0x1b & 0xffff;
      local_40._0_2_ = CONCAT11(((byte)(uVar6 >> 8) & 0xf) + 0xb0,(undefined1)local_40);
      local_40 = CONCAT22(CONCAT11(local_40._3_1_,(char)uVar6),(undefined2)local_40);
      _local_3c = CONCAT12((char)local_14,local_3c);
      _local_30 = CONCAT12((char)(local_8 + 6 >> 8),_local_30);
      _local_30 = CONCAT13((char)local_8 + '\x06',_local_30);
      iVar12 = *param_3;
      local_28 = CONCAT31(CONCAT21(local_28._2_2_,(char)local_14),(char)(local_14 >> 8));
      iVar11 = *param_2;
      puVar14 = &local_40;
      puVar15 = (undefined4 *)(iVar12 + iVar11);
      for (iVar10 = 6; iVar10 != 0; iVar10 = iVar10 + -1) {
        *puVar15 = *puVar14;
        puVar14 = puVar14 + 1;
        puVar15 = puVar15 + 1;
      }
      iVar12 = iVar12 + 0x1a;
      *param_3 = iVar12;
      *(undefined2 *)puVar15 = *(undefined2 *)puVar14;
      sVar7 = fread((void *)(iVar12 + iVar11),1,local_8,param_1);
      if (sVar7 != local_8) break;
      local_1c = (FILE *)*param_2;
      local_10 = local_10 + local_8;
      uVar6 = 0xffffffff;
      pbVar13 = (byte *)(*param_3 + -0x1a + (int)local_1c);
      for (iVar12 = local_8 + 0x1a; iVar12 != 0; iVar12 = iVar12 + -1) {
        uVar6 = uVar6 << 8 ^ *(uint *)(&DAT_10014628 + (uVar6 >> 0x18 ^ (uint)*pbVar13) * 4);
        pbVar13 = pbVar13 + 1;
      }
      iVar12 = *param_3;
      local_c._0_2_ = CONCAT11((char)(uVar6 >> 0x10),(char)(uVar6 >> 0x18));
      local_c = CONCAT22(CONCAT11((char)uVar6,(char)(uVar6 >> 8)),(undefined2)local_c);
      *(undefined4 *)((int)&local_1c->_ptr + iVar12 + local_8) = local_c;
      *param_3 = iVar12 + local_8 + 4;
      local_14 = local_14 + 1;
      if (*(ushort *)(unaff_EBX + 0x1c) < (ushort)local_14) {
        return local_18;
      }
    }
    goto LAB_1000a7cb;
  }
  local_10 = 0;
  local_1c = (FILE *)0x0;
  if (param_4 == 1) {
    _Filename = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\0218.dcf";
LAB_1000a684:
    eVar8 = fopen_s(&local_1c,_Filename,"rb");
    if (eVar8 != 0) {
      return 7;
    }
  }
  else if (param_4 == 2) {
    _Filename = "C:\\Philips\\HotelTV\\2K15_MediaSuite\\021b.dcf";
    goto LAB_1000a684;
  }
  _File = local_1c;
  *param_3 = 0;
  if (*(ushort *)(unaff_EBX + 0x1c) < (ushort)local_14) {
    return local_18;
  }
  while( true ) {
    uVar9 = local_40;
    uVar6 = (uint)*(ushort *)(unaff_EBX + 0x1e);
    local_8 = *(int *)(unaff_EBX + 0x20) - local_10;
    if (local_8 < uVar6) {
    }
    else {
      local_8 = uVar6;
    }
    local_20 = local_8 + 0x1b & 0xffff;
    local_40._0_2_ = CONCAT11(((byte)(local_20 >> 8) & 0xf) + 0xb0,(undefined1)local_40);
    uVar6 = local_20;
    local_40._3_1_ = SUB41(uVar9,3);
    local_40._0_3_ = CONCAT12((undefined1)local_20,(undefined2)local_40);
    _local_30 = CONCAT12((char)(local_8 + 6 >> 8),_local_30);
    _local_3c = CONCAT12((char)local_14,local_3c);
    _local_30 = CONCAT13((char)local_8 + '\x06',_local_30);
    local_28 = CONCAT31(CONCAT21(local_28._2_2_,(char)local_14),(char)(local_14 >> 8));
    iVar12 = *param_2;
    puVar14 = &local_40;
    puVar15 = (undefined4 *)(*param_3 + iVar12);
    for (iVar11 = 6; iVar11 != 0; iVar11 = iVar11 + -1) {
      *puVar15 = *puVar14;
      puVar14 = puVar14 + 1;
      puVar15 = puVar15 + 1;
    }
    iVar11 = *param_3;
    *(undefined2 *)puVar15 = *(undefined2 *)puVar14;
    *param_3 = iVar11 + 0x1a;
    local_20 = uVar6;
    sVar7 = fread((void *)(iVar11 + 0x1a + iVar12),1,local_8,_File);
    if (sVar7 != local_8) break;
    iVar12 = *param_2;
    iVar11 = *param_3;
    local_10 = local_10 + local_8;
    uVar9 = FUN_1000b3b0(local_8 + 0x1a);
    local_c._0_2_ = CONCAT11((char)((uint)uVar9 >> 0x10),(char)((uint)uVar9 >> 0x18));
    local_c = CONCAT13((char)uVar9,CONCAT12((char)((uint)uVar9 >> 8),(undefined2)local_c));
    *(undefined4 *)(iVar11 + local_8 + iVar12) = local_c;
    *param_3 = iVar11 + local_8 + 4;
    local_14 = local_14 + 1;
    if (*(ushort *)(unaff_EBX + 0x1c) < (ushort)local_14) {
      return local_18;
    }
  }
LAB_1000a7cb:
  printf("001-0 Cannot read required amount of bytes(%d) after %d bytes\n",local_8,local_10);
  return 0x1a;
}



/* 1000a7f0 FUN_1000a7f0 */

undefined4 __cdecl FUN_1000a7f0(ushort param_1,undefined4 param_2,undefined4 *param_3)

{
  int iVar1;
  void *pvVar2;
  
  pvVar2 = malloc(0x18);
  *param_3 = pvVar2;
  if (pvVar2 == (void *)0x0) {
    return 0x16;
  }
  *(uint *)((int)pvVar2 + 8) = (uint)param_1;
  *(undefined4 *)((int)pvVar2 + 0xc) = param_2;
  iVar1 = (int)pvVar2 + 0x10;
  *(int *)iVar1 = iVar1;
  *(int *)((int)pvVar2 + 0x14) = iVar1;
  return 0;
}



/* 1000a830 FUN_1000a830 */

undefined4 __cdecl
FUN_1000a830(undefined4 param_1,undefined4 param_2,void *param_3,undefined4 *param_4)

{
  undefined4 *puVar1;
  undefined4 *_Memory;
  void *_Dst;
  size_t unaff_EBX;
  
  _Memory = malloc(0x20);
  *param_4 = _Memory;
  if (_Memory == (undefined4 *)0x0) {
    printf("Insufficient memory!! Failed to create group_info\n");
    return 0x16;
  }
  *_Memory = 0;
  _Memory[1] = 0;
  _Memory[2] = 0;
  _Memory[3] = 0;
  _Memory[6] = 0;
  _Memory[7] = 0;
  _Memory[4] = param_1;
  _Memory[5] = param_2;
  if (param_3 != (void *)0x0) {
    *(short *)(_Memory + 6) = (short)unaff_EBX;
    _Dst = malloc(unaff_EBX);
    _Memory[7] = _Dst;
    if (_Dst == (void *)0x0) {
      free(_Memory);
      return 0x16;
    }
    memcpy(_Dst,param_3,unaff_EBX);
  }
  *_Memory = _Memory;
  puVar1 = _Memory + 2;
  _Memory[1] = _Memory;
  *puVar1 = puVar1;
  _Memory[3] = puVar1;
  return 0;
}



/* 1000a8d0 FUN_1000a8d0 */

int __cdecl FUN_1000a8d0(undefined4 param_1,undefined4 *param_2)

{
  void *_Dst;
  int iVar1;
  size_t *unaff_EBX;
  size_t _Size;
  undefined1 local_38 [28];
  ushort local_1c;
  uint local_8;
  
  local_8 = 0;
  FUN_1000ab60((int)local_38);
  _Size = local_1c + 3;
  *unaff_EBX = _Size;
  _Dst = malloc(_Size);
  *param_2 = _Dst;
  if (_Dst == (void *)0x0) {
    printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
    FUN_1000adc0();
    return 0x16;
  }
  memset(_Dst,0,_Size);
  iVar1 = FUN_1000bc70((int)_Dst,_Size,&local_8,(uint)local_38,0x19,0x10015150);
  if (iVar1 != 0) {
    printf("WriteToSection failed for DSI(%d)\n",iVar1);
  }
  FUN_1000adc0();
  return iVar1;
}



/* 1000a980 FUN_1000a980 */

undefined4 __fastcall FUN_1000a980(undefined4 param_1,int *param_2,int param_3)

{
  int iVar1;
  undefined4 extraout_ECX;
  undefined4 extraout_ECX_00;
  undefined4 uVar2;
  undefined4 uVar3;
  int local_3c [12];
  undefined4 local_c [2];
  
  uVar3 = 0;
  local_c[0] = 0;
  iVar1 = FUN_1000b3f0((int *)&DAT_1005ab48,param_2,local_c,local_3c,0x19,0x10015150);
  uVar2 = extraout_ECX;
  if (iVar1 != 0) {
    printf("ReadFromSection for DSI failed(%d)\n",iVar1);
    uVar3 = 0x1a;
    uVar2 = extraout_ECX_00;
  }
  iVar1 = FUN_1000aa00(uVar2,(int)local_3c,param_3);
  if (iVar1 != 0) {
    printf("copy_2_dsi failed(%d)\n",iVar1);
    uVar3 = 0x1a;
  }
  FUN_1000adc0();
  return uVar3;
}



/* 1000aa00 FUN_1000aa00 */

int __fastcall FUN_1000aa00(undefined4 param_1,int param_2,int param_3)

{
  int *piVar1;
  undefined2 uVar2;
  undefined4 *puVar3;
  int iVar4;
  int iVar5;
  int *piVar6;
  int *piVar7;
  int *local_14;
  int *local_10;
  undefined1 local_c;
  undefined1 local_8;
  
  *(undefined4 *)(param_3 + 0xc) = *(undefined4 *)(param_2 + 0xc);
  iVar5 = param_3 + 0x10;
  *(int *)iVar5 = iVar5;
  *(int *)(param_3 + 0x14) = iVar5;
  piVar1 = (int *)(param_2 + 0x10);
  piVar6 = (int *)*piVar1;
  iVar5 = 0;
  if (piVar6 != piVar1) {
    while (local_10 = piVar6, iVar5 = FUN_1000a830(piVar6[4],piVar6[5],(void *)piVar6[7],&local_14),
          iVar5 == 0) {
      piVar7 = local_14 + 2;
      *piVar7 = (int)piVar7;
      local_14[3] = (int)piVar7;
      piVar7 = (int *)piVar6[2];
      if (piVar7 != piVar6 + 2) {
        do {
          iVar5 = piVar7[3];
          local_c = *(undefined1 *)((int)piVar7 + 10);
          local_8 = (undefined1)piVar7[2];
          uVar2 = *(undefined2 *)((int)piVar7 + 0x12);
          iVar4 = piVar7[4];
          piVar6 = malloc(0x18);
          if (piVar6 == (int *)0x0) {
            iVar5 = 0x16;
          }
          else {
            piVar6[2] = 0;
            piVar6[4] = 0;
            piVar6[5] = 0;
            *(undefined1 *)(piVar6 + 2) = local_8;
            piVar6[3] = iVar5;
            iVar5 = 0;
            *(undefined1 *)((int)piVar6 + 10) = local_c;
            *(short *)(piVar6 + 4) = (short)iVar4;
            *(undefined2 *)((int)piVar6 + 0x12) = uVar2;
            *(undefined1 *)((int)piVar6 + 9) = 9;
            *piVar6 = (int)piVar6;
            piVar6[1] = (int)piVar6;
          }
          if (iVar5 != 0) goto LAB_1000ab41;
          puVar3 = (undefined4 *)local_14[3];
          local_14[3] = (int)piVar6;
          *piVar6 = (int)(local_14 + 2);
          piVar6[1] = (int)puVar3;
          *puVar3 = piVar6;
          piVar7 = (int *)*piVar7;
          piVar6 = local_10;
        } while (piVar7 != local_10 + 2);
      }
      puVar3 = *(undefined4 **)(param_3 + 0x14);
      *(int **)(param_3 + 0x14) = local_14;
      *local_14 = param_3 + 0x10;
      local_14[1] = (int)puVar3;
      *puVar3 = local_14;
      piVar6 = (int *)*piVar6;
      if (piVar6 == piVar1) {
        return 0;
      }
    }
    printf("create_dsi_group_info failed(%d)\n",iVar5);
LAB_1000ab41:
    FUN_1000ae50();
  }
  return iVar5;
}



/* 1000ab60 FUN_1000ab60 */

/* WARNING: Removing unreachable block (ram,0x1000ad94) */

int __cdecl FUN_1000ab60(int param_1)

{
  int *piVar1;
  int *piVar2;
  undefined1 uVar3;
  undefined2 uVar4;
  ushort uVar5;
  undefined4 *puVar6;
  int iVar7;
  int iVar8;
  short sVar9;
  int in_EAX;
  undefined4 *puVar10;
  int iVar11;
  int *piVar12;
  void *_Dst;
  int *piVar13;
  int *piVar14;
  int local_10;
  
  *(undefined4 *)(param_1 + 0xc) = *(undefined4 *)(in_EAX + 0xc);
  iVar11 = param_1 + 0x10;
  *(undefined2 *)(param_1 + 0x2a) = *(undefined2 *)(in_EAX + 0xc);
  *(int *)iVar11 = iVar11;
  *(int *)(param_1 + 0x14) = iVar11;
  puVar10 = malloc(0x14);
  *(undefined4 **)(param_1 + 0x2c) = puVar10;
  if (puVar10 != (undefined4 *)0x0) {
    *puVar10 = 0xffffffff;
    puVar10[1] = 0xffffffff;
    puVar10[2] = 0xffffffff;
    puVar10[3] = 0xffffffff;
    puVar10[4] = 0xffffffff;
    sVar9 = 0x2c;
    piVar1 = (int *)(in_EAX + 0x10);
    *(undefined4 *)(param_1 + 0x18) = 0;
    *(undefined1 *)(param_1 + 0x20) = 0;
    *(undefined2 *)(param_1 + 0x1e) = 0;
    piVar13 = (int *)*piVar1;
    if (piVar13 != piVar1) {
      iVar11 = 0x2e;
      do {
        *(short *)(param_1 + 0x1a) = *(short *)(param_1 + 0x1a) + 1;
        local_10 = iVar11 + 0xe;
        piVar12 = malloc(0x24);
        if (piVar12 == (int *)0x0) goto LAB_1000ab9d;
        *piVar12 = 0;
        piVar12[1] = 0;
        piVar12[2] = 0;
        piVar12[4] = 0;
        piVar12[5] = 0;
        piVar12[6] = 0;
        piVar12[7] = 0;
        piVar12[8] = 0;
        piVar2 = piVar12 + 2;
        *piVar2 = (int)piVar2;
        piVar12[3] = (int)piVar2;
        piVar12[4] = piVar13[4];
        piVar2 = piVar13 + 2;
        piVar12[5] = piVar13[5];
        if ((int *)*piVar2 != piVar2) {
          local_10 = iVar11 + 0x10;
          *(undefined2 *)((int)piVar12 + 0x22) = 2;
        }
        piVar14 = (int *)*piVar2;
        if (piVar14 != piVar2) {
          do {
            *(short *)(piVar12 + 8) = (short)piVar12[8] + 1;
            iVar7 = piVar14[4];
            iVar11 = piVar14[3];
            uVar3 = *(undefined1 *)((int)piVar14 + 10);
            uVar4 = *(undefined2 *)((int)piVar14 + 0x12);
            iVar8 = piVar14[2];
            puVar10 = malloc(0x18);
            if (puVar10 == (undefined4 *)0x0) {
              iVar11 = 0x16;
            }
            else {
              puVar10[2] = 0;
              puVar10[4] = 0;
              puVar10[5] = 0;
              *(char *)(puVar10 + 2) = (char)iVar8;
              *(undefined1 *)((int)puVar10 + 10) = uVar3;
              puVar10[3] = iVar11;
              iVar11 = 0;
              *(short *)(puVar10 + 4) = (short)iVar7;
              *(undefined2 *)((int)puVar10 + 0x12) = uVar4;
              *(undefined1 *)((int)puVar10 + 9) = 9;
              *puVar10 = puVar10;
              puVar10[1] = puVar10;
            }
            if (iVar11 != 0) {
              FUN_1000adc0();
              return iVar11;
            }
            *(short *)((int)piVar12 + 0x22) = *(short *)((int)piVar12 + 0x22) + 0xb;
            local_10 = local_10 + 0xb;
            puVar6 = (undefined4 *)piVar12[3];
            piVar12[3] = (int)puVar10;
            *puVar10 = piVar12 + 2;
            puVar10[1] = puVar6;
            *puVar6 = puVar10;
            piVar14 = (int *)*piVar14;
          } while (piVar14 != piVar13 + 2);
        }
        uVar5 = *(ushort *)(piVar13 + 6);
        iVar11 = local_10 + (uint)uVar5;
        sVar9 = (short)iVar11;
        *(ushort *)(piVar12 + 6) = uVar5;
        if (uVar5 != 0) {
          _Dst = malloc((uint)uVar5);
          piVar12[7] = (int)_Dst;
          if (_Dst == (void *)0x0) goto LAB_1000ab9d;
          memcpy(_Dst,(void *)piVar13[7],(uint)*(ushort *)(piVar12 + 6));
        }
        puVar10 = *(undefined4 **)(param_1 + 0x14);
        *(int **)(param_1 + 0x14) = piVar12;
        *piVar12 = param_1 + 0x10;
        piVar12[1] = (int)puVar10;
        *puVar10 = piVar12;
        piVar13 = (int *)*piVar13;
      } while (piVar13 != piVar1);
    }
    *(short *)(param_1 + 0x28) = sVar9 + -0x14;
    *(short *)(param_1 + 0x18) = sVar9 + -0x2c;
    *(short *)(param_1 + 0x1c) = sVar9 + 1;
    return 0;
  }
LAB_1000ab9d:
  FUN_1000adc0();
  return -1;
}



/* 1000adc0 FUN_1000adc0 */

undefined4 FUN_1000adc0(void)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  undefined4 *_Memory;
  undefined4 *puVar3;
  undefined4 *puVar4;
  int in_EAX;
  
  free(*(void **)(in_EAX + 0x2c));
  puVar3 = (undefined4 *)**(undefined4 **)(in_EAX + 0x10);
  _Memory = *(undefined4 **)(in_EAX + 0x10);
  while (puVar1 = puVar3, _Memory != (undefined4 *)(in_EAX + 0x10)) {
    puVar4 = *(undefined4 **)_Memory[2];
    puVar3 = (undefined4 *)_Memory[2];
    while (puVar2 = puVar4, puVar3 != _Memory + 2) {
      free(puVar3);
      puVar4 = (undefined4 *)*puVar2;
      puVar3 = puVar2;
    }
    if ((void *)_Memory[7] != (void *)0x0) {
      free((void *)_Memory[7]);
    }
    free(_Memory);
    puVar3 = (undefined4 *)*puVar1;
    _Memory = puVar1;
  }
  return 0;
}



/* 1000ae50 FUN_1000ae50 */

undefined4 FUN_1000ae50(void)

{
  undefined4 *_Memory;
  undefined4 *puVar1;
  undefined4 *puVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  int in_EAX;
  
  _Memory = *(undefined4 **)(in_EAX + 0x10);
  puVar3 = (undefined4 *)*_Memory;
  while (puVar1 = puVar3, _Memory != (undefined4 *)(in_EAX + 0x10)) {
    puVar4 = *(undefined4 **)_Memory[2];
    puVar3 = (undefined4 *)_Memory[2];
    while (puVar2 = puVar4, puVar3 != _Memory + 2) {
      free(puVar3);
      puVar4 = (undefined4 *)*puVar2;
      puVar3 = puVar2;
    }
    if ((void *)_Memory[7] != (void *)0x0) {
      free((void *)_Memory[7]);
    }
    free(_Memory);
    puVar3 = (undefined4 *)*puVar1;
    _Memory = puVar1;
  }
  return 0;
}



/* 1000aed0 FUN_1000aed0 */

undefined4 __fastcall FUN_1000aed0(int param_1)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  undefined4 *puVar3;
  undefined4 *_Memory;
  undefined4 *puVar4;
  
  puVar2 = (undefined4 *)**(undefined4 **)(param_1 + 8);
  _Memory = *(undefined4 **)(param_1 + 8);
  while (puVar3 = puVar2, _Memory != (undefined4 *)(param_1 + 8)) {
    puVar4 = *(undefined4 **)_Memory[4];
    puVar2 = (undefined4 *)_Memory[4];
    while (puVar1 = puVar4, puVar2 != _Memory + 4) {
      free((void *)puVar2[3]);
      free(puVar2);
      puVar4 = (undefined4 *)*puVar1;
      puVar2 = puVar1;
    }
    free(_Memory);
    puVar2 = (undefined4 *)*puVar3;
    _Memory = puVar3;
  }
  return 0;
}



/* 1000af50 FUN_1000af50 */

int __cdecl FUN_1000af50(undefined4 *param_1)

{
  int iVar1;
  undefined4 *puVar2;
  int in_EAX;
  void *pvVar3;
  int *_Memory;
  void *_Dst;
  int *piVar4;
  int *piVar5;
  
  pvVar3 = malloc(0x18);
  *param_1 = pvVar3;
  if (pvVar3 == (void *)0x0) {
    return 0x16;
  }
  *(undefined2 *)((int)pvVar3 + 8) = *(undefined2 *)(in_EAX + 8);
  *(undefined1 *)((int)pvVar3 + 10) = *(undefined1 *)(in_EAX + 10);
  *(undefined1 *)((int)pvVar3 + 0xb) = *(undefined1 *)(in_EAX + 0xb);
  *(undefined1 *)((int)pvVar3 + 0xc) = *(undefined1 *)(in_EAX + 0xc);
  *(undefined1 *)((int)pvVar3 + 0xd) = *(undefined1 *)(in_EAX + 0xd);
  *(undefined2 *)((int)pvVar3 + 0xe) = *(undefined2 *)(in_EAX + 0xe);
  iVar1 = (int)pvVar3 + 0x10;
  piVar4 = (int *)(in_EAX + 0x10);
  *(int *)iVar1 = iVar1;
  *(int *)((int)pvVar3 + 0x14) = iVar1;
  piVar5 = (int *)*piVar4;
  if (piVar5 == piVar4) {
    param_1 = (undefined4 *)0x0;
  }
  else {
    while( true ) {
      param_1 = (undefined4 *)0x0;
      _Memory = malloc(0x10);
      if (_Memory == (int *)0x0) {
        param_1 = (undefined4 *)0x16;
      }
      else {
        *_Memory = 0;
        _Memory[1] = 0;
        _Memory[2] = 0;
        _Memory[3] = 0;
        *(char *)(_Memory + 2) = (char)piVar5[2];
        *(undefined1 *)((int)_Memory + 9) = *(undefined1 *)((int)piVar5 + 9);
        _Dst = malloc((uint)*(byte *)((int)piVar5 + 9));
        _Memory[3] = (int)_Dst;
        if (_Dst == (void *)0x0) {
          param_1 = (undefined4 *)0x16;
          free(_Memory);
        }
        else {
          memcpy(_Dst,(void *)piVar5[3],(uint)*(byte *)((int)piVar5 + 9));
        }
      }
      if (param_1 != (undefined4 *)0x0) break;
      puVar2 = *(undefined4 **)((int)pvVar3 + 0x14);
      *(int **)((int)pvVar3 + 0x14) = _Memory;
      *_Memory = iVar1;
      _Memory[1] = (int)puVar2;
      *puVar2 = _Memory;
      piVar5 = (int *)*piVar5;
      if (piVar5 == piVar4) {
        return 0;
      }
    }
  }
  return (int)param_1;
}



/* 1000b070 FUN_1000b070 */

int __cdecl FUN_1000b070(undefined4 *param_1,size_t *param_2)

{
  int ***pppiVar1;
  int ****ppppiVar2;
  undefined2 *in_EAX;
  void *_Dst;
  int iVar3;
  int iVar4;
  size_t _Size;
  int ****ppppiVar5;
  undefined2 local_24;
  undefined1 local_22;
  undefined2 local_20;
  int ***local_1c;
  int *local_18;
  short local_14;
  undefined2 local_12;
  uint local_c;
  int **local_8;
  
  local_24 = *in_EAX;
  local_20 = in_EAX[2];
  local_22 = *(undefined1 *)(in_EAX + 1);
  local_18 = *(int **)(in_EAX + 6);
  local_1c = *(int ****)(in_EAX + 4);
  pppiVar1 = (int ***)(in_EAX + 4);
  local_12 = 0;
  local_1c[1] = (int **)&local_1c;
  **(int **)(in_EAX + 6) = (int)&local_1c;
  local_c = 0;
  iVar4 = 0xb;
  ppppiVar5 = (int ****)local_1c;
  if ((int ****)local_1c != &local_1c) {
    do {
      iVar3 = 0;
      for (ppppiVar2 = (int ****)ppppiVar5[4]; ppppiVar2 != ppppiVar5 + 4;
          ppppiVar2 = (int ****)*ppppiVar2) {
        iVar3 = iVar3 + 2 + (uint)*(byte *)((int)ppppiVar2 + 9);
      }
      *(short *)((int)ppppiVar5 + 0xe) = (short)iVar3;
      ppppiVar5 = (int ****)*ppppiVar5;
      iVar4 = iVar4 + 5 + iVar3;
    } while (ppppiVar5 != &local_1c);
  }
  _Size = iVar4 + 4;
  local_8 = (int **)pppiVar1;
  _Dst = malloc(_Size);
  *param_1 = _Dst;
  if (_Dst == (void *)0x0) {
    printf("OUT OF MEMORY!! Failed to allocate memory for dsmcc section\n");
    iVar4 = 0x16;
  }
  else {
    local_14 = (short)iVar4 + 1;
    *param_2 = _Size;
    memset(_Dst,0,_Size);
    iVar3 = FUN_1000bc70((int)_Dst,_Size,&local_c,(uint)&local_24,0xf,0x10014e68);
    iVar4 = 0;
    if (iVar3 != 0) {
      printf("WriteToSection for SDT failed(%d)\n",iVar3);
      iVar4 = 0x1a;
    }
  }
  local_1c[1] = (int **)pppiVar1;
  *local_18 = (int)pppiVar1;
  if (iVar4 != 0) {
    free((void *)*param_1);
  }
  return iVar4;
}



/* 1000b190 FUN_1000b190 */

undefined4 __fastcall FUN_1000b190(undefined4 param_1,int *param_2)

{
  int *piVar1;
  int iVar2;
  undefined4 uVar3;
  undefined2 *unaff_ESI;
  undefined2 local_20;
  undefined1 local_1e;
  undefined2 local_1c;
  int local_18;
  int *local_14;
  undefined4 local_8;
  
  local_8 = 0;
  iVar2 = FUN_1000b3f0((int *)&DAT_1005ab48,param_2,&local_8,(int *)&local_20,0xf,0x10014e68);
  uVar3 = 0;
  if (iVar2 != 0) {
    printf("ReadFromSection for SDT failed(%d)\n",iVar2);
    uVar3 = 0x1a;
  }
  *unaff_ESI = local_20;
  *(undefined1 *)(unaff_ESI + 1) = local_1e;
  unaff_ESI[2] = local_1c;
  piVar1 = (int *)(unaff_ESI + 4);
  *piVar1 = local_18;
  *(int **)(unaff_ESI + 6) = local_14;
  *(int **)(local_18 + 4) = piVar1;
  *local_14 = (int)piVar1;
  return uVar3;
}



/* 1000b210 FUN_1000b210 */

undefined4 __cdecl FUN_1000b210(undefined4 *param_1,size_t *param_2)

{
  undefined4 *puVar1;
  int ***pppiVar2;
  short sVar3;
  undefined2 *in_EAX;
  int ****ppppiVar4;
  void *_Dst;
  int iVar5;
  int ****_Memory;
  size_t _Size;
  undefined4 uVar6;
  bool bVar7;
  ushort local_1c;
  undefined2 local_1a;
  undefined1 local_18;
  undefined2 local_17;
  int ***local_14;
  int ***local_10;
  uint local_8;
  
  local_1a = *in_EAX;
  local_18 = *(undefined1 *)(in_EAX + 1);
  puVar1 = *(undefined4 **)(in_EAX + 2);
  local_8 = 0;
  local_17 = 0;
  local_14 = (int ***)&local_14;
  local_10 = (int ***)&local_14;
  for (; puVar1 != (undefined4 *)(in_EAX + 2); puVar1 = (undefined4 *)*puVar1) {
    ppppiVar4 = malloc(0xc);
    pppiVar2 = local_10;
    *(undefined2 *)((int)ppppiVar4 + 10) = *(undefined2 *)((int)puVar1 + 10);
    *(undefined2 *)(ppppiVar4 + 2) = *(undefined2 *)(puVar1 + 2);
    local_10 = (int ***)ppppiVar4;
    *ppppiVar4 = (int ***)&local_14;
    ppppiVar4[1] = pppiVar2;
    *pppiVar2 = (int **)ppppiVar4;
  }
  sVar3 = 5;
  ppppiVar4 = (int ****)local_14;
  if ((int ****)local_14 != &local_14) {
    do {
      sVar3 = sVar3 + 4;
      ppppiVar4 = (int ****)*ppppiVar4;
    } while (ppppiVar4 != &local_14);
  }
  local_1c = sVar3 + 4;
  _Size = local_1c + 3;
  _Dst = malloc(_Size);
  *param_1 = _Dst;
  if (_Dst == (void *)0x0) {
    printf("OUT OF MEMORY\n");
    uVar6 = 0x16;
  }
  else {
    *param_2 = _Size;
    memset(_Dst,0,_Size);
    iVar5 = FUN_1000bc70((int)_Dst,_Size,&local_8,(uint)&local_1c,0xd,0x10014c70);
    uVar6 = 0;
    if (iVar5 != 0) {
      printf("WriteToSection for PAT failed(%d)\n",iVar5);
      uVar6 = 0x1a;
    }
  }
  ppppiVar4 = (int ****)*local_14;
  _Memory = (int ****)local_14;
  if ((int ****)local_14 != &local_14) {
    do {
      free(_Memory);
      bVar7 = ppppiVar4 != &local_14;
      _Memory = ppppiVar4;
      ppppiVar4 = (int ****)*ppppiVar4;
    } while (bVar7);
  }
  return uVar6;
}



/* 1000b340 FUN_1000b340 */

undefined4 __fastcall FUN_1000b340(undefined4 param_1,int *param_2)

{
  int *piVar1;
  int iVar2;
  undefined2 *unaff_ESI;
  undefined4 local_1c;
  undefined1 local_18;
  int local_14;
  int *local_10;
  undefined4 local_8;
  
  local_8 = 0;
  iVar2 = FUN_1000b3f0((int *)&DAT_1005ab48,param_2,&local_8,&local_1c,0xd,0x10014c70);
  if (iVar2 != 0) {
    printf("ReadFromSection for PAT failed(%d)\n",iVar2);
    return 0x1a;
  }
  *(undefined1 *)(unaff_ESI + 1) = local_18;
  *unaff_ESI = local_1c._2_2_;
  piVar1 = (int *)(unaff_ESI + 2);
  *piVar1 = local_14;
  *(int **)(unaff_ESI + 4) = local_10;
  *(int **)(local_14 + 4) = piVar1;
  *local_10 = (int)piVar1;
  return 0;
}



/* 1000b3b0 FUN_1000b3b0 */

void __fastcall FUN_1000b3b0(int param_1)

{
  for (; param_1 != 0; param_1 = param_1 + -1) {
  }
  return;
}



/* 1000b3f0 FUN_1000b3f0 */

void __cdecl
FUN_1000b3f0(int *param_1,int *param_2,undefined4 *param_3,int *param_4,int param_5,int param_6)

{
  int *piVar1;
  size_t sVar2;
  undefined4 *puVar3;
  ushort uVar4;
  uint3 uVar5;
  int *piVar6;
  int *piVar7;
  byte bVar8;
  uint uVar9;
  int iVar10;
  undefined3 uVar12;
  int *piVar11;
  int *piVar13;
  int *piVar14;
  int *_Dst;
  undefined8 uVar15;
  int local_104;
  int *local_100;
  int local_fc;
  int *local_f8;
  int *local_ec;
  int *local_e8;
  int *local_e4;
  int local_e0;
  uint local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  _Dst = (int *)0x0;
  local_dc = 0;
  local_e4 = (int *)0xffffffff;
  local_100 = (int *)0xffffffff;
  if (param_6 == 0) {
    local_dc = 1;
  }
  else if (param_5 == 0) {
    local_dc = 2;
  }
  else {
    piVar13 = (int *)*param_3;
    local_e0 = 8;
    local_104 = 0;
    local_ec = piVar13;
    if (0 < param_5) {
      piVar14 = (int *)(param_6 + 0xc);
      piVar11 = param_4;
      do {
        local_ec = piVar13;
        if ((param_2 <= piVar13) || (local_dc != 0)) break;
        piVar1 = (int *)piVar14[-2];
        uVar12 = (undefined3)((uint)piVar11 >> 8);
        local_f8._0_1_ = (char)piVar1;
        piVar7 = piVar1;
        switch(piVar14[-3]) {
        case 1:
        case 0xc:
          local_e8 = (int *)0x0;
          break;
        case 2:
        case 3:
          if (local_e0 < (int)piVar1) {
LAB_1000bb9e:
            local_dc = 3;
            goto LAB_1000bb72;
          }
          bVar8 = FUN_1000c0e0((byte)local_e0,CONCAT31(uVar12,(char)local_f8));
          bVar8 = (bVar8 & *(byte *)((int)param_1 + (int)piVar13)) >>
                  ((byte)local_e0 - (char)local_f8 & 0x1f);
          if (piVar14[-3] == 2) {
            local_dc = -(uint)((uint)bVar8 != piVar14[-1]) & 0xb;
            piVar11 = (int *)(piVar14[-2] >> 0x1f & 7);
            local_e8 = (int *)(piVar14[-2] + (int)piVar11 >> 3);
          }
          else {
            if (*piVar14 != -1) {
              *(byte *)((int)param_4 + *piVar14) = bVar8;
              piVar13 = local_ec;
            }
            piVar11 = (int *)(piVar14[-2] >> 0x1f & 7);
            local_e8 = (int *)(piVar14[-2] + (int)piVar11 >> 3);
          }
          break;
        case 4:
        case 5:
          if (local_e0 + 8 < (int)piVar1) goto LAB_1000bbaa;
          bVar8 = FUN_1000c0e0((byte)local_e0,CONCAT31(uVar12,(byte)local_e0));
          piVar11 = (int *)0x0;
          uVar4 = CONCAT11(bVar8 & *(byte *)((int)param_1 + (int)piVar13),
                           *(undefined1 *)((int)param_1 + 1 + (int)piVar13));
          if (piVar14[-3] == 4) {
            local_e8 = (int *)0x2;
            local_dc = -(uint)((uint)uVar4 != piVar14[-1]) & 0xb;
          }
          else {
            if (*piVar14 != -1) {
              *(ushort *)((int)param_4 + *piVar14) = uVar4;
              piVar11 = param_4;
              piVar13 = local_ec;
            }
            local_e8 = (int *)0x2;
          }
          break;
        case 6:
        case 7:
          if (local_e0 + 0x18 < (int)piVar1) {
            local_dc = 5;
            goto LAB_1000bb72;
          }
          bVar8 = FUN_1000c0e0((byte)local_e0,CONCAT31(uVar12,(byte)local_e0));
          uVar5 = CONCAT21(CONCAT11(bVar8 & *(byte *)((int)param_1 + (int)piVar13),
                                    *(undefined1 *)((int)param_1 + 1 + (int)piVar13)),
                           *(undefined1 *)((int)param_1 + 2 + (int)piVar13));
          uVar9 = (uint)uVar5;
          local_e8 = (int *)0x3;
          piVar11 = (int *)0x0;
          if (0x18 < (int)piVar1) {
            uVar9 = CONCAT31(uVar5,*(undefined1 *)((int)param_1 + 3 + (int)piVar13));
            local_e8 = (int *)0x4;
            piVar11 = param_1;
          }
          if (piVar14[-3] == 6) {
            local_dc = -(uint)(uVar9 != piVar14[-1]) & 0xb;
          }
          else if (*piVar14 != -1) {
            *(uint *)((int)param_4 + *piVar14) = uVar9;
            piVar11 = param_4;
            piVar13 = local_ec;
          }
          break;
        case 8:
          if (local_e0 < (int)piVar1) goto LAB_1000bb9e;
          bVar8 = FUN_1000c0e0((byte)local_e0,CONCAT31(uVar12,(char)local_f8));
          bVar8 = (bVar8 & *(byte *)((int)param_1 + (int)piVar13)) >>
                  ((byte)local_e0 - (char)local_f8 & 0x1f);
          local_e4 = (int *)(uint)bVar8;
          if (*piVar14 != -1) {
            *(byte *)((int)param_4 + *piVar14) = bVar8;
            piVar13 = local_ec;
          }
          piVar11 = (int *)(piVar14[-2] >> 0x1f & 7);
          local_e8 = (int *)(piVar14[-2] + (int)piVar11 >> 3);
joined_r0x1000b7b8:
          if ((int)param_2 < (int)local_e4) {
            sprintf(local_d8,"arraylen > sectionlen!!\n");
            if (DAT_1005bc04 != 0) {
              fprintf((FILE *)DAT_1005bc04,"%s : %s","general",local_d8);
            }
            local_dc = 10;
            goto LAB_1000bb72;
          }
          break;
        case 9:
          if ((int)piVar1 <= local_e0 + 8) {
            bVar8 = FUN_1000c0e0((byte)local_e0,CONCAT31(uVar12,(byte)local_e0));
            uVar4 = CONCAT11(bVar8 & *(byte *)((int)param_1 + (int)piVar13),
                             *(undefined1 *)((int)param_1 + 1 + (int)piVar13));
            local_e4 = (int *)(uint)uVar4;
            piVar11 = (int *)0x0;
            if (*piVar14 != -1) {
              *(ushort *)((int)param_4 + *piVar14) = uVar4;
              piVar11 = param_4;
              piVar13 = local_ec;
            }
            local_e8 = (int *)0x2;
            goto joined_r0x1000b7b8;
          }
LAB_1000bbaa:
          local_dc = 4;
          goto LAB_1000bb72;
        case 10:
          if (local_e0 + 8 < (int)piVar1) {
            local_dc = 9;
            goto LAB_1000bb72;
          }
          bVar8 = FUN_1000c0e0((byte)local_e0,CONCAT31(uVar12,(byte)local_e0));
          uVar4 = CONCAT11(bVar8 & *(byte *)((int)param_1 + (int)piVar13),
                           *(undefined1 *)((int)param_1 + 1 + (int)piVar13));
          local_100 = (int *)(uint)uVar4;
          piVar11 = (int *)0x0;
          if (*piVar14 != -1) {
            *(ushort *)((int)param_4 + *piVar14) = uVar4;
            piVar11 = param_4;
            piVar13 = local_ec;
          }
          local_e8 = (int *)0x2;
          break;
        case 0xb:
          local_f8 = (int *)0x0;
          if (piVar1 != (int *)0xffffffff) {
            local_e4 = piVar1;
          }
          local_e8 = local_e4;
          piVar7 = local_f8;
          if (*piVar14 != -1) {
            if (0 < (int)local_e4) {
              if (param_2 < (int *)((int)local_e4 + (int)piVar13)) {
                local_dc = 6;
              }
              else {
                _Dst = malloc((size_t)local_e4);
                if (_Dst != (int *)0x0) {
                  memcpy(_Dst,(void *)((int)param_1 + (int)piVar13),(size_t)local_e4);
                  *(int **)((int)param_4 + *piVar14) = _Dst;
                  local_e8 = local_e4;
                  local_e4 = (int *)0xffffffff;
                  piVar11 = param_4;
                  piVar13 = local_ec;
                  break;
                }
LAB_1000bbda:
                local_dc = 0xc;
              }
              goto LAB_1000bb72;
            }
            *(undefined4 *)((int)param_4 + *piVar14) = 0;
            piVar11 = param_4;
          }
          local_e4 = (int *)0xffffffff;
          break;
        case 0xd:
        case 0xe:
          local_f8 = (int *)0x0;
          local_e8 = (int *)0x0;
          piVar7 = local_f8;
          if ((piVar1 != (int *)0xffffffff) || (*piVar14 != -1)) {
            if (*piVar14 == -1) {
              local_dc = 7;
              goto LAB_1000bb72;
            }
            if (local_e4 == (int *)0xffffffff) {
              piVar11 = param_2 + -1;
            }
            else {
              piVar11 = (int *)((int)piVar13 + (int)local_e4);
            }
            piVar6 = param_4;
            if (piVar1 != (int *)0x0) {
              *(int *)(*piVar14 + (int)param_4) = *piVar14 + (int)param_4;
              *(int *)(*piVar14 + (int)param_4 + 4) = *piVar14 + (int)param_4;
              piVar6 = _Dst;
            }
            _Dst = piVar6;
            local_e4 = piVar11;
            if (piVar13 < piVar11) {
              do {
                sVar2 = piVar14[-2];
                if (sVar2 != 0) {
                  _Dst = malloc(sVar2);
                  if (_Dst == (int *)0x0) goto LAB_1000bbda;
                  memset(_Dst,0,sVar2);
                }
                uVar15 = FUN_1000b3f0(param_1,param_2,&local_ec,_Dst,piVar14[1],piVar14[2]);
                piVar11 = (int *)((ulonglong)uVar15 >> 0x20);
                local_dc = (uint)uVar15;
                if (local_dc != 0) goto LAB_1000bbf2;
                if (piVar1 != (int *)0x0) {
                  iVar10 = *piVar14 + (int)param_4;
                  puVar3 = *(undefined4 **)(iVar10 + 4);
                  *(int **)(iVar10 + 4) = _Dst;
                  *_Dst = iVar10;
                  _Dst[1] = (int)puVar3;
                  *puVar3 = _Dst;
                }
                local_dc = 0;
              } while (local_ec < local_e4);
            }
            local_e4 = (int *)0xffffffff;
            piVar13 = local_ec;
          }
          break;
        case 0xf:
          local_f8 = (int *)0x0;
          local_e8 = (int *)0x0;
          if ((*piVar14 == -1) || (local_100 == (int *)0xffffffff)) {
            local_dc = 8;
            goto LAB_1000bb72;
          }
          *(int *)(*piVar14 + (int)param_4) = *piVar14 + (int)param_4;
          *(int *)(*piVar14 + (int)param_4 + 4) = *piVar14 + (int)param_4;
          local_fc = 0;
          piVar11 = local_100;
          if (0 < (int)local_100) {
            do {
              sVar2 = piVar14[-2];
              if (sVar2 != 0) {
                _Dst = malloc(sVar2);
                if (_Dst == (int *)0x0) goto LAB_1000bbda;
                memset(_Dst,0,sVar2);
              }
              uVar15 = FUN_1000b3f0(param_1,param_2,&local_ec,_Dst,piVar14[1],piVar14[2]);
              piVar11 = (int *)((ulonglong)uVar15 >> 0x20);
              local_dc = (uint)uVar15;
              if (local_dc != 0) goto LAB_1000bbf2;
              iVar10 = *piVar14 + (int)param_4;
              puVar3 = *(undefined4 **)(iVar10 + 4);
              *(int **)(iVar10 + 4) = _Dst;
              *_Dst = iVar10;
              local_fc = local_fc + 1;
              _Dst[1] = (int)puVar3;
              *puVar3 = _Dst;
              local_dc = 0;
            } while (local_fc < (int)local_100);
          }
          local_100 = (int *)0xffffffff;
          piVar13 = local_ec;
          piVar7 = local_f8;
          break;
        case 0x10:
          local_e8 = (int *)0x4;
        }
        local_f8 = piVar7;
        if ((local_e0 < (int)local_f8) &&
           (local_f8 = (int *)((uint)local_f8 & 0x80000007), (int)local_f8 < 0)) {
          local_f8 = (int *)(((int)local_f8 - 1U | 0xfffffff8) + 1);
        }
        local_e0 = local_e0 - (int)local_f8;
        if (local_e8 == (int *)0x0) {
          if (local_e0 == 0) {
            local_e8 = (int *)0x1;
LAB_1000bb36:
            local_e0 = 8;
          }
        }
        else if (local_e0 == 0) goto LAB_1000bb36;
        local_104 = local_104 + 1;
        piVar13 = (int *)((int)piVar13 + (int)local_e8);
        piVar14 = piVar14 + 6;
        local_ec = piVar13;
      } while (local_104 < param_5);
    }
    *param_3 = local_ec;
    if (local_dc == 0) goto LAB_1000bb87;
  }
LAB_1000bb72:
  printf("Failed at index: %d\n",local_104);
LAB_1000bb87:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
LAB_1000bbf2:
  printf("Recursive ReadFromSection failed (%d)\n",local_dc);
  local_dc = 7;
  free(_Dst);
  goto LAB_1000bb72;
}



/* 1000bc70 FUN_1000bc70 */

undefined4 __cdecl
FUN_1000bc70(int param_1,uint param_2,uint *param_3,uint param_4,int param_5,uint param_6)

{
  undefined4 *puVar1;
  byte bVar2;
  byte bVar3;
  int *piVar4;
  undefined4 *puVar5;
  int iVar6;
  uint uVar7;
  undefined4 uVar8;
  uint uVar9;
  int local_1c;
  uint local_18;
  uint local_10;
  int local_c;
  uint local_8;
  
  local_18 = 0xffffffff;
  if (param_6 == 0) {
    uVar8 = 1;
  }
  else {
    if (param_5 != 0) {
      uVar7 = *param_3;
      local_c = 8;
      local_1c = 0;
      if (0 < param_5) {
        piVar4 = (int *)(param_6 + 0xc);
        uVar9 = uVar7;
        do {
          if (param_2 <= uVar7) break;
          local_10 = piVar4[-2];
          bVar3 = (byte)local_c;
          param_6 = uVar7;
          switch(piVar4[-3]) {
          case 1:
            bVar2 = FUN_1000c0e0(bVar3,local_10);
            *(byte *)(uVar7 + param_1) =
                 *(byte *)(uVar7 + param_1) |
                 bVar2 & (char)piVar4[-1] << (bVar3 - (char)local_10 & 0x1f);
            uVar9 = 0;
            break;
          case 2:
          case 3:
          case 8:
            if (local_c < (int)local_10) {
              uVar8 = 3;
              goto LAB_1000bc91;
            }
            iVar6 = piVar4[-3];
            if (iVar6 == 2) {
              local_8 = piVar4[-1];
            }
            else if (((iVar6 == 3) || (iVar6 == 8)) && (*piVar4 != -1)) {
              local_8 = (uint)*(byte *)(*piVar4 + param_4);
            }
            else {
              local_8 = 0;
            }
            bVar2 = FUN_1000c0e0(bVar3,local_10);
            *(byte *)(uVar7 + param_1) =
                 *(byte *)(uVar7 + param_1) |
                 bVar2 & (char)local_8 << (bVar3 - (char)local_10 & 0x1f);
            uVar9 = (int)((piVar4[-2] >> 0x1f & 7U) + piVar4[-2]) >> 3;
            if (piVar4[-3] == 8) {
              local_18 = local_8;
            }
            break;
          case 4:
          case 5:
          case 9:
          case 10:
            if (local_c + 8 < (int)local_10) {
LAB_1000c033:
              uVar8 = 4;
              goto LAB_1000bc91;
            }
            if (piVar4[-3] == 4) {
              local_8 = piVar4[-1];
              uVar9 = local_8;
            }
            else if (*piVar4 == -1) {
              local_8 = 0;
              uVar9 = local_10;
            }
            else {
              local_8 = (uint)*(ushort *)(*piVar4 + param_4);
              uVar9 = param_4;
            }
            bVar3 = FUN_1000c0e0(bVar3,CONCAT31((int3)(uVar9 >> 8),bVar3));
            *(byte *)(uVar7 + param_1) = *(byte *)(uVar7 + param_1) | bVar3 & (byte)(local_8 >> 8);
            *(char *)(param_6 + 1 + param_1) = (char)local_8;
            uVar9 = 2;
            if (piVar4[-3] == 9) {
              local_18 = local_8;
            }
            break;
          case 6:
          case 7:
            if (local_c + 0x18 < (int)local_10) {
LAB_1000c010:
              uVar8 = 5;
              goto LAB_1000bc91;
            }
            if (piVar4[-3] == 6) {
              local_8 = piVar4[-1];
            }
            else if ((piVar4[-3] == 7) && (*piVar4 != -1)) {
              local_8 = *(int *)(*piVar4 + param_4);
            }
            else {
              local_8 = 0;
            }
            uVar9 = CONCAT31((int3)(local_10 >> 8),bVar3);
            bVar2 = (byte)(local_8 >> 0x10);
            if ((int)local_10 < 0x19) {
              bVar3 = FUN_1000c0e0(bVar3,uVar9);
              *(byte *)(uVar7 + param_1) = *(byte *)(uVar7 + param_1) | bVar3 & bVar2;
              iVar6 = 1;
            }
            else {
              bVar3 = FUN_1000c0e0(bVar3,uVar9);
              *(byte *)(uVar7 + param_1) =
                   *(byte *)(uVar7 + param_1) | bVar3 & (byte)(local_8 >> 0x18);
              *(byte *)(param_6 + 1 + param_1) = bVar2;
              iVar6 = 2;
            }
            *(char *)(param_6 + iVar6 + param_1) = (char)(local_8 >> 8);
            *(char *)(iVar6 + 1 + param_6 + param_1) = (char)local_8;
            uVar9 = iVar6 + 2;
            break;
          case 0xb:
            local_10 = 0;
            if (piVar4[-2] != 0xffffffff) {
              local_18 = piVar4[-2];
            }
            uVar9 = local_18;
            if ((0 < (int)local_18) && (*piVar4 != -1)) {
              if (param_2 <= uVar7 + local_18) goto LAB_1000c010;
              memcpy((void *)(uVar7 + param_1),*(void **)(*piVar4 + param_4),local_18);
            }
            local_18 = 0xffffffff;
            break;
          case 0xc:
            uVar9 = 0;
            break;
          case 0xd:
          case 0xe:
          case 0xf:
            if (*piVar4 == -1) {
              uVar8 = 7;
              goto LAB_1000bc91;
            }
            puVar5 = (undefined4 *)(*piVar4 + param_4);
            for (puVar1 = (undefined4 *)*puVar5; puVar1 != puVar5; puVar1 = (undefined4 *)*puVar1) {
              iVar6 = FUN_1000bc70(param_1,param_2,&param_6,(uint)puVar1,piVar4[1],piVar4[2]);
              if (iVar6 != 0) {
                printf("Recursive WriteToSection failed (%d)\n",iVar6);
                goto LAB_1000c033;
              }
            }
            uVar9 = 0;
            local_10 = 0;
            break;
          case 0x10:
            uVar8 = FUN_1000b3b0(param_2 - 4);
            *(char *)(uVar7 + param_1) = (char)((uint)uVar8 >> 0x18);
            *(char *)(param_6 + 1 + param_1) = (char)((uint)uVar8 >> 0x10);
            *(char *)(param_6 + 2 + param_1) = (char)((uint)uVar8 >> 8);
            *(char *)(param_6 + 3 + param_1) = (char)uVar8;
            uVar9 = 4;
          }
          if ((local_c < (int)local_10) && (local_10 = local_10 & 0x80000007, (int)local_10 < 0)) {
            local_10 = (local_10 - 1 | 0xfffffff8) + 1;
          }
          local_c = local_c - local_10;
          if (uVar9 == 0) {
            if (local_c == 0) {
              uVar9 = 1;
LAB_1000bfd5:
              local_c = 8;
            }
          }
          else if (local_c == 0) goto LAB_1000bfd5;
          local_1c = local_1c + 1;
          piVar4 = piVar4 + 6;
          uVar7 = param_6 + uVar9;
        } while (local_1c < param_5);
      }
      *param_3 = uVar7;
      return 0;
    }
    uVar8 = 2;
  }
LAB_1000bc91:
  printf("Failed at index: %d\n",local_1c);
  return uVar8;
}



/* 1000c070 FUN_1000c070 */

undefined4 FUN_1000c070(void)

{
  char *pcVar1;
  char *unaff_ESI;
  undefined4 local_8;
  
  local_8 = 0;
  pcVar1 = strstr(unaff_ESI,"0x");
  if (pcVar1 == (char *)0x0) {
    pcVar1 = strstr(unaff_ESI,"0X");
    if (pcVar1 == (char *)0x0) {
      sscanf_s(unaff_ESI,"%d",&local_8,4);
      return local_8;
    }
  }
  sscanf_s(unaff_ESI,"%x",&local_8,4);
  return local_8;
}



/* 1000c0e0 FUN_1000c0e0 */

byte __fastcall FUN_1000c0e0(byte param_1,uint param_2)

{
  byte bVar1;
  uint uVar2;
  
  bVar1 = 0;
  for (uVar2 = param_2 & 0xff; uVar2 != 0; uVar2 = uVar2 - 1) {
    param_1 = param_1 - 1;
    bVar1 = bVar1 | '\x01' << (param_1 & 0x1f);
  }
  return bVar1;
}



/* 1000c100 FUN_1000c100 */

undefined4 __cdecl FUN_1000c100(ushort param_1,undefined2 param_2,undefined4 *param_3)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  
  puVar2 = malloc(0x20);
  *param_3 = puVar2;
  if (puVar2 == (undefined4 *)0x0) {
    return 0x16;
  }
  *puVar2 = 0;
  puVar2[1] = 0;
  puVar2[3] = 0;
  puVar2[6] = 0;
  *(undefined2 *)(puVar2 + 3) = param_2;
  *(undefined1 *)((int)puVar2 + 0xe) = 1;
  puVar2[4] = 0x1fff;
  puVar2[5] = 0;
  puVar2[2] = (uint)param_1;
  puVar1 = puVar2 + 6;
  *puVar1 = puVar1;
  puVar2[7] = puVar1;
  return 0;
}



/* 1000c160 FUN_1000c160 */

undefined4 __cdecl FUN_1000c160(void *param_1)

{
  undefined4 *puVar1;
  undefined4 *_Memory;
  undefined4 *puVar2;
  undefined4 *_Memory_00;
  bool bVar3;
  undefined4 *local_c;
  
  _Memory_00 = *(undefined4 **)((int)param_1 + 0x18);
  local_c = (undefined4 *)*_Memory_00;
  if (_Memory_00 != (undefined4 *)((int)param_1 + 0x18)) {
    do {
      puVar2 = *(undefined4 **)_Memory_00[2];
      _Memory = (undefined4 *)_Memory_00[2];
      while (puVar1 = puVar2, _Memory != _Memory_00 + 2) {
        free((void *)_Memory[3]);
        free(_Memory);
        puVar2 = (undefined4 *)*puVar1;
        _Memory = puVar1;
      }
      free(_Memory_00);
      bVar3 = local_c != (undefined4 *)((int)param_1 + 0x18);
      _Memory_00 = local_c;
      local_c = (undefined4 *)*local_c;
    } while (bVar3);
  }
  if (*(void **)((int)param_1 + 0x14) != (void *)0x0) {
    free(*(void **)((int)param_1 + 0x14));
  }
  free(param_1);
  return 0;
}



/* 1000c200 FUN_1000c200 */

void __fastcall FUN_1000c200(int param_1,int *param_2)

{
  int *piVar1;
  byte bVar2;
  undefined2 uVar3;
  int iVar4;
  undefined4 *puVar5;
  void *pvVar6;
  int *piVar7;
  int *piVar8;
  int *piVar9;
  int *piVar10;
  int local_e4;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  pvVar6 = malloc(0x20);
  *param_2 = (int)pvVar6;
  if (pvVar6 == (void *)0x0) {
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  *(undefined4 *)((int)pvVar6 + 8) = *(undefined4 *)(param_1 + 8);
  *(undefined2 *)(*param_2 + 0x10) = *(undefined2 *)(param_1 + 0x10);
  *(undefined2 *)(*param_2 + 0xc) = *(undefined2 *)(param_1 + 0xc);
  *(undefined1 *)(*param_2 + 0xe) = *(undefined1 *)(param_1 + 0xe);
  *(undefined2 *)(*param_2 + 0x12) = *(undefined2 *)(param_1 + 0x12);
  iVar4 = *param_2;
  pvVar6 = malloc((uint)*(ushort *)(iVar4 + 0x12));
  *(void **)(iVar4 + 0x14) = pvVar6;
  if (*(void **)(*param_2 + 0x14) == (void *)0x0) {
LAB_1000c460:
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  memcpy(*(void **)(*param_2 + 0x14),*(void **)(param_1 + 0x14),(uint)*(ushort *)(param_1 + 0x12));
  *(int *)(*param_2 + 0x18) = *param_2 + 0x18;
  *(int *)(*param_2 + 0x1c) = *param_2 + 0x18;
  piVar1 = (int *)(param_1 + 0x18);
  piVar10 = (int *)*piVar1;
  if (piVar10 == piVar1) {
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  do {
    iVar4 = piVar10[4];
    uVar3 = *(undefined2 *)((int)piVar10 + 0x12);
    local_e4 = 0;
    piVar7 = malloc(0x18);
    if (piVar7 == (int *)0x0) {
      local_e4 = 0x16;
    }
    else {
      piVar7[2] = 0;
      piVar7[4] = 0;
      piVar7[5] = 0;
      piVar9 = piVar7 + 2;
      *(char *)(piVar7 + 4) = (char)iVar4;
      *(undefined2 *)((int)piVar7 + 0x12) = uVar3;
      *piVar7 = (int)piVar7;
      piVar7[1] = (int)piVar7;
      *piVar9 = (int)piVar9;
      piVar7[3] = (int)piVar9;
    }
    if (local_e4 != 0) {
      sprintf(local_d8,"PMTw_CopyProgramDefinition: PMTw_CreateProgramStreamInfo failed(%d)\n",
              local_e4);
      if (DAT_1005bc04 != 0) {
        fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_10012350,local_d8);
      }
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
    *(short *)(piVar7 + 5) = (short)piVar10[5];
    piVar9 = (int *)piVar10[2];
    if (piVar9 != piVar10 + 2) {
      do {
        piVar8 = malloc(0x10);
        if (piVar8 == (int *)0x0) goto LAB_1000c460;
        *(char *)(piVar8 + 2) = (char)piVar9[2];
        bVar2 = *(byte *)((int)piVar9 + 9);
        *(byte *)((int)piVar8 + 9) = bVar2;
        pvVar6 = malloc((uint)bVar2);
        piVar8[3] = (int)pvVar6;
        if (pvVar6 == (void *)0x0) goto LAB_1000c460;
        memcpy(pvVar6,(void *)piVar9[3],(uint)*(byte *)((int)piVar8 + 9));
        puVar5 = (undefined4 *)piVar7[3];
        piVar7[3] = (int)piVar8;
        *piVar8 = (int)(piVar7 + 2);
        piVar8[1] = (int)puVar5;
        *puVar5 = piVar8;
        piVar9 = (int *)*piVar9;
      } while (piVar9 != piVar10 + 2);
    }
    iVar4 = *param_2;
    puVar5 = *(undefined4 **)(iVar4 + 0x1c);
    *(int **)(iVar4 + 0x1c) = piVar7;
    *piVar7 = iVar4 + 0x18;
    piVar7[1] = (int)puVar5;
    *puVar5 = piVar7;
    piVar10 = (int *)*piVar10;
    if (piVar10 == piVar1) {
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
  } while( true );
}



/* 1000c480 FUN_1000c480 */

int __cdecl FUN_1000c480(undefined4 *param_1,size_t *param_2)

{
  int ****ppppiVar1;
  int *****pppppiVar2;
  int in_EAX;
  void *_Dst;
  int iVar3;
  int iVar4;
  size_t _Size;
  int *****pppppiVar5;
  short local_28;
  undefined2 local_26;
  undefined1 local_24;
  undefined2 local_23;
  undefined2 local_20;
  undefined2 local_1e;
  undefined4 local_1c;
  int ****local_18;
  int *local_14;
  uint local_c;
  int ***local_8;
  
  local_26 = *(undefined2 *)(in_EAX + 0xc);
  local_24 = *(undefined1 *)(in_EAX + 0xe);
  local_20 = *(undefined2 *)(in_EAX + 0x10);
  local_1e = *(undefined2 *)(in_EAX + 0x12);
  local_1c = *(undefined4 *)(in_EAX + 0x14);
  ppppiVar1 = (int ****)(in_EAX + 0x18);
  local_14 = *(int **)(in_EAX + 0x1c);
  local_18 = (int ****)*ppppiVar1;
  local_23 = 0;
  *local_14 = (int)&local_18;
  (*ppppiVar1)[1] = (int **)&local_18;
  local_c = 0;
  iVar4 = 0xc;
  pppppiVar5 = (int *****)local_18;
  if ((int *****)local_18 != &local_18) {
    do {
      iVar3 = 0;
      for (pppppiVar2 = (int *****)pppppiVar5[2]; pppppiVar2 != pppppiVar5 + 2;
          pppppiVar2 = (int *****)*pppppiVar2) {
        iVar3 = iVar3 + 2 + (uint)*(byte *)((int)pppppiVar2 + 9);
      }
      iVar4 = iVar4 + 5 + iVar3;
      *(short *)(pppppiVar5 + 5) = (short)iVar3;
      pppppiVar5 = (int *****)*pppppiVar5;
    } while (pppppiVar5 != &local_18);
  }
  _Size = iVar4 + 4;
  local_8 = (int ***)ppppiVar1;
  _Dst = malloc(_Size);
  *param_1 = _Dst;
  if (_Dst == (void *)0x0) {
    iVar4 = 0x16;
  }
  else {
    local_28 = (short)iVar4 + 1;
    *param_2 = _Size;
    memset(_Dst,0,_Size);
    iVar3 = FUN_1000bc70((int)_Dst,_Size,&local_c,(uint)&local_28,0x12,0x10014430);
    iVar4 = 0;
    if (iVar3 != 0) {
      printf("WriteToSection for PMT failed(%d)\n",iVar3);
      iVar4 = 0x1a;
    }
  }
  *local_14 = (int)ppppiVar1;
  local_18[1] = (int ***)ppppiVar1;
  if (iVar4 != 0) {
    free((void *)*param_1);
  }
  return iVar4;
}



/* 1000c5a0 FUN_1000c5a0 */

undefined4 __fastcall FUN_1000c5a0(undefined4 param_1,int *param_2)

{
  int *piVar1;
  int iVar2;
  int unaff_ESI;
  undefined4 local_24;
  undefined1 local_20;
  undefined2 local_1c;
  short local_1a;
  undefined4 local_18;
  int local_14;
  int *local_10;
  undefined4 local_8;
  
  local_8 = 0;
  iVar2 = FUN_1000b3f0((int *)&DAT_1005ab48,param_2,&local_8,&local_24,0x12,0x10014430);
  if (iVar2 != 0) {
    printf("ReadFromSection for PMT failed(%d)\n",iVar2);
    return 0x1a;
  }
  *(undefined2 *)(unaff_ESI + 0xc) = local_24._2_2_;
  *(undefined2 *)(unaff_ESI + 0x10) = local_1c;
  *(undefined1 *)(unaff_ESI + 0xe) = local_20;
  *(short *)(unaff_ESI + 0x12) = local_1a;
  *(undefined4 *)(unaff_ESI + 0x14) = 0;
  if (local_1a != 0) {
    *(undefined4 *)(unaff_ESI + 0x14) = local_18;
  }
  piVar1 = (int *)(unaff_ESI + 0x18);
  *piVar1 = local_14;
  *(int **)(unaff_ESI + 0x1c) = local_10;
  *local_10 = (int)piVar1;
  *(int **)(local_14 + 4) = piVar1;
  return 0;
}



/* 1000c630 FUN_1000c630 */

void __fastcall FUN_1000c630(char *param_1)

{
  void *_Dst;
  errno_t eVar1;
  int iVar2;
  int *unaff_EBX;
  char local_dc [212];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  _Dst = malloc(0x3c);
  *unaff_EBX = (int)_Dst;
  if (_Dst == (void *)0x0) {
    sprintf(local_dc,"mem_alloc failed for transport_stream object\n");
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_dc);
    }
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  memset(_Dst,0,0x3c);
  *(int *)(*unaff_EBX + 0xc) = *unaff_EBX + 0xc;
  *(int *)(*unaff_EBX + 0x10) = *unaff_EBX + 0xc;
  *(int *)(*unaff_EBX + 0x1c) = *unaff_EBX + 0x1c;
  *(int *)(*unaff_EBX + 0x20) = *unaff_EBX + 0x1c;
  *(int *)(*unaff_EBX + 0x24) = *unaff_EBX + 0x24;
  *(int *)(*unaff_EBX + 0x28) = *unaff_EBX + 0x24;
  *(int *)(*unaff_EBX + 0x2c) = *unaff_EBX + 0x2c;
  *(int *)(*unaff_EBX + 0x30) = *unaff_EBX + 0x2c;
  *(int *)(*unaff_EBX + 0x34) = *unaff_EBX + 0x34;
  *(int *)(*unaff_EBX + 0x38) = *unaff_EBX + 0x34;
  eVar1 = fopen_s((FILE **)*unaff_EBX,param_1,"rb");
  if (eVar1 == 0) {
    fseek(*(FILE **)*unaff_EBX,0,0);
    iVar2 = FUN_1000de10();
    if (iVar2 == 0) {
      iVar2 = FUN_1000cdf0();
      if (iVar2 == 0) {
        iVar2 = FUN_1000cf00(*unaff_EBX);
        if (iVar2 == 0) {
          if ((*(int *)(*unaff_EBX + 0x14) == 0) || (iVar2 = FUN_1000d150(), iVar2 == 0)) {
            iVar2 = FUN_1000d240();
            if (iVar2 == 0) {
              sprintf(local_dc,"ts_read_data\n");
            }
            else {
              sprintf(local_dc,"ts_read_data failed(%d)\n",iVar2);
            }
          }
          else {
            sprintf(local_dc,"ts_read_sdt failed(%d)\n",iVar2);
          }
        }
        else {
          sprintf(local_dc,"ts_read_pmt failed(%d)\n",iVar2);
        }
      }
      else {
        sprintf(local_dc,"ts_read_pat failed(%d)\n",iVar2);
      }
    }
    else {
      sprintf(local_dc,"ts_read_synchronize failed(%d)\n",iVar2);
    }
  }
  else {
    sprintf(local_dc,"Cannot open ts file for reading %d\n",eVar1);
  }
  if ((FILE *)DAT_1005bc04 != (FILE *)0x0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_dc);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000c880 FUN_1000c880 */

undefined4 FUN_1000c880(void)

{
  int *piVar1;
  int *_Memory;
  undefined4 *puVar2;
  undefined4 *unaff_EBX;
  undefined4 *puVar3;
  bool bVar4;
  undefined4 *local_8;
  
  fclose((FILE *)*unaff_EBX);
  if (unaff_EBX[2] != 0) {
    piVar1 = (int *)(unaff_EBX[2] + 4);
    _Memory = (int *)*piVar1;
    puVar2 = (undefined4 *)*_Memory;
    if (_Memory != piVar1) {
      do {
        free(_Memory);
        bVar4 = puVar2 != (undefined4 *)(unaff_EBX[2] + 4);
        _Memory = puVar2;
        puVar2 = (undefined4 *)*puVar2;
      } while (bVar4);
    }
    free((void *)unaff_EBX[2]);
  }
  puVar2 = (undefined4 *)unaff_EBX[3];
  puVar3 = (undefined4 *)*puVar2;
  if (puVar2 != unaff_EBX + 3) {
    do {
      FUN_1000c160(puVar2);
      bVar4 = puVar3 != unaff_EBX + 3;
      puVar2 = puVar3;
      puVar3 = (undefined4 *)*puVar3;
    } while (bVar4);
  }
  if (unaff_EBX[5] != 0) {
    FUN_1000a110(unaff_EBX[5]);
    free((void *)unaff_EBX[5]);
  }
  if (unaff_EBX[6] != 0) {
    FUN_1000aed0(unaff_EBX[6]);
    free((void *)unaff_EBX[6]);
  }
  puVar2 = (undefined4 *)unaff_EBX[7];
  local_8 = (undefined4 *)*puVar2;
  if (puVar2 != unaff_EBX + 7) {
    do {
      FUN_1000ae50();
      free(puVar2);
      bVar4 = local_8 != unaff_EBX + 7;
      puVar2 = local_8;
      local_8 = (undefined4 *)*local_8;
    } while (bVar4);
  }
  puVar2 = (undefined4 *)unaff_EBX[9];
  local_8 = (undefined4 *)*puVar2;
  if (puVar2 != unaff_EBX + 9) {
    do {
      FUN_10009ff0((int)puVar2);
      free(puVar2);
      bVar4 = local_8 != unaff_EBX + 9;
      puVar2 = local_8;
      local_8 = (undefined4 *)*local_8;
    } while (bVar4);
  }
  puVar2 = (undefined4 *)unaff_EBX[0xb];
  local_8 = (undefined4 *)*puVar2;
  if (puVar2 != unaff_EBX + 0xb) {
    do {
      FUN_100098c0((int)puVar2);
      free(puVar2);
      bVar4 = local_8 != unaff_EBX + 0xb;
      puVar2 = local_8;
      local_8 = (undefined4 *)*local_8;
    } while (bVar4);
  }
  puVar2 = (undefined4 *)unaff_EBX[0xd];
  puVar3 = (undefined4 *)*puVar2;
  if (puVar2 != unaff_EBX + 0xd) {
    do {
      free(puVar2);
      bVar4 = puVar3 != unaff_EBX + 0xd;
      puVar2 = puVar3;
      puVar3 = (undefined4 *)*puVar3;
    } while (bVar4);
  }
  free(unaff_EBX);
  return 0;
}



/* 1000c9e0 FUN_1000c9e0 */

void FUN_1000c9e0(void)

{
  size_t sVar1;
  int iVar2;
  size_t sVar3;
  FILE *unaff_EBX;
  int unaff_EDI;
  undefined4 local_104;
  undefined4 local_100;
  undefined4 local_fc;
  int local_f8;
  undefined4 local_f4;
  int local_f0 [4];
  undefined4 *local_e0;
  size_t local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_e0 = (undefined4 *)0x0;
  local_f4 = 0;
  local_f0[0] = 10;
  local_f0[1] = 2;
  local_f0[2] = 0xffff;
  local_f0[3] = 0x1003;
  local_104 = 0xc;
  local_100 = 4;
  local_fc = 0xffffffff;
  if (*(int *)(unaff_EDI + 0x14) != -1) {
    local_e0 = &local_104;
    local_f8 = *(int *)(unaff_EDI + 0x14);
  }
  FUN_1000df90();
  do {
    iVar2 = FUN_1000e060(&DAT_1005ab48,*(int *)(unaff_EDI + 8),0x3c,local_f0,(int *)&local_dc);
    sVar1 = local_dc;
    if (iVar2 != 0) {
      if (iVar2 != 9) {
        sprintf(local_d8,"TSP_SectionFilter failed for ts data(%d)\n",iVar2);
        if (DAT_1005bc04 != 0) {
          fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
        }
        __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
        return;
      }
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
    sVar3 = fwrite(&DAT_1005ab48,1,local_dc,unaff_EBX);
  } while (sVar3 == sVar1);
  sprintf(local_d8,"Error writing to file while extracting data section\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000cb70 FUN_1000cb70 */

void __thiscall FUN_1000cb70(void *this,FILE *param_1)

{
  size_t sVar1;
  int iVar2;
  size_t sVar3;
  int iVar4;
  int local_114 [4];
  undefined4 *local_104;
  undefined4 local_100;
  undefined4 local_fc;
  undefined4 local_f8;
  int local_f4;
  undefined4 local_f0;
  uint local_ec;
  FILE *local_e8;
  void *local_e4;
  size_t local_e0;
  int local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_e8 = param_1;
  local_104 = &local_100;
  local_114[0] = 3;
  local_114[1] = 1;
  local_114[2] = 0xff;
  local_114[3] = 0;
  local_100 = 6;
  local_fc = 1;
  local_f8 = 0xff;
  local_f4 = 0;
  local_f0 = 0;
  local_e4 = this;
  FUN_1000df90();
  local_dc = 0;
  if (*(int *)((int)this + 0x10) < 0) {
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  do {
    local_114[3] = local_dc;
    local_f4 = 0;
    iVar2 = FUN_1000e060(&DAT_1005ab48,*(int *)((int)this + 8),0xffffffff,local_114,(int *)&local_e0
                        );
    sVar1 = local_e0;
    if (iVar2 != 0) {
      sprintf(local_d8,"TSP_SectionFilter failed for ts data(%d)\n",iVar2);
LAB_1000cd3c:
      if (DAT_1005bc04 != 0) {
        fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
      }
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
    sVar3 = fwrite(&DAT_1005ab48,1,local_e0,local_e8);
    if (sVar3 != sVar1) {
LAB_1000cd8b:
      sprintf(local_d8,"Error writing to file while extracting data section\n");
      if (DAT_1005bc04 != 0) {
        fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
      }
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
    local_ec = (uint)DAT_1005ab4f;
    iVar2 = 1;
    if (local_ec != 0) {
      do {
        local_f4 = iVar2;
        iVar4 = FUN_1000e060(&DAT_1005ab48,*(int *)((int)this + 8),0xffffffff,local_114,
                             (int *)&local_e0);
        sVar1 = local_e0;
        if (iVar4 != 0) {
          sprintf(local_d8,"TSP_SectionFilter failed for ts data(%d)\n",iVar4);
          goto LAB_1000cd3c;
        }
        sVar3 = fwrite(&DAT_1005ab48,1,local_e0,local_e8);
        if (sVar3 != sVar1) goto LAB_1000cd8b;
        iVar2 = iVar2 + 1;
        this = local_e4;
      } while (iVar2 <= (int)local_ec);
    }
    local_dc = local_dc + 1;
    if (*(int *)((int)this + 0x10) < local_dc) {
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
  } while( true );
}



/* 1000cdf0 FUN_1000cdf0 */

void FUN_1000cdf0(void)

{
  int iVar1;
  undefined4 *puVar2;
  int unaff_EDI;
  int *local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  *(undefined4 *)(unaff_EDI + 8) = 0;
  iVar1 = FUN_1000e060(&DAT_1005ab48,0,0,(int *)0x0,(int *)&local_dc);
  if (iVar1 == 0) {
    puVar2 = malloc(0xc);
    *(undefined4 **)(unaff_EDI + 8) = puVar2;
    if (puVar2 != (undefined4 *)0x0) {
      *puVar2 = 0;
      puVar2[1] = 0;
      puVar2[2] = 0;
      iVar1 = *(int *)(unaff_EDI + 8) + 4;
      *(int *)iVar1 = iVar1;
      iVar1 = *(int *)(unaff_EDI + 8) + 4;
      *(int *)(*(int *)(unaff_EDI + 8) + 8) = iVar1;
      iVar1 = FUN_1000b340(iVar1,local_dc);
      if (iVar1 == 0) goto LAB_1000cee6;
      sprintf(local_d8,"PATr_Convert_2_PAT failed(%d)\n",iVar1);
      goto joined_r0x1000ceba;
    }
  }
  else {
    sprintf(local_d8,"TSP_SectionFilter for PAT failed(%d)\n",iVar1);
joined_r0x1000ceba:
    if ((FILE *)DAT_1005bc04 != (FILE *)0x0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
    }
  }
  free(*(void **)(unaff_EDI + 8));
LAB_1000cee6:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000cf00 FUN_1000cf00 */

void __fastcall FUN_1000cf00(int param_1)

{
  int iVar1;
  undefined4 *puVar2;
  int iVar3;
  undefined4 *puVar4;
  int *piVar5;
  undefined4 extraout_ECX;
  int *piVar6;
  bool bVar7;
  char *_Format;
  int local_fc [4];
  undefined4 local_ec;
  int local_e8;
  int *local_e4;
  int *local_e0;
  char local_dc [212];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_e0 = (int *)(param_1 + 0xc);
  *local_e0 = (int)local_e0;
  *(int **)(param_1 + 0x10) = local_e0;
  iVar1 = *(int *)(param_1 + 8);
  local_e8 = param_1;
  if (iVar1 == 0) {
    sprintf(local_dc,"pat cannot be NULL\n");
LAB_1000d0eb:
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_dc);
    }
  }
  else {
    piVar5 = *(int **)(iVar1 + 4);
    local_fc[0] = 3;
    local_fc[1] = 2;
    local_fc[2] = 0xffff;
    local_ec = 0;
    if (piVar5 == (int *)(iVar1 + 4)) goto LAB_1000d137;
    while( true ) {
      iVar1 = local_e8;
      local_fc[3] = (int)*(ushort *)(piVar5 + 2);
      FUN_1000df90();
      if ((short)piVar5[2] != 0) break;
      iVar3 = FUN_1000e060(&DAT_1005ab48,(uint)*(ushort *)((int)piVar5 + 10),0x40,(int *)0x0,
                           (int *)&local_e4);
      if (iVar3 != 0) {
        _Format = "TSP_SectionFilter for NIT failed(%d)\n";
        goto LAB_1000d0db;
      }
      puVar4 = malloc(0x14);
      *(undefined4 **)(iVar1 + 0x14) = puVar4;
      if (puVar4 == (undefined4 *)0x0) goto LAB_1000d10f;
      *puVar4 = 0;
      puVar4[1] = 0;
      puVar4[2] = 0;
      puVar4[3] = 0;
      puVar4[4] = 0;
      iVar3 = FUN_1000a330(0,local_e4);
      if (iVar3 != 0) {
        _Format = "NITr_Convert_2_NIT failed(%d)\n";
        goto LAB_1000d0db;
      }
LAB_1000d095:
      piVar5 = (int *)*piVar5;
      if (piVar5 == (int *)(*(int *)(local_e8 + 8) + 4)) {
        __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
        return;
      }
    }
    iVar3 = FUN_1000e060(&DAT_1005ab48,(uint)*(ushort *)((int)piVar5 + 10),2,local_fc,
                         (int *)&local_e4);
    if (iVar3 != 0) {
      _Format = "TSP_SectionFilter for PMT failed(%d)\n";
LAB_1000d0db:
      sprintf(local_dc,_Format,iVar3);
      goto LAB_1000d0eb;
    }
    puVar4 = malloc(0x20);
    if (puVar4 != (undefined4 *)0x0) {
      *puVar4 = 0;
      puVar4[1] = 0;
      puVar4[2] = 0;
      puVar4[3] = 0;
      puVar4[4] = 0;
      puVar4[5] = 0;
      puVar4[6] = 0;
      puVar4[7] = 0;
      iVar3 = FUN_1000c5a0(extraout_ECX,local_e4);
      if (iVar3 == 0) {
        puVar4[2] = (uint)*(ushort *)((int)piVar5 + 10);
        puVar2 = (undefined4 *)local_e0[1];
        local_e0[1] = (int)puVar4;
        *puVar4 = local_e0;
        puVar4[1] = puVar2;
        *puVar2 = puVar4;
        goto LAB_1000d095;
      }
      _Format = "PMTr_Convert_2_PMT failed(%d)\n";
      goto LAB_1000d0db;
    }
  }
LAB_1000d10f:
  piVar5 = (int *)*local_e0;
  piVar6 = (int *)*piVar5;
  if (piVar5 != local_e0) {
    do {
      free(piVar5);
      bVar7 = piVar6 != local_e0;
      piVar5 = piVar6;
      piVar6 = (int *)*piVar6;
    } while (bVar7);
  }
LAB_1000d137:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000d150 FUN_1000d150 */

void FUN_1000d150(void)

{
  int iVar1;
  void *pvVar2;
  undefined4 extraout_ECX;
  int unaff_EDI;
  int *local_e0;
  char local_dc [212];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  *(undefined4 *)(unaff_EDI + 0x18) = 0;
  FUN_1000df90();
  iVar1 = FUN_1000e060(&DAT_1005ab48,0x11,0x42,(int *)0x0,(int *)&local_e0);
  if (iVar1 == 0) {
    pvVar2 = malloc(0x10);
    *(void **)(unaff_EDI + 0x18) = pvVar2;
    if (pvVar2 != (void *)0x0) {
      iVar1 = FUN_1000b190(extraout_ECX,local_e0);
      if (iVar1 == 0) goto LAB_1000d22b;
      sprintf(local_dc,"SDTr_Convert_2_SDT failed(%d)\n",iVar1);
      goto joined_r0x1000d1ff;
    }
  }
  else {
    sprintf(local_dc,"TSP_SectionFilter for SDT failed(%d)\n",iVar1);
joined_r0x1000d1ff:
    if ((FILE *)DAT_1005bc04 != (FILE *)0x0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_dc);
    }
  }
  free(*(void **)(unaff_EDI + 0x18));
LAB_1000d22b:
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000d240 FUN_1000d240 */

void FUN_1000d240(void)

{
  byte bVar1;
  undefined4 *puVar2;
  int *piVar3;
  int iVar4;
  int *piVar5;
  int unaff_EDI;
  char *_Format;
  int *local_e4;
  char local_dc [212];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  iVar4 = unaff_EDI + 0x1c;
  *(int *)iVar4 = iVar4;
  *(int *)(unaff_EDI + 0x20) = iVar4;
  iVar4 = unaff_EDI + 0x24;
  *(int *)iVar4 = iVar4;
  *(int *)(unaff_EDI + 0x28) = iVar4;
  iVar4 = unaff_EDI + 0x2c;
  *(int *)iVar4 = iVar4;
  *(int *)(unaff_EDI + 0x30) = iVar4;
  piVar3 = *(int **)(unaff_EDI + 0xc);
  if (piVar3 == (int *)(unaff_EDI + 0xc)) {
LAB_1000d3ae:
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  do {
    piVar5 = (int *)piVar3[6];
    if (piVar5 != piVar3 + 6) {
      do {
        bVar1 = *(byte *)(piVar5 + 4);
        if (bVar1 == 0xb) {
          iVar4 = FUN_1000d3c0(unaff_EDI);
          if (iVar4 != 0) {
            _Format = "read_data_carousel failed(%d)\n";
LAB_1000d381:
            sprintf(local_dc,_Format,iVar4);
            if (DAT_1005bc04 != 0) {
              fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_dc);
            }
            goto LAB_1000d3ae;
          }
        }
        else if (bVar1 == 0x80) {
          iVar4 = FUN_1000d8d0((int)piVar5);
          if (iVar4 != 0) {
            _Format = "read_user_data failed(%d)\n";
            goto LAB_1000d381;
          }
          puVar2 = *(undefined4 **)(unaff_EDI + 0x38);
          *(int **)(unaff_EDI + 0x38) = local_e4;
          *local_e4 = unaff_EDI + 0x34;
          local_e4[1] = (int)puVar2;
          *puVar2 = local_e4;
        }
        else {
          sprintf(local_dc,"ts_read_data doesnt support stream type 0x%x\n",(uint)bVar1);
          if (DAT_1005bc04 != 0) {
            fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_dc);
          }
        }
        piVar5 = (int *)*piVar5;
      } while (piVar5 != piVar3 + 6);
    }
    piVar3 = (int *)*piVar3;
    if (piVar3 == (int *)(unaff_EDI + 0xc)) {
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
  } while( true );
}



/* 1000d3c0 FUN_1000d3c0 */

void __cdecl FUN_1000d3c0(int param_1)

{
  undefined4 *puVar1;
  int iVar2;
  int *piVar3;
  int *piVar4;
  undefined4 extraout_ECX;
  undefined4 extraout_ECX_00;
  undefined4 extraout_ECX_01;
  int unaff_EBX;
  char *pcVar5;
  undefined4 local_140;
  undefined4 local_13c;
  undefined4 local_138;
  int local_134;
  undefined4 local_130;
  int local_12c [4];
  undefined4 *local_11c;
  int local_118 [10];
  int *local_f0;
  int *local_ec;
  int local_e8;
  int *local_e4;
  char local_e0 [212];
  uint local_c;
  
  local_c = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_e8 = param_1;
  local_118[5] = 10;
  local_12c[0] = 10;
  local_11c = &local_140;
  local_118[6] = 2;
  local_118[7] = 0xffff;
  local_118[8] = 0x1006;
  local_118[9] = 0;
  local_12c[1] = 2;
  local_12c[2] = 0xffff;
  local_12c[3] = 0x1002;
  local_140 = 0xc;
  local_13c = 4;
  local_138 = 0xffffffff;
  local_130 = 0;
  local_118[0] = 8;
  local_118[1] = 3;
  local_118[2] = 0xffffff;
  local_118[3] = 0x903e;
  local_118[4] = 0;
  FUN_1000df90();
  iVar2 = FUN_1000e060(&DAT_1005ab48,(uint)*(ushort *)(unaff_EBX + 0x12),0x3b,local_118 + 5,
                       (int *)&local_e4);
  if (iVar2 != 0) {
    sprintf(local_e0,"TSP_SectionFilter for DSI failed(%d)\n",iVar2);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_e0);
      __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
      return;
    }
    goto LAB_1000d8b2;
  }
  piVar3 = malloc(0x18);
  if (piVar3 == (int *)0x0) {
LAB_1000d7b1:
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
  *piVar3 = 0;
  piVar3[1] = 0;
  piVar3[2] = 0;
  piVar3[3] = 0;
  piVar3[4] = 0;
  piVar3[5] = 0;
  iVar2 = FUN_1000a980(extraout_ECX,local_e4,(int)piVar3);
  if (iVar2 == 0) {
    piVar3[2] = (uint)*(ushort *)(unaff_EBX + 0x12);
    puVar1 = *(undefined4 **)(local_e8 + 0x20);
    *(int **)(local_e8 + 0x20) = piVar3;
    *piVar3 = local_e8 + 0x1c;
    piVar3[1] = (int)puVar1;
    *puVar1 = piVar3;
    FUN_1000df90();
    local_ec = (int *)piVar3[4];
    local_f0 = piVar3 + 4;
    if (local_ec != local_f0) {
      do {
        local_134 = local_ec[4];
        iVar2 = FUN_1000e060(&DAT_1005ab48,(uint)*(ushort *)(unaff_EBX + 0x12),0x3b,local_12c,
                             (int *)&local_e4);
        if (iVar2 != 0) {
          sprintf(local_e0,"TSP_SectionFilter for DII failed(%d)\n",iVar2);
          if (DAT_1005bc04 != 0) {
            fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_e0);
            __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
            return;
          }
          goto LAB_1000d8b2;
        }
        piVar3 = malloc(0x28);
        if (piVar3 == (int *)0x0) {
          pcVar5 = "mem_alloc failed for dii\n";
          goto LAB_1000d784;
        }
        *piVar3 = 0;
        piVar3[1] = 0;
        piVar3[2] = 0;
        piVar3[3] = 0;
        piVar3[4] = 0;
        piVar3[5] = 0;
        piVar3[6] = 0;
        piVar3[7] = 0;
        piVar3[8] = 0;
        piVar3[9] = 0;
        iVar2 = FUN_10009dd0(extraout_ECX_00,local_e4,(int)piVar3);
        if (iVar2 != 0) {
          sprintf(local_e0,"DIIr_Convert_2_DII failed(%d)\n",iVar2);
          if (DAT_1005bc04 != 0) {
            fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_e0);
            __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
            return;
          }
          goto LAB_1000d8b2;
        }
        piVar3[2] = (uint)*(ushort *)(unaff_EBX + 0x12);
        puVar1 = *(undefined4 **)(local_e8 + 0x28);
        *(int **)(local_e8 + 0x28) = piVar3;
        *piVar3 = local_e8 + 0x24;
        piVar3[1] = (int)puVar1;
        *puVar1 = piVar3;
        piVar4 = malloc(0x18);
        if (piVar4 == (int *)0x0) {
          sprintf(local_e0,"mem_alloc failed to datablk for dii\n");
          goto LAB_1000d55b;
        }
        piVar4[3] = 2;
        piVar4[2] = (uint)*(ushort *)(unaff_EBX + 0x12);
        piVar4[5] = piVar3[4];
        piVar4[4] = (uint)*(ushort *)((int)piVar3 + 0x16);
        puVar1 = *(undefined4 **)(local_e8 + 0x38);
        *(int **)(local_e8 + 0x38) = piVar4;
        *piVar4 = local_e8 + 0x34;
        piVar4[1] = (int)puVar1;
        *puVar1 = piVar4;
        local_ec = (int *)*local_ec;
      } while (local_ec != local_f0);
    }
    FUN_1000df90();
    iVar2 = FUN_1000e060(&DAT_1005ab48,(uint)*(ushort *)(unaff_EBX + 0x12),0x4b,local_118,
                         (int *)&local_e4);
    if (iVar2 == 0) {
      piVar3 = malloc(0x28);
      if (piVar3 == (int *)0x0) {
        pcVar5 = "mem_alloc failed for unt\n";
LAB_1000d784:
        sprintf(local_e0,pcVar5);
        if (DAT_1005bc04 != 0) {
          fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_e0);
        }
        goto LAB_1000d7b1;
      }
      *piVar3 = 0;
      piVar3[1] = 0;
      piVar3[2] = 0;
      piVar3[3] = 0;
      piVar3[4] = 0;
      piVar3[5] = 0;
      piVar3[6] = 0;
      piVar3[7] = 0;
      piVar3[8] = 0;
      piVar3[9] = 0;
      iVar2 = FUN_10008d90(extraout_ECX_01,local_e4,(int)piVar3);
      if (iVar2 == 0) {
        piVar3[2] = (uint)*(ushort *)(unaff_EBX + 0x12);
        puVar1 = *(undefined4 **)(local_e8 + 0x30);
        *(int **)(local_e8 + 0x30) = piVar3;
        *piVar3 = local_e8 + 0x2c;
        piVar3[1] = (int)puVar1;
        *puVar1 = piVar3;
        goto LAB_1000d8b2;
      }
      pcVar5 = "UNTr_Convert_2_UNT failed(%d)\n";
    }
    else {
      if (iVar2 == 9) goto LAB_1000d8b2;
      pcVar5 = "TSP_SectionFilter for UNT failed(%d)\n";
    }
  }
  else {
    pcVar5 = "DSIr_Convert_2_DSI failed(%d)\n";
  }
  sprintf(local_e0,pcVar5,iVar2);
LAB_1000d55b:
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_e0);
    __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
    return;
  }
LAB_1000d8b2:
  __security_check_cookie(local_c ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000d8d0 FUN_1000d8d0 */

void __cdecl FUN_1000d8d0(int param_1)

{
  int iVar1;
  void *pvVar2;
  int *unaff_ESI;
  int local_108 [5];
  undefined4 *local_f4;
  undefined4 local_f0;
  undefined4 local_ec;
  undefined4 local_e8;
  undefined4 local_e4;
  undefined4 local_e0;
  int local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_dc = param_1;
  sprintf(local_d8,"inside read_user_data\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
  }
  local_f4 = &local_f0;
  *unaff_ESI = 0;
  local_108[1] = 3;
  local_108[2] = 1;
  local_108[3] = 0xff;
  local_108[4] = 0;
  local_f0 = 6;
  local_ec = 1;
  local_e8 = 0xff;
  local_e4 = 0;
  local_e0 = 0;
  FUN_1000df90();
  sprintf(local_d8,"at the beginning of stream\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
  }
  iVar1 = FUN_1000e060(&DAT_1005ab48,(uint)*(ushort *)(local_dc + 0x12),0xffffffff,local_108 + 1,
                       local_108);
  if (iVar1 == 0) {
    pvVar2 = malloc(0x18);
    *unaff_ESI = (int)pvVar2;
    if (pvVar2 == (void *)0x0) {
      sprintf(local_d8,"mem_alloc failed for datablock_info\n");
      if (DAT_1005bc04 != 0) {
        fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
      }
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
    *(undefined4 *)((int)pvVar2 + 0xc) = 1;
    *(uint *)(*unaff_ESI + 8) = (uint)*(ushort *)(local_dc + 0x12);
    *(uint *)(*unaff_ESI + 0x10) = (uint)DAT_1005ab4c;
  }
  else {
    sprintf(local_d8,"TSP_SectionFilter for private section failed(%d)\n",iVar1);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_read",local_d8);
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000dad0 FUN_1000dad0 */

undefined4 __fastcall FUN_1000dad0(undefined4 param_1,int param_2)

{
  int *piVar1;
  undefined4 *puVar2;
  int in_EAX;
  int *piVar3;
  int *piVar4;
  int *piVar5;
  
  piVar4 = (int *)(param_2 + 4);
  piVar5 = (int *)*piVar4;
  if (piVar5 != piVar4) {
    piVar1 = (int *)(in_EAX + 4);
    do {
      piVar3 = (int *)*piVar1;
      if (piVar3 != piVar1) {
        do {
          if ((short)piVar5[2] == (short)piVar3[2]) goto LAB_1000db33;
          piVar3 = (int *)*piVar3;
        } while (piVar3 != piVar1);
      }
      piVar3 = malloc(0xc);
      *(undefined2 *)((int)piVar3 + 10) = *(undefined2 *)((int)piVar5 + 10);
      *(short *)(piVar3 + 2) = (short)piVar5[2];
      puVar2 = *(undefined4 **)(in_EAX + 8);
      *(int **)(in_EAX + 8) = piVar3;
      *piVar3 = (int)piVar1;
      piVar3[1] = (int)puVar2;
      *puVar2 = piVar3;
LAB_1000db33:
      piVar5 = (int *)*piVar5;
    } while (piVar5 != piVar4);
  }
  return 0;
}



/* 1000db50 FUN_1000db50 */

void __fastcall FUN_1000db50(int param_1,int param_2)

{
  int *piVar1;
  int *piVar2;
  undefined4 *puVar3;
  int *piVar4;
  int *piVar5;
  int *local_e0;
  int local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  piVar1 = (int *)(param_2 + 0xc);
  piVar5 = (int *)*piVar1;
  if (piVar5 != piVar1) {
    piVar2 = (int *)(param_1 + 0xc);
    do {
      local_dc = 0;
      piVar4 = (int *)*piVar2;
      if (piVar4 != piVar2) {
        do {
          if ((short)piVar4[3] == (short)piVar5[3]) {
            sprintf(local_d8,
                    "tsmerge_merge_pmt failed: Cannot merge program definitions with same program numbers!!\n"
                   );
            if (DAT_1005bc04 != 0) {
              fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_merge",local_d8);
            }
            __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
            return;
          }
          piVar4 = (int *)*piVar4;
        } while (piVar4 != piVar2);
      }
      local_dc = FUN_1000c200((int)piVar5,(int *)&local_e0);
      if (local_dc != 0) {
        __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
        return;
      }
      puVar3 = *(undefined4 **)(param_1 + 0x10);
      *(int **)(param_1 + 0x10) = local_e0;
      *local_e0 = (int)piVar2;
      local_e0[1] = (int)puVar3;
      *puVar3 = local_e0;
      piVar5 = (int *)*piVar5;
    } while (piVar5 != piVar1);
  }
  local_dc = 0;
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000dc50 FUN_1000dc50 */

void __fastcall FUN_1000dc50(short *param_1,short *param_2)

{
  int *piVar1;
  int *piVar2;
  undefined4 *puVar3;
  int iVar4;
  int *piVar5;
  int *piVar6;
  int *local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  if (((*param_1 != *param_2) || (param_1[2] != param_2[2])) ||
     ((char)param_1[1] != (char)param_2[1])) {
    sprintf(local_d8,"Cannot merge incompatible SDTs");
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_merge",local_d8);
    }
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  piVar1 = (int *)(param_1 + 4);
  piVar6 = (int *)*piVar1;
  if (piVar6 != piVar1) {
    piVar2 = (int *)(param_2 + 4);
    do {
      piVar5 = (int *)*piVar2;
      if (piVar5 != piVar2) {
        do {
          if ((short)piVar5[2] == (short)piVar6[2]) goto LAB_1000dcd4;
          piVar5 = (int *)*piVar5;
        } while (piVar5 != piVar2);
      }
      iVar4 = FUN_1000af50(&local_dc);
      if (iVar4 != 0) break;
      puVar3 = *(undefined4 **)(param_2 + 6);
      *(int **)(param_2 + 6) = local_dc;
      *local_dc = (int)piVar2;
      local_dc[1] = (int)puVar3;
      *puVar3 = local_dc;
LAB_1000dcd4:
      piVar6 = (int *)*piVar6;
    } while (piVar6 != piVar1);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000dd40 FUN_1000dd40 */

void __fastcall FUN_1000dd40(int param_1)

{
  undefined4 *puVar1;
  int iVar2;
  int *piVar3;
  int unaff_EBX;
  int *piVar4;
  int *local_e0;
  int *local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  if (*(short *)(param_1 + 2) != *(short *)(unaff_EBX + 2)) {
    sprintf(local_d8,"Cannot merge incompatible NITs");
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s","ts_merge",local_d8);
    }
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  piVar3 = (int *)(param_1 + 4);
  piVar4 = (int *)*piVar3;
  local_dc = piVar3;
  if (piVar4 != piVar3) {
    do {
      if ((char)piVar4[2] == 'J') {
        iVar2 = FUN_10008b70(&local_e0);
        if (iVar2 != 0) break;
        puVar1 = *(undefined4 **)(unaff_EBX + 8);
        *(int **)(unaff_EBX + 8) = local_e0;
        *local_e0 = unaff_EBX + 4;
        local_e0[1] = (int)puVar1;
        *puVar1 = local_e0;
        piVar3 = local_dc;
      }
      piVar4 = (int *)*piVar4;
    } while (piVar4 != piVar3);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000de10 FUN_1000de10 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void FUN_1000de10(void)

{
  char cVar1;
  int iVar2;
  int iVar3;
  int unaff_ESI;
  undefined4 uVar4;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  uVar4 = 0;
  sprintf(local_d8,"entry parameters, none\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  DAT_1005bc08 = unaff_ESI;
  if (unaff_ESI == 0) {
    uVar4 = 7;
  }
  else {
    printf("VERIFY_SIZE : %d\n",0x69c);
    _DAT_1005bc0c = fread(&DAT_1005bcd0,1,0x69c,(FILE *)DAT_1005bc08);
    if ((int)_DAT_1005bc0c < 0x69c) {
      uVar4 = 2;
    }
    else {
      _DAT_1006bcd4 = 1;
      iVar3 = 0;
      do {
        cVar1 = (&DAT_1005bcd0)[iVar3];
        while (cVar1 != 'G') {
          if (0x69b < iVar3) goto LAB_1000df16;
          cVar1 = (&DAT_1005bcd1)[iVar3];
          iVar3 = iVar3 + 1;
        }
        if (0x69b < iVar3) {
LAB_1000df16:
          uVar4 = 3;
          break;
        }
        cVar1 = (&DAT_1005bcd0)[iVar3];
        for (iVar2 = iVar3; (cVar1 == 'G' && (iVar2 < 0x69c)); iVar2 = iVar2 + 0xbc) {
          cVar1 = *(char *)(iVar2 + 0x1005bd8c);
        }
        DAT_1005bccc = iVar3;
      } while (iVar2 < 0xbc);
    }
    iVar3 = fseek((FILE *)DAT_1005bc08,DAT_1005bccc,0);
    if (iVar3 != 0) {
      uVar4 = 0xe;
    }
    DAT_10014398 = 0xffff;
  }
  sprintf(local_d8,"finished, result %d, offset %d\n",uVar4,DAT_1005bccc);
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000df90 FUN_1000df90 */

void FUN_1000df90(void)

{
  int iVar1;
  undefined4 uVar2;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  uVar2 = 0;
  sprintf(local_d8,"entering GoToBeginOfStream\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  iVar1 = fseek(DAT_1005bc08,DAT_1005bccc,0);
  if (iVar1 != 0) {
    uVar2 = 0xe;
  }
  DAT_10014398 = 0xffff;
  sprintf(local_d8,"leaving GoToBeginOfStream with result %d\n",uVar2);
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000e060 FUN_1000e060 */

void __thiscall FUN_1000e060(void *this,int param_1,uint param_2,int *param_3,int *param_4)

{
  bool bVar1;
  int iVar2;
  uint uVar3;
  int iVar4;
  byte *pbVar5;
  int *piVar6;
  int local_e4;
  int local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_dc = 0;
  bVar1 = false;
  local_e4 = 0;
  sprintf(local_d8,"entering Section Filter\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
LAB_1000e0e0:
  do {
    if (local_dc != 0) break;
    local_dc = FUN_1000e270(this,param_1,param_4);
    sprintf(local_d8,"loop %d\n",local_e4);
    local_e4 = local_e4 + 1;
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    if ((local_dc == 0) && ((param_2 == 0xffffffff || (*(byte *)this == param_2)))) {
      iVar4 = *param_4;
      uVar3 = 0xffffffff;
      bVar1 = true;
      pbVar5 = this;
      if (iVar4 != 0) {
        do {
          uVar3 = uVar3 << 8 ^ *(uint *)(&DAT_10014628 + (uVar3 >> 0x18 ^ (uint)*pbVar5) * 4);
          pbVar5 = pbVar5 + 1;
          iVar4 = iVar4 + -1;
        } while (iVar4 != 0);
        if (uVar3 == 0) {
          piVar6 = param_3;
          if (param_3 != (int *)0x0) {
            do {
              if (!bVar1) goto LAB_1000e0e0;
              uVar3 = (uint)*(byte *)(*piVar6 + (int)this);
              iVar4 = 1;
              if (1 < piVar6[1]) {
                do {
                  iVar2 = iVar4 + *piVar6;
                  iVar4 = iVar4 + 1;
                  uVar3 = uVar3 * 0x100 + (uint)*(byte *)((int)this + iVar2);
                } while (iVar4 < piVar6[1]);
              }
              if ((piVar6[2] & uVar3) == piVar6[3]) {
                piVar6 = (int *)piVar6[4];
              }
              else {
                bVar1 = false;
              }
            } while (piVar6 != (int *)0x0);
            goto LAB_1000e203;
          }
          break;
        }
      }
      local_dc = 0xc;
      break;
    }
LAB_1000e203:
  } while (!bVar1);
  sprintf(local_d8,"leaving Section Filter with result %d\n",local_dc);
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000e270 FUN_1000e270 */

void __thiscall FUN_1000e270(void *this,int param_1,int *param_2)

{
  int iVar1;
  int iVar2;
  int iVar3;
  undefined1 *puVar4;
  int iVar5;
  undefined4 local_e8;
  int local_e4;
  undefined4 local_e0;
  int local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  iVar5 = 0;
  local_dc = 0;
  sprintf(local_d8,"entering ReadSection\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  if (param_1 == DAT_10014398) {
    FUN_1000e550((byte *)&local_e8);
    iVar1 = FUN_1000e980(this,local_e8,local_e4,local_e0,&local_dc);
    if (*(char *)(DAT_1006bcd8 + (int)this) != -1) {
      iVar2 = 0;
      if (DAT_1006bcd8 < local_dc) {
        iVar2 = local_dc - DAT_1006bcd8;
        iVar3 = iVar2;
        puVar4 = this;
        do {
          *puVar4 = puVar4[DAT_1006bcd8];
          puVar4 = puVar4 + 1;
          iVar3 = iVar3 + -1;
        } while (iVar3 != 0);
      }
LAB_1000e3e3:
      if (iVar1 == 0) {
        DAT_10014398 = param_1;
LAB_1000e3f4:
        if (iVar1 == 0) {
          if (((iVar5 != 0) ||
              ((1 < iVar2 + 1 &&
               (iVar5 = (CONCAT11(*(undefined1 *)((int)this + 1),*(undefined1 *)((int)this + 2)) &
                        0xfff) + 3, iVar5 != 0)))) && (iVar5 <= iVar2)) {
            if (iVar5 < iVar2) {
              DAT_1006bcd8 = (local_dc - iVar2) + iVar5;
            }
            else {
              DAT_10014398 = 0xffff;
            }
            goto LAB_1000e4ba;
          }
          do {
            iVar1 = FUN_1000e8b0();
            FUN_1000e550((byte *)&local_e8);
            if ((local_e4 == param_1) && (local_e0._2_1_ == '\x01')) {
              if (iVar1 != 0) goto LAB_1000e4ba;
              iVar1 = FUN_1000e980((void *)((int)this + iVar2),local_e8,local_e4,local_e0,&local_dc)
              ;
              iVar2 = iVar2 + local_dc;
              break;
            }
          } while (iVar1 == 0);
          goto LAB_1000e3f4;
        }
LAB_1000e4ba:
        *param_2 = iVar5;
      }
      goto LAB_1000e4c2;
    }
  }
  iVar1 = FUN_1000e8b0();
  if (iVar1 == 0) {
    do {
      FUN_1000e550((byte *)&local_e8);
      do {
        if ((local_e4 == param_1) && (local_e8._2_1_ == '\x01')) {
          if (iVar1 != 0) goto LAB_1000e4c2;
          iVar1 = FUN_1000e980(this,local_e8,local_e4,local_e0,&local_dc);
          iVar2 = local_dc;
          goto LAB_1000e3e3;
        }
        if (iVar1 != 0) goto LAB_1000e4c2;
        iVar1 = FUN_1000e8b0();
      } while (iVar1 != 0);
    } while( true );
  }
LAB_1000e4c2:
  sprintf(local_d8,"length found %d\n",iVar5);
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  sprintf(local_d8,"leaving ReadSection with result %d\n",iVar1);
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000e550 FUN_1000e550 */

void __fastcall FUN_1000e550(byte *param_1)

{
  undefined4 local_dc;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  local_dc = 0;
  sprintf(local_d8,"entering ReadPacketHeader\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  *param_1 = DAT_1005bc10;
  if (DAT_1005bc10 == 0x47) {
    param_1[1] = DAT_1005bc11 >> 7;
    param_1[2] = DAT_1005bc11 >> 6 & 1;
    param_1[3] = DAT_1005bc11 >> 5 & 1;
    *(uint *)(param_1 + 4) = (DAT_1005bc11 & 0x1f) * 0x100 + (uint)DAT_1005bc12;
    param_1[8] = DAT_1005bc13 >> 6;
    param_1[9] = DAT_1005bc13 >> 5 & 1;
    param_1[10] = DAT_1005bc13 >> 4 & 1;
    param_1[0xb] = DAT_1005bc13 & 4;
    sprintf(local_d8,"packet header\n");
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"sync bytes              %x\n",(uint)*param_1);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"error indicator         %x\n",(uint)param_1[1]);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"payload start indicator %x\n",(uint)param_1[2]);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"transport priority      %x\n",(uint)param_1[3]);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"packet identifier       %x\n",*(undefined4 *)(param_1 + 4));
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"scrambling flags        %x\n",(uint)param_1[8]);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"adaptation field        %x\n",(uint)param_1[9]);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"payload flag            %x\n",(uint)param_1[10]);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
    sprintf(local_d8,"continuity counter      %x\n",(uint)param_1[0xb]);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
  }
  else {
    local_dc = 8;
  }
  sprintf(local_d8,"leaving ReadPacketHeader with result %d\n",local_dc);
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000e8b0 FUN_1000e8b0 */

void FUN_1000e8b0(void)

{
  size_t sVar1;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  sprintf(local_d8,"entering IncrementToNextPacket\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  sVar1 = fread(&DAT_1005bc10,1,0xbc,DAT_1005bc08);
  if ((int)sVar1 < 0xbc) {
    sprintf(local_d8,"leaving IncrementToNextPacket with result %d\n",9);
    if (DAT_1005bc04 != 0) {
      fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
    }
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000e980 FUN_1000e980 */

void __thiscall
FUN_1000e980(void *this,undefined4 param_1,undefined4 param_2,undefined4 param_3,int *param_4)

{
  uint uVar1;
  uint uVar2;
  undefined4 uVar3;
  char local_d8 [208];
  uint local_8;
  
  local_8 = DAT_1001409c ^ (uint)&stack0xfffffffc;
  uVar3 = 0;
  uVar2 = 4;
  sprintf(local_d8,"entering ReadPayload\n");
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  if (param_3._2_1_ == '\0') {
    uVar3 = 10;
  }
  else {
    if (param_3._1_1_ == '\x01') {
      uVar2 = DAT_1005bc14 + 5;
    }
    if (param_1._2_1_ == '\x01') {
      uVar2 = uVar2 + 1 + (uint)(byte)(&DAT_1005bc10)[uVar2];
    }
    uVar1 = uVar2;
    if (uVar2 < 0xbc) {
      do {
        *(undefined1 *)this = (&DAT_1005bc10)[uVar1];
        uVar1 = uVar1 + 1;
        this = (void *)((int)this + 1);
      } while ((int)uVar1 < 0xbc);
    }
    *param_4 = 0xbc - uVar2;
  }
  sprintf(local_d8,"leaving ReadPayload with result %d\n",uVar3);
  if (DAT_1005bc04 != 0) {
    fprintf((FILE *)DAT_1005bc04,"%s : %s",&DAT_100129b4,local_d8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 1000ea90 operator_delete */

void __cdecl operator_delete(void *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000ea90. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  operator_delete(param_1);
  return;
}



/* 1000ea96 operator_new */

void * __cdecl operator_new(uint param_1)

{
  void *pvVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ea96. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pvVar1 = operator_new(param_1);
  return pvVar1;
}



/* 1000ea9c GetConnectionHook */

IConnectionPoint * __thiscall CCmdTarget::GetConnectionHook(CCmdTarget *this,_GUID *param_1)

{
  IConnectionPoint *pIVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ea9c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pIVar1 = GetConnectionHook(this,param_1);
  return pIVar1;
}



/* 1000eaa2 GetExtraConnectionPoints */

int __thiscall CCmdTarget::GetExtraConnectionPoints(CCmdTarget *this,CPtrArray *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eaa2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = GetExtraConnectionPoints(this,param_1);
  return iVar1;
}



/* 1000eaa8 GetInterfaceHook */

IUnknown * __thiscall CCmdTarget::GetInterfaceHook(CCmdTarget *this,void *param_1)

{
  IUnknown *pIVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eaa8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pIVar1 = GetInterfaceHook(this,param_1);
  return pIVar1;
}



/* 1000eaae OnCreateAggregates */

int __thiscall CCmdTarget::OnCreateAggregates(CCmdTarget *this)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eaae. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnCreateAggregates(this);
  return iVar1;
}



/* 1000eab4 GetEventSinkMap */

AFX_EVENTSINKMAP * __thiscall CCmdTarget::GetEventSinkMap(CCmdTarget *this)

{
  AFX_EVENTSINKMAP *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eab4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = GetEventSinkMap(this);
  return pAVar1;
}



/* 1000eaba GetConnectionMap */

AFX_CONNECTIONMAP * __thiscall CCmdTarget::GetConnectionMap(CCmdTarget *this)

{
  AFX_CONNECTIONMAP *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eaba. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = GetConnectionMap(this);
  return pAVar1;
}



/* 1000eac0 GetDispatchMap */

AFX_DISPMAP * __thiscall CCmdTarget::GetDispatchMap(CCmdTarget *this)

{
  AFX_DISPMAP *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eac0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = GetDispatchMap(this);
  return pAVar1;
}



/* 1000eac6 GetCommandMap */

AFX_OLECMDMAP * __thiscall CCmdTarget::GetCommandMap(CCmdTarget *this)

{
  AFX_OLECMDMAP *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eac6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = GetCommandMap(this);
  return pAVar1;
}



/* 1000eacc GetTypeLib */

long __thiscall CCmdTarget::GetTypeLib(CCmdTarget *this,ulong param_1,ITypeLib **param_2)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eacc. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = GetTypeLib(this,param_1,param_2);
  return lVar1;
}



/* 1000ead2 GetTypeLibCache */

CTypeLibCache * __thiscall CCmdTarget::GetTypeLibCache(CCmdTarget *this)

{
  CTypeLibCache *pCVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ead2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pCVar1 = GetTypeLibCache(this);
  return pCVar1;
}



/* 1000ead8 GetTypeInfoCount */

uint __thiscall CCmdTarget::GetTypeInfoCount(CCmdTarget *this)

{
  uint uVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ead8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  uVar1 = GetTypeInfoCount(this);
  return uVar1;
}



/* 1000eade GetDispatchIID */

int __thiscall CCmdTarget::GetDispatchIID(CCmdTarget *this,_GUID *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eade. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = GetDispatchIID(this,param_1);
  return iVar1;
}



/* 1000eae4 IsInvokeAllowed */

int __thiscall CCmdTarget::IsInvokeAllowed(CCmdTarget *this,long param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eae4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = IsInvokeAllowed(this,param_1);
  return iVar1;
}



/* 1000eaea OnCmdMsg */

int __thiscall
CCmdTarget::OnCmdMsg
          (CCmdTarget *this,uint param_1,int param_2,void *param_3,AFX_CMDHANDLERINFO *param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eaea. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnCmdMsg(this,param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000eaf0 OnChildNotify */

int __thiscall
CComboBox::OnChildNotify(CComboBox *this,uint param_1,uint param_2,long param_3,long *param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eaf0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnChildNotify(this,param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000eaf6 DeleteItem */

void __thiscall CComboBox::DeleteItem(CComboBox *this,tagDELETEITEMSTRUCT *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000eaf6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  DeleteItem(this,param_1);
  return;
}



/* 1000eafc CompareItem */

int __thiscall CComboBox::CompareItem(CComboBox *this,tagCOMPAREITEMSTRUCT *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eafc. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CompareItem(this,param_1);
  return iVar1;
}



/* 1000eb02 MeasureItem */

void __thiscall CComboBox::MeasureItem(CComboBox *this,tagMEASUREITEMSTRUCT *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000eb02. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  MeasureItem(this,param_1);
  return;
}



/* 1000eb08 DrawItem */

void __thiscall CComboBox::DrawItem(CComboBox *this,tagDRAWITEMSTRUCT *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000eb08. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  DrawItem(this,param_1);
  return;
}



/* 1000eb0e Create */

int __thiscall
CComboBox::Create(CComboBox *this,ulong param_1,tagRECT *param_2,CWnd *param_3,uint param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb0e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = Create(this,param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000eb14 GetRuntimeClass */

CRuntimeClass * __thiscall CComboBox::GetRuntimeClass(CComboBox *this)

{
  CRuntimeClass *pCVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb14. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pCVar1 = GetRuntimeClass(this);
  return pCVar1;
}



/* 1000eb20 PreInitDialog */

void __thiscall CDialog::PreInitDialog(CDialog *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000eb20. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  PreInitDialog(this);
  return;
}



/* 1000eb26 GetOccDialogInfo */

_AFX_OCC_DIALOG_INFO * __thiscall CDialog::GetOccDialogInfo(CDialog *this)

{
  _AFX_OCC_DIALOG_INFO *p_Var1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb26. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  p_Var1 = GetOccDialogInfo(this);
  return p_Var1;
}



/* 1000eb2c SetOccDialogInfo */

int __thiscall CDialog::SetOccDialogInfo(CDialog *this,_AFX_OCC_DIALOG_INFO *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb2c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = SetOccDialogInfo(this,param_1);
  return iVar1;
}



/* 1000eb32 CheckAutoCenter */

int __thiscall CDialog::CheckAutoCenter(CDialog *this)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb32. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CheckAutoCenter(this);
  return iVar1;
}



/* 1000eb38 OnCmdMsg */

int __thiscall
CDialog::OnCmdMsg(CDialog *this,uint param_1,int param_2,void *param_3,AFX_CMDHANDLERINFO *param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb38. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnCmdMsg(this,param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000eb3e PreTranslateMessage */

int __thiscall CDialog::PreTranslateMessage(CDialog *this,tagMSG *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb3e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = PreTranslateMessage(this,param_1);
  return iVar1;
}



/* 1000eb44 OnOK */

void __thiscall CDialog::OnOK(CDialog *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000eb44. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  OnOK(this);
  return;
}



/* 1000eb4a OnSetFont */

void __thiscall CDialog::OnSetFont(CDialog *this,CFont *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000eb4a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  OnSetFont(this,param_1);
  return;
}



/* 1000eb50 DoModal */

int __thiscall CDialog::DoModal(CDialog *this)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb50. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = DoModal(this);
  return iVar1;
}



/* 1000eb56 CreateIndirect */

int __thiscall
CDialog::CreateIndirect(CDialog *this,DLGTEMPLATE *param_1,CWnd *param_2,void *param_3)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb56. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CreateIndirect(this,param_1,param_2,param_3);
  return iVar1;
}



/* 1000eb5c CreateIndirect */

int __thiscall CDialog::CreateIndirect(CDialog *this,void *param_1,CWnd *param_2)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb5c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CreateIndirect(this,param_1,param_2);
  return iVar1;
}



/* 1000eb62 Create */

int __thiscall CDialog::Create(CDialog *this,char *param_1,CWnd *param_2)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb62. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = Create(this,param_1,param_2);
  return iVar1;
}



/* 1000eb68 GetRuntimeClass */

CRuntimeClass * __thiscall CDialog::GetRuntimeClass(CDialog *this)

{
  CRuntimeClass *pCVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb68. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pCVar1 = GetRuntimeClass(this);
  return pCVar1;
}



/* 1000eb6e GetMessageMap */

AFX_MSGMAP * __thiscall CWnd::GetMessageMap(CWnd *this)

{
  AFX_MSGMAP *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb6e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = GetMessageMap(this);
  return pAVar1;
}



/* 1000eb74 GetOccDialogInfo */

_AFX_OCC_DIALOG_INFO * __thiscall CWnd::GetOccDialogInfo(CWnd *this)

{
  _AFX_OCC_DIALOG_INFO *p_Var1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb74. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  p_Var1 = GetOccDialogInfo(this);
  return p_Var1;
}



/* 1000eb7a SetOccDialogInfo */

int __thiscall CWnd::SetOccDialogInfo(CWnd *this,_AFX_OCC_DIALOG_INFO *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb7a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = SetOccDialogInfo(this,param_1);
  return iVar1;
}



/* 1000eb80 CreateControlSite */

int __thiscall
CWnd::CreateControlSite
          (CWnd *this,COleControlContainer *param_1,COleControlSite **param_2,uint param_3,
          _GUID *param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb80. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CreateControlSite(this,param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000eb86 CreateControlContainer */

int __thiscall CWnd::CreateControlContainer(CWnd *this,COleControlContainer **param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb86. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CreateControlContainer(this,param_1);
  return iVar1;
}



/* 1000eb8c OnFinalRelease */

void __thiscall CWnd::OnFinalRelease(CWnd *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000eb8c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  OnFinalRelease(this);
  return;
}



/* 1000eb92 IsFrameWnd */

int __thiscall CWnd::IsFrameWnd(CWnd *this)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb92. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = IsFrameWnd(this);
  return iVar1;
}



/* 1000eb98 CheckAutoCenter */

int __thiscall CWnd::CheckAutoCenter(CWnd *this)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb98. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CheckAutoCenter(this);
  return iVar1;
}



/* 1000eb9e OnGesturePressAndTap */

int __thiscall CWnd::OnGesturePressAndTap(void)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eb9e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnGesturePressAndTap();
  return iVar1;
}



/* 1000eba4 OnGestureTwoFingerTap */

int __thiscall CWnd::OnGestureTwoFingerTap(void)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000eba4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnGestureTwoFingerTap();
  return iVar1;
}



/* 1000ebaa OnGestureRotate */

int __thiscall CWnd::OnGestureRotate(void)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebaa. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnGestureRotate();
  return iVar1;
}



/* 1000ebb0 OnGesturePan */

int __thiscall CWnd::OnGesturePan(void)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebb0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnGesturePan();
  return iVar1;
}



/* 1000ebb6 OnGestureZoom */

int __thiscall CWnd::OnGestureZoom(void)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebb6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnGestureZoom();
  return iVar1;
}



/* 1000ebbc GetGestureStatus */

ulong __thiscall CWnd::GetGestureStatus(void)

{
  ulong uVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebbc. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  uVar1 = GetGestureStatus();
  return uVar1;
}



/* 1000ebc2 OnTouchInput */

int __thiscall CWnd::OnTouchInput(void)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebc2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnTouchInput();
  return iVar1;
}



/* 1000ebc8 OnTouchInputs */

int __thiscall CWnd::OnTouchInputs(CWnd *this,uint param_1,tagTOUCHINPUT *param_2)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebc8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnTouchInputs(this,param_1,param_2);
  return iVar1;
}



/* 1000ebce OnChildNotify */

int __thiscall CWnd::OnChildNotify(CWnd *this,uint param_1,uint param_2,long param_3,long *param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebce. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnChildNotify(this,param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000ebd4 PostNcDestroy */

void __thiscall CWnd::PostNcDestroy(CWnd *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000ebd4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  PostNcDestroy(this);
  return;
}



/* 1000ebda DefWindowProcA */

long __thiscall CWnd::DefWindowProcA(CWnd *this,uint param_1,uint param_2,long param_3)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebda. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = DefWindowProcA(this,param_1,param_2,param_3);
  return lVar1;
}



/* 1000ebe0 OnWndMsg */

int __thiscall CWnd::OnWndMsg(CWnd *this,uint param_1,uint param_2,long param_3,long *param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebe0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnWndMsg(this,param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000ebe6 WindowProc */

long __thiscall CWnd::WindowProc(CWnd *this,uint param_1,uint param_2,long param_3)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebe6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = WindowProc(this,param_1,param_2,param_3);
  return lVar1;
}



/* 1000ebec OnAmbientProperty */

int __thiscall
CWnd::OnAmbientProperty(CWnd *this,COleControlSite *param_1,long param_2,tagVARIANT *param_3)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebec. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnAmbientProperty(this,param_1,param_2,param_3);
  return iVar1;
}



/* 1000ebf2 PreTranslateMessage */

int __thiscall CWnd::PreTranslateMessage(CWnd *this,tagMSG *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebf2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = PreTranslateMessage(this,param_1);
  return iVar1;
}



/* 1000ebf8 GetSuperWndProcAddr */

_func_long_HWND___ptr_uint_uint_long ** __thiscall CWnd::GetSuperWndProcAddr(CWnd *this)

{
  _func_long_HWND___ptr_uint_uint_long **pp_Var1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebf8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pp_Var1 = GetSuperWndProcAddr(this);
  return pp_Var1;
}



/* 1000ebfe OnNotify */

int __thiscall CWnd::OnNotify(CWnd *this,uint param_1,long param_2,long *param_3)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ebfe. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnNotify(this,param_1,param_2,param_3);
  return iVar1;
}



/* 1000ec04 OnCommand */

int __thiscall CWnd::OnCommand(CWnd *this,uint param_1,long param_2)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec04. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnCommand(this,param_1,param_2);
  return iVar1;
}



/* 1000ec0a CreateAccessibleProxy */

long __thiscall CWnd::CreateAccessibleProxy(CWnd *this,uint param_1,long param_2,long *param_3)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec0a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = CreateAccessibleProxy(this,param_1,param_2,param_3);
  return lVar1;
}



/* 1000ec10 SetProxy */

long __thiscall CWnd::SetProxy(CWnd *this,IAccessibleProxy *param_1)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec10. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = SetProxy(this,param_1);
  return lVar1;
}



/* 1000ec16 put_accValue */

long __thiscall CWnd::put_accValue(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec16. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = put_accValue();
  return lVar1;
}



/* 1000ec1c put_accName */

long __thiscall CWnd::put_accName(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec1c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = put_accName();
  return lVar1;
}



/* 1000ec22 accDoDefaultAction */

long __thiscall CWnd::accDoDefaultAction(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec22. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = accDoDefaultAction();
  return lVar1;
}



/* 1000ec28 accHitTest */

long __thiscall CWnd::accHitTest(CWnd *this,long param_1,long param_2,tagVARIANT *param_3)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec28. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = accHitTest(this,param_1,param_2,param_3);
  return lVar1;
}



/* 1000ec2e accNavigate */

long __thiscall CWnd::accNavigate(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec2e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = accNavigate();
  return lVar1;
}



/* 1000ec34 accLocation */

long __thiscall CWnd::accLocation(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec34. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = accLocation();
  return lVar1;
}



/* 1000ec3a accSelect */

long __thiscall CWnd::accSelect(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec3a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = accSelect();
  return lVar1;
}



/* 1000ec40 get_accDefaultAction */

long __thiscall CWnd::get_accDefaultAction(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec40. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accDefaultAction();
  return lVar1;
}



/* 1000ec46 get_accSelection */

long __thiscall CWnd::get_accSelection(CWnd *this,tagVARIANT *param_1)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec46. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accSelection(this,param_1);
  return lVar1;
}



/* 1000ec4c get_accFocus */

long __thiscall CWnd::get_accFocus(CWnd *this,tagVARIANT *param_1)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec4c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accFocus(this,param_1);
  return lVar1;
}



/* 1000ec52 get_accKeyboardShortcut */

long __thiscall CWnd::get_accKeyboardShortcut(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec52. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accKeyboardShortcut();
  return lVar1;
}



/* 1000ec58 get_accHelpTopic */

long __thiscall CWnd::get_accHelpTopic(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec58. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accHelpTopic();
  return lVar1;
}



/* 1000ec5e get_accHelp */

long __thiscall CWnd::get_accHelp(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec5e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accHelp();
  return lVar1;
}



/* 1000ec64 get_accState */

long __thiscall CWnd::get_accState(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec64. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accState();
  return lVar1;
}



/* 1000ec6a get_accRole */

long __thiscall CWnd::get_accRole(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec6a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accRole();
  return lVar1;
}



/* 1000ec70 get_accDescription */

long __thiscall CWnd::get_accDescription(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec70. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accDescription();
  return lVar1;
}



/* 1000ec76 get_accValue */

long __thiscall CWnd::get_accValue(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec76. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accValue();
  return lVar1;
}



/* 1000ec7c get_accName */

long __thiscall CWnd::get_accName(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec7c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accName();
  return lVar1;
}



/* 1000ec82 get_accChild */

long __thiscall CWnd::get_accChild(void)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec82. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accChild();
  return lVar1;
}



/* 1000ec88 get_accChildCount */

long __thiscall CWnd::get_accChildCount(CWnd *this,long *param_1)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec88. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accChildCount(this,param_1);
  return lVar1;
}



/* 1000ec8e get_accParent */

long __thiscall CWnd::get_accParent(CWnd *this,IDispatch **param_1)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec8e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = get_accParent(this,param_1);
  return lVar1;
}



/* 1000ec94 EnsureStdObj */

long __thiscall CWnd::EnsureStdObj(CWnd *this)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec94. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = EnsureStdObj(this);
  return lVar1;
}



/* 1000ec9a GetInterfaceMap */

AFX_INTERFACEMAP * __thiscall CWnd::GetInterfaceMap(CWnd *this)

{
  AFX_INTERFACEMAP *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ec9a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = GetInterfaceMap(this);
  return pAVar1;
}



/* 1000eca0 OnDrawIconicThumbnailOrLivePreview */

void __thiscall CWnd::OnDrawIconicThumbnailOrLivePreview(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000eca0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  OnDrawIconicThumbnailOrLivePreview();
  return;
}



/* 1000eca6 EndModalLoop */

void __thiscall CWnd::EndModalLoop(CWnd *this,int param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000eca6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  EndModalLoop(this,param_1);
  return;
}



/* 1000ecac ContinueModal */

int __thiscall CWnd::ContinueModal(CWnd *this)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecac. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = ContinueModal(this);
  return iVar1;
}



/* 1000ecb2 WinHelpInternal */

void __thiscall CWnd::WinHelpInternal(CWnd *this,ulong param_1,uint param_2)

{
                    /* WARNING: Could not recover jumptable at 0x1000ecb2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  WinHelpInternal(this,param_1,param_2);
  return;
}



/* 1000ecb8 HtmlHelpA */

void __thiscall CWnd::HtmlHelpA(CWnd *this,ulong param_1,uint param_2)

{
                    /* WARNING: Could not recover jumptable at 0x1000ecb8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  HtmlHelpA(this,param_1,param_2);
  return;
}



/* 1000ecbe WinHelpA */

void __thiscall CWnd::WinHelpA(CWnd *this,ulong param_1,uint param_2)

{
                    /* WARNING: Could not recover jumptable at 0x1000ecbe. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  WinHelpA(this,param_1,param_2);
  return;
}



/* 1000ecc4 GetScrollBarCtrl */

CScrollBar * __thiscall CWnd::GetScrollBarCtrl(CWnd *this,int param_1)

{
  CScrollBar *pCVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecc4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pCVar1 = GetScrollBarCtrl(this,param_1);
  return pCVar1;
}



/* 1000ecca OnToolHitTest */

int __thiscall CWnd::OnToolHitTest(void)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecca. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = OnToolHitTest();
  return iVar1;
}



/* 1000ecd0 SetMenu */

int __thiscall CWnd::SetMenu(CWnd *this,CMenu *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecd0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = SetMenu(this,param_1);
  return iVar1;
}



/* 1000ecd6 GetMenu */

CMenu * __thiscall CWnd::GetMenu(CWnd *this)

{
  CMenu *pCVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecd6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pCVar1 = GetMenu(this);
  return pCVar1;
}



/* 1000ecdc CalcWindowRect */

void __thiscall CWnd::CalcWindowRect(CWnd *this,tagRECT *param_1,uint param_2)

{
                    /* WARNING: Could not recover jumptable at 0x1000ecdc. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  CalcWindowRect(this,param_1,param_2);
  return;
}



/* 1000ece2 PreCreateWindow */

int __thiscall CWnd::PreCreateWindow(CWnd *this,tagCREATESTRUCTA *param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ece2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = PreCreateWindow(this,param_1);
  return iVar1;
}



/* 1000ece8 DestroyWindow */

int __thiscall CWnd::DestroyWindow(CWnd *this)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ece8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = DestroyWindow(this);
  return iVar1;
}



/* 1000ecee CreateEx */

int __thiscall
CWnd::CreateEx(CWnd *this,ulong param_1,char *param_2,char *param_3,ulong param_4,int param_5,
              int param_6,int param_7,int param_8,HWND__ *param_9,HMENU__ *param_10,void *param_11)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecee. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CreateEx(this,param_1,param_2,param_3,param_4,param_5,param_6,param_7,param_8,param_9,
                   param_10,param_11);
  return iVar1;
}



/* 1000ecf4 CreateEx */

int __thiscall
CWnd::CreateEx(CWnd *this,ulong param_1,char *param_2,char *param_3,ulong param_4,tagRECT *param_5,
              CWnd *param_6,uint param_7,void *param_8)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecf4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = CreateEx(this,param_1,param_2,param_3,param_4,param_5,param_6,param_7,param_8);
  return iVar1;
}



/* 1000ecfa Create */

int __thiscall
CWnd::Create(CWnd *this,char *param_1,char *param_2,ulong param_3,tagRECT *param_4,CWnd *param_5,
            uint param_6,CCreateContext *param_7)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000ecfa. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = Create(this,param_1,param_2,param_3,param_4,param_5,param_6,param_7);
  return iVar1;
}



/* 1000ed00 PreSubclassWindow */

void __thiscall CWnd::PreSubclassWindow(CWnd *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000ed00. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  PreSubclassWindow(this);
  return;
}



/* 1000ed06 FUN_1000ed06 */

/* WARNING: Function: __EH_epilog3 replaced with injection: EH_epilog3 */

long FUN_1000ed06(void)

{
  long lVar1;
  int unaff_EBP;
  
  FUN_1000f524(0x10);
  FUN_10001000();
  AFX_MAINTAIN_STATE2::AFX_MAINTAIN_STATE2
            ((AFX_MAINTAIN_STATE2 *)(unaff_EBP + -0x1c),(AFX_MODULE_STATE *)&DAT_1005a660);
  *(undefined4 *)(unaff_EBP + -4) = 0;
  lVar1 = AfxWndProc(*(HWND__ **)(unaff_EBP + 8),*(uint *)(unaff_EBP + 0xc),
                     *(uint *)(unaff_EBP + 0x10),*(long *)(unaff_EBP + 0x14));
  *(undefined4 *)(unaff_EBP + -4) = 0xffffffff;
  AFX_MAINTAIN_STATE2::~AFX_MAINTAIN_STATE2((AFX_MAINTAIN_STATE2 *)(unaff_EBP + -0x1c));
  return lVar1;
}



/* 1000ed51 FUN_1000ed51 */

/* WARNING: Function: __EH_prolog3_catch replaced with injection: EH_prolog3 */
/* WARNING: Function: __EH_epilog3 replaced with injection: EH_epilog3 */

undefined4 FUN_1000ed51(HINSTANCE__ *param_1,int param_2)

{
  _AFX_THREAD_STATE *p_Var1;
  int iVar2;
  undefined4 uVar3;
  int *piVar4;
  CDynLinkLibrary *this;
  AFX_MODULE_STATE *pAVar5;
  AFX_MAINTAIN_STATE2 local_34 [20];
  undefined4 local_20;
  _AFX_THREAD_STATE *local_1c;
  int *local_18;
  undefined4 local_8;
  undefined4 uStack_4;
  
  uStack_4 = 0x24;
  local_8 = 0x1000ed5d;
  if (param_2 != 1) {
    if (param_2 == 0) {
      pAVar5 = AfxSetModuleState((AFX_MODULE_STATE *)&DAT_1005a660);
      p_Var1 = AfxGetThreadState();
      *(AFX_MODULE_STATE **)(p_Var1 + 8) = pAVar5;
      piVar4 = (int *)FUN_1000efb0();
      if (piVar4 != (int *)0x0) {
        (**(code **)(*piVar4 + 0x70))();
      }
      AfxLockTempMaps();
      AfxUnlockTempMaps(-1);
      AfxWinTerm();
      AfxTermExtensionModule((AFX_EXTENSION_MODULE *)&DAT_1005a64c,1);
      if (DAT_1005a644 != 0) {
        p_Var1 = AfxGetThreadState();
        AfxSetModuleState(*(AFX_MODULE_STATE **)(p_Var1 + 8));
      }
    }
    else if (param_2 == 3) {
      AFX_MAINTAIN_STATE2::AFX_MAINTAIN_STATE2(local_34,(AFX_MODULE_STATE *)&DAT_1005a660);
      local_8 = 3;
      AfxLockTempMaps();
      AfxUnlockTempMaps(-1);
      AfxTermThread(param_1);
      local_8 = 0xffffffff;
      AFX_MAINTAIN_STATE2::~AFX_MAINTAIN_STATE2(local_34);
    }
    return 1;
  }
  AfxCoreInitModule();
  p_Var1 = AfxGetThreadState();
  uVar3 = *(undefined4 *)(p_Var1 + 8);
  local_20 = uVar3;
  local_1c = p_Var1;
  iVar2 = AfxWinInit(param_1,(HINSTANCE__ *)0x0,"",0);
  if (iVar2 == 0) {
LAB_1000ed94:
    AfxWinTerm();
    uVar3 = FUN_1000ee27();
    return uVar3;
  }
  piVar4 = (int *)FUN_1000efb0();
  local_18 = piVar4;
  if (piVar4 != (int *)0x0) {
    iVar2 = (**(code **)(*piVar4 + 0x58))();
    if (iVar2 == 0) {
      (**(code **)(*piVar4 + 0x70))();
      goto LAB_1000ed94;
    }
  }
  *(undefined4 *)(p_Var1 + 8) = uVar3;
  AfxInitExtensionModule((AFX_EXTENSION_MODULE *)&DAT_1005a64c,param_1);
  local_8 = 0;
  this = (CDynLinkLibrary *)FUN_1000ef9e(0x40);
  local_8 = CONCAT31(local_8._1_3_,1);
  if (this != (CDynLinkLibrary *)0x0) {
    CDynLinkLibrary::CDynLinkLibrary(this,(AFX_EXTENSION_MODULE *)&DAT_1005a64c,0);
  }
  local_8 = 0xffffffff;
  uVar3 = FUN_1000ee27();
  return uVar3;
}



/* 1000edfe Catch@1000edfe */

undefined * Catch_1000edfe(void)

{
  int unaff_EBP;
  
  if (*(CException **)(unaff_EBP + -0x20) != (CException *)0x0) {
    CException::Delete(*(CException **)(unaff_EBP + -0x20));
  }
  (**(code **)(**(int **)(unaff_EBP + -0x14) + 0x70))();
  AfxWinTerm();
  *(undefined4 *)(unaff_EBP + -4) = 0xffffffff;
  return &DAT_1000ee21;
}



/* 1000ee27 FUN_1000ee27 */

/* WARNING: Function: __EH_epilog3 replaced with injection: EH_epilog3 */

undefined4 FUN_1000ee27(void)

{
  _AFX_THREAD_STATE *p_Var1;
  undefined4 unaff_EBX;
  int unaff_EBP;
  int unaff_ESI;
  
  *(undefined4 *)(unaff_ESI + 8) = unaff_EBX;
  p_Var1 = AfxGetThreadState();
  AfxSetModuleState(*(AFX_MODULE_STATE **)(p_Var1 + 8));
  return *(undefined4 *)(unaff_EBP + 0xc);
}



/* 1000eee1 _RawDllMain@12 */

/* Library Function - Single Match
    _RawDllMain@12
   
   Libraries: Visual Studio 2008 Release, Visual Studio 2010 Release */

undefined4 _RawDllMain_12(undefined4 param_1,int param_2)

{
  HLOCAL hMem;
  _AFX_THREAD_STATE *p_Var1;
  AFX_MODULE_STATE *pAVar2;
  
  if (param_2 == 1) {
    hMem = LocalAlloc(0,0x2000);
    if (hMem == (HLOCAL)0x0) {
      return 0;
    }
    LocalFree(hMem);
    p_Var1 = AfxGetThreadState();
    pAVar2 = AfxSetModuleState((AFX_MODULE_STATE *)&DAT_1005a660);
    *(AFX_MODULE_STATE **)(p_Var1 + 8) = pAVar2;
  }
  else if ((param_2 == 0) && (DAT_1005a644 == 0)) {
    p_Var1 = AfxGetThreadState();
    AfxSetModuleState(*(AFX_MODULE_STATE **)(p_Var1 + 8));
  }
  return 1;
}



/* 1000ef3f _DllMain@12 */

/* Library Function - Single Match
    _DllMain@12
   
   Libraries: Visual Studio 2005 Release, Visual Studio 2008 Release, Visual Studio 2010 Release */

undefined4 _DllMain_12(HINSTANCE__ *param_1,int param_2)

{
  AFX_MODULE_STATE *pAVar1;
  _AFX_THREAD_STATE *p_Var2;
  undefined4 uVar3;
  
  if (DAT_1005a644 != 0) {
    if (param_2 == 1) {
      pAVar1 = AfxGetModuleState();
      *(HINSTANCE__ **)(pAVar1 + 8) = param_1;
      p_Var2 = AfxGetThreadState();
      AfxSetModuleState(*(AFX_MODULE_STATE **)(p_Var2 + 8));
    }
    else if (param_2 != 0) goto LAB_1000ef78;
    return 1;
  }
LAB_1000ef78:
  uVar3 = FUN_1000ed51(param_1,param_2);
  return uVar3;
}



/* 1000ef7e FUN_1000ef7e */

AFX_MODULE_STATE * __thiscall FUN_1000ef7e(void *this,byte param_1)

{
  AFX_MODULE_STATE::~AFX_MODULE_STATE(this);
  if ((param_1 & 1) != 0) {
    CNoTrackObject::operator_delete(this);
  }
  return this;
}



/* 1000ef9e FUN_1000ef9e */

void FUN_1000ef9e(uint param_1)

{
  operator_new(param_1);
  return;
}



/* 1000efb0 FUN_1000efb0 */

undefined4 FUN_1000efb0(void)

{
  AFX_MODULE_STATE *pAVar1;
  
  pAVar1 = AfxGetModuleState();
  return *(undefined4 *)(pAVar1 + 4);
}



/* 1000efb9 __security_check_cookie */

/* Library Function - Single Match
    @__security_check_cookie@4
   
   Libraries: Visual Studio 2005 Release, Visual Studio 2008 Release, Visual Studio 2010 Release
   __fastcall __security_check_cookie,4 */

void __fastcall __security_check_cookie(int param_1)

{
  if (param_1 == DAT_1001409c) {
    return;
  }
                    /* WARNING: Subroutine does not return */
  ___report_gsfailure();
}



/* 1000efc8 FID_conflict:`vector_deleting_destructor' */

/* Library Function - Multiple Matches With Different Base Names
    public: virtual void * __thiscall CDaoRelationFieldInfo::`vector deleting destructor'(unsigned
   int)
    public: virtual void * __thiscall exception::`vector deleting destructor'(unsigned int)
    public: virtual void * __thiscall std::exception::`vector deleting destructor'(unsigned int)
    public: virtual void * __thiscall logic_error::`vector deleting destructor'(unsigned int)
     5 names - too many to list
   
   Libraries: Visual Studio 2008 Release, Visual Studio 2010 Release */

type_info * __thiscall FID_conflict__vector_deleting_destructor_(void *this,byte param_1)

{
  type_info *ptVar1;
  
  if ((param_1 & 2) == 0) {
    type_info::_type_info_dtor_internal_method(this);
    ptVar1 = this;
    if ((param_1 & 1) != 0) {
      operator_delete(this);
    }
  }
  else {
    ptVar1 = (type_info *)((int)this + -4);
    _eh_vector_destructor_iterator_
              (this,0xc,*(int *)ptVar1,type_info::_type_info_dtor_internal_method);
    if ((param_1 & 1) != 0) {
      operator_delete(ptVar1);
    }
  }
  return ptVar1;
}



/* 1000f014 __ArrayUnwind */

/* WARNING: Function: __SEH_prolog4 replaced with injection: SEH_prolog4 */
/* WARNING: Function: __SEH_epilog4 replaced with injection: EH_epilog3 */
/* Library Function - Single Match
    void __stdcall __ArrayUnwind(void *,unsigned int,int,void (__thiscall*)(void *))
   
   Libraries: Visual Studio 2005 Release, Visual Studio 2008 Release, Visual Studio 2010 Release */

void __ArrayUnwind(void *param_1,uint param_2,int param_3,_func_void_void_ptr *param_4)

{
  void *in_stack_ffffffc8;
  
  while( true ) {
    param_3 = param_3 + -1;
    if (param_3 < 0) break;
    (*param_4)(in_stack_ffffffc8);
  }
  return;
}



/* 1000f072 `eh_vector_destructor_iterator' */

/* WARNING: Function: __SEH_prolog4 replaced with injection: SEH_prolog4 */
/* WARNING: Function: __SEH_epilog4 replaced with injection: EH_epilog3 */
/* Library Function - Single Match
    void __stdcall `eh vector destructor iterator'(void *,unsigned int,int,void (__thiscall*)(void
   *))
   
   Libraries: Visual Studio 2008 Release, Visual Studio 2010 Release */

void _eh_vector_destructor_iterator_
               (void *param_1,uint param_2,int param_3,_func_void_void_ptr *param_4)

{
  void *in_stack_ffffffd0;
  
  while( true ) {
    param_3 = param_3 + -1;
    if (param_3 < 0) break;
    (*param_4)(in_stack_ffffffd0);
  }
  FUN_1000f0bd();
  return;
}



/* 1000f0bd FUN_1000f0bd */

void FUN_1000f0bd(void)

{
  int unaff_EBP;
  
  if (*(int *)(unaff_EBP + -0x1c) == 0) {
    __ArrayUnwind(*(void **)(unaff_EBP + 8),*(uint *)(unaff_EBP + 0xc),*(int *)(unaff_EBP + 0x10),
                  *(_func_void_void_ptr **)(unaff_EBP + 0x14));
  }
  return;
}



/* 1000f0d5 __onexit */

/* WARNING: Function: __SEH_prolog4 replaced with injection: SEH_prolog4 */
/* WARNING: Function: __SEH_epilog4 replaced with injection: EH_epilog3 */
/* Library Function - Single Match
    __onexit
   
   Library: Visual Studio 2010 Release */

_onexit_t __cdecl __onexit(_onexit_t param_1)

{
  _onexit_t p_Var1;
  PVOID pvVar2;
  PVOID *ppvVar3;
  PVOID *ppvVar4;
  PVOID local_24;
  PVOID local_20 [5];
  undefined4 uStack_c;
  undefined *local_8;
  
  local_8 = &DAT_10013360;
  uStack_c = 0x1000f0e1;
  local_20[0] = DecodePointer(DAT_1006c014);
  if (local_20[0] == (PVOID)0xffffffff) {
    p_Var1 = _onexit(param_1);
  }
  else {
    _lock(8);
    local_8 = (undefined *)0x0;
    local_20[0] = DecodePointer(DAT_1006c014);
    local_24 = DecodePointer(DAT_1006c010);
    ppvVar4 = &local_24;
    ppvVar3 = local_20;
    pvVar2 = EncodePointer(param_1);
    p_Var1 = (_onexit_t)__dllonexit(pvVar2,ppvVar3,ppvVar4);
    DAT_1006c014 = EncodePointer(local_20[0]);
    DAT_1006c010 = EncodePointer(local_24);
    local_8 = (undefined *)0xfffffffe;
    FUN_1000f16d();
  }
  return p_Var1;
}



/* 1000f16d FUN_1000f16d */

void FUN_1000f16d(void)

{
  _unlock(8);
  return;
}



/* 1000f176 _atexit */

/* Library Function - Single Match
    _atexit
   
   Library: Visual Studio 2010 Release */

int __cdecl _atexit(_func_4879 *param_1)

{
  _onexit_t p_Var1;
  
  p_Var1 = __onexit((_onexit_t)param_1);
  return (p_Var1 != (_onexit_t)0x0) - 1;
}



/* 1000f1db __CRT_INIT@12 */

/* Library Function - Single Match
    __CRT_INIT@12
   
   Library: Visual Studio 2010 Release */

undefined4 __CRT_INIT_12(int *param_1,int *param_2,int *param_3)

{
  bool bVar1;
  void *Exchange;
  BOOL BVar2;
  void *pvVar3;
  int *piVar4;
  int iVar5;
  code *pcVar6;
  int *piVar7;
  int *piVar8;
  
  Exchange = StackBase;
  if (param_2 == (int *)0x0) {
    if (DAT_1005a70c < 1) {
      return 0;
    }
    DAT_1005a70c = DAT_1005a70c + -1;
    bVar1 = false;
    while (pvVar3 = (void *)InterlockedCompareExchange((LONG *)&DAT_1006c008,(LONG)Exchange,0),
          pvVar3 != (void *)0x0) {
      if (pvVar3 == Exchange) {
        bVar1 = true;
        break;
      }
      Sleep(1000);
    }
    if (DAT_1006c004 == 2) {
      param_2 = DecodePointer(DAT_1006c014);
      if (param_2 != (int *)0x0) {
        piVar4 = DecodePointer(DAT_1006c010);
        param_1 = piVar4;
        param_3 = param_2;
        while (piVar4 = piVar4 + -1, param_2 <= piVar4) {
          if ((*piVar4 != 0) && (iVar5 = encoded_null(), *piVar4 != iVar5)) {
            pcVar6 = DecodePointer((PVOID)*piVar4);
            iVar5 = encoded_null();
            *piVar4 = iVar5;
            (*pcVar6)();
            piVar7 = DecodePointer(DAT_1006c014);
            piVar8 = DecodePointer(DAT_1006c010);
            if ((param_3 != piVar7) || (param_1 != piVar8)) {
              piVar4 = piVar8;
              param_1 = piVar8;
              param_2 = piVar7;
              param_3 = piVar7;
            }
          }
        }
        free(param_2);
        DAT_1006c010 = (PVOID)encoded_null();
        DAT_1006c014 = DAT_1006c010;
      }
      DAT_1006c004 = 0;
      if (!bVar1) {
        InterlockedExchange((LONG *)&DAT_1006c008,0);
      }
    }
    else {
      _amsg_exit(0x1f);
    }
  }
  else if (param_2 == (int *)0x1) {
    bVar1 = false;
    while (pvVar3 = (void *)InterlockedCompareExchange((LONG *)&DAT_1006c008,(LONG)Exchange,0),
          pvVar3 != (void *)0x0) {
      if (pvVar3 == Exchange) {
        bVar1 = true;
        break;
      }
      Sleep(1000);
    }
    if (DAT_1006c004 == 0) {
      DAT_1006c004 = 1;
      iVar5 = initterm_e(&DAT_10011378,&DAT_10011380);
      if (iVar5 != 0) {
        return 0;
      }
      initterm(&DAT_10011338,&DAT_10011374);
      DAT_1006c004 = 2;
    }
    else {
      _amsg_exit(0x1f);
    }
    if (!bVar1) {
      InterlockedExchange((LONG *)&DAT_1006c008,0);
    }
    if ((DAT_1006c00c != (code *)0x0) &&
       (BVar2 = __IsNonwritableInCurrentImage((PBYTE)&DAT_1006c00c), BVar2 != 0)) {
      (*DAT_1006c00c)(param_1,2,param_3);
    }
    DAT_1005a70c = DAT_1005a70c + 1;
  }
  return 1;
}



/* 1000f3e5 ___DllMainCRTStartup */

/* WARNING: Function: __SEH_prolog4 replaced with injection: SEH_prolog4 */
/* WARNING: Function: __SEH_epilog4 replaced with injection: EH_epilog3 */
/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */
/* Library Function - Single Match
    ___DllMainCRTStartup
   
   Library: Visual Studio 2010 Release */

int __fastcall ___DllMainCRTStartup(int *param_1,int *param_2,HINSTANCE__ *param_3)

{
  int iVar1;
  undefined4 local_20;
  
  _DAT_100140b0 = param_2;
  if ((param_2 == (int *)0x0) && (DAT_1005a70c == 0)) {
    local_20 = 0;
  }
  else if (((param_2 != (int *)0x1) && (param_2 != (int *)0x2)) ||
          ((local_20 = __RawDllMainProxy_12(param_3,(int)param_2), local_20 != 0 &&
           (local_20 = __CRT_INIT_12((int *)param_3,param_2,param_1), local_20 != 0)))) {
    local_20 = _DllMain_12(param_3,(int)param_2);
    if ((param_2 == (int *)0x1) && (local_20 == 0)) {
      _DllMain_12(param_3,0);
      __CRT_INIT_12((int *)param_3,(int *)0x0,param_1);
      __RawDllMainProxy_12(param_3,0);
    }
    if ((param_2 == (int *)0x0) || (param_2 == (int *)0x3)) {
      iVar1 = __CRT_INIT_12((int *)param_3,param_2,param_1);
      if (iVar1 == 0) {
        local_20 = 0;
      }
      if (local_20 != 0) {
        local_20 = __RawDllMainProxy_12(param_3,(int)param_2);
      }
    }
  }
  FUN_1000f4f0();
  return local_20;
}



/* 1000f4f0 FUN_1000f4f0 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void FUN_1000f4f0(void)

{
  _DAT_100140b0 = 0xffffffff;
  return;
}



/* 1000f4fb entry */

void entry(HINSTANCE__ *param_1,int *param_2,int *param_3)

{
  if (param_2 == (int *)0x1) {
    ___security_init_cookie();
  }
  ___DllMainCRTStartup(param_3,param_2,param_1);
  return;
}



/* 1000f51e __CxxFrameHandler3 */

void __CxxFrameHandler3(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000f51e. Too many branches */
                    /* WARNING: Subroutine does not return */
                    /* WARNING: Treating indirect jump as call */
  __CxxFrameHandler3();
  return;
}



/* 1000f524 FUN_1000f524 */

/* WARNING: Unable to track spacebase fully for stack */
/* WARNING: Variable defined which should be unmapped: param_1 */

void __cdecl FUN_1000f524(int param_1)

{
  int iVar1;
  undefined4 unaff_EBX;
  undefined4 unaff_ESI;
  undefined4 unaff_EDI;
  undefined4 unaff_retaddr;
  uint auStack_1c [5];
  undefined1 local_8 [8];
  
  iVar1 = -param_1;
  *(undefined4 *)((int)auStack_1c + iVar1 + 0x10) = unaff_EBX;
  *(undefined4 *)((int)auStack_1c + iVar1 + 0xc) = unaff_ESI;
  *(undefined4 *)((int)auStack_1c + iVar1 + 8) = unaff_EDI;
  *(uint *)((int)auStack_1c + iVar1 + 4) = DAT_1001409c ^ (uint)&param_1;
  *(undefined4 *)((int)auStack_1c + iVar1) = unaff_retaddr;
  ExceptionList = local_8;
  return;
}



/* 1000f557 __EH_prolog3_catch */

/* WARNING: This is an inlined function */
/* WARNING: Unable to track spacebase fully for stack */
/* WARNING: Variable defined which should be unmapped: param_1 */
/* Library Function - Single Match
    __EH_prolog3_catch
   
   Libraries: Visual Studio 2005, Visual Studio 2008, Visual Studio 2010, Visual Studio 2012 */

void __cdecl __EH_prolog3_catch(int param_1)

{
  int iVar1;
  undefined4 unaff_EBX;
  undefined4 unaff_ESI;
  undefined4 unaff_EDI;
  undefined4 unaff_retaddr;
  uint auStack_1c [5];
  undefined1 local_8 [8];
  
  iVar1 = -param_1;
  *(undefined4 *)((int)auStack_1c + iVar1 + 0x10) = unaff_EBX;
  *(undefined4 *)((int)auStack_1c + iVar1 + 0xc) = unaff_ESI;
  *(undefined4 *)((int)auStack_1c + iVar1 + 8) = unaff_EDI;
  *(uint *)((int)auStack_1c + iVar1 + 4) = DAT_1001409c ^ (uint)&param_1;
  *(undefined4 *)((int)auStack_1c + iVar1) = unaff_retaddr;
  ExceptionList = local_8;
  return;
}



/* 1000f58d __EH_epilog3 */

/* WARNING: This is an inlined function */
/* Library Function - Single Match
    __EH_epilog3
   
   Libraries: Visual Studio 2005, Visual Studio 2008, Visual Studio 2010, Visual Studio 2012 */

void __EH_epilog3(void)

{
  undefined4 *unaff_EBP;
  undefined4 unaff_retaddr;
  
  ExceptionList = (void *)unaff_EBP[-3];
  *unaff_EBP = unaff_retaddr;
  return;
}



/* 1000f5a1 ___report_gsfailure */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */
/* Library Function - Single Match
    ___report_gsfailure
   
   Libraries: Visual Studio 2005 Release, Visual Studio 2008 Release, Visual Studio 2010 Release */

void __cdecl ___report_gsfailure(void)

{
  undefined4 in_EAX;
  HANDLE hProcess;
  undefined4 in_ECX;
  undefined4 in_EDX;
  undefined4 unaff_EBX;
  undefined4 unaff_EBP;
  undefined4 unaff_ESI;
  undefined4 unaff_EDI;
  undefined2 in_ES;
  undefined2 in_CS;
  undefined2 in_SS;
  undefined2 in_DS;
  undefined2 in_FS;
  undefined2 in_GS;
  byte in_AF;
  byte in_TF;
  byte in_IF;
  byte in_NT;
  byte in_AC;
  byte in_VIF;
  byte in_VIP;
  byte in_ID;
  undefined4 unaff_retaddr;
  UINT uExitCode;
  undefined4 local_32c;
  undefined4 local_328;
  
  _DAT_1005a828 =
       (uint)(in_NT & 1) * 0x4000 | (uint)SBORROW4((int)&stack0xfffffffc,0x328) * 0x800 |
       (uint)(in_IF & 1) * 0x200 | (uint)(in_TF & 1) * 0x100 | (uint)((int)&local_32c < 0) * 0x80 |
       (uint)(&stack0x00000000 == (undefined1 *)0x32c) * 0x40 | (uint)(in_AF & 1) * 0x10 |
       (uint)((POPCOUNT((uint)&local_32c & 0xff) & 1U) == 0) * 4 |
       (uint)(&stack0xfffffffc < (undefined1 *)0x328) | (uint)(in_ID & 1) * 0x200000 |
       (uint)(in_VIP & 1) * 0x100000 | (uint)(in_VIF & 1) * 0x80000 | (uint)(in_AC & 1) * 0x40000;
  _DAT_1005a82c = &stack0x00000004;
  _DAT_1005a768 = 0x10001;
  _DAT_1005a710 = 0xc0000409;
  _DAT_1005a714 = 1;
  local_32c = DAT_1001409c;
  local_328 = DAT_100140a0;
  _DAT_1005a71c = unaff_retaddr;
  _DAT_1005a7f4 = in_GS;
  _DAT_1005a7f8 = in_FS;
  _DAT_1005a7fc = in_ES;
  _DAT_1005a800 = in_DS;
  _DAT_1005a804 = unaff_EDI;
  _DAT_1005a808 = unaff_ESI;
  _DAT_1005a80c = unaff_EBX;
  _DAT_1005a810 = in_EDX;
  _DAT_1005a814 = in_ECX;
  _DAT_1005a818 = in_EAX;
  _DAT_1005a81c = unaff_EBP;
  DAT_1005a820 = unaff_retaddr;
  _DAT_1005a824 = in_CS;
  _DAT_1005a830 = in_SS;
  DAT_1005a760 = IsDebuggerPresent();
  _crt_debugger_hook(1);
  SetUnhandledExceptionFilter((LPTOP_LEVEL_EXCEPTION_FILTER)0x0);
  UnhandledExceptionFilter((_EXCEPTION_POINTERS *)&PTR_DAT_10011444);
  if (DAT_1005a760 == 0) {
    _crt_debugger_hook(1);
  }
  uExitCode = 0xc0000409;
  hProcess = GetCurrentProcess();
  TerminateProcess(hProcess,uExitCode);
  return;
}



/* 1000f6b0 __SEH_prolog4 */

/* WARNING: This is an inlined function */
/* WARNING: Unable to track spacebase fully for stack */
/* WARNING: Variable defined which should be unmapped: param_2 */
/* Library Function - Single Match
    __SEH_prolog4
   
   Library: Visual Studio */

void __cdecl __SEH_prolog4(undefined4 param_1,int param_2)

{
  int iVar1;
  undefined4 unaff_EBX;
  undefined4 unaff_ESI;
  undefined4 unaff_EDI;
  undefined4 unaff_retaddr;
  uint auStack_1c [5];
  undefined1 local_8 [8];
  
  iVar1 = -param_2;
  *(undefined4 *)((int)auStack_1c + iVar1 + 0x10) = unaff_EBX;
  *(undefined4 *)((int)auStack_1c + iVar1 + 0xc) = unaff_ESI;
  *(undefined4 *)((int)auStack_1c + iVar1 + 8) = unaff_EDI;
  *(uint *)((int)auStack_1c + iVar1 + 4) = DAT_1001409c ^ (uint)&param_2;
  *(undefined4 *)((int)auStack_1c + iVar1) = unaff_retaddr;
  ExceptionList = local_8;
  return;
}



/* 1000f6f5 __SEH_epilog4 */

/* WARNING: This is an inlined function */
/* Library Function - Single Match
    __SEH_epilog4
   
   Library: Visual Studio */

void __SEH_epilog4(void)

{
  undefined4 *unaff_EBP;
  undefined4 unaff_retaddr;
  
  ExceptionList = (void *)unaff_EBP[-4];
  *unaff_EBP = unaff_retaddr;
  return;
}



/* 1000f709 FUN_1000f709 */

void __cdecl
FUN_1000f709(undefined4 param_1,undefined4 param_2,undefined4 param_3,undefined4 param_4)

{
  except_handler4_common(&DAT_1001409c,__security_check_cookie,param_1,param_2,param_3,param_4);
  return;
}



/* 1000f72e _unlock */

void __cdecl _unlock(int _File)

{
                    /* WARNING: Could not recover jumptable at 0x1000f72e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _unlock(_File);
  return;
}



/* 1000f734 __dllonexit */

void __dllonexit(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000f734. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  __dllonexit();
  return;
}



/* 1000f73a _lock */

void __cdecl _lock(int _File)

{
                    /* WARNING: Could not recover jumptable at 0x1000f73a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _lock(_File);
  return;
}



/* 1000f7a0 __ValidateImageBase */

/* Library Function - Single Match
    __ValidateImageBase
   
   Libraries: Visual Studio 2008 Release, Visual Studio 2010 Release */

BOOL __cdecl __ValidateImageBase(PBYTE pImageBase)

{
  if ((*(short *)pImageBase == 0x5a4d) &&
     (*(int *)(pImageBase + *(int *)(pImageBase + 0x3c)) == 0x4550)) {
    return (uint)((short)*(int *)((int)(pImageBase + *(int *)(pImageBase + 0x3c)) + 0x18) == 0x10b);
  }
  return 0;
}



/* 1000f7e0 __FindPESection */

/* Library Function - Single Match
    __FindPESection
   
   Library: Visual Studio 2010 Release */

PIMAGE_SECTION_HEADER __cdecl __FindPESection(PBYTE pImageBase,DWORD_PTR rva)

{
  int iVar1;
  PIMAGE_SECTION_HEADER p_Var2;
  uint uVar3;
  
  iVar1 = *(int *)(pImageBase + 0x3c);
  uVar3 = 0;
  p_Var2 = (PIMAGE_SECTION_HEADER)
           (pImageBase + *(ushort *)(pImageBase + iVar1 + 0x14) + 0x18 + iVar1);
  if (*(ushort *)(pImageBase + iVar1 + 6) != 0) {
    do {
      if ((p_Var2->VirtualAddress <= rva) &&
         (rva < (p_Var2->Misc).PhysicalAddress + p_Var2->VirtualAddress)) {
        return p_Var2;
      }
      uVar3 = uVar3 + 1;
      p_Var2 = p_Var2 + 1;
    } while (uVar3 < *(ushort *)(pImageBase + iVar1 + 6));
  }
  return (PIMAGE_SECTION_HEADER)0x0;
}



/* 1000f830 __IsNonwritableInCurrentImage */

/* Library Function - Single Match
    __IsNonwritableInCurrentImage
   
   Library: Visual Studio 2010 Release */

BOOL __cdecl __IsNonwritableInCurrentImage(PBYTE pTarget)

{
  BOOL BVar1;
  PIMAGE_SECTION_HEADER p_Var2;
  void *local_14;
  code *pcStack_10;
  uint local_c;
  undefined4 local_8;
  
  pcStack_10 = FUN_1000f709;
  local_14 = ExceptionList;
  local_c = DAT_1001409c ^ 0x100133a8;
  ExceptionList = &local_14;
  local_8 = 0;
  BVar1 = __ValidateImageBase((PBYTE)&IMAGE_DOS_HEADER_10000000);
  if (BVar1 != 0) {
    p_Var2 = __FindPESection((PBYTE)&IMAGE_DOS_HEADER_10000000,(DWORD_PTR)(pTarget + -0x10000000));
    if (p_Var2 != (PIMAGE_SECTION_HEADER)0x0) {
      ExceptionList = local_14;
      return ~(p_Var2->Characteristics >> 0x1f) & 1;
    }
  }
  ExceptionList = local_14;
  return 0;
}



/* 1000f8ec initterm */

void __cdecl initterm(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000f8ec. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  initterm();
  return;
}



/* 1000f8f2 initterm_e */

void __cdecl initterm_e(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000f8f2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  initterm_e();
  return;
}



/* 1000f8f8 _amsg_exit */

void __cdecl _amsg_exit(int param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000f8f8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _amsg_exit(param_1);
  return;
}



/* 1000f904 ___security_init_cookie */

/* Library Function - Single Match
    ___security_init_cookie
   
   Library: Visual Studio 2010 Release */

void __cdecl ___security_init_cookie(void)

{
  DWORD DVar1;
  DWORD DVar2;
  DWORD DVar3;
  uint uVar4;
  LARGE_INTEGER local_14;
  _FILETIME local_c;
  
  local_c.dwLowDateTime = 0;
  local_c.dwHighDateTime = 0;
  if ((DAT_1001409c == 0xbb40e64e) || ((DAT_1001409c & 0xffff0000) == 0)) {
    GetSystemTimeAsFileTime(&local_c);
    uVar4 = local_c.dwHighDateTime ^ local_c.dwLowDateTime;
    DVar1 = GetCurrentProcessId();
    DVar2 = GetCurrentThreadId();
    DVar3 = GetTickCount();
    QueryPerformanceCounter(&local_14);
    DAT_1001409c = uVar4 ^ DVar1 ^ DVar2 ^ DVar3 ^ local_14.s.HighPart ^ local_14.s.LowPart;
    if (DAT_1001409c == 0xbb40e64e) {
      DAT_1001409c = 0xbb40e64f;
    }
    else if ((DAT_1001409c & 0xffff0000) == 0) {
      DAT_1001409c = DAT_1001409c | (DAT_1001409c | 0x4711) << 0x10;
    }
  }
  DAT_100140a0 = ~DAT_1001409c;
  return;
}



/* 1000f9a0 _crt_debugger_hook */

void __cdecl _crt_debugger_hook(int param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9a0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _crt_debugger_hook(param_1);
  return;
}



/* 1000f9a6 _type_info_dtor_internal_method */

void __thiscall type_info::_type_info_dtor_internal_method(type_info *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9a6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _type_info_dtor_internal_method(this);
  return;
}



/* 1000f9ac except_handler4_common */

void __cdecl except_handler4_common(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9ac. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  except_handler4_common();
  return;
}



/* 1000f9b8 ~AFX_MODULE_STATE */

void __thiscall AFX_MODULE_STATE::~AFX_MODULE_STATE(AFX_MODULE_STATE *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9b8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ~AFX_MODULE_STATE(this);
  return;
}



/* 1000f9be ~AFX_MAINTAIN_STATE2 */

void __thiscall AFX_MAINTAIN_STATE2::~AFX_MAINTAIN_STATE2(AFX_MAINTAIN_STATE2 *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9be. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ~AFX_MAINTAIN_STATE2(this);
  return;
}



/* 1000f9c4 AfxWndProc */

long AfxWndProc(HWND__ *param_1,uint param_2,uint param_3,long param_4)

{
  long lVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000f9c4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  lVar1 = AfxWndProc(param_1,param_2,param_3,param_4);
  return lVar1;
}



/* 1000f9ca AFX_MAINTAIN_STATE2 */

void __thiscall
AFX_MAINTAIN_STATE2::AFX_MAINTAIN_STATE2(AFX_MAINTAIN_STATE2 *this,AFX_MODULE_STATE *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9ca. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AFX_MAINTAIN_STATE2(this,param_1);
  return;
}



/* 1000f9d0 AfxTermThread */

void AfxTermThread(HINSTANCE__ *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9d0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AfxTermThread(param_1);
  return;
}



/* 1000f9d6 AfxTermExtensionModule */

void AfxTermExtensionModule(AFX_EXTENSION_MODULE *param_1,int param_2)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9d6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AfxTermExtensionModule(param_1,param_2);
  return;
}



/* 1000f9dc AfxUnlockTempMaps */

int AfxUnlockTempMaps(int param_1)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000f9dc. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = AfxUnlockTempMaps(param_1);
  return iVar1;
}



/* 1000f9e2 AfxLockTempMaps */

void AfxLockTempMaps(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9e2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AfxLockTempMaps();
  return;
}



/* 1000f9e8 AfxSetModuleState */

AFX_MODULE_STATE * AfxSetModuleState(AFX_MODULE_STATE *param_1)

{
  AFX_MODULE_STATE *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000f9e8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = AfxSetModuleState(param_1);
  return pAVar1;
}



/* 1000f9ee Delete */

void __thiscall CException::Delete(CException *this)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9ee. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  Delete(this);
  return;
}



/* 1000f9f4 CDynLinkLibrary */

void __thiscall
CDynLinkLibrary::CDynLinkLibrary(CDynLinkLibrary *this,AFX_EXTENSION_MODULE *param_1,int param_2)

{
                    /* WARNING: Could not recover jumptable at 0x1000f9f4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  CDynLinkLibrary(this,param_1,param_2);
  return;
}



/* 1000f9fa AfxInitExtensionModule */

int AfxInitExtensionModule(AFX_EXTENSION_MODULE *param_1,HINSTANCE__ *param_2)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000f9fa. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = AfxInitExtensionModule(param_1,param_2);
  return iVar1;
}



/* 1000fa00 AfxWinTerm */

void AfxWinTerm(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000fa00. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AfxWinTerm();
  return;
}



/* 1000fa06 AfxWinInit */

int AfxWinInit(HINSTANCE__ *param_1,HINSTANCE__ *param_2,char *param_3,int param_4)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000fa06. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = AfxWinInit(param_1,param_2,param_3,param_4);
  return iVar1;
}



/* 1000fa0c AfxGetThreadState */

_AFX_THREAD_STATE * AfxGetThreadState(void)

{
  _AFX_THREAD_STATE *p_Var1;
  
                    /* WARNING: Could not recover jumptable at 0x1000fa0c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  p_Var1 = AfxGetThreadState();
  return p_Var1;
}



/* 1000fa12 AfxCoreInitModule */

void AfxCoreInitModule(void)

{
                    /* WARNING: Could not recover jumptable at 0x1000fa12. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AfxCoreInitModule();
  return;
}



/* 1000fa18 AfxGetModuleState */

AFX_MODULE_STATE * AfxGetModuleState(void)

{
  AFX_MODULE_STATE *pAVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000fa18. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pAVar1 = AfxGetModuleState();
  return pAVar1;
}



/* 1000fa1e AFX_MODULE_STATE */

void __thiscall
AFX_MODULE_STATE::AFX_MODULE_STATE
          (AFX_MODULE_STATE *this,int param_1,_func_long_HWND___ptr_uint_uint_long *param_2,
          ulong param_3,int param_4)

{
                    /* WARNING: Could not recover jumptable at 0x1000fa1e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AFX_MODULE_STATE(this,param_1,param_2,param_3,param_4);
  return;
}



/* 1000fa24 operator_delete */

void CNoTrackObject::operator_delete(void *param_1)

{
                    /* WARNING: Could not recover jumptable at 0x1000fa24. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  operator_delete(param_1);
  return;
}



/* 1000fa2a __IsNonwritableInCurrentImage */

/* WARNING: Function: __SEH_prolog4 replaced with injection: SEH_prolog4 */
/* WARNING: Function: __SEH_epilog4 replaced with injection: EH_epilog3 */
/* WARNING: Enum "SectionFlags": Some values do not have unique names */
/* Library Function - Single Match
    __IsNonwritableInCurrentImage
   
   Library: Visual Studio 2010 Release */

BOOL __cdecl __IsNonwritableInCurrentImage(PBYTE pTarget)

{
  uint uVar1;
  int in_ECX;
  IMAGE_SECTION_HEADER *pIVar2;
  uint local_20;
  
  pIVar2 = &IMAGE_SECTION_HEADER_100001f0;
  for (local_20 = 0; local_20 < 5; local_20 = local_20 + 1) {
    if (((uint)pIVar2->VirtualAddress <= in_ECX + 0xf0000000U) &&
       (in_ECX + 0xf0000000U < (pIVar2->Misc).PhysicalAddress + pIVar2->VirtualAddress))
    goto LAB_1000faac;
    pIVar2 = pIVar2 + 1;
  }
  pIVar2 = (IMAGE_SECTION_HEADER *)0x0;
LAB_1000faac:
  if (pIVar2 == (IMAGE_SECTION_HEADER *)0x0) {
    uVar1 = 0;
  }
  else {
    uVar1 = ~(pIVar2->Characteristics >> 0x1f) & 1;
  }
  return uVar1;
}



/* 1000faea __RawDllMainProxy@12 */

/* WARNING: Removing unreachable block (ram,0x1000fb0d) */
/* Library Function - Single Match
    __RawDllMainProxy@12
   
   Library: Visual Studio 2010 Release */

undefined4 __RawDllMainProxy_12(undefined4 param_1,int param_2)

{
  HLOCAL hMem;
  _AFX_THREAD_STATE *p_Var1;
  AFX_MODULE_STATE *pAVar2;
  BOOL BVar3;
  PBYTE unaff_EBP;
  
  BVar3 = __IsNonwritableInCurrentImage(unaff_EBP);
  if (BVar3 == 0) {
    return 0;
  }
  if (param_2 == 1) {
    hMem = LocalAlloc(0,0x2000);
    if (hMem == (HLOCAL)0x0) {
      return 0;
    }
    LocalFree(hMem);
    p_Var1 = AfxGetThreadState();
    pAVar2 = AfxSetModuleState((AFX_MODULE_STATE *)&DAT_1005a660);
    *(AFX_MODULE_STATE **)(p_Var1 + 8) = pAVar2;
  }
  else if ((param_2 == 0) && (DAT_1005a644 == 0)) {
    p_Var1 = AfxGetThreadState();
    AfxSetModuleState(*(AFX_MODULE_STATE **)(p_Var1 + 8));
  }
  return 1;
}



/* 1000fb14 memcpy */

void * __cdecl memcpy(void *_Dst,void *_Src,size_t _Size)

{
  void *pvVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000fb14. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pvVar1 = memcpy(_Dst,_Src,_Size);
  return pvVar1;
}



/* 1000fb20 __aulldvrm */

/* Library Function - Single Match
    __aulldvrm
   
   Library: Visual Studio */

undefined8 __aulldvrm(uint param_1,uint param_2,uint param_3,uint param_4)

{
  ulonglong uVar1;
  longlong lVar2;
  uint uVar3;
  int iVar4;
  uint uVar5;
  uint uVar7;
  uint uVar8;
  uint uVar9;
  uint uVar6;
  
  uVar9 = param_1;
  uVar6 = param_4;
  uVar7 = param_2;
  uVar3 = param_3;
  if (param_4 == 0) {
    uVar3 = param_2 / param_3;
    iVar4 = (int)(((ulonglong)param_2 % (ulonglong)param_3 << 0x20 | (ulonglong)param_1) /
                 (ulonglong)param_3);
  }
  else {
    do {
      uVar5 = uVar6 >> 1;
      uVar3 = (uint)(CONCAT14((uVar6 & 1) != 0,uVar3) >> 1);
      uVar8 = uVar7 >> 1;
      uVar9 = (uint)(CONCAT14((uVar7 & 1) != 0,uVar9) >> 1);
      uVar6 = uVar5;
      uVar7 = uVar8;
    } while (uVar5 != 0);
    uVar1 = CONCAT44(uVar8,uVar9) / (ulonglong)uVar3;
    iVar4 = (int)uVar1;
    lVar2 = (ulonglong)param_3 * (uVar1 & 0xffffffff);
    uVar3 = (uint)((ulonglong)lVar2 >> 0x20);
    uVar9 = uVar3 + iVar4 * param_4;
    if (((CARRY4(uVar3,iVar4 * param_4)) || (param_2 < uVar9)) ||
       ((param_2 <= uVar9 && (param_1 < (uint)lVar2)))) {
      iVar4 = iVar4 + -1;
    }
    uVar3 = 0;
  }
  return CONCAT44(uVar3,iVar4);
}



/* 1000fbb6 memset */

void * __cdecl memset(void *_Dst,int _Val,size_t _Size)

{
  void *pvVar1;
  
                    /* WARNING: Could not recover jumptable at 0x1000fbb6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pvVar1 = memset(_Dst,_Val,_Size);
  return pvVar1;
}



/* 1000fbc0 Unwind@1000fbc0 */

void Unwind_1000fbc0(void)

{
  int unaff_EBP;
  
  AFX_MAINTAIN_STATE2::~AFX_MAINTAIN_STATE2((AFX_MAINTAIN_STATE2 *)(unaff_EBP + -0x1c));
  return;
}



/* 1000fbe3 Unwind@1000fbe3 */

void Unwind_1000fbe3(void)

{
  int unaff_EBP;
  
  FUN_10006e90(*(void **)(unaff_EBP + 8));
  return;
}



/* 1000fbec Unwind@1000fbec */

void Unwind_1000fbec(void)

{
  int unaff_EBP;
  
  AFX_MAINTAIN_STATE2::~AFX_MAINTAIN_STATE2((AFX_MAINTAIN_STATE2 *)(unaff_EBP + -0x30));
  return;
}



/* 1000fc10 Unwind@1000fc10 */

void Unwind_1000fc10(void)

{
  int unaff_EBP;
  
  FUN_100042a0(*(undefined4 **)(unaff_EBP + -0x10));
  return;
}



/* 1000fc40 Unwind@1000fc40 */

void Unwind_1000fc40(void)

{
  int unaff_EBP;
  
  FUN_100042a0(*(undefined4 **)(unaff_EBP + -0x10));
  return;
}



/* 1000fc70 Unwind@1000fc70 */

void Unwind_1000fc70(void)

{
  int unaff_EBP;
  
  FUN_100042a0(*(undefined4 **)(unaff_EBP + 8));
  return;
}



/* 1000fca0 Unwind@1000fca0 */

void Unwind_1000fca0(void)

{
  int unaff_EBP;
  
  FUN_100042a0(*(undefined4 **)(unaff_EBP + -0x10));
  return;
}



/* 1000fcd0 Unwind@1000fcd0 */

void Unwind_1000fcd0(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fcd3. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  CDialog::~CDialog(*(CDialog **)(unaff_EBP + 8));
  return;
}



/* 1000fcd9 Unwind@1000fcd9 */

void Unwind_1000fcd9(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fce2. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  CComboBox::~CComboBox((CComboBox *)(*(int *)(unaff_EBP + 8) + 0x94));
  return;
}



/* 1000fce8 Unwind@1000fce8 */

void Unwind_1000fce8(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fcf1. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x150));
  return;
}



/* 1000fcf7 Unwind@1000fcf7 */

void Unwind_1000fcf7(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd00. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x154));
  return;
}



/* 1000fd06 Unwind@1000fd06 */

void Unwind_1000fd06(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd0f. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x158));
  return;
}



/* 1000fd15 Unwind@1000fd15 */

void Unwind_1000fd15(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd1e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x15c));
  return;
}



/* 1000fd24 Unwind@1000fd24 */

void Unwind_1000fd24(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd2d. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x160));
  return;
}



/* 1000fd33 Unwind@1000fd33 */

void Unwind_1000fd33(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd3c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x1a0));
  return;
}



/* 1000fd42 Unwind@1000fd42 */

void Unwind_1000fd42(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd4b. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x1a4));
  return;
}



/* 1000fd51 Unwind@1000fd51 */

void Unwind_1000fd51(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd5a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x1a8));
  return;
}



/* 1000fd60 Unwind@1000fd60 */

void Unwind_1000fd60(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd69. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x1f8));
  return;
}



/* 1000fd6f Unwind@1000fd6f */

void Unwind_1000fd6f(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd78. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x214));
  return;
}



/* 1000fd7e Unwind@1000fd7e */

void Unwind_1000fd7e(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fd87. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + 8) + 0x230));
  return;
}



/* 1000fd8d Unwind@1000fd8d */

void Unwind_1000fd8d(void)

{
  int unaff_EBP;
  
  FUN_10004470((undefined4 *)(*(int *)(unaff_EBP + 8) + 0x24c));
  return;
}



/* 1000fd9b Unwind@1000fd9b */

void Unwind_1000fd9b(void)

{
  int unaff_EBP;
  
  FUN_100046f0((undefined4 *)(*(int *)(unaff_EBP + 8) + 0x260));
  return;
}



/* 1000fda9 Unwind@1000fda9 */

void Unwind_1000fda9(void)

{
  int unaff_EBP;
  
  FUN_100042a0(*(undefined4 **)(unaff_EBP + -0x10));
  return;
}



/* 1000fdd0 Unwind@1000fdd0 */

void Unwind_1000fdd0(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fdd6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  CDialog::~CDialog(*(CDialog **)(unaff_EBP + -0x140));
  return;
}



/* 1000fddc Unwind@1000fddc */

void Unwind_1000fddc(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fde8. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  CComboBox::~CComboBox((CComboBox *)(*(int *)(unaff_EBP + -0x140) + 0x94));
  return;
}



/* 1000fdee Unwind@1000fdee */

void Unwind_1000fdee(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fdfa. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x150));
  return;
}



/* 1000fe00 Unwind@1000fe00 */

void Unwind_1000fe00(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe0c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x154));
  return;
}



/* 1000fe12 Unwind@1000fe12 */

void Unwind_1000fe12(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe1e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x158));
  return;
}



/* 1000fe24 Unwind@1000fe24 */

void Unwind_1000fe24(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe30. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x15c));
  return;
}



/* 1000fe36 Unwind@1000fe36 */

void Unwind_1000fe36(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe42. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x160));
  return;
}



/* 1000fe48 Unwind@1000fe48 */

void Unwind_1000fe48(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe54. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x1a0));
  return;
}



/* 1000fe5a Unwind@1000fe5a */

void Unwind_1000fe5a(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe66. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x1a4));
  return;
}



/* 1000fe6c Unwind@1000fe6c */

void Unwind_1000fe6c(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe78. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x1a8));
  return;
}



/* 1000fe7e Unwind@1000fe7e */

void Unwind_1000fe7e(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe8a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x1f8));
  return;
}



/* 1000fe90 Unwind@1000fe90 */

void Unwind_1000fe90(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fe9c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x214));
  return;
}



/* 1000fea2 Unwind@1000fea2 */

void Unwind_1000fea2(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000feae. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x230));
  return;
}



/* 1000feb4 Unwind@1000feb4 */

void Unwind_1000feb4(void)

{
  int unaff_EBP;
  
  FUN_10004470((undefined4 *)(*(int *)(unaff_EBP + -0x140) + 0x24c));
  return;
}



/* 1000fec5 Unwind@1000fec5 */

void Unwind_1000fec5(void)

{
  int unaff_EBP;
  
  FUN_100046f0((undefined4 *)(*(int *)(unaff_EBP + -0x140) + 0x260));
  return;
}



/* 1000fed6 Unwind@1000fed6 */

void Unwind_1000fed6(void)

{
  int unaff_EBP;
  
  FUN_100045f0((undefined4 *)(*(int *)(unaff_EBP + -0x140) + 0x270));
  return;
}



/* 1000fee7 Unwind@1000fee7 */

void Unwind_1000fee7(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000fef3. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x2d8));
  return;
}



/* 1000fef9 Unwind@1000fef9 */

void Unwind_1000fef9(void)

{
  int unaff_EBP;
  
                    /* WARNING: Could not recover jumptable at 0x1000ff05. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)
             (*(int *)(unaff_EBP + -0x140) + 0x2dc));
  return;
}



/* 10010110 FUN_10010110 */

void FUN_10010110(void)

{
  FUN_10004780((CDialog *)&DAT_1006bd10);
  return;
}



/* 10010120 FUN_10010120 */

void FUN_10010120(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010125. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bce0);
  return;
}



/* 10010130 FUN_10010130 */

void FUN_10010130(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010135. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bcfc);
  return;
}



/* 10010140 FUN_10010140 */

void FUN_10010140(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010145. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bcec);
  return;
}



/* 10010150 FUN_10010150 */

void FUN_10010150(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010155. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bcf8);
  return;
}



/* 10010160 FUN_10010160 */

void FUN_10010160(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010165. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bcdc);
  return;
}



/* 10010170 FUN_10010170 */

void FUN_10010170(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010175. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bd04);
  return;
}



/* 10010180 FUN_10010180 */

void FUN_10010180(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010185. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bd00);
  return;
}



/* 10010190 FUN_10010190 */

void FUN_10010190(void)

{
                    /* WARNING: Could not recover jumptable at 0x10010195. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bcf4);
  return;
}



/* 100101a0 FUN_100101a0 */

void FUN_100101a0(void)

{
                    /* WARNING: Could not recover jumptable at 0x100101a5. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bcf0);
  return;
}



/* 100101b0 FUN_100101b0 */

void FUN_100101b0(void)

{
                    /* WARNING: Could not recover jumptable at 0x100101b5. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bce4);
  return;
}



/* 100101c0 FUN_100101c0 */

void FUN_100101c0(void)

{
                    /* WARNING: Could not recover jumptable at 0x100101c5. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bce8);
  return;
}



/* 100101d0 FUN_100101d0 */

void FUN_100101d0(void)

{
                    /* WARNING: Could not recover jumptable at 0x100101d5. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ATL::CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>::
  ~CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_>
            ((CStringT<char,StrTraitMFC_DLL<char,ATL::ChTraitsCRT<char>_>_> *)&DAT_1006bd08);
  return;
}



/* 100101db FUN_100101db */

void FUN_100101db(void)

{
  AFX_MODULE_STATE::~AFX_MODULE_STATE((AFX_MODULE_STATE *)&DAT_1005a660);
  return;
}


