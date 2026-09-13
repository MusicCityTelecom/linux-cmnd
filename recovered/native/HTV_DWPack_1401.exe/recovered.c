/* Reconstructed pseudocode; not original or buildable C. */

/* 00401000 FUN_00401000 */

int __cdecl FUN_00401000(int param_1,undefined4 *param_2)

{
  char cVar1;
  byte bVar2;
  byte *pbVar3;
  int iVar4;
  uint uVar5;
  void *extraout_ECX;
  void *this;
  uint uVar6;
  void *pvVar7;
  int *piVar8;
  char *pcVar9;
  undefined4 unaff_EDI;
  undefined4 *puVar10;
  char *pcVar11;
  bool bVar12;
  CHAR CVar13;
  char *local_24;
  char local_20 [32];
  
  if (param_1 == 2) {
    pcVar9 = s_version_0042f138;
    pbVar3 = (byte *)param_2[1];
    do {
      bVar2 = *pbVar3;
      bVar12 = bVar2 < (byte)*pcVar9;
      if (bVar2 != *pcVar9) {
LAB_00401044:
        iVar4 = (1 - (uint)bVar12) - (uint)(bVar12 != 0);
        goto LAB_00401049;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar3[1];
      bVar12 = bVar2 < (byte)pcVar9[1];
      if (bVar2 != pcVar9[1]) goto LAB_00401044;
      pbVar3 = pbVar3 + 2;
      pcVar9 = pcVar9 + 2;
    } while (bVar2 != 0);
    iVar4 = 0;
LAB_00401049:
    if (iVar4 == 0) {
      FUN_0040aa66((byte *)s_2K14_BUH_Pack_version____________0042f0f8);
      FUN_0040aa66((byte *)s_20131110___Initial_Version_0042f0dc);
      FUN_0040aa66((byte *)s__________________________________0042f0b4);
      return 0;
    }
  }
  else if (2 < param_1) {
    uVar5 = param_1 - 1U & 0x80000001;
    bVar12 = uVar5 == 0;
    if ((int)uVar5 < 0) {
      bVar12 = (uVar5 - 1 | 0xfffffffe) == 0xffffffff;
    }
    if ((bVar12) && (pvVar7 = (void *)((int)(param_1 - 1U) / 2), (int)pvVar7 < 0x3c)) {
      puVar10 = &DAT_00431780;
      for (iVar4 = 0x73a0; iVar4 != 0; iVar4 = iVar4 + -1) {
        *puVar10 = 0;
        puVar10 = puVar10 + 1;
      }
      this = DAT_00452e04;
      DAT_00431780 = pvVar7;
      if (DAT_00452e04 == (void *)0x1) {
        FUN_0040aa66((byte *)s_argc__d_t_setting_i_pair_num__d_0042f08c);
        this = extraout_ECX;
      }
      param_1 = 0;
      if (0 < (int)DAT_00431780) {
        local_24 = (char *)&DAT_00431878;
        piVar8 = &DAT_00431788;
        do {
          iVar4 = FUN_0040aa5b(this,(byte *)param_2[1]);
          *piVar8 = iVar4;
          if (0xf < iVar4) {
            FUN_0040aa66((byte *)s_argv_index_value_exceeds_0042f04c);
            return -3;
          }
          uVar5 = 0xffffffff;
          pcVar9 = (char *)param_2[2];
          do {
            pcVar11 = pcVar9;
            if (uVar5 == 0) break;
            uVar5 = uVar5 - 1;
            pcVar11 = pcVar9 + 1;
            cVar1 = *pcVar9;
            pcVar9 = pcVar11;
          } while (cVar1 != '\0');
          uVar5 = ~uVar5;
          pcVar9 = pcVar11 + -uVar5;
          pcVar11 = local_24;
          for (uVar6 = uVar5 >> 2; uVar6 != 0; uVar6 = uVar6 - 1) {
            *(undefined4 *)pcVar11 = *(undefined4 *)pcVar9;
            pcVar9 = pcVar9 + 4;
            pcVar11 = pcVar11 + 4;
          }
          for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
            *pcVar11 = *pcVar9;
            pcVar9 = pcVar9 + 1;
            pcVar11 = pcVar11 + 1;
          }
          if (*piVar8 == 0) {
            uVar5 = 0xffffffff;
            pcVar9 = local_24;
            do {
              pcVar11 = pcVar9;
              if (uVar5 == 0) break;
              uVar5 = uVar5 - 1;
              pcVar11 = pcVar9 + 1;
              cVar1 = *pcVar9;
              pcVar9 = pcVar11;
            } while (cVar1 != '\0');
            uVar5 = ~uVar5;
            DAT_00452e1c = 1;
            pcVar9 = pcVar11 + -uVar5;
            pcVar11 = local_20;
            for (uVar6 = uVar5 >> 2; uVar6 != 0; uVar6 = uVar6 - 1) {
              *(undefined4 *)pcVar11 = *(undefined4 *)pcVar9;
              pcVar9 = pcVar9 + 4;
              pcVar11 = pcVar11 + 4;
            }
            for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
              *pcVar11 = *pcVar9;
              pcVar9 = pcVar9 + 1;
              pcVar11 = pcVar11 + 1;
            }
          }
          param_1 = param_1 + 1;
          piVar8 = piVar8 + 1;
          local_24 = local_24 + 0x400;
          this = DAT_00431780;
          param_2 = param_2 + 2;
        } while (param_1 < (int)DAT_00431780);
      }
      iVar4 = FUN_00401280();
      CVar13 = (CHAR)unaff_EDI;
      if (iVar4 == 0) {
        if ((DAT_00452e1c != 0) && (iVar4 = 0, 0 < (int)DAT_00431780)) {
          do {
            pvVar7 = DAT_00452e04;
            uVar5 = 0xffffffff;
            pcVar9 = local_20;
            do {
              pcVar11 = pcVar9;
              if (uVar5 == 0) break;
              uVar5 = uVar5 - 1;
              pcVar11 = pcVar9 + 1;
              cVar1 = *pcVar9;
              pcVar9 = pcVar11;
            } while (cVar1 != '\0');
            uVar5 = ~uVar5;
            pcVar9 = pcVar11 + -uVar5;
            pcVar11 = (char *)&DAT_00440938;
            for (uVar6 = uVar5 >> 2; uVar6 != 0; uVar6 = uVar6 - 1) {
              *(undefined4 *)pcVar11 = *(undefined4 *)pcVar9;
              pcVar9 = pcVar9 + 4;
              pcVar11 = pcVar11 + 4;
            }
            for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
              *pcVar11 = *pcVar9;
              pcVar9 = pcVar9 + 1;
              pcVar11 = pcVar11 + 1;
            }
            if (pvVar7 == (void *)0x1) {
              FUN_0040aa66((byte *)s_b_is_model_name_from_argv_TRUE_0042f068);
            }
            CVar13 = (CHAR)unaff_EDI;
            iVar4 = iVar4 + 1;
          } while (iVar4 < (int)DAT_00431780);
        }
        iVar4 = FUN_00401c20();
        if (iVar4 == 0) {
          iVar4 = FUN_00402470();
          if (iVar4 == 0) {
            FUN_00402150(CVar13);
            if (DAT_00452e04 == (void *)0x0) {
              FUN_00402c10();
            }
          }
        }
      }
      return iVar4;
    }
  }
  FUN_0040aa66((byte *)s_argc____d_is_incorrect_0042f034);
  return -2;
}



/* 00401280 FUN_00401280 */

int FUN_00401280(void)

{
  char cVar1;
  DWORD DVar2;
  uint uVar3;
  int iVar4;
  uint uVar5;
  undefined4 *puVar6;
  char *pcVar7;
  char *pcVar8;
  undefined4 *puVar9;
  char *pcVar10;
  DWORD local_4;
  
  puVar6 = (undefined4 *)0x0;
  local_4 = 0;
  if (DAT_00452e04 == 1) {
    FUN_0040aa66((byte *)s_>>_s___0042f218);
  }
  DVar2 = GetCurrentDirectoryA(0x400,(LPSTR)&DAT_0044ddf8);
  if (DVar2 != 0) {
    uVar3 = 0xffffffff;
    pcVar10 = &DAT_0042f214;
    do {
      pcVar8 = pcVar10;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar8 = pcVar10 + 1;
      cVar1 = *pcVar10;
      pcVar10 = pcVar8;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    iVar4 = -1;
    pcVar10 = (char *)&DAT_0044ddf8;
    do {
      pcVar7 = pcVar10;
      if (iVar4 == 0) break;
      iVar4 = iVar4 + -1;
      pcVar7 = pcVar10 + 1;
      cVar1 = *pcVar10;
      pcVar10 = pcVar7;
    } while (cVar1 != '\0');
    pcVar10 = pcVar8 + -uVar3;
    pcVar8 = pcVar7 + -1;
    for (uVar5 = uVar3 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      *(undefined4 *)pcVar8 = *(undefined4 *)pcVar10;
      pcVar10 = pcVar10 + 4;
      pcVar8 = pcVar8 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar8 = *pcVar10;
      pcVar10 = pcVar10 + 1;
      pcVar8 = pcVar8 + 1;
    }
    uVar3 = 0xffffffff;
    pcVar10 = &DAT_0042f208;
    do {
      pcVar8 = pcVar10;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar8 = pcVar10 + 1;
      cVar1 = *pcVar10;
      pcVar10 = pcVar8;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    iVar4 = -1;
    pcVar10 = (char *)&DAT_0044ddf8;
    do {
      pcVar7 = pcVar10;
      if (iVar4 == 0) break;
      iVar4 = iVar4 + -1;
      pcVar7 = pcVar10 + 1;
      cVar1 = *pcVar10;
      pcVar10 = pcVar7;
    } while (cVar1 != '\0');
    pcVar10 = pcVar8 + -uVar3;
    pcVar8 = pcVar7 + -1;
    for (uVar5 = uVar3 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      *(undefined4 *)pcVar8 = *(undefined4 *)pcVar10;
      pcVar10 = pcVar10 + 4;
      pcVar8 = pcVar8 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar8 = *pcVar10;
      pcVar10 = pcVar10 + 1;
      pcVar8 = pcVar8 + 1;
    }
  }
  iVar4 = FUN_00401490(&DAT_0044e1f8,(LPCSTR)&DAT_0044ddf8,0xc0000000,4);
  if (iVar4 != 0) {
    FUN_0040aa66((byte *)s__s__err____d_0042f1f8);
    return iVar4;
  }
  iVar4 = -1;
  DVar2 = GetFileSize(DAT_0044e1f8,(LPDWORD)0x0);
  if (DVar2 == 0) {
    FUN_0040aa66((byte *)s__s__dw_file_size____0_0042f1e0);
  }
  else {
    uVar3 = DVar2 + 2;
    puVar6 = _malloc(uVar3);
    if (puVar6 == (undefined4 *)0x0) {
      FUN_0040aa66((byte *)s__s__ps_buff_is_null_0042f1c8);
    }
    else {
      puVar9 = puVar6;
      for (uVar5 = uVar3 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
        *puVar9 = 0;
        puVar9 = puVar9 + 1;
      }
      for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
        *(undefined1 *)puVar9 = 0;
        puVar9 = (undefined4 *)((int)puVar9 + 1);
      }
      iVar4 = FUN_004014c0(DAT_0044e1f8,puVar6,DVar2,&local_4);
      if (iVar4 < 0) {
        pcVar10 = s__s___xml_read__err____d_0042f1ac;
      }
      else {
        iVar4 = FUN_00403a10(puVar6,local_4,&DAT_00452e20);
        if (iVar4 == 0) {
          iVar4 = FUN_004014f0(&DAT_00440878,DAT_00452e20);
          if (iVar4 == 0) {
            iVar4 = FUN_00401850(0x440bf8,0x3c,DAT_00452e20);
            if (iVar4 == 0) goto LAB_0040145e;
            pcVar10 = s__s___xml_parse_sw_info__err____d_0042f140;
          }
          else {
            pcVar10 = s__s___xml_parse_sys_info__err_____0042f164;
          }
        }
        else {
          pcVar10 = s__s__c_xml_parse_buffer__err____d_0042f188;
        }
      }
      FUN_0040aa66((byte *)pcVar10);
    }
  }
LAB_0040145e:
  FUN_004014e0(DAT_0044e1f8);
  if (puVar6 != (undefined4 *)0x0) {
    FUN_0040aa97(puVar6);
  }
  return iVar4;
}



/* 00401490 FUN_00401490 */

int __cdecl FUN_00401490(undefined4 *param_1,LPCSTR param_2,DWORD param_3,DWORD param_4)

{
  HANDLE pvVar1;
  
  pvVar1 = CreateFileA(param_2,param_3,0,(LPSECURITY_ATTRIBUTES)0x0,param_4,0x80,(HANDLE)0x0);
  *param_1 = pvVar1;
  return (param_1 != (undefined4 *)0xffffffff) - 1;
}



/* 004014c0 FUN_004014c0 */

void __cdecl FUN_004014c0(HANDLE param_1,LPVOID param_2,DWORD param_3,LPDWORD param_4)

{
  ReadFile(param_1,param_2,param_3,param_4,(LPOVERLAPPED)0x0);
  return;
}



/* 004014e0 FUN_004014e0 */

void __cdecl FUN_004014e0(HANDLE param_1)

{
  CloseHandle(param_1);
  return;
}



/* 004014f0 FUN_004014f0 */

undefined4 __cdecl FUN_004014f0(char *param_1,int param_2)

{
  int iVar1;
  int iVar2;
  
  if (DAT_00452e04 == 1) {
    FUN_0040aa66((byte *)s_>>_s___0042f218);
  }
  if ((param_2 != 0) && (param_1 != (char *)0x0)) {
    iVar1 = FUN_004039c0(param_2,(byte *)s_SYS_INFO_0042f5e0);
    if (iVar1 != 0) {
      iVar2 = FUN_00401800(iVar1,(byte *)s_VERSION_0042f5a8,param_1,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f574);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_VENDOR_0042f56c,param_1 + 0x40,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f538);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_MANUFACTURE_0042f52c,param_1 + 0x80,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f4f4);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_MODEL_NAME_0042f4e8,param_1 + 0xc0,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f4b0);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,&DAT_0042f4a8,param_1 + 0x100,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f478);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_CLUSTER_NAME_0042f468,param_1 + 0x140,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f430);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_PLATFORM_NAME_0042f420,param_1 + 0x180,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f3e8);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_DBG_ON_0042f3e0,param_1 + 0x1c0,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f3b0);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_PRIV_ON_0042f3a8,param_1 + 0x200,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f378);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_ENCRYPT_METHOD_0042f368,param_1 + 0x240,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f334);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_OUTPUT_0042f32c,param_1 + 0x280,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f2f8);
        return 0xffffffff;
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_UP_AES_KEY_0042f2ec,param_1 + 0x2c0,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f2b8);
      }
      iVar2 = FUN_00401800(iVar1,(byte *)s_UP_AES_IV_0042f2ac,param_1 + 0x300,0x40);
      if (iVar2 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f278);
      }
      iVar1 = FUN_00401800(iVar1,(byte *)s_UP_RSA_KEY_0042f26c,param_1 + 0x340,0x40);
      if (iVar1 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SYS_I_0042f238);
      }
      return 0;
    }
    FUN_0040aa66((byte *)s__s__c_xml_get_element_XML_SYS_IN_0042f5b0);
  }
  return 0xffffffff;
}



/* 00401800 FUN_00401800 */

undefined4 __cdecl FUN_00401800(int param_1,byte *param_2,char *param_3,size_t param_4)

{
  int iVar1;
  char *_Source;
  
  if (((param_1 != 0) && (param_2 != (byte *)0x0)) && (param_3 != (char *)0x0)) {
    iVar1 = FUN_004039c0(param_1,param_2);
    if (iVar1 == 0) {
      _Source = &DAT_00452e24;
    }
    else {
      _Source = *(char **)(iVar1 + 0x14);
    }
    _strncpy(param_3,_Source,param_4);
    return 0;
  }
  return 0xffffffff;
}



/* 00401850 FUN_00401850 */

undefined4 __cdecl FUN_00401850(uint param_1,uint param_2,int param_3)

{
  uint uVar1;
  uint uVar2;
  uint *puVar3;
  int iVar4;
  int iVar5;
  char *pcVar6;
  
  uVar2 = param_1;
  if ((((param_1 == 0) || (param_3 == 0)) ||
      (puVar3 = (uint *)FUN_004039e0(param_3,&DAT_0042f95c), puVar3 == (uint *)0x0)) ||
     (uVar1 = *puVar3, param_2 < uVar1)) {
    return 0xffffffff;
  }
  param_1 = 0;
  DAT_00431784 = uVar1;
  if (uVar1 != 0) {
    pcVar6 = (char *)(uVar2 + 0x80);
    do {
      iVar5 = *(int *)(puVar3[2] + param_1 * 4);
      iVar4 = FUN_00401800(iVar5,&DAT_0042f958,pcVar6 + -0x80,0x40);
      if (DAT_00452e04 == 1) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f930);
      }
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f880);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,&DAT_0042f928,pcVar6 + -0x40,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f850);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_METHOD_0042f920,pcVar6,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f820);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_FILE_NAME_0042f914,pcVar6 + 0x40,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f7ec);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_ENABLE_0042f90c,pcVar6 + 0x80,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f7bc);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_VERSION_0042f5a8,pcVar6 + 0xc0,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f788);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,&DAT_0042f904,pcVar6 + 0x100,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f758);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_PATH2_0042f8fc,pcVar6 + 0x140,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f728);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,&DAT_0042f8f4,pcVar6 + 0x180,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f6f8);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_OFFSET_0042f8ec,pcVar6 + 0x1c0,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f6c8);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_CHECKSUM_0042f8e0,pcVar6 + 0x200,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f698);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,(byte *)s_CMPRS_0042f8d8,pcVar6 + 0x280,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f664);
        return 0xffffffff;
      }
      iVar4 = FUN_00401800(iVar5,&DAT_0042f8d0,pcVar6 + 0x2c0,0x40);
      if (iVar4 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f634);
        return 0xffffffff;
      }
      iVar5 = FUN_00401800(iVar5,(byte *)s_FLASHTYPE_0042f8c4,pcVar6 + 0x240,0x40);
      if (iVar5 != 0) {
        FUN_0040aa66((byte *)s__s___xml_get_elem_info_XML_SW_IN_0042f600);
        return 0xffffffff;
      }
      param_1 = param_1 + 1;
      pcVar6 = pcVar6 + 0x380;
    } while (param_1 < uVar1);
  }
  return 0;
}



/* 00401c20 FUN_00401c20 */

undefined4 FUN_00401c20(void)

{
  char cVar1;
  byte bVar2;
  int *piVar3;
  int iVar4;
  byte *pbVar5;
  byte *pbVar6;
  int iVar7;
  DWORD DVar8;
  uint uVar9;
  uint uVar10;
  uint *puVar11;
  char *pcVar12;
  byte *pbVar13;
  char *pcVar14;
  char *pcVar15;
  bool bVar16;
  int local_18;
  int *local_14;
  int local_10;
  int local_c;
  int *local_8;
  DWORD local_4;
  
  local_4 = 0;
  local_18 = 0;
  if (DAT_00452e04 == 1) {
    FUN_0040aa66((byte *)s_>>_s___0042f218);
  }
  piVar3 = (int *)FUN_00403970((uint *)&DAT_0042f9cc,(uint *)0x0);
  local_8 = piVar3;
  iVar4 = FUN_00403990((int)piVar3,(uint *)s_SYS_INFO_0042f5e0,(uint *)0x0);
  FUN_00403990(iVar4,(uint *)s_VERSION_0042f5a8,(uint *)&DAT_00440878);
  FUN_00403990(iVar4,(uint *)s_MANUFACTURE_0042f52c,(uint *)&DAT_004408f8);
  FUN_00403990(iVar4,(uint *)s_VENDOR_0042f56c,(uint *)&DAT_004408b8);
  FUN_00403990(iVar4,(uint *)s_MODEL_NAME_0042f4e8,&DAT_00440938);
  FUN_00403990(iVar4,(uint *)&DAT_0042f4a8,(uint *)&DAT_00440978);
  FUN_00403990(iVar4,(uint *)s_CLUSTER_NAME_0042f468,(uint *)&DAT_004409b8);
  local_c = FUN_00403990((int)piVar3,(uint *)s_SW_INFO_0042f9c4,(uint *)0x0);
  local_10 = 0;
  if (0 < DAT_00431780) {
    puVar11 = &DAT_00431878;
    local_14 = &DAT_00431788;
    do {
      iVar4 = FUN_004020e0(&local_18,*local_14,&local_18);
      if (iVar4 == 1) {
        if (DAT_00452e04 == 1) {
          FUN_0040aa66((byte *)s_i_type__d_i_sw_index__d_0042f9a8);
        }
        pcVar12 = s_S_main_0042f9a0;
        pbVar6 = &DAT_00440ef8 + local_18 * 0x380;
        pbVar5 = pbVar6;
        do {
          bVar2 = *pbVar5;
          bVar16 = bVar2 < (byte)*pcVar12;
          if (bVar2 != *pcVar12) {
LAB_00401d7a:
            iVar4 = (1 - (uint)bVar16) - (uint)(bVar16 != 0);
            goto LAB_00401d7f;
          }
          if (bVar2 == 0) break;
          bVar2 = pbVar5[1];
          bVar16 = bVar2 < (byte)pcVar12[1];
          if (bVar2 != pcVar12[1]) goto LAB_00401d7a;
          pbVar5 = pbVar5 + 2;
          pcVar12 = pcVar12 + 2;
        } while (bVar2 != 0);
        iVar4 = 0;
LAB_00401d7f:
        if (iVar4 != 0) {
          pcVar12 = s_S2_main_0042f998;
          pbVar5 = pbVar6;
          do {
            bVar2 = *pbVar5;
            bVar16 = bVar2 < (byte)*pcVar12;
            if (bVar2 != *pcVar12) {
LAB_00401db2:
              iVar4 = (1 - (uint)bVar16) - (uint)(bVar16 != 0);
              goto LAB_00401db7;
            }
            if (bVar2 == 0) break;
            bVar2 = pbVar5[1];
            bVar16 = bVar2 < (byte)pcVar12[1];
            if (bVar2 != pcVar12[1]) goto LAB_00401db2;
            pbVar5 = pbVar5 + 2;
            pcVar12 = pcVar12 + 2;
          } while (bVar2 != 0);
          iVar4 = 0;
LAB_00401db7:
          if (iVar4 != 0) {
            pbVar13 = &DAT_0042f990;
            pbVar5 = pbVar6;
            do {
              bVar2 = *pbVar5;
              bVar16 = bVar2 < *pbVar13;
              if (bVar2 != *pbVar13) {
LAB_00401dea:
                iVar4 = (1 - (uint)bVar16) - (uint)(bVar16 != 0);
                goto LAB_00401def;
              }
              if (bVar2 == 0) break;
              bVar2 = pbVar5[1];
              bVar16 = bVar2 < pbVar13[1];
              if (bVar2 != pbVar13[1]) goto LAB_00401dea;
              pbVar5 = pbVar5 + 2;
              pbVar13 = pbVar13 + 2;
            } while (bVar2 != 0);
            iVar4 = 0;
LAB_00401def:
            if (iVar4 != 0) {
              pbVar5 = &DAT_0042f988;
              do {
                bVar2 = *pbVar6;
                bVar16 = bVar2 < *pbVar5;
                if (bVar2 != *pbVar5) {
LAB_00401e22:
                  iVar4 = (1 - (uint)bVar16) - (uint)(bVar16 != 0);
                  goto LAB_00401e27;
                }
                if (bVar2 == 0) break;
                bVar2 = pbVar6[1];
                bVar16 = bVar2 < pbVar5[1];
                if (bVar2 != pbVar5[1]) goto LAB_00401e22;
                pbVar6 = pbVar6 + 2;
                pbVar5 = pbVar5 + 2;
              } while (bVar2 != 0);
              iVar4 = 0;
LAB_00401e27:
              if (iVar4 != 0) {
                iVar4 = FUN_00403990(local_c,(uint *)&DAT_0042f95c,(uint *)0x0);
                FUN_00403990(iVar4,(uint *)&DAT_0042f958,&DAT_00440bf8 + local_18 * 0xe0);
                FUN_00403990(iVar4,(uint *)&DAT_0042f928,&DAT_00440c38 + local_18 * 0xe0);
                FUN_00403990(iVar4,(uint *)s_METHOD_0042f920,&DAT_00440c78 + local_18 * 0xe0);
                FUN_00403990(iVar4,(uint *)s_FILE_NAME_0042f914,puVar11);
                FUN_00403990(iVar4,(uint *)s_ENABLE_0042f90c,&DAT_00440cf8 + local_18 * 0xe0);
                FUN_00403990(iVar4,(uint *)s_VERSION_0042f5a8,&DAT_00440d38 + local_18 * 0xe0);
                FUN_00403990(iVar4,(uint *)&DAT_0042f904,&DAT_00440d78 + local_18 * 0xe0);
                FUN_00403990(iVar4,(uint *)s_PATH2_0042f8fc,&DAT_00440db8 + local_18 * 0xe0);
                pbVar5 = &DAT_0042f980;
                pbVar6 = (byte *)(&DAT_00440bf8 + local_18 * 0xe0);
                do {
                  bVar2 = *pbVar6;
                  bVar16 = bVar2 < *pbVar5;
                  if (bVar2 != *pbVar5) {
LAB_00401f7f:
                    iVar7 = (1 - (uint)bVar16) - (uint)(bVar16 != 0);
                    goto LAB_00401f84;
                  }
                  if (bVar2 == 0) break;
                  bVar2 = pbVar6[1];
                  bVar16 = bVar2 < pbVar5[1];
                  if (bVar2 != pbVar5[1]) goto LAB_00401f7f;
                  pbVar6 = pbVar6 + 2;
                  pbVar5 = pbVar5 + 2;
                } while (bVar2 != 0);
                iVar7 = 0;
LAB_00401f84:
                if (iVar7 == 0) {
                  FUN_00403990(iVar4,(uint *)&DAT_0042f970,(uint *)&DAT_0042f978);
                }
                FUN_00403990(iVar4,(uint *)&DAT_0042f8f4,(uint *)0x0);
                FUN_00403990(iVar4,(uint *)s_OFFSET_0042f8ec,(uint *)0x0);
                FUN_00403990(iVar4,(uint *)s_CHECKSUM_0042f8e0,(uint *)0x0);
              }
            }
          }
        }
      }
      local_10 = local_10 + 1;
      local_14 = local_14 + 1;
      puVar11 = puVar11 + 0x100;
    } while (local_10 < DAT_00431780);
  }
  DVar8 = GetCurrentDirectoryA(0x400,(LPSTR)&DAT_0044e1fc);
  piVar3 = local_8;
  if (DVar8 != 0) {
    uVar9 = 0xffffffff;
    pcVar12 = &DAT_0042f214;
    do {
      pcVar15 = pcVar12;
      if (uVar9 == 0) break;
      uVar9 = uVar9 - 1;
      pcVar15 = pcVar12 + 1;
      cVar1 = *pcVar12;
      pcVar12 = pcVar15;
    } while (cVar1 != '\0');
    uVar9 = ~uVar9;
    iVar4 = -1;
    pcVar12 = (char *)&DAT_0044e1fc;
    do {
      pcVar14 = pcVar12;
      if (iVar4 == 0) break;
      iVar4 = iVar4 + -1;
      pcVar14 = pcVar12 + 1;
      cVar1 = *pcVar12;
      pcVar12 = pcVar14;
    } while (cVar1 != '\0');
    pcVar12 = pcVar15 + -uVar9;
    pcVar15 = pcVar14 + -1;
    for (uVar10 = uVar9 >> 2; uVar10 != 0; uVar10 = uVar10 - 1) {
      *(undefined4 *)pcVar15 = *(undefined4 *)pcVar12;
      pcVar12 = pcVar12 + 4;
      pcVar15 = pcVar15 + 4;
    }
    for (uVar9 = uVar9 & 3; uVar9 != 0; uVar9 = uVar9 - 1) {
      *pcVar15 = *pcVar12;
      pcVar12 = pcVar12 + 1;
      pcVar15 = pcVar15 + 1;
    }
    uVar9 = 0xffffffff;
    pcVar12 = s_download_xml_0042f960;
    do {
      pcVar15 = pcVar12;
      if (uVar9 == 0) break;
      uVar9 = uVar9 - 1;
      pcVar15 = pcVar12 + 1;
      cVar1 = *pcVar12;
      pcVar12 = pcVar15;
    } while (cVar1 != '\0');
    uVar9 = ~uVar9;
    iVar4 = -1;
    pcVar12 = (char *)&DAT_0044e1fc;
    do {
      pcVar14 = pcVar12;
      if (iVar4 == 0) break;
      iVar4 = iVar4 + -1;
      pcVar14 = pcVar12 + 1;
      cVar1 = *pcVar12;
      pcVar12 = pcVar14;
    } while (cVar1 != '\0');
    pcVar12 = pcVar15 + -uVar9;
    pcVar15 = pcVar14 + -1;
    for (uVar10 = uVar9 >> 2; uVar10 != 0; uVar10 = uVar10 - 1) {
      *(undefined4 *)pcVar15 = *(undefined4 *)pcVar12;
      pcVar12 = pcVar12 + 4;
      pcVar15 = pcVar15 + 4;
    }
    for (uVar9 = uVar9 & 3; uVar9 != 0; uVar9 = uVar9 - 1) {
      *pcVar15 = *pcVar12;
      pcVar12 = pcVar12 + 1;
      pcVar15 = pcVar15 + 1;
    }
  }
  pcVar12 = (char *)FUN_00403a00(local_8);
  if (piVar3 != (int *)0x0) {
    FUN_004039b0(piVar3);
  }
  FUN_00401490(&DAT_0044e5fc,(LPCSTR)&DAT_0044e1fc,0xc0000000,2);
  uVar9 = 0xffffffff;
  pcVar15 = pcVar12;
  do {
    if (uVar9 == 0) break;
    uVar9 = uVar9 - 1;
    cVar1 = *pcVar15;
    pcVar15 = pcVar15 + 1;
  } while (cVar1 != '\0');
  FUN_00402130(DAT_0044e5fc,pcVar12,~uVar9 - 1,&local_4);
  FUN_004014e0(DAT_0044e5fc);
  DAT_0044e5fc = (HANDLE)0x0;
  if (pcVar12 != (char *)0x0) {
    FUN_0040aa97(pcVar12);
  }
  return 0;
}



/* 004020e0 FUN_004020e0 */

undefined4 __thiscall FUN_004020e0(void *this,int param_1,int *param_2)

{
  int iVar1;
  void *extraout_ECX;
  int iVar2;
  byte *pbVar3;
  
  iVar2 = 0;
  if (0 < DAT_00431784) {
    pbVar3 = &DAT_00440f38;
    do {
      iVar1 = FUN_0040aa5b(this,pbVar3);
      if (param_1 == iVar1) {
        *param_2 = iVar2;
        return 1;
      }
      iVar2 = iVar2 + 1;
      pbVar3 = pbVar3 + 0x380;
      this = extraout_ECX;
    } while (iVar2 < DAT_00431784);
  }
  return 0;
}



/* 00402130 FUN_00402130 */

void __cdecl FUN_00402130(HANDLE param_1,LPCVOID param_2,DWORD param_3,LPDWORD param_4)

{
  WriteFile(param_1,param_2,param_3,param_4,(LPOVERLAPPED)0x0);
  return;
}



/* 00402150 FUN_00402150 */

undefined4 __cdecl FUN_00402150(CHAR param_1)

{
  char cVar1;
  FILE *pFVar2;
  FILE *pFVar3;
  int iVar4;
  uint uVar5;
  uint uVar6;
  uint uVar7;
  uint uVar8;
  char *pcVar9;
  undefined4 *puVar10;
  char *pcVar11;
  char cStack00000104;
  
  FUN_0040afb0();
  puVar10 = (undefined4 *)&stack0x00000205;
  for (iVar4 = 0x9ff; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar10 = 0;
    puVar10 = puVar10 + 1;
  }
  *(undefined2 *)puVar10 = 0;
  *(undefined1 *)((int)puVar10 + 2) = 0;
  cStack00000104 = '\0';
  puVar10 = (undefined4 *)&stack0x00000105;
  for (iVar4 = 0x3f; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar10 = 0;
    puVar10 = puVar10 + 1;
  }
  *(undefined2 *)puVar10 = 0;
  *(undefined1 *)((int)puVar10 + 2) = 0;
  param_1 = '\0';
  puVar10 = (undefined4 *)&stack0x00000005;
  for (iVar4 = 0x3f; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar10 = 0;
    puVar10 = puVar10 + 1;
  }
  *(undefined2 *)puVar10 = 0;
  *(undefined1 *)((int)puVar10 + 2) = 0;
  uVar5 = 0xffffffff;
  pcVar11 = &DAT_004409f8;
  do {
    if (uVar5 == 0) break;
    uVar5 = uVar5 - 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar11 + 1;
  } while (cVar1 != '\0');
  uVar6 = 0xffffffff;
  pcVar11 = &DAT_00440978;
  do {
    if (uVar6 == 0) break;
    uVar6 = uVar6 - 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar11 + 1;
  } while (cVar1 != '\0');
  uVar7 = 0xffffffff;
  pcVar11 = &DAT_004409b8;
  do {
    if (uVar7 == 0) break;
    uVar7 = uVar7 - 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar11 + 1;
  } while (cVar1 != '\0');
  uVar8 = 0xffffffff;
  pcVar11 = &DAT_00440878;
  do {
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar11 + 1;
  } while (cVar1 != '\0');
  iVar4 = ~uVar5 + ~uVar6 + ~uVar7 + ~uVar8;
  if (0x80 < iVar4) {
    return 0xfffffffe;
  }
  iVar4 = 0x80 - iVar4;
  uVar5 = 0xffffffff;
  pcVar11 = s_buh_pack_out_0042fa58;
  do {
    pcVar9 = pcVar11;
    if (uVar5 == 0) break;
    uVar5 = uVar5 - 1;
    pcVar9 = pcVar11 + 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar9;
  } while (cVar1 != '\0');
  uVar5 = ~uVar5;
  pcVar11 = pcVar9 + -uVar5;
  pcVar9 = &stack0x00000104;
  for (uVar6 = uVar5 >> 2; uVar6 != 0; uVar6 = uVar6 - 1) {
    *(undefined4 *)pcVar9 = *(undefined4 *)pcVar11;
    pcVar11 = pcVar11 + 4;
    pcVar9 = pcVar9 + 4;
  }
  for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
    *pcVar9 = *pcVar11;
    pcVar11 = pcVar11 + 1;
    pcVar9 = pcVar9 + 1;
  }
  pcVar11 = &DAT_00440af8;
  if (DAT_00440af8 == '\0') {
    pcVar11 = s_autorun09m_upg_0042fa48;
  }
  uVar5 = 0xffffffff;
  do {
    pcVar9 = pcVar11;
    if (uVar5 == 0) break;
    uVar5 = uVar5 - 1;
    pcVar9 = pcVar11 + 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar9;
  } while (cVar1 != '\0');
  uVar5 = ~uVar5;
  pcVar9 = pcVar9 + -uVar5;
  pcVar11 = (char *)register0x00000010;
  for (uVar6 = uVar5 >> 2; pcVar11 = pcVar11 + 4, uVar6 != 0; uVar6 = uVar6 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar9;
    pcVar9 = pcVar9 + 4;
  }
  for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
    *pcVar11 = *pcVar9;
    pcVar9 = pcVar9 + 1;
    pcVar11 = pcVar11 + 1;
  }
  pFVar2 = (FILE *)FUN_0040af91(&stack0x00000104,(char *)&DAT_0042fa44);
  if (pFVar2 == (FILE *)0x0) {
    return 0xfffffffd;
  }
  pFVar3 = (FILE *)FUN_0040af91(&param_1,(char *)&DAT_0042fa40);
  if (pFVar3 == (FILE *)0x0) {
    FUN_0040af1b(pFVar2);
    return 0xfffffffd;
  }
  uVar5 = 0xffffffff;
  pcVar11 = &DAT_004409b8;
  do {
    if (uVar5 == 0) break;
    uVar5 = uVar5 - 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar11 + 1;
  } while (cVar1 != '\0');
  uVar5 = FUN_0040ae11(&DAT_004409b8,1,~uVar5 - 1,(int *)pFVar3);
  if ((-1 < (int)uVar5) && (uVar5 = FUN_0040ae11(&DAT_0042fa3c,1,1,(int *)pFVar3), -1 < (int)uVar5))
  {
    uVar5 = 0xffffffff;
    pcVar11 = &DAT_00440978;
    do {
      if (uVar5 == 0) break;
      uVar5 = uVar5 - 1;
      cVar1 = *pcVar11;
      pcVar11 = pcVar11 + 1;
    } while (cVar1 != '\0');
    uVar5 = FUN_0040ae11(&DAT_00440978,1,~uVar5,(int *)pFVar3);
    if (-1 < (int)uVar5) {
      uVar5 = 0xffffffff;
      pcVar11 = &DAT_00440878;
      do {
        if (uVar5 == 0) break;
        uVar5 = uVar5 - 1;
        cVar1 = *pcVar11;
        pcVar11 = pcVar11 + 1;
      } while (cVar1 != '\0');
      uVar5 = FUN_0040ae11(&DAT_00440878,1,~uVar5,(int *)pFVar3);
      if (-1 < (int)uVar5) {
        uVar5 = 0xffffffff;
        pcVar11 = &DAT_004409f8;
        do {
          if (uVar5 == 0) break;
          uVar5 = uVar5 - 1;
          cVar1 = *pcVar11;
          pcVar11 = pcVar11 + 1;
        } while (cVar1 != '\0');
        uVar5 = FUN_0040ae11(&DAT_004409f8,1,~uVar5,(int *)pFVar3);
        if (-1 < (int)uVar5) {
          if (0 < iVar4) {
            do {
              uVar5 = FUN_0040ae11(&stack0x00000000,1,1,(int *)pFVar3);
              iVar4 = iVar4 + -1;
            } while (iVar4 != 0);
          }
          if (-1 < (int)uVar5) {
            while ((uVar5 = FUN_0040ad29(&stack0x00000204,1,0x2800,(int *)pFVar2), uVar5 == 0 ||
                   (uVar6 = FUN_0040ae11(&stack0x00000204,1,uVar5,(int *)pFVar3), -1 < (int)uVar6)))
            {
              if (uVar5 != 0x2800) {
                FUN_0040af1b(pFVar3);
                FUN_0040af1b(pFVar2);
                return 0;
              }
            }
          }
        }
      }
    }
  }
  FUN_0040ac3e((int *)&DAT_004302c0,(byte *)s_error__Cannot_output_include_fil_0042fa0c);
  FUN_0040af1b(pFVar2);
  FUN_0040af1b(pFVar3);
  return 0;
}



/* 00402470 FUN_00402470 */

undefined4 FUN_00402470(void)

{
  byte bVar1;
  char cVar2;
  char *pcVar3;
  FILE *pFVar4;
  FILE *pFVar5;
  byte *pbVar6;
  int iVar7;
  uint uVar8;
  uint uVar9;
  int iVar10;
  char *pcVar11;
  char *pcVar12;
  char *pcVar13;
  char *pcVar14;
  byte *pbVar15;
  undefined4 *puVar16;
  size_t sVar17;
  bool bVar18;
  char *pcStack00000004;
  char *in_stack_00006820;
  
  FUN_0040afb0();
  iVar10 = 0;
  puVar16 = (undefined4 *)&stack0x00002008;
  for (iVar7 = 0x1207; iVar7 != 0; iVar7 = iVar7 + -1) {
    *puVar16 = 0;
    puVar16 = puVar16 + 1;
  }
  puVar16 = (undefined4 *)&stack0x00000008;
  for (iVar7 = 0x800; iVar7 != 0; iVar7 = iVar7 + -1) {
    *puVar16 = 0;
    puVar16 = puVar16 + 1;
  }
  pcStack00000004 = (char *)0x0;
  if (DAT_00452e04 == 1) {
    FUN_0040aa66((byte *)s_>>_s___0042f218);
  }
  uVar8 = 0xffffffff;
  pcVar14 = s_pack_script_scr_0042fa74;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x00000008;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  uVar8 = 0xffffffff;
  pcVar14 = &DAT_004408b8;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x00000408;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  uVar8 = 0xffffffff;
  pcVar14 = (char *)&DAT_00440938;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x00000808;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  uVar8 = 0xffffffff;
  pcVar14 = &DAT_00440878;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x00000c08;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  if (0 < DAT_00431784) {
    pcVar11 = (char *)&DAT_00440c38;
    pcVar14 = in_stack_00006820;
    do {
      pcVar3 = _malloc(0x3014);
      if (pcVar3 == (char *)0x0) {
        FUN_0040aa66((byte *)s_Error_Allocate_memory_failed_0042f9ec);
        return 0;
      }
      pcVar13 = pcVar3;
      for (iVar7 = 0xc05; iVar7 != 0; iVar7 = iVar7 + -1) {
        pcVar13[0] = '\0';
        pcVar13[1] = '\0';
        pcVar13[2] = '\0';
        pcVar13[3] = '\0';
        pcVar13 = pcVar13 + 4;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + -0x40;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar12 = pcVar12 + -uVar8;
      pcVar13 = pcVar3;
      for (uVar9 = uVar8 >> 2; pcVar13 = pcVar13 + 4, uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar13 = *(undefined4 *)pcVar12;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar13 = *pcVar12;
        pcVar12 = pcVar12 + 1;
        pcVar13 = pcVar13 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3 + 0xe;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + 0x80;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3 + 0x100e;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + 0x100;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3 + 0x140e;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + 0x40;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3 + 0x180e;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + 0x140;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3 + 0x1c0e;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + 0x180;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3 + 0x200e;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + 0x280;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3 + 0x2c0e;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar13 = pcVar11 + 0xc0;
      do {
        pcVar12 = pcVar13;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar12 = pcVar13 + 1;
        cVar2 = *pcVar13;
        pcVar13 = pcVar12;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar13 = pcVar12 + -uVar8;
      pcVar12 = pcVar3;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
      pcVar3[0x3010] = '\0';
      pcVar3[0x3011] = '\0';
      pcVar3[0x3012] = '\0';
      pcVar3[0x3013] = '\0';
      in_stack_00006820 = pcVar3;
      if (pcVar14 != (char *)0x0) {
        *(char **)(pcStack00000004 + 0x3010) = pcVar3;
        in_stack_00006820 = pcVar14;
      }
      iVar10 = iVar10 + 1;
      pcVar11 = pcVar11 + 0x380;
      pcStack00000004 = pcVar3;
      pcVar14 = in_stack_00006820;
    } while (iVar10 < DAT_00431784);
  }
  uVar8 = 0xffffffff;
  pcVar14 = &DAT_00440b38;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x0000580c;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  uVar8 = 0xffffffff;
  pcVar14 = (char *)&DAT_00440b78;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x00005c0c;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  uVar8 = 0xffffffff;
  pcVar14 = (char *)&DAT_00440bb8;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x0000600c;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  uVar8 = 0xffffffff;
  pcVar14 = &DAT_00440af8;
  do {
    pcVar11 = pcVar14;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar11 = pcVar14 + 1;
    cVar2 = *pcVar14;
    pcVar14 = pcVar11;
  } while (cVar2 != '\0');
  uVar8 = ~uVar8;
  pcVar14 = pcVar11 + -uVar8;
  pcVar11 = &stack0x0000540c;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
    pcVar14 = pcVar14 + 4;
    pcVar11 = pcVar11 + 4;
  }
  pcVar3 = &DAT_00440a78;
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar11 = *pcVar14;
    pcVar14 = pcVar14 + 1;
    pcVar11 = pcVar11 + 1;
  }
  pcVar14 = &DAT_0042fa70;
  do {
    if (((*pcVar3 != *pcVar14) || (*pcVar3 == '\0')) ||
       (pcVar11 = pcVar3 + 1, *pcVar11 != pcVar14[1])) break;
    pcVar3 = pcVar3 + 2;
    pcVar14 = pcVar14 + 2;
  } while (*pcVar11 != '\0');
  pcVar11 = &DAT_0042fa70;
  pcVar14 = &DAT_00440a38;
  do {
    if (((*pcVar14 != *pcVar11) || (*pcVar14 == '\0')) ||
       (pcVar3 = pcVar14 + 1, *pcVar3 != pcVar11[1])) break;
    pcVar14 = pcVar14 + 2;
    pcVar11 = pcVar11 + 2;
  } while (*pcVar3 != '\0');
  iVar7 = FUN_00402df0((undefined4 *)&stack0x00002008);
  if ((-1 < iVar7) && (iVar7 = FUN_00402e20((int)&stack0x00002008), -1 < iVar7)) {
    pFVar4 = (FILE *)FUN_0040af91(&stack0x0000580c,&DAT_0042fa6c);
    if (pFVar4 != (FILE *)0x0) {
      pFVar5 = (FILE *)FUN_0040af91(&stack0x0000580c,&DAT_0042fa6c);
      if (pFVar5 == (FILE *)0x0) {
        return 0xffffffff;
      }
      iVar7 = FUN_0040b1a5((int *)pFVar5,0,2);
      if (iVar7 != 0) {
        return 0xffffffff;
      }
      FUN_0040b04d((char *)pFVar5);
      FUN_0040aff9((int *)pFVar5);
      FUN_0040af1b(pFVar5);
      pcVar11 = _malloc(0x400);
      pcVar14 = pcVar11;
      for (iVar7 = 0x100; iVar7 != 0; iVar7 = iVar7 + -1) {
        pcVar14[0] = '\0';
        pcVar14[1] = '\0';
        pcVar14[2] = '\0';
        pcVar14[3] = '\0';
        pcVar14 = pcVar14 + 4;
      }
      _strncpy(&stack0x0000580c,pcVar11,0x400);
      sVar17 = 0;
      uVar8 = FUN_0040afdf((undefined4 *)pFVar4);
      cVar2 = (char)uVar8;
      while (cVar2 != -1) {
        pcVar11[sVar17] = (char)uVar8;
        sVar17 = sVar17 + 1;
        uVar8 = FUN_0040afdf((undefined4 *)pFVar4);
        cVar2 = (char)uVar8;
      }
      FUN_0040af1b(pFVar4);
      _strncpy(&stack0x0000580c,pcVar11,sVar17);
      FUN_0040aa97(pcVar11);
    }
    pFVar4 = (FILE *)FUN_0040af91(&stack0x00005c0c,&DAT_0042fa6c);
    if (pFVar4 != (FILE *)0x0) {
      pFVar5 = (FILE *)FUN_0040af91(&stack0x00005c0c,&DAT_0042fa6c);
      if (pFVar5 == (FILE *)0x0) {
        return 0xffffffff;
      }
      iVar7 = FUN_0040b1a5((int *)pFVar5,0,2);
      if (iVar7 != 0) {
        return 0xffffffff;
      }
      FUN_0040b04d((char *)pFVar5);
      FUN_0040aff9((int *)pFVar5);
      FUN_0040af1b(pFVar5);
      pcVar11 = _malloc(0x400);
      pcVar14 = pcVar11;
      for (iVar7 = 0x100; iVar7 != 0; iVar7 = iVar7 + -1) {
        pcVar14[0] = '\0';
        pcVar14[1] = '\0';
        pcVar14[2] = '\0';
        pcVar14[3] = '\0';
        pcVar14 = pcVar14 + 4;
      }
      _strncpy(&stack0x00005c0c,pcVar11,0x400);
      sVar17 = 0;
      uVar8 = FUN_0040afdf((undefined4 *)pFVar4);
      cVar2 = (char)uVar8;
      while (cVar2 != -1) {
        pcVar11[sVar17] = (char)uVar8;
        sVar17 = sVar17 + 1;
        uVar8 = FUN_0040afdf((undefined4 *)pFVar4);
        cVar2 = (char)uVar8;
      }
      FUN_0040af1b(pFVar4);
      _strncpy(&stack0x00005c0c,pcVar11,sVar17);
      FUN_0040aa97(pcVar11);
    }
    pbVar15 = &DAT_0042fa68;
    pbVar6 = &DAT_00440ab8;
    do {
      bVar1 = *pbVar6;
      bVar18 = bVar1 < *pbVar15;
      if (bVar1 != *pbVar15) {
LAB_00402adb:
        iVar7 = (1 - (uint)bVar18) - (uint)(bVar18 != 0);
        goto LAB_00402ae0;
      }
      if (bVar1 == 0) break;
      bVar1 = pbVar6[1];
      bVar18 = bVar1 < pbVar15[1];
      if (bVar1 != pbVar15[1]) goto LAB_00402adb;
      pbVar6 = pbVar6 + 2;
      pbVar15 = pbVar15 + 2;
    } while (bVar1 != 0);
    iVar7 = 0;
LAB_00402ae0:
    if (iVar7 == 0) {
      uVar8 = 0xffffffff;
      pcVar14 = &stack0x0000580c;
      do {
        pcVar11 = pcVar14;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar11 = pcVar14 + 1;
        cVar2 = *pcVar14;
        pcVar14 = pcVar11;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar14 = pcVar11 + -uVar8;
      pcVar11 = &stack0x00001008;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
        pcVar14 = pcVar14 + 4;
        pcVar11 = pcVar11 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar11 = *pcVar14;
        pcVar14 = pcVar14 + 1;
        pcVar11 = pcVar11 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar14 = &stack0x00005c0c;
      do {
        pcVar11 = pcVar14;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar11 = pcVar14 + 1;
        cVar2 = *pcVar14;
        pcVar14 = pcVar11;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar14 = pcVar11 + -uVar8;
      pcVar11 = &stack0x00001408;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
        pcVar14 = pcVar14 + 4;
        pcVar11 = pcVar11 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar11 = *pcVar14;
        pcVar14 = pcVar14 + 1;
        pcVar11 = pcVar11 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar14 = &stack0x0000600c;
      do {
        pcVar11 = pcVar14;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar11 = pcVar14 + 1;
        cVar2 = *pcVar14;
        pcVar14 = pcVar11;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar14 = pcVar11 + -uVar8;
      pcVar11 = &stack0x00001808;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
        pcVar14 = pcVar14 + 4;
        pcVar11 = pcVar11 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar11 = *pcVar14;
        pcVar14 = pcVar14 + 1;
        pcVar11 = pcVar11 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar14 = &stack0x0000540c;
      do {
        pcVar11 = pcVar14;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar11 = pcVar14 + 1;
        cVar2 = *pcVar14;
        pcVar14 = pcVar11;
      } while (cVar2 != '\0');
      uVar8 = ~uVar8;
      pcVar14 = pcVar11 + -uVar8;
      pcVar11 = &stack0x00001c08;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar11 = *(undefined4 *)pcVar14;
        pcVar14 = pcVar14 + 4;
        pcVar11 = pcVar11 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar11 = *pcVar14;
        pcVar14 = pcVar14 + 1;
        pcVar11 = pcVar11 + 1;
      }
      iVar7 = FUN_00410a40(&stack0x00000008,(int)in_stack_00006820);
      if (-1 < iVar7) {
        return 0;
      }
    }
    else {
      iVar7 = FUN_00412f70(&stack0x00000008,(int)in_stack_00006820);
      if (-1 < iVar7) {
        return 0;
      }
    }
  }
  while (in_stack_00006820 != (char *)0x0) {
    pcVar14 = *(char **)(in_stack_00006820 + 0x3010);
    FUN_0040aa97(in_stack_00006820);
    in_stack_00006820 = pcVar14;
  }
  return 0xffffffff;
}



/* 00402c10 FUN_00402c10 */

undefined4 FUN_00402c10(void)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  byte *pbVar4;
  undefined4 *puVar5;
  bool bVar6;
  undefined1 local_80;
  undefined4 local_7f;
  
  local_80 = 0;
  puVar5 = &local_7f;
  for (iVar3 = 0x1f; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar5 = 0;
    puVar5 = puVar5 + 1;
  }
  *(undefined2 *)puVar5 = 0;
  *(undefined1 *)((int)puVar5 + 2) = 0;
  if (DAT_00452e04 == 1) {
    FUN_0040aa66((byte *)s_>>_s___0042f218);
  }
  pbVar4 = &DAT_0042fa70;
  pbVar2 = &DAT_00440a38;
  do {
    bVar1 = *pbVar2;
    bVar6 = bVar1 < *pbVar4;
    if (bVar1 != *pbVar4) {
LAB_00402c7d:
      iVar3 = (1 - (uint)bVar6) - (uint)(bVar6 != 0);
      goto LAB_00402c82;
    }
    if (bVar1 == 0) break;
    bVar1 = pbVar2[1];
    bVar6 = bVar1 < pbVar4[1];
    if (bVar1 != pbVar4[1]) goto LAB_00402c7d;
    pbVar2 = pbVar2 + 2;
    pbVar4 = pbVar4 + 2;
  } while (bVar1 != 0);
  iVar3 = 0;
LAB_00402c82:
  if (iVar3 != 0) {
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__del__s__0042fb20);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__del__s__0042fb20);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__del__s__0042fb20);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s_tmp__del__s_tmp__0042faec);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__rmdir__S__Q__s__0042fac0);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__rmdir__S__Q__s__0042fac0);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__rmdir__S__Q__s__0042fac0);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__rmdir__S__Q__s__0042fac0);
    FUN_0040b231((int)&local_80);
    FUN_0040b2c7(&local_80,(byte *)s_IF_EXIST__s__del__s__0042fb20);
    FUN_0040b231((int)&local_80);
  }
  return 0;
}



/* 00402df0 FUN_00402df0 */

undefined4 __cdecl FUN_00402df0(undefined4 *param_1)

{
  int iVar1;
  undefined4 *puVar2;
  
  if (param_1 == (undefined4 *)0x0) {
    return 0xfffffffe;
  }
  puVar2 = &DAT_0044e600;
  for (iVar1 = 0x1207; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0;
    puVar2 = puVar2 + 1;
  }
  puVar2 = &DAT_0044e600;
  for (iVar1 = 0x1207; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = *param_1;
    param_1 = param_1 + 1;
    puVar2 = puVar2 + 1;
  }
  return 0;
}



/* 00402e20 FUN_00402e20 */

undefined4 __cdecl FUN_00402e20(int param_1)

{
  char cVar1;
  byte bVar2;
  char *pcVar3;
  int iVar4;
  char *pcVar5;
  int iVar6;
  uint uVar7;
  uint uVar8;
  undefined4 *puVar9;
  char *pcVar10;
  byte *pbVar11;
  char *pcVar12;
  char *pcVar13;
  undefined4 *puVar14;
  byte *pbVar15;
  byte *pbVar16;
  bool bVar17;
  
  puVar9 = *(undefined4 **)(param_1 + 0x4818);
  if (puVar9 == (undefined4 *)0x0) {
    return 0xfffffffe;
  }
  pcVar3 = _malloc(0x3014);
  pcVar10 = pcVar3;
  do {
    pbVar15 = &DAT_0042fb50;
    pbVar11 = (byte *)((int)puVar9 + 0x180e);
    do {
      bVar2 = *pbVar11;
      bVar17 = bVar2 < *pbVar15;
      if (bVar2 != *pbVar15) {
LAB_00402e81:
        iVar4 = (1 - (uint)bVar17) - (uint)(bVar17 != 0);
        goto LAB_00402e86;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar11[1];
      bVar17 = bVar2 < pbVar15[1];
      if (bVar2 != pbVar15[1]) goto LAB_00402e81;
      pbVar11 = pbVar11 + 2;
      pbVar15 = pbVar15 + 2;
    } while (bVar2 != 0);
    iVar4 = 0;
LAB_00402e86:
    pbVar11 = (byte *)((int)puVar9 + 0x240e);
    puVar14 = puVar9;
    pcVar5 = pcVar10;
    for (iVar6 = 0xc04; iVar6 != 0; iVar6 = iVar6 + -1) {
      *(undefined4 *)pcVar5 = *puVar14;
      puVar14 = puVar14 + 1;
      pcVar5 = pcVar5 + 4;
    }
    if (iVar4 == 0) {
      uVar7 = 0xffffffff;
      pbVar15 = pbVar11;
      do {
        pbVar16 = pbVar15;
        if (uVar7 == 0) break;
        uVar7 = uVar7 - 1;
        pbVar16 = pbVar15 + 1;
        bVar2 = *pbVar15;
        pbVar15 = pbVar16;
      } while (bVar2 != 0);
      uVar7 = ~uVar7;
      pbVar15 = pbVar16 + -uVar7;
      pbVar16 = (byte *)(pcVar10 + 0x1c0e);
      for (uVar8 = uVar7 >> 2; uVar8 != 0; uVar8 = uVar8 - 1) {
        *(undefined4 *)pbVar16 = *(undefined4 *)pbVar15;
        pbVar15 = pbVar15 + 4;
        pbVar16 = pbVar16 + 4;
      }
      for (uVar7 = uVar7 & 3; uVar7 != 0; uVar7 = uVar7 - 1) {
        *pbVar16 = *pbVar15;
        pbVar15 = pbVar15 + 1;
        pbVar16 = pbVar16 + 1;
      }
      uVar7 = 0xffffffff;
      pcVar5 = (char *)((int)puVar9 + 0x80e);
      do {
        pcVar12 = pcVar5;
        if (uVar7 == 0) break;
        uVar7 = uVar7 - 1;
        pcVar12 = pcVar5 + 1;
        cVar1 = *pcVar5;
        pcVar5 = pcVar12;
      } while (cVar1 != '\0');
      uVar7 = ~uVar7;
      pcVar5 = pcVar12 + -uVar7;
      pcVar12 = pcVar10 + 0x40e;
      for (uVar8 = uVar7 >> 2; uVar8 != 0; uVar8 = uVar8 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar5;
        pcVar5 = pcVar5 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar7 = uVar7 & 3; uVar7 != 0; uVar7 = uVar7 - 1) {
        *pcVar12 = *pcVar5;
        pcVar5 = pcVar5 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar7 = 0xffffffff;
      pcVar5 = (char *)((int)puVar9 + 9);
      do {
        pcVar12 = pcVar5;
        if (uVar7 == 0) break;
        uVar7 = uVar7 - 1;
        pcVar12 = pcVar5 + 1;
        cVar1 = *pcVar5;
        pcVar5 = pcVar12;
      } while (cVar1 != '\0');
      uVar7 = ~uVar7;
      pcVar12 = pcVar12 + -uVar7;
      pcVar5 = pcVar10;
      for (uVar8 = uVar7 >> 2; pcVar5 = pcVar5 + 4, uVar8 != 0; uVar8 = uVar8 - 1) {
        *(undefined4 *)pcVar5 = *(undefined4 *)pcVar12;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar7 = uVar7 & 3; uVar7 != 0; uVar7 = uVar7 - 1) {
        *pcVar5 = *pcVar12;
        pcVar12 = pcVar12 + 1;
        pcVar5 = pcVar5 + 1;
      }
      uVar7 = 0xffffffff;
      do {
        pbVar15 = pbVar11;
        if (uVar7 == 0) break;
        uVar7 = uVar7 - 1;
        pbVar15 = pbVar11 + 1;
        bVar2 = *pbVar11;
        pbVar11 = pbVar15;
      } while (bVar2 != 0);
      uVar7 = ~uVar7;
      pbVar11 = pbVar15 + -uVar7;
      pbVar15 = (byte *)(pcVar10 + 0x1c0e);
      for (uVar8 = uVar7 >> 2; uVar8 != 0; uVar8 = uVar8 - 1) {
        *(undefined4 *)pbVar15 = *(undefined4 *)pbVar11;
        pbVar11 = pbVar11 + 4;
        pbVar15 = pbVar15 + 4;
      }
      for (uVar7 = uVar7 & 3; uVar7 != 0; uVar7 = uVar7 - 1) {
        *pbVar15 = *pbVar11;
        pbVar11 = pbVar11 + 1;
        pbVar15 = pbVar15 + 1;
      }
      uVar7 = 0xffffffff;
      pcVar5 = (char *)((int)puVar9 + 0x40e);
      do {
        pcVar12 = pcVar5;
        if (uVar7 == 0) break;
        uVar7 = uVar7 - 1;
        pcVar12 = pcVar5 + 1;
        cVar1 = *pcVar5;
        pcVar5 = pcVar12;
      } while (cVar1 != '\0');
      uVar7 = ~uVar7;
      pcVar5 = pcVar12 + -uVar7;
      pcVar12 = pcVar10 + 0x40e;
      for (uVar8 = uVar7 >> 2; uVar8 != 0; uVar8 = uVar8 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar5;
        pcVar5 = pcVar5 + 4;
        pcVar12 = pcVar12 + 4;
      }
      for (uVar7 = uVar7 & 3; uVar7 != 0; uVar7 = uVar7 - 1) {
        *pcVar12 = *pcVar5;
        pcVar5 = pcVar5 + 1;
        pcVar12 = pcVar12 + 1;
      }
      uVar7 = 0xffffffff;
      pcVar5 = (char *)(puVar9 + 1);
      do {
        pcVar12 = pcVar5;
        if (uVar7 == 0) break;
        uVar7 = uVar7 - 1;
        pcVar12 = pcVar5 + 1;
        cVar1 = *pcVar5;
        pcVar5 = pcVar12;
      } while (cVar1 != '\0');
      uVar7 = ~uVar7;
      pcVar13 = pcVar12 + -uVar7;
      pcVar12 = pcVar10;
      for (uVar8 = uVar7 >> 2; pcVar12 = pcVar12 + 4, uVar8 != 0; uVar8 = uVar8 - 1) {
        *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
        pcVar13 = pcVar13 + 4;
      }
LAB_00403029:
      for (uVar7 = uVar7 & 3; uVar7 != 0; uVar7 = uVar7 - 1) {
        *pcVar12 = *pcVar13;
        pcVar13 = pcVar13 + 1;
        pcVar12 = pcVar12 + 1;
      }
    }
    else {
      uVar7 = 0xffffffff;
      pbVar15 = pbVar11;
      do {
        pbVar16 = pbVar15;
        if (uVar7 == 0) break;
        uVar7 = uVar7 - 1;
        pbVar16 = pbVar15 + 1;
        bVar2 = *pbVar15;
        pbVar15 = pbVar16;
      } while (bVar2 != 0);
      uVar7 = ~uVar7;
      pbVar15 = pbVar16 + -uVar7;
      pbVar16 = (byte *)(pcVar10 + 0x1c0e);
      for (uVar8 = uVar7 >> 2; uVar8 != 0; uVar8 = uVar8 - 1) {
        *(undefined4 *)pbVar16 = *(undefined4 *)pbVar15;
        pbVar15 = pbVar15 + 4;
        pbVar16 = pbVar16 + 4;
      }
      for (uVar7 = uVar7 & 3; uVar7 != 0; uVar7 = uVar7 - 1) {
        *pbVar16 = *pbVar15;
        pbVar15 = pbVar15 + 1;
        pbVar16 = pbVar16 + 1;
      }
      if (*(char *)((int)puVar9 + 0x200e) != '\0') {
        pbVar15 = (byte *)((int)puVar9 + 0x280e);
        do {
          bVar2 = *pbVar11;
          bVar17 = bVar2 < *pbVar15;
          if (bVar2 != *pbVar15) {
LAB_00402fdc:
            iVar4 = (1 - (uint)bVar17) - (uint)(bVar17 != 0);
            goto LAB_00402fe1;
          }
          if (bVar2 == 0) break;
          bVar2 = pbVar11[1];
          bVar17 = bVar2 < pbVar15[1];
          if (bVar2 != pbVar15[1]) goto LAB_00402fdc;
          pbVar11 = pbVar11 + 2;
          pbVar15 = pbVar15 + 2;
        } while (bVar2 != 0);
        iVar4 = 0;
LAB_00402fe1:
        if (iVar4 != 0) {
          pcVar5 = _malloc(0x3014);
          *(char **)(pcVar10 + 0x3010) = pcVar5;
          puVar14 = puVar9;
          pcVar10 = pcVar5;
          for (iVar4 = 0xc04; iVar4 != 0; iVar4 = iVar4 + -1) {
            *(undefined4 *)pcVar10 = *puVar14;
            puVar14 = puVar14 + 1;
            pcVar10 = pcVar10 + 4;
          }
          uVar7 = 0xffffffff;
          pcVar10 = (char *)((int)puVar9 + 0x280e);
          do {
            pcVar12 = pcVar10;
            if (uVar7 == 0) break;
            uVar7 = uVar7 - 1;
            pcVar12 = pcVar10 + 1;
            cVar1 = *pcVar10;
            pcVar10 = pcVar12;
          } while (cVar1 != '\0');
          uVar7 = ~uVar7;
          pcVar13 = pcVar12 + -uVar7;
          pcVar12 = pcVar5 + 0x1c0e;
          for (uVar8 = uVar7 >> 2; pcVar10 = pcVar5, uVar8 != 0; uVar8 = uVar8 - 1) {
            *(undefined4 *)pcVar12 = *(undefined4 *)pcVar13;
            pcVar13 = pcVar13 + 4;
            pcVar12 = pcVar12 + 4;
          }
          goto LAB_00403029;
        }
      }
    }
    pcVar5 = _malloc(0x3014);
    *(char **)(pcVar10 + 0x3010) = pcVar5;
    pcVar10 = pcVar5;
    for (iVar4 = 0xc05; iVar4 != 0; iVar4 = iVar4 + -1) {
      pcVar10[0] = '\0';
      pcVar10[1] = '\0';
      pcVar10[2] = '\0';
      pcVar10[3] = '\0';
      pcVar10 = pcVar10 + 4;
    }
    puVar9 = (undefined4 *)puVar9[0xc04];
    pcVar10 = pcVar5;
    if (puVar9 == (undefined4 *)0x0) {
      FUN_00403820((int)pcVar3);
      FUN_00403080();
      return 1;
    }
  } while( true );
}



/* 00403080 FUN_00403080 */

undefined4 FUN_00403080(void)

{
  byte bVar1;
  FILE *pFVar2;
  int *this;
  byte *pbVar3;
  int iVar4;
  uint uVar5;
  int *piVar6;
  int iVar8;
  int iVar9;
  char *pcVar10;
  byte *pbVar11;
  byte *pbVar12;
  undefined4 *puVar13;
  bool bVar14;
  bool bVar15;
  int *local_27c;
  int local_278;
  int *local_274;
  undefined4 local_26c;
  undefined4 local_260 [32];
  int local_1e0 [60];
  int local_f0 [60];
  undefined3 uVar7;
  
  local_278 = 0;
  if (DAT_00452e04 == 1) {
    FUN_0040aa66((byte *)s_>>_s___0042f218);
  }
  pFVar2 = (FILE *)FUN_0040af91(s_pack_script_scr_0042fa74,&DAT_0042fc8c);
  if (pFVar2 == (FILE *)0x0) {
    return 0xffffffff;
  }
  local_26c = 0xffffffff;
  this = (int *)FUN_0040ac3e((int *)pFVar2,(byte *)s_control_id_model_0042fc78);
  if (0 < (int)this) {
    if (DAT_00452e04 == 1) {
      FUN_0040aa66((byte *)s_t_setting_i_pair_num__d_0042fc5c);
    }
    iVar8 = 0;
    piVar6 = local_f0;
    for (uVar5 = DAT_00431780 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      *piVar6 = 0x3c3c3c3c;
      piVar6 = piVar6 + 1;
    }
    for (uVar5 = DAT_00431780 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
      *(undefined1 *)piVar6 = 0x3c;
      piVar6 = (int *)((int)piVar6 + 1);
    }
    piVar6 = local_1e0;
    for (uVar5 = DAT_00431780 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      *piVar6 = 0x3c3c3c3c;
      piVar6 = piVar6 + 1;
    }
    for (uVar5 = DAT_00431780 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
      *(undefined1 *)piVar6 = 0x3c;
      piVar6 = (int *)((int)piVar6 + 1);
    }
    if (0 < (int)DAT_00431780) {
      piVar6 = local_f0;
      local_27c = &DAT_00431788;
      local_274 = piVar6;
      do {
        iVar9 = 0;
        bVar15 = false;
        if (0 < DAT_00431784) {
          pbVar12 = &DAT_00440ef8;
          do {
            pcVar10 = s_S_main_0042f9a0;
            pbVar3 = pbVar12;
            do {
              bVar1 = *pbVar3;
              uVar7 = (undefined3)((uint)piVar6 >> 8);
              piVar6 = (int *)CONCAT31(uVar7,bVar1);
              bVar14 = bVar1 < (byte)*pcVar10;
              if (bVar1 != *pcVar10) {
LAB_004031af:
                iVar4 = (1 - (uint)bVar14) - (uint)(bVar14 != 0);
                goto LAB_004031b4;
              }
              if (bVar1 == 0) break;
              bVar1 = pbVar3[1];
              piVar6 = (int *)CONCAT31(uVar7,bVar1);
              bVar14 = bVar1 < (byte)pcVar10[1];
              if (bVar1 != pcVar10[1]) goto LAB_004031af;
              pbVar3 = pbVar3 + 2;
              pcVar10 = pcVar10 + 2;
            } while (bVar1 != 0);
            iVar4 = 0;
LAB_004031b4:
            if (iVar4 != 0) {
              pcVar10 = s_S2_main_0042f998;
              pbVar3 = pbVar12;
              do {
                bVar1 = *pbVar3;
                uVar7 = (undefined3)((uint)piVar6 >> 8);
                piVar6 = (int *)CONCAT31(uVar7,bVar1);
                bVar14 = bVar1 < (byte)*pcVar10;
                if (bVar1 != *pcVar10) {
LAB_004031e7:
                  iVar4 = (1 - (uint)bVar14) - (uint)(bVar14 != 0);
                  goto LAB_004031ec;
                }
                if (bVar1 == 0) break;
                bVar1 = pbVar3[1];
                piVar6 = (int *)CONCAT31(uVar7,bVar1);
                bVar14 = bVar1 < (byte)pcVar10[1];
                if (bVar1 != pcVar10[1]) goto LAB_004031e7;
                pbVar3 = pbVar3 + 2;
                pcVar10 = pcVar10 + 2;
              } while (bVar1 != 0);
              iVar4 = 0;
LAB_004031ec:
              if (iVar4 != 0) {
                pbVar11 = &DAT_0042f990;
                pbVar3 = pbVar12;
                do {
                  bVar1 = *pbVar3;
                  uVar7 = (undefined3)((uint)piVar6 >> 8);
                  piVar6 = (int *)CONCAT31(uVar7,bVar1);
                  bVar14 = bVar1 < *pbVar11;
                  if (bVar1 != *pbVar11) {
LAB_0040321f:
                    iVar4 = (1 - (uint)bVar14) - (uint)(bVar14 != 0);
                    goto LAB_00403224;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pbVar3[1];
                  piVar6 = (int *)CONCAT31(uVar7,bVar1);
                  bVar14 = bVar1 < pbVar11[1];
                  if (bVar1 != pbVar11[1]) goto LAB_0040321f;
                  pbVar3 = pbVar3 + 2;
                  pbVar11 = pbVar11 + 2;
                } while (bVar1 != 0);
                iVar4 = 0;
LAB_00403224:
                if (iVar4 != 0) {
                  pbVar11 = &DAT_0042f988;
                  pbVar3 = pbVar12;
                  do {
                    bVar1 = *pbVar3;
                    uVar7 = (undefined3)((uint)piVar6 >> 8);
                    piVar6 = (int *)CONCAT31(uVar7,bVar1);
                    bVar14 = bVar1 < *pbVar11;
                    if (bVar1 != *pbVar11) {
LAB_00403253:
                      iVar4 = (1 - (uint)bVar14) - (uint)(bVar14 != 0);
                      goto LAB_00403258;
                    }
                    if (bVar1 == 0) break;
                    bVar1 = pbVar3[1];
                    piVar6 = (int *)CONCAT31(uVar7,bVar1);
                    bVar14 = bVar1 < pbVar11[1];
                    if (bVar1 != pbVar11[1]) goto LAB_00403253;
                    pbVar3 = pbVar3 + 2;
                    pbVar11 = pbVar11 + 2;
                  } while (bVar1 != 0);
                  iVar4 = 0;
LAB_00403258:
                  if ((iVar4 != 0) &&
                     (iVar4 = FUN_0040aa5b(piVar6,pbVar12 + 0x40), piVar6 = local_27c,
                     *local_27c == iVar4)) {
                    bVar15 = true;
                  }
                }
              }
            }
            iVar9 = iVar9 + 1;
            pbVar12 = pbVar12 + 0x380;
          } while (iVar9 < DAT_00431784);
          if (bVar15) {
            local_278 = local_278 + 1;
            *local_274 = iVar8;
            local_274 = local_274 + 1;
            if (DAT_00452e04 == 1) {
              FUN_0040aa66((byte *)s_1_index_i__d_0042fc4c);
            }
          }
        }
        iVar8 = iVar8 + 1;
        piVar6 = local_27c + 1;
        local_27c = piVar6;
      } while (iVar8 < (int)DAT_00431780);
    }
    if (0 < local_278) {
      iVar8 = 0;
      piVar6 = local_f0;
      iVar9 = local_278;
      do {
        if (DAT_00452e04 == 1) {
          FUN_0040aa66((byte *)s_2_index_k__d_packing_index_index_0042fc24);
        }
        local_1e0[iVar8] = *piVar6;
        iVar8 = iVar8 + 2;
        if (local_278 <= iVar8) {
          iVar8 = 1;
        }
        piVar6 = piVar6 + 1;
        iVar9 = iVar9 + -1;
      } while (iVar9 != 0);
    }
    iVar8 = 0;
    if (0 < local_278) {
      do {
        if (DAT_00452e04 == 1) {
          FUN_0040aa66((byte *)s_2_1_reorder_index__d___d_0042fc08);
        }
        iVar8 = iVar8 + 1;
      } while (iVar8 < local_278);
    }
    FUN_004037f0(local_1e0,local_278);
    iVar8 = 0;
    if (0 < local_278) {
      do {
        if (DAT_00452e04 == 1) {
          FUN_0040aa66((byte *)s_2_2_reorder_index__d___d_0042fbec);
        }
        iVar8 = iVar8 + 1;
      } while (iVar8 < local_278);
    }
    local_274 = (int *)0x0;
    if (0 < local_278) {
      this = local_1e0;
      local_27c = this;
      do {
        iVar8 = 0;
        piVar6 = (int *)*local_27c;
        if (0 < DAT_00431784) {
          pbVar12 = &DAT_00440ef8;
          do {
            pcVar10 = s_S_main_0042f9a0;
            pbVar3 = pbVar12;
            do {
              bVar1 = *pbVar3;
              uVar7 = (undefined3)((uint)this >> 8);
              this = (int *)CONCAT31(uVar7,bVar1);
              bVar15 = bVar1 < (byte)*pcVar10;
              if (bVar1 != *pcVar10) {
LAB_0040340a:
                iVar9 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
                goto LAB_0040340f;
              }
              if (bVar1 == 0) break;
              bVar1 = pbVar3[1];
              this = (int *)CONCAT31(uVar7,bVar1);
              bVar15 = bVar1 < (byte)pcVar10[1];
              if (bVar1 != pcVar10[1]) goto LAB_0040340a;
              pbVar3 = pbVar3 + 2;
              pcVar10 = pcVar10 + 2;
            } while (bVar1 != 0);
            iVar9 = 0;
LAB_0040340f:
            if (iVar9 != 0) {
              pcVar10 = s_S2_main_0042f998;
              pbVar3 = pbVar12;
              do {
                bVar1 = *pbVar3;
                uVar7 = (undefined3)((uint)this >> 8);
                this = (int *)CONCAT31(uVar7,bVar1);
                bVar15 = bVar1 < (byte)*pcVar10;
                if (bVar1 != *pcVar10) {
LAB_00403442:
                  iVar9 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
                  goto LAB_00403447;
                }
                if (bVar1 == 0) break;
                bVar1 = pbVar3[1];
                this = (int *)CONCAT31(uVar7,bVar1);
                bVar15 = bVar1 < (byte)pcVar10[1];
                if (bVar1 != pcVar10[1]) goto LAB_00403442;
                pbVar3 = pbVar3 + 2;
                pcVar10 = pcVar10 + 2;
              } while (bVar1 != 0);
              iVar9 = 0;
LAB_00403447:
              if (iVar9 != 0) {
                pbVar11 = &DAT_0042f990;
                pbVar3 = pbVar12;
                do {
                  bVar1 = *pbVar3;
                  uVar7 = (undefined3)((uint)this >> 8);
                  this = (int *)CONCAT31(uVar7,bVar1);
                  bVar15 = bVar1 < *pbVar11;
                  if (bVar1 != *pbVar11) {
LAB_0040347a:
                    iVar9 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
                    goto LAB_0040347f;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pbVar3[1];
                  this = (int *)CONCAT31(uVar7,bVar1);
                  bVar15 = bVar1 < pbVar11[1];
                  if (bVar1 != pbVar11[1]) goto LAB_0040347a;
                  pbVar3 = pbVar3 + 2;
                  pbVar11 = pbVar11 + 2;
                } while (bVar1 != 0);
                iVar9 = 0;
LAB_0040347f:
                if (iVar9 != 0) {
                  pbVar11 = &DAT_0042f988;
                  pbVar3 = pbVar12;
                  do {
                    bVar1 = *pbVar3;
                    uVar7 = (undefined3)((uint)this >> 8);
                    this = (int *)CONCAT31(uVar7,bVar1);
                    bVar15 = bVar1 < *pbVar11;
                    if (bVar1 != *pbVar11) {
LAB_004034ae:
                      iVar9 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
                      goto LAB_004034b3;
                    }
                    if (bVar1 == 0) break;
                    bVar1 = pbVar3[1];
                    this = (int *)CONCAT31(uVar7,bVar1);
                    bVar15 = bVar1 < pbVar11[1];
                    if (bVar1 != pbVar11[1]) goto LAB_004034ae;
                    pbVar3 = pbVar3 + 2;
                    pbVar11 = pbVar11 + 2;
                  } while (bVar1 != 0);
                  iVar9 = 0;
LAB_004034b3:
                  if ((iVar9 != 0) &&
                     (iVar9 = FUN_0040aa5b(this,pbVar12 + 0x40), this = piVar6,
                     (&DAT_00431788)[(int)piVar6] == iVar9)) break;
                }
              }
            }
            iVar8 = iVar8 + 1;
            pbVar12 = pbVar12 + 0x380;
          } while (iVar8 < DAT_00431784);
        }
        if ((DAT_00452e04 == 1) &&
           (FUN_0040aa66((byte *)s_3_index_k__d_index_j__d_0042fbd4), DAT_00452e04 == 1)) {
          FUN_0040aa66((byte *)s_as_tag__s_0042fbc8);
        }
        pcVar10 = s_T_main_0042fbc0;
        puVar13 = local_260;
        for (iVar9 = 0x20; iVar9 != 0; iVar9 = iVar9 + -1) {
          *puVar13 = 0;
          puVar13 = puVar13 + 1;
        }
        pbVar12 = &DAT_00440ef8 + iVar8 * 0x380;
        pbVar3 = pbVar12;
        do {
          bVar1 = *pbVar3;
          bVar15 = bVar1 < (byte)*pcVar10;
          if (bVar1 != *pcVar10) {
LAB_0040357b:
            iVar8 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
            goto LAB_00403580;
          }
          if (bVar1 == 0) break;
          bVar1 = pbVar3[1];
          bVar15 = bVar1 < (byte)pcVar10[1];
          if (bVar1 != pcVar10[1]) goto LAB_0040357b;
          pbVar3 = pbVar3 + 2;
          pcVar10 = pcVar10 + 2;
        } while (bVar1 != 0);
        iVar8 = 0;
LAB_00403580:
        if (iVar8 == 0) {
LAB_00403596:
          FUN_0040b2c7((undefined1 *)local_260,(byte *)s__s__s_0042fbb8);
        }
        else {
          pcVar10 = s_T2_main_0042fbb0;
          pbVar3 = pbVar12;
          do {
            bVar1 = *pbVar3;
            bVar15 = bVar1 < (byte)*pcVar10;
            if (bVar1 != *pcVar10) {
LAB_004035dc:
              iVar8 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
              goto LAB_004035e1;
            }
            if (bVar1 == 0) break;
            bVar1 = pbVar3[1];
            bVar15 = bVar1 < (byte)pcVar10[1];
            if (bVar1 != pcVar10[1]) goto LAB_004035dc;
            pbVar3 = pbVar3 + 2;
            pcVar10 = pcVar10 + 2;
          } while (bVar1 != 0);
          iVar8 = 0;
LAB_004035e1:
          if (iVar8 == 0) goto LAB_00403596;
          pbVar11 = &DAT_0042fba8;
          pbVar3 = pbVar12;
          do {
            bVar1 = *pbVar3;
            bVar15 = bVar1 < *pbVar11;
            if (bVar1 != *pbVar11) {
LAB_00403628:
              iVar8 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
              goto LAB_0040362d;
            }
            if (bVar1 == 0) break;
            bVar1 = pbVar3[1];
            bVar15 = bVar1 < pbVar11[1];
            if (bVar1 != pbVar11[1]) goto LAB_00403628;
            pbVar3 = pbVar3 + 2;
            pbVar11 = pbVar11 + 2;
          } while (bVar1 != 0);
          iVar8 = 0;
LAB_0040362d:
          if (iVar8 == 0) goto LAB_00403596;
          pcVar10 = s_T4_main_0042fba0;
          do {
            bVar1 = *pbVar12;
            bVar15 = bVar1 < (byte)*pcVar10;
            if (bVar1 != *pcVar10) {
LAB_00403677:
              iVar8 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
              goto LAB_0040367c;
            }
            if (bVar1 == 0) break;
            bVar1 = pbVar12[1];
            bVar15 = bVar1 < (byte)pcVar10[1];
            if (bVar1 != pcVar10[1]) goto LAB_00403677;
            pbVar12 = pbVar12 + 2;
            pcVar10 = pcVar10 + 2;
          } while (bVar1 != 0);
          iVar8 = 0;
LAB_0040367c:
          if (iVar8 == 0) goto LAB_00403596;
          FUN_0040b2c7((undefined1 *)local_260,&DAT_0042fb9c);
        }
        pbVar3 = &DAT_0042fb98;
        pbVar12 = &DAT_00440a78;
        do {
          bVar1 = *pbVar12;
          bVar15 = bVar1 < *pbVar3;
          if (bVar1 != *pbVar3) {
LAB_004036e4:
            iVar8 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
            goto LAB_004036e9;
          }
          if (bVar1 == 0) break;
          bVar1 = pbVar12[1];
          bVar15 = bVar1 < pbVar3[1];
          if (bVar1 != pbVar3[1]) goto LAB_004036e4;
          pbVar12 = pbVar12 + 2;
          pbVar3 = pbVar3 + 2;
        } while (bVar1 != 0);
        iVar8 = 0;
LAB_004036e9:
        pcVar10 = s_append_no_priv__s__s_0042fb80;
        if (iVar8 != 0) {
          pcVar10 = s_append_priv__s__s_0042fb6c;
        }
        this = (int *)FUN_0040ac3e((int *)pFVar2,(byte *)pcVar10);
        if ((int)this < 1) goto LAB_004037cb;
        local_274 = (int *)((int)local_274 + 1);
        local_27c = local_27c + 1;
      } while ((int)local_274 < local_278);
    }
    if ((int)this < 1) {
LAB_004037cb:
      local_26c = 0xffffffff;
    }
    else {
      pbVar3 = &DAT_0042fb98;
      pbVar12 = &DAT_00440a78;
      do {
        bVar1 = *pbVar12;
        bVar15 = bVar1 < *pbVar3;
        if (bVar1 != *pbVar3) {
LAB_0040377b:
          iVar8 = (1 - (uint)bVar15) - (uint)(bVar15 != 0);
          goto LAB_00403780;
        }
        if (bVar1 == 0) break;
        bVar1 = pbVar12[1];
        bVar15 = bVar1 < pbVar3[1];
        if (bVar1 != pbVar3[1]) goto LAB_0040377b;
        pbVar12 = pbVar12 + 2;
        pbVar3 = pbVar3 + 2;
      } while (bVar1 != 0);
      iVar8 = 0;
LAB_00403780:
      pcVar10 = s_append_no_priv__s__s_0042fb80;
      if (iVar8 != 0) {
        pcVar10 = s_append_priv__s__s_0042fb6c;
      }
      iVar8 = FUN_0040ac3e((int *)pFVar2,(byte *)pcVar10);
      if ((0 < iVar8) &&
         (iVar8 = FUN_0040ac3e((int *)pFVar2,(byte *)s_output__s_0042fb58), 0 < iVar8)) {
        local_26c = 0;
      }
    }
  }
  FUN_0040af1b(pFVar2);
  return local_26c;
}



/* 004037f0 FUN_004037f0 */

void __cdecl FUN_004037f0(undefined4 *param_1,int param_2)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  
  iVar2 = param_2 / 2;
  if (0 < iVar2) {
    puVar3 = param_1 + param_2 + -1;
    do {
      uVar1 = *param_1;
      *param_1 = *puVar3;
      *puVar3 = uVar1;
      puVar3 = puVar3 + -1;
      param_1 = param_1 + 1;
      iVar2 = iVar2 + -1;
    } while (iVar2 != 0);
  }
  return;
}



/* 00403820 FUN_00403820 */

undefined4 __cdecl FUN_00403820(int param_1)

{
  FILE *pFVar1;
  int iVar2;
  
  if (param_1 == 0) {
    return 0xfffffffe;
  }
  pFVar1 = (FILE *)FUN_0040af91((LPCSTR)&DAT_0042fcb4,(char *)&DAT_0042fa40);
  if (pFVar1 == (FILE *)0x0) {
    return 0xfffffffd;
  }
  iVar2 = FUN_0040ac3e((int *)pFVar1,&DAT_0042fca8);
  if (-1 < iVar2) {
    do {
      FUN_004038c0(pFVar1,param_1);
      param_1 = *(int *)(param_1 + 0x3010);
    } while (param_1 != 0);
    iVar2 = FUN_0040ac3e((int *)pFVar1,&DAT_0042fca8);
    if (-1 < iVar2) goto LAB_004038a6;
  }
  FUN_0040ac3e((int *)&DAT_004302c0,(byte *)s_error__Cannot_output_include_fil_0042fa0c);
LAB_004038a6:
  FUN_0040af1b(pFVar1);
  return 0;
}



/* 004038c0 FUN_004038c0 */

undefined4 __cdecl FUN_004038c0(FILE *param_1,int param_2)

{
  int iVar1;
  
  if ((param_1 == (FILE *)0x0) || (param_2 == 0)) {
    return 0;
  }
  iVar1 = FUN_0040ac3e((int *)param_1,(byte *)(param_2 + 4));
  if (-1 < iVar1) {
    if (iVar1 != 0) {
      FUN_0040ac3e((int *)param_1,&DAT_0042fcbc);
    }
    iVar1 = FUN_0040ac3e((int *)param_1,(byte *)(param_2 + 0x2c0e));
    if (-1 < iVar1) {
      if (iVar1 != 0) {
        FUN_0040ac3e((int *)param_1,&DAT_0042fcbc);
      }
      iVar1 = FUN_0040ac3e((int *)param_1,(byte *)(param_2 + 0x1c0e));
      if (-1 < iVar1) {
        if (iVar1 == 0) {
          return 0;
        }
        FUN_0040ac3e((int *)param_1,&DAT_0042fcbc);
        return 0;
      }
    }
  }
  FUN_0040ac3e((int *)&DAT_004302c0,(byte *)s_error__Cannot_output_include_fil_0042fa0c);
  FUN_0040af1b(param_1);
  return 0;
}



/* 00403970 FUN_00403970 */

void __cdecl FUN_00403970(uint *param_1,uint *param_2)

{
  FUN_00403a30(param_1,param_2);
  return;
}



/* 00403990 FUN_00403990 */

void __cdecl FUN_00403990(int param_1,uint *param_2,uint *param_3)

{
  FUN_00403ad0(param_1,param_2,param_3);
  return;
}



/* 004039b0 FUN_004039b0 */

void __cdecl FUN_004039b0(int *param_1)

{
  FUN_00403cb0(param_1);
  return;
}



/* 004039c0 FUN_004039c0 */

void __cdecl FUN_004039c0(int param_1,byte *param_2)

{
  FUN_00403df0(param_1,param_2);
  return;
}



/* 004039e0 FUN_004039e0 */

void __cdecl FUN_004039e0(int param_1,byte *param_2)

{
  FUN_00403e30(param_1,param_2);
  return;
}



/* 00403a00 FUN_00403a00 */

void __cdecl FUN_00403a00(int *param_1)

{
  FUN_00404280(param_1);
  return;
}



/* 00403a10 FUN_00403a10 */

void __cdecl FUN_00403a10(undefined4 *param_1,uint param_2,int *param_3)

{
  FUN_004045f0(param_1,param_2,param_3);
  return;
}



/* 00403a30 FUN_00403a30 */

undefined4 * __cdecl FUN_00403a30(uint *param_1,uint *param_2)

{
  LPVOID pvVar1;
  undefined4 *puVar2;
  uint *puVar3;
  int iVar4;
  undefined4 *puVar5;
  
  puVar2 = _malloc(0x34);
  if (puVar2 != (undefined4 *)0x0) {
    puVar5 = puVar2;
    for (iVar4 = 0xd; iVar4 != 0; iVar4 = iVar4 + -1) {
      *puVar5 = 0;
      puVar5 = puVar5 + 1;
    }
    puVar2[3] = 0;
    puVar3 = FUN_00410a0f(param_1);
    puVar2[4] = puVar3;
    if (param_2 == (uint *)0x0) {
      param_2 = (uint *)&DAT_00452e24;
    }
    puVar3 = FUN_00410a0f(param_2);
    pvVar1 = (LPVOID)puVar2[4];
    puVar2[5] = puVar3;
    if (pvVar1 != (LPVOID)0x0) {
      if (puVar3 != (uint *)0x0) {
        puVar2[7] = 0;
        puVar2[8] = 0;
        puVar2[9] = 0;
        puVar2[10] = 0;
        puVar2[0xb] = 0;
        puVar2[0xc] = 0;
        return puVar2;
      }
      if (pvVar1 != (LPVOID)0x0) {
        FUN_0040aa97(pvVar1);
      }
    }
    if ((LPVOID)puVar2[5] != (LPVOID)0x0) {
      FUN_0040aa97((LPVOID)puVar2[5]);
    }
    FUN_0040aa97(puVar2);
  }
  return (undefined4 *)0x0;
}



/* 00403ad0 FUN_00403ad0 */

void __cdecl FUN_00403ad0(int param_1,uint *param_2,uint *param_3)

{
  undefined4 *puVar1;
  
  puVar1 = FUN_00403a30(param_2,param_3);
  if (puVar1 != (undefined4 *)0x0) {
    *puVar1 = 0;
    puVar1[3] = param_1;
    puVar1[1] = *(undefined4 *)(param_1 + 0x20);
    if (*(int *)(param_1 + 0x1c) == 0) {
      *(undefined4 **)(param_1 + 0x1c) = puVar1;
      *(undefined4 **)(param_1 + 0x20) = puVar1;
      *(int *)(param_1 + 0x24) = *(int *)(param_1 + 0x24) + 1;
      return;
    }
    **(undefined4 **)(param_1 + 0x20) = puVar1;
    *(undefined4 **)(param_1 + 0x20) = puVar1;
    *(int *)(param_1 + 0x24) = *(int *)(param_1 + 0x24) + 1;
  }
  return;
}



/* 00403b20 FUN_00403b20 */

void __cdecl FUN_00403b20(int *param_1)

{
  int iVar1;
  
  iVar1 = param_1[3];
  if (iVar1 == 0) {
    return;
  }
  if ((int *)param_1[1] == (int *)0x0) {
    if ((*param_1 == 0) && (param_1 != *(int **)(iVar1 + 0x1c))) goto LAB_00403b75;
    *(int *)(iVar1 + 0x1c) = *param_1;
  }
  else {
    *(int *)param_1[1] = *param_1;
  }
  if (*param_1 == 0) {
    *(int *)(param_1[3] + 0x20) = param_1[1];
  }
  else {
    *(int *)(*param_1 + 4) = param_1[1];
  }
  *param_1 = 0;
  param_1[1] = 0;
  *(int *)(param_1[3] + 0x24) = *(int *)(param_1[3] + 0x24) + -1;
LAB_00403b75:
  param_1[3] = 0;
  return;
}



/* 00403b80 FUN_00403b80 */

undefined4 * __cdecl FUN_00403b80(uint *param_1,uint *param_2)

{
  undefined4 *puVar1;
  uint *puVar2;
  
  puVar1 = _malloc(0x10);
  if (puVar1 != (undefined4 *)0x0) {
    *puVar1 = 0;
    puVar1[1] = 0;
    puVar1[2] = 0;
    puVar1[3] = 0;
    puVar2 = FUN_00410a0f(param_1);
    puVar1[2] = puVar2;
    if (param_2 == (uint *)0x0) {
      param_2 = (uint *)&DAT_00452e24;
    }
    puVar2 = FUN_00410a0f(param_2);
    puVar1[3] = puVar2;
    if ((puVar1[2] != 0) && (puVar2 != (uint *)0x0)) {
      return puVar1;
    }
    FUN_0040aa66((byte *)s_run_here_0042fcc0);
    if ((LPVOID)puVar1[2] != (LPVOID)0x0) {
      FUN_0040aa97((LPVOID)puVar1[2]);
    }
    if ((LPVOID)puVar1[3] != (LPVOID)0x0) {
      FUN_0040aa97((LPVOID)puVar1[3]);
    }
    FUN_0040aa97(puVar1);
  }
  return (undefined4 *)0x0;
}



/* 00403c20 FUN_00403c20 */

undefined4 * __cdecl FUN_00403c20(int param_1,uint *param_2,uint *param_3)

{
  undefined4 *puVar1;
  
  if (param_1 == 0) {
    return (undefined4 *)0x0;
  }
  puVar1 = FUN_00403b80(param_2,param_3);
  if (puVar1 != (undefined4 *)0x0) {
    *puVar1 = 0;
    puVar1[1] = *(undefined4 *)(param_1 + 0x2c);
    if (*(int *)(param_1 + 0x28) == 0) {
      *(undefined4 **)(param_1 + 0x28) = puVar1;
      *(undefined4 **)(param_1 + 0x2c) = puVar1;
      *(int *)(param_1 + 0x30) = *(int *)(param_1 + 0x30) + 1;
      return puVar1;
    }
    **(undefined4 **)(param_1 + 0x2c) = puVar1;
    *(undefined4 **)(param_1 + 0x2c) = puVar1;
    *(int *)(param_1 + 0x30) = *(int *)(param_1 + 0x30) + 1;
  }
  return puVar1;
}



/* 00403c80 FUN_00403c80 */

void __cdecl FUN_00403c80(LPVOID param_1)

{
  if (*(LPVOID *)((int)param_1 + 8) != (LPVOID)0x0) {
    FUN_0040aa97(*(LPVOID *)((int)param_1 + 8));
  }
  if (*(LPVOID *)((int)param_1 + 0xc) != (LPVOID)0x0) {
    FUN_0040aa97(*(LPVOID *)((int)param_1 + 0xc));
  }
  FUN_0040aa97(param_1);
  return;
}



/* 00403cb0 FUN_00403cb0 */

void __cdecl FUN_00403cb0(int *param_1)

{
  int iVar1;
  
  if ((param_1 != (int *)0x0) &&
     ((param_1[2] == 0 || (iVar1 = param_1[2] + -1, param_1[2] = iVar1, iVar1 == 0)))) {
    FUN_00403cd0(param_1);
  }
  return;
}



/* 00403cd0 FUN_00403cd0 */

void __cdecl FUN_00403cd0(int *param_1)

{
  int *piVar1;
  
  if (param_1 != (int *)0x0) {
    piVar1 = (int *)param_1[7];
    while (piVar1 != (int *)0x0) {
      FUN_00403b20(piVar1);
      FUN_00403cb0(piVar1);
      piVar1 = (int *)param_1[7];
    }
    piVar1 = (int *)param_1[10];
    while (piVar1 != (int *)0x0) {
      if ((int *)piVar1[1] == (int *)0x0) {
        param_1[10] = *piVar1;
      }
      else {
        *(int *)piVar1[1] = *piVar1;
      }
      if (*piVar1 == 0) {
        param_1[0xb] = piVar1[1];
      }
      else {
        *(int *)(*piVar1 + 4) = piVar1[1];
      }
      *piVar1 = 0;
      piVar1[1] = 0;
      param_1[0xc] = param_1[0xc] + -1;
      FUN_00403c80(piVar1);
      piVar1 = (int *)param_1[10];
    }
    FUN_00403d60(param_1);
  }
  return;
}



/* 00403d60 FUN_00403d60 */

void __cdecl FUN_00403d60(int *param_1)

{
  FUN_00403b20(param_1);
  if ((LPVOID)param_1[4] != (LPVOID)0x0) {
    FUN_0040aa97((LPVOID)param_1[4]);
  }
  if ((LPVOID)param_1[5] != (LPVOID)0x0) {
    FUN_0040aa97((LPVOID)param_1[5]);
  }
  FUN_0040aa97(param_1);
  return;
}



/* 00403da0 FUN_00403da0 */

int __cdecl FUN_00403da0(byte *param_1,byte *param_2)

{
  byte bVar1;
  bool bVar2;
  
  if (param_1 == (byte *)0x0) {
    return -1;
  }
  if (param_2 == (byte *)0x0) {
    return 1;
  }
  while( true ) {
    bVar1 = *param_1;
    bVar2 = bVar1 < *param_2;
    if (bVar1 != *param_2) break;
    if (bVar1 == 0) {
      return 0;
    }
    bVar1 = param_1[1];
    bVar2 = bVar1 < param_2[1];
    if (bVar1 != param_2[1]) break;
    param_1 = param_1 + 2;
    param_2 = param_2 + 2;
    if (bVar1 == 0) {
      return 0;
    }
  }
  return (1 - (uint)bVar2) - (uint)(bVar2 != 0);
}



/* 00403df0 FUN_00403df0 */

undefined4 * __cdecl FUN_00403df0(int param_1,byte *param_2)

{
  undefined4 *puVar1;
  int iVar2;
  
  puVar1 = *(undefined4 **)(param_1 + 0x1c);
  while( true ) {
    if (puVar1 == (undefined4 *)0x0) {
      return (undefined4 *)0x0;
    }
    iVar2 = FUN_00403da0((byte *)puVar1[4],param_2);
    if (iVar2 == 0) break;
    puVar1 = (undefined4 *)*puVar1;
  }
  return puVar1;
}



/* 00403e30 FUN_00403e30 */

uint * __cdecl FUN_00403e30(int param_1,byte *param_2)

{
  uint *puVar1;
  
  if (param_1 == 0) {
    return (uint *)0x0;
  }
  puVar1 = FUN_00403e70(4);
  if (puVar1 != (uint *)0x0) {
    FUN_00403ef0(param_1,param_2,puVar1);
  }
  return puVar1;
}



/* 00403e70 FUN_00403e70 */

undefined4 * __cdecl FUN_00403e70(uint param_1)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  uint uVar3;
  int iVar4;
  
  if (param_1 < 4) {
    param_1 = 4;
  }
  puVar1 = _malloc(0xc);
  if (puVar1 != (undefined4 *)0x0) {
    *puVar1 = 0;
    puVar1[1] = 0;
    puVar1[2] = 0;
    puVar2 = _malloc(param_1 * 4);
    puVar1[2] = puVar2;
    if (puVar2 != (undefined4 *)0x0) {
      for (uVar3 = param_1 & 0x3fffffff; uVar3 != 0; uVar3 = uVar3 - 1) {
        *puVar2 = 0;
        puVar2 = puVar2 + 1;
      }
      for (iVar4 = 0; iVar4 != 0; iVar4 = iVar4 + -1) {
        *(undefined1 *)puVar2 = 0;
        puVar2 = (undefined4 *)((int)puVar2 + 1);
      }
      puVar1[1] = param_1;
      return puVar1;
    }
    FUN_0040aa97(puVar1);
  }
  return (undefined4 *)0x0;
}



/* 00403ef0 FUN_00403ef0 */

void __cdecl FUN_00403ef0(int param_1,byte *param_2,uint *param_3)

{
  undefined4 *puVar1;
  int iVar2;
  
  for (puVar1 = *(undefined4 **)(param_1 + 0x1c); puVar1 != (undefined4 *)0x0;
      puVar1 = (undefined4 *)*puVar1) {
    FUN_00403ef0((int)puVar1,param_2,param_3);
  }
  iVar2 = FUN_00403da0(*(byte **)(param_1 + 0x10),param_2);
  if (iVar2 == 0) {
    FUN_00403f40(param_3,param_1);
  }
  return;
}



/* 00403f40 FUN_00403f40 */

uint __cdecl FUN_00403f40(uint *param_1,undefined4 param_2)

{
  uint uVar1;
  int iVar2;
  
  uVar1 = FUN_00403f80(param_1);
  iVar2 = FUN_00403fb0((int)param_1,uVar1);
  if (iVar2 == 0) {
    return 0xffffffff;
  }
  *(undefined4 *)(param_1[2] + uVar1 * 4) = param_2;
  if (*param_1 <= uVar1) {
    *param_1 = uVar1 + 1;
  }
  return uVar1;
}



/* 00403f80 FUN_00403f80 */

uint __cdecl FUN_00403f80(uint *param_1)

{
  uint uVar1;
  uint uVar2;
  int *piVar3;
  
  uVar1 = *param_1;
  if (uVar1 == 0) {
    return 0;
  }
  uVar2 = 0;
  if (uVar1 != 0) {
    piVar3 = (int *)param_1[2];
    do {
      if (*piVar3 == 0) {
        return uVar2;
      }
      uVar2 = uVar2 + 1;
      piVar3 = piVar3 + 1;
    } while (uVar2 < uVar1);
  }
  return uVar2;
}



/* 00403fb0 FUN_00403fb0 */

undefined4 __cdecl FUN_00403fb0(int param_1,uint param_2)

{
  int *piVar1;
  uint uVar2;
  int iVar3;
  
  if (param_2 < *(uint *)(param_1 + 4)) {
    return 1;
  }
  piVar1 = FUN_0040b428(*(int **)(param_1 + 8),(uint *)(*(uint *)(param_1 + 4) << 3));
  *(int **)(param_1 + 8) = piVar1;
  if (piVar1 == (int *)0x0) {
    return 0;
  }
  piVar1 = piVar1 + *(uint *)(param_1 + 4);
  for (uVar2 = *(uint *)(param_1 + 4) & 0x3fffffff; uVar2 != 0; uVar2 = uVar2 - 1) {
    *piVar1 = 0;
    piVar1 = piVar1 + 1;
  }
  for (iVar3 = 0; iVar3 != 0; iVar3 = iVar3 + -1) {
    *(undefined1 *)piVar1 = 0;
    piVar1 = (int *)((int)piVar1 + 1);
  }
  uVar2 = *(int *)(param_1 + 4) * 2;
  *(uint *)(param_1 + 4) = uVar2;
  if ((uVar2 < param_2 || uVar2 - param_2 == 0) &&
     (iVar3 = FUN_00403fb0(param_1,param_2), iVar3 == 0)) {
    return 0;
  }
  return 1;
}



/* 00404030 FUN_00404030 */

undefined4 __cdecl FUN_00404030(int param_1)

{
  if ((((param_1 != 0x20) && (param_1 != 0xc)) && (param_1 != 10)) &&
     (((param_1 != 0xd && (param_1 != 9)) && (param_1 != 0xb)))) {
    return 0;
  }
  return 1;
}



/* 00404060 FUN_00404060 */

undefined4 __cdecl FUN_00404060(int *param_1,int *param_2,uint param_3)

{
  char cVar1;
  undefined4 *puVar2;
  int *piVar3;
  int iVar4;
  uint uVar5;
  char *pcVar6;
  uint uVar7;
  
  if (param_1 == (int *)0x0) {
    return 0;
  }
  FUN_004048a0(param_2,&DAT_0042fce4);
  FUN_004048a0(param_2,(char *)param_1[4]);
  for (puVar2 = (undefined4 *)param_1[10]; puVar2 != (undefined4 *)0x0;
      puVar2 = (undefined4 *)*puVar2) {
    FUN_004048a0(param_2,&DAT_0042fce0);
    FUN_004048a0(param_2,(char *)puVar2[2]);
    FUN_004048a0(param_2,&DAT_0042fcdc);
    FUN_004048a0(param_2,(char *)puVar2[3]);
    FUN_004048a0(param_2,&DAT_0042fcd8);
  }
  if (param_1[7] == 0) {
    FUN_004048a0(param_2,&DAT_0042fcd4);
    uVar5 = 0xffffffff;
    pcVar6 = (char *)param_1[4];
    do {
      if (uVar5 == 0) break;
      uVar5 = uVar5 - 1;
      cVar1 = *pcVar6;
      pcVar6 = pcVar6 + 1;
    } while (cVar1 != '\0');
    for (iVar4 = 0x14 - (~uVar5 - 1); iVar4 != 0; iVar4 = iVar4 + -1) {
      FUN_004048a0(param_2,&DAT_0042fce0);
    }
    FUN_00404780(param_2,(char *)param_1[5]);
    uVar5 = 0xffffffff;
    pcVar6 = (char *)param_1[5];
    do {
      if (uVar5 == 0) break;
      uVar5 = uVar5 - 1;
      cVar1 = *pcVar6;
      pcVar6 = pcVar6 + 1;
    } while (cVar1 != '\0');
    if (~uVar5 - 1 < 0x41) {
      uVar5 = 0xffffffff;
      pcVar6 = (char *)param_1[5];
      do {
        if (uVar5 == 0) break;
        uVar5 = uVar5 - 1;
        cVar1 = *pcVar6;
        pcVar6 = pcVar6 + 1;
      } while (cVar1 != '\0');
      for (iVar4 = 0x40 - (~uVar5 - 1); iVar4 != 0; iVar4 = iVar4 + -1) {
        FUN_004048a0(param_2,&DAT_0042fce0);
      }
    }
    else {
      FUN_004048a0(param_2,&DAT_0042fcbc);
      for (iVar4 = param_3 + 0x54; iVar4 != 0; iVar4 = iVar4 + -1) {
        FUN_004048a0(param_2,&DAT_0042fce0);
      }
    }
    FUN_004048a0(param_2,&DAT_0042fcd0);
    FUN_004048a0(param_2,(char *)param_1[4]);
    FUN_004048a0(param_2,&DAT_0042fccc);
    if ((*param_1 == 0) && (3 < param_3)) {
      param_3 = param_3 - 4;
    }
    if (param_3 != 0) {
      do {
        FUN_004048a0(param_2,&DAT_0042fce0);
        param_3 = param_3 - 1;
      } while (param_3 != 0);
      return 0;
    }
  }
  else {
    FUN_004048a0(param_2,&DAT_0042fcd4);
    FUN_004048a0(param_2,&DAT_0042fcbc);
    uVar5 = param_3 + 4;
    for (uVar7 = uVar5; uVar7 != 0; uVar7 = uVar7 - 1) {
      FUN_004048a0(param_2,&DAT_0042fce0);
    }
    for (piVar3 = (int *)param_1[7]; piVar3 != (int *)0x0; piVar3 = (int *)*piVar3) {
      FUN_00404060(piVar3,param_2,uVar5);
    }
    FUN_004048a0(param_2,&DAT_0042fcd0);
    FUN_004048a0(param_2,(char *)param_1[4]);
    FUN_004048a0(param_2,&DAT_0042fccc);
    uVar7 = uVar5;
    if (((*param_1 != 0) || (uVar7 = param_3, 3 < uVar5)) && (uVar5 = uVar7, 3 < uVar7)) {
      uVar5 = uVar7 - 4;
    }
    for (; uVar5 != 0; uVar5 = uVar5 - 1) {
      FUN_004048a0(param_2,&DAT_0042fce0);
    }
  }
  return 0;
}



/* 00404280 FUN_00404280 */

uint __cdecl FUN_00404280(int *param_1)

{
  int iVar1;
  uint local_c [3];
  
  FUN_00404670(local_c);
  iVar1 = FUN_00404060(param_1,(int *)local_c,0);
  return ~-(uint)(iVar1 != 0) & local_c[0];
}



/* 004042b0 FUN_004042b0 */

undefined4 __cdecl FUN_004042b0(undefined4 *param_1)

{
  int *piVar1;
  int iVar2;
  undefined4 *puVar3;
  
  puVar3 = param_1;
  for (iVar2 = 6; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0;
    puVar3 = puVar3 + 1;
  }
  piVar1 = _malloc(4);
  if (piVar1 == (int *)0x0) {
    return 0xffffffff;
  }
  *piVar1 = 0;
  iVar2 = FUN_004048d0((char *)0x0);
  *piVar1 = iVar2;
  param_1[5] = piVar1;
  FUN_00404e10(*piVar1,&LAB_00404340,FUN_00404410);
  FUN_00404e40(*piVar1,&LAB_004044d0,&LAB_004044e0);
  FUN_00404e30(*piVar1,FUN_004044f0);
  FUN_00404df0((int *)*piVar1,(int)param_1);
  param_1[3] = 0;
  return 0;
}



/* 004043d0 FUN_004043d0 */

undefined4 __cdecl FUN_004043d0(int param_1,undefined4 param_2)

{
  undefined4 *puVar1;
  
  puVar1 = _malloc(8);
  if (puVar1 == (undefined4 *)0x0) {
    return 0xfffffffe;
  }
  *puVar1 = 0;
  puVar1[1] = 0;
  puVar1[1] = param_2;
  *puVar1 = *(undefined4 *)(param_1 + 8);
  *(undefined4 **)(param_1 + 8) = puVar1;
  return 0;
}



/* 00404410 FUN_00404410 */

void __cdecl FUN_00404410(int param_1)

{
  byte *pbVar1;
  undefined4 uVar2;
  
  if ((*(int *)(param_1 + 4) != 0) &&
     (pbVar1 = *(byte **)(*(int *)(param_1 + 4) + 0x14), pbVar1 != (byte *)0x0)) {
    FUN_00404470(pbVar1);
  }
  uVar2 = FUN_00404440(param_1);
  *(undefined4 *)(param_1 + 4) = uVar2;
  return;
}



/* 00404440 FUN_00404440 */

undefined4 __cdecl FUN_00404440(int param_1)

{
  undefined4 *puVar1;
  undefined4 uVar2;
  
  uVar2 = 0;
  puVar1 = *(undefined4 **)(param_1 + 8);
  if (puVar1 != (undefined4 *)0x0) {
    *(undefined4 *)(param_1 + 8) = *puVar1;
    uVar2 = puVar1[1];
    FUN_0040aa97(puVar1);
  }
  return uVar2;
}



/* 00404470 FUN_00404470 */

void __cdecl FUN_00404470(byte *param_1)

{
  byte bVar1;
  int iVar2;
  uint uVar3;
  int iVar4;
  byte *pbVar5;
  
  uVar3 = 0xffffffff;
  pbVar5 = param_1;
  do {
    if (uVar3 == 0) break;
    uVar3 = uVar3 - 1;
    bVar1 = *pbVar5;
    pbVar5 = pbVar5 + 1;
  } while (bVar1 != 0);
  iVar4 = ~uVar3 - 1;
  while ((0 < iVar4 && (iVar2 = FUN_00404030((int)(char)param_1[iVar4 + -1]), iVar2 != 0))) {
    iVar4 = iVar4 + -1;
    param_1[iVar4] = 0;
  }
  iVar2 = FUN_0040b890(param_1,&DAT_0042fce8);
  FUN_0040b550((undefined4 *)param_1,(undefined4 *)(param_1 + iVar2),iVar4 - iVar2);
  param_1[iVar4 - iVar2] = 0;
  return;
}



/* 004044f0 FUN_004044f0 */

void __cdecl FUN_004044f0(int param_1,char *param_2,size_t param_3)

{
  uint *_Size;
  int iVar1;
  void *pvVar2;
  int *piVar3;
  uint uVar4;
  int iVar5;
  int *piVar6;
  
  iVar5 = 0;
  if ((*(int *)(param_1 + 4) != 0) && ((*(byte *)(param_1 + 0x10) & 1) == 0)) {
    piVar3 = *(int **)(*(int *)(param_1 + 4) + 0x14);
    if (piVar3 != (int *)0x0) {
      uVar4 = 0xffffffff;
      piVar6 = piVar3;
      do {
        if (uVar4 == 0) break;
        uVar4 = uVar4 - 1;
        iVar5 = *piVar6;
        piVar6 = (int *)((int)piVar6 + 1);
      } while ((char)iVar5 != '\0');
      iVar5 = ~uVar4 - 1;
    }
    _Size = (uint *)(iVar5 + 1 + param_3);
    if (piVar3 == (int *)0x0) {
      pvVar2 = _malloc((size_t)_Size);
      *(void **)(*(int *)(param_1 + 4) + 0x14) = pvVar2;
    }
    else {
      piVar3 = FUN_0040b428(piVar3,_Size);
      *(int **)(*(int *)(param_1 + 4) + 0x14) = piVar3;
    }
    iVar1 = *(int *)(*(int *)(param_1 + 4) + 0x14);
    if (iVar1 != 0) {
      _strncpy((char *)(iVar1 + iVar5),param_2,param_3);
      *(undefined1 *)(*(int *)(*(int *)(param_1 + 4) + 0x14) + -1 + (int)_Size) = 0;
    }
  }
  return;
}



/* 00404580 FUN_00404580 */

void __cdecl FUN_00404580(undefined4 *param_1)

{
  if ((int *)param_1[5] != (int *)0x0) {
    FUN_00404c90(*(int *)param_1[5]);
    FUN_0040aa97((LPVOID)param_1[5]);
    param_1[5] = 0;
  }
  FUN_004045d0((int)param_1);
  if ((int *)*param_1 != (int *)0x0) {
    FUN_00403cb0((int *)*param_1);
    *param_1 = 0;
  }
  return;
}



/* 004045d0 FUN_004045d0 */

void __cdecl FUN_004045d0(int param_1)

{
  int iVar1;
  
  iVar1 = *(int *)(param_1 + 8);
  while (iVar1 != 0) {
    FUN_00404440(param_1);
    iVar1 = *(int *)(param_1 + 8);
  }
  return;
}



/* 004045f0 FUN_004045f0 */

undefined4 __cdecl FUN_004045f0(undefined4 *param_1,uint param_2,int *param_3)

{
  int iVar1;
  int local_18 [3];
  undefined4 local_c;
  int *local_4;
  
  FUN_004042b0(local_18);
  *param_3 = 0;
  iVar1 = FUN_00404e60(*local_4,param_1,param_2,(undefined1 *)0x1);
  if (iVar1 == 0) {
    FUN_00404580(local_18);
    return 0xfffffffd;
  }
  if (local_18[0] == 0) {
    local_c = 0xffffffff;
  }
  else {
    *param_3 = local_18[0];
    local_18[0] = 0;
  }
  FUN_00404580(local_18);
  return local_c;
}



/* 00404670 FUN_00404670 */

void __cdecl FUN_00404670(undefined4 *param_1)

{
  *param_1 = 0;
  param_1[1] = 0;
  param_1[2] = 0;
  return;
}



/* 00404680 FUN_00404680 */

undefined4 __cdecl FUN_00404680(undefined4 *param_1,int param_2)

{
  int *piVar1;
  uint *_Size;
  
  _Size = (uint *)param_1[2];
  if (_Size < (uint *)(param_2 + 1)) {
    if (_Size == (uint *)0x0) {
      _Size = (uint *)0x200;
    }
    for (; _Size < (uint *)(param_2 + 1); _Size = (uint *)((int)_Size * 2)) {
    }
    if ((int *)*param_1 == (int *)0x0) {
      piVar1 = _malloc((size_t)_Size);
    }
    else {
      piVar1 = FUN_0040b428((int *)*param_1,_Size);
    }
    if (piVar1 == (int *)0x0) {
      return 0xffffffff;
    }
    param_1[2] = _Size;
    *param_1 = piVar1;
  }
  return 0;
}



/* 004046e0 FUN_004046e0 */

undefined4 __cdecl FUN_004046e0(int *param_1,undefined4 *param_2,uint param_3)

{
  int iVar1;
  uint uVar2;
  undefined4 *puVar3;
  
  iVar1 = FUN_00404680(param_1,param_3 + param_1[1]);
  if (iVar1 < 0) {
    return 0xffffffff;
  }
  puVar3 = (undefined4 *)(*param_1 + param_1[1]);
  for (uVar2 = param_3 >> 2; uVar2 != 0; uVar2 = uVar2 - 1) {
    *puVar3 = *param_2;
    param_2 = param_2 + 1;
    puVar3 = puVar3 + 1;
  }
  for (uVar2 = param_3 & 3; uVar2 != 0; uVar2 = uVar2 - 1) {
    *(undefined1 *)puVar3 = *(undefined1 *)param_2;
    param_2 = (undefined4 *)((int)param_2 + 1);
    puVar3 = (undefined4 *)((int)puVar3 + 1);
  }
  iVar1 = param_1[1];
  param_1[1] = iVar1 + param_3;
  *(undefined1 *)(*param_1 + iVar1 + param_3) = 0;
  return 0;
}



/* 00404740 FUN_00404740 */

undefined4 __cdecl FUN_00404740(int *param_1,undefined1 param_2)

{
  int iVar1;
  
  iVar1 = FUN_00404680(param_1,param_1[1] + 1);
  if (iVar1 < 0) {
    return 0xffffffff;
  }
  *(undefined1 *)(*param_1 + param_1[1]) = param_2;
  param_1[1] = param_1[1] + 1;
  return 0;
}



/* 00404780 FUN_00404780 */

undefined4 __cdecl FUN_00404780(int *param_1,char *param_2)

{
  char cVar1;
  int iVar2;
  uint uVar3;
  char *pcVar4;
  
  uVar3 = 0xffffffff;
  pcVar4 = param_2;
  do {
    if (uVar3 == 0) break;
    uVar3 = uVar3 - 1;
    cVar1 = *pcVar4;
    pcVar4 = pcVar4 + 1;
  } while (cVar1 != '\0');
  iVar2 = FUN_00404680(param_1,param_1[1] + (~uVar3 - 1) * 4);
  if (iVar2 < 0) {
    return 0xffffffff;
  }
  FUN_004047d0(param_1,(int)param_2,~uVar3 - 1);
  *(undefined1 *)(*param_1 + param_1[1]) = 0;
  return 0;
}



/* 004047d0 FUN_004047d0 */

void __cdecl FUN_004047d0(int *param_1,int param_2,uint param_3)

{
  uint uVar1;
  
  if ((param_2 != 0) && (uVar1 = 0, param_3 != 0)) {
    do {
      switch(*(undefined1 *)(uVar1 + param_2)) {
      case 0x22:
        FUN_004048a0(param_1,s__quot__0042fcf0);
        break;
      default:
        FUN_00404740(param_1,*(undefined1 *)(uVar1 + param_2));
        break;
      case 0x26:
        FUN_004048a0(param_1,s__amp_amp__0042fd00);
        break;
      case 0x27:
        FUN_004048a0(param_1,s__apos__0042fcf8);
        break;
      case 0x3c:
        FUN_004048a0(param_1,&DAT_0042fd14);
        break;
      case 0x3e:
        FUN_004048a0(param_1,&DAT_0042fd0c);
      }
      uVar1 = uVar1 + 1;
    } while (uVar1 < param_3);
  }
  return;
}



/* 004048a0 FUN_004048a0 */

void __cdecl FUN_004048a0(int *param_1,char *param_2)

{
  char cVar1;
  uint uVar2;
  char *pcVar3;
  
  uVar2 = 0xffffffff;
  pcVar3 = param_2;
  do {
    if (uVar2 == 0) break;
    uVar2 = uVar2 - 1;
    cVar1 = *pcVar3;
    pcVar3 = pcVar3 + 1;
  } while (cVar1 != '\0');
  FUN_004046e0(param_1,(undefined4 *)param_2,~uVar2 - 1);
  return;
}



/* 004048d0 FUN_004048d0 */

void __cdecl FUN_004048d0(char *param_1)

{
  FUN_004048f0(param_1,(undefined4 *)0x0,(undefined1 *)0x0);
  return;
}



/* 004048f0 FUN_004048f0 */

undefined4 * __cdecl FUN_004048f0(char *param_1,undefined4 *param_2,undefined1 *param_3)

{
  undefined4 *puVar1;
  undefined4 uVar2;
  
  puVar1 = FUN_00404940(param_1,param_2,param_3,0);
  if ((puVar1 != (undefined4 *)0x0) && (*(char *)(puVar1 + 0x3a) != '\0')) {
    uVar2 = FUN_00409ab0((int)puVar1,"xml=http://www.w3.org/XML/1998/namespace");
    if ((char)uVar2 == '\0') {
      FUN_00404c90((int)puVar1);
      return (undefined4 *)0x0;
    }
  }
  return puVar1;
}



/* 00404940 FUN_00404940 */

undefined4 * __cdecl FUN_00404940(char *param_1,undefined4 *param_2,undefined1 *param_3,int param_4)

{
  undefined4 *puVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined **ppuVar4;
  undefined4 *puVar5;
  
  if (param_2 == (undefined4 *)0x0) {
    puVar1 = _malloc(0x1d8);
    if (puVar1 == (undefined4 *)0x0) {
      return (undefined4 *)0x0;
    }
    puVar5 = puVar1 + 3;
    *puVar5 = _malloc;
    puVar1[4] = FUN_0040b428;
    puVar1[5] = FUN_0040aa97;
  }
  else {
    puVar1 = (undefined4 *)(*(code *)*param_2)();
    if (puVar1 == (undefined4 *)0x0) {
      return (undefined4 *)0x0;
    }
    puVar5 = puVar1 + 3;
    *puVar5 = *param_2;
    puVar1[4] = param_2[1];
    puVar1[5] = param_2[2];
  }
  puVar1[2] = 0;
  puVar1[8] = 0;
  puVar1[0x5b] = 0x10;
  iVar2 = (*(code *)*puVar5)(0x100);
  puVar1[0x5e] = iVar2;
  if (iVar2 == 0) {
    (*(code *)puVar1[5])(puVar1);
    return (undefined4 *)0x0;
  }
  iVar2 = (*(code *)*puVar5)(0x400);
  puVar1[0xb] = iVar2;
  if (iVar2 == 0) {
    (*(code *)puVar1[5])(puVar1[0x5e]);
    (*(code *)puVar1[5])(puVar1);
    return (undefined4 *)0x0;
  }
  puVar1[0xc] = puVar1[0xb] + 0x400;
  if (param_4 == 0) {
    puVar3 = FUN_00409d80(puVar5);
    puVar1[0x55] = puVar3;
    if (puVar3 == (undefined4 *)0x0) {
      (*(code *)puVar1[5])(puVar1[0xb]);
      (*(code *)puVar1[5])(puVar1[0x5e]);
      (*(code *)puVar1[5])(puVar1);
      return (undefined4 *)0x0;
    }
  }
  else {
    puVar1[0x55] = param_4;
  }
  puVar1[0x5a] = 0;
  puVar1[0x58] = 0;
  puVar1[0x48] = 0;
  puVar1[0x71] = 0;
  puVar1[0x70] = 0;
  puVar1[0x1f] = 0;
  puVar1[0x3d] = 0;
  *(undefined1 *)(puVar1 + 0x72) = 0x21;
  *(undefined1 *)(puVar1 + 0x3a) = 0;
  *(undefined1 *)((int)puVar1 + 0xe9) = 0;
  puVar1[0x5f] = 0;
  puVar1[0x60] = 0;
  *(undefined1 *)(puVar1 + 0x61) = 0;
  FUN_0040a2f0(puVar1 + 100,puVar5);
  FUN_0040a2f0(puVar1 + 0x6a,puVar5);
  FUN_00404b20(puVar1,param_1);
  if ((param_1 != (char *)0x0) && (puVar1[0x39] == 0)) {
    FUN_00404c90((int)puVar1);
    return (undefined4 *)0x0;
  }
  if (param_3 == (undefined1 *)0x0) {
    ppuVar4 = FUN_00426170();
    puVar1[0x38] = ppuVar4;
    return puVar1;
  }
  *(undefined1 *)(puVar1 + 0x3a) = 1;
  ppuVar4 = FUN_00426170();
  puVar1[0x38] = ppuVar4;
  *(undefined1 *)(puVar1 + 0x72) = *param_3;
  return puVar1;
}



/* 00404b20 FUN_00404b20 */

void __cdecl FUN_00404b20(undefined4 *param_1,char *param_2)

{
  int iVar1;
  
  param_1[0x42] = FUN_00407350;
  FUN_00426f80(param_1 + 0x3f);
  if (param_2 == (char *)0x0) {
    iVar1 = 0;
  }
  else {
    iVar1 = FUN_0040a420(param_1 + 100,param_2);
  }
  param_1[0x39] = iVar1;
  param_1[0x56] = 0;
  FUN_004261a0(param_1 + 0x25,param_1 + 0x24,(char *)0x0);
  *param_1 = 0;
  param_1[1] = 0;
  param_1[0xd] = 0;
  param_1[0xe] = 0;
  param_1[0xf] = 0;
  param_1[0x10] = 0;
  param_1[0x11] = 0;
  param_1[0x12] = 0;
  param_1[0x13] = 0;
  param_1[0x14] = 0;
  param_1[0x15] = 0;
  param_1[0x16] = 0;
  param_1[0x17] = 0;
  param_1[0x18] = 0;
  param_1[0x19] = 0;
  param_1[0x1a] = 0;
  param_1[0x1b] = 0;
  param_1[0x1c] = 0;
  param_1[0x1d] = param_1;
  param_1[0x1e] = 0;
  param_1[0x20] = 0;
  param_1[0x21] = 0;
  param_1[0x22] = 0;
  param_1[0x23] = 0;
  param_1[6] = param_1[2];
  param_1[7] = param_1[2];
  param_1[9] = 0;
  param_1[10] = 0;
  param_1[0x52] = 0;
  param_1[0x53] = 0;
  param_1[0x4b] = 0;
  param_1[0x4c] = 0;
  param_1[0x4d] = 0;
  param_1[0x4e] = 0;
  param_1[0x4f] = 0;
  param_1[0x50] = 0;
  param_1[0x51] = 0;
  *(undefined1 *)(param_1 + 0x54) = 0;
  *(undefined1 *)((int)param_1 + 0x151) = 0;
  param_1[0x62] = 0;
  param_1[99] = 0;
  param_1[0x43] = 0;
  param_1[0x44] = 0;
  param_1[0x45] = 0;
  param_1[0x46] = 0;
  param_1[0x47] = 0;
  *(undefined1 *)(param_1 + 0x49) = 1;
  param_1[0x4a] = 0;
  param_1[0x57] = 0;
  param_1[0x59] = 0;
  param_1[0x5c] = 0;
  param_1[0x3b] = 0;
  param_1[0x3e] = 0;
  param_1[0x3c] = 0;
  param_1[0x73] = 0;
  param_1[0x74] = 0;
  return;
}



/* 00404c90 FUN_00404c90 */

void __cdecl FUN_00404c90(int param_1)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  int iVar3;
  int iVar4;
  
  if (param_1 == 0) {
    return;
  }
  puVar1 = *(undefined4 **)(param_1 + 0x15c);
  do {
    puVar2 = puVar1;
    if (puVar1 == (undefined4 *)0x0) {
      puVar2 = *(undefined4 **)(param_1 + 0x160);
      if (puVar2 == (undefined4 *)0x0) {
        iVar3 = *(int *)(param_1 + 0x11c);
        do {
          iVar4 = iVar3;
          if (iVar3 == 0) {
            iVar4 = *(int *)(param_1 + 0x120);
            if (iVar4 == 0) {
              FUN_00404dc0(*(int *)(param_1 + 0x168),param_1);
              FUN_00404dc0(*(int *)(param_1 + 0x164),param_1);
              FUN_0040a360((undefined4 *)(param_1 + 400));
              FUN_0040a360((undefined4 *)(param_1 + 0x1a8));
              if (*(int **)(param_1 + 0x154) != (int *)0x0) {
                FUN_00409e30(*(int **)(param_1 + 0x154),*(int *)(param_1 + 0x1cc) == 0,param_1 + 0xc
                            );
              }
              (**(code **)(param_1 + 0x14))(*(undefined4 *)(param_1 + 0x178));
              (**(code **)(param_1 + 0x14))(*(undefined4 *)(param_1 + 0x1c0));
              (**(code **)(param_1 + 0x14))(*(undefined4 *)(param_1 + 8));
              (**(code **)(param_1 + 0x14))(*(undefined4 *)(param_1 + 0x2c));
              (**(code **)(param_1 + 0x14))(*(undefined4 *)(param_1 + 0x17c));
              (**(code **)(param_1 + 0x14))(*(undefined4 *)(param_1 + 0xec));
              if (*(code **)(param_1 + 0xf8) != (code *)0x0) {
                (**(code **)(param_1 + 0xf8))(*(undefined4 *)(param_1 + 0xf0));
              }
              (**(code **)(param_1 + 0x14))(param_1);
              return;
            }
            *(undefined4 *)(param_1 + 0x120) = 0;
          }
          iVar3 = *(int *)(iVar4 + 8);
          (**(code **)(param_1 + 0x14))(iVar4);
        } while( true );
      }
      *(undefined4 *)(param_1 + 0x160) = 0;
    }
    puVar1 = (undefined4 *)*puVar2;
    (**(code **)(param_1 + 0x14))(puVar2[9]);
    FUN_00404dc0(puVar2[0xb],param_1);
    (**(code **)(param_1 + 0x14))(puVar2);
  } while( true );
}



/* 00404dc0 FUN_00404dc0 */

void __cdecl FUN_00404dc0(int param_1,int param_2)

{
  int iVar1;
  
  while (param_1 != 0) {
    iVar1 = *(int *)(param_1 + 4);
    (**(code **)(param_2 + 0x14))(*(undefined4 *)(param_1 + 0x10));
    (**(code **)(param_2 + 0x14))(param_1);
    param_1 = iVar1;
  }
  return;
}



/* 00404df0 FUN_00404df0 */

void __cdecl FUN_00404df0(int *param_1,int param_2)

{
  if (param_1[1] == *param_1) {
    *param_1 = param_2;
    param_1[1] = param_2;
    return;
  }
  *param_1 = param_2;
  return;
}



/* 00404e10 FUN_00404e10 */

void __cdecl FUN_00404e10(int param_1,undefined4 param_2,undefined4 param_3)

{
  *(undefined4 *)(param_1 + 0x34) = param_2;
  *(undefined4 *)(param_1 + 0x38) = param_3;
  return;
}



/* 00404e30 FUN_00404e30 */

void __cdecl FUN_00404e30(int param_1,undefined4 param_2)

{
  *(undefined4 *)(param_1 + 0x3c) = param_2;
  return;
}



/* 00404e40 FUN_00404e40 */

void __cdecl FUN_00404e40(int param_1,undefined4 param_2,undefined4 param_3)

{
  *(undefined4 *)(param_1 + 0x48) = param_2;
  *(undefined4 *)(param_1 + 0x4c) = param_3;
  return;
}



/* 00404e60 FUN_00404e60 */

int __cdecl FUN_00404e60(int param_1,undefined4 *param_2,uint param_3,undefined1 *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  int iVar3;
  uint uVar4;
  uint uVar5;
  undefined1 *puVar6;
  undefined4 *puVar7;
  
  iVar3 = param_1;
  FUN_0040aa66((byte *)s_ohohohtt_00430270);
  uVar4 = param_3;
  puVar2 = param_2;
  if (*(int *)(param_1 + 0x1d0) == 2) {
    *(undefined4 *)(param_1 + 0x10c) = 0x24;
    return 0;
  }
  if (*(int *)(param_1 + 0x1d0) == 3) {
    *(undefined4 *)(param_1 + 0x10c) = 0x21;
    return 0;
  }
  *(undefined4 *)(param_1 + 0x1d0) = 1;
  if (param_3 == 0) {
    *(undefined1 *)(param_1 + 0x1d4) = param_4._0_1_;
    if (param_4 == (undefined1 *)0x0) {
      return 1;
    }
    puVar2 = (undefined4 *)(param_1 + 0x18);
    *(undefined4 *)(param_1 + 0x118) = *(undefined4 *)(param_1 + 0x18);
    *(undefined4 *)(param_1 + 0x28) = *(undefined4 *)(param_1 + 0x1c);
    iVar1 = (**(code **)(param_1 + 0x108))
                      (param_1,*(undefined4 *)(param_1 + 0x18),*(undefined4 *)(param_1 + 0x1c),
                       puVar2);
    *(int *)(param_1 + 0x10c) = iVar1;
    if (iVar1 != 0) {
      *(undefined4 *)(param_1 + 0x114) = *(undefined4 *)(param_1 + 0x110);
      *(undefined1 **)(param_1 + 0x108) = &LAB_00408ab0;
      return 0;
    }
    iVar1 = *(int *)(param_1 + 0x1d0);
    if (iVar1 < 0) {
      return 1;
    }
    if (1 < iVar1) {
      if (iVar1 != 3) {
        return 1;
      }
      (**(code **)(*(int *)(param_1 + 0x90) + 0x30))
                (*(int *)(param_1 + 0x90),*(undefined4 *)(param_1 + 0x118),*puVar2,param_1 + 0x188);
      *(undefined4 *)(param_1 + 0x118) = *puVar2;
      return 2;
    }
LAB_0040502d:
    *(undefined4 *)(iVar3 + 0x1d0) = 2;
    return 1;
  }
  if (*(int *)(param_1 + 0x18) != *(int *)(param_1 + 0x1c)) {
    puVar2 = (undefined4 *)FUN_00405220(param_1,param_3);
    if (puVar2 == (undefined4 *)0x0) {
      return 0;
    }
    puVar7 = param_2;
    for (uVar5 = uVar4 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      *puVar2 = *puVar7;
      puVar7 = puVar7 + 1;
      puVar2 = puVar2 + 1;
    }
    for (uVar5 = uVar4 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
      *(undefined1 *)puVar2 = *(undefined1 *)puVar7;
      puVar7 = (undefined4 *)((int)puVar7 + 1);
      puVar2 = (undefined4 *)((int)puVar2 + 1);
    }
    iVar3 = FUN_00405110(param_1,uVar4,(int)param_4);
    return iVar3;
  }
  *(uint *)(param_1 + 0x24) = *(int *)(param_1 + 0x24) + param_3;
  *(undefined4 **)(param_1 + 0x118) = param_2;
  *(undefined1 *)(param_1 + 0x1d4) = param_4._0_1_;
  *(uint *)(param_1 + 0x28) = (int)param_2 + param_3;
  iVar1 = (**(code **)(param_1 + 0x108))(param_1,param_2,(int)param_2 + param_3,&param_2);
  *(int *)(param_1 + 0x10c) = iVar1;
  if (iVar1 != 0) {
    *(undefined4 *)(param_1 + 0x114) = *(undefined4 *)(param_1 + 0x110);
    *(undefined1 **)(param_1 + 0x108) = &LAB_00408ab0;
    return 0;
  }
  iVar1 = *(int *)(param_1 + 0x1d0);
  if (-1 < iVar1) {
    if (iVar1 < 2) {
      param_1 = 1;
      if (param_4 != (undefined1 *)0x0) goto LAB_0040502d;
    }
    else if (iVar1 == 3) {
      param_1 = 2;
    }
  }
  (**(code **)(*(int *)(iVar3 + 0x90) + 0x30))
            (*(int *)(iVar3 + 0x90),*(undefined4 *)(iVar3 + 0x118),param_2,iVar3 + 0x188);
  puVar6 = (undefined1 *)((int)puVar2 + (uVar4 - (int)param_2));
  param_4 = puVar6;
  if (puVar6 == (undefined1 *)0x0) goto LAB_0040507d;
  iVar1 = *(int *)(iVar3 + 8);
  if (iVar1 == 0) {
LAB_00405012:
    iVar1 = (**(code **)(iVar3 + 0xc))(uVar4 * 2);
LAB_00405047:
    if (iVar1 == 0) {
      *(undefined4 *)(iVar3 + 0x10c) = 1;
      return 0;
    }
    *(int *)(iVar3 + 8) = iVar1;
    *(uint *)(iVar3 + 0x20) = uVar4 * 2 + iVar1;
  }
  else if (*(int *)(iVar3 + 0x20) - iVar1 < (int)puVar6) {
    if (iVar1 == 0) goto LAB_00405012;
    iVar1 = (**(code **)(iVar3 + 0x10))(iVar1,uVar4 * 2);
    goto LAB_00405047;
  }
  puVar2 = *(undefined4 **)(iVar3 + 8);
  for (uVar4 = (uint)puVar6 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
    *puVar2 = *param_2;
    param_2 = param_2 + 1;
    puVar2 = puVar2 + 1;
  }
  for (uVar4 = (uint)puVar6 & 3; uVar4 != 0; uVar4 = uVar4 - 1) {
    *(undefined1 *)puVar2 = *(undefined1 *)param_2;
    param_2 = (undefined4 *)((int)param_2 + 1);
    puVar2 = (undefined4 *)((int)puVar2 + 1);
  }
LAB_0040507d:
  iVar1 = *(int *)(iVar3 + 8);
  *(int *)(iVar3 + 0x18) = iVar1;
  *(int *)(iVar3 + 0x118) = iVar1;
  *(int *)(iVar3 + 0x110) = iVar1;
  *(undefined1 **)(iVar3 + 0x1c) = param_4 + iVar1;
  *(undefined1 **)(iVar3 + 0x28) = param_4 + iVar1;
  *(int *)(iVar3 + 0x114) = iVar1;
  return param_1;
}



/* 00405110 FUN_00405110 */

undefined4 __cdecl FUN_00405110(int param_1,int param_2,int param_3)

{
  undefined4 *puVar1;
  int iVar2;
  undefined4 uVar3;
  
  uVar3 = 1;
  if (*(int *)(param_1 + 0x1d0) == 2) {
    *(undefined4 *)(param_1 + 0x10c) = 0x24;
    return 0;
  }
  if (*(int *)(param_1 + 0x1d0) != 3) {
    puVar1 = (undefined4 *)(param_1 + 0x18);
    iVar2 = *(int *)(param_1 + 0x1c) + param_2;
    *(int *)(param_1 + 0x24) = *(int *)(param_1 + 0x24) + param_2;
    *(undefined4 *)(param_1 + 0x1d0) = 1;
    *(undefined4 *)(param_1 + 0x118) = *(undefined4 *)(param_1 + 0x18);
    *(int *)(param_1 + 0x1c) = iVar2;
    *(int *)(param_1 + 0x28) = iVar2;
    *(char *)(param_1 + 0x1d4) = (char)param_3;
    iVar2 = (**(code **)(param_1 + 0x108))(param_1,*(undefined4 *)(param_1 + 0x18),iVar2,puVar1);
    *(int *)(param_1 + 0x10c) = iVar2;
    if (iVar2 != 0) {
      *(undefined1 **)(param_1 + 0x108) = &LAB_00408ab0;
      *(undefined4 *)(param_1 + 0x114) = *(undefined4 *)(param_1 + 0x110);
      return 0;
    }
    iVar2 = *(int *)(param_1 + 0x1d0);
    if (-1 < iVar2) {
      if (iVar2 < 2) {
        if (param_3 != 0) {
          *(undefined4 *)(param_1 + 0x1d0) = 2;
          return 1;
        }
      }
      else if (iVar2 == 3) {
        uVar3 = 2;
      }
    }
    (**(code **)(*(int *)(param_1 + 0x90) + 0x30))
              (*(int *)(param_1 + 0x90),*(undefined4 *)(param_1 + 0x118),*puVar1,param_1 + 0x188);
    *(undefined4 *)(param_1 + 0x118) = *puVar1;
    return uVar3;
  }
  *(undefined4 *)(param_1 + 0x10c) = 0x21;
  return 0;
}



/* 00405220 FUN_00405220 */

int __cdecl FUN_00405220(int param_1,int param_2)

{
  int iVar1;
  uint uVar2;
  undefined4 *puVar3;
  int iVar4;
  uint uVar5;
  undefined4 *puVar6;
  undefined4 *puVar7;
  
  if (*(int *)(param_1 + 0x1d0) == 2) {
    *(undefined4 *)(param_1 + 0x10c) = 0x24;
    return 0;
  }
  if (*(int *)(param_1 + 0x1d0) == 3) {
    *(undefined4 *)(param_1 + 0x10c) = 0x21;
    return 0;
  }
  iVar4 = *(int *)(param_1 + 0x20);
  if (iVar4 - *(int *)(param_1 + 0x1c) < param_2) {
    puVar3 = *(undefined4 **)(param_1 + 0x18);
    uVar2 = *(int *)(param_1 + 0x1c) - (int)puVar3;
    iVar1 = uVar2 + param_2;
    if (iVar1 <= iVar4 - (int)*(undefined4 **)(param_1 + 8)) {
      FUN_0040b550(*(undefined4 **)(param_1 + 8),puVar3,uVar2);
      iVar4 = *(int *)(param_1 + 0x1c) + (*(int *)(param_1 + 8) - *(int *)(param_1 + 0x18));
      *(int *)(param_1 + 0x18) = *(int *)(param_1 + 8);
      *(int *)(param_1 + 0x1c) = iVar4;
      return iVar4;
    }
    iVar4 = iVar4 - (int)puVar3;
    if (iVar4 == 0) {
      iVar4 = 0x400;
    }
    do {
      iVar4 = iVar4 * 2;
    } while (iVar4 < iVar1);
    puVar3 = (undefined4 *)(**(code **)(param_1 + 0xc))(iVar4);
    if (puVar3 == (undefined4 *)0x0) {
      *(undefined4 *)(param_1 + 0x10c) = 1;
      return 0;
    }
    puVar6 = *(undefined4 **)(param_1 + 0x18);
    *(undefined1 **)(param_1 + 0x20) = (undefined1 *)(iVar4 + (int)puVar3);
    if (puVar6 != (undefined4 *)0x0) {
      uVar5 = *(int *)(param_1 + 0x1c) - (int)puVar6;
      puVar7 = puVar3;
      for (uVar2 = uVar5 >> 2; uVar2 != 0; uVar2 = uVar2 - 1) {
        *puVar7 = *puVar6;
        puVar6 = puVar6 + 1;
        puVar7 = puVar7 + 1;
      }
      for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
        *(undefined1 *)puVar7 = *(undefined1 *)puVar6;
        puVar6 = (undefined4 *)((int)puVar6 + 1);
        puVar7 = (undefined4 *)((int)puVar7 + 1);
      }
      (**(code **)(param_1 + 0x14))(*(undefined4 *)(param_1 + 8));
    }
    iVar4 = *(int *)(param_1 + 0x18);
    *(undefined4 **)(param_1 + 8) = puVar3;
    *(undefined4 **)(param_1 + 0x18) = puVar3;
    *(undefined1 **)(param_1 + 0x1c) =
         (undefined1 *)((int)puVar3 + (*(int *)(param_1 + 0x1c) - iVar4));
  }
  return *(int *)(param_1 + 0x1c);
}



/* 00405330 FUN_00405330 */

int __cdecl FUN_00405330(int *param_1,int param_2,int param_3,int *param_4)

{
  undefined4 in_EAX;
  int iVar1;
  undefined4 uVar2;
  
  iVar1 = FUN_00405420(param_1,1,(int *)param_1[0x24],param_2,param_3,param_4,
                       CONCAT31((int3)((uint)in_EAX >> 8),(char)param_1[0x75] == '\0'));
  if ((iVar1 == 0) && (uVar2 = FUN_00405380((int)param_1), (char)uVar2 == '\0')) {
    return 1;
  }
  return iVar1;
}



/* 00405380 FUN_00405380 */

undefined4 __cdecl FUN_00405380(int param_1)

{
  undefined4 *puVar1;
  undefined4 *in_EAX;
  int iVar2;
  uint uVar3;
  uint uVar4;
  int iVar5;
  undefined4 *puVar6;
  int iVar7;
  undefined4 *puVar8;
  
  for (puVar1 = *(undefined4 **)(param_1 + 0x15c); puVar1 != (undefined4 *)0x0;
      puVar1 = (undefined4 *)*puVar1) {
    iVar2 = puVar1[9];
    iVar7 = puVar1[6] + 1;
    in_EAX = (undefined4 *)(iVar2 + iVar7);
    if ((undefined4 *)puVar1[1] == in_EAX) break;
    iVar5 = iVar7 + puVar1[2];
    if (puVar1[10] - iVar2 < iVar5) {
      iVar2 = (**(code **)(param_1 + 0x10))(iVar2,iVar5);
      if (iVar2 == 0) {
        return 0;
      }
      if (puVar1[3] == puVar1[9]) {
        puVar1[3] = iVar2;
      }
      if (puVar1[4] != 0) {
        puVar1[4] = puVar1[4] + (iVar2 - puVar1[9]);
      }
      puVar1[9] = iVar2;
      puVar1[10] = iVar2 + iVar5;
      in_EAX = (undefined4 *)(iVar2 + iVar7);
    }
    uVar4 = puVar1[2];
    puVar6 = (undefined4 *)puVar1[1];
    puVar8 = in_EAX;
    for (uVar3 = uVar4 >> 2; uVar3 != 0; uVar3 = uVar3 - 1) {
      *puVar8 = *puVar6;
      puVar6 = puVar6 + 1;
      puVar8 = puVar8 + 1;
    }
    for (uVar4 = uVar4 & 3; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined1 *)puVar8 = *(undefined1 *)puVar6;
      puVar6 = (undefined4 *)((int)puVar6 + 1);
      puVar8 = (undefined4 *)((int)puVar8 + 1);
    }
    puVar1[1] = in_EAX;
  }
  return CONCAT31((int3)((uint)in_EAX >> 8),1);
}



/* 00405420 FUN_00405420 */

int __cdecl
FUN_00405420(int *param_1,int param_2,int *param_3,int param_4,int param_5,int *param_6,int param_7)

{
  char *pcVar1;
  code *pcVar2;
  int *piVar3;
  int *piVar4;
  char cVar5;
  byte bVar6;
  undefined4 uVar7;
  byte *pbVar8;
  undefined4 *puVar9;
  int *piVar10;
  char *pcVar11;
  undefined3 extraout_var;
  int iVar12;
  char *pcVar13;
  int iVar14;
  int *piVar15;
  bool bVar16;
  int local_3c;
  int *local_38;
  char *pcStack_34;
  int *piStack_30;
  int *local_2c;
  int *local_28;
  int iStack_24;
  int *piStack_20;
  byte abStack_1c [4];
  int aiStack_18 [6];
  
  piVar4 = param_3;
  piVar3 = param_1;
  local_28 = (int *)param_1[0x55];
  if (param_3 == (int *)param_1[0x24]) {
    local_38 = param_1 + 0x44;
    local_2c = param_1 + 0x45;
  }
  else {
    local_38 = (int *)param_1[0x47];
    local_2c = local_38 + 1;
  }
  *local_38 = param_4;
  do {
    piVar10 = local_2c;
    iVar12 = param_5;
    local_3c = param_4;
    uVar7 = (*(code *)piVar4[1])(piVar4,param_4,param_5,&local_3c);
    *piVar10 = local_3c;
    switch(uVar7) {
    case 0:
      *local_38 = local_3c;
      return 4;
    case 1:
    case 2:
      piVar10 = (int *)piVar3[0x58];
      if (piVar10 == (int *)0x0) {
        piVar10 = (int *)(*(code *)piVar3[3])(0x30);
        if (piVar10 == (int *)0x0) {
          return 1;
        }
        iVar12 = (*(code *)piVar3[3])(0x20);
        piVar10[9] = iVar12;
        if (iVar12 == 0) {
          (*(code *)piVar3[5])(piVar10);
          return 1;
        }
        piVar10[10] = iVar12 + 0x20;
      }
      else {
        piVar3[0x58] = *piVar10;
      }
      piStack_20 = piVar10 + 0xb;
      *piStack_20 = 0;
      *piVar10 = piVar3[0x57];
      piVar3[0x57] = (int)piVar10;
      piVar10[4] = 0;
      piVar10[5] = 0;
      iVar12 = piVar4[0x10];
      piVar10[1] = iVar12 + param_4;
      iVar12 = (*(code *)piVar4[7])(piVar4,iVar12 + param_4);
      piVar10[2] = iVar12;
      piVar3[0x4a] = piVar3[0x4a] + 1;
      param_3 = (int *)piVar10[1];
      pcStack_34 = (char *)piVar10[9];
      piVar15 = (int *)((int)param_3 + piVar10[2]);
      (*(code *)piVar4[0xe])(piVar4,&param_3,piVar15,&pcStack_34,piVar10[10] + -1);
      iVar12 = piVar10[9];
      piStack_30 = (int *)(pcStack_34 + -iVar12);
      while (param_3 != piVar15) {
        iStack_24 = (piVar10[10] - iVar12) * 2;
        iVar12 = (*(code *)piVar3[4])(iVar12,iStack_24);
        if (iVar12 == 0) {
          return 1;
        }
        piVar10[9] = iVar12;
        pcStack_34 = (char *)((int)piStack_30 + iVar12);
        piVar10[10] = iVar12 + iStack_24;
        (*(code *)piVar4[0xe])(piVar4,&param_3,piVar15,&pcStack_34,piVar10[10] + -1);
        iVar12 = piVar10[9];
        piStack_30 = (int *)(pcStack_34 + -iVar12);
      }
      piVar15 = piVar10 + 3;
      piVar10[6] = (int)piStack_30;
      *piVar15 = piVar10[9];
      *pcStack_34 = '\0';
      iVar12 = FUN_00405ff0((int)piVar3,(char *)piVar4,param_4,piVar15,piStack_20);
      if (iVar12 != 0) {
        return iVar12;
      }
      if ((code *)piVar3[0xd] == (code *)0x0) {
        if (piVar3[0x14] != 0) {
          FUN_00409320(piVar3,(int)piVar4,param_4,local_3c);
        }
        FUN_0040a310(piVar3 + 100);
      }
      else {
        (*(code *)piVar3[0xd])(piVar3[1],*piVar15,piVar3[0x5e]);
        FUN_0040a310(piVar3 + 100);
      }
      break;
    case 3:
    case 4:
      iVar14 = piVar4[0x10] + param_4;
      piStack_30 = (int *)0x0;
      param_3 = (int *)CONCAT31(param_3._1_3_,1);
      iVar12 = (*(code *)piVar4[7])(piVar4,iVar14);
      aiStack_18[0] = FUN_0040a4c0(piVar3 + 100,(int)piVar4,iVar14,iVar12 + iVar14);
      if (aiStack_18[0] == 0) {
        return 1;
      }
      piVar3[0x68] = piVar3[0x67];
      iVar12 = FUN_00405ff0((int)piVar3,(char *)piVar4,param_4,aiStack_18,(int *)&piStack_30);
      if (iVar12 != 0) {
        return iVar12;
      }
      piVar3[0x68] = piVar3[0x67];
      if ((code *)piVar3[0xd] == (code *)0x0) {
        cVar5 = (char)param_3;
      }
      else {
        (*(code *)piVar3[0xd])(piVar3[1],aiStack_18[0],piVar3[0x5e]);
        cVar5 = '\0';
      }
      if (piVar3[0xe] == 0) {
        if ((cVar5 != '\0') && (piVar3[0x14] != 0)) {
          FUN_00409320(piVar3,(int)piVar4,param_4,local_3c);
        }
      }
      else {
        if (piVar3[0xd] != 0) {
          *local_38 = *local_2c;
        }
        (*(code *)piVar3[0xe])(piVar3[1],aiStack_18[0]);
      }
      FUN_0040a310(piVar3 + 100);
      piVar10 = piStack_30;
      while (piVar10 != (int *)0x0) {
        piStack_30 = piVar10;
        if ((code *)piVar3[0x1a] != (code *)0x0) {
          (*(code *)piVar3[0x1a])(piVar3[1],*(undefined4 *)*piVar10);
        }
        piVar15 = (int *)piStack_30[1];
        piVar10[1] = piVar3[0x5a];
        piVar3[0x5a] = (int)piVar10;
        *(int *)(*piVar10 + 4) = piVar10[2];
        piVar10 = piVar15;
      }
      piStack_30 = (int *)0x0;
      if (piVar3[0x4a] == 0) {
LAB_00405e4a:
        iVar12 = FUN_00408700(piVar3,local_3c,param_5,param_6);
        return iVar12;
      }
      piStack_30 = (int *)0x0;
      break;
    case 5:
      if (piVar3[0x4a] == param_2) {
        return 0xd;
      }
      piVar10 = (int *)piVar3[0x57];
      piVar3[0x57] = *piVar10;
      *piVar10 = piVar3[0x58];
      piVar3[0x58] = (int)piVar10;
      piStack_30 = (int *)(param_4 + piVar4[0x10] * 2);
      param_3 = piVar10;
      iVar12 = (*(code *)piVar4[7])(piVar4,piStack_30);
      if (iVar12 != piVar10[2]) {
LAB_00405e6a:
        *local_38 = (int)piStack_30;
        return 7;
      }
      bVar16 = true;
      pcVar13 = (char *)piVar10[1];
      piVar10 = piStack_30;
      do {
        if (iVar12 == 0) break;
        iVar12 = iVar12 + -1;
        bVar16 = *pcVar13 == (char)*piVar10;
        pcVar13 = pcVar13 + 1;
        piVar10 = (int *)((int)piVar10 + 1);
      } while (bVar16);
      if (!bVar16) goto LAB_00405e6a;
      piVar3[0x4a] = piVar3[0x4a] + -1;
      if (piVar3[0xe] == 0) {
        if (piVar3[0x14] != 0) {
          FUN_00409320(piVar3,(int)piVar4,param_4,local_3c);
        }
      }
      else {
        pcVar13 = (char *)param_3[4];
        if (((char)piVar3[0x3a] != '\0') && (pcVar13 != (char *)0x0)) {
          pcVar11 = (char *)(param_3[7] + param_3[3]);
          cVar5 = *pcVar13;
          while (cVar5 != '\0') {
            *pcVar11 = cVar5;
            pcVar1 = pcVar13 + 1;
            pcVar11 = pcVar11 + 1;
            pcVar13 = pcVar13 + 1;
            cVar5 = *pcVar1;
          }
          pcVar13 = (char *)param_3[5];
          if ((*(char *)((int)piVar3 + 0xe9) != '\0') && (pcVar13 != (char *)0x0)) {
            *pcVar11 = (char)piVar3[0x72];
            cVar5 = *pcVar13;
            while (pcVar11 = pcVar11 + 1, cVar5 != '\0') {
              *pcVar11 = cVar5;
              pcVar1 = pcVar13 + 1;
              pcVar13 = pcVar13 + 1;
              cVar5 = *pcVar1;
            }
          }
          *pcVar11 = '\0';
        }
        (*(code *)piVar3[0xe])(piVar3[1],param_3[3]);
      }
      piVar10 = (int *)param_3[0xb];
      piVar15 = param_3;
      while (piVar10 != (int *)0x0) {
        if ((code *)piVar3[0x1a] != (code *)0x0) {
          (*(code *)piVar3[0x1a])(piVar3[1],*(undefined4 *)*piVar10);
          piVar15 = param_3;
        }
        piVar15[0xb] = *(int *)(piVar15[0xb] + 4);
        piVar10[1] = piVar3[0x5a];
        piVar3[0x5a] = (int)piVar10;
        *(int *)(*piVar10 + 4) = piVar10[2];
        piVar10 = (int *)piVar15[0xb];
      }
      if (piVar3[0x4a] == 0) goto LAB_00405e4a;
      break;
    case 6:
      pcVar2 = (code *)piVar3[0xf];
      if (pcVar2 == (code *)0x0) {
        iVar12 = piVar3[0x14];
joined_r0x00405b17:
        if (iVar12 == 0) break;
LAB_00405c8b:
        FUN_00409320(piVar3,(int)piVar4,param_4,local_3c);
      }
      else if ((char)piVar4[0x11] == '\0') {
        param_3 = (int *)piVar3[0xb];
        (*(code *)piVar4[0xe])(piVar4,&param_4,local_3c,&param_3,piVar3[0xc]);
        *local_2c = param_4;
        (*pcVar2)(piVar3[1],piVar3[0xb],(int)param_3 - piVar3[0xb]);
        piVar10 = local_38;
        if (param_4 != local_3c) {
          do {
            *piVar10 = param_4;
            param_3 = (int *)piVar3[0xb];
            (*(code *)piVar4[0xe])(piVar4,&param_4,local_3c,&param_3,piVar3[0xc]);
            *local_2c = param_4;
            (*pcVar2)(piVar3[1],piVar3[0xb],(int)param_3 - piVar3[0xb]);
          } while (param_4 != local_3c);
        }
      }
      else {
        (*pcVar2)(piVar3[1],param_4,local_3c - param_4);
      }
      break;
    case 7:
      if ((code *)piVar3[0xf] == (code *)0x0) {
        iVar12 = piVar3[0x14];
        goto joined_r0x00405b17;
      }
      param_3 = (int *)CONCAT31(param_3._1_3_,10);
      (*(code *)piVar3[0xf])(piVar3[1],&param_3,1);
      break;
    case 8:
      if ((code *)piVar3[0x12] == (code *)0x0) {
        if (piVar3[0x14] != 0) {
          FUN_00409320(piVar3,(int)piVar4,param_4,local_3c);
        }
      }
      else {
        (*(code *)piVar3[0x12])(piVar3[1]);
      }
      bVar6 = FUN_00406c90(piVar3,(int)piVar4,&local_3c,iVar12,param_6,(char)param_7);
      if (CONCAT31(extraout_var,bVar6) != 0) {
        return CONCAT31(extraout_var,bVar6);
      }
      if (local_3c == 0) {
        piVar3[0x42] = (int)&LAB_00406bb0;
        return 0;
      }
      break;
    case 9:
      cVar5 = (*(code *)piVar4[0xb])(piVar4,piVar4[0x10] + param_4,local_3c - piVar4[0x10]);
      param_1 = (int *)CONCAT31(param_1._1_3_,cVar5);
      if (cVar5 == '\0') {
        pbVar8 = (byte *)FUN_0040a4c0(local_28 + 0x14,(int)piVar4,piVar4[0x10] + param_4,
                                      local_3c - piVar4[0x10]);
        if (pbVar8 == (byte *)0x0) {
          return 1;
        }
        puVar9 = (undefined4 *)FUN_00409ee0(local_28,pbVar8,0);
        local_28[0x17] = local_28[0x18];
        if ((*(char *)((int)local_28 + 0x81) == '\0') || (*(char *)((int)local_28 + 0x82) != '\0'))
        {
          if (puVar9 == (undefined4 *)0x0) {
            return 0xb;
          }
          if (*(char *)((int)puVar9 + 0x22) == '\0') {
            return 0x18;
          }
        }
        else if (puVar9 == (undefined4 *)0x0) {
          if ((code *)piVar3[0x1e] == (code *)0x0) goto LAB_00405628;
          (*(code *)piVar3[0x1e])(piVar3[1],pbVar8,0);
          break;
        }
        if (*(char *)(puVar9 + 8) != '\0') {
          return 0xc;
        }
        if (puVar9[7] != 0) {
          return 0xf;
        }
        if (puVar9[1] == 0) {
          if (piVar3[0x1c] == 0) goto LAB_00405628;
          *(undefined1 *)(puVar9 + 8) = 1;
          iVar12 = FUN_00409740((int)piVar3);
          *(undefined1 *)(puVar9 + 8) = 0;
          if (iVar12 == 0) {
            return 1;
          }
          iVar12 = (*(code *)piVar3[0x1c])(piVar3[0x1d],iVar12,puVar9[5],puVar9[4],puVar9[6]);
          if (iVar12 == 0) {
            return 0x15;
          }
          piVar3[0x67] = piVar3[0x68];
        }
        else if ((char)piVar3[0x49] == '\0') {
          if ((code *)piVar3[0x1e] == (code *)0x0) {
            iVar12 = piVar3[0x14];
            goto joined_r0x00405c83;
          }
          (*(code *)piVar3[0x1e])(piVar3[1],*puVar9,0);
        }
        else {
          iVar12 = FUN_004088f0((int)piVar3,(int)puVar9,0);
          if (iVar12 != 0) {
            return iVar12;
          }
        }
      }
      else {
        if ((code *)piVar3[0xf] == (code *)0x0) {
LAB_00405628:
          iVar12 = piVar3[0x14];
          goto joined_r0x00405c83;
        }
        (*(code *)piVar3[0xf])(piVar3[1],&param_1,1);
      }
      break;
    case 10:
      iVar12 = (*(code *)piVar4[10])(piVar4,param_4);
      if (iVar12 < 0) {
        return 0xe;
      }
      if (piVar3[0xf] == 0) {
        iVar12 = piVar3[0x14];
        goto joined_r0x00405c83;
      }
      uVar7 = FUN_00425860(iVar12,abStack_1c);
      (*(code *)piVar3[0xf])(piVar3[1],abStack_1c,uVar7);
      break;
    case 0xb:
      iVar12 = FUN_00409180(piVar3,(int)piVar4,param_4,local_3c);
      goto joined_r0x00405c76;
    case 0xc:
      return 0x11;
    case 0xd:
      iVar12 = FUN_00409290(piVar3,(int)piVar4,param_4,local_3c);
joined_r0x00405c76:
      if (iVar12 == 0) {
        return 1;
      }
      break;
    case 0xfffffffb:
      if ((char)param_7 != '\0') {
        *param_6 = param_4;
        return 0;
      }
      if ((code *)piVar3[0xf] == (code *)0x0) {
        if (piVar3[0x14] != 0) {
          FUN_00409320(piVar3,(int)piVar4,param_4,iVar12);
        }
      }
      else if ((char)piVar4[0x11] == '\0') {
        param_7 = piVar3[0xb];
        (*(code *)piVar4[0xe])(piVar4,&param_4,iVar12,&param_7,piVar3[0xc]);
        (*(code *)piVar3[0xf])(piVar3[1],piVar3[0xb],param_7 - piVar3[0xb]);
      }
      else {
        (*(code *)piVar3[0xf])(piVar3[1],param_4,iVar12 - param_4);
      }
      if (param_2 == 0) {
        *local_38 = iVar12;
        return 3;
      }
      if (piVar3[0x4a] != param_2) {
        *local_38 = iVar12;
        return 0xd;
      }
      *param_6 = iVar12;
      return 0;
    case 0xfffffffc:
      if ((char)param_7 != '\0') {
        *param_6 = param_4;
        return 0;
      }
      if (param_2 < 1) {
        return 3;
      }
      if (piVar3[0x4a] != param_2) {
        return 0xd;
      }
      goto LAB_00405cce;
    case 0xfffffffd:
      if ((char)param_7 == '\0') {
        *piVar10 = iVar12;
        if ((code *)piVar3[0xf] == (code *)0x0) {
          if (piVar3[0x14] != 0) {
            FUN_00409320(piVar3,(int)piVar4,param_4,iVar12);
          }
        }
        else {
          param_7 = CONCAT31(param_7._1_3_,10);
          (*(code *)piVar3[0xf])(piVar3[1],&param_7,1);
        }
        if (param_2 == 0) {
          return 3;
        }
        if (piVar3[0x4a] != param_2) {
          return 0xd;
        }
        *param_6 = iVar12;
        return 0;
      }
LAB_00405cce:
      *param_6 = param_4;
      return 0;
    case 0xfffffffe:
      if ((char)param_7 != '\0') {
        *param_6 = param_4;
        return 0;
      }
      return 6;
    case 0xffffffff:
      if ((char)param_7 != '\0') {
        *param_6 = param_4;
        return 0;
      }
      return 5;
    default:
      iVar12 = piVar3[0x14];
joined_r0x00405c83:
      if (iVar12 != 0) goto LAB_00405c8b;
    }
    param_4 = local_3c;
    *local_38 = local_3c;
    if (piVar3[0x74] == 2) {
      return 0x23;
    }
    if (piVar3[0x74] == 3) {
      *param_6 = local_3c;
      return 0;
    }
  } while( true );
}



/* 00405ff0 FUN_00405ff0 */

int __cdecl FUN_00405ff0(int param_1,char *param_2,int param_3,undefined4 *param_4,int *param_5)

{
  byte bVar1;
  int iVar2;
  byte bVar3;
  byte bVar4;
  byte *pbVar5;
  int iVar6;
  int iVar7;
  int iVar8;
  int *piVar9;
  int iVar10;
  int *piVar11;
  undefined4 uVar12;
  int *piVar13;
  int iVar14;
  undefined4 *puVar15;
  char *pcVar16;
  int *piVar17;
  uint uVar18;
  char cVar19;
  int *piVar20;
  undefined4 *puVar21;
  char *pcVar22;
  uint uVar23;
  undefined4 *puVar24;
  uint uVar25;
  undefined4 *puVar26;
  int local_28;
  undefined4 *local_24;
  int *piStack_1c;
  undefined4 uStack_14;
  uint uStack_8;
  
  iVar2 = param_1;
  iVar14 = *(int *)(param_1 + 0x154);
  param_1 = 0;
  local_28 = 0;
  local_24 = (undefined4 *)FUN_00409ee0((int *)(iVar14 + 0x14),(byte *)*param_4,0);
  if (local_24 == (undefined4 *)0x0) {
    pbVar5 = (byte *)FUN_0040a420((int *)(iVar14 + 0x50),(char *)*param_4);
    if (pbVar5 == (byte *)0x0) {
      return 1;
    }
    local_24 = (undefined4 *)FUN_00409ee0((int *)(iVar14 + 0x14),pbVar5,0x18);
    if (local_24 == (undefined4 *)0x0) {
      return 1;
    }
    if ((*(char *)(iVar2 + 0xe8) != '\0') && (iVar6 = FUN_004094b0(iVar2,local_24), iVar6 == 0)) {
      return 1;
    }
  }
  iVar6 = local_24[3];
  iVar7 = (**(code **)(param_2 + 0x24))
                    (param_2,param_3,*(undefined4 *)(iVar2 + 0x16c),*(undefined4 *)(iVar2 + 0x178));
  iVar10 = *(int *)(iVar2 + 0x16c);
  if (iVar10 < iVar7 + iVar6) {
    iVar8 = iVar7 + iVar6 + 0x10;
    *(int *)(iVar2 + 0x16c) = iVar8;
    iVar8 = (**(code **)(iVar2 + 0x10))(*(undefined4 *)(iVar2 + 0x178),iVar8 * 0x10);
    if (iVar8 == 0) {
      return 1;
    }
    *(int *)(iVar2 + 0x178) = iVar8;
    if (iVar10 < iVar7) {
      (**(code **)(param_2 + 0x24))(param_2,param_3,iVar7,iVar8);
    }
  }
  piVar13 = *(int **)(iVar2 + 0x178);
  param_3 = 0;
  if (0 < iVar7) {
    piStack_1c = (int *)0x0;
    param_1 = 0;
    piVar17 = piVar13;
    do {
      iVar10 = *(int *)(*(int *)(iVar2 + 0x178) + (int)piStack_1c);
      iVar8 = (**(code **)(param_2 + 0x1c))
                        (param_2,*(undefined4 *)(*(int *)(iVar2 + 0x178) + (int)piStack_1c));
      piVar9 = FUN_00409580(iVar2,(int)param_2,iVar10,iVar8 + iVar10);
      if (piVar9 == (int *)0x0) {
        return 1;
      }
      if (*(char *)(*piVar9 + -1) != '\0') {
        if (param_2 == *(char **)(iVar2 + 0x90)) {
          *(undefined4 *)(iVar2 + 0x110) = *(undefined4 *)(param_3 * 0x10 + *(int *)(iVar2 + 0x178))
          ;
        }
        return 8;
      }
      *(undefined1 *)(*piVar9 + -1) = 1;
      *piVar17 = *piVar9;
      iVar10 = *(int *)(iVar2 + 0x178) + (int)piStack_1c;
      piVar11 = piVar17 + 1;
      if (*(char *)(iVar10 + 0xc) == '\0') {
        uStack_14 = CONCAT31(uStack_14._1_3_,1);
        if (((char)piVar9[2] != '\0') && (iVar8 = 0, 0 < iVar6)) {
          piVar20 = (int *)local_24[5];
          do {
            if (piVar9 == (int *)*piVar20) {
              uStack_14 = CONCAT31(uStack_14._1_3_,(char)((int *)local_24[5])[iVar8 * 3 + 1]);
              break;
            }
            iVar8 = iVar8 + 1;
            piVar20 = piVar20 + 3;
          } while (iVar8 < iVar6);
        }
        iVar10 = FUN_00408ac0(iVar2,(int)param_2,uStack_14,*(int *)(iVar10 + 4),
                              *(undefined4 *)(iVar10 + 8),(int *)(iVar2 + 400));
        if (iVar10 != 0) {
          return iVar10;
        }
        *piVar11 = *(int *)(iVar2 + 0x1a0);
        *(undefined4 *)(iVar2 + 0x1a0) = *(undefined4 *)(iVar2 + 0x19c);
      }
      else {
        iVar10 = FUN_0040a4c0((int *)(iVar2 + 400),(int)param_2,*(int *)(iVar10 + 4),
                              *(int *)(iVar10 + 8));
        *piVar11 = iVar10;
        if (iVar10 == 0) {
          return 1;
        }
        *(undefined4 *)(iVar2 + 0x1a0) = *(undefined4 *)(iVar2 + 0x19c);
      }
      if ((int *)piVar9[1] == (int *)0x0) {
        param_1 = param_1 + 2;
        piVar17 = piVar17 + 2;
      }
      else if (*(char *)((int)piVar9 + 9) == '\0') {
        param_1 = param_1 + 2;
        piVar17 = piVar17 + 2;
        local_28 = local_28 + 1;
        *(undefined1 *)(*piVar9 + -1) = 2;
      }
      else {
        iVar10 = FUN_00406970(iVar2,(int *)piVar9[1],(int)piVar9,(char *)*piVar11,param_5);
        if (iVar10 != 0) {
          return iVar10;
        }
      }
      param_3 = param_3 + 1;
      piStack_1c = (int *)((int)piStack_1c + 0x10);
    } while (param_3 < iVar7);
  }
  *(int *)(iVar2 + 0x170) = param_1;
  if (((int *)local_24[2] == (int *)0x0) ||
     (iVar10 = *(int *)local_24[2], *(char *)(iVar10 + -1) == '\0')) {
    *(undefined4 *)(iVar2 + 0x174) = 0xffffffff;
  }
  else {
    iVar7 = 0;
    piVar17 = piVar13;
    if (0 < param_1) {
      do {
        if (*piVar17 == iVar10) {
          *(int *)(iVar2 + 0x174) = iVar7;
          break;
        }
        iVar7 = iVar7 + 2;
        piVar17 = piVar17 + 2;
      } while (iVar7 < param_1);
    }
  }
  param_3 = 0;
  if (0 < iVar6) {
    param_2 = (char *)0x0;
    piVar17 = piVar13 + param_1;
    do {
      piVar11 = (int *)(param_2 + local_24[5]);
      piVar9 = (int *)*piVar11;
      iVar10 = *piVar9;
      if ((*(char *)(iVar10 + -1) == '\0') && ((char *)piVar11[2] != (char *)0x0)) {
        if ((int *)piVar9[1] == (int *)0x0) {
          *(undefined1 *)(iVar10 + -1) = 1;
        }
        else {
          if (*(char *)((int)piVar9 + 9) != '\0') {
            iVar10 = FUN_00406970(iVar2,(int *)piVar9[1],(int)piVar9,(char *)piVar11[2],param_5);
            if (iVar10 != 0) {
              return iVar10;
            }
            goto LAB_004063cd;
          }
          *(undefined1 *)(iVar10 + -1) = 2;
          local_28 = local_28 + 1;
        }
        *piVar17 = *(int *)*piVar11;
        piVar17[1] = piVar11[2];
        param_1 = param_1 + 2;
        piVar17 = piVar17 + 2;
      }
LAB_004063cd:
      param_3 = param_3 + 1;
      param_2 = param_2 + 0xc;
    } while (param_3 < iVar6);
  }
  iVar6 = 0;
  piVar13[param_1] = 0;
  param_3 = 0;
  if (local_28 == 0) {
LAB_00406796:
    if (iVar6 < param_1) {
      piVar13 = piVar13 + iVar6;
      uVar25 = (param_1 - iVar6) + 1U >> 1;
      do {
        iVar6 = *piVar13;
        piVar13 = piVar13 + 2;
        uVar25 = uVar25 - 1;
        *(undefined1 *)(iVar6 + -1) = 0;
      } while (uVar25 != 0);
    }
    goto LAB_004067b0;
  }
  bVar4 = *(byte *)(iVar2 + 0x184);
  iVar6 = *(int *)(iVar2 + 0x180);
  param_2 = (char *)(1 << (bVar4 & 0x1f));
  if (local_28 * 2 >> (bVar4 & 0x1f) == 0) {
    if (iVar6 == 0) goto LAB_004064a9;
  }
  else {
    *(byte *)(iVar2 + 0x184) = bVar4 + 1;
    bVar3 = bVar4 + 1;
    while (bVar1 = bVar3, local_28 >> (bVar4 & 0x1f) != 0) {
      *(byte *)(iVar2 + 0x184) = bVar1 + 1;
      bVar3 = bVar1 + 1;
      bVar4 = bVar1;
    }
    if (bVar1 < 3) {
      *(undefined1 *)(iVar2 + 0x184) = 3;
    }
    param_2 = (char *)(1 << (*(byte *)(iVar2 + 0x184) & 0x1f));
    iVar6 = (**(code **)(iVar2 + 0x10))(*(undefined4 *)(iVar2 + 0x17c),(int)param_2 * 0xc);
    if (iVar6 == 0) {
      return 1;
    }
    *(int *)(iVar2 + 0x17c) = iVar6;
LAB_004064a9:
    iVar6 = -1;
    if (param_2 != (char *)0x0) {
      iVar10 = (int)param_2 * 0xc;
      pcVar22 = param_2;
      do {
        iVar10 = iVar10 + -0xc;
        pcVar22 = pcVar22 + -1;
        *(undefined4 *)(iVar10 + *(int *)(iVar2 + 0x17c)) = 0xffffffff;
      } while (pcVar22 != (char *)0x0);
    }
  }
  iVar6 = iVar6 + -1;
  *(int *)(iVar2 + 0x180) = iVar6;
  piStack_1c = piVar13;
  if (0 < param_1) {
    do {
      pbVar5 = (byte *)*piStack_1c;
      if (pbVar5[-1] == 2) {
        uVar25 = 0;
        pbVar5[-1] = 0;
        iVar10 = FUN_00409ee0((int *)(iVar14 + 0x28),pbVar5,0);
        puVar21 = *(undefined4 **)(*(int *)(iVar10 + 4) + 4);
        if (puVar21 == (undefined4 *)0x0) {
          return 0x1b;
        }
        iVar10 = 0;
        if (0 < (int)puVar21[5]) {
          do {
            bVar4 = *(byte *)(puVar21[4] + iVar10);
            if ((*(int *)(iVar2 + 0x19c) == *(int *)(iVar2 + 0x198)) &&
               (uVar12 = FUN_0040a510((int *)(iVar2 + 400)), (char)uVar12 == '\0')) {
              return 1;
            }
            **(byte **)(iVar2 + 0x19c) = bVar4;
            *(int *)(iVar2 + 0x19c) = *(int *)(iVar2 + 0x19c) + 1;
            uVar25 = uVar25 * 0xf4243 ^ (uint)bVar4;
            iVar10 = iVar10 + 1;
          } while (iVar10 < (int)puVar21[5]);
        }
        bVar4 = *pbVar5;
        while (pbVar5 = pbVar5 + 1, bVar4 != 0x3a) {
          bVar4 = *pbVar5;
        }
        do {
          bVar4 = *pbVar5;
          if ((*(int *)(iVar2 + 0x19c) == *(int *)(iVar2 + 0x198)) &&
             (uVar12 = FUN_0040a510((int *)(iVar2 + 400)), (char)uVar12 == '\0')) {
            return 1;
          }
          **(byte **)(iVar2 + 0x19c) = *pbVar5;
          *(int *)(iVar2 + 0x19c) = *(int *)(iVar2 + 0x19c) + 1;
          bVar3 = *pbVar5;
          uVar25 = uVar25 * 0xf4243 ^ (uint)bVar4;
          pbVar5 = pbVar5 + 1;
        } while (bVar3 != 0);
        uStack_8 = 0;
        uVar23 = (uint)(param_2 + -1) & uVar25;
        piVar17 = (int *)(*(int *)(iVar2 + 0x17c) + uVar23 * 0xc);
        bVar4 = 0;
        iVar10 = *piVar17;
        while (iVar10 == iVar6) {
          if (uVar25 == piVar17[1]) {
            pcVar22 = *(char **)(iVar2 + 0x1a0);
            cVar19 = *pcVar22;
            if (cVar19 == *(char *)piVar17[2]) {
              iVar10 = piVar17[2] - (int)pcVar22;
              do {
                if (cVar19 == '\0') break;
                cVar19 = pcVar22[1];
                pcVar22 = pcVar22 + 1;
              } while (cVar19 == pcVar22[iVar10]);
            }
            if (*pcVar22 == '\0') {
              return 8;
            }
          }
          if (bVar4 == 0) {
            bVar3 = (byte)((~(uint)(param_2 + -1) & uVar25) >>
                          (*(char *)(iVar2 + 0x184) - 1U & 0x1f)) &
                    (byte)((uint)(param_2 + -1) >> 2);
            bVar4 = bVar3 | 1;
            uStack_8 = bVar3 | 1;
          }
          if ((int)uVar23 < (int)uStack_8) {
            iVar10 = (int)param_2 - uStack_8;
          }
          else {
            iVar10 = -uStack_8;
          }
          uVar23 = uVar23 + iVar10;
          piVar17 = (int *)(*(int *)(iVar2 + 0x17c) + uVar23 * 0xc);
          iVar10 = *(int *)(*(int *)(iVar2 + 0x17c) + uVar23 * 0xc);
        }
        if (*(char *)(iVar2 + 0xe9) != '\0') {
          *(undefined1 *)(*(int *)(iVar2 + 0x19c) + -1) = *(undefined1 *)(iVar2 + 0x1c8);
          pcVar22 = *(char **)*puVar21;
          do {
            if ((*(int *)(iVar2 + 0x19c) == *(int *)(iVar2 + 0x198)) &&
               (uVar12 = FUN_0040a510((int *)(iVar2 + 400)), (char)uVar12 == '\0')) {
              return 1;
            }
            **(char **)(iVar2 + 0x19c) = *pcVar22;
            *(int *)(iVar2 + 0x19c) = *(int *)(iVar2 + 0x19c) + 1;
            cVar19 = *pcVar22;
            pcVar22 = pcVar22 + 1;
          } while (cVar19 != '\0');
        }
        iVar10 = *(int *)(iVar2 + 0x1a0);
        *(undefined4 *)(iVar2 + 0x1a0) = *(undefined4 *)(iVar2 + 0x19c);
        *piStack_1c = iVar10;
        iVar7 = uVar23 * 0xc;
        *(int *)(*(int *)(iVar2 + 0x17c) + iVar7) = iVar6;
        *(uint *)(*(int *)(iVar2 + 0x17c) + 4 + iVar7) = uVar25;
        *(int *)(*(int *)(iVar2 + 0x17c) + 8 + iVar7) = iVar10;
        local_28 = local_28 + -1;
        if (local_28 == 0) {
          iVar6 = param_3 + 2;
          goto LAB_00406796;
        }
      }
      else {
        pbVar5[-1] = 0;
      }
      param_3 = param_3 + 2;
      piStack_1c = piStack_1c + 2;
    } while (param_3 < param_1);
  }
LAB_004067b0:
  for (iVar6 = *param_5; iVar6 != 0; iVar6 = *(int *)(iVar6 + 4)) {
    *(undefined1 *)(**(int **)(iVar6 + 0xc) + -1) = 0;
  }
  if (*(char *)(iVar2 + 0xe8) != '\0') {
    if (local_24[1] == 0) {
      puVar21 = *(undefined4 **)(iVar14 + 0x88);
      if (puVar21 == (undefined4 *)0x0) {
        return 0;
      }
      param_2 = (char *)*param_4;
    }
    else {
      puVar21 = *(undefined4 **)(local_24[1] + 4);
      if (puVar21 == (undefined4 *)0x0) {
        return 0x1b;
      }
      param_2 = (char *)*param_4;
      cVar19 = *param_2;
      while (param_2 = param_2 + 1, cVar19 != ':') {
        cVar19 = *param_2;
      }
    }
    param_5 = (int *)0x0;
    if ((*(char *)(iVar2 + 0xe9) != '\0') && (pcVar22 = *(char **)*puVar21, pcVar22 != (char *)0x0))
    {
      param_5 = (int *)0x1;
      cVar19 = *pcVar22;
      while (cVar19 != '\0') {
        pcVar16 = (char *)((int)param_5 + (int)pcVar22);
        param_5 = (int *)((int)param_5 + 1);
        cVar19 = *pcVar16;
      }
    }
    param_4[1] = param_2;
    param_4[4] = puVar21[5];
    uVar12 = *(undefined4 *)*puVar21;
    param_4[5] = param_5;
    param_4[2] = uVar12;
    uVar25 = 1;
    cVar19 = *param_2;
    while (cVar19 != '\0') {
      pcVar22 = param_2 + uVar25;
      uVar25 = uVar25 + 1;
      cVar19 = *pcVar22;
    }
    iVar14 = (int)param_5 + uVar25 + puVar21[5];
    if ((int)puVar21[6] < iVar14) {
      iVar14 = iVar14 + 0x18;
      puVar15 = (undefined4 *)(**(code **)(iVar2 + 0xc))(iVar14);
      if (puVar15 == (undefined4 *)0x0) {
        return 1;
      }
      uVar23 = puVar21[5];
      puVar21[6] = iVar14;
      puVar24 = (undefined4 *)puVar21[4];
      puVar26 = puVar15;
      for (uVar18 = uVar23 >> 2; uVar18 != 0; uVar18 = uVar18 - 1) {
        *puVar26 = *puVar24;
        puVar24 = puVar24 + 1;
        puVar26 = puVar26 + 1;
      }
      for (uVar23 = uVar23 & 3; uVar23 != 0; uVar23 = uVar23 - 1) {
        *(undefined1 *)puVar26 = *(undefined1 *)puVar24;
        puVar24 = (undefined4 *)((int)puVar24 + 1);
        puVar26 = (undefined4 *)((int)puVar26 + 1);
      }
      for (puVar24 = *(undefined4 **)(iVar2 + 0x15c); puVar24 != (undefined4 *)0x0;
          puVar24 = (undefined4 *)*puVar24) {
        if (puVar24[3] == puVar21[4]) {
          puVar24[3] = puVar15;
        }
      }
      (**(code **)(iVar2 + 0x14))(puVar21[4]);
      puVar21[4] = puVar15;
    }
    pcVar16 = (char *)(puVar21[5] + puVar21[4]);
    pcVar22 = pcVar16;
    for (uVar23 = uVar25 >> 2; uVar23 != 0; uVar23 = uVar23 - 1) {
      *(undefined4 *)pcVar22 = *(undefined4 *)param_2;
      param_2 = param_2 + 4;
      pcVar22 = pcVar22 + 4;
    }
    for (uVar23 = uVar25 & 3; uVar23 != 0; uVar23 = uVar23 - 1) {
      *pcVar22 = *param_2;
      param_2 = param_2 + 1;
      pcVar22 = pcVar22 + 1;
    }
    if (param_5 != (int *)0x0) {
      pcVar16[uVar25 - 1] = *(char *)(iVar2 + 0x1c8);
      pcVar22 = *(char **)*puVar21;
      pcVar16 = pcVar16 + uVar25;
      for (uVar23 = (uint)param_5 >> 2; uVar23 != 0; uVar23 = uVar23 - 1) {
        *(undefined4 *)pcVar16 = *(undefined4 *)pcVar22;
        pcVar22 = pcVar22 + 4;
        pcVar16 = pcVar16 + 4;
      }
      for (uVar25 = (uint)param_5 & 3; uVar25 != 0; uVar25 = uVar25 - 1) {
        *pcVar16 = *pcVar22;
        pcVar22 = pcVar22 + 1;
        pcVar16 = pcVar16 + 1;
      }
    }
    *param_4 = puVar21[4];
  }
  return 0;
}



/* 00406970 FUN_00406970 */

int __cdecl FUN_00406970(int param_1,int *param_2,int param_3,char *param_4,undefined4 *param_5)

{
  bool bVar1;
  int iVar2;
  undefined4 *puVar3;
  uint uVar4;
  bool bVar5;
  uint uVar6;
  char *pcVar7;
  char *pcVar8;
  bool bVar9;
  
  bVar9 = false;
  bVar5 = true;
  if ((*param_4 == '\0') && (*param_2 != 0)) {
    return 0x1c;
  }
  pcVar7 = (char *)*param_2;
  if ((((pcVar7 != (char *)0x0) && (*pcVar7 == 'x')) && (pcVar7[1] == 'm')) && (pcVar7[2] == 'l')) {
    if (((pcVar7[3] == 'n') && (pcVar7[4] == 's')) && (pcVar7[5] == '\0')) {
      return 0x27;
    }
    bVar9 = pcVar7[3] == '\0';
  }
  bVar1 = true;
  uVar6 = 0;
  if (*param_4 == '\0') {
LAB_00406a3a:
    if (uVar6 == 0x24) {
      bVar5 = true;
      goto LAB_00406a44;
    }
  }
  else {
    do {
      if ((bVar5) &&
         ((0x24 < (int)uVar6 || (param_4[uVar6] != "http://www.w3.org/XML/1998/namespace"[uVar6]))))
      {
        bVar5 = false;
      }
      if (((bVar9 == false) && (bVar1)) &&
         ((0x1d < (int)uVar6 || (param_4[uVar6] != "http://www.w3.org/2000/xmlns/"[uVar6])))) {
        bVar1 = false;
      }
      iVar2 = uVar6 + 1;
      uVar6 = uVar6 + 1;
    } while (param_4[iVar2] != '\0');
    if (bVar5) goto LAB_00406a3a;
  }
  bVar5 = false;
LAB_00406a44:
  if ((bVar1) && (uVar6 == 0x1d)) {
    bVar1 = true;
  }
  else {
    bVar1 = false;
  }
  if (bVar9 != bVar5) {
    return (-(uint)(bVar9 != false) & 0xfffffffe) + 0x28;
  }
  if (bVar1) {
    return 0x28;
  }
  if (*(char *)(param_1 + 0x1c8) != '\0') {
    uVar6 = uVar6 + 1;
  }
  puVar3 = *(undefined4 **)(param_1 + 0x168);
  if (puVar3 == (undefined4 *)0x0) {
    puVar3 = (undefined4 *)(**(code **)(param_1 + 0xc))(0x1c);
    if (puVar3 == (undefined4 *)0x0) {
      return 1;
    }
    iVar2 = (**(code **)(param_1 + 0xc))(uVar6 + 0x18);
    puVar3[4] = iVar2;
    if (iVar2 == 0) {
      (**(code **)(param_1 + 0x14))(puVar3);
      return 1;
    }
    puVar3[6] = uVar6 + 0x18;
  }
  else {
    if ((int)puVar3[6] < (int)uVar6) {
      iVar2 = (**(code **)(param_1 + 0x10))(puVar3[4],uVar6 + 0x18);
      if (iVar2 == 0) {
        return 1;
      }
      puVar3[6] = uVar6 + 0x18;
      puVar3[4] = iVar2;
    }
    *(undefined4 *)(param_1 + 0x168) = puVar3[1];
  }
  puVar3[5] = uVar6;
  pcVar7 = param_4;
  pcVar8 = (char *)puVar3[4];
  for (uVar4 = uVar6 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
    *(undefined4 *)pcVar8 = *(undefined4 *)pcVar7;
    pcVar7 = pcVar7 + 4;
    pcVar8 = pcVar8 + 4;
  }
  for (uVar4 = uVar6 & 3; uVar4 != 0; uVar4 = uVar4 - 1) {
    *pcVar8 = *pcVar7;
    pcVar7 = pcVar7 + 1;
    pcVar8 = pcVar8 + 1;
  }
  if (*(char *)(param_1 + 0x1c8) != '\0') {
    *(char *)(puVar3[4] + -1 + uVar6) = *(char *)(param_1 + 0x1c8);
  }
  *puVar3 = param_2;
  puVar3[3] = param_3;
  puVar3[2] = param_2[1];
  if ((*param_4 == '\0') && (param_2 == (int *)(*(int *)(param_1 + 0x154) + 0x84))) {
    param_2[1] = 0;
  }
  else {
    param_2[1] = (int)puVar3;
  }
  puVar3[1] = *param_5;
  *param_5 = puVar3;
  if ((param_3 != 0) && (*(code **)(param_1 + 100) != (code *)0x0)) {
    (**(code **)(param_1 + 100))
              (*(undefined4 *)(param_1 + 4),*param_2,-(uint)(param_2[1] != 0) & (uint)param_4);
  }
  return 0;
}



/* 00406c40 FUN_00406c40 */

int __cdecl FUN_00406c40(int *param_1,int param_2,int param_3,int *param_4)

{
  undefined4 in_EAX;
  int iVar1;
  undefined4 uVar2;
  
  iVar1 = FUN_00405420(param_1,0,(int *)param_1[0x24],param_2,param_3,param_4,
                       CONCAT31((int3)((uint)in_EAX >> 8),(char)param_1[0x75] == '\0'));
  if ((iVar1 == 0) && (uVar2 = FUN_00405380((int)param_1), (char)uVar2 == '\0')) {
    return 1;
  }
  return iVar1;
}



/* 00406c90 FUN_00406c90 */

byte __cdecl
FUN_00406c90(int *param_1,int param_2,int *param_3,undefined4 param_4,int *param_5,char param_6)

{
  int iVar1;
  code *pcVar2;
  uint uVar3;
  int *piVar4;
  int iVar5;
  undefined4 uVar6;
  int *piVar7;
  int *local_8;
  int *local_4;
  
  iVar5 = param_2;
  piVar4 = param_1;
  local_8 = (int *)*param_3;
  if (param_2 == param_1[0x24]) {
    local_4 = param_1 + 0x44;
    piVar7 = param_1 + 0x45;
    *local_4 = (int)local_8;
  }
  else {
    local_4 = (int *)param_1[0x47];
    piVar7 = local_4 + 1;
  }
  *local_4 = (int)local_8;
  *param_3 = 0;
  uVar6 = (**(code **)(param_2 + 8))(param_2,local_8,param_4,&param_1);
  *piVar7 = (int)param_1;
  do {
    switch(uVar6) {
    case 0:
      *local_4 = (int)param_1;
      return 4;
    case 6:
      pcVar2 = (code *)piVar4[0xf];
      if (pcVar2 == (code *)0x0) {
        iVar1 = piVar4[0x14];
joined_r0x00406d3d:
        if (iVar1 != 0) {
          FUN_00409320(piVar4,iVar5,(int)local_8,(int)param_1);
        }
      }
      else if (*(char *)(iVar5 + 0x44) == '\0') {
        param_2 = piVar4[0xb];
        (**(code **)(iVar5 + 0x38))(iVar5,&local_8,param_1,&param_2,piVar4[0xc]);
        *piVar7 = (int)param_1;
        (*pcVar2)(piVar4[1],piVar4[0xb],param_2 - piVar4[0xb]);
        if (local_8 != param_1) {
          do {
            *local_4 = (int)local_8;
            param_2 = piVar4[0xb];
            (**(code **)(iVar5 + 0x38))(iVar5,&local_8,param_1,&param_2,piVar4[0xc]);
            *piVar7 = (int)param_1;
            (*pcVar2)(piVar4[1],piVar4[0xb],param_2 - piVar4[0xb]);
          } while (local_8 != param_1);
        }
      }
      else {
        (*pcVar2)(piVar4[1],local_8,(int)param_1 - (int)local_8);
      }
      break;
    case 7:
      if ((code *)piVar4[0xf] == (code *)0x0) {
        iVar1 = piVar4[0x14];
        goto joined_r0x00406d3d;
      }
      uVar3 = (uint)param_2 >> 8;
      param_2 = CONCAT31((int3)uVar3,10);
      (*(code *)piVar4[0xf])(piVar4[1],&param_2,1);
      break;
    case 0x28:
      if ((code *)piVar4[0x13] == (code *)0x0) {
        if (piVar4[0x14] != 0) {
          FUN_00409320(piVar4,iVar5,(int)local_8,(int)param_1);
        }
      }
      else {
        (*(code *)piVar4[0x13])(piVar4[1]);
      }
      *param_3 = (int)param_1;
      *param_5 = (int)param_1;
      return (piVar4[0x74] != 2) - 1U & 0x23;
    case 0xfffffffc:
    case 0xffffffff:
      if (param_6 == '\0') {
        return 0x14;
      }
      *param_5 = (int)local_8;
      return 0;
    default:
      *local_4 = (int)param_1;
      return 0x17;
    case 0xfffffffe:
      if (param_6 == '\0') {
        return 6;
      }
      *param_5 = (int)local_8;
      return 0;
    }
    *local_4 = (int)param_1;
    if (piVar4[0x74] == 2) {
      return 0x23;
    }
    if (piVar4[0x74] == 3) {
      *param_5 = (int)param_1;
      return 0;
    }
    local_8 = param_1;
    uVar6 = (**(code **)(iVar5 + 8))(iVar5,param_1,param_4,&param_1);
    *piVar7 = (int)param_1;
  } while( true );
}



/* 00406fa0 FUN_00406fa0 */

undefined4 __cdecl FUN_00406fa0(int param_1)

{
  int iVar1;
  undefined4 uVar2;
  
  iVar1 = FUN_004261a0((undefined4 *)(param_1 + 0x94),(undefined4 *)(param_1 + 0x90),
                       *(char **)(param_1 + 0xe4));
  if (iVar1 != 0) {
    return 0;
  }
  uVar2 = FUN_00407230(param_1,*(undefined4 *)(param_1 + 0xe4));
  return uVar2;
}



/* 00406fe0 FUN_00406fe0 */

int __cdecl FUN_00406fe0(int *param_1,int param_2,int param_3,int param_4)

{
  int *piVar1;
  int iVar2;
  int iVar3;
  int iVar4;
  int local_14;
  int local_10;
  int local_c;
  int local_8;
  int local_4;
  
  iVar3 = param_4;
  piVar1 = param_1 + 0x44;
  iVar4 = 0;
  local_14 = 0;
  local_4 = 0;
  local_8 = 0;
  local_c = 0;
  local_10 = -1;
  iVar2 = FUN_00426780(param_2,param_1[0x24],param_3,param_4,piVar1,&local_8,&param_4,&local_14,
                       &local_4,&local_10);
  if (iVar2 == 0) {
    return (param_2 != 0) + 0x1e;
  }
  if ((param_2 == 0) && (local_10 == 1)) {
    *(undefined1 *)(param_1[0x55] + 0x82) = 1;
  }
  if (param_1[0x23] == 0) {
    iVar2 = local_c;
    if (param_1[0x14] != 0) {
      FUN_00409320(param_1,param_1[0x24],param_3,iVar3);
      iVar2 = local_c;
    }
  }
  else {
    if (local_14 != 0) {
      iVar3 = (**(code **)(param_1[0x24] + 0x1c))(param_1[0x24],local_14);
      iVar4 = FUN_0040a4c0(param_1 + 0x6a,param_1[0x24],local_14,iVar3 + local_14);
      if (iVar4 == 0) {
        return 1;
      }
      param_1[0x6e] = param_1[0x6d];
    }
    iVar2 = local_c;
    if ((local_8 != 0) &&
       (iVar2 = FUN_0040a4c0(param_1 + 0x6a,param_1[0x24],local_8,
                             param_4 - *(int *)(param_1[0x24] + 0x40)), iVar2 == 0)) {
      return 1;
    }
    (*(code *)param_1[0x23])(param_1[1],iVar2,iVar4,local_10);
  }
  if (param_1[0x39] == 0) {
    if (local_4 == 0) {
      if (local_14 != 0) {
        if (iVar4 == 0) {
          iVar3 = (**(code **)(param_1[0x24] + 0x1c))(param_1[0x24],local_14);
          iVar4 = FUN_0040a4c0(param_1 + 0x6a,param_1[0x24],local_14,iVar3 + local_14);
          if (iVar4 == 0) {
            return 1;
          }
        }
        iVar3 = FUN_00407230((int)param_1,iVar4);
        FUN_0040a310(param_1 + 0x6a);
        if (iVar3 == 0x12) {
          *piVar1 = local_14;
        }
        return iVar3;
      }
    }
    else {
      if (*(int *)(local_4 + 0x40) != *(int *)(param_1[0x24] + 0x40)) {
        *piVar1 = local_14;
        return 0x13;
      }
      param_1[0x24] = local_4;
    }
  }
  if ((iVar4 != 0) || (iVar2 != 0)) {
    FUN_0040a310(param_1 + 0x6a);
  }
  return 0;
}



/* 00407230 FUN_00407230 */

undefined4 __cdecl FUN_00407230(int param_1,undefined4 param_2)

{
  code *pcVar1;
  undefined4 uVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_40c [256];
  undefined4 local_c;
  int local_8;
  code *local_4;
  
  pcVar1 = *(code **)(param_1 + 0x7c);
  if (pcVar1 != (code *)0x0) {
    puVar4 = local_40c;
    for (iVar3 = 0x100; iVar3 != 0; iVar3 = iVar3 + -1) {
      *puVar4 = 0xffffffff;
      puVar4 = puVar4 + 1;
    }
    local_8 = 0;
    local_c = 0;
    local_4 = (code *)0x0;
    iVar3 = (*pcVar1)(*(undefined4 *)(param_1 + 0xf4),param_2,local_40c);
    if (iVar3 != 0) {
      uVar2 = FUN_00425970();
      iVar3 = (**(code **)(param_1 + 0xc))(uVar2);
      *(int *)(param_1 + 0xec) = iVar3;
      if (iVar3 == 0) {
        if (local_4 != (code *)0x0) {
          (*local_4)(local_c);
        }
        return 1;
      }
      iVar3 = FUN_004259a0(iVar3,(int)local_40c,local_8,local_c);
      if (iVar3 != 0) {
        *(undefined4 *)(param_1 + 0xf0) = local_c;
        *(code **)(param_1 + 0xf8) = local_4;
        *(int *)(param_1 + 0x90) = iVar3;
        return 0;
      }
    }
    if (local_4 != (code *)0x0) {
      (*local_4)(local_c);
    }
  }
  return 0x12;
}



/* 00407350 FUN_00407350 */

void __cdecl FUN_00407350(int *param_1,int param_2,int param_3,int *param_4)

{
  int iVar1;
  
  iVar1 = FUN_00406fa0((int)param_1);
  if (iVar1 == 0) {
    param_1[0x42] = (int)FUN_00407390;
    FUN_00407390(param_1,param_2,param_3,param_4);
  }
  return;
}



/* 00407390 FUN_00407390 */

void __cdecl FUN_00407390(int *param_1,int param_2,int param_3,int *param_4)

{
  int iVar1;
  int iVar2;
  int iVar3;
  
  iVar2 = param_3;
  iVar1 = param_2;
  iVar3 = (**(code **)param_1[0x24])((undefined4 *)param_1[0x24],param_2,param_3,&param_2);
  FUN_004073e0(param_1,(undefined4 *)param_1[0x24],iVar1,iVar2,iVar3,param_2,param_4,
               (char)param_1[0x75] == '\0');
  return;
}



/* 004073e0 FUN_004073e0 */

/* WARNING: Type propagation algorithm not settling */

int __cdecl
FUN_004073e0(int *param_1,undefined4 *param_2,int param_3,int param_4,int param_5,int param_6,
            int *param_7,char param_8)

{
  char *pcVar1;
  char cVar2;
  int *piVar3;
  uint uVar4;
  bool bVar5;
  undefined1 uVar6;
  int iVar7;
  char *pcVar8;
  undefined4 *puVar9;
  byte *pbVar10;
  int iVar11;
  int iVar12;
  int *piVar13;
  int *piVar14;
  undefined4 uVar15;
  bool bVar16;
  undefined4 uVar17;
  undefined4 uVar18;
  undefined4 uVar19;
  int *local_8;
  
  piVar3 = (int *)param_1[0x55];
  if (param_2 == (undefined4 *)param_1[0x24]) {
    piVar14 = param_1 + 0x44;
    local_8 = param_1 + 0x45;
  }
  else {
    piVar14 = (int *)param_1[0x47];
    local_8 = piVar14 + 1;
  }
  do {
    *piVar14 = param_3;
    bVar16 = true;
    bVar5 = true;
    *local_8 = param_6;
    if (param_5 < 1) {
      if ((param_8 != '\0') && (param_5 != 0)) {
        *param_7 = param_3;
        return 0;
      }
      switch(param_5) {
      case 0:
        *piVar14 = param_6;
        return 4;
      case -4:
        return 3;
      default:
        param_5 = -param_5;
        param_6 = param_4;
        break;
      case -2:
        return 6;
      case -1:
        return 5;
      }
    }
    iVar7 = (*(code *)param_1[0x3f])(param_1 + 0x3f,param_5,param_3,param_6,param_2);
    switch(iVar7) {
    case 0:
      if (param_5 != 0xe) break;
      goto LAB_00407539;
    case 1:
      iVar7 = FUN_00406fe0(param_1,0,param_3,param_6);
      if (iVar7 != 0) {
        return iVar7;
      }
      param_2 = (undefined4 *)param_1[0x24];
      goto LAB_00407539;
    case 2:
      param_1[0x42] = (int)FUN_00406c40;
      iVar7 = FUN_00406c40(param_1,param_3,param_4,param_7);
      return iVar7;
    case 3:
      iVar7 = param_1[0x15];
joined_r0x004084e5:
      if (iVar7 == 0) break;
      goto LAB_00407539;
    case 4:
      if (param_1[0x15] != 0) {
        iVar7 = FUN_0040a4c0(param_1 + 100,(int)param_2,param_3,param_6);
        param_1[0x4c] = iVar7;
        if (iVar7 == 0) {
          return 1;
        }
        param_1[0x4e] = 0;
        param_1[0x68] = param_1[0x67];
        bVar16 = false;
      }
      param_1[0x4d] = 0;
      goto LAB_00407512;
    case 5:
      *(undefined1 *)((int)piVar3 + 0x81) = 1;
      bVar16 = bVar5;
      if (param_1[0x15] != 0) {
        iVar7 = FUN_0040a4c0(param_1 + 100,(int)param_2,param_2[0x10] + param_3,
                             param_6 - param_2[0x10]);
        param_1[0x4d] = iVar7;
        if (iVar7 == 0) {
          return 1;
        }
        bVar16 = false;
        param_1[0x68] = param_1[0x67];
      }
      if (((*(char *)((int)piVar3 + 0x82) == '\0') && ((code *)param_1[0x1b] != (code *)0x0)) &&
         (iVar7 = (*(code *)param_1[0x1b])(param_1[1]), iVar7 == 0)) {
        return 0x16;
      }
      goto LAB_00407512;
    case 6:
      *(undefined1 *)((int)piVar3 + 0x81) = 1;
      if (param_1[0x15] == 0) goto switchD_00407492_caseD_e;
      iVar7 = (*(code *)param_2[0xd])(param_2,param_3,param_6,piVar14);
      if (iVar7 == 0) {
        return 0x20;
      }
      pcVar8 = (char *)FUN_0040a4c0(param_1 + 100,(int)param_2,param_2[0x10] + param_3,
                                    param_6 - param_2[0x10]);
      param_1[0x4e] = (int)pcVar8;
      if (pcVar8 == (char *)0x0) {
        return 1;
      }
      FUN_00409d30(pcVar8);
      param_1[0x68] = param_1[0x67];
      bVar16 = false;
      goto LAB_00407647;
    case 7:
      if ((code *)param_1[0x15] == (code *)0x0) break;
      (*(code *)param_1[0x15])(param_1[1],param_1[0x4c],param_1[0x4d],param_1[0x4e],1);
      param_1[0x4c] = 0;
      FUN_0040a310(param_1 + 100);
      goto LAB_00407539;
    case 8:
      iVar7 = param_1[0x4c];
      if (iVar7 != 0) {
        (*(code *)param_1[0x15])(param_1[1],iVar7,param_1[0x4d],param_1[0x4e],0);
        FUN_0040a310(param_1 + 100);
      }
      bVar16 = iVar7 == 0;
      if ((code *)param_1[0x16] == (code *)0x0) goto LAB_00407512;
      (*(code *)param_1[0x16])(param_1[1]);
      goto LAB_00407539;
    case 9:
      iVar7 = (*(code *)param_2[0xb])(param_2,param_3,param_6);
      if (iVar7 == 0) {
        if ((char)piVar3[0x20] == '\0') {
          piVar3[0x17] = piVar3[0x18];
          goto switchD_00407492_caseD_a;
        }
        pbVar10 = (byte *)FUN_0040a4c0(piVar3 + 0x14,(int)param_2,param_3,param_6);
        if (pbVar10 == (byte *)0x0) {
          return 1;
        }
        piVar13 = (int *)FUN_00409ee0(piVar3,pbVar10,0x24);
        param_1[0x4b] = (int)piVar13;
        if (piVar13 == (int *)0x0) {
          return 1;
        }
        if ((byte *)*piVar13 == pbVar10) {
          piVar3[0x18] = piVar3[0x17];
          *(undefined4 *)(param_1[0x4b] + 0x18) = 0;
          *(undefined1 *)(param_1[0x4b] + 0x21) = 0;
          if ((param_1[0x73] == 0) && (param_1[0x47] == 0)) {
            uVar6 = 1;
          }
          else {
            uVar6 = 0;
          }
          *(undefined1 *)(param_1[0x4b] + 0x22) = uVar6;
          iVar7 = param_1[0x22];
          goto joined_r0x004077ea;
        }
        piVar3[0x17] = piVar3[0x18];
        param_1[0x4b] = 0;
      }
      else {
        param_1[0x4b] = 0;
      }
      break;
    case 10:
switchD_00407492_caseD_a:
      param_1[0x4b] = 0;
      break;
    case 0xb:
      if ((char)piVar3[0x20] != '\0') {
        iVar7 = param_1[0x22];
        goto joined_r0x004084e5;
      }
      break;
    case 0xc:
      if ((char)piVar3[0x20] != '\0') {
        iVar7 = FUN_00408f60((int)param_1,(int)param_2,param_2[0x10] + param_3,
                             param_6 - param_2[0x10]);
        if (param_1[0x4b] == 0) {
          piVar3[0x1d] = piVar3[0x1e];
          bVar16 = bVar5;
        }
        else {
          *(int *)(param_1[0x4b] + 4) = piVar3[0x1e];
          *(int *)(param_1[0x4b] + 8) = piVar3[0x1d] - piVar3[0x1e];
          piVar3[0x1e] = piVar3[0x1d];
          bVar16 = bVar5;
          if (param_1[0x22] != 0) {
            *local_8 = param_3;
            puVar9 = (undefined4 *)param_1[0x4b];
            (*(code *)param_1[0x22])
                      (param_1[1],*puVar9,*(undefined1 *)((int)puVar9 + 0x21),puVar9[1],puVar9[2],
                       param_1[0x56],0,0,0);
            bVar16 = false;
          }
        }
        if (iVar7 != 0) {
          return iVar7;
        }
        goto LAB_00407512;
      }
      break;
    case 0xd:
      if (((char)piVar3[0x20] != '\0') && (param_1[0x4b] != 0)) {
        iVar7 = FUN_0040a4c0(piVar3 + 0x14,(int)param_2,param_2[0x10] + param_3,
                             param_6 - param_2[0x10]);
        *(int *)(param_1[0x4b] + 0x10) = iVar7;
        if (*(int *)(param_1[0x4b] + 0x10) == 0) {
          return 1;
        }
        *(int *)(param_1[0x4b] + 0x14) = param_1[0x56];
        piVar3[0x18] = piVar3[0x17];
        iVar7 = param_1[0x22];
        goto joined_r0x004077ea;
      }
      break;
    case 0xe:
switchD_00407492_caseD_e:
      iVar7 = (*(code *)param_2[0xd])(param_2,param_3,param_6,piVar14);
      bVar16 = bVar5;
      if (iVar7 == 0) {
        return 0x20;
      }
LAB_00407647:
      if (((char)piVar3[0x20] != '\0') && (param_1[0x4b] != 0)) {
        pcVar8 = (char *)FUN_0040a4c0(piVar3 + 0x14,(int)param_2,param_2[0x10] + param_3,
                                      param_6 - param_2[0x10]);
        if (pcVar8 == (char *)0x0) {
          return 1;
        }
        FUN_00409d30(pcVar8);
        *(char **)(param_1[0x4b] + 0x18) = pcVar8;
        piVar3[0x18] = piVar3[0x17];
        if (param_1[0x22] != 0) goto LAB_00407539;
      }
      goto LAB_00407512;
    case 0xf:
      if ((((char)piVar3[0x20] == '\0') || (param_1[0x4b] == 0)) || (param_1[0x22] == 0)) break;
      uVar15 = 0;
      *local_8 = param_3;
      puVar9 = (undefined4 *)param_1[0x4b];
      uVar19 = puVar9[6];
      uVar18 = puVar9[4];
      uVar17 = puVar9[5];
      uVar6 = *(undefined1 *)((int)puVar9 + 0x21);
LAB_00407db0:
      (*(code *)param_1[0x22])(param_1[1],*puVar9,uVar6,0,0,uVar17,uVar18,uVar19,uVar15);
      goto LAB_00407539;
    case 0x10:
      if (((char)piVar3[0x20] != '\0') && (param_1[0x4b] != 0)) {
        iVar7 = FUN_0040a4c0(piVar3 + 0x14,(int)param_2,param_3,param_6);
        *(int *)(param_1[0x4b] + 0x1c) = iVar7;
        if (*(int *)(param_1[0x4b] + 0x1c) == 0) {
          return 1;
        }
        piVar3[0x18] = piVar3[0x17];
        if (param_1[0x17] != 0) {
          *local_8 = param_3;
          puVar9 = (undefined4 *)param_1[0x4b];
          (*(code *)param_1[0x17])(param_1[1],*puVar9,puVar9[5],puVar9[4],puVar9[6],puVar9[7]);
          goto LAB_00407539;
        }
        if (param_1[0x22] != 0) {
          *local_8 = param_3;
          puVar9 = (undefined4 *)param_1[0x4b];
          uVar15 = puVar9[7];
          uVar19 = puVar9[6];
          uVar18 = puVar9[4];
          uVar17 = puVar9[5];
          uVar6 = 0;
          goto LAB_00407db0;
        }
      }
      break;
    case 0x11:
      iVar7 = param_1[0x18];
      goto joined_r0x004077ea;
    case 0x12:
      param_1[0x51] = 0;
      param_1[0x50] = 0;
      if (param_1[0x18] == 0) break;
      iVar7 = FUN_0040a4c0(param_1 + 100,(int)param_2,param_3,param_6);
      param_1[0x50] = iVar7;
      if (iVar7 == 0) {
        return 1;
      }
      param_1[0x68] = param_1[0x67];
      goto LAB_00407539;
    case 0x13:
      bVar16 = bVar5;
      if ((param_1[0x50] != 0) && (param_1[0x18] != 0)) {
        iVar11 = FUN_0040a4c0(param_1 + 100,(int)param_2,param_2[0x10] + param_3,
                              param_6 - param_2[0x10]);
        if (iVar11 == 0) {
          return 1;
        }
        *local_8 = param_3;
        iVar7 = param_1[0x51];
LAB_00407fe1:
        (*(code *)param_1[0x18])(param_1[1],param_1[0x50],param_1[0x56],iVar11,iVar7);
        bVar16 = false;
      }
      goto LAB_00407ffe;
    case 0x14:
      bVar16 = bVar5;
      if ((param_1[0x51] != 0) && (param_1[0x18] != 0)) {
        *local_8 = param_3;
        iVar7 = param_1[0x51];
        iVar11 = 0;
        goto LAB_00407fe1;
      }
LAB_00407ffe:
      FUN_0040a310(param_1 + 100);
      goto LAB_00407512;
    case 0x15:
      iVar7 = (*(code *)param_2[0xd])(param_2,param_3,param_6,piVar14);
      if (iVar7 == 0) {
        return 0x20;
      }
      if (param_1[0x50] == 0) break;
      pcVar8 = (char *)FUN_0040a4c0(param_1 + 100,(int)param_2,param_2[0x10] + param_3,
                                    param_6 - param_2[0x10]);
      if (pcVar8 == (char *)0x0) {
        return 1;
      }
      FUN_00409d30(pcVar8);
      param_1[0x51] = (int)pcVar8;
      param_1[0x68] = param_1[0x67];
      goto LAB_00407539;
    case 0x16:
      piVar13 = FUN_00409580((int)param_1,(int)param_2,param_3,param_6);
      param_1[0x53] = (int)piVar13;
      if (piVar13 == (int *)0x0) {
        return 1;
      }
      *(undefined1 *)(param_1 + 0x54) = 0;
      param_1[0x4f] = 0;
      *(undefined1 *)((int)param_1 + 0x151) = 0;
      goto switchD_00407492_caseD_21;
    case 0x17:
      *(undefined1 *)(param_1 + 0x54) = 1;
      param_1[0x4f] = (int)"CDATA";
      goto switchD_00407492_caseD_21;
    case 0x18:
      *(undefined1 *)((int)param_1 + 0x151) = 1;
      param_1[0x4f] = (int)&DAT_0042c26c;
      goto switchD_00407492_caseD_21;
    case 0x19:
      param_1[0x4f] = (int)"IDREF";
      goto switchD_00407492_caseD_21;
    case 0x1a:
      param_1[0x4f] = (int)"IDREFS";
      goto switchD_00407492_caseD_21;
    case 0x1b:
      param_1[0x4f] = (int)"ENTITY";
      goto switchD_00407492_caseD_21;
    case 0x1c:
      param_1[0x4f] = (int)"ENTITIES";
      goto switchD_00407492_caseD_21;
    case 0x1d:
      param_1[0x4f] = (int)"NMTOKEN";
      goto switchD_00407492_caseD_21;
    case 0x1e:
      param_1[0x4f] = (int)"NMTOKENS";
      goto switchD_00407492_caseD_21;
    case 0x1f:
    case 0x20:
      if (((char)piVar3[0x20] == '\0') || (param_1[0x21] == 0)) break;
      if (param_1[0x4f] == 0) {
        pcVar8 = "NOTATION(";
        if (iVar7 != 0x20) {
          pcVar8 = "(";
        }
      }
      else {
        pcVar8 = "|";
      }
      iVar7 = FUN_0040a470(param_1 + 100,pcVar8);
      if (iVar7 == 0) {
        return 1;
      }
      iVar7 = FUN_0040a3a0(param_1 + 100,(int)param_2,param_3,param_6);
      if (iVar7 == 0) {
        return 1;
      }
      param_1[0x4f] = param_1[0x68];
      goto LAB_00407539;
    case 0x21:
      goto switchD_00407492_caseD_21;
    case 0x22:
      piVar13 = FUN_0040a950((int)param_1,(int)param_2,param_3,param_6);
      param_1[0x52] = (int)piVar13;
      if (piVar13 == (int *)0x0) {
        return 1;
      }
switchD_00407492_caseD_21:
      if ((char)piVar3[0x20] != '\0') {
        iVar7 = param_1[0x21];
joined_r0x004077ea:
        if (iVar7 != 0) goto LAB_00407539;
      }
      break;
    case 0x23:
    case 0x24:
      if ((char)piVar3[0x20] != '\0') {
        iVar11 = FUN_004093d0(param_1[0x52],param_1[0x53],(char)param_1[0x54],
                              *(char *)((int)param_1 + 0x151),0,(int)param_1);
        if (iVar11 == 0) {
          return 1;
        }
        if ((param_1[0x21] != 0) && (pcVar8 = (char *)param_1[0x4f], pcVar8 != (char *)0x0)) {
          if ((*pcVar8 == '(') || ((*pcVar8 == 'N' && (pcVar8[1] == 'O')))) {
            if ((param_1[0x67] == param_1[0x66]) &&
               (uVar15 = FUN_0040a510(param_1 + 100), (char)uVar15 == '\0')) {
              return 1;
            }
            *(undefined1 *)param_1[0x67] = 0x29;
            iVar11 = param_1[0x67];
            param_1[0x67] = iVar11 + 1;
            if ((iVar11 + 1 == param_1[0x66]) &&
               (uVar15 = FUN_0040a510(param_1 + 100), (char)uVar15 == '\0')) {
              return 1;
            }
            *(undefined1 *)param_1[0x67] = 0;
            iVar11 = param_1[0x67];
            param_1[0x67] = iVar11 + 1;
            param_1[0x4f] = param_1[0x68];
            param_1[0x68] = iVar11 + 1;
          }
          bVar16 = iVar7 == 0x24;
          *local_8 = param_3;
          iVar11 = 0;
LAB_00407ace:
          (*(code *)param_1[0x21])
                    (param_1[1],*(undefined4 *)param_1[0x52],*(undefined4 *)param_1[0x53],
                     param_1[0x4f],iVar11,bVar16);
          FUN_0040a310(param_1 + 100);
          goto LAB_00407539;
        }
      }
      break;
    case 0x25:
    case 0x26:
      if ((char)piVar3[0x20] != '\0') {
        iVar11 = param_2[0x10] + param_3;
        iVar11 = FUN_00408ac0((int)param_1,(int)param_2,
                              CONCAT31((int3)((uint)iVar11 >> 8),(char)param_1[0x54]),iVar11,
                              param_6 - param_2[0x10],piVar3 + 0x14);
        if (iVar11 != 0) {
          return iVar11;
        }
        iVar11 = piVar3[0x18];
        piVar3[0x18] = piVar3[0x17];
        iVar12 = FUN_004093d0(param_1[0x52],param_1[0x53],(char)param_1[0x54],'\0',iVar11,
                              (int)param_1);
        if (iVar12 == 0) {
          return 1;
        }
        if ((param_1[0x21] != 0) && (pcVar8 = (char *)param_1[0x4f], pcVar8 != (char *)0x0)) {
          if ((*pcVar8 == '(') || ((*pcVar8 == 'N' && (pcVar8[1] == 'O')))) {
            if ((param_1[0x67] == param_1[0x66]) &&
               (uVar15 = FUN_0040a510(param_1 + 100), (char)uVar15 == '\0')) {
              return 1;
            }
            *(undefined1 *)param_1[0x67] = 0x29;
            iVar12 = param_1[0x67];
            param_1[0x67] = iVar12 + 1;
            if ((iVar12 + 1 == param_1[0x66]) &&
               (uVar15 = FUN_0040a510(param_1 + 100), (char)uVar15 == '\0')) {
              return 1;
            }
            *(undefined1 *)param_1[0x67] = 0;
            iVar12 = param_1[0x67];
            param_1[0x67] = iVar12 + 1;
            param_1[0x4f] = param_1[0x68];
            param_1[0x68] = iVar12 + 1;
          }
          *local_8 = param_3;
          bVar16 = iVar7 == 0x26;
          goto LAB_00407ace;
        }
      }
      break;
    case 0x27:
      goto switchD_00407492_caseD_27;
    case 0x28:
      if (param_1[0x20] == 0) break;
      piVar13 = FUN_0040a950((int)param_1,(int)param_2,param_3,param_6);
      param_1[0x52] = (int)piVar13;
      if (piVar13 == (int *)0x0) {
        return 1;
      }
      piVar3[0x28] = 0;
      piVar3[0x27] = 0;
      *(undefined1 *)(piVar3 + 0x23) = 1;
      goto LAB_00407539;
    case 0x29:
    case 0x2a:
      if ((char)piVar3[0x23] != '\0') {
        bVar16 = bVar5;
        if (param_1[0x20] != 0) {
          piVar13 = (int *)(*(code *)param_1[3])(0x14);
          if (piVar13 == (int *)0x0) {
            return 1;
          }
          piVar13[1] = 0;
          piVar13[2] = 0;
          piVar13[3] = 0;
          piVar13[4] = 0;
          *piVar13 = (iVar7 == 0x29) + 1;
          *local_8 = param_3;
          (*(code *)param_1[0x20])(param_1[1],*(undefined4 *)param_1[0x52],piVar13);
          bVar16 = false;
        }
        *(undefined1 *)(piVar3 + 0x23) = 0;
        goto LAB_00407512;
      }
      break;
    case 0x2b:
      if ((char)piVar3[0x23] != '\0') {
        *(undefined4 *)(piVar3[0x24] + *(int *)(piVar3[0x29] + -4 + piVar3[0x28] * 4) * 0x1c) = 3;
        iVar7 = param_1[0x20];
        goto joined_r0x004077ea;
      }
      break;
    case 0x2c:
      uVar4 = param_1[0x71];
      if (uVar4 <= (uint)param_1[0x40]) {
        if (uVar4 == 0) {
          param_1[0x71] = 0x20;
          iVar7 = (*(code *)param_1[3])(0x20);
          param_1[0x70] = iVar7;
          if (iVar7 == 0) {
            return 1;
          }
        }
        else {
          param_1[0x71] = uVar4 * 2;
          iVar7 = (*(code *)param_1[4])(param_1[0x70],uVar4 * 2);
          if (iVar7 == 0) {
            return 1;
          }
          param_1[0x70] = iVar7;
          if (piVar3[0x29] != 0) {
            iVar7 = (*(code *)param_1[4])(piVar3[0x29],param_1[0x71] << 2);
            if (iVar7 == 0) {
              return 1;
            }
            piVar3[0x29] = iVar7;
          }
        }
      }
      *(undefined1 *)(param_1[0x70] + param_1[0x40]) = 0;
      if ((char)piVar3[0x23] != '\0') {
        iVar7 = FUN_0040a690((int)param_1);
        if (iVar7 < 0) {
          return 1;
        }
        *(int *)(piVar3[0x29] + piVar3[0x28] * 4) = iVar7;
        piVar3[0x28] = piVar3[0x28] + 1;
        *(undefined4 *)(piVar3[0x24] + iVar7 * 0x1c) = 6;
        iVar7 = param_1[0x20];
        goto joined_r0x004077ea;
      }
      break;
    case 0x2d:
      uVar15 = 0;
      goto LAB_004083cf;
    case 0x2e:
      uVar15 = 2;
      goto LAB_004083cf;
    case 0x2f:
      uVar15 = 1;
      goto LAB_004083cf;
    case 0x30:
      uVar15 = 3;
LAB_004083cf:
      if ((char)piVar3[0x23] != '\0') {
        bVar16 = param_1[0x20] == 0;
        iVar7 = piVar3[0x28];
        piVar3[0x28] = iVar7 + -1;
        *(undefined4 *)(piVar3[0x24] + 4 + *(int *)(piVar3[0x29] + (iVar7 + -1) * 4) * 0x1c) =
             uVar15;
        if (piVar3[0x28] == 0) {
          if (!bVar16) {
            piVar13 = FUN_0040a7c0(param_1);
            if (piVar13 == (int *)0x0) {
              return 1;
            }
            *local_8 = param_3;
            (*(code *)param_1[0x20])(param_1[1],*(undefined4 *)param_1[0x52],piVar13);
          }
          *(undefined1 *)(piVar3 + 0x23) = 0;
          piVar3[0x25] = 0;
        }
        goto LAB_00407512;
      }
      break;
    case 0x31:
      if (*(char *)(param_1[0x70] + param_1[0x40]) == ',') {
        return 2;
      }
      bVar16 = bVar5;
      if ((((char)piVar3[0x23] != '\0') && (*(char *)(param_1[0x70] + param_1[0x40]) == '\0')) &&
         ((iVar7 = *(int *)(piVar3[0x29] + -4 + piVar3[0x28] * 4),
          *(int *)(piVar3[0x24] + iVar7 * 0x1c) != 3 &&
          (*(undefined4 *)(piVar3[0x24] + iVar7 * 0x1c) = 5, param_1[0x20] != 0)))) {
        bVar16 = false;
      }
      *(undefined1 *)(param_1[0x70] + param_1[0x40]) = 0x7c;
LAB_00407512:
      if (bVar16) break;
      goto LAB_00407539;
    case 0x32:
      if (*(char *)(param_1[0x70] + param_1[0x40]) == '|') {
        return 2;
      }
      *(undefined1 *)(param_1[0x70] + param_1[0x40]) = 0x2c;
      if ((char)piVar3[0x23] != '\0') {
        iVar7 = param_1[0x20];
        goto joined_r0x004077ea;
      }
      break;
    case 0x33:
      iVar7 = 0;
      goto LAB_0040830b;
    case 0x34:
      iVar7 = 2;
      goto LAB_0040830b;
    case 0x35:
      iVar7 = 1;
      goto LAB_0040830b;
    case 0x36:
      iVar7 = 3;
LAB_0040830b:
      if ((char)piVar3[0x23] == '\0') break;
      iVar11 = param_6;
      if (iVar7 != 0) {
        iVar11 = param_6 - param_2[0x10];
      }
      iVar12 = FUN_0040a690((int)param_1);
      if (iVar12 < 0) {
        return 1;
      }
      iVar12 = iVar12 * 0x1c;
      *(undefined4 *)(iVar12 + piVar3[0x24]) = 4;
      *(int *)(iVar12 + 4 + piVar3[0x24]) = iVar7;
      piVar13 = FUN_0040a950((int)param_1,(int)param_2,param_3,iVar11);
      if (piVar13 == (int *)0x0) {
        return 1;
      }
      pcVar8 = (char *)*piVar13;
      *(char **)(iVar12 + 8 + piVar3[0x24]) = pcVar8;
      iVar7 = 1;
      cVar2 = *pcVar8;
      while (cVar2 != '\0') {
        pcVar1 = pcVar8 + iVar7;
        iVar7 = iVar7 + 1;
        cVar2 = *pcVar1;
      }
      piVar3[0x25] = piVar3[0x25] + iVar7;
switchD_00407492_caseD_27:
      iVar7 = param_1[0x20];
      goto joined_r0x004077ea;
    case 0x37:
      iVar7 = FUN_00409180(param_1,(int)param_2,param_3,param_6);
      goto joined_r0x004084a6;
    case 0x38:
      iVar7 = FUN_00409290(param_1,(int)param_2,param_3,param_6);
joined_r0x004084a6:
      if (iVar7 == 0) {
        return 1;
      }
      goto LAB_00407539;
    case 0x39:
      if (((*(char *)((int)piVar3 + 0x82) == '\0') && ((code *)param_1[0x1b] != (code *)0x0)) &&
         (iVar7 = (*(code *)param_1[0x1b])(param_1[1]), iVar7 == 0)) {
        return 0x16;
      }
      break;
    case -1:
      if (param_5 != 0xc) {
        return (-(uint)(param_5 != 0x1c) & 0xfffffff8) + 10;
      }
      return 0x11;
    }
    if (param_1[0x14] != 0) {
      FUN_00409320(param_1,(int)param_2,param_3,param_6);
    }
LAB_00407539:
    if (param_1[0x74] == 2) {
      return 0x23;
    }
    if (param_1[0x74] == 3) {
      *param_7 = param_6;
      return 0;
    }
    param_3 = param_6;
    param_5 = (*(code *)*param_2)(param_2,param_6,param_4,&param_6);
  } while( true );
}



/* 00408700 FUN_00408700 */

undefined4 __cdecl FUN_00408700(int *param_1,int param_2,undefined4 param_3,int *param_4)

{
  int *piVar1;
  int *piVar2;
  undefined4 uVar3;
  undefined4 uVar4;
  int iVar5;
  int *piVar6;
  
  uVar3 = param_3;
  piVar1 = (int *)param_2;
  piVar2 = param_1;
  piVar6 = param_1 + 0x24;
  param_1[0x42] = (int)FUN_00408700;
  param_1[0x44] = param_2;
  param_1 = (int *)0x0;
  uVar4 = (**(code **)*piVar6)((undefined4 *)*piVar6,param_2,param_3,&param_1);
  piVar2[0x45] = (int)param_1;
  do {
    switch(uVar4) {
    case 0:
      piVar2[0x44] = (int)param_1;
      return 4;
    case 0xb:
      iVar5 = FUN_00409180(piVar2,piVar2[0x24],(int)piVar1,(int)param_1);
      piVar6 = param_1;
      break;
    case 0xd:
      iVar5 = FUN_00409290(piVar2,piVar2[0x24],(int)piVar1,(int)param_1);
      piVar6 = param_1;
      break;
    case 0xf:
      piVar6 = param_1;
      if (piVar2[0x14] != 0) {
        FUN_00409320(piVar2,piVar2[0x24],(int)piVar1,(int)param_1);
        piVar6 = param_1;
      }
      goto LAB_004087b4;
    case 0xfffffff1:
      if ((piVar2[0x14] != 0) &&
         (FUN_00409320(piVar2,piVar2[0x24],(int)piVar1,(int)param_1), piVar2[0x74] == 2)) {
        return 0x23;
      }
      *param_4 = (int)param_1;
      return 0;
    default:
      return 9;
    case 0xfffffffe:
      if ((char)piVar2[0x75] != '\0') {
        return 6;
      }
    case 0xfffffffc:
      *param_4 = (int)piVar1;
      return 0;
    case 0xffffffff:
      if ((char)piVar2[0x75] == '\0') {
        *param_4 = (int)piVar1;
        return 0;
      }
      return 5;
    }
    if (iVar5 == 0) {
      return 1;
    }
LAB_004087b4:
    piVar2[0x44] = (int)piVar6;
    if (piVar2[0x74] == 2) {
      return 0x23;
    }
    if (piVar2[0x74] == 3) {
      *param_4 = (int)piVar6;
      return 0;
    }
    param_1 = (int *)0x0;
    uVar4 = (**(code **)piVar2[0x24])((undefined4 *)piVar2[0x24],piVar6,uVar3,&param_1);
    piVar2[0x45] = (int)param_1;
    piVar1 = piVar6;
  } while( true );
}



/* 004088f0 FUN_004088f0 */

int __cdecl FUN_004088f0(int param_1,int param_2,int param_3)

{
  int iVar1;
  int iVar2;
  int iVar3;
  undefined4 *puVar4;
  int iVar5;
  
  iVar1 = param_1;
  puVar4 = *(undefined4 **)(param_1 + 0x120);
  if (puVar4 == (undefined4 *)0x0) {
    puVar4 = (undefined4 *)(**(code **)(param_1 + 0xc))(0x18);
    if (puVar4 == (undefined4 *)0x0) {
      return 1;
    }
  }
  else {
    *(undefined4 *)(param_1 + 0x120) = puVar4[2];
  }
  iVar2 = param_2;
  *(undefined1 *)(param_2 + 0x20) = 1;
  *(undefined4 *)(param_2 + 0xc) = 0;
  puVar4[2] = *(undefined4 *)(iVar1 + 0x11c);
  *(undefined4 **)(iVar1 + 0x11c) = puVar4;
  puVar4[3] = param_2;
  puVar4[4] = *(undefined4 *)(iVar1 + 0x128);
  *(undefined1 *)(puVar4 + 5) = (undefined1)param_3;
  *puVar4 = 0;
  puVar4[1] = 0;
  param_3 = *(int *)(param_2 + 4);
  iVar5 = *(int *)(param_2 + 8) + param_3;
  iVar3 = FUN_00405420((int *)iVar1,*(int *)(iVar1 + 0x128),*(int **)(iVar1 + 0xe0),param_3,iVar5,
                       &param_1,0);
  if (iVar3 == 0) {
    if ((iVar5 != param_1) && (*(int *)(iVar1 + 0x1d0) == 3)) {
      *(int *)(iVar2 + 0xc) = param_1 - param_3;
      *(code **)(iVar1 + 0x108) = FUN_004089e0;
      return 0;
    }
    *(undefined1 *)(iVar2 + 0x20) = 0;
    *(undefined4 *)(iVar1 + 0x11c) = puVar4[2];
    puVar4[2] = *(undefined4 *)(iVar1 + 0x120);
    *(undefined4 **)(iVar1 + 0x120) = puVar4;
  }
  return iVar3;
}



/* 004089e0 FUN_004089e0 */

int __cdecl FUN_004089e0(int *param_1,int param_2,int param_3,int *param_4)

{
  int iVar1;
  int iVar2;
  int *piVar3;
  int iVar4;
  int *piVar5;
  
  piVar3 = param_1;
  iVar1 = param_1[0x47];
  if (iVar1 == 0) {
    return 0x17;
  }
  iVar2 = *(int *)(iVar1 + 0xc);
  piVar5 = (int *)(*(int *)(iVar2 + 8) + *(int *)(iVar2 + 4));
  iVar4 = FUN_00405420(param_1,*(int *)(iVar1 + 0x10),(int *)param_1[0x38],
                       *(int *)(iVar2 + 0xc) + *(int *)(iVar2 + 4),(int)piVar5,(int *)&param_1,0);
  if (iVar4 == 0) {
    if ((piVar5 != param_1) && (piVar3[0x74] == 3)) {
      *(int *)(iVar2 + 0xc) = (int)param_1 - *(int *)(iVar2 + 4);
      return 0;
    }
    *(undefined1 *)(iVar2 + 0x20) = 0;
    piVar3[0x47] = *(int *)(iVar1 + 8);
    *(int *)(iVar1 + 8) = piVar3[0x48];
    piVar3[0x48] = iVar1;
    piVar3[0x42] = (int)FUN_00406c40;
    iVar4 = FUN_00405420(piVar3,(uint)(piVar3[0x73] != 0),(int *)piVar3[0x24],param_2,param_3,
                         param_4,CONCAT31((int3)((uint)param_1 >> 8),(char)piVar3[0x75] == '\0'));
  }
  return iVar4;
}



/* 00408ac0 FUN_00408ac0 */

int __cdecl
FUN_00408ac0(int param_1,int param_2,undefined4 param_3,int param_4,undefined4 param_5,int *param_6)

{
  int iVar1;
  undefined4 uVar2;
  
  iVar1 = FUN_00408b40(param_1,param_2,param_3,param_4,param_5,param_6);
  if (iVar1 == 0) {
    if ((((char)param_3 == '\0') && (iVar1 = param_6[3], iVar1 != param_6[4])) &&
       (*(char *)(iVar1 + -1) == ' ')) {
      param_6[3] = iVar1 + -1;
    }
    if (param_6[3] == param_6[2]) {
      uVar2 = FUN_0040a510(param_6);
      if ((char)uVar2 == '\0') {
        return 1;
      }
    }
    *(undefined1 *)param_6[3] = 0;
    param_6[3] = param_6[3] + 1;
    iVar1 = 0;
  }
  return iVar1;
}



/* 00408b40 FUN_00408b40 */

int __cdecl
FUN_00408b40(int param_1,int param_2,undefined4 param_3,int param_4,undefined4 param_5,int *param_6)

{
  int *piVar1;
  int iVar2;
  int *piVar3;
  char cVar4;
  int iVar5;
  undefined4 uVar6;
  byte *pbVar7;
  int iVar8;
  bool bVar9;
  
  iVar8 = param_4;
  iVar2 = param_2;
  piVar1 = *(int **)(param_1 + 0x154);
  iVar5 = (**(code **)(param_2 + 0xc))(param_2,param_4,param_5,&param_2);
  piVar3 = param_6;
  while (iVar5 + 4U < 0x2c) {
                    /* WARNING: Could not find normalized switch variable to match jumptable */
    switch((&switchD_00408b85::switchdataD_00408f28)[iVar5 + 4U]) {
    case 0:
      if (iVar2 == *(int *)(param_1 + 0x90)) {
        *(int *)(param_1 + 0x110) = param_2;
      }
      return 4;
    case 6:
      iVar8 = FUN_0040a3a0(piVar3,iVar2,iVar8,param_2);
      if (iVar8 == 0) {
        return 1;
      }
      break;
    case 9:
      cVar4 = (**(code **)(iVar2 + 0x2c))
                        (iVar2,*(int *)(iVar2 + 0x40) + iVar8,param_2 - *(int *)(iVar2 + 0x40));
      if (cVar4 == '\0') {
        pbVar7 = (byte *)FUN_0040a4c0((int *)(param_1 + 0x1a8),iVar2,*(int *)(iVar2 + 0x40) + iVar8,
                                      param_2 - *(int *)(iVar2 + 0x40));
        if (pbVar7 == (byte *)0x0) {
          return 1;
        }
        iVar8 = FUN_00409ee0(piVar1,pbVar7,0);
        *(undefined4 *)(param_1 + 0x1b4) = *(undefined4 *)(param_1 + 0x1b8);
        if (piVar3 == piVar1 + 0x14) {
          if (*(char *)((int)piVar1 + 0x82) == '\0') {
            bVar9 = *(char *)((int)piVar1 + 0x81) == '\0';
          }
          else {
            bVar9 = *(int *)(param_1 + 0x11c) == 0;
          }
          if (!bVar9) goto LAB_00408d76;
LAB_00408d41:
          if (iVar8 == 0) {
            return 0xb;
          }
          if (*(char *)(iVar8 + 0x22) == '\0') {
            return 0x18;
          }
        }
        else {
          if ((*(char *)((int)piVar1 + 0x81) == '\0') || (*(char *)((int)piVar1 + 0x82) != '\0'))
          goto LAB_00408d41;
LAB_00408d76:
          if (iVar8 == 0) break;
        }
        if (*(char *)(iVar8 + 0x20) != '\0') {
          if (iVar2 == *(int *)(param_1 + 0x90)) {
            *(int *)(param_1 + 0x110) = param_4;
          }
          return 0xc;
        }
        if (*(int *)(iVar8 + 0x1c) != 0) {
          if (iVar2 == *(int *)(param_1 + 0x90)) {
            *(int *)(param_1 + 0x110) = param_4;
          }
          return 0xf;
        }
        iVar5 = *(int *)(iVar8 + 4);
        if (iVar5 == 0) {
          if (iVar2 == *(int *)(param_1 + 0x90)) {
            *(int *)(param_1 + 0x110) = param_4;
          }
          return 0x10;
        }
        *(undefined1 *)(iVar8 + 0x20) = 1;
        iVar5 = FUN_00408b40(param_1,*(int *)(param_1 + 0xe0),param_3,iVar5,
                             *(int *)(iVar8 + 8) + iVar5,piVar3);
        *(undefined1 *)(iVar8 + 0x20) = 0;
        if (iVar5 != 0) {
          return iVar5;
        }
      }
      else {
        if ((piVar3[3] == piVar3[2]) && (uVar6 = FUN_0040a510(piVar3), (char)uVar6 == '\0')) {
          return 1;
        }
        *(char *)piVar3[3] = cVar4;
        piVar3[3] = piVar3[3] + 1;
      }
      break;
    case 10:
      iVar5 = (**(code **)(iVar2 + 0x28))(iVar2,iVar8);
      if (iVar5 < 0) {
        if (iVar2 == *(int *)(param_1 + 0x90)) {
          *(int *)(param_1 + 0x110) = iVar8;
        }
        return 0xe;
      }
      if ((((char)param_3 != '\0') || (iVar5 != 0x20)) ||
         ((piVar3[3] != piVar3[4] && (*(char *)(piVar3[3] + -1) != ' ')))) {
        iVar5 = FUN_00425860(iVar5,(byte *)&param_6);
        if (iVar5 == 0) {
          if (iVar2 == *(int *)(param_1 + 0x90)) {
            *(int *)(param_1 + 0x110) = iVar8;
          }
          return 0xe;
        }
        iVar8 = 0;
        if (0 < iVar5) {
          do {
            if ((piVar3[3] == piVar3[2]) && (uVar6 = FUN_0040a510(piVar3), (char)uVar6 == '\0')) {
              return 1;
            }
            *(undefined1 *)piVar3[3] = *(undefined1 *)((int)&param_6 + iVar8);
            iVar8 = iVar8 + 1;
            piVar3[3] = piVar3[3] + 1;
          } while (iVar8 < iVar5);
        }
      }
      break;
    case 0xfffffffc:
      return 0;
    case 0xfffffffd:
      param_2 = *(int *)(iVar2 + 0x40) + iVar8;
    case 7:
    case 0x27:
      if (((char)param_3 != '\0') ||
         ((piVar3[3] != piVar3[4] && (*(char *)(piVar3[3] + -1) != ' ')))) {
        if ((piVar3[3] == piVar3[2]) && (uVar6 = FUN_0040a510(piVar3), (char)uVar6 == '\0')) {
          return 1;
        }
        *(undefined1 *)piVar3[3] = 0x20;
        piVar3[3] = piVar3[3] + 1;
      }
      break;
    default:
      goto switchD_00408b85_caseD_fffffffe;
    case 0xffffffff:
      if (iVar2 == *(int *)(param_1 + 0x90)) {
        *(int *)(param_1 + 0x110) = iVar8;
      }
      return 4;
    }
    param_4 = param_2;
    iVar5 = (**(code **)(iVar2 + 0xc))(iVar2,param_2,param_5,&param_2);
    iVar8 = param_4;
  }
switchD_00408b85_caseD_fffffffe:
  if (iVar2 == *(int *)(param_1 + 0x90)) {
    *(int *)(param_1 + 0x110) = iVar8;
  }
  return 0x17;
}



/* 00408f60 FUN_00408f60 */

undefined4 __cdecl FUN_00408f60(int param_1,int param_2,int param_3,undefined4 param_4)

{
  int iVar1;
  int iVar2;
  undefined4 uVar3;
  int iVar4;
  int iVar5;
  int *piVar6;
  
  iVar1 = *(int *)(param_1 + 0x154);
  piVar6 = (int *)(iVar1 + 0x68);
  if ((*piVar6 == 0) && (uVar3 = FUN_0040a510(piVar6), (char)uVar3 == '\0')) {
    return 1;
  }
  iVar5 = param_3;
  iVar2 = param_2;
  uVar3 = (**(code **)(param_2 + 0x10))(param_2,param_3,param_4,&param_2);
  do {
    switch(uVar3) {
    case 0:
      if (iVar2 == *(int *)(param_1 + 0x90)) {
        *(int *)(param_1 + 0x110) = param_2;
      }
      return 4;
    case 6:
    case 9:
      iVar5 = FUN_0040a3a0(piVar6,iVar2,iVar5,param_2);
      if (iVar5 == 0) {
        return 1;
      }
      break;
    case 10:
      iVar4 = (**(code **)(iVar2 + 0x28))(iVar2,iVar5);
      if (iVar4 < 0) {
        if (iVar2 == *(int *)(param_1 + 0x90)) {
          *(int *)(param_1 + 0x110) = iVar5;
        }
        return 0xe;
      }
      iVar4 = FUN_00425860(iVar4,(byte *)&param_3);
      if (iVar4 == 0) {
        if (iVar2 == *(int *)(param_1 + 0x90)) {
          *(int *)(param_1 + 0x110) = iVar5;
        }
        return 0xe;
      }
      iVar5 = 0;
      if (0 < iVar4) {
        do {
          if ((*(int *)(iVar1 + 0x70) == *(int *)(iVar1 + 0x74)) &&
             (uVar3 = FUN_0040a510(piVar6), (char)uVar3 == '\0')) {
            return 1;
          }
          **(undefined1 **)(iVar1 + 0x74) = *(undefined1 *)((int)&param_3 + iVar5);
          iVar5 = iVar5 + 1;
          *(int *)(iVar1 + 0x74) = *(int *)(iVar1 + 0x74) + 1;
        } while (iVar5 < iVar4);
      }
      break;
    case 0x1c:
      *(int *)(param_1 + 0x110) = iVar5;
      return 10;
    case 0xfffffffc:
      return 0;
    case 0xfffffffd:
      param_2 = *(int *)(iVar2 + 0x40) + iVar5;
    case 7:
      if ((*(int *)(iVar1 + 0x70) == *(int *)(iVar1 + 0x74)) &&
         (uVar3 = FUN_0040a510(piVar6), (char)uVar3 == '\0')) {
        return 1;
      }
      **(undefined1 **)(iVar1 + 0x74) = 10;
      *(int *)(iVar1 + 0x74) = *(int *)(iVar1 + 0x74) + 1;
      break;
    default:
      if (iVar2 == *(int *)(param_1 + 0x90)) {
        *(int *)(param_1 + 0x110) = iVar5;
      }
      return 0x17;
    case 0xffffffff:
      if (iVar2 == *(int *)(param_1 + 0x90)) {
        *(int *)(param_1 + 0x110) = iVar5;
      }
      return 4;
    }
    iVar5 = param_2;
    uVar3 = (**(code **)(iVar2 + 0x10))(iVar2,param_2,param_4,&param_2);
  } while( true );
}



/* 00409180 FUN_00409180 */

undefined4 __cdecl FUN_00409180(int *param_1,int param_2,int param_3,int param_4)

{
  int iVar1;
  int iVar2;
  int iVar3;
  char *pcVar4;
  int iVar5;
  
  if (param_1[0x10] == 0) {
    if (param_1[0x14] != 0) {
      FUN_00409320(param_1,param_2,param_3,param_4);
      return 1;
    }
  }
  else {
    iVar3 = param_3 + *(int *)(param_2 + 0x40) * 2;
    iVar1 = (**(code **)(param_2 + 0x1c))(param_2,iVar3);
    iVar2 = FUN_0040a4c0(param_1 + 100,param_2,iVar3,iVar1 + iVar3);
    if (iVar2 == 0) {
      return 0;
    }
    param_1[0x68] = param_1[0x67];
    iVar5 = param_4 + *(int *)(param_2 + 0x40) * -2;
    iVar3 = (**(code **)(param_2 + 0x20))(param_2,iVar1 + iVar3);
    pcVar4 = (char *)FUN_0040a4c0(param_1 + 100,param_2,iVar3,iVar5);
    if (pcVar4 == (char *)0x0) {
      return 0;
    }
    FUN_00409250(pcVar4);
    (*(code *)param_1[0x10])(param_1[1],iVar2,pcVar4);
    FUN_0040a310(param_1 + 100);
  }
  return 1;
}



/* 00409250 FUN_00409250 */

void __cdecl FUN_00409250(char *param_1)

{
  char *pcVar1;
  char cVar2;
  char *pcVar3;
  
  cVar2 = *param_1;
  if (cVar2 != '\0') {
    while (pcVar3 = param_1, cVar2 != '\r') {
      cVar2 = param_1[1];
      param_1 = param_1 + 1;
      if (cVar2 == '\0') {
        return;
      }
    }
    do {
      if (*param_1 == '\r') {
        *pcVar3 = '\n';
        pcVar1 = param_1 + 1;
        param_1 = param_1 + 1;
        if (*pcVar1 == '\n') goto LAB_00409283;
      }
      else {
        *pcVar3 = *param_1;
LAB_00409283:
        param_1 = param_1 + 1;
      }
      pcVar3 = pcVar3 + 1;
    } while (*param_1 != '\0');
    *pcVar3 = '\0';
  }
  return;
}



/* 00409290 FUN_00409290 */

undefined4 __cdecl FUN_00409290(int *param_1,int param_2,int param_3,int param_4)

{
  char *pcVar1;
  
  if (param_1[0x11] == 0) {
    if (param_1[0x14] != 0) {
      FUN_00409320(param_1,param_2,param_3,param_4);
      return 1;
    }
  }
  else {
    pcVar1 = (char *)FUN_0040a4c0(param_1 + 100,param_2,param_3 + *(int *)(param_2 + 0x40) * 4,
                                  param_4 + *(int *)(param_2 + 0x40) * -3);
    if (pcVar1 == (char *)0x0) {
      return 0;
    }
    FUN_00409250(pcVar1);
    (*(code *)param_1[0x11])(param_1[1],pcVar1);
    FUN_0040a310(param_1 + 100);
  }
  return 1;
}



/* 00409320 FUN_00409320 */

void __cdecl FUN_00409320(int *param_1,int param_2,int param_3,int param_4)

{
  int *piVar1;
  int iVar2;
  int *piVar3;
  
  iVar2 = param_4;
  piVar1 = param_1;
  if (*(char *)(param_2 + 0x44) != '\0') {
    (*(code *)param_1[0x14])(param_1[1],param_3,param_4 - param_3);
    return;
  }
  if (param_2 == param_1[0x24]) {
    piVar3 = param_1 + 0x44;
    param_1 = param_1 + 0x45;
  }
  else {
    piVar3 = (int *)param_1[0x47];
    param_1 = piVar3 + 1;
  }
  do {
    param_4 = piVar1[0xb];
    (**(code **)(param_2 + 0x38))(param_2,&param_3,iVar2,&param_4,piVar1[0xc]);
    *param_1 = param_3;
    (*(code *)piVar1[0x14])(piVar1[1],piVar1[0xb],param_4 - piVar1[0xb]);
    *piVar3 = param_3;
  } while (param_3 != iVar2);
  return;
}



/* 004093d0 FUN_004093d0 */

undefined4 __cdecl
FUN_004093d0(int param_1,int param_2,char param_3,char param_4,int param_5,int param_6)

{
  int iVar1;
  int iVar2;
  int *piVar3;
  
  if ((param_5 != 0) || (param_4 != '\0')) {
    iVar1 = 0;
    if (0 < *(int *)(param_1 + 0xc)) {
      piVar3 = *(int **)(param_1 + 0x14);
      do {
        if (param_2 == *piVar3) {
          return 1;
        }
        iVar1 = iVar1 + 1;
        piVar3 = piVar3 + 3;
      } while (iVar1 < *(int *)(param_1 + 0xc));
    }
    if (((param_4 != '\0') && (*(int *)(param_1 + 8) == 0)) && (*(char *)(param_2 + 9) == '\0')) {
      *(int *)(param_1 + 8) = param_2;
    }
  }
  iVar1 = *(int *)(param_1 + 0x10);
  if (*(int *)(param_1 + 0xc) == iVar1) {
    if (iVar1 == 0) {
      *(undefined4 *)(param_1 + 0x10) = 8;
      iVar1 = (**(code **)(param_6 + 0xc))(0x60);
      *(int *)(param_1 + 0x14) = iVar1;
      if (iVar1 == 0) {
        return 0;
      }
    }
    else {
      iVar2 = (**(code **)(param_6 + 0x10))(*(undefined4 *)(param_1 + 0x14),iVar1 * 0x18);
      if (iVar2 == 0) {
        return 0;
      }
      *(int *)(param_1 + 0x10) = iVar1 * 2;
      *(int *)(param_1 + 0x14) = iVar2;
    }
  }
  piVar3 = (int *)(*(int *)(param_1 + 0x14) + *(int *)(param_1 + 0xc) * 0xc);
  piVar3[2] = param_5;
  *piVar3 = param_2;
  *(char *)(piVar3 + 1) = param_3;
  if (param_3 == '\0') {
    *(undefined1 *)(param_2 + 8) = 1;
  }
  *(int *)(param_1 + 0xc) = *(int *)(param_1 + 0xc) + 1;
  return 1;
}



/* 004094b0 FUN_004094b0 */

undefined4 __cdecl FUN_004094b0(int param_1,undefined4 *param_2)

{
  char cVar1;
  int iVar2;
  undefined4 uVar3;
  int *piVar4;
  char *pcVar5;
  char *pcVar6;
  
  iVar2 = *(int *)(param_1 + 0x154);
  pcVar5 = (char *)*param_2;
  cVar1 = *pcVar5;
  do {
    if (cVar1 == '\0') {
      return 1;
    }
    if (cVar1 == ':') {
      for (pcVar6 = (char *)*param_2; pcVar6 != pcVar5; pcVar6 = pcVar6 + 1) {
        if ((*(int *)(iVar2 + 0x5c) == *(int *)(iVar2 + 0x58)) &&
           (uVar3 = FUN_0040a510((int *)(iVar2 + 0x50)), (char)uVar3 == '\0')) {
          return 0;
        }
        **(char **)(iVar2 + 0x5c) = *pcVar6;
        *(int *)(iVar2 + 0x5c) = *(int *)(iVar2 + 0x5c) + 1;
      }
      if ((*(int *)(iVar2 + 0x5c) == *(int *)(iVar2 + 0x58)) &&
         (uVar3 = FUN_0040a510((int *)(iVar2 + 0x50)), (char)uVar3 == '\0')) {
        return 0;
      }
      **(undefined1 **)(iVar2 + 0x5c) = 0;
      *(int *)(iVar2 + 0x5c) = *(int *)(iVar2 + 0x5c) + 1;
      piVar4 = (int *)FUN_00409ee0((int *)(iVar2 + 0x3c),*(byte **)(iVar2 + 0x60),8);
      if (piVar4 == (int *)0x0) {
        return 0;
      }
      if (*piVar4 == *(int *)(iVar2 + 0x60)) {
        *(undefined4 *)(iVar2 + 0x60) = *(undefined4 *)(iVar2 + 0x5c);
      }
      else {
        *(int *)(iVar2 + 0x5c) = *(int *)(iVar2 + 0x60);
      }
      param_2[1] = piVar4;
    }
    cVar1 = pcVar5[1];
    pcVar5 = pcVar5 + 1;
  } while( true );
}



/* 00409580 FUN_00409580 */

int * __cdecl FUN_00409580(int param_1,int param_2,int param_3,int param_4)

{
  undefined4 uVar1;
  int iVar2;
  int *piVar3;
  int iVar4;
  int *piVar5;
  byte bVar6;
  int iVar7;
  byte *pbVar8;
  
  iVar4 = *(int *)(param_1 + 0x154);
  if ((*(int *)(iVar4 + 0x5c) != *(int *)(iVar4 + 0x58)) ||
     (uVar1 = FUN_0040a510((int *)(iVar4 + 0x50)), (char)uVar1 != '\0')) {
    **(undefined1 **)(iVar4 + 0x5c) = 0;
    *(int *)(iVar4 + 0x5c) = *(int *)(iVar4 + 0x5c) + 1;
    iVar2 = FUN_0040a4c0((int *)(iVar4 + 0x50),param_2,param_3,param_4);
    if (iVar2 == 0) {
      return (int *)0x0;
    }
    pbVar8 = (byte *)(iVar2 + 1);
    piVar3 = (int *)FUN_00409ee0((int *)(iVar4 + 0x28),pbVar8,0xc);
    if (piVar3 != (int *)0x0) {
      if ((byte *)*piVar3 != pbVar8) {
        *(undefined4 *)(iVar4 + 0x5c) = *(undefined4 *)(iVar4 + 0x60);
        return piVar3;
      }
      *(undefined4 *)(iVar4 + 0x60) = *(undefined4 *)(iVar4 + 0x5c);
      if (*(char *)(param_1 + 0xe8) != '\0') {
        bVar6 = *pbVar8;
        if ((((bVar6 == 0x78) && (*(char *)(iVar2 + 2) == 'm')) && (*(char *)(iVar2 + 3) == 'l')) &&
           ((*(char *)(iVar2 + 4) == 'n' && (*(char *)(iVar2 + 5) == 's')))) {
          if (*(char *)(iVar2 + 6) == '\0') {
            piVar3[1] = iVar4 + 0x84;
            *(undefined1 *)((int)piVar3 + 9) = 1;
            return piVar3;
          }
          if (*(char *)(iVar2 + 6) == ':') {
            iVar4 = FUN_00409ee0((int *)(iVar4 + 0x3c),(byte *)(iVar2 + 7),8);
            piVar3[1] = iVar4;
            *(undefined1 *)((int)piVar3 + 9) = 1;
            return piVar3;
          }
        }
        iVar7 = 0;
        if (bVar6 != 0) {
          while (bVar6 != 0x3a) {
            bVar6 = *(byte *)(iVar2 + 2 + iVar7);
            iVar7 = iVar7 + 1;
            if (bVar6 == 0) {
              return piVar3;
            }
          }
          iVar2 = 0;
          if (0 < iVar7) {
            do {
              if ((*(int *)(iVar4 + 0x5c) == *(int *)(iVar4 + 0x58)) &&
                 (uVar1 = FUN_0040a510((int *)(iVar4 + 0x50)), (char)uVar1 == '\0')) {
                return (int *)0x0;
              }
              **(byte **)(iVar4 + 0x5c) = pbVar8[iVar2];
              iVar2 = iVar2 + 1;
              *(int *)(iVar4 + 0x5c) = *(int *)(iVar4 + 0x5c) + 1;
            } while (iVar2 < iVar7);
          }
          if ((*(int *)(iVar4 + 0x5c) == *(int *)(iVar4 + 0x58)) &&
             (uVar1 = FUN_0040a510((int *)(iVar4 + 0x50)), (char)uVar1 == '\0')) {
            return (int *)0x0;
          }
          **(undefined1 **)(iVar4 + 0x5c) = 0;
          *(int *)(iVar4 + 0x5c) = *(int *)(iVar4 + 0x5c) + 1;
          piVar5 = (int *)FUN_00409ee0((int *)(iVar4 + 0x3c),*(byte **)(iVar4 + 0x60),8);
          piVar3[1] = (int)piVar5;
          if (*piVar5 == *(int *)(iVar4 + 0x60)) {
            *(undefined4 *)(iVar4 + 0x60) = *(undefined4 *)(iVar4 + 0x5c);
            return piVar3;
          }
          *(int *)(iVar4 + 0x5c) = *(int *)(iVar4 + 0x60);
        }
      }
      return piVar3;
    }
  }
  return (int *)0x0;
}



/* 00409740 FUN_00409740 */

undefined4 __cdecl FUN_00409740(int param_1)

{
  char cVar1;
  int *piVar2;
  bool bVar3;
  undefined4 uVar4;
  int iVar5;
  undefined4 *puVar6;
  int iVar7;
  int iVar8;
  char *pcVar9;
  int local_8 [2];
  
  bVar3 = false;
  piVar2 = *(int **)(param_1 + 0x154);
  if (piVar2[0x22] != 0) {
    if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
       (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
      return 0;
    }
    **(undefined1 **)(param_1 + 0x19c) = 0x3d;
    iVar5 = *(int *)(param_1 + 0x19c) + 1;
    *(int *)(param_1 + 0x19c) = iVar5;
    iVar7 = *(int *)(piVar2[0x22] + 0x14);
    if (*(char *)(param_1 + 0x1c8) != '\0') {
      iVar7 = iVar7 + -1;
    }
    iVar8 = 0;
    if (0 < iVar7) {
      do {
        if ((iVar5 == *(int *)(param_1 + 0x198)) &&
           (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
          return 0;
        }
        **(undefined1 **)(param_1 + 0x19c) = *(undefined1 *)(*(int *)(piVar2[0x22] + 0x10) + iVar8);
        iVar5 = *(int *)(param_1 + 0x19c) + 1;
        iVar8 = iVar8 + 1;
        *(int *)(param_1 + 0x19c) = iVar5;
      } while (iVar8 < iVar7);
    }
    bVar3 = true;
  }
  FUN_0040a2a0(local_8,piVar2 + 0xf);
  puVar6 = (undefined4 *)FUN_0040a2c0(local_8);
  while (puVar6 != (undefined4 *)0x0) {
    if (puVar6[1] != 0) {
      if (bVar3) {
        if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
           (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
          return 0;
        }
        **(undefined1 **)(param_1 + 0x19c) = 0xc;
        *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
      }
      pcVar9 = (char *)*puVar6;
      cVar1 = *pcVar9;
      while (cVar1 != '\0') {
        if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
           (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
          return 0;
        }
        **(char **)(param_1 + 0x19c) = *pcVar9;
        pcVar9 = pcVar9 + 1;
        *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
        cVar1 = *pcVar9;
      }
      if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
         (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
        return 0;
      }
      **(undefined1 **)(param_1 + 0x19c) = 0x3d;
      iVar5 = *(int *)(param_1 + 0x19c) + 1;
      *(int *)(param_1 + 0x19c) = iVar5;
      iVar7 = *(int *)(puVar6[1] + 0x14);
      if (*(char *)(param_1 + 0x1c8) != '\0') {
        iVar7 = iVar7 + -1;
      }
      iVar8 = 0;
      if (0 < iVar7) {
        do {
          if ((iVar5 == *(int *)(param_1 + 0x198)) &&
             (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
            return 0;
          }
          **(undefined1 **)(param_1 + 0x19c) = *(undefined1 *)(*(int *)(puVar6[1] + 0x10) + iVar8);
          iVar5 = *(int *)(param_1 + 0x19c) + 1;
          iVar8 = iVar8 + 1;
          *(int *)(param_1 + 0x19c) = iVar5;
        } while (iVar8 < iVar7);
      }
      bVar3 = true;
    }
    puVar6 = (undefined4 *)FUN_0040a2c0(local_8);
  }
  FUN_0040a2a0(local_8,piVar2);
  puVar6 = (undefined4 *)FUN_0040a2c0(local_8);
  while (puVar6 != (undefined4 *)0x0) {
    if (*(char *)(puVar6 + 8) != '\0') {
      if (bVar3) {
        if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
           (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
          return 0;
        }
        **(undefined1 **)(param_1 + 0x19c) = 0xc;
        *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
      }
      pcVar9 = (char *)*puVar6;
      cVar1 = *pcVar9;
      while (cVar1 != '\0') {
        if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
           (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
          return 0;
        }
        **(char **)(param_1 + 0x19c) = *pcVar9;
        pcVar9 = pcVar9 + 1;
        *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
        cVar1 = *pcVar9;
      }
      bVar3 = true;
    }
    puVar6 = (undefined4 *)FUN_0040a2c0(local_8);
  }
  if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
     (uVar4 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar4 == '\0')) {
    return 0;
  }
  **(undefined1 **)(param_1 + 0x19c) = 0;
  *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
  return *(undefined4 *)(param_1 + 0x1a0);
}



/* 00409ab0 FUN_00409ab0 */

undefined4 __cdecl FUN_00409ab0(int param_1,char *param_2)

{
  char cVar1;
  int *piVar2;
  undefined4 in_EAX;
  int *piVar3;
  int iVar4;
  uint uVar5;
  char *pcVar6;
  
  piVar2 = *(int **)(param_1 + 0x154);
  cVar1 = *param_2;
  uVar5 = CONCAT31((int3)((uint)in_EAX >> 8),cVar1);
  pcVar6 = param_2;
  do {
    if (cVar1 == '\0') {
      return CONCAT31((int3)(uVar5 >> 8),1);
    }
    cVar1 = *param_2;
    if ((cVar1 == '\f') || (cVar1 == '\0')) {
      if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
         (uVar5 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar5 == '\0')) goto LAB_00409d14;
      **(undefined1 **)(param_1 + 0x19c) = 0;
      *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
      uVar5 = FUN_00409ee0(piVar2,*(byte **)(param_1 + 0x1a0),0);
      if (uVar5 != 0) {
        *(undefined1 *)(uVar5 + 0x20) = 1;
      }
      if (*param_2 != '\0') {
        param_2 = param_2 + 1;
      }
      *(undefined4 *)(param_1 + 0x19c) = *(undefined4 *)(param_1 + 0x1a0);
      pcVar6 = param_2;
    }
    else if (cVar1 == '=') {
      if (*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x1a0)) {
        piVar3 = piVar2 + 0x21;
      }
      else {
        if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
           (uVar5 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar5 == '\0')) goto LAB_00409d14;
        **(undefined1 **)(param_1 + 0x19c) = 0;
        *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
        piVar3 = (int *)FUN_00409ee0(piVar2 + 0xf,*(byte **)(param_1 + 0x1a0),8);
        uVar5 = 0;
        if (piVar3 == (int *)0x0) goto LAB_00409d14;
        if ((char *)*piVar3 == *(char **)(param_1 + 0x1a0)) {
          iVar4 = FUN_0040a420(piVar2 + 0x14,(char *)*piVar3);
          *piVar3 = iVar4;
          uVar5 = 0;
          if (iVar4 == 0) goto LAB_00409d14;
        }
        *(undefined4 *)(param_1 + 0x19c) = *(undefined4 *)(param_1 + 0x1a0);
      }
      pcVar6 = param_2 + 1;
      param_2 = param_2 + 1;
      cVar1 = *pcVar6;
      while ((cVar1 != '\f' && (cVar1 != '\0'))) {
        if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
           (uVar5 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar5 == '\0')) goto LAB_00409d14;
        **(char **)(param_1 + 0x19c) = *param_2;
        param_2 = param_2 + 1;
        *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
        cVar1 = *param_2;
      }
      if ((*(int *)(param_1 + 0x19c) == *(int *)(param_1 + 0x198)) &&
         (uVar5 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar5 == '\0')) {
LAB_00409d14:
        return uVar5 & 0xffffff00;
      }
      **(undefined1 **)(param_1 + 0x19c) = 0;
      *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
      uVar5 = FUN_00406970(param_1,piVar3,0,*(char **)(param_1 + 0x1a0),
                           (undefined4 *)(param_1 + 0x164));
      if (uVar5 != 0) goto LAB_00409d14;
      *(undefined4 *)(param_1 + 0x19c) = *(undefined4 *)(param_1 + 0x1a0);
      uVar5 = 0;
      pcVar6 = param_2;
      if (*param_2 != '\0') {
        param_2 = param_2 + 1;
        pcVar6 = param_2;
      }
    }
    else {
      uVar5 = *(uint *)(param_1 + 0x198);
      if ((*(uint *)(param_1 + 0x19c) == uVar5) &&
         (uVar5 = FUN_0040a510((int *)(param_1 + 400)), (char)uVar5 == '\0')) goto LAB_00409d14;
      **(char **)(param_1 + 0x19c) = *param_2;
      param_2 = param_2 + 1;
      *(int *)(param_1 + 0x19c) = *(int *)(param_1 + 0x19c) + 1;
    }
    cVar1 = *pcVar6;
  } while( true );
}



/* 00409d30 FUN_00409d30 */

void __cdecl FUN_00409d30(char *param_1)

{
  char *pcVar1;
  char cVar2;
  char *pcVar3;
  char *pcVar4;
  
  pcVar3 = param_1;
  pcVar4 = param_1;
  if (*param_1 != '\0') {
    do {
      cVar2 = *pcVar4;
      if (((cVar2 == '\n') || (cVar2 == '\r')) || (cVar2 == ' ')) {
        if ((pcVar3 != param_1) && (pcVar3[-1] != ' ')) {
          *pcVar3 = ' ';
          goto LAB_00409d60;
        }
      }
      else {
        *pcVar3 = cVar2;
LAB_00409d60:
        pcVar3 = pcVar3 + 1;
      }
      pcVar1 = pcVar4 + 1;
      pcVar4 = pcVar4 + 1;
    } while (*pcVar1 != '\0');
    if ((pcVar3 != param_1) && (pcVar3[-1] == ' ')) {
      pcVar3[-1] = '\0';
      return;
    }
  }
  *pcVar3 = '\0';
  return;
}



/* 00409d80 FUN_00409d80 */

undefined4 * __cdecl FUN_00409d80(undefined4 *param_1)

{
  undefined4 *puVar1;
  
  puVar1 = (undefined4 *)(*(code *)*param_1)(0xa8);
  if (puVar1 == (undefined4 *)0x0) {
    return (undefined4 *)0x0;
  }
  FUN_0040a2f0(puVar1 + 0x14,param_1);
  FUN_0040a2f0(puVar1 + 0x1a,param_1);
  FUN_0040a280(puVar1,param_1);
  FUN_0040a280(puVar1 + 5,param_1);
  FUN_0040a280(puVar1 + 10,param_1);
  FUN_0040a280(puVar1 + 0xf,param_1);
  puVar1[0x21] = 0;
  puVar1[0x22] = 0;
  *(undefined1 *)(puVar1 + 0x23) = 0;
  puVar1[0x29] = 0;
  puVar1[0x24] = 0;
  puVar1[0x28] = 0;
  puVar1[0x26] = 0;
  puVar1[0x27] = 0;
  puVar1[0x25] = 0;
  *(undefined1 *)(puVar1 + 0x20) = 1;
  *(undefined1 *)((int)puVar1 + 0x81) = 0;
  *(undefined1 *)((int)puVar1 + 0x82) = 0;
  return puVar1;
}



/* 00409e30 FUN_00409e30 */

void __cdecl FUN_00409e30(int *param_1,char param_2,int param_3)

{
  int iVar1;
  int local_8 [2];
  
  FUN_0040a2a0(local_8,param_1 + 5);
  iVar1 = FUN_0040a2c0(local_8);
  while (iVar1 != 0) {
    if (*(int *)(iVar1 + 0x10) != 0) {
      (**(code **)(param_3 + 8))(*(undefined4 *)(iVar1 + 0x14));
    }
    iVar1 = FUN_0040a2c0(local_8);
  }
  FUN_0040a240(param_1);
  FUN_0040a240(param_1 + 5);
  FUN_0040a240(param_1 + 10);
  FUN_0040a240(param_1 + 0xf);
  FUN_0040a360(param_1 + 0x14);
  FUN_0040a360(param_1 + 0x1a);
  if (param_2 != '\0') {
    (**(code **)(param_3 + 8))(param_1[0x29]);
    (**(code **)(param_3 + 8))(param_1[0x24]);
  }
  (**(code **)(param_3 + 8))(param_1);
  return;
}



/* 00409ee0 FUN_00409ee0 */

undefined4 __cdecl FUN_00409ee0(int *param_1,byte *param_2,uint param_3)

{
  int *piVar1;
  char cVar2;
  undefined4 *puVar3;
  uint uVar4;
  undefined4 uVar5;
  int iVar6;
  uint uVar7;
  byte bVar8;
  byte bVar9;
  byte bVar10;
  int *piVar11;
  uint uVar12;
  uint uVar13;
  undefined4 *puVar14;
  uint uStack_14;
  
  piVar1 = param_1;
  if (param_1[2] == 0) {
    if (param_3 == 0) {
      return 0;
    }
    *(undefined1 *)(param_1 + 1) = 6;
    param_1[2] = 0x40;
    puVar3 = (undefined4 *)(**(code **)param_1[4])(0x100);
    *param_1 = (int)puVar3;
    if (puVar3 == (undefined4 *)0x0) {
      param_1[2] = 0;
      return 0;
    }
    for (iVar6 = 0x40; iVar6 != 0; iVar6 = iVar6 + -1) {
      *puVar3 = 0;
      puVar3 = puVar3 + 1;
    }
    uVar4 = FUN_0040a210(param_2);
    piVar11 = (int *)(uVar4 & param_1[2] - 1U);
  }
  else {
    uVar4 = FUN_0040a210(param_2);
    iVar6 = *param_1;
    uVar13 = param_1[2] - 1;
    piVar11 = (int *)(uVar13 & uVar4);
    bVar10 = 0;
    param_1 = (int *)0x0;
    puVar3 = *(undefined4 **)(iVar6 + (int)piVar11 * 4);
    while (puVar3 != (undefined4 *)0x0) {
      cVar2 = FUN_0040a1e0((char *)param_2,(char *)*puVar3);
      if (cVar2 != '\0') {
        return *(undefined4 *)(*piVar1 + (int)piVar11 * 4);
      }
      if (bVar10 == 0) {
        bVar9 = (byte)((~uVar13 & uVar4) >> ((char)piVar1[1] - 1U & 0x1f)) & (byte)(uVar13 >> 2);
        bVar10 = bVar9 | 1;
        param_1 = (int *)(bVar9 | 1);
      }
      if (piVar11 < param_1) {
        iVar6 = piVar1[2] - (int)param_1;
      }
      else {
        iVar6 = -(int)param_1;
      }
      piVar11 = (int *)((int)piVar11 + iVar6);
      puVar3 = *(undefined4 **)(*piVar1 + (int)piVar11 * 4);
    }
    if (param_3 == 0) {
      return 0;
    }
    bVar10 = *(byte *)(piVar1 + 1);
    if ((uint)piVar1[3] >> (bVar10 - 1 & 0x1f) != 0) {
      uVar12 = 1 << (bVar10 + 1 & 0x1f);
      uVar13 = uVar12 - 1;
      puVar3 = (undefined4 *)(**(code **)piVar1[4])(uVar12 * 4);
      if (puVar3 == (undefined4 *)0x0) {
        return 0;
      }
      puVar14 = puVar3;
      for (uVar7 = uVar12 & 0x3fffffff; uVar7 != 0; uVar7 = uVar7 - 1) {
        *puVar14 = 0;
        puVar14 = puVar14 + 1;
      }
      for (iVar6 = 0; iVar6 != 0; iVar6 = iVar6 + -1) {
        *(undefined1 *)puVar14 = 0;
        puVar14 = (undefined4 *)((int)puVar14 + 1);
      }
      uStack_14 = 0;
      if (piVar1[2] != 0) {
        do {
          puVar14 = *(undefined4 **)(*piVar1 + uStack_14 * 4);
          if (puVar14 != (undefined4 *)0x0) {
            uVar7 = FUN_0040a210((byte *)*puVar14);
            piVar11 = (int *)(uVar7 & uVar13);
            bVar9 = 0;
            param_1 = (int *)0x0;
            iVar6 = puVar3[(int)piVar11];
            while (iVar6 != 0) {
              if (bVar9 == 0) {
                bVar8 = (byte)((~uVar13 & uVar7) >> (bVar10 & 0x1f)) & (byte)(uVar13 >> 2);
                bVar9 = bVar8 | 1;
                param_1 = (int *)(bVar8 | 1);
              }
              if (piVar11 < param_1) {
                iVar6 = uVar12 - (int)param_1;
              }
              else {
                iVar6 = -(int)param_1;
              }
              piVar11 = (int *)((int)piVar11 + iVar6);
              iVar6 = puVar3[(int)piVar11];
            }
            puVar3[(int)piVar11] = *(undefined4 *)(*piVar1 + uStack_14 * 4);
          }
          uStack_14 = uStack_14 + 1;
        } while (uStack_14 < (uint)piVar1[2]);
      }
      (**(code **)(piVar1[4] + 8))(*piVar1);
      piVar1[2] = uVar12;
      *piVar1 = (int)puVar3;
      piVar11 = (int *)(uVar13 & uVar4);
      *(byte *)(piVar1 + 1) = bVar10 + 1;
      bVar9 = 0;
      iVar6 = puVar3[(int)piVar11];
      param_1 = (int *)0x0;
      while (iVar6 != 0) {
        if (bVar9 == 0) {
          bVar8 = (byte)((~uVar13 & uVar4) >> (bVar10 & 0x1f)) & (byte)(uVar13 >> 2);
          bVar9 = bVar8 | 1;
          param_1 = (int *)(bVar8 | 1);
        }
        if (piVar11 < param_1) {
          iVar6 = uVar12 - (int)param_1;
        }
        else {
          iVar6 = -(int)param_1;
        }
        piVar11 = (int *)((int)piVar11 + iVar6);
        iVar6 = puVar3[(int)piVar11];
      }
    }
  }
  uVar5 = (**(code **)piVar1[4])(param_3);
  *(undefined4 *)(*piVar1 + (int)piVar11 * 4) = uVar5;
  puVar3 = *(undefined4 **)(*piVar1 + (int)piVar11 * 4);
  if (puVar3 != (undefined4 *)0x0) {
    for (uVar4 = param_3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *puVar3 = 0;
      puVar3 = puVar3 + 1;
    }
    for (uVar4 = param_3 & 3; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined1 *)puVar3 = 0;
      puVar3 = (undefined4 *)((int)puVar3 + 1);
    }
    **(undefined4 **)(*piVar1 + (int)piVar11 * 4) = param_2;
    piVar1[3] = piVar1[3] + 1;
    return *(undefined4 *)(*piVar1 + (int)piVar11 * 4);
  }
  return 0;
}



/* 0040a1e0 FUN_0040a1e0 */

undefined1 __cdecl FUN_0040a1e0(char *param_1,char *param_2)

{
  char *pcVar1;
  char cVar2;
  
  cVar2 = *param_1;
  if (cVar2 == *param_2) {
    do {
      if (cVar2 == '\0') {
        return 1;
      }
      cVar2 = param_1[1];
      pcVar1 = param_2 + 1;
      param_1 = param_1 + 1;
      param_2 = param_2 + 1;
    } while (cVar2 == *pcVar1);
  }
  return 0;
}



/* 0040a210 FUN_0040a210 */

uint __cdecl FUN_0040a210(byte *param_1)

{
  byte *pbVar1;
  byte bVar2;
  uint uVar3;
  
  uVar3 = 0;
  bVar2 = *param_1;
  while (bVar2 != 0) {
    uVar3 = uVar3 * 0xf4243 ^ (uint)bVar2;
    pbVar1 = param_1 + 1;
    param_1 = param_1 + 1;
    bVar2 = *pbVar1;
  }
  return uVar3;
}



/* 0040a240 FUN_0040a240 */

void __cdecl FUN_0040a240(int *param_1)

{
  uint uVar1;
  
  uVar1 = 0;
  if (param_1[2] != 0) {
    do {
      (**(code **)(param_1[4] + 8))(*(undefined4 *)(*param_1 + uVar1 * 4));
      uVar1 = uVar1 + 1;
    } while (uVar1 < (uint)param_1[2]);
  }
  (**(code **)(param_1[4] + 8))(*param_1);
  return;
}



/* 0040a280 FUN_0040a280 */

void __cdecl FUN_0040a280(undefined4 *param_1,undefined4 param_2)

{
  *(undefined1 *)(param_1 + 1) = 0;
  param_1[2] = 0;
  param_1[3] = 0;
  *param_1 = 0;
  param_1[4] = param_2;
  return;
}



/* 0040a2a0 FUN_0040a2a0 */

void __cdecl FUN_0040a2a0(int *param_1,int *param_2)

{
  int iVar1;
  
  iVar1 = *param_2;
  *param_1 = iVar1;
  param_1[1] = iVar1 + param_2[2] * 4;
  return;
}



/* 0040a2c0 FUN_0040a2c0 */

int __cdecl FUN_0040a2c0(int *param_1)

{
  int iVar1;
  int *piVar2;
  
  piVar2 = (int *)*param_1;
  do {
    if (piVar2 == (int *)param_1[1]) {
      return 0;
    }
    iVar1 = *(int *)*param_1;
    piVar2 = (int *)*param_1 + 1;
    *param_1 = (int)piVar2;
  } while (iVar1 == 0);
  return iVar1;
}



/* 0040a2f0 FUN_0040a2f0 */

void __cdecl FUN_0040a2f0(undefined4 *param_1,undefined4 param_2)

{
  *param_1 = 0;
  param_1[1] = 0;
  param_1[4] = 0;
  param_1[3] = 0;
  param_1[2] = 0;
  param_1[5] = param_2;
  return;
}



/* 0040a310 FUN_0040a310 */

void __cdecl FUN_0040a310(undefined4 *param_1)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  
  puVar1 = (undefined4 *)*param_1;
  if (param_1[1] == 0) {
    param_1[1] = puVar1;
  }
  else {
    while (puVar1 != (undefined4 *)0x0) {
      puVar2 = (undefined4 *)*puVar1;
      *puVar1 = param_1[1];
      param_1[1] = puVar1;
      puVar1 = puVar2;
    }
  }
  *param_1 = 0;
  param_1[4] = 0;
  param_1[3] = 0;
  param_1[2] = 0;
  return;
}



/* 0040a360 FUN_0040a360 */

void __cdecl FUN_0040a360(undefined4 *param_1)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  
  puVar2 = (undefined4 *)*param_1;
  while (puVar2 != (undefined4 *)0x0) {
    puVar1 = (undefined4 *)*puVar2;
    (**(code **)(param_1[5] + 8))(puVar2);
    puVar2 = puVar1;
  }
  puVar2 = (undefined4 *)param_1[1];
  while (puVar2 != (undefined4 *)0x0) {
    puVar1 = (undefined4 *)*puVar2;
    (**(code **)(param_1[5] + 8))(puVar2);
    puVar2 = puVar1;
  }
  return;
}



/* 0040a3a0 FUN_0040a3a0 */

int __cdecl FUN_0040a3a0(int *param_1,int param_2,int param_3,int param_4)

{
  int iVar1;
  undefined4 uVar2;
  
  if ((param_1[3] != 0) || (uVar2 = FUN_0040a510(param_1), (char)uVar2 != '\0')) {
    iVar1 = param_4;
    (**(code **)(param_2 + 0x38))(param_2,&param_3,param_4,param_1 + 3,param_1[2]);
    while( true ) {
      if (param_3 == iVar1) {
        return param_1[4];
      }
      uVar2 = FUN_0040a510(param_1);
      if ((char)uVar2 == '\0') break;
      (**(code **)(param_2 + 0x38))(param_2,&param_3,iVar1,param_1 + 3,param_1[2]);
    }
  }
  return 0;
}



/* 0040a420 FUN_0040a420 */

int __cdecl FUN_0040a420(int *param_1,char *param_2)

{
  char cVar1;
  int iVar2;
  int iVar3;
  undefined4 uVar4;
  
  while ((param_1[3] != param_1[2] || (uVar4 = FUN_0040a510(param_1), (char)uVar4 != '\0'))) {
    *(char *)param_1[3] = *param_2;
    iVar2 = param_1[3];
    param_1[3] = iVar2 + 1;
    cVar1 = *param_2;
    param_2 = param_2 + 1;
    if (cVar1 == '\0') {
      iVar3 = param_1[4];
      param_1[4] = iVar2 + 1;
      return iVar3;
    }
  }
  return 0;
}



/* 0040a470 FUN_0040a470 */

int __cdecl FUN_0040a470(int *param_1,char *param_2)

{
  char cVar1;
  undefined4 uVar2;
  
  cVar1 = *param_2;
  while( true ) {
    if (cVar1 == '\0') {
      return param_1[4];
    }
    if ((param_1[3] == param_1[2]) && (uVar2 = FUN_0040a510(param_1), (char)uVar2 == '\0')) break;
    *(char *)param_1[3] = *param_2;
    param_2 = param_2 + 1;
    param_1[3] = param_1[3] + 1;
    cVar1 = *param_2;
  }
  return 0;
}



/* 0040a4c0 FUN_0040a4c0 */

int __cdecl FUN_0040a4c0(int *param_1,int param_2,int param_3,int param_4)

{
  int iVar1;
  undefined4 uVar2;
  
  iVar1 = FUN_0040a3a0(param_1,param_2,param_3,param_4);
  if (iVar1 == 0) {
    return 0;
  }
  if (param_1[3] == param_1[2]) {
    uVar2 = FUN_0040a510(param_1);
    if ((char)uVar2 == '\0') {
      return 0;
    }
  }
  *(undefined1 *)param_1[3] = 0;
  param_1[3] = param_1[3] + 1;
  return param_1[4];
}



/* 0040a510 FUN_0040a510 */

undefined4 __cdecl FUN_0040a510(int *param_1)

{
  int iVar1;
  int iVar2;
  int *piVar3;
  uint uVar4;
  uint uVar5;
  undefined4 *puVar6;
  int iVar7;
  int *piVar8;
  undefined4 *puVar9;
  int *piVar10;
  
  piVar3 = (int *)param_1[1];
  if (piVar3 != (int *)0x0) {
    if (param_1[4] == 0) {
      *param_1 = (int)piVar3;
      param_1[1] = *piVar3;
      *(undefined4 *)*param_1 = 0;
      iVar1 = *param_1 + 8;
      param_1[4] = iVar1;
      iVar7 = *(int *)(*param_1 + 4);
      param_1[3] = iVar1;
      param_1[2] = iVar7 + iVar1;
      return CONCAT31((int3)((uint)iVar1 >> 8),1);
    }
    if (param_1[2] - param_1[4] < piVar3[1]) {
      iVar1 = *piVar3;
      *piVar3 = *param_1;
      iVar7 = param_1[1];
      param_1[1] = iVar1;
      uVar4 = param_1[2] - param_1[4];
      *param_1 = iVar7;
      puVar6 = (undefined4 *)param_1[4];
      puVar9 = (undefined4 *)(iVar7 + 8);
      for (uVar5 = uVar4 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
        *puVar9 = *puVar6;
        puVar6 = puVar6 + 1;
        puVar9 = puVar9 + 1;
      }
      for (uVar4 = uVar4 & 3; uVar4 != 0; uVar4 = uVar4 - 1) {
        *(undefined1 *)puVar9 = *(undefined1 *)puVar6;
        puVar6 = (undefined4 *)((int)puVar6 + 1);
        puVar9 = (undefined4 *)((int)puVar9 + 1);
      }
      iVar1 = *param_1;
      param_1[3] = param_1[3] + (iVar1 - param_1[4]) + 8;
      param_1[4] = iVar1 + 8;
      param_1[2] = *(int *)(iVar1 + 4) + iVar1 + 8;
      return CONCAT31((int3)((uint)iVar1 >> 8),1);
    }
  }
  iVar1 = *param_1;
  if ((iVar1 != 0) && (param_1[4] == iVar1 + 8)) {
    iVar7 = (param_1[2] - param_1[4]) * 2;
    iVar1 = (**(code **)(param_1[5] + 4))(iVar1,iVar7 + 8);
    *param_1 = iVar1;
    if (iVar1 == 0) {
      return 0;
    }
    *(int *)(iVar1 + 4) = iVar7;
    iVar1 = param_1[4];
    iVar2 = *param_1 + 8;
    param_1[4] = iVar2;
    iVar2 = iVar2 + iVar7;
    param_1[3] = param_1[3] + (*param_1 - iVar1) + 8;
    param_1[2] = iVar2;
    return CONCAT31((int3)((uint)iVar2 >> 8),1);
  }
  iVar1 = 0x400;
  if (0x3ff < param_1[2] - param_1[4]) {
    iVar1 = (param_1[2] - param_1[4]) * 2;
  }
  piVar3 = (int *)(**(code **)param_1[5])(iVar1 + 8);
  if (piVar3 == (int *)0x0) {
    return 0;
  }
  piVar3[1] = iVar1;
  *piVar3 = *param_1;
  piVar8 = (int *)param_1[4];
  *param_1 = (int)piVar3;
  if ((int *)param_1[3] != piVar8) {
    uVar4 = param_1[3] - (int)piVar8;
    piVar10 = piVar3 + 2;
    for (uVar5 = uVar4 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      *piVar10 = *piVar8;
      piVar8 = piVar8 + 1;
      piVar10 = piVar10 + 1;
    }
    for (uVar4 = uVar4 & 3; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(char *)piVar10 = (char)*piVar8;
      piVar8 = (int *)((int)piVar8 + 1);
      piVar10 = (int *)((int)piVar10 + 1);
    }
  }
  param_1[3] = (int)piVar3 + param_1[3] + (8 - param_1[4]);
  iVar1 = (int)piVar3 + iVar1 + 8;
  param_1[4] = (int)(piVar3 + 2);
  param_1[2] = iVar1;
  return CONCAT31((int3)((uint)iVar1 >> 8),1);
}



/* 0040a690 FUN_0040a690 */

int __cdecl FUN_0040a690(int param_1)

{
  int iVar1;
  int iVar2;
  int iVar3;
  int iVar4;
  undefined4 *puVar5;
  int iVar6;
  
  iVar1 = *(int *)(param_1 + 0x154);
  if (*(int *)(iVar1 + 0xa4) == 0) {
    puVar5 = (undefined4 *)(**(code **)(param_1 + 0xc))(*(int *)(param_1 + 0x1c4) << 2);
    *(undefined4 **)(iVar1 + 0xa4) = puVar5;
    if (puVar5 == (undefined4 *)0x0) {
      return -1;
    }
    *puVar5 = 0;
  }
  if (*(uint *)(iVar1 + 0x98) <= *(uint *)(iVar1 + 0x9c)) {
    if (*(int *)(iVar1 + 0x90) == 0) {
      iVar6 = (**(code **)(param_1 + 0xc))(0x380);
      if (iVar6 == 0) {
        return -1;
      }
      *(undefined4 *)(iVar1 + 0x98) = 0x20;
    }
    else {
      iVar6 = (**(code **)(param_1 + 0x10))(*(int *)(iVar1 + 0x90),*(uint *)(iVar1 + 0x98) * 0x38);
      if (iVar6 == 0) {
        return -1;
      }
      *(int *)(iVar1 + 0x98) = *(int *)(iVar1 + 0x98) << 1;
    }
    *(int *)(iVar1 + 0x90) = iVar6;
  }
  iVar2 = *(int *)(iVar1 + 0x9c);
  *(int *)(iVar1 + 0x9c) = iVar2 + 1;
  iVar3 = *(int *)(iVar1 + 0x90);
  iVar6 = iVar3 + iVar2 * 0x1c;
  if (*(int *)(iVar1 + 0xa0) != 0) {
    iVar4 = *(int *)(*(int *)(iVar1 + 0xa4) + -4 + *(int *)(iVar1 + 0xa0) * 4);
    iVar1 = iVar3 + iVar4 * 0x1c;
    iVar4 = *(int *)(iVar3 + 0x10 + iVar4 * 0x1c);
    if (iVar4 != 0) {
      *(int *)(iVar3 + 0x18 + iVar4 * 0x1c) = iVar2;
    }
    if (*(int *)(iVar1 + 0x14) == 0) {
      *(int *)(iVar1 + 0xc) = iVar2;
    }
    *(int *)(iVar1 + 0x10) = iVar2;
    *(int *)(iVar1 + 0x14) = *(int *)(iVar1 + 0x14) + 1;
  }
  *(undefined4 *)(iVar6 + 0x18) = 0;
  *(undefined4 *)(iVar6 + 0x14) = 0;
  *(undefined4 *)(iVar6 + 0x10) = 0;
  *(undefined4 *)(iVar6 + 0xc) = 0;
  return iVar2;
}



/* 0040a7c0 FUN_0040a7c0 */

int * __cdecl FUN_0040a7c0(int *param_1)

{
  int iVar1;
  int *piVar2;
  int *piVar3;
  int *piStack_4;
  
  piVar2 = param_1;
  iVar1 = param_1[0x55];
  piVar3 = (int *)(*(code *)param_1[3])(*(int *)(iVar1 + 0x94) + *(int *)(iVar1 + 0x9c) * 0x14);
  if (piVar3 == (int *)0x0) {
    return (int *)0x0;
  }
  piStack_4 = piVar3 + 5;
  param_1 = piVar3 + *(int *)(iVar1 + 0x9c) * 5;
  FUN_0040a830((int)piVar2,0,piVar3,(int *)&piStack_4,(int *)&param_1);
  return piVar3;
}



/* 0040a830 FUN_0040a830 */

void __cdecl FUN_0040a830(int param_1,int param_2,int *param_3,int *param_4,int *param_5)

{
  char *pcVar1;
  char cVar2;
  int iVar3;
  int iVar4;
  char *pcVar5;
  uint uVar6;
  char *pcVar7;
  
  iVar3 = *(int *)(param_1 + 0x154);
  iVar4 = param_2 * 0x1c;
  *param_3 = *(int *)(*(int *)(iVar3 + 0x90) + iVar4);
  param_3[1] = *(int *)(*(int *)(iVar3 + 0x90) + 4 + iVar4);
  if (*param_3 == 4) {
    param_3[2] = *param_5;
    pcVar5 = *(char **)(*(int *)(iVar3 + 0x90) + 8 + iVar4);
    *(char *)*param_5 = *pcVar5;
    pcVar7 = (char *)(*param_5 + 1);
    *param_5 = (int)pcVar7;
    cVar2 = *pcVar5;
    while (cVar2 != '\0') {
      pcVar1 = pcVar5 + 1;
      pcVar5 = pcVar5 + 1;
      *pcVar7 = *pcVar1;
      pcVar7 = (char *)(*param_5 + 1);
      *param_5 = (int)pcVar7;
      cVar2 = *pcVar5;
    }
    param_3[3] = 0;
    param_3[4] = 0;
    return;
  }
  uVar6 = 0;
  param_3[3] = *(int *)(*(int *)(iVar3 + 0x90) + 0x14 + iVar4);
  param_3[4] = *param_4;
  *param_4 = *param_4 + param_3[3] * 0x14;
  iVar4 = *(int *)(*(int *)(iVar3 + 0x90) + 0xc + iVar4);
  if (param_3[3] != 0) {
    param_2 = 0;
    do {
      FUN_0040a830(param_1,iVar4,(int *)(param_3[4] + param_2),param_4,param_5);
      param_2 = param_2 + 0x14;
      uVar6 = uVar6 + 1;
      iVar4 = *(int *)(*(int *)(iVar3 + 0x90) + 0x18 + iVar4 * 0x1c);
    } while (uVar6 < (uint)param_3[3]);
  }
  param_3[2] = 0;
  return;
}



/* 0040a950 FUN_0040a950 */

int * __cdecl FUN_0040a950(int param_1,int param_2,int param_3,int param_4)

{
  byte *pbVar1;
  int *piVar2;
  int iVar3;
  
  iVar3 = *(int *)(param_1 + 0x154);
  pbVar1 = (byte *)FUN_0040a4c0((int *)(iVar3 + 0x50),param_2,param_3,param_4);
  if (pbVar1 == (byte *)0x0) {
    return (int *)0x0;
  }
  piVar2 = (int *)FUN_00409ee0((int *)(iVar3 + 0x14),pbVar1,0x18);
  if (piVar2 == (int *)0x0) {
    return (int *)0x0;
  }
  if ((byte *)*piVar2 == pbVar1) {
    *(undefined4 *)(iVar3 + 0x60) = *(undefined4 *)(iVar3 + 0x5c);
    iVar3 = FUN_004094b0(param_1,piVar2);
    if (iVar3 == 0) {
      return (int *)0x0;
    }
  }
  else {
    *(undefined4 *)(iVar3 + 0x5c) = *(undefined4 *)(iVar3 + 0x60);
  }
  return piVar2;
}



/* 0040a9d0 FUN_0040a9d0 */

int __thiscall FUN_0040a9d0(void *this,byte *param_1)

{
  uint uVar1;
  uint uVar2;
  int iVar3;
  uint uVar4;
  byte *pbVar5;
  undefined *puVar6;
  
  while( true ) {
    if (DAT_00430714 < 2) {
      uVar1 = (byte)PTR_DAT_00430508[(uint)*param_1 * 2] & 8;
      this = PTR_DAT_00430508;
    }
    else {
      puVar6 = (undefined *)0x8;
      uVar1 = FUN_0040b9f6(this,(uint)*param_1,8);
      this = puVar6;
    }
    if (uVar1 == 0) break;
    param_1 = param_1 + 1;
  }
  uVar1 = (uint)*param_1;
  pbVar5 = param_1 + 1;
  if ((uVar1 == 0x2d) || (uVar4 = uVar1, uVar1 == 0x2b)) {
    uVar4 = (uint)*pbVar5;
    pbVar5 = param_1 + 2;
  }
  iVar3 = 0;
  while( true ) {
    if (DAT_00430714 < 2) {
      uVar2 = (byte)PTR_DAT_00430508[uVar4 * 2] & 4;
    }
    else {
      puVar6 = (undefined *)0x4;
      uVar2 = FUN_0040b9f6(this,uVar4,4);
      this = puVar6;
    }
    if (uVar2 == 0) break;
    iVar3 = (uVar4 - 0x30) + iVar3 * 10;
    uVar4 = (uint)*pbVar5;
    pbVar5 = pbVar5 + 1;
  }
  if (uVar1 == 0x2d) {
    iVar3 = -iVar3;
  }
  return iVar3;
}



/* 0040aa5b FUN_0040aa5b */

void __thiscall FUN_0040aa5b(void *this,byte *param_1)

{
  FUN_0040a9d0(this,param_1);
  return;
}



/* 0040aa66 FUN_0040aa66 */

int __cdecl FUN_0040aa66(byte *param_1)

{
  int iVar1;
  int iVar2;
  
  iVar1 = FUN_0040baa4((undefined4 *)&DAT_004302a0);
  iVar2 = FUN_0040bb6e((int *)&DAT_004302a0,param_1,(undefined4 *)&stack0x00000008);
  FUN_0040bb31(iVar1,(int *)&DAT_004302a0);
  return iVar2;
}



/* 0040aa97 FUN_0040aa97 */

void __cdecl FUN_0040aa97(LPVOID param_1)

{
  uint *puVar1;
  
  if (param_1 != (LPVOID)0x0) {
    puVar1 = (uint *)FUN_0040c3f2((int)param_1);
    if (puVar1 != (uint *)0x0) {
      FUN_0040c41d(puVar1,(uint)param_1);
      return;
    }
    HeapFree(DAT_0045577c,0,param_1);
  }
  return;
}



/* 0040aac6 _malloc */

/* Library Function - Single Match
    _malloc
   
   Library: Visual Studio 2003 Release */

void * __cdecl _malloc(size_t _Size)

{
  void *pvVar1;
  
  pvVar1 = __nh_malloc(_Size,DAT_00452e40);
  return pvVar1;
}



/* 0040aad8 __nh_malloc */

/* Library Function - Single Match
    __nh_malloc
   
   Library: Visual Studio 2003 Release */

void * __cdecl __nh_malloc(size_t _Size,int _NhFlag)

{
  void *pvVar1;
  int iVar2;
  
  if (_Size < 0xffffffe1) {
    do {
      pvVar1 = (void *)FUN_0040ab04((uint *)_Size);
      if (pvVar1 != (void *)0x0) {
        return pvVar1;
      }
      if (_NhFlag == 0) {
        return (void *)0x0;
      }
      iVar2 = FUN_0040cef3(_Size);
    } while (iVar2 != 0);
  }
  return (void *)0x0;
}



/* 0040ab04 FUN_0040ab04 */

void __cdecl FUN_0040ab04(uint *param_1)

{
  int *piVar1;
  
  if ((param_1 <= DAT_0043072c) && (piVar1 = FUN_0040c748(param_1), piVar1 != (int *)0x0)) {
    return;
  }
  if (param_1 == (uint *)0x0) {
    param_1 = (uint *)0x1;
  }
  HeapAlloc(DAT_0045577c,0,(int)param_1 + 0xfU & 0xfffffff0);
  return;
}



/* 0040ab40 _strncpy */

/* Library Function - Single Match
    _strncpy
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release */

char * __cdecl _strncpy(char *_Dest,char *_Source,size_t _Count)

{
  uint uVar1;
  uint uVar2;
  char cVar3;
  uint uVar4;
  uint *puVar5;
  
  if (_Count == 0) {
    return _Dest;
  }
  puVar5 = (uint *)_Dest;
  if (((uint)_Source & 3) != 0) {
    while( true ) {
      uVar4 = *(uint *)_Source;
      _Source = (char *)((int)_Source + 1);
      *(char *)puVar5 = (char)uVar4;
      puVar5 = (uint *)((int)puVar5 + 1);
      _Count = _Count - 1;
      if (_Count == 0) {
        return _Dest;
      }
      if ((char)uVar4 == '\0') break;
      if (((uint)_Source & 3) == 0) {
        uVar4 = _Count >> 2;
        goto joined_r0x0040ab7e;
      }
    }
    do {
      if (((uint)puVar5 & 3) == 0) {
        uVar4 = _Count >> 2;
        cVar3 = '\0';
        if (uVar4 == 0) goto LAB_0040abbb;
        goto LAB_0040ac29;
      }
      *(char *)puVar5 = '\0';
      puVar5 = (uint *)((int)puVar5 + 1);
      _Count = _Count - 1;
    } while (_Count != 0);
    return _Dest;
  }
  uVar4 = _Count >> 2;
  if (uVar4 != 0) {
    do {
      uVar1 = *(uint *)_Source;
      uVar2 = *(uint *)_Source;
      _Source = (char *)((int)_Source + 4);
      if (((uVar1 ^ 0xffffffff ^ uVar1 + 0x7efefeff) & 0x81010100) != 0) {
        if ((char)uVar2 == '\0') {
          *puVar5 = 0;
joined_r0x0040ac25:
          while( true ) {
            uVar4 = uVar4 - 1;
            puVar5 = puVar5 + 1;
            if (uVar4 == 0) break;
LAB_0040ac29:
            *puVar5 = 0;
          }
          cVar3 = '\0';
          _Count = _Count & 3;
          if (_Count != 0) goto LAB_0040abbb;
          return _Dest;
        }
        if ((char)(uVar2 >> 8) == '\0') {
          *puVar5 = uVar2 & 0xff;
          goto joined_r0x0040ac25;
        }
        if ((uVar2 & 0xff0000) == 0) {
          *puVar5 = uVar2 & 0xffff;
          goto joined_r0x0040ac25;
        }
        if ((uVar2 & 0xff000000) == 0) {
          *puVar5 = uVar2;
          goto joined_r0x0040ac25;
        }
      }
      *puVar5 = uVar2;
      puVar5 = puVar5 + 1;
      uVar4 = uVar4 - 1;
joined_r0x0040ab7e:
    } while (uVar4 != 0);
    _Count = _Count & 3;
    if (_Count == 0) {
      return _Dest;
    }
  }
  do {
    cVar3 = (char)*(uint *)_Source;
    _Source = (char *)((int)_Source + 1);
    *(char *)puVar5 = cVar3;
    puVar5 = (uint *)((int)puVar5 + 1);
    if (cVar3 == '\0') {
      while (_Count = _Count - 1, _Count != 0) {
LAB_0040abbb:
        *(char *)puVar5 = cVar3;
        puVar5 = (uint *)((int)puVar5 + 1);
      }
      return _Dest;
    }
    _Count = _Count - 1;
  } while (_Count != 0);
  return _Dest;
}



/* 0040ac3e FUN_0040ac3e */

int __cdecl FUN_0040ac3e(int *param_1,byte *param_2)

{
  int iVar1;
  int iVar2;
  
  iVar1 = FUN_0040baa4(param_1);
  iVar2 = FUN_0040bb6e(param_1,param_2,(undefined4 *)&stack0x0000000c);
  FUN_0040bb31(iVar1,param_1);
  return iVar2;
}



/* 0040ad29 FUN_0040ad29 */

uint __cdecl FUN_0040ad29(char *param_1,uint param_2,uint param_3,int *param_4)

{
  int *piVar1;
  char *pcVar2;
  int iVar3;
  uint uVar4;
  char *pcVar5;
  char *pcVar6;
  char *pcVar7;
  
  piVar1 = param_4;
  pcVar6 = (char *)(param_2 * param_3);
  if (pcVar6 == (char *)0x0) {
    param_3 = 0;
  }
  else {
    pcVar5 = param_1;
    param_1 = pcVar6;
    if ((*(ushort *)(param_4 + 3) & 0x10c) == 0) {
      param_4 = (int *)0x1000;
    }
    else {
      param_4 = (int *)param_4[6];
    }
    do {
      if (((*(ushort *)(piVar1 + 3) & 0x10c) == 0) ||
         (pcVar2 = (char *)piVar1[1], pcVar2 == (char *)0x0)) {
        if (param_1 < param_4) {
          uVar4 = FUN_0040d39d(piVar1);
          if (uVar4 == 0xffffffff) goto LAB_0040ae05;
          *pcVar5 = (char)uVar4;
          param_4 = (int *)piVar1[6];
          pcVar5 = pcVar5 + 1;
          param_1 = param_1 + -1;
        }
        else {
          pcVar2 = param_1;
          if (param_4 != (int *)0x0) {
            pcVar2 = param_1 + -((uint)param_1 % (uint)param_4);
          }
          iVar3 = FUN_0040d476(piVar1[4],pcVar5,pcVar2);
          if (iVar3 == 0) {
            piVar1[3] = piVar1[3] | 0x10;
LAB_0040ae05:
            return (uint)((int)pcVar6 - (int)param_1) / param_2;
          }
          if (iVar3 == -1) {
            piVar1[3] = piVar1[3] | 0x20;
            goto LAB_0040ae05;
          }
          param_1 = param_1 + -iVar3;
          pcVar5 = pcVar5 + iVar3;
        }
      }
      else {
        pcVar7 = param_1;
        if (pcVar2 <= param_1) {
          pcVar7 = pcVar2;
        }
        FUN_0040d670((undefined4 *)pcVar5,(undefined4 *)*piVar1,(uint)pcVar7);
        param_1 = param_1 + -(int)pcVar7;
        piVar1[1] = piVar1[1] - (int)pcVar7;
        *piVar1 = (int)(pcVar7 + *piVar1);
        pcVar5 = pcVar5 + (int)pcVar7;
      }
    } while (param_1 != (char *)0x0);
  }
  return param_3;
}



/* 0040ae11 FUN_0040ae11 */

uint __cdecl FUN_0040ae11(char *param_1,uint param_2,uint param_3,int *param_4)

{
  int *piVar1;
  int iVar2;
  int *piVar3;
  uint uVar4;
  int *piVar5;
  int *piVar6;
  int *piVar7;
  
  piVar1 = param_4;
  piVar6 = (int *)(param_2 * param_3);
  if (piVar6 == (int *)0x0) {
    param_3 = 0;
  }
  else {
    piVar5 = piVar6;
    if ((*(ushort *)(param_4 + 3) & 0x10c) == 0) {
      param_4 = (int *)0x1000;
    }
    else {
      param_4 = (int *)param_4[6];
    }
    do {
      if (((piVar1[3] & 0x108U) == 0) || (piVar7 = (int *)piVar1[1], piVar7 == (int *)0x0)) {
        if (param_4 <= piVar5) {
          if (((piVar1[3] & 0x108U) != 0) && (iVar2 = FUN_0040d2cb(piVar1), iVar2 != 0)) {
LAB_0040af12:
            return (uint)((int)piVar6 - (int)piVar5) / param_2;
          }
          piVar7 = piVar5;
          if (param_4 != (int *)0x0) {
            piVar7 = (int *)((int)piVar5 - (uint)piVar5 % (uint)param_4);
          }
          piVar3 = (int *)FUN_0040daba(piVar1[4],param_1,(uint)piVar7);
          if ((piVar3 == (int *)0xffffffff) ||
             (piVar5 = (int *)((int)piVar5 - (int)piVar3), piVar3 < piVar7)) {
            piVar1[3] = piVar1[3] | 0x20;
            goto LAB_0040af12;
          }
          goto LAB_0040aec9;
        }
        uVar4 = FUN_0040d9a5((int)*param_1,piVar1);
        if (uVar4 == 0xffffffff) goto LAB_0040af12;
        param_1 = param_1 + 1;
        param_4 = (int *)piVar1[6];
        piVar5 = (int *)((int)piVar5 - 1);
        if ((int)param_4 < 1) {
          param_4 = (int *)0x1;
        }
      }
      else {
        piVar3 = piVar5;
        if (piVar7 <= piVar5) {
          piVar3 = piVar7;
        }
        FUN_0040d670((undefined4 *)*piVar1,(undefined4 *)param_1,(uint)piVar3);
        piVar1[1] = piVar1[1] - (int)piVar3;
        *piVar1 = *piVar1 + (int)piVar3;
        piVar5 = (int *)((int)piVar5 - (int)piVar3);
LAB_0040aec9:
        param_1 = param_1 + (int)piVar3;
      }
    } while (piVar5 != (int *)0x0);
  }
  return param_3;
}



/* 0040af1b FUN_0040af1b */

undefined4 __cdecl FUN_0040af1b(FILE *param_1)

{
  int iVar1;
  undefined4 uVar2;
  
  uVar2 = 0xffffffff;
  if ((param_1->_flag & 0x40U) == 0) {
    if ((param_1->_flag & 0x83U) != 0) {
      uVar2 = FUN_0040d2cb((int *)param_1);
      __freebuf(param_1);
      iVar1 = FUN_0040dc67(param_1->_file);
      if (iVar1 < 0) {
        uVar2 = 0xffffffff;
      }
      else if (param_1->_tmpfname != (char *)0x0) {
        FUN_0040aa97(param_1->_tmpfname);
        param_1->_tmpfname = (char *)0x0;
      }
    }
  }
  else {
    uVar2 = 0xffffffff;
  }
  param_1->_flag = 0;
  return uVar2;
}



/* 0040af71 FUN_0040af71 */

void __cdecl FUN_0040af71(LPCSTR param_1,char *param_2,uint param_3)

{
  undefined4 *puVar1;
  
  puVar1 = FUN_0040deb5();
  if (puVar1 == (undefined4 *)0x0) {
    return;
  }
  FUN_0040dd45(param_1,param_2,param_3,puVar1);
  return;
}



/* 0040af91 FUN_0040af91 */

void __cdecl FUN_0040af91(LPCSTR param_1,char *param_2)

{
  FUN_0040af71(param_1,param_2,0x40);
  return;
}



/* 0040afb0 FUN_0040afb0 */

/* WARNING: Unable to track spacebase fully for stack */

void FUN_0040afb0(void)

{
  uint in_EAX;
  undefined1 *puVar1;
  undefined4 unaff_retaddr;
  
  puVar1 = &stack0x00000004;
  for (; 0xfff < in_EAX; in_EAX = in_EAX - 0x1000) {
    puVar1 = puVar1 + -0x1000;
  }
  *(undefined4 *)(puVar1 + (-4 - in_EAX)) = unaff_retaddr;
  return;
}



/* 0040afdf FUN_0040afdf */

uint __cdecl FUN_0040afdf(undefined4 *param_1)

{
  int *piVar1;
  byte bVar2;
  uint uVar3;
  
  piVar1 = param_1 + 1;
  *piVar1 = *piVar1 + -1;
  if (-1 < *piVar1) {
    bVar2 = *(byte *)*param_1;
    *param_1 = (byte *)*param_1 + 1;
    return (uint)bVar2;
  }
  uVar3 = FUN_0040d39d(param_1);
  return uVar3;
}



/* 0040aff9 FUN_0040aff9 */

void __cdecl FUN_0040aff9(int *param_1)

{
  uint uVar1;
  undefined *puVar2;
  
  uVar1 = param_1[4];
  FUN_0040d2cb(param_1);
  param_1[3] = param_1[3] & 0xffffffcf;
  if (uVar1 == 0xffffffff) {
    puVar2 = &DAT_00430730;
  }
  else {
    puVar2 = (undefined *)((&DAT_00455660)[(int)uVar1 >> 5] + (uVar1 & 0x1f) * 8);
  }
  puVar2[4] = puVar2[4] & 0xfd;
  if ((param_1[3] & 0x80U) != 0) {
    param_1[3] = param_1[3] & 0xfffffffc;
  }
  FUN_0040df2d(uVar1,0,0);
  return;
}



/* 0040b04d FUN_0040b04d */

int __cdecl FUN_0040b04d(char *param_1)

{
  uint uVar1;
  uint uVar2;
  byte bVar3;
  char *pcVar4;
  DWORD DVar5;
  char *pcVar6;
  char *pcVar7;
  char *pcVar8;
  int iVar9;
  int local_c;
  DWORD local_8;
  
  pcVar7 = param_1;
  uVar1 = *(uint *)(param_1 + 0x10);
  if (*(int *)(param_1 + 4) < 0) {
    param_1[4] = '\0';
    param_1[5] = '\0';
    param_1[6] = '\0';
    param_1[7] = '\0';
  }
  local_8 = FUN_0040df2d(uVar1,0,1);
  if ((int)local_8 < 0) {
LAB_0040b0d7:
    local_c = -1;
  }
  else {
    uVar2 = *(uint *)(param_1 + 0xc);
    if ((uVar2 & 0x108) == 0) {
      return local_8 - *(int *)(param_1 + 4);
    }
    pcVar4 = *(char **)param_1;
    pcVar6 = *(char **)(param_1 + 8);
    local_c = (int)pcVar4 - (int)pcVar6;
    if ((uVar2 & 3) == 0) {
      if ((uVar2 & 0x80) == 0) {
        DAT_00452e48 = 0x16;
        goto LAB_0040b0d7;
      }
    }
    else {
      pcVar8 = pcVar6;
      if ((*(byte *)((&DAT_00455660)[(int)uVar1 >> 5] + 4 + (uVar1 & 0x1f) * 8) & 0x80) != 0) {
        for (; pcVar8 < pcVar4; pcVar8 = pcVar8 + 1) {
          if (*pcVar8 == '\n') {
            local_c = local_c + 1;
          }
        }
      }
    }
    if (local_8 != 0) {
      if ((param_1[0xc] & 1U) != 0) {
        if (*(int *)(param_1 + 4) == 0) {
          local_c = 0;
        }
        else {
          pcVar4 = pcVar4 + (*(int *)(param_1 + 4) - (int)pcVar6);
          iVar9 = (uVar1 & 0x1f) * 8;
          if ((*(byte *)(iVar9 + 4 + (&DAT_00455660)[(int)uVar1 >> 5]) & 0x80) != 0) {
            DVar5 = FUN_0040df2d(uVar1,0,2);
            if (DVar5 == local_8) {
              pcVar6 = *(char **)(param_1 + 8);
              pcVar8 = pcVar4 + (int)pcVar6;
              param_1 = pcVar4;
              for (; pcVar6 < pcVar8; pcVar6 = pcVar6 + 1) {
                if (*pcVar6 == '\n') {
                  param_1 = param_1 + 1;
                }
              }
              bVar3 = pcVar7[0xd] & 0x20;
            }
            else {
              FUN_0040df2d(uVar1,local_8,0);
              pcVar7 = (char *)0x200;
              if ((((char *)0x200 < pcVar4) || ((*(uint *)(param_1 + 0xc) & 8) == 0)) ||
                 ((*(uint *)(param_1 + 0xc) & 0x400) != 0)) {
                pcVar7 = *(char **)(param_1 + 0x18);
              }
              bVar3 = *(byte *)(iVar9 + 4 + (&DAT_00455660)[(int)uVar1 >> 5]) & 4;
              param_1 = pcVar7;
            }
            pcVar4 = param_1;
            if (bVar3 != 0) {
              pcVar4 = param_1 + 1;
            }
          }
          param_1 = pcVar4;
          local_8 = local_8 - (int)param_1;
        }
      }
      local_c = local_c + local_8;
    }
  }
  return local_c;
}



/* 0040b1a5 FUN_0040b1a5 */

int __cdecl FUN_0040b1a5(int *param_1,int param_2,DWORD param_3)

{
  uint uVar1;
  int iVar2;
  DWORD DVar3;
  
  if (((param_1[3] & 0x83U) == 0) || (((param_3 != 0 && (param_3 != 1)) && (param_3 != 2)))) {
    DAT_00452e48 = 0x16;
    iVar2 = -1;
  }
  else {
    param_1[3] = param_1[3] & 0xffffffef;
    if (param_3 == 1) {
      iVar2 = FUN_0040b04d((char *)param_1);
      param_2 = param_2 + iVar2;
      param_3 = 0;
    }
    FUN_0040d2cb(param_1);
    uVar1 = param_1[3];
    if ((uVar1 & 0x80) == 0) {
      if ((((uVar1 & 1) != 0) && ((uVar1 & 8) != 0)) && ((uVar1 & 0x400) == 0)) {
        param_1[6] = 0x200;
      }
    }
    else {
      param_1[3] = uVar1 & 0xfffffffc;
    }
    DVar3 = FUN_0040df2d(param_1[4],param_2,param_3);
    iVar2 = (DVar3 != 0xffffffff) - 1;
  }
  return iVar2;
}



/* 0040b231 FUN_0040b231 */

char * __cdecl FUN_0040b231(int param_1)

{
  char *pcVar1;
  int iVar2;
  uint *local_14;
  undefined *local_10;
  int local_c;
  undefined4 local_8;
  
  local_14 = (uint *)FUN_0040e308((uchar *)"COMSPEC");
  if (param_1 == 0) {
    if (local_14 == (uint *)0x0) {
      pcVar1 = (char *)0x0;
    }
    else {
      iVar2 = FUN_0040e2c4((LPCSTR)local_14,0);
      pcVar1 = (char *)(uint)(iVar2 == 0);
    }
  }
  else {
    local_10 = &DAT_0042c2d0;
    local_c = param_1;
    local_8 = 0;
    if (local_14 != (uint *)0x0) {
      pcVar1 = FUN_0040e12a(0,local_14,&local_14,(char *)0x0);
      if (pcVar1 != (char *)0xffffffff) {
        return pcVar1;
      }
      if ((DAT_00452e48 != 2) && (DAT_00452e48 != 0xd)) {
        return (char *)0xffffffff;
      }
    }
    local_14 = (uint *)0x42c2c4;
    if ((DAT_00452e55 & 0x80) == 0) {
      local_14 = (uint *)0x42c2bc;
    }
    pcVar1 = FUN_0040dfc7(0,local_14,&local_14,(char *)0x0);
  }
  return pcVar1;
}



/* 0040b2c7 FUN_0040b2c7 */

int __cdecl FUN_0040b2c7(undefined1 *param_1,byte *param_2)

{
  int iVar1;
  undefined1 *local_24;
  int local_20;
  undefined1 *local_1c;
  undefined4 local_18;
  
  local_1c = param_1;
  local_24 = param_1;
  local_18 = 0x42;
  local_20 = 0x7fffffff;
  iVar1 = FUN_0040bb6e((int *)&local_24,param_2,(undefined4 *)&stack0x0000000c);
  local_20 = local_20 + -1;
  if (local_20 < 0) {
    FUN_0040d9a5(0,(int *)&local_24);
  }
  else {
    *local_24 = 0;
  }
  return iVar1;
}



/* 0040b330 _strchr */

/* Library Function - Single Match
    _strchr
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release */

char * __cdecl _strchr(char *_Str,int _Val)

{
  uint uVar1;
  char cVar2;
  uint uVar3;
  uint uVar4;
  uint *puVar5;
  
  while (((uint)_Str & 3) != 0) {
    uVar1 = *(uint *)_Str;
    if ((char)uVar1 == (char)_Val) {
      return (char *)(uint *)_Str;
    }
    _Str = (char *)((int)_Str + 1);
    if ((char)uVar1 == '\0') {
      return (char *)0x0;
    }
  }
  while( true ) {
    while( true ) {
      uVar1 = *(uint *)_Str;
      uVar4 = uVar1 ^ CONCAT22(CONCAT11((char)_Val,(char)_Val),CONCAT11((char)_Val,(char)_Val));
      uVar3 = uVar1 ^ 0xffffffff ^ uVar1 + 0x7efefeff;
      puVar5 = (uint *)((int)_Str + 4);
      if (((uVar4 ^ 0xffffffff ^ uVar4 + 0x7efefeff) & 0x81010100) != 0) break;
      _Str = (char *)puVar5;
      if ((uVar3 & 0x81010100) != 0) {
        if ((uVar3 & 0x1010100) != 0) {
          return (char *)0x0;
        }
        if ((uVar1 + 0x7efefeff & 0x80000000) == 0) {
          return (char *)0x0;
        }
      }
    }
    uVar1 = *(uint *)_Str;
    if ((char)uVar1 == (char)_Val) {
      return (char *)(uint *)_Str;
    }
    if ((char)uVar1 == '\0') {
      return (char *)0x0;
    }
    cVar2 = (char)(uVar1 >> 8);
    if (cVar2 == (char)_Val) {
      return (char *)((int)_Str + 1);
    }
    if (cVar2 == '\0') {
      return (char *)0x0;
    }
    cVar2 = (char)(uVar1 >> 0x10);
    if (cVar2 == (char)_Val) {
      return (char *)((int)_Str + 2);
    }
    if (cVar2 == '\0') break;
    cVar2 = (char)(uVar1 >> 0x18);
    if (cVar2 == (char)_Val) {
      return (char *)((int)_Str + 3);
    }
    _Str = (char *)puVar5;
    if (cVar2 == '\0') {
      return (char *)0x0;
    }
  }
  return (char *)0x0;
}



/* 0040b3f0 _strncmp */

/* Library Function - Single Match
    _strncmp
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release */

int __cdecl _strncmp(char *_Str1,char *_Str2,size_t _MaxCount)

{
  char cVar1;
  char cVar2;
  size_t sVar3;
  int iVar4;
  uint uVar5;
  char *pcVar6;
  char *pcVar7;
  
  uVar5 = 0;
  sVar3 = _MaxCount;
  pcVar6 = _Str1;
  if (_MaxCount != 0) {
    do {
      if (sVar3 == 0) break;
      sVar3 = sVar3 - 1;
      cVar1 = *pcVar6;
      pcVar6 = pcVar6 + 1;
    } while (cVar1 != '\0');
    iVar4 = _MaxCount - sVar3;
    do {
      pcVar6 = _Str2;
      pcVar7 = _Str1;
      if (iVar4 == 0) break;
      iVar4 = iVar4 + -1;
      pcVar7 = _Str1 + 1;
      pcVar6 = _Str2 + 1;
      cVar2 = *_Str1;
      cVar1 = *_Str2;
      _Str2 = pcVar6;
      _Str1 = pcVar7;
    } while (cVar1 == cVar2);
    uVar5 = 0;
    if ((byte)pcVar6[-1] <= (byte)pcVar7[-1]) {
      if (pcVar6[-1] == pcVar7[-1]) {
        return 0;
      }
      uVar5 = 0xfffffffe;
    }
    uVar5 = ~uVar5;
  }
  return uVar5;
}



/* 0040b428 FUN_0040b428 */

int * __cdecl FUN_0040b428(int *param_1,uint *param_2)

{
  int *piVar1;
  uint *puVar2;
  int iVar3;
  uint *puVar4;
  
  if (param_1 == (int *)0x0) {
    piVar1 = _malloc((size_t)param_2);
  }
  else {
    if (param_2 == (uint *)0x0) {
      FUN_0040aa97(param_1);
    }
    else {
      do {
        if (param_2 < (uint *)0xffffffe1) {
          puVar2 = (uint *)FUN_0040c3f2((int)param_1);
          if (puVar2 == (uint *)0x0) {
            if (param_2 == (uint *)0x0) {
              param_2 = (uint *)0x1;
            }
            param_2 = (uint *)((int)param_2 + 0xfU & 0xfffffff0);
            piVar1 = HeapReAlloc(DAT_0045577c,0,param_1,(SIZE_T)param_2);
          }
          else {
            if (param_2 <= DAT_0043072c) {
              iVar3 = FUN_0040cbfd(puVar2,(int)param_1,(int)param_2);
              piVar1 = param_1;
              if (iVar3 == 0) {
                piVar1 = FUN_0040c748(param_2);
                if (piVar1 == (int *)0x0) goto LAB_0040b4c1;
                puVar4 = (uint *)(param_1[-1] - 1U);
                if (param_2 <= (uint *)(param_1[-1] - 1U)) {
                  puVar4 = param_2;
                }
                FUN_0040d670(piVar1,param_1,(uint)puVar4);
                FUN_0040c41d(puVar2,(uint)param_1);
              }
              if (piVar1 != (int *)0x0) {
                return piVar1;
              }
            }
LAB_0040b4c1:
            if (param_2 == (uint *)0x0) {
              param_2 = (uint *)0x1;
            }
            param_2 = (uint *)((int)param_2 + 0xfU & 0xfffffff0);
            piVar1 = HeapAlloc(DAT_0045577c,0,(SIZE_T)param_2);
            if (piVar1 == (int *)0x0) goto LAB_0040b524;
            puVar4 = (uint *)(param_1[-1] - 1U);
            if (param_2 <= (uint *)(param_1[-1] - 1U)) {
              puVar4 = param_2;
            }
            FUN_0040d670(piVar1,param_1,(uint)puVar4);
            FUN_0040c41d(puVar2,(uint)param_1);
          }
          if (piVar1 != (int *)0x0) {
            return piVar1;
          }
        }
LAB_0040b524:
        if (DAT_00452e40 == 0) {
          return (int *)0x0;
        }
        iVar3 = FUN_0040cef3(param_2);
      } while (iVar3 != 0);
    }
    piVar1 = (int *)0x0;
  }
  return piVar1;
}



/* 0040b550 FUN_0040b550 */

undefined4 * __cdecl FUN_0040b550(undefined4 *param_1,undefined4 *param_2,uint param_3)

{
  uint uVar1;
  uint uVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  
  if ((param_2 < param_1) && (param_1 < (undefined4 *)(param_3 + (int)param_2))) {
    puVar3 = (undefined4 *)((param_3 - 4) + (int)param_2);
    puVar4 = (undefined4 *)((param_3 - 4) + (int)param_1);
    if (((uint)puVar4 & 3) == 0) {
      uVar1 = param_3 >> 2;
      uVar2 = param_3 & 3;
      if (7 < uVar1) {
        for (; uVar1 != 0; uVar1 = uVar1 - 1) {
          *puVar4 = *puVar3;
          puVar3 = puVar3 + -1;
          puVar4 = puVar4 + -1;
        }
        switch(uVar2) {
        case 0:
          return param_1;
        case 2:
          goto switchD_0040b707_caseD_2;
        case 3:
          goto switchD_0040b707_caseD_3;
        }
        goto switchD_0040b707_caseD_1;
      }
    }
    else {
      switch(param_3) {
      case 0:
        goto switchD_0040b707_caseD_0;
      case 1:
        goto switchD_0040b707_caseD_1;
      case 2:
        goto switchD_0040b707_caseD_2;
      case 3:
        goto switchD_0040b707_caseD_3;
      default:
        uVar1 = param_3 - ((uint)puVar4 & 3);
        switch((uint)puVar4 & 3) {
        case 1:
          uVar2 = uVar1 & 3;
          *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
          puVar3 = (undefined4 *)((int)puVar3 + -1);
          uVar1 = uVar1 >> 2;
          puVar4 = (undefined4 *)((int)puVar4 - 1);
          if (7 < uVar1) {
            for (; uVar1 != 0; uVar1 = uVar1 - 1) {
              *puVar4 = *puVar3;
              puVar3 = puVar3 + -1;
              puVar4 = puVar4 + -1;
            }
            switch(uVar2) {
            case 0:
              return param_1;
            case 2:
              goto switchD_0040b707_caseD_2;
            case 3:
              goto switchD_0040b707_caseD_3;
            }
            goto switchD_0040b707_caseD_1;
          }
          break;
        case 2:
          uVar2 = uVar1 & 3;
          *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
          uVar1 = uVar1 >> 2;
          *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
          puVar3 = (undefined4 *)((int)puVar3 + -2);
          puVar4 = (undefined4 *)((int)puVar4 - 2);
          if (7 < uVar1) {
            for (; uVar1 != 0; uVar1 = uVar1 - 1) {
              *puVar4 = *puVar3;
              puVar3 = puVar3 + -1;
              puVar4 = puVar4 + -1;
            }
            switch(uVar2) {
            case 0:
              return param_1;
            case 2:
              goto switchD_0040b707_caseD_2;
            case 3:
              goto switchD_0040b707_caseD_3;
            }
            goto switchD_0040b707_caseD_1;
          }
          break;
        case 3:
          uVar2 = uVar1 & 3;
          *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
          *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
          uVar1 = uVar1 >> 2;
          *(undefined1 *)((int)puVar4 + 1) = *(undefined1 *)((int)puVar3 + 1);
          puVar3 = (undefined4 *)((int)puVar3 + -3);
          puVar4 = (undefined4 *)((int)puVar4 - 3);
          if (7 < uVar1) {
            for (; uVar1 != 0; uVar1 = uVar1 - 1) {
              *puVar4 = *puVar3;
              puVar3 = puVar3 + -1;
              puVar4 = puVar4 + -1;
            }
            switch(uVar2) {
            case 0:
              return param_1;
            case 2:
              goto switchD_0040b707_caseD_2;
            case 3:
              goto switchD_0040b707_caseD_3;
            }
            goto switchD_0040b707_caseD_1;
          }
        }
      }
    }
    switch(uVar1) {
    case 7:
      puVar4[7 - uVar1] = puVar3[7 - uVar1];
    case 6:
      puVar4[6 - uVar1] = puVar3[6 - uVar1];
    case 5:
      puVar4[5 - uVar1] = puVar3[5 - uVar1];
    case 4:
      puVar4[4 - uVar1] = puVar3[4 - uVar1];
    case 3:
      puVar4[3 - uVar1] = puVar3[3 - uVar1];
    case 2:
      puVar4[2 - uVar1] = puVar3[2 - uVar1];
    case 1:
      puVar4[1 - uVar1] = puVar3[1 - uVar1];
      puVar3 = puVar3 + -uVar1;
      puVar4 = puVar4 + -uVar1;
    }
    switch(uVar2) {
    case 1:
switchD_0040b707_caseD_1:
      *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
      return param_1;
    case 2:
switchD_0040b707_caseD_2:
      *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
      *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
      return param_1;
    case 3:
switchD_0040b707_caseD_3:
      *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
      *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
      *(undefined1 *)((int)puVar4 + 1) = *(undefined1 *)((int)puVar3 + 1);
      return param_1;
    }
switchD_0040b707_caseD_0:
    return param_1;
  }
  puVar3 = param_1;
  if (((uint)param_1 & 3) == 0) {
    uVar1 = param_3 >> 2;
    uVar2 = param_3 & 3;
    if (7 < uVar1) {
      for (; uVar1 != 0; uVar1 = uVar1 - 1) {
        *puVar3 = *param_2;
        param_2 = param_2 + 1;
        puVar3 = puVar3 + 1;
      }
      switch(uVar2) {
      case 0:
        return param_1;
      case 2:
        goto switchD_0040b585_caseD_2;
      case 3:
        goto switchD_0040b585_caseD_3;
      }
      goto switchD_0040b585_caseD_1;
    }
  }
  else {
    switch(param_3) {
    case 0:
      goto switchD_0040b585_caseD_0;
    case 1:
      goto switchD_0040b585_caseD_1;
    case 2:
      goto switchD_0040b585_caseD_2;
    case 3:
      goto switchD_0040b585_caseD_3;
    default:
      uVar1 = (param_3 - 4) + ((uint)param_1 & 3);
      switch((uint)param_1 & 3) {
      case 1:
        uVar2 = uVar1 & 3;
        *(undefined1 *)param_1 = *(undefined1 *)param_2;
        *(undefined1 *)((int)param_1 + 1) = *(undefined1 *)((int)param_2 + 1);
        uVar1 = uVar1 >> 2;
        *(undefined1 *)((int)param_1 + 2) = *(undefined1 *)((int)param_2 + 2);
        param_2 = (undefined4 *)((int)param_2 + 3);
        puVar3 = (undefined4 *)((int)param_1 + 3);
        if (7 < uVar1) {
          for (; uVar1 != 0; uVar1 = uVar1 - 1) {
            *puVar3 = *param_2;
            param_2 = param_2 + 1;
            puVar3 = puVar3 + 1;
          }
          switch(uVar2) {
          case 0:
            return param_1;
          case 2:
            goto switchD_0040b585_caseD_2;
          case 3:
            goto switchD_0040b585_caseD_3;
          }
          goto switchD_0040b585_caseD_1;
        }
        break;
      case 2:
        uVar2 = uVar1 & 3;
        *(undefined1 *)param_1 = *(undefined1 *)param_2;
        uVar1 = uVar1 >> 2;
        *(undefined1 *)((int)param_1 + 1) = *(undefined1 *)((int)param_2 + 1);
        param_2 = (undefined4 *)((int)param_2 + 2);
        puVar3 = (undefined4 *)((int)param_1 + 2);
        if (7 < uVar1) {
          for (; uVar1 != 0; uVar1 = uVar1 - 1) {
            *puVar3 = *param_2;
            param_2 = param_2 + 1;
            puVar3 = puVar3 + 1;
          }
          switch(uVar2) {
          case 0:
            return param_1;
          case 2:
            goto switchD_0040b585_caseD_2;
          case 3:
            goto switchD_0040b585_caseD_3;
          }
          goto switchD_0040b585_caseD_1;
        }
        break;
      case 3:
        uVar2 = uVar1 & 3;
        *(undefined1 *)param_1 = *(undefined1 *)param_2;
        param_2 = (undefined4 *)((int)param_2 + 1);
        uVar1 = uVar1 >> 2;
        puVar3 = (undefined4 *)((int)param_1 + 1);
        if (7 < uVar1) {
          for (; uVar1 != 0; uVar1 = uVar1 - 1) {
            *puVar3 = *param_2;
            param_2 = param_2 + 1;
            puVar3 = puVar3 + 1;
          }
          switch(uVar2) {
          case 0:
            return param_1;
          case 2:
            goto switchD_0040b585_caseD_2;
          case 3:
            goto switchD_0040b585_caseD_3;
          }
          goto switchD_0040b585_caseD_1;
        }
      }
    }
  }
  switch(uVar1) {
  case 7:
    puVar3[uVar1 - 7] = param_2[uVar1 - 7];
  case 6:
    puVar3[uVar1 - 6] = param_2[uVar1 - 6];
  case 5:
    puVar3[uVar1 - 5] = param_2[uVar1 - 5];
  case 4:
    puVar3[uVar1 - 4] = param_2[uVar1 - 4];
  case 3:
    puVar3[uVar1 - 3] = param_2[uVar1 - 3];
  case 2:
    puVar3[uVar1 - 2] = param_2[uVar1 - 2];
  case 1:
    puVar3[uVar1 - 1] = param_2[uVar1 - 1];
    param_2 = param_2 + uVar1;
    puVar3 = puVar3 + uVar1;
  }
  switch(uVar2) {
  case 1:
switchD_0040b585_caseD_1:
    *(undefined1 *)puVar3 = *(undefined1 *)param_2;
    return param_1;
  case 2:
switchD_0040b585_caseD_2:
    *(undefined1 *)puVar3 = *(undefined1 *)param_2;
    *(undefined1 *)((int)puVar3 + 1) = *(undefined1 *)((int)param_2 + 1);
    return param_1;
  case 3:
switchD_0040b585_caseD_3:
    *(undefined1 *)puVar3 = *(undefined1 *)param_2;
    *(undefined1 *)((int)puVar3 + 1) = *(undefined1 *)((int)param_2 + 1);
    *(undefined1 *)((int)puVar3 + 2) = *(undefined1 *)((int)param_2 + 2);
    return param_1;
  }
switchD_0040b585_caseD_0:
  return param_1;
}



/* 0040b890 FUN_0040b890 */

int __cdecl FUN_0040b890(byte *param_1,byte *param_2)

{
  byte bVar1;
  int iVar2;
  byte abStack_28 [32];
  
  abStack_28[0x1c] = 0;
  abStack_28[0x1d] = 0;
  abStack_28[0x1e] = 0;
  abStack_28[0x1f] = 0;
  abStack_28[0x18] = 0;
  abStack_28[0x19] = 0;
  abStack_28[0x1a] = 0;
  abStack_28[0x1b] = 0;
  abStack_28[0x14] = 0;
  abStack_28[0x15] = 0;
  abStack_28[0x16] = 0;
  abStack_28[0x17] = 0;
  abStack_28[0x10] = 0;
  abStack_28[0x11] = 0;
  abStack_28[0x12] = 0;
  abStack_28[0x13] = 0;
  abStack_28[0xc] = 0;
  abStack_28[0xd] = 0;
  abStack_28[0xe] = 0;
  abStack_28[0xf] = 0;
  abStack_28[8] = 0;
  abStack_28[9] = 0;
  abStack_28[10] = 0;
  abStack_28[0xb] = 0;
  abStack_28[4] = 0;
  abStack_28[5] = 0;
  abStack_28[6] = 0;
  abStack_28[7] = 0;
  abStack_28[0] = 0;
  abStack_28[1] = 0;
  abStack_28[2] = 0;
  abStack_28[3] = 0;
  while( true ) {
    bVar1 = *param_2;
    if (bVar1 == 0) break;
    param_2 = param_2 + 1;
    abStack_28[(int)(uint)bVar1 >> 3] = abStack_28[(int)(uint)bVar1 >> 3] | '\x01' << (bVar1 & 7);
  }
  iVar2 = -1;
  do {
    iVar2 = iVar2 + 1;
    bVar1 = *param_1;
    if (bVar1 == 0) {
      return iVar2;
    }
    param_1 = param_1 + 1;
  } while ((abStack_28[(int)(uint)bVar1 >> 3] >> (bVar1 & 7) & 1) != 0);
  return iVar2;
}



/* 0040b8ce entry */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void entry(void)

{
  DWORD DVar1;
  int iVar2;
  UINT UVar3;
  _EXCEPTION_POINTERS *local_18;
  void *pvStack_14;
  undefined1 *puStack_10;
  undefined *puStack_c;
  undefined4 local_8;
  
  local_8 = 0xffffffff;
  puStack_c = &DAT_0042c2e0;
  puStack_10 = &LAB_0040ea3c;
  pvStack_14 = ExceptionList;
  ExceptionList = &pvStack_14;
  DVar1 = GetVersion();
  _DAT_00452e60 = DVar1 >> 8 & 0xff;
  _DAT_00452e5c = DVar1 & 0xff;
  _DAT_00452e58 = _DAT_00452e5c * 0x100 + _DAT_00452e60;
  _DAT_00452e54 = DVar1 >> 0x10;
  iVar2 = FUN_0040c378(0);
  if (iVar2 == 0) {
    FUN_0040b9d2(0x1c);
  }
  local_8 = 0;
  FUN_0040cf0e();
  DAT_00455780 = GetCommandLineA();
  DAT_00452e2c = FUN_0040e80f();
  FUN_0040e5c2();
  FUN_0040e509();
  FUN_0040d18e();
  DAT_00452e74 = DAT_00452e70;
  UVar3 = FUN_00401000(DAT_00452e64,DAT_00452e68);
  FUN_0040d1bb(UVar3);
  FUN_0040e385(local_18->ExceptionRecord->ExceptionCode,local_18);
  return;
}



/* 0040b9ad __amsg_exit */

/* Library Function - Single Match
    __amsg_exit
   
   Library: Visual Studio 2003 Release */

void __cdecl __amsg_exit(int param_1)

{
  if (DAT_00452e34 != 2) {
    FUN_0040eb14();
  }
  FUN_0040eb4d(param_1);
  (*(code *)PTR___exit_00430500)(0xff);
  return;
}



/* 0040b9d2 FUN_0040b9d2 */

void __cdecl FUN_0040b9d2(DWORD param_1)

{
  if (DAT_00452e34 != 2) {
    FUN_0040eb14();
  }
  FUN_0040eb4d(param_1);
                    /* WARNING: Subroutine does not return */
  ExitProcess(0xff);
}



/* 0040b9f6 FUN_0040b9f6 */

uint __thiscall FUN_0040b9f6(void *this,int param_1,uint param_2)

{
  BOOL BVar1;
  int iVar2;
  undefined4 local_8;
  
  if (param_1 + 1U < 0x101) {
    param_1._2_2_ = *(ushort *)(PTR_DAT_00430508 + param_1 * 2);
  }
  else {
    if ((PTR_DAT_00430508[(param_1 >> 8 & 0xffU) * 2 + 1] & 0x80) == 0) {
      local_8 = CONCAT31((int3)((uint)this >> 8),(char)param_1) & 0xffff00ff;
      iVar2 = 1;
    }
    else {
      local_8._0_2_ = CONCAT11((char)param_1,(char)((uint)param_1 >> 8));
      local_8 = CONCAT22((short)((uint)this >> 0x10),(undefined2)local_8) & 0xff00ffff;
      iVar2 = 2;
    }
    BVar1 = FUN_0040eca0(1,(LPCSTR)&local_8,iVar2,(LPWORD)((int)&param_1 + 2),0,0,1);
    if (BVar1 == 0) {
      return 0;
    }
  }
  return param_1._2_2_ & param_2;
}



/* 0040baa4 FUN_0040baa4 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined4 __cdecl FUN_0040baa4(undefined4 *param_1)

{
  undefined4 uVar1;
  byte bVar2;
  undefined3 extraout_var;
  int iVar3;
  void *pvVar4;
  
  bVar2 = FUN_0040ede9(param_1[4]);
  if (CONCAT31(extraout_var,bVar2) == 0) {
    return 0;
  }
  if (param_1 == (undefined4 *)&DAT_004302a0) {
    iVar3 = 0;
  }
  else {
    if (param_1 != (undefined4 *)&DAT_004302c0) {
      return 0;
    }
    iVar3 = 1;
  }
  _DAT_00452e28 = _DAT_00452e28 + 1;
  if ((*(ushort *)(param_1 + 3) & 0x10c) != 0) {
    return 0;
  }
  if ((&DAT_00452e38)[iVar3] == 0) {
    pvVar4 = _malloc(0x1000);
    (&DAT_00452e38)[iVar3] = pvVar4;
    if (pvVar4 == (void *)0x0) {
      param_1[2] = param_1 + 5;
      *param_1 = param_1 + 5;
      param_1[6] = 2;
      param_1[1] = 2;
      goto LAB_0040bb20;
    }
  }
  uVar1 = (&DAT_00452e38)[iVar3];
  param_1[6] = 0x1000;
  param_1[2] = uVar1;
  *param_1 = uVar1;
  param_1[1] = 0x1000;
LAB_0040bb20:
  *(ushort *)(param_1 + 3) = *(ushort *)(param_1 + 3) | 0x1102;
  return 1;
}



/* 0040bb31 FUN_0040bb31 */

void __cdecl FUN_0040bb31(int param_1,int *param_2)

{
  if (param_1 == 0) {
    if ((*(byte *)((int)param_2 + 0xd) & 0x10) != 0) {
      FUN_0040d2cb(param_2);
    }
  }
  else if ((*(byte *)((int)param_2 + 0xd) & 0x10) != 0) {
    FUN_0040d2cb(param_2);
    *(byte *)((int)param_2 + 0xd) = *(byte *)((int)param_2 + 0xd) & 0xee;
    param_2[6] = 0;
    *param_2 = 0;
    param_2[2] = 0;
    return;
  }
  return;
}



/* 0040bb6e FUN_0040bb6e */

int __cdecl FUN_0040bb6e(int *param_1,byte *param_2,undefined4 *param_3)

{
  byte *pbVar1;
  uint uVar2;
  WCHAR *pWVar3;
  WCHAR *pWVar4;
  undefined4 uVar5;
  short *psVar6;
  int *piVar7;
  int iVar8;
  byte bVar9;
  int iVar10;
  uint uVar11;
  undefined1 *puVar12;
  ulonglong uVar13;
  undefined8 uVar14;
  ulonglong uVar15;
  undefined1 local_24c [511];
  undefined1 local_4d;
  undefined4 local_4c;
  undefined4 local_48;
  uint local_44;
  uint local_40;
  CHAR local_3c [4];
  undefined4 local_38;
  int local_34;
  int local_30;
  int local_2c;
  int local_28;
  int local_24;
  int local_20;
  char local_1a;
  char local_19;
  int local_18;
  int local_14;
  undefined1 *local_10;
  WCHAR *local_c;
  uint local_8;
  
  local_34 = 0;
  bVar9 = *param_2;
  local_10 = (undefined1 *)0x0;
  local_18 = 0;
  pbVar1 = param_2;
  do {
    if ((bVar9 == 0) || (param_2 = pbVar1 + 1, local_18 < 0)) {
      return local_18;
    }
    if (((char)bVar9 < ' ') || ('x' < (char)bVar9)) {
      uVar2 = 0;
    }
    else {
      uVar2 = (byte)"command.com"[(char)bVar9 + 8] & 0xf;
    }
    local_34 = (int)(char)(&DAT_0042c2ec)[uVar2 * 8 + local_34] >> 4;
    switch(local_34) {
    case 0:
switchD_0040bbdc_caseD_0:
      local_28 = 0;
      if ((PTR_DAT_00430508[(uint)bVar9 * 2 + 1] & 0x80) != 0) {
        FUN_0040c2af((int)(char)bVar9,param_1,&local_18);
        bVar9 = *param_2;
        param_2 = pbVar1 + 2;
      }
      FUN_0040c2af((int)(char)bVar9,param_1,&local_18);
      break;
    case 1:
      local_14 = -1;
      local_38 = 0;
      local_2c = 0;
      local_24 = 0;
      local_20 = 0;
      local_8 = 0;
      local_28 = 0;
      break;
    case 2:
      if (bVar9 == 0x20) {
        local_8 = local_8 | 2;
      }
      else if (bVar9 == 0x23) {
        local_8 = local_8 | 0x80;
      }
      else if (bVar9 == 0x2b) {
        local_8 = local_8 | 1;
      }
      else if (bVar9 == 0x2d) {
        local_8 = local_8 | 4;
      }
      else if (bVar9 == 0x30) {
        local_8 = local_8 | 8;
      }
      break;
    case 3:
      if (bVar9 == 0x2a) {
        local_24 = FUN_0040c34d((int *)&param_3);
        if (local_24 < 0) {
          local_8 = local_8 | 4;
          local_24 = -local_24;
        }
      }
      else {
        local_24 = (char)bVar9 + -0x30 + local_24 * 10;
      }
      break;
    case 4:
      local_14 = 0;
      break;
    case 5:
      if (bVar9 == 0x2a) {
        local_14 = FUN_0040c34d((int *)&param_3);
        if (local_14 < 0) {
          local_14 = -1;
        }
      }
      else {
        local_14 = (char)bVar9 + -0x30 + local_14 * 10;
      }
      break;
    case 6:
      if (bVar9 == 0x49) {
        if ((*param_2 != 0x36) || (pbVar1[2] != 0x34)) {
          local_34 = 0;
          goto switchD_0040bbdc_caseD_0;
        }
        param_2 = pbVar1 + 3;
        local_8 = local_8 | 0x8000;
      }
      else if (bVar9 == 0x68) {
        local_8 = local_8 | 0x20;
      }
      else if (bVar9 == 0x6c) {
        local_8 = local_8 | 0x10;
      }
      else if (bVar9 == 0x77) {
        local_8 = local_8 | 0x800;
      }
      break;
    case 7:
      pWVar4 = local_c;
      if ((char)bVar9 < 'h') {
        if ((char)bVar9 < 'e') {
          if ((char)bVar9 < 'Y') {
            if (bVar9 == 0x58) {
LAB_0040bfed:
              local_30 = 7;
LAB_0040bff4:
              local_10 = (undefined1 *)0x10;
              if ((local_8 & 0x80) != 0) {
                local_1a = '0';
                local_19 = (char)local_30 + 'Q';
                local_20 = 2;
              }
              goto LAB_0040c05e;
            }
            if (bVar9 != 0x43) {
              if ((bVar9 != 0x45) && (bVar9 != 0x47)) {
                if (bVar9 == 0x53) {
                  if ((local_8 & 0x830) == 0) {
                    local_8 = local_8 | 0x800;
                  }
                  goto LAB_0040bd9b;
                }
                goto LAB_0040c178;
              }
              local_38 = 1;
              bVar9 = bVar9 + 0x20;
              goto LAB_0040bdfc;
            }
            if ((local_8 & 0x830) == 0) {
              local_8 = local_8 | 0x800;
            }
LAB_0040be29:
            if ((local_8 & 0x810) == 0) {
              uVar5 = FUN_0040c34d((int *)&param_3);
              local_24c[0] = (char)uVar5;
              local_10 = (undefined1 *)0x1;
            }
            else {
              uVar5 = FUN_0040c36a((int *)&param_3);
              local_10 = (undefined1 *)FUN_0040ee8b(local_24c,(WCHAR)uVar5);
              if ((int)local_10 < 0) {
                local_2c = 1;
              }
            }
            pWVar4 = (WCHAR *)local_24c;
          }
          else if (bVar9 == 0x5a) {
            psVar6 = (short *)FUN_0040c34d((int *)&param_3);
            if ((psVar6 == (short *)0x0) ||
               (pWVar4 = *(WCHAR **)(psVar6 + 2), pWVar4 == (WCHAR *)0x0)) {
              local_c = (WCHAR *)PTR_DAT_00430720;
              pWVar4 = (WCHAR *)PTR_DAT_00430720;
              goto LAB_0040bf6e;
            }
            if ((local_8 & 0x800) == 0) {
              local_28 = 0;
              local_10 = (undefined1 *)(int)*psVar6;
            }
            else {
              local_28 = 1;
              local_10 = (undefined1 *)((uint)(int)*psVar6 >> 1);
            }
          }
          else {
            if (bVar9 == 99) goto LAB_0040be29;
            if (bVar9 == 100) goto LAB_0040c053;
          }
        }
        else {
LAB_0040bdfc:
          local_8 = local_8 | 0x40;
          pWVar4 = (WCHAR *)local_24c;
          if (local_14 < 0) {
            local_14 = 6;
          }
          else if ((local_14 == 0) && (bVar9 == 0x67)) {
            local_14 = 1;
          }
          local_4c = *param_3;
          local_48 = param_3[1];
          param_3 = param_3 + 2;
          local_c = pWVar4;
          (*(code *)PTR_FUN_00430880)(&local_4c,local_24c,(int)(char)bVar9,local_14,local_38);
          uVar2 = local_8 & 0x80;
          if ((uVar2 != 0) && (local_14 == 0)) {
            (*(code *)PTR_FUN_0043088c)(local_24c);
          }
          if ((bVar9 == 0x67) && (uVar2 == 0)) {
            (*(code *)PTR_FUN_00430884)(local_24c);
          }
          if (local_24c[0] == '-') {
            local_8 = local_8 | 0x100;
            pWVar4 = (WCHAR *)(local_24c + 1);
            local_c = pWVar4;
          }
LAB_0040bf6e:
          local_10 = (undefined1 *)_strlen((char *)pWVar4);
          pWVar4 = local_c;
        }
      }
      else {
        if (bVar9 == 0x69) {
LAB_0040c053:
          local_8 = local_8 | 0x40;
        }
        else {
          if (bVar9 == 0x6e) {
            piVar7 = (int *)FUN_0040c34d((int *)&param_3);
            if ((local_8 & 0x20) == 0) {
              *piVar7 = local_18;
            }
            else {
              *(undefined2 *)piVar7 = (undefined2)local_18;
            }
            local_2c = 1;
            break;
          }
          if (bVar9 == 0x6f) {
            local_10 = (undefined1 *)0x8;
            if ((local_8 & 0x80) != 0) {
              local_8 = local_8 | 0x200;
            }
            goto LAB_0040c05e;
          }
          if (bVar9 == 0x70) {
            local_14 = 8;
            goto LAB_0040bfed;
          }
          if (bVar9 == 0x73) {
LAB_0040bd9b:
            iVar10 = local_14;
            if (local_14 == -1) {
              iVar10 = 0x7fffffff;
            }
            pWVar3 = (WCHAR *)FUN_0040c34d((int *)&param_3);
            if ((local_8 & 0x810) == 0) {
              pWVar4 = pWVar3;
              if (pWVar3 == (WCHAR *)0x0) {
                pWVar3 = (WCHAR *)PTR_DAT_00430720;
                pWVar4 = (WCHAR *)PTR_DAT_00430720;
              }
              for (; (iVar10 != 0 && ((char)*pWVar3 != '\0')); pWVar3 = (WCHAR *)((int)pWVar3 + 1))
              {
                iVar10 = iVar10 + -1;
              }
              local_10 = (undefined1 *)((int)pWVar3 - (int)pWVar4);
            }
            else {
              if (pWVar3 == (WCHAR *)0x0) {
                pWVar3 = (WCHAR *)PTR_DAT_00430724;
              }
              local_28 = 1;
              for (pWVar4 = pWVar3; (iVar10 != 0 && (*pWVar4 != L'\0')); pWVar4 = pWVar4 + 1) {
                iVar10 = iVar10 + -1;
              }
              local_10 = (undefined1 *)((int)pWVar4 - (int)pWVar3 >> 1);
              pWVar4 = pWVar3;
            }
            goto LAB_0040c178;
          }
          if (bVar9 != 0x75) {
            if (bVar9 != 0x78) goto LAB_0040c178;
            local_30 = 0x27;
            goto LAB_0040bff4;
          }
        }
        local_10 = (undefined1 *)0xa;
LAB_0040c05e:
        if ((local_8 & 0x8000) == 0) {
          if ((local_8 & 0x20) == 0) {
            if ((local_8 & 0x40) == 0) {
              uVar2 = FUN_0040c34d((int *)&param_3);
              uVar13 = (ulonglong)uVar2;
              goto LAB_0040c0b1;
            }
            uVar2 = FUN_0040c34d((int *)&param_3);
          }
          else if ((local_8 & 0x40) == 0) {
            uVar2 = FUN_0040c34d((int *)&param_3);
            uVar2 = uVar2 & 0xffff;
          }
          else {
            uVar5 = FUN_0040c34d((int *)&param_3);
            uVar2 = (uint)(short)uVar5;
          }
          uVar13 = (ulonglong)(int)uVar2;
        }
        else {
          uVar13 = FUN_0040c35a((int *)&param_3);
        }
LAB_0040c0b1:
        iVar10 = (int)(uVar13 >> 0x20);
        if ((((local_8 & 0x40) != 0) && (iVar10 == 0 || (longlong)uVar13 < 0)) &&
           ((longlong)uVar13 < 0)) {
          local_8 = local_8 | 0x100;
          uVar13 = CONCAT44(-(iVar10 + (uint)((int)uVar13 != 0)),-(int)uVar13);
        }
        uVar2 = (uint)(uVar13 >> 0x20);
        uVar15 = uVar13 & 0xffffffff;
        if ((local_8 & 0x8000) == 0) {
          uVar2 = 0;
        }
        if (local_14 < 0) {
          local_14 = 1;
        }
        else {
          local_8 = local_8 & 0xfffffff7;
        }
        if ((int)uVar13 == 0 && uVar2 == 0) {
          local_20 = 0;
        }
        local_c = (WCHAR *)&local_4d;
        while( true ) {
          uVar11 = (uint)uVar15;
          iVar10 = local_14 + -1;
          if ((local_14 < 1) && (uVar11 == 0 && uVar2 == 0)) break;
          local_40 = (int)local_10 >> 0x1f;
          local_44 = (uint)local_10;
          local_14 = iVar10;
          uVar14 = __aullrem(uVar11,uVar2,(uint)local_10,local_40);
          iVar10 = (int)uVar14 + 0x30;
          uVar15 = __aulldiv(uVar11,uVar2,local_44,local_40);
          uVar2 = (uint)(uVar15 >> 0x20);
          if (0x39 < iVar10) {
            iVar10 = iVar10 + local_30;
          }
          pWVar4 = (WCHAR *)((int)local_c + -1);
          *(char *)local_c = (char)iVar10;
          local_c = pWVar4;
        }
        iVar8 = -(int)local_c;
        local_10 = &local_4d + iVar8;
        pWVar4 = (WCHAR *)((int)local_c + 1);
        local_14 = iVar10;
        if (((local_8 & 0x200) != 0) &&
           ((*(char *)pWVar4 != '0' || (local_10 == (undefined1 *)0x0)))) {
          *(char *)local_c = '0';
          local_10 = (undefined1 *)((int)&local_4c + iVar8);
          pWVar4 = local_c;
        }
      }
LAB_0040c178:
      local_c = pWVar4;
      uVar2 = local_8;
      if (local_2c == 0) {
        if ((local_8 & 0x40) != 0) {
          if ((local_8 & 0x100) == 0) {
            if ((local_8 & 1) == 0) {
              if ((local_8 & 2) == 0) goto LAB_0040c1b0;
              local_1a = ' ';
            }
            else {
              local_1a = '+';
            }
          }
          else {
            local_1a = '-';
          }
          local_20 = 1;
        }
LAB_0040c1b0:
        iVar10 = (local_24 - local_20) - (int)local_10;
        if ((local_8 & 0xc) == 0) {
          FUN_0040c2e4(0x20,iVar10,param_1,&local_18);
        }
        FUN_0040c315(&local_1a,local_20,param_1,&local_18);
        if (((uVar2 & 8) != 0) && ((uVar2 & 4) == 0)) {
          FUN_0040c2e4(0x30,iVar10,param_1,&local_18);
        }
        if ((local_28 == 0) || (puVar12 = local_10, pWVar4 = local_c, (int)local_10 < 1)) {
          FUN_0040c315((char *)local_c,(int)local_10,param_1,&local_18);
        }
        else {
          do {
            puVar12 = puVar12 + -1;
            iVar8 = FUN_0040ee8b(local_3c,*pWVar4);
            if (iVar8 < 1) break;
            FUN_0040c315(local_3c,iVar8,param_1,&local_18);
            pWVar4 = pWVar4 + 1;
          } while (puVar12 != (undefined1 *)0x0);
        }
        if ((local_8 & 4) != 0) {
          FUN_0040c2e4(0x20,iVar10,param_1,&local_18);
        }
      }
    }
    bVar9 = *param_2;
    pbVar1 = param_2;
  } while( true );
}



/* 0040c2af FUN_0040c2af */

void __cdecl FUN_0040c2af(uint param_1,int *param_2,int *param_3)

{
  int *piVar1;
  uint uVar2;
  
  piVar1 = param_2 + 1;
  *piVar1 = *piVar1 + -1;
  if (*piVar1 < 0) {
    uVar2 = FUN_0040d9a5(param_1,param_2);
  }
  else {
    *(undefined1 *)*param_2 = (undefined1)param_1;
    *param_2 = *param_2 + 1;
    uVar2 = param_1 & 0xff;
  }
  if (uVar2 == 0xffffffff) {
    *param_3 = -1;
    return;
  }
  *param_3 = *param_3 + 1;
  return;
}



/* 0040c2e4 FUN_0040c2e4 */

void __cdecl FUN_0040c2e4(uint param_1,int param_2,int *param_3,int *param_4)

{
  do {
    if (param_2 < 1) {
      return;
    }
    param_2 = param_2 + -1;
    FUN_0040c2af(param_1,param_3,param_4);
  } while (*param_4 != -1);
  return;
}



/* 0040c315 FUN_0040c315 */

void __cdecl FUN_0040c315(char *param_1,int param_2,int *param_3,int *param_4)

{
  char cVar1;
  
  do {
    if (param_2 < 1) {
      return;
    }
    param_2 = param_2 + -1;
    cVar1 = *param_1;
    param_1 = param_1 + 1;
    FUN_0040c2af((int)cVar1,param_3,param_4);
  } while (*param_4 != -1);
  return;
}



/* 0040c34d FUN_0040c34d */

undefined4 __cdecl FUN_0040c34d(int *param_1)

{
  *param_1 = *param_1 + 4;
  return *(undefined4 *)(*param_1 + -4);
}



/* 0040c35a FUN_0040c35a */

undefined8 __cdecl FUN_0040c35a(int *param_1)

{
  *param_1 = *param_1 + 8;
  return *(undefined8 *)(*param_1 + -8);
}



/* 0040c36a FUN_0040c36a */

undefined4 __cdecl FUN_0040c36a(int *param_1)

{
  *param_1 = *param_1 + 4;
  return CONCAT22((short)((uint)*param_1 >> 0x10),*(undefined2 *)(*param_1 + -4));
}



/* 0040c378 FUN_0040c378 */

undefined4 __cdecl FUN_0040c378(int param_1)

{
  int iVar1;
  
  DAT_0045577c = HeapCreate((uint)(param_1 == 0),0x1000,0);
  if (DAT_0045577c != (HANDLE)0x0) {
    iVar1 = FUN_0040c3b4();
    if (iVar1 != 0) {
      return 1;
    }
    HeapDestroy(DAT_0045577c);
  }
  return 0;
}



/* 0040c3b4 FUN_0040c3b4 */

undefined4 FUN_0040c3b4(void)

{
  DAT_00455778 = HeapAlloc(DAT_0045577c,0,0x140);
  if (DAT_00455778 == (LPVOID)0x0) {
    return 0;
  }
  DAT_00455770 = 0;
  DAT_00455774 = 0;
  DAT_0045576c = DAT_00455778;
  DAT_00455764 = 0x10;
  return 1;
}



/* 0040c3f2 FUN_0040c3f2 */

uint __cdecl FUN_0040c3f2(int param_1)

{
  uint uVar1;
  
  uVar1 = DAT_00455778;
  while( true ) {
    if (DAT_00455778 + DAT_00455774 * 0x14 <= uVar1) {
      return 0;
    }
    if ((uint)(param_1 - *(int *)(uVar1 + 0xc)) < 0x100000) break;
    uVar1 = uVar1 + 0x14;
  }
  return uVar1;
}



/* 0040c41d FUN_0040c41d */

void __cdecl FUN_0040c41d(uint *param_1,uint param_2)

{
  char *pcVar1;
  uint *puVar2;
  int *piVar3;
  char cVar4;
  uint uVar5;
  int iVar6;
  uint uVar7;
  byte bVar8;
  int *piVar9;
  uint uVar10;
  uint uVar11;
  uint uVar12;
  int local_10;
  
  uVar5 = param_1[4];
  iVar6 = *(int *)(param_2 - 4);
  piVar9 = (int *)(param_2 - 4);
  uVar10 = param_2 - param_1[3] >> 0xf;
  uVar7 = *(uint *)(param_2 - 8);
  local_10 = iVar6 + -1;
  piVar3 = (int *)(uVar10 * 0x204 + 0x144 + uVar5);
  uVar12 = *(uint *)(local_10 + (int)piVar9);
  if ((uVar12 & 1) == 0) {
    param_2 = ((int)uVar12 >> 4) - 1;
    if (0x3f < param_2) {
      param_2 = 0x3f;
    }
    if (*(int *)(iVar6 + 3 + (int)piVar9) == *(int *)(iVar6 + 7 + (int)piVar9)) {
      if (param_2 < 0x20) {
        pcVar1 = (char *)(param_2 + 4 + uVar5);
        uVar11 = ~(0x80000000U >> ((byte)param_2 & 0x1f));
        puVar2 = (uint *)(uVar5 + 0x44 + uVar10 * 4);
        *puVar2 = *puVar2 & uVar11;
        *pcVar1 = *pcVar1 + -1;
        if (*pcVar1 == '\0') {
          *param_1 = *param_1 & uVar11;
        }
      }
      else {
        pcVar1 = (char *)(param_2 + 4 + uVar5);
        uVar11 = ~(0x80000000U >> ((byte)param_2 - 0x20 & 0x1f));
        puVar2 = (uint *)(uVar5 + 0xc4 + uVar10 * 4);
        *puVar2 = *puVar2 & uVar11;
        *pcVar1 = *pcVar1 + -1;
        if (*pcVar1 == '\0') {
          param_1[1] = param_1[1] & uVar11;
        }
      }
    }
    *(undefined4 *)(*(int *)(iVar6 + 7 + (int)piVar9) + 4) =
         *(undefined4 *)(iVar6 + 3 + (int)piVar9);
    local_10 = local_10 + uVar12;
    *(undefined4 *)(*(int *)(iVar6 + 3 + (int)piVar9) + 8) =
         *(undefined4 *)(iVar6 + 7 + (int)piVar9);
  }
  uVar12 = (local_10 >> 4) - 1;
  if (0x3f < uVar12) {
    uVar12 = 0x3f;
  }
  if ((uVar7 & 1) == 0) {
    piVar9 = (int *)((int)piVar9 - uVar7);
    param_2 = ((int)uVar7 >> 4) - 1;
    if (0x3f < param_2) {
      param_2 = 0x3f;
    }
    local_10 = local_10 + uVar7;
    uVar12 = (local_10 >> 4) - 1;
    if (0x3f < uVar12) {
      uVar12 = 0x3f;
    }
    if (param_2 != uVar12) {
      if (piVar9[1] == piVar9[2]) {
        if (param_2 < 0x20) {
          pcVar1 = (char *)(param_2 + 4 + uVar5);
          uVar11 = ~(0x80000000U >> ((byte)param_2 & 0x1f));
          puVar2 = (uint *)(uVar5 + 0x44 + uVar10 * 4);
          *puVar2 = *puVar2 & uVar11;
          *pcVar1 = *pcVar1 + -1;
          if (*pcVar1 == '\0') {
            *param_1 = *param_1 & uVar11;
          }
        }
        else {
          pcVar1 = (char *)(param_2 + 4 + uVar5);
          uVar11 = ~(0x80000000U >> ((byte)param_2 - 0x20 & 0x1f));
          puVar2 = (uint *)(uVar5 + 0xc4 + uVar10 * 4);
          *puVar2 = *puVar2 & uVar11;
          *pcVar1 = *pcVar1 + -1;
          if (*pcVar1 == '\0') {
            param_1[1] = param_1[1] & uVar11;
          }
        }
      }
      *(int *)(piVar9[2] + 4) = piVar9[1];
      *(int *)(piVar9[1] + 8) = piVar9[2];
    }
  }
  if (((uVar7 & 1) != 0) || (param_2 != uVar12)) {
    piVar9[1] = piVar3[uVar12 * 2 + 1];
    piVar9[2] = (int)(piVar3 + uVar12 * 2);
    (piVar3 + uVar12 * 2)[1] = (int)piVar9;
    *(int **)(piVar9[1] + 8) = piVar9;
    if (piVar9[1] == piVar9[2]) {
      cVar4 = *(char *)(uVar12 + 4 + uVar5);
      *(char *)(uVar12 + 4 + uVar5) = cVar4 + '\x01';
      bVar8 = (byte)uVar12;
      if (uVar12 < 0x20) {
        if (cVar4 == '\0') {
          *param_1 = *param_1 | 0x80000000U >> (bVar8 & 0x1f);
        }
        puVar2 = (uint *)(uVar5 + 0x44 + uVar10 * 4);
        *puVar2 = *puVar2 | 0x80000000U >> (bVar8 & 0x1f);
      }
      else {
        if (cVar4 == '\0') {
          param_1[1] = param_1[1] | 0x80000000U >> (bVar8 - 0x20 & 0x1f);
        }
        puVar2 = (uint *)(uVar5 + 0xc4 + uVar10 * 4);
        *puVar2 = *puVar2 | 0x80000000U >> (bVar8 - 0x20 & 0x1f);
      }
    }
  }
  *piVar9 = local_10;
  *(int *)(local_10 + -4 + (int)piVar9) = local_10;
  *piVar3 = *piVar3 + -1;
  uVar5 = DAT_00455768;
  puVar2 = DAT_00455770;
  if ((*piVar3 == 0) && (uVar5 = uVar10, puVar2 = param_1, DAT_00455770 != (uint *)0x0)) {
    VirtualFree((LPVOID)(DAT_00455768 * 0x8000 + DAT_00455770[3]),0x8000,0x4000);
    DAT_00455770[2] = DAT_00455770[2] | 0x80000000U >> ((byte)DAT_00455768 & 0x1f);
    *(undefined4 *)(DAT_00455770[4] + 0xc4 + DAT_00455768 * 4) = 0;
    *(char *)(DAT_00455770[4] + 0x43) = *(char *)(DAT_00455770[4] + 0x43) + -1;
    if (*(char *)(DAT_00455770[4] + 0x43) == '\0') {
      DAT_00455770[1] = DAT_00455770[1] & 0xfffffffe;
    }
    puVar2 = param_1;
    if (DAT_00455770[2] == 0xffffffff) {
      VirtualFree((LPVOID)DAT_00455770[3],0,0x8000);
      HeapFree(DAT_0045577c,0,(LPVOID)DAT_00455770[4]);
      FUN_0040b550(DAT_00455770,DAT_00455770 + 5,
                   (DAT_00455774 * 0x14 - (int)DAT_00455770) + -0x14 + DAT_00455778);
      DAT_00455774 = DAT_00455774 + -1;
      if (DAT_00455770 < param_1) {
        param_1 = param_1 + -5;
      }
      DAT_0045576c = DAT_00455778;
      puVar2 = param_1;
    }
  }
  DAT_00455770 = puVar2;
  DAT_00455768 = uVar5;
  return;
}



/* 0040c748 FUN_0040c748 */

int * __cdecl FUN_0040c748(uint *param_1)

{
  char *pcVar1;
  int *piVar2;
  char cVar3;
  int *piVar4;
  byte bVar5;
  uint uVar6;
  int iVar7;
  uint *puVar8;
  int iVar9;
  int *piVar10;
  uint *puVar11;
  uint *puVar12;
  uint uVar13;
  int iVar14;
  uint local_10;
  uint local_c;
  int local_8;
  
  puVar8 = DAT_00455778 + DAT_00455774 * 5;
  uVar6 = (int)param_1 + 0x17U & 0xfffffff0;
  iVar7 = ((int)((int)param_1 + 0x17U) >> 4) + -1;
  bVar5 = (byte)iVar7;
  param_1 = DAT_0045576c;
  if (iVar7 < 0x20) {
    local_10 = 0xffffffff >> (bVar5 & 0x1f);
    local_c = 0xffffffff;
  }
  else {
    local_c = 0xffffffff >> (bVar5 - 0x20 & 0x1f);
    local_10 = 0;
  }
  for (; (param_1 < puVar8 && ((param_1[1] & local_c) == 0 && (*param_1 & local_10) == 0));
      param_1 = param_1 + 5) {
  }
  puVar11 = DAT_00455778;
  if (param_1 == puVar8) {
    for (; (puVar11 < DAT_0045576c && ((puVar11[1] & local_c) == 0 && (*puVar11 & local_10) == 0));
        puVar11 = puVar11 + 5) {
    }
    param_1 = puVar11;
    if (puVar11 == DAT_0045576c) {
      for (; (puVar11 < puVar8 && (puVar11[2] == 0)); puVar11 = puVar11 + 5) {
      }
      puVar12 = DAT_00455778;
      param_1 = puVar11;
      if (puVar11 == puVar8) {
        for (; (puVar12 < DAT_0045576c && (puVar12[2] == 0)); puVar12 = puVar12 + 5) {
        }
        param_1 = puVar12;
        if ((puVar12 == DAT_0045576c) && (param_1 = FUN_0040ca51(), param_1 == (uint *)0x0)) {
          return (int *)0x0;
        }
      }
      iVar7 = FUN_0040cb02((int)param_1);
      *(int *)param_1[4] = iVar7;
      if (*(int *)param_1[4] == -1) {
        return (int *)0x0;
      }
    }
  }
  piVar4 = (int *)param_1[4];
  local_8 = *piVar4;
  if ((local_8 == -1) ||
     ((piVar4[local_8 + 0x31] & local_c) == 0 && (piVar4[local_8 + 0x11] & local_10) == 0)) {
    local_8 = 0;
    puVar8 = (uint *)(piVar4 + 0x11);
    if ((piVar4[0x31] & local_c) == 0 && (piVar4[0x11] & local_10) == 0) {
      do {
        puVar11 = puVar8 + 0x21;
        local_8 = local_8 + 1;
        puVar8 = puVar8 + 1;
      } while ((*puVar11 & local_c) == 0 && (local_10 & *puVar8) == 0);
    }
  }
  iVar7 = 0;
  piVar2 = piVar4 + local_8 * 0x81 + 0x51;
  local_10 = piVar4[local_8 + 0x11] & local_10;
  if (local_10 == 0) {
    local_10 = piVar4[local_8 + 0x31] & local_c;
    iVar7 = 0x20;
  }
  for (; -1 < (int)local_10; local_10 = local_10 << 1) {
    iVar7 = iVar7 + 1;
  }
  piVar10 = (int *)piVar2[iVar7 * 2 + 1];
  iVar9 = *piVar10 - uVar6;
  iVar14 = (iVar9 >> 4) + -1;
  if (0x3f < iVar14) {
    iVar14 = 0x3f;
  }
  DAT_0045576c = param_1;
  if (iVar14 != iVar7) {
    if (piVar10[1] == piVar10[2]) {
      if (iVar7 < 0x20) {
        pcVar1 = (char *)((int)piVar4 + iVar7 + 4);
        uVar13 = ~(0x80000000U >> ((byte)iVar7 & 0x1f));
        piVar4[local_8 + 0x11] = uVar13 & piVar4[local_8 + 0x11];
        *pcVar1 = *pcVar1 + -1;
        if (*pcVar1 == '\0') {
          *param_1 = *param_1 & uVar13;
        }
      }
      else {
        pcVar1 = (char *)((int)piVar4 + iVar7 + 4);
        uVar13 = ~(0x80000000U >> ((byte)iVar7 - 0x20 & 0x1f));
        piVar4[local_8 + 0x31] = piVar4[local_8 + 0x31] & uVar13;
        *pcVar1 = *pcVar1 + -1;
        if (*pcVar1 == '\0') {
          param_1[1] = param_1[1] & uVar13;
        }
      }
    }
    *(int *)(piVar10[2] + 4) = piVar10[1];
    *(int *)(piVar10[1] + 8) = piVar10[2];
    if (iVar9 == 0) goto LAB_0040ca0e;
    piVar10[1] = piVar2[iVar14 * 2 + 1];
    piVar10[2] = (int)(piVar2 + iVar14 * 2);
    (piVar2 + iVar14 * 2)[1] = (int)piVar10;
    *(int **)(piVar10[1] + 8) = piVar10;
    if (piVar10[1] == piVar10[2]) {
      cVar3 = *(char *)(iVar14 + 4 + (int)piVar4);
      bVar5 = (byte)iVar14;
      if (iVar14 < 0x20) {
        *(char *)(iVar14 + 4 + (int)piVar4) = cVar3 + '\x01';
        if (cVar3 == '\0') {
          *param_1 = *param_1 | 0x80000000U >> (bVar5 & 0x1f);
        }
        piVar4[local_8 + 0x11] = piVar4[local_8 + 0x11] | 0x80000000U >> (bVar5 & 0x1f);
      }
      else {
        *(char *)(iVar14 + 4 + (int)piVar4) = cVar3 + '\x01';
        if (cVar3 == '\0') {
          param_1[1] = param_1[1] | 0x80000000U >> (bVar5 - 0x20 & 0x1f);
        }
        piVar4[local_8 + 0x31] = piVar4[local_8 + 0x31] | 0x80000000U >> (bVar5 - 0x20 & 0x1f);
      }
    }
  }
  if (iVar9 != 0) {
    *piVar10 = iVar9;
    *(int *)(iVar9 + -4 + (int)piVar10) = iVar9;
  }
LAB_0040ca0e:
  piVar10 = (int *)((int)piVar10 + iVar9);
  *piVar10 = uVar6 + 1;
  *(uint *)((int)piVar10 + (uVar6 - 4)) = uVar6 + 1;
  iVar7 = *piVar2;
  *piVar2 = iVar7 + 1;
  if (((iVar7 == 0) && (param_1 == DAT_00455770)) && (local_8 == DAT_00455768)) {
    DAT_00455770 = (uint *)0x0;
  }
  *piVar4 = local_8;
  return piVar10 + 1;
}



/* 0040ca51 FUN_0040ca51 */

undefined4 * FUN_0040ca51(void)

{
  undefined4 *puVar1;
  LPVOID pvVar2;
  
  if (DAT_00455774 == DAT_00455764) {
    pvVar2 = HeapReAlloc(DAT_0045577c,0,DAT_00455778,(DAT_00455764 * 5 + 0x50) * 4);
    if (pvVar2 == (LPVOID)0x0) {
      return (undefined4 *)0x0;
    }
    DAT_00455764 = DAT_00455764 + 0x10;
    DAT_00455778 = pvVar2;
  }
  puVar1 = (undefined4 *)((int)DAT_00455778 + DAT_00455774 * 0x14);
  pvVar2 = HeapAlloc(DAT_0045577c,8,0x41c4);
  puVar1[4] = pvVar2;
  if (pvVar2 != (LPVOID)0x0) {
    pvVar2 = VirtualAlloc((LPVOID)0x0,0x100000,0x2000,4);
    puVar1[3] = pvVar2;
    if (pvVar2 != (LPVOID)0x0) {
      puVar1[2] = 0xffffffff;
      *puVar1 = 0;
      puVar1[1] = 0;
      DAT_00455774 = DAT_00455774 + 1;
      *(undefined4 *)puVar1[4] = 0xffffffff;
      return puVar1;
    }
    HeapFree(DAT_0045577c,0,(LPVOID)puVar1[4]);
  }
  return (undefined4 *)0x0;
}



/* 0040cb02 FUN_0040cb02 */

int __cdecl FUN_0040cb02(int param_1)

{
  int *piVar1;
  char cVar2;
  int iVar3;
  int iVar4;
  int iVar5;
  LPVOID pvVar6;
  int *piVar7;
  int iVar8;
  int iVar9;
  int *lpAddress;
  
  iVar3 = *(int *)(param_1 + 0x10);
  iVar9 = 0;
  for (iVar4 = *(int *)(param_1 + 8); -1 < iVar4; iVar4 = iVar4 << 1) {
    iVar9 = iVar9 + 1;
  }
  iVar8 = 0x3f;
  iVar4 = iVar9 * 0x204 + 0x144 + iVar3;
  iVar5 = iVar4;
  do {
    *(int *)(iVar5 + 8) = iVar5;
    *(int *)(iVar5 + 4) = iVar5;
    iVar5 = iVar5 + 8;
    iVar8 = iVar8 + -1;
  } while (iVar8 != 0);
  lpAddress = (int *)(iVar9 * 0x8000 + *(int *)(param_1 + 0xc));
  pvVar6 = VirtualAlloc(lpAddress,0x8000,0x1000,4);
  if (pvVar6 == (LPVOID)0x0) {
    iVar9 = -1;
  }
  else {
    if (lpAddress <= lpAddress + 0x1c00) {
      piVar7 = lpAddress + 4;
      do {
        piVar7[-2] = -1;
        piVar7[0x3fb] = -1;
        piVar7[-1] = 0xff0;
        *piVar7 = (int)(piVar7 + 0x3ff);
        piVar7[1] = (int)(piVar7 + -0x401);
        piVar7[0x3fa] = 0xff0;
        piVar1 = piVar7 + 0x3fc;
        piVar7 = piVar7 + 0x400;
      } while (piVar1 <= lpAddress + 0x1c00);
    }
    *(int **)(iVar4 + 0x1fc) = lpAddress + 3;
    lpAddress[5] = iVar4 + 0x1f8;
    *(int **)(iVar4 + 0x200) = lpAddress + 0x1c03;
    lpAddress[0x1c04] = iVar4 + 0x1f8;
    *(undefined4 *)(iVar3 + 0x44 + iVar9 * 4) = 0;
    *(undefined4 *)(iVar3 + 0xc4 + iVar9 * 4) = 1;
    cVar2 = *(char *)(iVar3 + 0x43);
    *(char *)(iVar3 + 0x43) = cVar2 + '\x01';
    if (cVar2 == '\0') {
      *(uint *)(param_1 + 4) = *(uint *)(param_1 + 4) | 1;
    }
    *(uint *)(param_1 + 8) = *(uint *)(param_1 + 8) & ~(0x80000000U >> ((byte)iVar9 & 0x1f));
  }
  return iVar9;
}



/* 0040cbfd FUN_0040cbfd */

undefined4 __cdecl FUN_0040cbfd(uint *param_1,int param_2,int param_3)

{
  char *pcVar1;
  int *piVar2;
  int iVar3;
  char cVar4;
  uint uVar5;
  int iVar6;
  uint *puVar7;
  byte bVar8;
  int iVar9;
  uint uVar10;
  uint uVar11;
  uint uVar12;
  uint uVar13;
  uint local_c;
  
  uVar5 = param_1[4];
  uVar12 = param_3 + 0x17U & 0xfffffff0;
  uVar10 = param_2 - param_1[3] >> 0xf;
  iVar3 = uVar10 * 0x204 + 0x144 + uVar5;
  iVar6 = *(int *)(param_2 + -4);
  iVar9 = iVar6 + -1;
  uVar13 = *(uint *)(iVar6 + -5 + param_2);
  iVar6 = iVar6 + -5 + param_2;
  if (iVar9 < (int)uVar12) {
    if (((uVar13 & 1) != 0) || ((int)(uVar13 + iVar9) < (int)uVar12)) {
      return 0;
    }
    local_c = ((int)uVar13 >> 4) - 1;
    if (0x3f < local_c) {
      local_c = 0x3f;
    }
    if (*(int *)(iVar6 + 4) == *(int *)(iVar6 + 8)) {
      if (local_c < 0x20) {
        pcVar1 = (char *)(local_c + 4 + uVar5);
        uVar11 = ~(0x80000000U >> ((byte)local_c & 0x1f));
        puVar7 = (uint *)(uVar5 + 0x44 + uVar10 * 4);
        *puVar7 = *puVar7 & uVar11;
        *pcVar1 = *pcVar1 + -1;
        if (*pcVar1 == '\0') {
          *param_1 = *param_1 & uVar11;
        }
      }
      else {
        pcVar1 = (char *)(local_c + 4 + uVar5);
        uVar11 = ~(0x80000000U >> ((byte)local_c - 0x20 & 0x1f));
        puVar7 = (uint *)(uVar5 + 0xc4 + uVar10 * 4);
        *puVar7 = *puVar7 & uVar11;
        *pcVar1 = *pcVar1 + -1;
        if (*pcVar1 == '\0') {
          param_1[1] = param_1[1] & uVar11;
        }
      }
    }
    *(undefined4 *)(*(int *)(iVar6 + 8) + 4) = *(undefined4 *)(iVar6 + 4);
    *(undefined4 *)(*(int *)(iVar6 + 4) + 8) = *(undefined4 *)(iVar6 + 8);
    iVar6 = uVar13 + (iVar9 - uVar12);
    if (0 < iVar6) {
      uVar13 = (iVar6 >> 4) - 1;
      iVar9 = param_2 + -4 + uVar12;
      if (0x3f < uVar13) {
        uVar13 = 0x3f;
      }
      iVar3 = iVar3 + uVar13 * 8;
      *(undefined4 *)(iVar9 + 4) = *(undefined4 *)(iVar3 + 4);
      *(int *)(iVar9 + 8) = iVar3;
      *(int *)(iVar3 + 4) = iVar9;
      *(int *)(*(int *)(iVar9 + 4) + 8) = iVar9;
      if (*(int *)(iVar9 + 4) == *(int *)(iVar9 + 8)) {
        cVar4 = *(char *)(uVar13 + 4 + uVar5);
        *(char *)(uVar13 + 4 + uVar5) = cVar4 + '\x01';
        bVar8 = (byte)uVar13;
        if (uVar13 < 0x20) {
          if (cVar4 == '\0') {
            *param_1 = *param_1 | 0x80000000U >> (bVar8 & 0x1f);
          }
          puVar7 = (uint *)(uVar5 + 0x44 + uVar10 * 4);
        }
        else {
          if (cVar4 == '\0') {
            param_1[1] = param_1[1] | 0x80000000U >> (bVar8 - 0x20 & 0x1f);
          }
          puVar7 = (uint *)(uVar5 + 0xc4 + uVar10 * 4);
          bVar8 = bVar8 - 0x20;
        }
        *puVar7 = *puVar7 | 0x80000000U >> (bVar8 & 0x1f);
      }
      piVar2 = (int *)(param_2 + -4 + uVar12);
      *piVar2 = iVar6;
      *(int *)(iVar6 + -4 + (int)piVar2) = iVar6;
    }
    *(uint *)(param_2 + -4) = uVar12 + 1;
    *(uint *)(param_2 + -8 + uVar12) = uVar12 + 1;
  }
  else if ((int)uVar12 < iVar9) {
    param_3 = iVar9 - uVar12;
    *(uint *)(param_2 + -4) = uVar12 + 1;
    piVar2 = (int *)(param_2 + -4 + uVar12);
    uVar11 = (param_3 >> 4) - 1;
    piVar2[-1] = uVar12 + 1;
    if (0x3f < uVar11) {
      uVar11 = 0x3f;
    }
    if ((uVar13 & 1) == 0) {
      uVar12 = ((int)uVar13 >> 4) - 1;
      if (0x3f < uVar12) {
        uVar12 = 0x3f;
      }
      if (*(int *)(iVar6 + 4) == *(int *)(iVar6 + 8)) {
        if (uVar12 < 0x20) {
          pcVar1 = (char *)(uVar12 + 4 + uVar5);
          uVar12 = ~(0x80000000U >> ((byte)uVar12 & 0x1f));
          puVar7 = (uint *)(uVar5 + 0x44 + uVar10 * 4);
          *puVar7 = *puVar7 & uVar12;
          *pcVar1 = *pcVar1 + -1;
          if (*pcVar1 == '\0') {
            *param_1 = *param_1 & uVar12;
          }
        }
        else {
          pcVar1 = (char *)(uVar12 + 4 + uVar5);
          uVar12 = ~(0x80000000U >> ((byte)uVar12 - 0x20 & 0x1f));
          puVar7 = (uint *)(uVar5 + 0xc4 + uVar10 * 4);
          *puVar7 = *puVar7 & uVar12;
          *pcVar1 = *pcVar1 + -1;
          if (*pcVar1 == '\0') {
            param_1[1] = param_1[1] & uVar12;
          }
        }
      }
      *(undefined4 *)(*(int *)(iVar6 + 8) + 4) = *(undefined4 *)(iVar6 + 4);
      *(undefined4 *)(*(int *)(iVar6 + 4) + 8) = *(undefined4 *)(iVar6 + 8);
      param_3 = param_3 + uVar13;
      uVar11 = (param_3 >> 4) - 1;
      if (0x3f < uVar11) {
        uVar11 = 0x3f;
      }
    }
    iVar6 = iVar3 + uVar11 * 8;
    piVar2[1] = *(int *)(iVar3 + 4 + uVar11 * 8);
    piVar2[2] = iVar6;
    *(int **)(iVar6 + 4) = piVar2;
    *(int **)(piVar2[1] + 8) = piVar2;
    if (piVar2[1] == piVar2[2]) {
      cVar4 = *(char *)(uVar11 + 4 + uVar5);
      *(char *)(uVar11 + 4 + uVar5) = cVar4 + '\x01';
      bVar8 = (byte)uVar11;
      if (uVar11 < 0x20) {
        if (cVar4 == '\0') {
          *param_1 = *param_1 | 0x80000000U >> (bVar8 & 0x1f);
        }
        puVar7 = (uint *)(uVar5 + 0x44 + uVar10 * 4);
      }
      else {
        if (cVar4 == '\0') {
          param_1[1] = param_1[1] | 0x80000000U >> (bVar8 - 0x20 & 0x1f);
        }
        puVar7 = (uint *)(uVar5 + 0xc4 + uVar10 * 4);
        bVar8 = bVar8 - 0x20;
      }
      *puVar7 = *puVar7 | 0x80000000U >> (bVar8 & 0x1f);
    }
    *piVar2 = param_3;
    *(int *)(param_3 + -4 + (int)piVar2) = param_3;
  }
  return 1;
}



/* 0040cef3 FUN_0040cef3 */

undefined4 __cdecl FUN_0040cef3(undefined4 param_1)

{
  int iVar1;
  
  if (DAT_00452e44 != (code *)0x0) {
    iVar1 = (*DAT_00452e44)(param_1);
    if (iVar1 != 0) {
      return 1;
    }
  }
  return 0;
}



/* 0040cf0e FUN_0040cf0e */

void FUN_0040cf0e(void)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  undefined4 *puVar3;
  DWORD DVar4;
  HANDLE hFile;
  byte *pbVar5;
  int iVar6;
  UINT *pUVar7;
  UINT UVar8;
  UINT UVar9;
  uint uVar10;
  _STARTUPINFOA local_44;
  
  puVar2 = _malloc(0x100);
  if (puVar2 == (undefined4 *)0x0) {
    __amsg_exit(0x1b);
  }
  DAT_00455760 = 0x20;
  DAT_00455660 = puVar2;
  for (; puVar2 < DAT_00455660 + 0x40; puVar2 = puVar2 + 2) {
    *(undefined1 *)(puVar2 + 1) = 0;
    *puVar2 = 0xffffffff;
    *(undefined1 *)((int)puVar2 + 5) = 10;
  }
  GetStartupInfoA(&local_44);
  if ((local_44.cbReserved2 != 0) && ((UINT *)local_44.lpReserved2 != (UINT *)0x0)) {
    UVar8 = *(UINT *)local_44.lpReserved2;
    pUVar7 = (UINT *)((int)local_44.lpReserved2 + 4);
    pbVar5 = (byte *)(UVar8 + (int)pUVar7);
    if (0x7ff < (int)UVar8) {
      UVar8 = 0x800;
    }
    UVar9 = UVar8;
    if ((int)DAT_00455760 < (int)UVar8) {
      puVar2 = &DAT_00455664;
      do {
        puVar3 = _malloc(0x100);
        UVar9 = DAT_00455760;
        if (puVar3 == (undefined4 *)0x0) break;
        DAT_00455760 = DAT_00455760 + 0x20;
        *puVar2 = puVar3;
        puVar1 = puVar3;
        for (; puVar3 < puVar1 + 0x40; puVar3 = puVar3 + 2) {
          *(undefined1 *)(puVar3 + 1) = 0;
          *puVar3 = 0xffffffff;
          *(undefined1 *)((int)puVar3 + 5) = 10;
          puVar1 = (undefined4 *)*puVar2;
        }
        puVar2 = puVar2 + 1;
        UVar9 = UVar8;
      } while ((int)DAT_00455760 < (int)UVar8);
    }
    uVar10 = 0;
    if (0 < (int)UVar9) {
      do {
        if (((*(HANDLE *)pbVar5 != (HANDLE)0xffffffff) && ((*pUVar7 & 1) != 0)) &&
           (((*pUVar7 & 8) != 0 || (DVar4 = GetFileType(*(HANDLE *)pbVar5), DVar4 != 0)))) {
          puVar2 = (undefined4 *)((int)(&DAT_00455660)[(int)uVar10 >> 5] + (uVar10 & 0x1f) * 8);
          *puVar2 = *(undefined4 *)pbVar5;
          *(byte *)(puVar2 + 1) = (byte)*pUVar7;
        }
        uVar10 = uVar10 + 1;
        pUVar7 = (UINT *)((int)pUVar7 + 1);
        pbVar5 = pbVar5 + 4;
      } while ((int)uVar10 < (int)UVar9);
    }
  }
  iVar6 = 0;
  do {
    puVar2 = DAT_00455660 + iVar6 * 2;
    if (DAT_00455660[iVar6 * 2] == -1) {
      *(undefined1 *)(puVar2 + 1) = 0x81;
      if (iVar6 == 0) {
        DVar4 = 0xfffffff6;
      }
      else {
        DVar4 = 0xfffffff5 - (iVar6 != 1);
      }
      hFile = GetStdHandle(DVar4);
      if ((hFile != (HANDLE)0xffffffff) && (DVar4 = GetFileType(hFile), DVar4 != 0)) {
        *puVar2 = hFile;
        if ((DVar4 & 0xff) != 2) {
          if ((DVar4 & 0xff) == 3) {
            *(byte *)(puVar2 + 1) = *(byte *)(puVar2 + 1) | 8;
          }
          goto LAB_0040d09f;
        }
      }
      *(byte *)(puVar2 + 1) = *(byte *)(puVar2 + 1) | 0x40;
    }
    else {
      *(byte *)(puVar2 + 1) = *(byte *)(puVar2 + 1) | 0x80;
    }
LAB_0040d09f:
    iVar6 = iVar6 + 1;
    if (2 < iVar6) {
      SetHandleCount(DAT_00455760);
      return;
    }
  } while( true );
}



/* 0040d0b9 FUN_0040d0b9 */

int * __cdecl FUN_0040d0b9(int param_1,int param_2)

{
  int *piVar1;
  int iVar2;
  uint *_Size;
  uint *puVar3;
  
  _Size = (uint *)(param_1 * param_2);
  puVar3 = _Size;
  if (_Size < (uint *)0xffffffe1) {
    if (_Size == (uint *)0x0) {
      puVar3 = (uint *)0x1;
    }
    puVar3 = (uint *)((int)puVar3 + 0xfU & 0xfffffff0);
  }
  do {
    if (puVar3 < (uint *)0xffffffe1) {
      if ((_Size < DAT_0043072c || (int)_Size - (int)DAT_0043072c == 0) &&
         (piVar1 = FUN_0040c748(_Size), piVar1 != (int *)0x0)) {
        _memset(piVar1,0,(size_t)_Size);
        return piVar1;
      }
      piVar1 = HeapAlloc(DAT_0045577c,8,(SIZE_T)puVar3);
      if (piVar1 != (int *)0x0) {
        return piVar1;
      }
    }
    if (DAT_00452e40 == 0) {
      return (int *)0x0;
    }
    iVar2 = FUN_0040cef3(puVar3);
  } while (iVar2 != 0);
  return (int *)0x0;
}



/* 0040d18e FUN_0040d18e */

void FUN_0040d18e(void)

{
  if (PTR_FUN_00431364 != (undefined *)0x0) {
    (*(code *)PTR_FUN_00431364)();
  }
  FUN_0040d276((undefined4 *)&DAT_0042f008,(undefined4 *)&DAT_0042f014);
  FUN_0040d276((undefined4 *)&DAT_0042f000,(undefined4 *)&DAT_0042f004);
  return;
}



/* 0040d1bb FUN_0040d1bb */

void __cdecl FUN_0040d1bb(UINT param_1)

{
  FUN_0040d1dd(param_1,0,0);
  return;
}



/* 0040d1cc __exit */

/* Library Function - Single Match
    __exit
   
   Library: Visual Studio 2003 Release */

void __cdecl __exit(int _Code)

{
  FUN_0040d1dd(_Code,1,0);
  return;
}



/* 0040d1dd FUN_0040d1dd */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void __cdecl FUN_0040d1dd(UINT param_1,int param_2,int param_3)

{
  HANDLE hProcess;
  undefined4 *puVar1;
  UINT uExitCode;
  
  if (DAT_00452e90 == 1) {
    uExitCode = param_1;
    hProcess = GetCurrentProcess();
    TerminateProcess(hProcess,uExitCode);
  }
  _DAT_00452e8c = 1;
  DAT_00452e88 = (undefined1)param_3;
  if (param_2 == 0) {
    if ((DAT_00455654 != (undefined4 *)0x0) &&
       (puVar1 = (undefined4 *)(DAT_00455650 - 4), DAT_00455654 <= puVar1)) {
      do {
        if ((code *)*puVar1 != (code *)0x0) {
          (*(code *)*puVar1)();
        }
        puVar1 = puVar1 + -1;
      } while (DAT_00455654 <= puVar1);
    }
    FUN_0040d276((undefined4 *)&DAT_0042f018,(undefined4 *)&DAT_0042f020);
  }
  FUN_0040d276((undefined4 *)&DAT_0042f024,(undefined4 *)&DAT_0042f028);
  if (param_3 != 0) {
    return;
  }
  DAT_00452e90 = 1;
                    /* WARNING: Subroutine does not return */
  ExitProcess(param_1);
}



/* 0040d276 FUN_0040d276 */

void __cdecl FUN_0040d276(undefined4 *param_1,undefined4 *param_2)

{
  for (; param_1 < param_2; param_1 = param_1 + 1) {
    if ((code *)*param_1 != (code *)0x0) {
      (*(code *)*param_1)();
    }
  }
  return;
}



/* 0040d290 FUN_0040d290 */

int __cdecl FUN_0040d290(int *param_1)

{
  int iVar1;
  
  if (param_1 == (int *)0x0) {
    iVar1 = flsall(0);
    return iVar1;
  }
  iVar1 = FUN_0040d2cb(param_1);
  if (iVar1 != 0) {
    return -1;
  }
  if ((*(byte *)((int)param_1 + 0xd) & 0x40) != 0) {
    iVar1 = FUN_0040f048(param_1[4]);
    return -(uint)(iVar1 != 0);
  }
  return 0;
}



/* 0040d2cb FUN_0040d2cb */

undefined4 __cdecl FUN_0040d2cb(int *param_1)

{
  uint uVar1;
  undefined4 uVar2;
  uint uVar3;
  
  uVar2 = 0;
  if ((((byte)param_1[3] & 3) == 2) && ((param_1[3] & 0x108U) != 0)) {
    uVar3 = *param_1 - param_1[2];
    if (0 < (int)uVar3) {
      uVar1 = FUN_0040daba(param_1[4],(char *)param_1[2],uVar3);
      if (uVar1 == uVar3) {
        if ((param_1[3] & 0x80U) != 0) {
          param_1[3] = param_1[3] & 0xfffffffd;
        }
      }
      else {
        param_1[3] = param_1[3] | 0x20;
        uVar2 = 0xffffffff;
      }
    }
  }
  param_1[1] = 0;
  *param_1 = param_1[2];
  return uVar2;
}



/* 0040d330 flsall */

/* Library Function - Single Match
    _flsall
   
   Library: Visual Studio 2003 Release */

int __cdecl flsall(int param_1)

{
  int *piVar1;
  int iVar2;
  int iVar3;
  int iVar4;
  int iVar5;
  
  iVar4 = 0;
  iVar3 = 0;
  iVar5 = 0;
  if (0 < DAT_004567a0) {
    do {
      piVar1 = *(int **)(DAT_00455784 + iVar4 * 4);
      if ((piVar1 != (int *)0x0) && ((piVar1[3] & 0x83U) != 0)) {
        if (param_1 == 1) {
          iVar2 = FUN_0040d290(piVar1);
          if (iVar2 != -1) {
            iVar3 = iVar3 + 1;
          }
        }
        else if ((param_1 == 0) && ((piVar1[3] & 2U) != 0)) {
          iVar2 = FUN_0040d290(piVar1);
          if (iVar2 == -1) {
            iVar5 = -1;
          }
        }
      }
      iVar4 = iVar4 + 1;
    } while (iVar4 < DAT_004567a0);
  }
  if (param_1 != 1) {
    iVar3 = iVar5;
  }
  return iVar3;
}



/* 0040d39d FUN_0040d39d */

uint __cdecl FUN_0040d39d(undefined4 *param_1)

{
  byte bVar1;
  uint uVar2;
  int iVar3;
  undefined *puVar4;
  
  uVar2 = param_1[3];
  if (((uVar2 & 0x83) != 0) && ((uVar2 & 0x40) == 0)) {
    if ((uVar2 & 2) == 0) {
      param_1[3] = uVar2 | 1;
      if ((uVar2 & 0x10c) == 0) {
        FUN_0040f09f(param_1);
      }
      else {
        *param_1 = param_1[2];
      }
      iVar3 = FUN_0040d476(param_1[4],(char *)param_1[2],(char *)param_1[6]);
      param_1[1] = iVar3;
      if ((iVar3 != 0) && (iVar3 != -1)) {
        if ((param_1[3] & 0x82) == 0) {
          uVar2 = param_1[4];
          if (uVar2 == 0xffffffff) {
            puVar4 = &DAT_00430730;
          }
          else {
            puVar4 = (undefined *)((&DAT_00455660)[(int)uVar2 >> 5] + (uVar2 & 0x1f) * 8);
          }
          if ((puVar4[4] & 0x82) == 0x82) {
            param_1[3] = param_1[3] | 0x2000;
          }
        }
        if (((param_1[6] == 0x200) && ((param_1[3] & 8) != 0)) && ((param_1[3] & 0x400) == 0)) {
          param_1[6] = 0x1000;
        }
        param_1[1] = iVar3 + -1;
        bVar1 = *(byte *)*param_1;
        *param_1 = (byte *)*param_1 + 1;
        return (uint)bVar1;
      }
      param_1[3] = param_1[3] | (-(uint)(iVar3 != 0) & 0x10) + 0x10;
      param_1[1] = 0;
    }
    else {
      param_1[3] = uVar2 | 0x20;
    }
  }
  return 0xffffffff;
}



/* 0040d476 FUN_0040d476 */

int __cdecl FUN_0040d476(uint param_1,char *param_2,char *param_3)

{
  int *piVar1;
  byte *pbVar2;
  char cVar3;
  byte bVar4;
  BOOL BVar5;
  DWORD DVar6;
  int iVar7;
  char *pcVar8;
  DWORD local_10;
  char *local_c;
  char local_5;
  
  if (param_1 < DAT_00455760) {
    iVar7 = (param_1 & 0x1f) * 8;
    piVar1 = &DAT_00455660 + ((int)param_1 >> 5);
    bVar4 = *(byte *)((&DAT_00455660)[(int)param_1 >> 5] + iVar7 + 4);
    if ((bVar4 & 1) != 0) {
      local_c = (char *)0x0;
      if ((param_3 == (char *)0x0) || ((bVar4 & 2) != 0)) {
        return 0;
      }
      pcVar8 = param_2;
      if (((bVar4 & 0x48) != 0) &&
         (cVar3 = *(char *)((&DAT_00455660)[(int)param_1 >> 5] + iVar7 + 5), cVar3 != '\n')) {
        param_3 = param_3 + -1;
        *param_2 = cVar3;
        pcVar8 = param_2 + 1;
        local_c = (char *)0x1;
        *(undefined1 *)(*piVar1 + 5 + iVar7) = 10;
      }
      BVar5 = ReadFile(*(HANDLE *)(*piVar1 + iVar7),pcVar8,(DWORD)param_3,&local_10,
                       (LPOVERLAPPED)0x0);
      if (BVar5 == 0) {
        DVar6 = GetLastError();
        if (DVar6 == 5) {
          DAT_00452e48 = 9;
          DAT_00452e4c = 5;
          return -1;
        }
        if (DVar6 != 0x6d) {
          FUN_0040f0e3(DVar6);
          return -1;
        }
        return 0;
      }
      bVar4 = *(byte *)(*piVar1 + 4 + iVar7);
      if ((bVar4 & 0x80) == 0) {
        return (int)local_c + local_10;
      }
      if ((local_10 == 0) || (*param_2 != '\n')) {
        bVar4 = bVar4 & 0xfb;
      }
      else {
        bVar4 = bVar4 | 4;
      }
      *(byte *)(*piVar1 + 4 + iVar7) = bVar4;
      param_3 = param_2;
      local_c = param_2 + (int)local_c + local_10;
      pcVar8 = param_2;
      if (param_2 < local_c) {
        do {
          cVar3 = *param_3;
          if (cVar3 == '\x1a') {
            pbVar2 = (byte *)(*piVar1 + 4 + iVar7);
            bVar4 = *pbVar2;
            if ((bVar4 & 0x40) == 0) {
              *pbVar2 = bVar4 | 2;
            }
            break;
          }
          if (cVar3 == '\r') {
            if (param_3 < local_c + -1) {
              if (param_3[1] == '\n') {
                param_3 = param_3 + 2;
                goto LAB_0040d60c;
              }
              *pcVar8 = '\r';
              pcVar8 = pcVar8 + 1;
              param_3 = param_3 + 1;
            }
            else {
              param_3 = param_3 + 1;
              BVar5 = ReadFile(*(HANDLE *)(*piVar1 + iVar7),&local_5,1,&local_10,(LPOVERLAPPED)0x0);
              if (((BVar5 == 0) && (DVar6 = GetLastError(), DVar6 != 0)) || (local_10 == 0)) {
LAB_0040d626:
                *pcVar8 = '\r';
LAB_0040d629:
                pcVar8 = pcVar8 + 1;
              }
              else if ((*(byte *)(*piVar1 + 4 + iVar7) & 0x48) == 0) {
                if ((pcVar8 == param_2) && (local_5 == '\n')) {
LAB_0040d60c:
                  *pcVar8 = '\n';
                  goto LAB_0040d629;
                }
                FUN_0040df2d(param_1,-1,1);
                if (local_5 != '\n') goto LAB_0040d626;
              }
              else {
                if (local_5 == '\n') goto LAB_0040d60c;
                *pcVar8 = '\r';
                pcVar8 = pcVar8 + 1;
                *(char *)(*piVar1 + 5 + iVar7) = local_5;
              }
            }
          }
          else {
            *pcVar8 = cVar3;
            pcVar8 = pcVar8 + 1;
            param_3 = param_3 + 1;
          }
        } while (param_3 < local_c);
      }
      return (int)pcVar8 - (int)param_2;
    }
  }
  DAT_00452e4c = 0;
  DAT_00452e48 = 9;
  return -1;
}



/* 0040d670 FUN_0040d670 */

undefined4 * __cdecl FUN_0040d670(undefined4 *param_1,undefined4 *param_2,uint param_3)

{
  uint uVar1;
  uint uVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  
  if ((param_2 < param_1) && (param_1 < (undefined4 *)(param_3 + (int)param_2))) {
    puVar3 = (undefined4 *)((param_3 - 4) + (int)param_2);
    puVar4 = (undefined4 *)((param_3 - 4) + (int)param_1);
    if (((uint)puVar4 & 3) == 0) {
      uVar1 = param_3 >> 2;
      uVar2 = param_3 & 3;
      if (7 < uVar1) {
        for (; uVar1 != 0; uVar1 = uVar1 - 1) {
          *puVar4 = *puVar3;
          puVar3 = puVar3 + -1;
          puVar4 = puVar4 + -1;
        }
        switch(uVar2) {
        case 0:
          return param_1;
        case 2:
          goto switchD_0040d827_caseD_2;
        case 3:
          goto switchD_0040d827_caseD_3;
        }
        goto switchD_0040d827_caseD_1;
      }
    }
    else {
      switch(param_3) {
      case 0:
        goto switchD_0040d827_caseD_0;
      case 1:
        goto switchD_0040d827_caseD_1;
      case 2:
        goto switchD_0040d827_caseD_2;
      case 3:
        goto switchD_0040d827_caseD_3;
      default:
        uVar1 = param_3 - ((uint)puVar4 & 3);
        switch((uint)puVar4 & 3) {
        case 1:
          uVar2 = uVar1 & 3;
          *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
          puVar3 = (undefined4 *)((int)puVar3 + -1);
          uVar1 = uVar1 >> 2;
          puVar4 = (undefined4 *)((int)puVar4 - 1);
          if (7 < uVar1) {
            for (; uVar1 != 0; uVar1 = uVar1 - 1) {
              *puVar4 = *puVar3;
              puVar3 = puVar3 + -1;
              puVar4 = puVar4 + -1;
            }
            switch(uVar2) {
            case 0:
              return param_1;
            case 2:
              goto switchD_0040d827_caseD_2;
            case 3:
              goto switchD_0040d827_caseD_3;
            }
            goto switchD_0040d827_caseD_1;
          }
          break;
        case 2:
          uVar2 = uVar1 & 3;
          *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
          uVar1 = uVar1 >> 2;
          *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
          puVar3 = (undefined4 *)((int)puVar3 + -2);
          puVar4 = (undefined4 *)((int)puVar4 - 2);
          if (7 < uVar1) {
            for (; uVar1 != 0; uVar1 = uVar1 - 1) {
              *puVar4 = *puVar3;
              puVar3 = puVar3 + -1;
              puVar4 = puVar4 + -1;
            }
            switch(uVar2) {
            case 0:
              return param_1;
            case 2:
              goto switchD_0040d827_caseD_2;
            case 3:
              goto switchD_0040d827_caseD_3;
            }
            goto switchD_0040d827_caseD_1;
          }
          break;
        case 3:
          uVar2 = uVar1 & 3;
          *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
          *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
          uVar1 = uVar1 >> 2;
          *(undefined1 *)((int)puVar4 + 1) = *(undefined1 *)((int)puVar3 + 1);
          puVar3 = (undefined4 *)((int)puVar3 + -3);
          puVar4 = (undefined4 *)((int)puVar4 - 3);
          if (7 < uVar1) {
            for (; uVar1 != 0; uVar1 = uVar1 - 1) {
              *puVar4 = *puVar3;
              puVar3 = puVar3 + -1;
              puVar4 = puVar4 + -1;
            }
            switch(uVar2) {
            case 0:
              return param_1;
            case 2:
              goto switchD_0040d827_caseD_2;
            case 3:
              goto switchD_0040d827_caseD_3;
            }
            goto switchD_0040d827_caseD_1;
          }
        }
      }
    }
    switch(uVar1) {
    case 7:
      puVar4[7 - uVar1] = puVar3[7 - uVar1];
    case 6:
      puVar4[6 - uVar1] = puVar3[6 - uVar1];
    case 5:
      puVar4[5 - uVar1] = puVar3[5 - uVar1];
    case 4:
      puVar4[4 - uVar1] = puVar3[4 - uVar1];
    case 3:
      puVar4[3 - uVar1] = puVar3[3 - uVar1];
    case 2:
      puVar4[2 - uVar1] = puVar3[2 - uVar1];
    case 1:
      puVar4[1 - uVar1] = puVar3[1 - uVar1];
      puVar3 = puVar3 + -uVar1;
      puVar4 = puVar4 + -uVar1;
    }
    switch(uVar2) {
    case 1:
switchD_0040d827_caseD_1:
      *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
      return param_1;
    case 2:
switchD_0040d827_caseD_2:
      *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
      *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
      return param_1;
    case 3:
switchD_0040d827_caseD_3:
      *(undefined1 *)((int)puVar4 + 3) = *(undefined1 *)((int)puVar3 + 3);
      *(undefined1 *)((int)puVar4 + 2) = *(undefined1 *)((int)puVar3 + 2);
      *(undefined1 *)((int)puVar4 + 1) = *(undefined1 *)((int)puVar3 + 1);
      return param_1;
    }
switchD_0040d827_caseD_0:
    return param_1;
  }
  puVar3 = param_1;
  if (((uint)param_1 & 3) == 0) {
    uVar1 = param_3 >> 2;
    uVar2 = param_3 & 3;
    if (7 < uVar1) {
      for (; uVar1 != 0; uVar1 = uVar1 - 1) {
        *puVar3 = *param_2;
        param_2 = param_2 + 1;
        puVar3 = puVar3 + 1;
      }
      switch(uVar2) {
      case 0:
        return param_1;
      case 2:
        goto switchD_0040d6a5_caseD_2;
      case 3:
        goto switchD_0040d6a5_caseD_3;
      }
      goto switchD_0040d6a5_caseD_1;
    }
  }
  else {
    switch(param_3) {
    case 0:
      goto switchD_0040d6a5_caseD_0;
    case 1:
      goto switchD_0040d6a5_caseD_1;
    case 2:
      goto switchD_0040d6a5_caseD_2;
    case 3:
      goto switchD_0040d6a5_caseD_3;
    default:
      uVar1 = (param_3 - 4) + ((uint)param_1 & 3);
      switch((uint)param_1 & 3) {
      case 1:
        uVar2 = uVar1 & 3;
        *(undefined1 *)param_1 = *(undefined1 *)param_2;
        *(undefined1 *)((int)param_1 + 1) = *(undefined1 *)((int)param_2 + 1);
        uVar1 = uVar1 >> 2;
        *(undefined1 *)((int)param_1 + 2) = *(undefined1 *)((int)param_2 + 2);
        param_2 = (undefined4 *)((int)param_2 + 3);
        puVar3 = (undefined4 *)((int)param_1 + 3);
        if (7 < uVar1) {
          for (; uVar1 != 0; uVar1 = uVar1 - 1) {
            *puVar3 = *param_2;
            param_2 = param_2 + 1;
            puVar3 = puVar3 + 1;
          }
          switch(uVar2) {
          case 0:
            return param_1;
          case 2:
            goto switchD_0040d6a5_caseD_2;
          case 3:
            goto switchD_0040d6a5_caseD_3;
          }
          goto switchD_0040d6a5_caseD_1;
        }
        break;
      case 2:
        uVar2 = uVar1 & 3;
        *(undefined1 *)param_1 = *(undefined1 *)param_2;
        uVar1 = uVar1 >> 2;
        *(undefined1 *)((int)param_1 + 1) = *(undefined1 *)((int)param_2 + 1);
        param_2 = (undefined4 *)((int)param_2 + 2);
        puVar3 = (undefined4 *)((int)param_1 + 2);
        if (7 < uVar1) {
          for (; uVar1 != 0; uVar1 = uVar1 - 1) {
            *puVar3 = *param_2;
            param_2 = param_2 + 1;
            puVar3 = puVar3 + 1;
          }
          switch(uVar2) {
          case 0:
            return param_1;
          case 2:
            goto switchD_0040d6a5_caseD_2;
          case 3:
            goto switchD_0040d6a5_caseD_3;
          }
          goto switchD_0040d6a5_caseD_1;
        }
        break;
      case 3:
        uVar2 = uVar1 & 3;
        *(undefined1 *)param_1 = *(undefined1 *)param_2;
        param_2 = (undefined4 *)((int)param_2 + 1);
        uVar1 = uVar1 >> 2;
        puVar3 = (undefined4 *)((int)param_1 + 1);
        if (7 < uVar1) {
          for (; uVar1 != 0; uVar1 = uVar1 - 1) {
            *puVar3 = *param_2;
            param_2 = param_2 + 1;
            puVar3 = puVar3 + 1;
          }
          switch(uVar2) {
          case 0:
            return param_1;
          case 2:
            goto switchD_0040d6a5_caseD_2;
          case 3:
            goto switchD_0040d6a5_caseD_3;
          }
          goto switchD_0040d6a5_caseD_1;
        }
      }
    }
  }
  switch(uVar1) {
  case 7:
    puVar3[uVar1 - 7] = param_2[uVar1 - 7];
  case 6:
    puVar3[uVar1 - 6] = param_2[uVar1 - 6];
  case 5:
    puVar3[uVar1 - 5] = param_2[uVar1 - 5];
  case 4:
    puVar3[uVar1 - 4] = param_2[uVar1 - 4];
  case 3:
    puVar3[uVar1 - 3] = param_2[uVar1 - 3];
  case 2:
    puVar3[uVar1 - 2] = param_2[uVar1 - 2];
  case 1:
    puVar3[uVar1 - 1] = param_2[uVar1 - 1];
    param_2 = param_2 + uVar1;
    puVar3 = puVar3 + uVar1;
  }
  switch(uVar2) {
  case 1:
switchD_0040d6a5_caseD_1:
    *(undefined1 *)puVar3 = *(undefined1 *)param_2;
    return param_1;
  case 2:
switchD_0040d6a5_caseD_2:
    *(undefined1 *)puVar3 = *(undefined1 *)param_2;
    *(undefined1 *)((int)puVar3 + 1) = *(undefined1 *)((int)param_2 + 1);
    return param_1;
  case 3:
switchD_0040d6a5_caseD_3:
    *(undefined1 *)puVar3 = *(undefined1 *)param_2;
    *(undefined1 *)((int)puVar3 + 1) = *(undefined1 *)((int)param_2 + 1);
    *(undefined1 *)((int)puVar3 + 2) = *(undefined1 *)((int)param_2 + 2);
    return param_1;
  }
switchD_0040d6a5_caseD_0:
  return param_1;
}



/* 0040d9a5 FUN_0040d9a5 */

uint __cdecl FUN_0040d9a5(uint param_1,int *param_2)

{
  uint uVar1;
  uint uVar2;
  char *pcVar3;
  int *piVar4;
  byte bVar5;
  undefined3 extraout_var;
  undefined *puVar6;
  int *piVar7;
  
  piVar4 = param_2;
  uVar1 = param_2[3];
  uVar2 = param_2[4];
  if (((uVar1 & 0x82) == 0) || ((uVar1 & 0x40) != 0)) {
LAB_0040daae:
    param_2[3] = uVar1 | 0x20;
  }
  else {
    if ((uVar1 & 1) != 0) {
      param_2[1] = 0;
      if ((uVar1 & 0x10) == 0) goto LAB_0040daae;
      *param_2 = param_2[2];
      param_2[3] = uVar1 & 0xfffffffe;
    }
    uVar1 = param_2[3];
    param_2[1] = 0;
    param_2 = (int *)0x0;
    piVar4[3] = uVar1 & 0xffffffef | 2;
    if (((uVar1 & 0x10c) == 0) &&
       (((piVar4 != (int *)&DAT_004302a0 && (piVar4 != (int *)&DAT_004302c0)) ||
        (bVar5 = FUN_0040ede9(uVar2), CONCAT31(extraout_var,bVar5) == 0)))) {
      FUN_0040f09f(piVar4);
    }
    if ((*(ushort *)(piVar4 + 3) & 0x108) == 0) {
      piVar7 = (int *)0x1;
      param_2 = (int *)FUN_0040daba(uVar2,(char *)&param_1,1);
    }
    else {
      pcVar3 = (char *)piVar4[2];
      piVar7 = (int *)(*piVar4 - (int)pcVar3);
      *piVar4 = (int)(pcVar3 + 1);
      piVar4[1] = piVar4[6] + -1;
      if ((int)piVar7 < 1) {
        if (uVar2 == 0xffffffff) {
          puVar6 = &DAT_00430730;
        }
        else {
          puVar6 = (undefined *)((&DAT_00455660)[(int)uVar2 >> 5] + (uVar2 & 0x1f) * 8);
        }
        if ((puVar6[4] & 0x20) != 0) {
          FUN_0040df2d(uVar2,0,2);
        }
      }
      else {
        param_2 = (int *)FUN_0040daba(uVar2,pcVar3,(uint)piVar7);
      }
      *(undefined1 *)piVar4[2] = (undefined1)param_1;
    }
    if (param_2 == piVar7) {
      return param_1 & 0xff;
    }
    piVar4[3] = piVar4[3] | 0x20;
  }
  return 0xffffffff;
}



/* 0040daba FUN_0040daba */

int __cdecl FUN_0040daba(DWORD param_1,char *param_2,uint param_3)

{
  int *piVar1;
  char *pcVar2;
  byte bVar3;
  char cVar4;
  char *pcVar5;
  BOOL BVar6;
  int iVar7;
  char local_418 [1028];
  int local_14;
  DWORD local_10;
  DWORD local_c;
  char *local_8;
  
  if (param_1 < DAT_00455760) {
    piVar1 = &DAT_00455660 + ((int)param_1 >> 5);
    iVar7 = (param_1 & 0x1f) * 8;
    bVar3 = *(byte *)(*piVar1 + 4 + iVar7);
    if ((bVar3 & 1) != 0) {
      local_c = 0;
      local_14 = 0;
      if (param_3 == 0) {
        return 0;
      }
      if ((bVar3 & 0x20) != 0) {
        FUN_0040df2d(param_1,0,2);
      }
      if ((*(byte *)((undefined4 *)(*piVar1 + iVar7) + 1) & 0x80) == 0) {
        BVar6 = WriteFile(*(HANDLE *)(*piVar1 + iVar7),param_2,param_3,&local_10,(LPOVERLAPPED)0x0);
        if (BVar6 == 0) {
          param_1 = GetLastError();
        }
        else {
          local_c = local_10;
          param_1 = 0;
        }
LAB_0040dbb3:
        if (local_c != 0) {
          return local_c - local_14;
        }
        if (param_1 != 0) {
          if (param_1 == 5) {
            DAT_00452e48 = 9;
            DAT_00452e4c = 5;
            return -1;
          }
          FUN_0040f0e3(param_1);
          return -1;
        }
      }
      else {
        local_8 = param_2;
        param_1 = 0;
        if (param_3 != 0) {
          do {
            pcVar5 = local_418;
            do {
              if (param_3 <= (uint)((int)local_8 - (int)param_2)) break;
              pcVar2 = local_8 + 1;
              cVar4 = *local_8;
              local_8 = pcVar2;
              if (cVar4 == '\n') {
                local_14 = local_14 + 1;
                *pcVar5 = '\r';
                pcVar5 = pcVar5 + 1;
              }
              *pcVar5 = cVar4;
              pcVar5 = pcVar5 + 1;
            } while ((int)pcVar5 - (int)local_418 < 0x400);
            BVar6 = WriteFile(*(HANDLE *)(*piVar1 + iVar7),local_418,(int)pcVar5 - (int)local_418,
                              &local_10,(LPOVERLAPPED)0x0);
            if (BVar6 == 0) {
              param_1 = GetLastError();
              goto LAB_0040dbb3;
            }
            local_c = local_c + local_10;
            if (((int)local_10 < (int)pcVar5 - (int)local_418) ||
               (param_3 <= (uint)((int)local_8 - (int)param_2))) goto LAB_0040dbb3;
          } while( true );
        }
      }
      if (((*(byte *)(*piVar1 + 4 + iVar7) & 0x40) != 0) && (*param_2 == '\x1a')) {
        return 0;
      }
      DAT_00452e48 = 0x1c;
      DAT_00452e4c = 0;
      return -1;
    }
  }
  DAT_00452e4c = 0;
  DAT_00452e48 = 9;
  return -1;
}



/* 0040dc67 FUN_0040dc67 */

undefined4 __cdecl FUN_0040dc67(uint param_1)

{
  int iVar1;
  int iVar2;
  HANDLE hObject;
  BOOL BVar3;
  DWORD DVar4;
  int iVar5;
  
  if (DAT_00455760 <= param_1) {
    DAT_00452e4c = 0;
    DAT_00452e48 = 9;
    return 0xffffffff;
  }
  iVar5 = (param_1 & 0x1f) * 8;
  if ((*(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + iVar5) & 1) == 0) {
    DAT_00452e48 = 9;
    DAT_00452e4c = 0;
    return 0xffffffff;
  }
  iVar1 = FUN_0040f2d0(param_1);
  if (iVar1 != -1) {
    if ((param_1 == 1) || (param_1 == 2)) {
      iVar1 = FUN_0040f2d0(2);
      iVar2 = FUN_0040f2d0(1);
      if (iVar2 == iVar1) goto LAB_0040dce0;
    }
    hObject = (HANDLE)FUN_0040f2d0(param_1);
    BVar3 = CloseHandle(hObject);
    if (BVar3 == 0) {
      DVar4 = GetLastError();
      goto LAB_0040dce2;
    }
  }
LAB_0040dce0:
  DVar4 = 0;
LAB_0040dce2:
  FUN_0040f256(param_1);
  *(undefined1 *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + iVar5) = 0;
  if (DVar4 == 0) {
    return 0;
  }
  FUN_0040f0e3(DVar4);
  return 0xffffffff;
}



/* 0040dd1a __freebuf */

/* Library Function - Single Match
    __freebuf
   
   Library: Visual Studio 2003 Release */

void __cdecl __freebuf(FILE *_File)

{
  if (((_File->_flag & 0x83U) != 0) && ((_File->_flag & 8U) != 0)) {
    FUN_0040aa97(_File->_base);
    *(ushort *)&_File->_flag = (ushort)_File->_flag & 0xfbf7;
    _File->_ptr = (char *)0x0;
    _File->_base = (char *)0x0;
    _File->_cnt = 0;
  }
  return;
}



/* 0040dd45 FUN_0040dd45 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined4 * __cdecl FUN_0040dd45(LPCSTR param_1,char *param_2,uint param_3,undefined4 *param_4)

{
  char cVar1;
  bool bVar2;
  bool bVar3;
  bool bVar4;
  uint uVar5;
  uint uVar6;
  
  bVar4 = false;
  bVar3 = false;
  cVar1 = *param_2;
  if (cVar1 == 'a') {
    uVar5 = 0x109;
  }
  else {
    if (cVar1 == 'r') {
      uVar5 = 0;
      uVar6 = DAT_00452fa8 | 1;
      goto LAB_0040dd86;
    }
    if (cVar1 != 'w') {
      return (undefined4 *)0x0;
    }
    uVar5 = 0x301;
  }
  uVar6 = DAT_00452fa8 | 2;
LAB_0040dd86:
  bVar2 = true;
LAB_0040dd89:
  cVar1 = param_2[1];
  param_2 = param_2 + 1;
  if ((cVar1 == '\0') || (!bVar2)) {
    uVar5 = FUN_0040f324(param_1,uVar5,param_3,0x1a4);
    if ((int)uVar5 < 0) {
      return (undefined4 *)0x0;
    }
    _DAT_00452e28 = _DAT_00452e28 + 1;
    param_4[3] = uVar6;
    param_4[1] = 0;
    *param_4 = 0;
    param_4[2] = 0;
    param_4[7] = 0;
    param_4[4] = uVar5;
    return param_4;
  }
  if (cVar1 < 'U') {
    if (cVar1 == 'T') {
      if ((uVar5 & 0x1000) == 0) {
        uVar5 = uVar5 | 0x1000;
        goto LAB_0040dd89;
      }
    }
    else if (cVar1 == '+') {
      if ((uVar5 & 2) == 0) {
        uVar5 = uVar5 & 0xfffffffe | 2;
        uVar6 = uVar6 & 0xfffffffc | 0x80;
        goto LAB_0040dd89;
      }
    }
    else if (cVar1 == 'D') {
      if ((uVar5 & 0x40) == 0) {
        uVar5 = uVar5 | 0x40;
        goto LAB_0040dd89;
      }
    }
    else if (cVar1 == 'R') {
      if (!bVar3) {
        bVar3 = true;
        uVar5 = uVar5 | 0x10;
        goto LAB_0040dd89;
      }
    }
    else if ((cVar1 == 'S') && (!bVar3)) {
      bVar3 = true;
      uVar5 = uVar5 | 0x20;
      goto LAB_0040dd89;
    }
  }
  else {
    if (cVar1 == 'b') {
      if ((uVar5 & 0xc000) != 0) goto LAB_0040de69;
      uVar5 = uVar5 | 0x8000;
      goto LAB_0040dd89;
    }
    if (cVar1 == 'c') {
      if (!bVar4) {
        bVar4 = true;
        uVar6 = uVar6 | 0x4000;
        goto LAB_0040dd89;
      }
    }
    else {
      if (cVar1 != 'n') {
        if ((cVar1 != 't') || ((uVar5 & 0xc000) != 0)) goto LAB_0040de69;
        uVar5 = uVar5 | 0x4000;
        goto LAB_0040dd89;
      }
      if (!bVar4) {
        bVar4 = true;
        uVar6 = uVar6 & 0xffffbfff;
        goto LAB_0040dd89;
      }
    }
  }
LAB_0040de69:
  bVar2 = false;
  goto LAB_0040dd89;
}



/* 0040deb5 FUN_0040deb5 */

undefined4 * FUN_0040deb5(void)

{
  int iVar1;
  void *pvVar2;
  undefined4 *puVar3;
  int *piVar4;
  
  iVar1 = 0;
  piVar4 = DAT_00455784;
  if (0 < DAT_004567a0) {
    do {
      if (*piVar4 == 0) {
        pvVar2 = _malloc(0x20);
        DAT_00455784[iVar1] = (int)pvVar2;
        puVar3 = (undefined4 *)DAT_00455784[iVar1];
        if (puVar3 == (undefined4 *)0x0) {
          return (undefined4 *)0x0;
        }
LAB_0040df10:
        if (puVar3 == (undefined4 *)0x0) {
          return (undefined4 *)0x0;
        }
        puVar3[4] = 0xffffffff;
        puVar3[1] = 0;
        puVar3[3] = 0;
        puVar3[2] = 0;
        *puVar3 = 0;
        puVar3[7] = 0;
        return puVar3;
      }
      if ((*(byte *)(*piVar4 + 0xc) & 0x83) == 0) {
        puVar3 = (undefined4 *)DAT_00455784[iVar1];
        goto LAB_0040df10;
      }
      iVar1 = iVar1 + 1;
      piVar4 = piVar4 + 1;
    } while (iVar1 < DAT_004567a0);
  }
  return (undefined4 *)0x0;
}



/* 0040df2d FUN_0040df2d */

DWORD __cdecl FUN_0040df2d(uint param_1,LONG param_2,DWORD param_3)

{
  byte *pbVar1;
  HANDLE hFile;
  DWORD DVar2;
  uint uVar3;
  int iVar4;
  
  if (param_1 < DAT_00455760) {
    iVar4 = (param_1 & 0x1f) * 8;
    if ((*(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + iVar4) & 1) != 0) {
      hFile = (HANDLE)FUN_0040f2d0(param_1);
      if (hFile == (HANDLE)0xffffffff) {
        DAT_00452e48 = 9;
        return 0xffffffff;
      }
      DVar2 = SetFilePointer(hFile,param_2,(PLONG)0x0,param_3);
      if (DVar2 == 0xffffffff) {
        uVar3 = GetLastError();
      }
      else {
        uVar3 = 0;
      }
      if (uVar3 != 0) {
        FUN_0040f0e3(uVar3);
        return 0xffffffff;
      }
      pbVar1 = (byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + iVar4);
      *pbVar1 = *pbVar1 & 0xfd;
      return DVar2;
    }
  }
  DAT_00452e4c = 0;
  DAT_00452e48 = 9;
  return 0xffffffff;
}



/* 0040dfc7 FUN_0040dfc7 */

char * __cdecl FUN_0040dfc7(int param_1,uint *param_2,undefined4 *param_3,char *param_4)

{
  char *pcVar1;
  char cVar2;
  byte *pbVar3;
  char *pcVar4;
  uint *_Str;
  size_t sVar5;
  char *pcVar6;
  size_t sVar7;
  uint *puVar8;
  byte *pbVar9;
  bool bVar10;
  char *local_c;
  
  local_c = FUN_0040e12a(param_1,param_2,param_3,param_4);
  if ((((local_c == (char *)0xffffffff) && (DAT_00452e48 == 2)) &&
      (pbVar3 = FUN_0040f7a4((byte *)param_2,0x2f), pbVar3 == (byte *)0x0)) &&
     ((pcVar4 = (char *)FUN_0040e308(&DAT_0042f904), pcVar4 != (char *)0x0 &&
      (_Str = _malloc(0x104), _Str != (uint *)0x0)))) {
    do {
      do {
        pcVar4 = (char *)FUN_0040f730(pcVar4,(char *)_Str,0x103);
        if ((pcVar4 == (char *)0x0) || ((char)*_Str == '\0')) goto LAB_0040e11b;
        sVar5 = _strlen((char *)_Str);
        pcVar1 = (char *)((sVar5 - 1) + (int)_Str);
        cVar2 = *pcVar1;
        if (cVar2 == '\\') {
          pcVar6 = (char *)FUN_0040f6d0((byte *)_Str,0x5c);
          bVar10 = pcVar1 == pcVar6;
        }
        else {
          bVar10 = cVar2 == '/';
        }
        if (!bVar10) {
          FUN_0040f5f0(_Str,(uint *)&DAT_0042f214);
        }
        sVar5 = _strlen((char *)_Str);
        sVar7 = _strlen((char *)param_2);
        if (0x103 < sVar5 + sVar7) goto LAB_0040e11b;
        FUN_0040f5f0(_Str,param_2);
        local_c = FUN_0040e12a(param_1,_Str,param_3,param_4);
        if (local_c != (char *)0xffffffff) goto LAB_0040e11b;
      } while (DAT_00452e48 == 2);
      puVar8 = (uint *)FUN_0040f7a4((byte *)_Str,0x5c);
      if ((_Str != puVar8) && (puVar8 = (uint *)FUN_0040f7a4((byte *)_Str,0x2f), _Str != puVar8))
      break;
      pbVar3 = (byte *)((int)_Str + 1);
      pbVar9 = FUN_0040f7a4(pbVar3,0x5c);
    } while ((pbVar3 == pbVar9) || (pbVar9 = FUN_0040f7a4(pbVar3,0x2f), pbVar3 == pbVar9));
LAB_0040e11b:
    FUN_0040aa97(_Str);
  }
  return local_c;
}



/* 0040e12a FUN_0040e12a */

char * __cdecl FUN_0040e12a(int param_1,uint *param_2,undefined4 *param_3,char *param_4)

{
  byte *pbVar1;
  byte *pbVar2;
  size_t sVar3;
  uint *puVar4;
  int iVar5;
  uint *puVar6;
  undefined **ppuVar7;
  char *local_c;
  
  pbVar1 = (byte *)FUN_0040f6d0((byte *)param_2,0x5c);
  pbVar2 = (byte *)FUN_0040f6d0((byte *)param_2,0x2f);
  puVar4 = param_2;
  if (pbVar2 == (byte *)0x0) {
    if ((pbVar1 != (byte *)0x0) ||
       (pbVar1 = FUN_0040f7a4((byte *)param_2,0x3a), pbVar1 != (byte *)0x0)) goto LAB_0040e19f;
    sVar3 = _strlen((char *)param_2);
    puVar4 = _malloc(sVar3 + 3);
    if (puVar4 != (uint *)0x0) {
      FUN_0040f5e0(puVar4,(uint *)&DAT_0042c380);
      FUN_0040f5f0(puVar4,param_2);
      pbVar1 = (byte *)((int)puVar4 + 2);
      goto LAB_0040e19f;
    }
LAB_0040e1f8:
    local_c = (char *)0xffffffff;
  }
  else {
    if ((pbVar1 == (byte *)0x0) || (pbVar1 < pbVar2)) {
      pbVar1 = pbVar2;
    }
LAB_0040e19f:
    local_c = (char *)0xffffffff;
    iVar5 = FUN_0040f6d0(pbVar1,0x2e);
    if (iVar5 == 0) {
      sVar3 = _strlen((char *)puVar4);
      puVar6 = _malloc(sVar3 + 5);
      if (puVar6 == (uint *)0x0) goto LAB_0040e1f8;
      FUN_0040f5e0(puVar6,puVar4);
      sVar3 = _strlen((char *)puVar4);
      ppuVar7 = &PTR_DAT_0043074c;
      do {
        FUN_0040f5e0((uint *)(sVar3 + (int)puVar6),(uint *)*ppuVar7);
        iVar5 = FUN_0040e2c4((LPCSTR)puVar6,0);
        if (iVar5 != -1) {
          local_c = FUN_0040e273(param_1,(LPCSTR)puVar6,param_3,param_4);
          break;
        }
        ppuVar7 = ppuVar7 + -1;
      } while (0x43073f < (int)ppuVar7);
      FUN_0040aa97(puVar6);
    }
    else {
      iVar5 = FUN_0040e2c4((LPCSTR)puVar4,0);
      if (iVar5 != -1) {
        local_c = FUN_0040e273(param_1,(LPCSTR)puVar4,param_3,param_4);
      }
    }
    if (puVar4 != param_2) {
      FUN_0040aa97(puVar4);
    }
  }
  return local_c;
}



/* 0040e273 FUN_0040e273 */

char * __cdecl FUN_0040e273(int param_1,LPCSTR param_2,undefined4 *param_3,char *param_4)

{
  int iVar1;
  char *pcVar2;
  
  iVar1 = FUN_0040f9f6(param_3,(undefined4 *)param_4,&param_4,&param_3);
  if (iVar1 == -1) {
    return (char *)0xffffffff;
  }
  pcVar2 = FUN_0040f817(param_1,param_2,param_4,param_3);
  FUN_0040aa97(param_4);
  FUN_0040aa97(param_3);
  return pcVar2;
}



/* 0040e2c4 FUN_0040e2c4 */

undefined4 __cdecl FUN_0040e2c4(LPCSTR param_1,byte param_2)

{
  DWORD DVar1;
  
  DVar1 = GetFileAttributesA(param_1);
  if (DVar1 == 0xffffffff) {
    DVar1 = GetLastError();
    FUN_0040f0e3(DVar1);
  }
  else {
    if (((DVar1 & 1) == 0) || ((param_2 & 2) == 0)) {
      return 0;
    }
    DAT_00452e48 = 0xd;
    DAT_00452e4c = 5;
  }
  return 0xffffffff;
}



/* 0040e308 FUN_0040e308 */

int __cdecl FUN_0040e308(uchar *param_1)

{
  int iVar1;
  size_t _MaxCount;
  size_t sVar2;
  int *piVar3;
  
  if (((DAT_00455648 != 0) &&
      ((DAT_00452e70 != (int *)0x0 ||
       (((DAT_00452e78 != 0 && (iVar1 = FUN_0040fc39(), iVar1 == 0)) && (DAT_00452e70 != (int *)0x0)
        ))))) && (piVar3 = DAT_00452e70, param_1 != (uchar *)0x0)) {
    _MaxCount = _strlen((char *)param_1);
    for (; (char *)*piVar3 != (char *)0x0; piVar3 = piVar3 + 1) {
      sVar2 = _strlen((char *)*piVar3);
      if (((_MaxCount < sVar2) && (((uchar *)*piVar3)[_MaxCount] == '=')) &&
         (iVar1 = __mbsnbicoll((uchar *)*piVar3,param_1,_MaxCount), iVar1 == 0)) {
        return *piVar3 + 1 + _MaxCount;
      }
    }
  }
  return 0;
}



/* 0040e385 FUN_0040e385 */

LONG __cdecl FUN_0040e385(int param_1,_EXCEPTION_POINTERS *param_2)

{
  code *pcVar1;
  undefined4 uVar2;
  undefined4 uVar3;
  int *piVar4;
  LONG LVar5;
  int iVar6;
  undefined4 *puVar7;
  
  piVar4 = FUN_0040e4c6(param_1);
  uVar3 = DAT_00452e94;
  if ((piVar4 == (int *)0x0) || (pcVar1 = (code *)piVar4[2], pcVar1 == (code *)0x0)) {
    LVar5 = UnhandledExceptionFilter(param_2);
  }
  else if (pcVar1 == (code *)0x5) {
    piVar4[2] = 0;
    LVar5 = 1;
  }
  else {
    if (pcVar1 != (code *)0x1) {
      DAT_00452e94 = param_2;
      if (piVar4[1] == 8) {
        if (DAT_004307c8 < DAT_004307cc + DAT_004307c8) {
          iVar6 = (DAT_004307cc + DAT_004307c8) - DAT_004307c8;
          puVar7 = (undefined4 *)(DAT_004307c8 * 0xc + 0x430758);
          do {
            *puVar7 = 0;
            puVar7 = puVar7 + 3;
            iVar6 = iVar6 + -1;
          } while (iVar6 != 0);
        }
        uVar2 = DAT_004307d4;
        iVar6 = *piVar4;
        if (iVar6 == -0x3fffff72) {
          DAT_004307d4 = 0x83;
        }
        else if (iVar6 == -0x3fffff70) {
          DAT_004307d4 = 0x81;
        }
        else if (iVar6 == -0x3fffff6f) {
          DAT_004307d4 = 0x84;
        }
        else if (iVar6 == -0x3fffff6d) {
          DAT_004307d4 = 0x85;
        }
        else if (iVar6 == -0x3fffff73) {
          DAT_004307d4 = 0x82;
        }
        else if (iVar6 == -0x3fffff71) {
          DAT_004307d4 = 0x86;
        }
        else if (iVar6 == -0x3fffff6e) {
          DAT_004307d4 = 0x8a;
        }
        (*pcVar1)(8,DAT_004307d4);
        DAT_004307d4 = uVar2;
      }
      else {
        piVar4[2] = 0;
        (*pcVar1)(piVar4[1]);
      }
    }
    LVar5 = -1;
    DAT_00452e94 = (_EXCEPTION_POINTERS *)uVar3;
  }
  return LVar5;
}



/* 0040e4c6 FUN_0040e4c6 */

int * __cdecl FUN_0040e4c6(int param_1)

{
  int *piVar1;
  
  piVar1 = &DAT_00430750;
  if (DAT_00430750 != param_1) {
    do {
      piVar1 = piVar1 + 3;
      if (&DAT_00430750 + DAT_004307d0 * 3 <= piVar1) break;
    } while (*piVar1 != param_1);
  }
  if ((&DAT_00430750 + DAT_004307d0 * 3 <= piVar1) || (*piVar1 != param_1)) {
    piVar1 = (int *)0x0;
  }
  return piVar1;
}



/* 0040e509 FUN_0040e509 */

void FUN_0040e509(void)

{
  char cVar1;
  size_t sVar2;
  undefined4 *puVar3;
  void *pvVar4;
  int iVar5;
  uint *puVar6;
  
  if (DAT_0045564c == 0) {
    FUN_0041006b();
  }
  iVar5 = 0;
  for (puVar6 = DAT_00452e2c; (char)*puVar6 != '\0'; puVar6 = (uint *)((int)puVar6 + sVar2 + 1)) {
    if ((char)*puVar6 != '=') {
      iVar5 = iVar5 + 1;
    }
    sVar2 = _strlen((char *)puVar6);
  }
  puVar3 = _malloc(iVar5 * 4 + 4);
  DAT_00452e70 = puVar3;
  if (puVar3 == (undefined4 *)0x0) {
    __amsg_exit(9);
  }
  cVar1 = (char)*DAT_00452e2c;
  puVar6 = DAT_00452e2c;
  while (cVar1 != '\0') {
    sVar2 = _strlen((char *)puVar6);
    if ((char)*puVar6 != '=') {
      pvVar4 = _malloc(sVar2 + 1);
      *puVar3 = pvVar4;
      if (pvVar4 == (void *)0x0) {
        __amsg_exit(9);
      }
      FUN_0040f5e0((uint *)*puVar3,puVar6);
      puVar3 = puVar3 + 1;
    }
    puVar6 = (uint *)((int)puVar6 + sVar2 + 1);
    cVar1 = (char)*puVar6;
  }
  FUN_0040aa97(DAT_00452e2c);
  DAT_00452e2c = (uint *)0x0;
  *puVar3 = 0;
  DAT_00455648 = 1;
  return;
}



/* 0040e5c2 FUN_0040e5c2 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void FUN_0040e5c2(void)

{
  undefined4 *puVar1;
  byte *pbVar2;
  int local_c;
  int local_8;
  
  if (DAT_0045564c == 0) {
    FUN_0041006b();
  }
  GetModuleFileNameA((HMODULE)0x0,&DAT_00452e98,0x104);
  _DAT_00452e80 = &DAT_00452e98;
  pbVar2 = &DAT_00452e98;
  if (*DAT_00455780 != 0) {
    pbVar2 = DAT_00455780;
  }
  FUN_0040e65b(pbVar2,(undefined4 *)0x0,(byte *)0x0,&local_8,&local_c);
  puVar1 = _malloc(local_c + local_8 * 4);
  if (puVar1 == (undefined4 *)0x0) {
    __amsg_exit(8);
  }
  FUN_0040e65b(pbVar2,puVar1,(byte *)(puVar1 + local_8),&local_8,&local_c);
  DAT_00452e68 = puVar1;
  DAT_00452e64 = local_8 + -1;
  return;
}



/* 0040e65b FUN_0040e65b */

void __cdecl FUN_0040e65b(byte *param_1,undefined4 *param_2,byte *param_3,int *param_4,int *param_5)

{
  byte bVar1;
  bool bVar2;
  bool bVar3;
  byte *pbVar4;
  byte *pbVar5;
  uint uVar6;
  undefined4 *puVar7;
  
  *param_5 = 0;
  *param_4 = 1;
  if (param_2 != (undefined4 *)0x0) {
    *param_2 = param_3;
    param_2 = param_2 + 1;
  }
  if (*param_1 == 0x22) {
    while( true ) {
      bVar1 = param_1[1];
      pbVar4 = param_1 + 1;
      if ((bVar1 == 0x22) || (bVar1 == 0)) break;
      if (((*(byte *)((int)&DAT_00455540 + bVar1 + 1) & 4) != 0) &&
         (*param_5 = *param_5 + 1, param_3 != (byte *)0x0)) {
        *param_3 = *pbVar4;
        param_3 = param_3 + 1;
        pbVar4 = param_1 + 2;
      }
      *param_5 = *param_5 + 1;
      param_1 = pbVar4;
      if (param_3 != (byte *)0x0) {
        *param_3 = *pbVar4;
        param_3 = param_3 + 1;
      }
    }
    *param_5 = *param_5 + 1;
    if (param_3 != (byte *)0x0) {
      *param_3 = 0;
      param_3 = param_3 + 1;
    }
    if (*pbVar4 == 0x22) {
      pbVar4 = param_1 + 2;
    }
  }
  else {
    do {
      *param_5 = *param_5 + 1;
      if (param_3 != (byte *)0x0) {
        *param_3 = *param_1;
        param_3 = param_3 + 1;
      }
      bVar1 = *param_1;
      pbVar4 = param_1 + 1;
      if ((*(byte *)((int)&DAT_00455540 + bVar1 + 1) & 4) != 0) {
        *param_5 = *param_5 + 1;
        if (param_3 != (byte *)0x0) {
          *param_3 = *pbVar4;
          param_3 = param_3 + 1;
        }
        pbVar4 = param_1 + 2;
      }
      if (bVar1 == 0x20) break;
      if (bVar1 == 0) goto LAB_0040e706;
      param_1 = pbVar4;
    } while (bVar1 != 9);
    if (bVar1 == 0) {
LAB_0040e706:
      pbVar4 = pbVar4 + -1;
    }
    else if (param_3 != (byte *)0x0) {
      param_3[-1] = 0;
    }
  }
  bVar2 = false;
  puVar7 = param_2;
  while (*pbVar4 != 0) {
    for (; (*pbVar4 == 0x20 || (*pbVar4 == 9)); pbVar4 = pbVar4 + 1) {
    }
    if (*pbVar4 == 0) break;
    if (puVar7 != (undefined4 *)0x0) {
      *puVar7 = param_3;
      puVar7 = puVar7 + 1;
      param_2 = puVar7;
    }
    *param_4 = *param_4 + 1;
    while( true ) {
      bVar3 = true;
      uVar6 = 0;
      for (; *pbVar4 == 0x5c; pbVar4 = pbVar4 + 1) {
        uVar6 = uVar6 + 1;
      }
      if (*pbVar4 == 0x22) {
        pbVar5 = pbVar4;
        if ((uVar6 & 1) == 0) {
          if ((!bVar2) || (pbVar5 = pbVar4 + 1, pbVar4[1] != 0x22)) {
            bVar3 = false;
            pbVar5 = pbVar4;
          }
          bVar2 = !bVar2;
          puVar7 = param_2;
        }
        uVar6 = uVar6 >> 1;
        pbVar4 = pbVar5;
      }
      for (; uVar6 != 0; uVar6 = uVar6 - 1) {
        if (param_3 != (byte *)0x0) {
          *param_3 = 0x5c;
          param_3 = param_3 + 1;
        }
        *param_5 = *param_5 + 1;
      }
      bVar1 = *pbVar4;
      if ((bVar1 == 0) || ((!bVar2 && ((bVar1 == 0x20 || (bVar1 == 9)))))) break;
      if (bVar3) {
        if (param_3 == (byte *)0x0) {
          if ((*(byte *)((int)&DAT_00455540 + bVar1 + 1) & 4) != 0) {
            pbVar4 = pbVar4 + 1;
            *param_5 = *param_5 + 1;
          }
        }
        else {
          if ((*(byte *)((int)&DAT_00455540 + bVar1 + 1) & 4) != 0) {
            *param_3 = bVar1;
            param_3 = param_3 + 1;
            pbVar4 = pbVar4 + 1;
            *param_5 = *param_5 + 1;
          }
          *param_3 = *pbVar4;
          param_3 = param_3 + 1;
        }
        *param_5 = *param_5 + 1;
      }
      pbVar4 = pbVar4 + 1;
    }
    if (param_3 != (byte *)0x0) {
      *param_3 = 0;
      param_3 = param_3 + 1;
    }
    *param_5 = *param_5 + 1;
  }
  if (puVar7 != (undefined4 *)0x0) {
    *puVar7 = 0;
  }
  *param_4 = *param_4 + 1;
  return;
}



/* 0040e80f FUN_0040e80f */

LPSTR FUN_0040e80f(void)

{
  char cVar1;
  WCHAR WVar2;
  WCHAR *pWVar3;
  WCHAR *pWVar4;
  int iVar5;
  size_t _Size;
  LPSTR pCVar6;
  char *pcVar7;
  LPWCH lpWideCharStr;
  LPCH pCVar9;
  LPSTR local_8;
  char *pcVar8;
  
  lpWideCharStr = (LPWCH)0x0;
  pCVar9 = (LPCH)0x0;
  if (DAT_00452f9c == 0) {
    lpWideCharStr = GetEnvironmentStringsW();
    if (lpWideCharStr != (LPWCH)0x0) {
      DAT_00452f9c = 1;
LAB_0040e866:
      if ((lpWideCharStr == (LPWCH)0x0) &&
         (lpWideCharStr = GetEnvironmentStringsW(), lpWideCharStr == (LPWCH)0x0)) {
        return (LPSTR)0x0;
      }
      WVar2 = *lpWideCharStr;
      pWVar4 = lpWideCharStr;
      while (WVar2 != L'\0') {
        do {
          pWVar3 = pWVar4;
          pWVar4 = pWVar3 + 1;
        } while (*pWVar4 != L'\0');
        pWVar4 = pWVar3 + 2;
        WVar2 = *pWVar4;
      }
      iVar5 = ((int)pWVar4 - (int)lpWideCharStr >> 1) + 1;
      _Size = WideCharToMultiByte(0,0,lpWideCharStr,iVar5,(LPSTR)0x0,0,(LPCSTR)0x0,(LPBOOL)0x0);
      local_8 = (LPSTR)0x0;
      if (((_Size != 0) && (pCVar6 = _malloc(_Size), pCVar6 != (LPSTR)0x0)) &&
         (iVar5 = WideCharToMultiByte(0,0,lpWideCharStr,iVar5,pCVar6,_Size,(LPCSTR)0x0,(LPBOOL)0x0),
         local_8 = pCVar6, iVar5 == 0)) {
        FUN_0040aa97(pCVar6);
        local_8 = (LPSTR)0x0;
      }
      FreeEnvironmentStringsW(lpWideCharStr);
      return local_8;
    }
    pCVar9 = GetEnvironmentStrings();
    if (pCVar9 == (LPCH)0x0) {
      return (LPSTR)0x0;
    }
    DAT_00452f9c = 2;
  }
  else {
    if (DAT_00452f9c == 1) goto LAB_0040e866;
    if (DAT_00452f9c != 2) {
      return (LPSTR)0x0;
    }
  }
  if ((pCVar9 == (LPCH)0x0) && (pCVar9 = GetEnvironmentStrings(), pCVar9 == (LPCH)0x0)) {
    return (LPSTR)0x0;
  }
  cVar1 = *pCVar9;
  pcVar7 = pCVar9;
  while (cVar1 != '\0') {
    do {
      pcVar8 = pcVar7;
      pcVar7 = pcVar8 + 1;
    } while (*pcVar7 != '\0');
    pcVar7 = pcVar8 + 2;
    cVar1 = *pcVar7;
  }
  pCVar6 = _malloc((size_t)(pcVar7 + (1 - (int)pCVar9)));
  if (pCVar6 == (LPSTR)0x0) {
    pCVar6 = (LPSTR)0x0;
  }
  else {
    FUN_0040d670((undefined4 *)pCVar6,(undefined4 *)pCVar9,(uint)(pcVar7 + (1 - (int)pCVar9)));
  }
  FreeEnvironmentStringsA(pCVar9);
  return pCVar6;
}



/* 0040e944 __global_unwind2 */

/* Library Function - Single Match
    __global_unwind2
   
   Library: Visual Studio */

void __cdecl __global_unwind2(PVOID param_1)

{
  RtlUnwind(param_1,(PVOID)0x40e95c,(PEXCEPTION_RECORD)0x0,(PVOID)0x0);
  return;
}



/* 0040e986 __local_unwind2 */

/* Library Function - Single Match
    __local_unwind2
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release, Visual Studio 2003 Debug, Visual
   Studio 2003 Release */

void __cdecl __local_unwind2(int param_1,int param_2)

{
  int iVar1;
  int iVar2;
  void *pvStack_1c;
  undefined1 *puStack_18;
  undefined4 local_14;
  int iStack_10;
  
  iStack_10 = param_1;
  puStack_18 = &LAB_0040e964;
  pvStack_1c = ExceptionList;
  ExceptionList = &pvStack_1c;
  while( true ) {
    iVar1 = *(int *)(param_1 + 8);
    iVar2 = *(int *)(param_1 + 0xc);
    if ((iVar2 == -1) || (iVar2 == param_2)) break;
    local_14 = *(undefined4 *)(iVar1 + iVar2 * 0xc);
    *(undefined4 *)(param_1 + 0xc) = local_14;
    if (*(int *)(iVar1 + 4 + iVar2 * 0xc) == 0) {
      FUN_0040ea1a();
      (**(code **)(iVar1 + 8 + iVar2 * 0xc))();
    }
  }
  ExceptionList = pvStack_1c;
  return;
}



/* 0040ea1a FUN_0040ea1a */

void FUN_0040ea1a(void)

{
  undefined4 in_EAX;
  int unaff_EBP;
  
  DAT_004307e0 = *(undefined4 *)(unaff_EBP + 8);
  DAT_004307dc = in_EAX;
  DAT_004307e4 = unaff_EBP;
  return;
}



/* 0040eaf9 FUN_0040eaf9 */

void FUN_0040eaf9(int param_1)

{
  __local_unwind2(*(int *)(param_1 + 0x18),*(int *)(param_1 + 0x1c));
  return;
}



/* 0040eb14 FUN_0040eb14 */

void FUN_0040eb14(void)

{
  if ((DAT_00452e34 == 1) || ((DAT_00452e34 == 0 && (DAT_00430504 == 1)))) {
    FUN_0040eb4d(0xfc);
    if (DAT_00452fa0 != (code *)0x0) {
      (*DAT_00452fa0)();
    }
    FUN_0040eb4d(0xff);
  }
  return;
}



/* 0040eb4d FUN_0040eb4d */

void __cdecl FUN_0040eb4d(DWORD param_1)

{
  undefined4 *puVar1;
  DWORD *pDVar2;
  DWORD DVar3;
  size_t sVar4;
  HANDLE hFile;
  int iVar5;
  uint *_Dest;
  undefined1 auStackY_1e3 [7];
  LPCVOID lpBuffer;
  LPOVERLAPPED lpOverlapped;
  uint local_1a8 [65];
  uint local_a4 [40];
  
  iVar5 = 0;
  pDVar2 = &DAT_004307e8;
  do {
    if (param_1 == *pDVar2) break;
    pDVar2 = pDVar2 + 2;
    iVar5 = iVar5 + 1;
  } while ((int)pDVar2 < 0x430878);
  if (param_1 == (&DAT_004307e8)[iVar5 * 2]) {
    if ((DAT_00452e34 == 1) || ((DAT_00452e34 == 0 && (DAT_00430504 == 1)))) {
      pDVar2 = &param_1;
      puVar1 = (undefined4 *)(iVar5 * 8 + 0x4307ec);
      lpOverlapped = (LPOVERLAPPED)0x0;
      sVar4 = _strlen((char *)*puVar1);
      lpBuffer = (LPCVOID)*puVar1;
      hFile = GetStdHandle(0xfffffff4);
      WriteFile(hFile,lpBuffer,sVar4,pDVar2,lpOverlapped);
    }
    else if (param_1 != 0xfc) {
      DVar3 = GetModuleFileNameA((HMODULE)0x0,(LPSTR)local_1a8,0x104);
      if (DVar3 == 0) {
        FUN_0040f5e0(local_1a8,(uint *)"<program name unknown>");
      }
      _Dest = local_1a8;
      sVar4 = _strlen((char *)local_1a8);
      if (0x3c < sVar4 + 1) {
        sVar4 = _strlen((char *)local_1a8);
        _Dest = (uint *)(auStackY_1e3 + sVar4);
        _strncpy((char *)_Dest,"...",3);
      }
      FUN_0040f5e0(local_a4,(uint *)"Runtime Error!\n\nProgram: ");
      FUN_0040f5f0(local_a4,_Dest);
      FUN_0040f5f0(local_a4,(uint *)&DAT_0042c638);
      FUN_0040f5f0(local_a4,*(uint **)(iVar5 * 8 + 0x4307ec));
      auStackY_1e3._3_4_ = 0x40ec71;
      FUN_00410087(local_a4,"Microsoft Visual C++ Runtime Library",0x12010);
    }
  }
  return;
}



/* 0040eca0 FUN_0040eca0 */

BOOL __cdecl
FUN_0040eca0(DWORD param_1,LPCSTR param_2,int param_3,LPWORD param_4,UINT param_5,LCID param_6,
            int param_7)

{
  undefined1 *puVar1;
  BOOL BVar2;
  int iVar3;
  WORD local_20 [2];
  undefined1 *local_1c;
  void *local_14;
  undefined1 *puStack_10;
  undefined *puStack_c;
  undefined4 local_8;
  
  local_8 = 0xffffffff;
  puStack_c = &DAT_0042c680;
  puStack_10 = &LAB_0040ea3c;
  local_14 = ExceptionList;
  local_1c = &stack0xffffffc8;
  iVar3 = DAT_00452fa4;
  ExceptionList = &local_14;
  puVar1 = &stack0xffffffc8;
  if (DAT_00452fa4 == 0) {
    ExceptionList = &local_14;
    BVar2 = GetStringTypeW(1,L"",1,local_20);
    iVar3 = 1;
    puVar1 = local_1c;
    if (BVar2 == 0) {
      BVar2 = GetStringTypeA(0,1,"",1,local_20);
      if (BVar2 == 0) {
        ExceptionList = local_14;
        return 0;
      }
      iVar3 = 2;
      puVar1 = local_1c;
    }
  }
  local_1c = puVar1;
  DAT_00452fa4 = iVar3;
  if (DAT_00452fa4 != 2) {
    if (DAT_00452fa4 == 1) {
      if (param_5 == 0) {
        param_5 = DAT_00452fd4;
      }
      iVar3 = MultiByteToWideChar(param_5,(-(uint)(param_7 != 0) & 8) + 1,param_2,param_3,
                                  (LPWSTR)0x0,0);
      if (iVar3 != 0) {
        local_8 = 0;
        FUN_0040afb0();
        local_1c = &stack0xffffffc8;
        _memset(&stack0xffffffc8,0,iVar3 * 2);
        local_8 = 0xffffffff;
        if ((&stack0x00000000 != (undefined1 *)0x38) &&
           (iVar3 = MultiByteToWideChar(param_5,1,param_2,param_3,(LPWSTR)&stack0xffffffc8,iVar3),
           iVar3 != 0)) {
          BVar2 = GetStringTypeW(param_1,(LPCWSTR)&stack0xffffffc8,iVar3,param_4);
          ExceptionList = local_14;
          return BVar2;
        }
      }
    }
    ExceptionList = local_14;
    return 0;
  }
  if (param_6 == 0) {
    param_6 = DAT_00452fc4;
  }
  BVar2 = GetStringTypeA(param_6,param_1,param_2,param_3,param_4);
  ExceptionList = local_14;
  return BVar2;
}



/* 0040ede9 FUN_0040ede9 */

byte __cdecl FUN_0040ede9(uint param_1)

{
  if (DAT_00455760 <= param_1) {
    return 0;
  }
  return *(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + (param_1 & 0x1f) * 8) & 0x40;
}



/* 0040ee10 _strlen */

/* Library Function - Single Match
    _strlen
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release */

size_t __cdecl _strlen(char *_Str)

{
  uint uVar1;
  uint *puVar2;
  uint *puVar3;
  
  puVar2 = (uint *)_Str;
  do {
    if (((uint)puVar2 & 3) == 0) goto LAB_0040ee30;
    uVar1 = *puVar2;
    puVar2 = (uint *)((int)puVar2 + 1);
  } while ((char)uVar1 != '\0');
LAB_0040ee63:
  return (size_t)((int)puVar2 + (-1 - (int)_Str));
LAB_0040ee30:
  do {
    do {
      puVar3 = puVar2;
      puVar2 = puVar3 + 1;
    } while (((*puVar3 ^ 0xffffffff ^ *puVar3 + 0x7efefeff) & 0x81010100) == 0);
    uVar1 = *puVar3;
    if ((char)uVar1 == '\0') {
      return (int)puVar3 - (int)_Str;
    }
    if ((char)(uVar1 >> 8) == '\0') {
      return (size_t)((int)puVar3 + (1 - (int)_Str));
    }
    if ((uVar1 & 0xff0000) == 0) {
      return (size_t)((int)puVar3 + (2 - (int)_Str));
    }
  } while ((uVar1 & 0xff000000) != 0);
  goto LAB_0040ee63;
}



/* 0040ee8b FUN_0040ee8b */

int __cdecl FUN_0040ee8b(LPSTR param_1,WCHAR param_2)

{
  LPSTR lpMultiByteStr;
  int iVar1;
  
  lpMultiByteStr = param_1;
  if (param_1 == (LPSTR)0x0) {
    return 0;
  }
  if (DAT_00452fc4 == 0) {
    if ((ushort)param_2 < 0x100) {
      *param_1 = (CHAR)param_2;
      return 1;
    }
  }
  else {
    param_1 = (LPSTR)0x0;
    iVar1 = WideCharToMultiByte(DAT_00452fd4,0x220,&param_2,1,lpMultiByteStr,DAT_00430714,
                                (LPCSTR)0x0,(LPBOOL)&param_1);
    if ((iVar1 != 0) && (param_1 == (LPSTR)0x0)) {
      return iVar1;
    }
  }
  DAT_00452e48 = 0x2a;
  return -1;
}



/* 0040ef00 __aulldiv */

/* Library Function - Single Match
    __aulldiv
   
   Library: Visual Studio */

undefined8 __aulldiv(uint param_1,uint param_2,uint param_3,uint param_4)

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



/* 0040ef70 __aullrem */

/* Library Function - Single Match
    __aullrem
   
   Library: Visual Studio */

undefined8 __aullrem(uint param_1,uint param_2,uint param_3,uint param_4)

{
  ulonglong uVar1;
  longlong lVar2;
  uint uVar3;
  uint uVar4;
  uint uVar5;
  int iVar6;
  int iVar7;
  uint uVar8;
  uint uVar9;
  uint uVar10;
  bool bVar11;
  
  uVar4 = param_1;
  uVar9 = param_4;
  uVar10 = param_2;
  uVar3 = param_3;
  if (param_4 == 0) {
    iVar6 = (int)(((ulonglong)param_2 % (ulonglong)param_3 << 0x20 | (ulonglong)param_1) %
                 (ulonglong)param_3);
    iVar7 = 0;
  }
  else {
    do {
      uVar5 = uVar9 >> 1;
      uVar3 = (uint)(CONCAT14((uVar9 & 1) != 0,uVar3) >> 1);
      uVar8 = uVar10 >> 1;
      uVar4 = (uint)(CONCAT14((uVar10 & 1) != 0,uVar4) >> 1);
      uVar9 = uVar5;
      uVar10 = uVar8;
    } while (uVar5 != 0);
    uVar1 = CONCAT44(uVar8,uVar4) / (ulonglong)uVar3;
    uVar3 = (int)uVar1 * param_4;
    lVar2 = (uVar1 & 0xffffffff) * (ulonglong)param_3;
    uVar9 = (uint)((ulonglong)lVar2 >> 0x20);
    uVar4 = (uint)lVar2;
    uVar10 = uVar9 + uVar3;
    if (((CARRY4(uVar9,uVar3)) || (param_2 < uVar10)) || ((param_2 <= uVar10 && (param_1 < uVar4))))
    {
      bVar11 = uVar4 < param_3;
      uVar4 = uVar4 - param_3;
      uVar10 = (uVar10 - param_4) - (uint)bVar11;
    }
    iVar6 = -(uVar4 - param_1);
    iVar7 = -(uint)(uVar4 - param_1 != 0) - ((uVar10 - param_2) - (uint)(uVar4 < param_1));
  }
  return CONCAT44(iVar7,iVar6);
}



/* 0040eff0 _memset */

/* Library Function - Single Match
    _memset
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release */

void * __cdecl _memset(void *_Dst,int _Val,size_t _Size)

{
  uint uVar1;
  uint uVar2;
  size_t sVar3;
  uint *puVar4;
  
  if (_Size == 0) {
    return _Dst;
  }
  uVar1 = _Val & 0xff;
  puVar4 = _Dst;
  if (3 < _Size) {
    uVar2 = -(int)_Dst & 3;
    sVar3 = _Size;
    if (uVar2 != 0) {
      sVar3 = _Size - uVar2;
      do {
        *(undefined1 *)puVar4 = (undefined1)_Val;
        puVar4 = (uint *)((int)puVar4 + 1);
        uVar2 = uVar2 - 1;
      } while (uVar2 != 0);
    }
    uVar1 = uVar1 * 0x1010101;
    _Size = sVar3 & 3;
    uVar2 = sVar3 >> 2;
    if (uVar2 != 0) {
      for (; uVar2 != 0; uVar2 = uVar2 - 1) {
        *puVar4 = uVar1;
        puVar4 = puVar4 + 1;
      }
      if (_Size == 0) {
        return _Dst;
      }
    }
  }
  do {
    *(char *)puVar4 = (char)uVar1;
    puVar4 = (uint *)((int)puVar4 + 1);
    _Size = _Size - 1;
  } while (_Size != 0);
  return _Dst;
}



/* 0040f048 FUN_0040f048 */

undefined4 __cdecl FUN_0040f048(uint param_1)

{
  HANDLE hFile;
  BOOL BVar1;
  DWORD DVar2;
  
  DVar2 = DAT_00452e4c;
  if ((param_1 < DAT_00455760) &&
     ((*(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + (param_1 & 0x1f) * 8) & 1) != 0)) {
    hFile = (HANDLE)FUN_0040f2d0(param_1);
    BVar1 = FlushFileBuffers(hFile);
    if (BVar1 == 0) {
      DVar2 = GetLastError();
    }
    else {
      DVar2 = 0;
    }
    if (DVar2 == 0) {
      return 0;
    }
  }
  DAT_00452e4c = DVar2;
  DAT_00452e48 = 9;
  return 0xffffffff;
}



/* 0040f09f FUN_0040f09f */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void __cdecl FUN_0040f09f(undefined4 *param_1)

{
  void *pvVar1;
  
  _DAT_00452e28 = _DAT_00452e28 + 1;
  pvVar1 = _malloc(0x1000);
  param_1[2] = pvVar1;
  if (pvVar1 == (void *)0x0) {
    param_1[3] = param_1[3] | 4;
    param_1[2] = param_1 + 5;
    param_1[6] = 2;
  }
  else {
    param_1[3] = param_1[3] | 8;
    param_1[6] = 0x1000;
  }
  param_1[1] = 0;
  *param_1 = param_1[2];
  return;
}



/* 0040f0e3 FUN_0040f0e3 */

void __cdecl FUN_0040f0e3(uint param_1)

{
  uint *puVar1;
  int iVar2;
  
  iVar2 = 0;
  DAT_00452e4c = param_1;
  puVar1 = &DAT_004308a0;
  do {
    if (param_1 == *puVar1) {
      DAT_00452e48 = *(undefined4 *)(iVar2 * 8 + 0x4308a4);
      return;
    }
    puVar1 = puVar1 + 2;
    iVar2 = iVar2 + 1;
  } while ((int)puVar1 < 0x430a08);
  if ((0x12 < param_1) && (param_1 < 0x25)) {
    DAT_00452e48 = 0xd;
    return;
  }
  if ((param_1 < 0xbc) || (DAT_00452e48 = 8, 0xca < param_1)) {
    DAT_00452e48 = 0x16;
  }
  return;
}



/* 0040f14a FUN_0040f14a */

int FUN_0040f14a(void)

{
  undefined4 *puVar1;
  undefined4 *puVar2;
  int *piVar3;
  int iVar4;
  int iVar5;
  int iVar6;
  
  iVar4 = -1;
  iVar6 = 0;
  iVar5 = 0;
  piVar3 = &DAT_00455660;
  do {
    puVar2 = (undefined4 *)*piVar3;
    if (puVar2 == (undefined4 *)0x0) {
      puVar2 = _malloc(0x100);
      if (puVar2 != (undefined4 *)0x0) {
        DAT_00455760 = DAT_00455760 + 0x20;
        (&DAT_00455660)[iVar6] = puVar2;
        puVar1 = puVar2;
        for (; puVar2 < puVar1 + 0x40; puVar2 = puVar2 + 2) {
          *(undefined1 *)(puVar2 + 1) = 0;
          *puVar2 = 0xffffffff;
          *(undefined1 *)((int)puVar2 + 5) = 10;
          puVar1 = (undefined4 *)(&DAT_00455660)[iVar6];
        }
        iVar4 = iVar6 << 5;
      }
      return iVar4;
    }
    puVar1 = puVar2 + 0x40;
    for (; puVar2 < puVar1; puVar2 = puVar2 + 2) {
      if ((*(byte *)(puVar2 + 1) & 1) == 0) {
        *puVar2 = 0xffffffff;
        iVar4 = ((int)puVar2 - *piVar3 >> 3) + iVar5;
        if (iVar4 != -1) {
          return iVar4;
        }
        break;
      }
    }
    piVar3 = piVar3 + 1;
    iVar6 = iVar6 + 1;
    iVar5 = iVar5 + 0x20;
    if (0x45575f < (int)piVar3) {
      return iVar4;
    }
  } while( true );
}



/* 0040f1df FUN_0040f1df */

undefined4 __cdecl FUN_0040f1df(uint param_1,HANDLE param_2)

{
  int iVar1;
  DWORD nStdHandle;
  
  if (param_1 < DAT_00455760) {
    iVar1 = (param_1 & 0x1f) * 8;
    if (*(int *)((&DAT_00455660)[(int)param_1 >> 5] + iVar1) == -1) {
      if (DAT_00430504 == 1) {
        if (param_1 == 0) {
          nStdHandle = 0xfffffff6;
        }
        else if (param_1 == 1) {
          nStdHandle = 0xfffffff5;
        }
        else {
          if (param_1 != 2) goto LAB_0040f235;
          nStdHandle = 0xfffffff4;
        }
        SetStdHandle(nStdHandle,param_2);
      }
LAB_0040f235:
      *(HANDLE *)((&DAT_00455660)[(int)param_1 >> 5] + iVar1) = param_2;
      return 0;
    }
  }
  DAT_00452e4c = 0;
  DAT_00452e48 = 9;
  return 0xffffffff;
}



/* 0040f256 FUN_0040f256 */

undefined4 __cdecl FUN_0040f256(uint param_1)

{
  int *piVar1;
  int iVar2;
  DWORD nStdHandle;
  
  if (param_1 < DAT_00455760) {
    iVar2 = (param_1 & 0x1f) * 8;
    piVar1 = (int *)((&DAT_00455660)[(int)param_1 >> 5] + iVar2);
    if (((*(byte *)(piVar1 + 1) & 1) != 0) && (*piVar1 != -1)) {
      if (DAT_00430504 == 1) {
        if (param_1 == 0) {
          nStdHandle = 0xfffffff6;
        }
        else if (param_1 == 1) {
          nStdHandle = 0xfffffff5;
        }
        else {
          if (param_1 != 2) goto LAB_0040f2af;
          nStdHandle = 0xfffffff4;
        }
        SetStdHandle(nStdHandle,(HANDLE)0x0);
      }
LAB_0040f2af:
      *(undefined4 *)((&DAT_00455660)[(int)param_1 >> 5] + iVar2) = 0xffffffff;
      return 0;
    }
  }
  DAT_00452e4c = 0;
  DAT_00452e48 = 9;
  return 0xffffffff;
}



/* 0040f2d0 FUN_0040f2d0 */

undefined4 __cdecl FUN_0040f2d0(uint param_1)

{
  if ((param_1 < DAT_00455760) &&
     ((*(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + (param_1 & 0x1f) * 8) & 1) != 0)) {
    return *(undefined4 *)((&DAT_00455660)[(int)param_1 >> 5] + (param_1 & 0x1f) * 8);
  }
  DAT_00452e4c = 0;
  DAT_00452e48 = 9;
  return 0xffffffff;
}



/* 0040f324 FUN_0040f324 */

uint __cdecl FUN_0040f324(LPCSTR param_1,uint param_2,uint param_3,uint param_4)

{
  byte *pbVar1;
  uint uVar2;
  uint uVar3;
  HANDLE hFile;
  DWORD DVar4;
  int iVar5;
  int iVar6;
  bool bVar7;
  _SECURITY_ATTRIBUTES local_20;
  DWORD local_14;
  DWORD local_10;
  DWORD local_c;
  byte local_5;
  
  bVar7 = (param_2 & 0x80) == 0;
  local_20.nLength = 0xc;
  local_20.lpSecurityDescriptor = (LPVOID)0x0;
  if (bVar7) {
    local_5 = 0;
  }
  else {
    local_5 = 0x10;
  }
  local_20.bInheritHandle = (BOOL)bVar7;
  if (((param_2 & 0x8000) == 0) && (((param_2 & 0x4000) != 0 || (DAT_00452fdc != 0x8000)))) {
    local_5 = local_5 | 0x80;
  }
  uVar2 = param_2 & 3;
  if (uVar2 == 0) {
    local_10 = 0x80000000;
  }
  else if (uVar2 == 1) {
    local_10 = 0x40000000;
  }
  else {
    if (uVar2 != 2) {
      DAT_00452e48 = 0x16;
      DAT_00452e4c = 0;
      return 0xffffffff;
    }
    local_10 = 0xc0000000;
  }
  if (param_3 == 0x10) {
    local_14 = 0;
  }
  else if (param_3 == 0x20) {
    local_14 = 1;
  }
  else if (param_3 == 0x30) {
    local_14 = 2;
  }
  else {
    if (param_3 != 0x40) {
      DAT_00452e48 = 0x16;
      DAT_00452e4c = 0;
      return 0xffffffff;
    }
    local_14 = 3;
  }
  uVar2 = param_2 & 0x700;
  if (uVar2 < 0x401) {
    if ((uVar2 == 0x400) || (uVar2 == 0)) {
      local_c = 3;
    }
    else if (uVar2 == 0x100) {
      local_c = 4;
    }
    else {
      if (uVar2 == 0x200) goto LAB_0040f442;
      if (uVar2 != 0x300) {
        DAT_00452e48 = 0x16;
        DAT_00452e4c = 0;
        return 0xffffffff;
      }
      local_c = 2;
    }
  }
  else {
    if (uVar2 != 0x500) {
      if (uVar2 == 0x600) {
LAB_0040f442:
        local_c = 5;
        goto LAB_0040f452;
      }
      if (uVar2 != 0x700) {
        DAT_00452e48 = 0x16;
        DAT_00452e4c = 0;
        return 0xffffffff;
      }
    }
    local_c = 1;
  }
LAB_0040f452:
  uVar2 = 0x80;
  if (((param_2 & 0x100) != 0) && ((~DAT_00452e50 & param_4 & 0x80) == 0)) {
    uVar2 = 1;
  }
  if ((param_2 & 0x40) != 0) {
    uVar2 = uVar2 | 0x4000000;
    local_10 = CONCAT13(local_10._3_1_,0x10000);
  }
  if ((param_2 & 0x1000) != 0) {
    uVar2 = uVar2 | 0x100;
  }
  if ((param_2 & 0x20) == 0) {
    if ((param_2 & 0x10) != 0) {
      uVar2 = uVar2 | 0x10000000;
    }
  }
  else {
    uVar2 = uVar2 | 0x8000000;
  }
  uVar3 = FUN_0040f14a();
  if (uVar3 == 0xffffffff) {
    DAT_00452e4c = 0;
    DAT_00452e48 = 0x18;
  }
  else {
    hFile = CreateFileA(param_1,local_10,local_14,&local_20,local_c,uVar2,(HANDLE)0x0);
    if (hFile != (HANDLE)0xffffffff) {
      DVar4 = GetFileType(hFile);
      if (DVar4 != 0) {
        if (DVar4 == 2) {
          local_5 = local_5 | 0x40;
        }
        else if (DVar4 == 3) {
          local_5 = local_5 | 8;
        }
        FUN_0040f1df(uVar3,hFile);
        iVar6 = (uVar3 & 0x1f) * 8;
        param_1._3_1_ = local_5 & 0x48;
        *(byte *)((&DAT_00455660)[(int)uVar3 >> 5] + 4 + iVar6) = local_5 | 1;
        if ((((local_5 & 0x48) == 0) && ((local_5 & 0x80) != 0)) && ((param_2 & 2) != 0)) {
          local_14 = FUN_0040df2d(uVar3,-1,2);
          if (local_14 == 0xffffffff) {
            if (DAT_00452e4c != 0x83) {
LAB_0040f5b3:
              FUN_0040dc67(uVar3);
              return 0xffffffff;
            }
          }
          else {
            param_3 = param_3 & 0xffffff;
            iVar5 = FUN_0040d476(uVar3,(char *)((int)&param_3 + 3),(char *)0x1);
            if ((((iVar5 == 0) && (param_3._3_1_ == '\x1a')) &&
                (iVar5 = FUN_00410119(uVar3,local_14), iVar5 == -1)) ||
               (DVar4 = FUN_0040df2d(uVar3,0,0), DVar4 == 0xffffffff)) goto LAB_0040f5b3;
          }
        }
        if (param_1._3_1_ != 0) {
          return uVar3;
        }
        if ((param_2 & 8) != 0) {
          pbVar1 = (byte *)((&DAT_00455660)[(int)uVar3 >> 5] + 4 + iVar6);
          *pbVar1 = *pbVar1 | 0x20;
          return uVar3;
        }
        return uVar3;
      }
      CloseHandle(hFile);
    }
    DVar4 = GetLastError();
    FUN_0040f0e3(DVar4);
  }
  return 0xffffffff;
}



/* 0040f5e0 FUN_0040f5e0 */

uint * __cdecl FUN_0040f5e0(uint *param_1,uint *param_2)

{
  byte bVar1;
  uint uVar2;
  uint uVar3;
  uint *puVar4;
  
  puVar4 = param_1;
  while (((uint)param_2 & 3) != 0) {
    bVar1 = (byte)*param_2;
    uVar3 = (uint)bVar1;
    param_2 = (uint *)((int)param_2 + 1);
    if (bVar1 == 0) goto LAB_0040f6c8;
    *(byte *)puVar4 = bVar1;
    puVar4 = (uint *)((int)puVar4 + 1);
  }
  do {
    uVar2 = *param_2;
    uVar3 = *param_2;
    param_2 = param_2 + 1;
    if (((uVar2 ^ 0xffffffff ^ uVar2 + 0x7efefeff) & 0x81010100) != 0) {
      if ((char)uVar3 == '\0') {
LAB_0040f6c8:
        *(byte *)puVar4 = (byte)uVar3;
        return param_1;
      }
      if ((char)(uVar3 >> 8) == '\0') {
        *(short *)puVar4 = (short)uVar3;
        return param_1;
      }
      if ((uVar3 & 0xff0000) == 0) {
        *(short *)puVar4 = (short)uVar3;
        *(byte *)((int)puVar4 + 2) = 0;
        return param_1;
      }
      if ((uVar3 & 0xff000000) == 0) {
        *puVar4 = uVar3;
        return param_1;
      }
    }
    *puVar4 = uVar3;
    puVar4 = puVar4 + 1;
  } while( true );
}



/* 0040f5f0 FUN_0040f5f0 */

uint * __cdecl FUN_0040f5f0(uint *param_1,uint *param_2)

{
  byte bVar1;
  uint uVar2;
  uint *puVar3;
  uint uVar4;
  uint *puVar5;
  
  puVar3 = param_1;
  do {
    if (((uint)puVar3 & 3) == 0) goto LAB_0040f60c;
    uVar4 = *puVar3;
    puVar3 = (uint *)((int)puVar3 + 1);
  } while ((byte)uVar4 != 0);
  goto LAB_0040f63f;
  while( true ) {
    if ((uVar4 & 0xff0000) == 0) {
      puVar5 = (uint *)((int)puVar5 + 2);
      goto joined_r0x0040f65b;
    }
    if ((uVar4 & 0xff000000) == 0) break;
LAB_0040f60c:
    do {
      puVar5 = puVar3;
      puVar3 = puVar5 + 1;
    } while (((*puVar5 ^ 0xffffffff ^ *puVar5 + 0x7efefeff) & 0x81010100) == 0);
    uVar4 = *puVar5;
    if ((char)uVar4 == '\0') goto joined_r0x0040f65b;
    if ((char)(uVar4 >> 8) == '\0') {
      puVar5 = (uint *)((int)puVar5 + 1);
      goto joined_r0x0040f65b;
    }
  }
LAB_0040f63f:
  puVar5 = (uint *)((int)puVar3 + -1);
joined_r0x0040f65b:
  do {
    if (((uint)param_2 & 3) == 0) {
      do {
        uVar2 = *param_2;
        uVar4 = *param_2;
        param_2 = param_2 + 1;
        if (((uVar2 ^ 0xffffffff ^ uVar2 + 0x7efefeff) & 0x81010100) != 0) {
          if ((char)uVar4 == '\0') {
LAB_0040f6c8:
            *(byte *)puVar5 = (byte)uVar4;
            return param_1;
          }
          if ((char)(uVar4 >> 8) == '\0') {
            *(short *)puVar5 = (short)uVar4;
            return param_1;
          }
          if ((uVar4 & 0xff0000) == 0) {
            *(short *)puVar5 = (short)uVar4;
            *(byte *)((int)puVar5 + 2) = 0;
            return param_1;
          }
          if ((uVar4 & 0xff000000) == 0) {
            *puVar5 = uVar4;
            return param_1;
          }
        }
        *puVar5 = uVar4;
        puVar5 = puVar5 + 1;
      } while( true );
    }
    bVar1 = (byte)*param_2;
    uVar4 = (uint)bVar1;
    param_2 = (uint *)((int)param_2 + 1);
    if (bVar1 == 0) goto LAB_0040f6c8;
    *(byte *)puVar5 = bVar1;
    puVar5 = (uint *)((int)puVar5 + 1);
  } while( true );
}



/* 0040f6d0 FUN_0040f6d0 */

void __cdecl FUN_0040f6d0(byte *param_1,uint param_2)

{
  byte bVar1;
  ushort uVar2;
  byte *pbVar3;
  byte *pbVar4;
  byte bVar5;
  bool bVar6;
  
  pbVar3 = (byte *)0x0;
  if (DAT_0045542c == 0) {
    _strrchr((char *)param_1,param_2);
    return;
  }
  do {
    bVar5 = *param_1;
    if ((*(byte *)((int)&DAT_00455540 + bVar5 + 1) & 4) == 0) {
      bVar6 = param_2 == bVar5;
LAB_0040f723:
      pbVar4 = param_1;
      if (bVar6) {
        pbVar3 = param_1;
      }
    }
    else {
      bVar1 = param_1[1];
      pbVar4 = param_1 + 1;
      if (bVar1 == 0) {
        bVar6 = pbVar3 == (byte *)0x0;
        param_1 = pbVar4;
        bVar5 = bVar1;
        goto LAB_0040f723;
      }
      uVar2 = CONCAT11(bVar5,bVar1);
      bVar5 = bVar1;
      if (param_2 == uVar2) {
        pbVar3 = param_1;
      }
    }
    param_1 = pbVar4 + 1;
    if (bVar5 == 0) {
      return;
    }
  } while( true );
}



/* 0040f730 FUN_0040f730 */

uint __cdecl FUN_0040f730(char *param_1,char *param_2,int param_3)

{
  char cVar1;
  char *pcVar2;
  char *pcVar3;
  
  for (; *param_1 == ';'; param_1 = param_1 + 1) {
  }
  param_3 = param_3 + -1;
  pcVar2 = param_1;
  pcVar3 = param_1;
  if (param_3 != 0) {
    cVar1 = *param_1;
    while ((cVar1 != '\0' && (cVar1 != ';'))) {
      if (cVar1 == '\"') {
        pcVar2 = pcVar3 + 1;
        while ((cVar1 = *pcVar2, cVar1 != '\0' && (cVar1 != '\"'))) {
          *param_2 = cVar1;
          param_2 = param_2 + 1;
          pcVar2 = pcVar2 + 1;
          param_3 = param_3 + -1;
          pcVar3 = pcVar2;
          if (param_3 == 0) goto LAB_0040f796;
        }
        if (*pcVar2 != '\0') {
          pcVar2 = pcVar2 + 1;
        }
      }
      else {
        *param_2 = cVar1;
        param_2 = param_2 + 1;
        pcVar2 = pcVar3 + 1;
        param_3 = param_3 + -1;
        pcVar3 = pcVar2;
        if (param_3 == 0) goto LAB_0040f796;
      }
      pcVar3 = pcVar2;
      cVar1 = *pcVar2;
    }
    for (; pcVar2 = param_1, *pcVar3 == ';'; pcVar3 = pcVar3 + 1) {
    }
  }
LAB_0040f796:
  *param_2 = '\0';
  return -(uint)(pcVar2 != pcVar3) & (uint)pcVar3;
}



/* 0040f7a4 FUN_0040f7a4 */

byte * __cdecl FUN_0040f7a4(byte *param_1,uint param_2)

{
  ushort uVar1;
  byte *pbVar2;
  
  if (DAT_0045542c == 0) {
    pbVar2 = (byte *)_strchr((char *)param_1,param_2);
    return pbVar2;
  }
  while( true ) {
    uVar1 = (ushort)*param_1;
    if (uVar1 == 0) break;
    if ((*(byte *)((int)&DAT_00455540 + uVar1 + 1) & 4) == 0) {
      pbVar2 = param_1;
      if (param_2 == uVar1) break;
    }
    else {
      pbVar2 = param_1 + 1;
      if (param_1[1] == 0) {
        return (byte *)0x0;
      }
      if (param_2 == CONCAT11(*param_1,param_1[1])) {
        return param_1;
      }
    }
    param_1 = pbVar2 + 1;
  }
  return (byte *)(~-(uint)(param_2 != uVar1) & (uint)param_1);
}



/* 0040f817 FUN_0040f817 */

char * __cdecl FUN_0040f817(int param_1,LPCSTR param_2,char *param_3,LPVOID param_4)

{
  undefined4 *puVar1;
  byte bVar2;
  char *pcVar3;
  uint uVar5;
  undefined4 *puVar6;
  BOOL BVar7;
  DWORD DVar8;
  uint *puVar9;
  int iVar10;
  uint uVar11;
  _STARTUPINFOA local_64;
  _PROCESS_INFORMATION local_20;
  char *local_10;
  DWORD local_c;
  char local_5;
  char *pcVar4;
  
  local_5 = '\0';
  local_c = 0;
  if ((param_1 != 0) && (param_1 != 1)) {
    if (param_1 < 2) {
      DAT_00452e48 = 0x16;
      DAT_00452e4c = 0;
      return (char *)0xffffffff;
    }
    if (3 < param_1) {
      if (param_1 != 4) {
        DAT_00452e48 = 0x16;
        DAT_00452e4c = 0;
        return (char *)0xffffffff;
      }
      local_5 = '\x01';
    }
  }
  local_10 = param_3;
  pcVar3 = param_3;
  while (*pcVar3 != '\0') {
    do {
      pcVar4 = pcVar3;
      pcVar3 = pcVar4 + 1;
    } while (*pcVar3 != '\0');
    if (pcVar4[2] != '\0') {
      *pcVar3 = ' ';
      pcVar3 = pcVar4 + 2;
    }
  }
  _memset(&local_64,0,0x44);
  local_64.cb = 0x44;
  uVar11 = DAT_00455760;
  uVar5 = DAT_00455760;
  while ((uVar11 != 0 &&
         (uVar5 = uVar5 - 1,
         *(char *)((&DAT_00455660)[(int)uVar5 >> 5] + 4 + (uVar5 & 0x1f) * 8) == '\0'))) {
    uVar11 = uVar11 - 1;
  }
  uVar5 = uVar11 * 5 + 4;
  local_64.cbReserved2 = (WORD)uVar5;
  local_64.lpReserved2 = (LPBYTE)FUN_0040d0b9(uVar5 & 0xffff,1);
  *(uint *)local_64.lpReserved2 = uVar11;
  uVar5 = 0;
  puVar9 = (uint *)((int)local_64.lpReserved2 + 4);
  puVar6 = (undefined4 *)((int)local_64.lpReserved2 + uVar11 + 4);
  if (0 < (int)uVar11) {
    do {
      puVar1 = (undefined4 *)((&DAT_00455660)[(int)uVar5 >> 5] + (uVar5 & 0x1f) * 8);
      bVar2 = *(byte *)(puVar1 + 1);
      if ((bVar2 & 0x10) == 0) {
        *(byte *)puVar9 = bVar2;
        *puVar6 = *puVar1;
      }
      else {
        *(byte *)puVar9 = 0;
        *puVar6 = 0xffffffff;
      }
      uVar5 = uVar5 + 1;
      puVar9 = (uint *)((int)puVar9 + 1);
      puVar6 = puVar6 + 1;
    } while ((int)uVar5 < (int)uVar11);
  }
  if (local_5 != '\0') {
    puVar9 = (uint *)((int)local_64.lpReserved2 + 4);
    iVar10 = 0;
    puVar6 = (undefined4 *)((int)local_64.lpReserved2 + uVar11 + 4);
    while( true ) {
      uVar5 = uVar11;
      if (2 < (int)uVar11) {
        uVar5 = 3;
      }
      if ((int)uVar5 <= iVar10) break;
      *(undefined1 *)puVar9 = 0;
      *puVar6 = 0xffffffff;
      iVar10 = iVar10 + 1;
      puVar9 = (uint *)((int)puVar9 + 1);
      puVar6 = puVar6 + 1;
    }
    local_c = 8;
  }
  DAT_00452e48 = 0;
  DAT_00452e4c = 0;
  BVar7 = CreateProcessA(param_2,local_10,(LPSECURITY_ATTRIBUTES)0x0,(LPSECURITY_ATTRIBUTES)0x0,1,
                         local_c,param_4,(LPCSTR)0x0,&local_64,&local_20);
  DVar8 = GetLastError();
  FUN_0040aa97(local_64.lpReserved2);
  if (BVar7 != 0) {
    if (param_1 != 2) {
      if (param_1 == 0) {
        WaitForSingleObject(local_20.hProcess,0xffffffff);
        GetExitCodeProcess(local_20.hProcess,(LPDWORD)&param_3);
        CloseHandle(local_20.hProcess);
      }
      else if (param_1 == 4) {
        CloseHandle(local_20.hProcess);
        param_3 = (char *)0x0;
      }
      else {
        param_3 = local_20.hProcess;
      }
      CloseHandle(local_20.hThread);
      return param_3;
    }
                    /* WARNING: Subroutine does not return */
    __exit(0);
  }
  FUN_0040f0e3(DVar8);
  return (char *)0xffffffff;
}



/* 0040f9f6 FUN_0040f9f6 */

undefined4 __cdecl
FUN_0040f9f6(undefined4 *param_1,undefined4 *param_2,undefined4 *param_3,undefined4 *param_4)

{
  size_t sVar1;
  void *pvVar2;
  size_t sVar3;
  char *pcVar4;
  undefined4 uVar5;
  char cVar6;
  undefined4 *puVar7;
  size_t sVar8;
  uint *puVar9;
  undefined4 *puVar10;
  
  sVar8 = 2;
  sVar3 = sVar8;
  for (puVar7 = param_1; (char *)*puVar7 != (char *)0x0; puVar7 = puVar7 + 1) {
    sVar1 = _strlen((char *)*puVar7);
    sVar3 = sVar3 + 1 + sVar1;
  }
  pvVar2 = _malloc(sVar3);
  *param_3 = pvVar2;
  if (pvVar2 == (void *)0x0) {
    *param_4 = 0;
LAB_0040fb17:
    DAT_00452e48 = 0xc;
    DAT_00452e4c = 8;
LAB_0040fb2b:
    uVar5 = 0xffffffff;
  }
  else {
    puVar7 = param_2;
    if (param_2 == (undefined4 *)0x0) {
      *param_4 = 0;
      puVar7 = param_4;
      puVar10 = param_4;
    }
    else {
      for (; (char *)*puVar7 != (char *)0x0; puVar7 = puVar7 + 1) {
        sVar3 = _strlen((char *)*puVar7);
        sVar8 = sVar8 + 1 + sVar3;
      }
      if (DAT_00452e2c == (char *)0x0) {
        DAT_00452e2c = FUN_0040e80f();
        if (DAT_00452e2c != (LPSTR)0x0) goto LAB_0040fa94;
        goto LAB_0040fb2b;
      }
LAB_0040fa94:
      puVar7 = (undefined4 *)0x0;
      if (*DAT_00452e2c != '\0') {
        cVar6 = *DAT_00452e2c;
        pcVar4 = DAT_00452e2c;
        do {
          if (cVar6 == '=') break;
          sVar3 = _strlen(pcVar4);
          puVar7 = (undefined4 *)((int)puVar7 + sVar3 + 1);
          cVar6 = DAT_00452e2c[(int)puVar7];
          pcVar4 = DAT_00452e2c + (int)puVar7;
        } while (cVar6 != '\0');
      }
      pcVar4 = DAT_00452e2c + (int)puVar7;
      puVar10 = puVar7;
      while ((((*pcVar4 == '=' && (pcVar4[1] != '\0')) && (pcVar4[2] == ':')) && (pcVar4[3] == '='))
            ) {
        sVar3 = _strlen(pcVar4 + 4);
        puVar10 = (undefined4 *)((int)puVar10 + sVar3 + 5);
        pcVar4 = DAT_00452e2c + (int)puVar10;
      }
      pvVar2 = _malloc((int)puVar10 + (sVar8 - (int)puVar7));
      *param_4 = pvVar2;
      if (pvVar2 == (void *)0x0) {
        FUN_0040aa97((LPVOID)*param_3);
        *param_3 = 0;
        goto LAB_0040fb17;
      }
    }
    puVar9 = (uint *)*param_3;
    param_3 = param_1;
    if ((uint *)*param_1 != (uint *)0x0) {
      FUN_0040f5e0(puVar9,(uint *)*param_1);
      param_3 = param_1 + 1;
      sVar3 = _strlen((char *)*param_1);
      puVar9 = (uint *)((int)puVar9 + sVar3 + 1);
      goto LAB_0040fb52;
    }
    while( true ) {
      puVar9 = (uint *)((int)puVar9 + 1);
LAB_0040fb52:
      if ((uint *)*param_3 == (uint *)0x0) break;
      FUN_0040f5e0(puVar9,(uint *)*param_3);
      sVar3 = _strlen((char *)*param_3);
      puVar9 = (uint *)((int)puVar9 + sVar3);
      *(undefined1 *)puVar9 = 0x20;
      param_3 = param_3 + 1;
    }
    *(undefined1 *)((int)puVar9 + -1) = 0;
    *(undefined1 *)puVar9 = 0;
    puVar9 = (uint *)*param_4;
    if (param_2 != (undefined4 *)0x0) {
      FUN_0040d670(puVar9,(undefined4 *)(DAT_00452e2c + (int)puVar7),(int)puVar10 - (int)puVar7);
      puVar9 = (uint *)((int)puVar9 + ((int)puVar10 - (int)puVar7));
      for (; (uint *)*param_2 != (uint *)0x0; param_2 = param_2 + 1) {
        FUN_0040f5e0(puVar9,(uint *)*param_2);
        sVar3 = _strlen((char *)*param_2);
        puVar9 = (uint *)((int)puVar9 + sVar3 + 1);
      }
    }
    if (puVar9 != (uint *)0x0) {
      if (puVar9 == (uint *)*param_4) {
        *(undefined1 *)puVar9 = 0;
        puVar9 = (uint *)((int)puVar9 + 1);
      }
      *(undefined1 *)puVar9 = 0;
    }
    FUN_0040aa97(DAT_00452e2c);
    DAT_00452e2c = (char *)0x0;
    uVar5 = 0;
  }
  return uVar5;
}



/* 0040fbfa __mbsnbicoll */

/* Library Function - Single Match
    __mbsnbicoll
   
   Library: Visual Studio 2003 Release */

int __cdecl __mbsnbicoll(uchar *_Str1,uchar *_Str2,size_t _MaxCount)

{
  int iVar1;
  
  if (_MaxCount == 0) {
    return 0;
  }
  iVar1 = FUN_00410287(DAT_00455644,1,_Str1,_MaxCount,_Str2,_MaxCount,DAT_00455410);
  if (iVar1 == 0) {
    return 0x7fffffff;
  }
  return iVar1 + -2;
}



/* 0040fc39 FUN_0040fc39 */

undefined4 FUN_0040fc39(void)

{
  LPCWSTR lpWideCharStr;
  size_t _Size;
  uint *lpMultiByteStr;
  int iVar1;
  undefined4 *puVar2;
  
  lpWideCharStr = (LPCWSTR)*DAT_00452e78;
  puVar2 = DAT_00452e78;
  while( true ) {
    if (lpWideCharStr == (LPCWSTR)0x0) {
      return 0;
    }
    _Size = WideCharToMultiByte(1,0,lpWideCharStr,-1,(LPSTR)0x0,0,(LPCSTR)0x0,(LPBOOL)0x0);
    if (((_Size == 0) || (lpMultiByteStr = _malloc(_Size), lpMultiByteStr == (uint *)0x0)) ||
       (iVar1 = WideCharToMultiByte(1,0,(LPCWSTR)*puVar2,-1,(LPSTR)lpMultiByteStr,_Size,(LPCSTR)0x0,
                                    (LPBOOL)0x0), iVar1 == 0)) break;
    FUN_00410504(lpMultiByteStr,0);
    lpWideCharStr = (LPCWSTR)puVar2[1];
    puVar2 = puVar2 + 1;
  }
  return 0xffffffff;
}



/* 0040fca7 FUN_0040fca7 */

undefined4 __cdecl FUN_0040fca7(int param_1)

{
  BYTE *pBVar1;
  byte *pbVar2;
  byte bVar3;
  byte bVar4;
  UINT CodePage;
  UINT *pUVar5;
  BOOL BVar6;
  uint uVar7;
  BYTE *pBVar8;
  int iVar9;
  byte *pbVar10;
  int iVar11;
  byte *pbVar12;
  undefined4 *puVar13;
  _cpinfo local_1c;
  uint local_8;
  
  CodePage = FUN_0040fe40(param_1);
  if (CodePage == DAT_00455410) {
    return 0;
  }
  if (CodePage != 0) {
    iVar11 = 0;
    pUVar5 = &DAT_00430a20;
    do {
      if (*pUVar5 == CodePage) {
        puVar13 = &DAT_00455540;
        for (iVar9 = 0x40; iVar9 != 0; iVar9 = iVar9 + -1) {
          *puVar13 = 0;
          puVar13 = puVar13 + 1;
        }
        local_8 = 0;
        iVar11 = iVar11 * 0x30;
        *(undefined1 *)puVar13 = 0;
        pbVar12 = (byte *)(iVar11 + 0x430a30);
        do {
          bVar3 = *pbVar12;
          pbVar10 = pbVar12;
          while ((bVar3 != 0 && (bVar3 = pbVar10[1], bVar3 != 0))) {
            uVar7 = (uint)*pbVar10;
            if (uVar7 <= bVar3) {
              bVar4 = (&DAT_00430a18)[local_8];
              do {
                pbVar2 = (byte *)((int)&DAT_00455540 + uVar7 + 1);
                *pbVar2 = *pbVar2 | bVar4;
                uVar7 = uVar7 + 1;
              } while (uVar7 <= bVar3);
            }
            pbVar10 = pbVar10 + 2;
            bVar3 = *pbVar10;
          }
          local_8 = local_8 + 1;
          pbVar12 = pbVar12 + 8;
        } while (local_8 < 4);
        DAT_0045542c = 1;
        DAT_00455410 = CodePage;
        DAT_00455644 = FUN_0040fe8a(CodePage);
        DAT_00455420 = *(undefined4 *)(iVar11 + 0x430a24);
        DAT_00455424 = *(undefined4 *)(iVar11 + 0x430a28);
        DAT_00455428 = *(undefined4 *)(iVar11 + 0x430a2c);
        goto LAB_0040fe2f;
      }
      pUVar5 = pUVar5 + 0xc;
      iVar11 = iVar11 + 1;
    } while ((int)pUVar5 < 0x430b10);
    BVar6 = GetCPInfo(CodePage,&local_1c);
    if (BVar6 == 1) {
      puVar13 = &DAT_00455540;
      DAT_00455410 = CodePage;
      for (iVar11 = 0x40; iVar11 != 0; iVar11 = iVar11 + -1) {
        *puVar13 = 0;
        puVar13 = puVar13 + 1;
      }
      *(undefined1 *)puVar13 = 0;
      DAT_00455644 = 0;
      if (local_1c.MaxCharSize < 2) {
        DAT_0045542c = 0;
      }
      else {
        if (local_1c.LeadByte[0] != '\0') {
          pBVar8 = local_1c.LeadByte + 1;
          do {
            bVar3 = *pBVar8;
            if (bVar3 == 0) break;
            for (uVar7 = (uint)pBVar8[-1]; uVar7 <= bVar3; uVar7 = uVar7 + 1) {
              pbVar12 = (byte *)((int)&DAT_00455540 + uVar7 + 1);
              *pbVar12 = *pbVar12 | 4;
            }
            pBVar1 = pBVar8 + 1;
            pBVar8 = pBVar8 + 2;
          } while (*pBVar1 != 0);
        }
        uVar7 = 1;
        do {
          pbVar12 = (byte *)((int)&DAT_00455540 + uVar7 + 1);
          *pbVar12 = *pbVar12 | 8;
          uVar7 = uVar7 + 1;
        } while (uVar7 < 0xff);
        DAT_00455644 = FUN_0040fe8a(CodePage);
        DAT_0045542c = 1;
      }
      DAT_00455420 = 0;
      DAT_00455424 = 0;
      DAT_00455428 = 0;
      goto LAB_0040fe2f;
    }
    if (DAT_00452fac == 0) {
      return 0xffffffff;
    }
  }
  FUN_0040febd();
LAB_0040fe2f:
  FUN_0040fee6();
  return 0;
}



/* 0040fe40 FUN_0040fe40 */

int __cdecl FUN_0040fe40(int param_1)

{
  int iVar1;
  bool bVar2;
  
  if (param_1 == -2) {
    DAT_00452fac = 1;
                    /* WARNING: Could not recover jumptable at 0x0040fe5a. Too many branches */
                    /* WARNING: Treating indirect jump as call */
    iVar1 = GetOEMCP();
    return iVar1;
  }
  if (param_1 == -3) {
    DAT_00452fac = 1;
                    /* WARNING: Could not recover jumptable at 0x0040fe6f. Too many branches */
                    /* WARNING: Treating indirect jump as call */
    iVar1 = GetACP();
    return iVar1;
  }
  bVar2 = param_1 == -4;
  if (bVar2) {
    param_1 = DAT_00452fd4;
  }
  DAT_00452fac = (uint)bVar2;
  return param_1;
}



/* 0040fe8a FUN_0040fe8a */

undefined4 __cdecl FUN_0040fe8a(int param_1)

{
  if (param_1 == 0x3a4) {
    return 0x411;
  }
  if (param_1 == 0x3a8) {
    return 0x804;
  }
  if (param_1 == 0x3b5) {
    return 0x412;
  }
  if (param_1 != 0x3b6) {
    return 0;
  }
  return 0x404;
}



/* 0040febd FUN_0040febd */

void FUN_0040febd(void)

{
  int iVar1;
  undefined4 *puVar2;
  
  puVar2 = &DAT_00455540;
  for (iVar1 = 0x40; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0;
    puVar2 = puVar2 + 1;
  }
  *(undefined1 *)puVar2 = 0;
  DAT_00455410 = 0;
  DAT_0045542c = 0;
  DAT_00455644 = 0;
  DAT_00455420 = 0;
  DAT_00455424 = 0;
  DAT_00455428 = 0;
  return;
}



/* 0040fee6 FUN_0040fee6 */

void FUN_0040fee6(void)

{
  byte *pbVar1;
  BOOL BVar2;
  uint uVar3;
  char cVar4;
  uint uVar5;
  uint uVar6;
  ushort *puVar7;
  undefined1 uVar8;
  BYTE *pBVar9;
  CHAR *pCVar10;
  WORD local_518 [256];
  WCHAR local_318 [128];
  WCHAR local_218 [128];
  CHAR local_118 [256];
  _cpinfo local_18;
  
  BVar2 = GetCPInfo(DAT_00455410,&local_18);
  if (BVar2 == 1) {
    uVar3 = 0;
    do {
      local_118[uVar3] = (CHAR)uVar3;
      uVar3 = uVar3 + 1;
    } while (uVar3 < 0x100);
    local_118[0] = ' ';
    if (local_18.LeadByte[0] != 0) {
      pBVar9 = local_18.LeadByte + 1;
      do {
        uVar3 = (uint)local_18.LeadByte[0];
        if (uVar3 <= *pBVar9) {
          uVar5 = (*pBVar9 - uVar3) + 1;
          uVar6 = uVar5 >> 2;
          pCVar10 = local_118 + uVar3;
          while (uVar6 != 0) {
            uVar6 = uVar6 - 1;
            builtin_memcpy(pCVar10,"    ",4);
            pCVar10 = pCVar10 + 4;
          }
          for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
            *pCVar10 = ' ';
            pCVar10 = pCVar10 + 1;
          }
        }
        local_18.LeadByte[0] = pBVar9[1];
        pBVar9 = pBVar9 + 2;
      } while (local_18.LeadByte[0] != 0);
    }
    FUN_0040eca0(1,local_118,0x100,local_518,DAT_00455410,DAT_00455644,0);
    FUN_0041074a(DAT_00455644,0x100,local_118,0x100,local_218,0x100,DAT_00455410,0);
    FUN_0041074a(DAT_00455644,0x200,local_118,0x100,local_318,0x100,DAT_00455410,0);
    uVar3 = 0;
    puVar7 = local_518;
    do {
      if ((*puVar7 & 1) == 0) {
        if ((*puVar7 & 2) != 0) {
          pbVar1 = (byte *)((int)&DAT_00455540 + uVar3 + 1);
          *pbVar1 = *pbVar1 | 0x20;
          uVar8 = *(undefined1 *)((int)local_318 + uVar3);
          goto LAB_0040fff2;
        }
        (&DAT_00455440)[uVar3] = 0;
      }
      else {
        pbVar1 = (byte *)((int)&DAT_00455540 + uVar3 + 1);
        *pbVar1 = *pbVar1 | 0x10;
        uVar8 = *(undefined1 *)((int)local_218 + uVar3);
LAB_0040fff2:
        (&DAT_00455440)[uVar3] = uVar8;
      }
      uVar3 = uVar3 + 1;
      puVar7 = puVar7 + 1;
    } while (uVar3 < 0x100);
  }
  else {
    uVar3 = 0;
    do {
      if ((uVar3 < 0x41) || (0x5a < uVar3)) {
        if ((0x60 < uVar3) && (uVar3 < 0x7b)) {
          pbVar1 = (byte *)((int)&DAT_00455540 + uVar3 + 1);
          *pbVar1 = *pbVar1 | 0x20;
          cVar4 = (char)uVar3 + -0x20;
          goto LAB_0041003c;
        }
        (&DAT_00455440)[uVar3] = 0;
      }
      else {
        pbVar1 = (byte *)((int)&DAT_00455540 + uVar3 + 1);
        *pbVar1 = *pbVar1 | 0x10;
        cVar4 = (char)uVar3 + ' ';
LAB_0041003c:
        (&DAT_00455440)[uVar3] = cVar4;
      }
      uVar3 = uVar3 + 1;
    } while (uVar3 < 0x100);
  }
  return;
}



/* 0041006b FUN_0041006b */

void FUN_0041006b(void)

{
  if (DAT_0045564c == 0) {
    FUN_0040fca7(-3);
    DAT_0045564c = 1;
  }
  return;
}



/* 00410087 FUN_00410087 */

int __cdecl FUN_00410087(undefined4 param_1,undefined4 param_2,undefined4 param_3)

{
  HMODULE hModule;
  int iVar1;
  
  iVar1 = 0;
  if (DAT_00452fb0 == (FARPROC)0x0) {
    hModule = LoadLibraryA("user32.dll");
    if (hModule != (HMODULE)0x0) {
      DAT_00452fb0 = GetProcAddress(hModule,"MessageBoxA");
      if (DAT_00452fb0 != (FARPROC)0x0) {
        DAT_00452fb4 = GetProcAddress(hModule,"GetActiveWindow");
        DAT_00452fb8 = GetProcAddress(hModule,"GetLastActivePopup");
        goto LAB_004100d6;
      }
    }
    iVar1 = 0;
  }
  else {
LAB_004100d6:
    if (DAT_00452fb4 != (FARPROC)0x0) {
      iVar1 = (*DAT_00452fb4)();
      if ((iVar1 != 0) && (DAT_00452fb8 != (FARPROC)0x0)) {
        iVar1 = (*DAT_00452fb8)(iVar1);
      }
    }
    iVar1 = (*DAT_00452fb0)(iVar1,param_1,param_2,param_3);
  }
  return iVar1;
}



/* 00410110 FUN_00410110 */

void FUN_00410110(void)

{
  __amsg_exit(2);
  return;
}



/* 00410119 FUN_00410119 */

int __cdecl FUN_00410119(uint param_1,int param_2)

{
  DWORD DVar1;
  DWORD DVar2;
  int iVar3;
  uint uVar4;
  int iVar5;
  HANDLE hFile;
  BOOL BVar6;
  int iVar7;
  uint uVar8;
  char local_1004 [4060];
  undefined4 uStackY_28;
  
  FUN_0040afb0();
  iVar7 = 0;
  if ((param_1 < DAT_00455760) &&
     ((*(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + (param_1 & 0x1f) * 8) & 1) != 0)) {
    DVar1 = FUN_0040df2d(param_1,0,1);
    if ((DVar1 != 0xffffffff) && (DVar2 = FUN_0040df2d(param_1,0,2), DVar2 != 0xffffffff)) {
      uVar8 = param_2 - DVar2;
      if ((int)uVar8 < 1) {
        if ((int)uVar8 < 0) {
          FUN_0040df2d(param_1,param_2,0);
          hFile = (HANDLE)FUN_0040f2d0(param_1);
          BVar6 = SetEndOfFile(hFile);
          iVar7 = (BVar6 != 0) - 1;
          if (iVar7 == -1) {
            DAT_00452e48 = 0xd;
            DAT_00452e4c = GetLastError();
          }
        }
      }
      else {
        _memset(local_1004,0,0x1000);
        uStackY_28 = 0x4101a9;
        iVar3 = FUN_00410999(param_1,0x8000);
        do {
          uVar4 = 0x1000;
          if ((int)uVar8 < 0x1000) {
            uVar4 = uVar8;
          }
          iVar5 = FUN_0040daba(param_1,local_1004,uVar4);
          if (iVar5 == -1) {
            if (DAT_00452e4c == 5) {
              DAT_00452e48 = 0xd;
            }
            iVar7 = -1;
            break;
          }
          uVar8 = uVar8 - iVar5;
        } while (0 < (int)uVar8);
        FUN_00410999(param_1,iVar3);
      }
      FUN_0040df2d(param_1,DVar1,0);
      return iVar7;
    }
  }
  else {
    DAT_00452e48 = 9;
  }
  return -1;
}



/* 00410260 _strrchr */

/* Library Function - Single Match
    _strrchr
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release */

char * __cdecl _strrchr(char *_Str,int _Ch)

{
  char cVar1;
  int iVar2;
  char *pcVar3;
  char *pcVar4;
  
  iVar2 = -1;
  do {
    pcVar4 = _Str;
    if (iVar2 == 0) break;
    iVar2 = iVar2 + -1;
    pcVar4 = _Str + 1;
    cVar1 = *_Str;
    _Str = pcVar4;
  } while (cVar1 != '\0');
  iVar2 = -(iVar2 + 1);
  pcVar4 = pcVar4 + -1;
  do {
    pcVar3 = pcVar4;
    if (iVar2 == 0) break;
    iVar2 = iVar2 + -1;
    pcVar3 = pcVar4 + -1;
    cVar1 = *pcVar4;
    pcVar4 = pcVar3;
  } while ((char)_Ch != cVar1);
  pcVar3 = pcVar3 + 1;
  if (*pcVar3 != (char)_Ch) {
    pcVar3 = (char *)0x0;
  }
  return pcVar3;
}



/* 00410287 FUN_00410287 */

int __cdecl
FUN_00410287(LCID param_1,DWORD param_2,byte *param_3,int param_4,byte *param_5,int param_6,
            UINT param_7)

{
  undefined1 *puVar1;
  int iVar2;
  BOOL BVar3;
  BYTE *pBVar4;
  int iVar5;
  _cpinfo local_40;
  undefined1 *local_2c;
  PCNZWCH local_28;
  int local_24;
  int local_20;
  undefined1 *local_1c;
  void *local_14;
  undefined1 *puStack_10;
  undefined *puStack_c;
  undefined4 local_8;
  
  local_8 = 0xffffffff;
  puStack_c = &DAT_0042c6c8;
  puStack_10 = &LAB_0040ea3c;
  local_14 = ExceptionList;
  local_1c = &stack0xffffffb0;
  ExceptionList = &local_14;
  puVar1 = &stack0xffffffb0;
  if (DAT_00452fe0 == 0) {
    ExceptionList = &local_14;
    iVar2 = CompareStringW(0,0,L"",1,L"",1);
    if (iVar2 == 0) {
      iVar2 = CompareStringA(0,0,"",1,"",1);
      if (iVar2 == 0) {
        ExceptionList = local_14;
        return 0;
      }
      DAT_00452fe0 = 2;
      puVar1 = local_1c;
    }
    else {
      DAT_00452fe0 = 1;
      puVar1 = local_1c;
    }
  }
  local_1c = puVar1;
  if (0 < param_4) {
    param_4 = FUN_0041096e((char *)param_3,param_4);
  }
  if (0 < param_6) {
    param_6 = FUN_0041096e((char *)param_5,param_6);
  }
  if (DAT_00452fe0 == 2) {
    iVar2 = CompareStringA(param_1,param_2,(PCNZCH)param_3,param_4,(PCNZCH)param_5,param_6);
    ExceptionList = local_14;
    return iVar2;
  }
  if (DAT_00452fe0 == 1) {
    if (param_7 == 0) {
      param_7 = DAT_00452fd4;
    }
    if ((param_4 == 0) || (param_6 == 0)) {
      if (param_4 == param_6) {
        ExceptionList = local_14;
        return 2;
      }
      if (1 < param_6) {
        ExceptionList = local_14;
        return 1;
      }
      if (1 < param_4) {
        ExceptionList = local_14;
        return 3;
      }
      BVar3 = GetCPInfo(param_7,&local_40);
      if (BVar3 == 0) {
        ExceptionList = local_14;
        return 0;
      }
      if (0 < param_4) {
        if (local_40.MaxCharSize < 2) {
          ExceptionList = local_14;
          return 3;
        }
        pBVar4 = local_40.LeadByte;
        while( true ) {
          if (local_40.LeadByte[0] == 0) {
            ExceptionList = local_14;
            return 3;
          }
          if (pBVar4[1] == 0) break;
          if ((*pBVar4 <= *param_3) && (*param_3 <= pBVar4[1])) {
            ExceptionList = local_14;
            return 2;
          }
          pBVar4 = pBVar4 + 2;
          local_40.LeadByte[0] = *pBVar4;
        }
        ExceptionList = local_14;
        return 3;
      }
      if (0 < param_6) {
        if (local_40.MaxCharSize < 2) {
          ExceptionList = local_14;
          return 1;
        }
        pBVar4 = local_40.LeadByte;
        while( true ) {
          if (local_40.LeadByte[0] == 0) {
            ExceptionList = local_14;
            return 1;
          }
          if (pBVar4[1] == 0) break;
          if ((*pBVar4 <= *param_5) && (*param_5 <= pBVar4[1])) {
            ExceptionList = local_14;
            return 2;
          }
          pBVar4 = pBVar4 + 2;
          local_40.LeadByte[0] = *pBVar4;
        }
        ExceptionList = local_14;
        return 1;
      }
    }
    local_20 = MultiByteToWideChar(param_7,9,(LPCSTR)param_3,param_4,(LPWSTR)0x0,0);
    if (local_20 != 0) {
      local_8 = 0;
      FUN_0040afb0();
      local_8 = 0xffffffff;
      if ((&stack0x00000000 != (undefined1 *)0x50) &&
         (local_28 = (PCNZWCH)&stack0xffffffb0, local_1c = &stack0xffffffb0,
         iVar2 = MultiByteToWideChar(param_7,1,(LPCSTR)param_3,param_4,(LPWSTR)&stack0xffffffb0,
                                     local_20), iVar2 != 0)) {
        iVar2 = MultiByteToWideChar(param_7,9,(LPCSTR)param_5,param_6,(LPWSTR)0x0,0);
        if (iVar2 != 0) {
          local_8 = 1;
          local_24 = iVar2;
          FUN_0040afb0();
          local_8 = 0xffffffff;
          if ((&stack0x00000000 != (undefined1 *)0x50) &&
             (local_2c = &stack0xffffffb0, local_1c = &stack0xffffffb0,
             iVar5 = MultiByteToWideChar(param_7,1,(LPCSTR)param_5,param_6,(LPWSTR)&stack0xffffffb0,
                                         iVar2), iVar5 != 0)) {
            iVar2 = CompareStringW(param_1,param_2,local_28,local_20,(PCNZWCH)&stack0xffffffb0,iVar2
                                  );
            ExceptionList = local_14;
            return iVar2;
          }
        }
      }
    }
  }
  ExceptionList = local_14;
  return 0;
}



/* 00410504 FUN_00410504 */

undefined4 __cdecl FUN_00410504(uint *param_1,int param_2)

{
  uint *puVar1;
  int iVar2;
  int *piVar3;
  size_t sVar4;
  uint *lpName;
  byte *pbVar5;
  int *piVar6;
  bool bVar7;
  
  if (param_1 == (uint *)0x0) {
    return 0xffffffff;
  }
  puVar1 = (uint *)FUN_0040f7a4((byte *)param_1,0x3d);
  if (puVar1 == (uint *)0x0) {
    return 0xffffffff;
  }
  if (param_1 == puVar1) {
    return 0xffffffff;
  }
  bVar7 = *(byte *)((int)puVar1 + 1) == 0;
  if (DAT_00452e70 == DAT_00452e74) {
    DAT_00452e70 = FUN_004106e3(DAT_00452e70);
  }
  if (DAT_00452e70 == (int *)0x0) {
    if ((param_2 == 0) || (DAT_00452e78 == (undefined4 *)0x0)) {
      if (bVar7) {
        return 0;
      }
      DAT_00452e70 = _malloc(4);
      if (DAT_00452e70 == (int *)0x0) {
        return 0xffffffff;
      }
      *DAT_00452e70 = 0;
      if (DAT_00452e78 == (undefined4 *)0x0) {
        DAT_00452e78 = _malloc(4);
        if (DAT_00452e78 == (undefined4 *)0x0) {
          return 0xffffffff;
        }
        *DAT_00452e78 = 0;
      }
    }
    else {
      iVar2 = FUN_0040fc39();
      if (iVar2 != 0) {
        return 0xffffffff;
      }
    }
  }
  piVar3 = DAT_00452e70;
  iVar2 = FUN_0041068b((uchar *)param_1,(int)puVar1 - (int)param_1);
  if ((iVar2 < 0) || (*piVar3 == 0)) {
    if (bVar7) {
      return 0;
    }
    if (iVar2 < 0) {
      iVar2 = -iVar2;
    }
    piVar3 = FUN_0040b428(piVar3,(uint *)(iVar2 * 4 + 8));
    if (piVar3 == (int *)0x0) {
      return 0xffffffff;
    }
    piVar3[iVar2] = (int)param_1;
    piVar3[iVar2 + 1] = 0;
  }
  else {
    if (!bVar7) {
      piVar3[iVar2] = (int)param_1;
      goto LAB_00410638;
    }
    piVar6 = piVar3 + iVar2;
    FUN_0040aa97((LPVOID)piVar3[iVar2]);
    for (; *piVar6 != 0; piVar6 = piVar6 + 1) {
      iVar2 = iVar2 + 1;
      *piVar6 = piVar6[1];
    }
    piVar3 = FUN_0040b428(piVar3,(uint *)(iVar2 << 2));
    if (piVar3 == (int *)0x0) goto LAB_00410638;
  }
  DAT_00452e70 = piVar3;
LAB_00410638:
  if (param_2 != 0) {
    sVar4 = _strlen((char *)param_1);
    lpName = _malloc(sVar4 + 2);
    if (lpName != (uint *)0x0) {
      FUN_0040f5e0(lpName,param_1);
      pbVar5 = (byte *)(((int)lpName - (int)param_1) + (int)puVar1);
      *pbVar5 = 0;
      SetEnvironmentVariableA((LPCSTR)lpName,(LPCSTR)(~-(uint)bVar7 & (uint)(pbVar5 + 1)));
      FUN_0040aa97(lpName);
    }
  }
  return 0;
}



/* 0041068b FUN_0041068b */

int __cdecl FUN_0041068b(uchar *param_1,size_t param_2)

{
  uchar *_Str2;
  int iVar1;
  int *piVar2;
  
  _Str2 = (uchar *)*DAT_00452e70;
  piVar2 = DAT_00452e70;
  while( true ) {
    if (_Str2 == (uchar *)0x0) {
      return -((int)piVar2 - (int)DAT_00452e70 >> 2);
    }
    iVar1 = __mbsnbicoll(param_1,_Str2,param_2);
    if ((iVar1 == 0) &&
       ((*(char *)(*piVar2 + param_2) == '=' || (*(char *)(*piVar2 + param_2) == '\0')))) break;
    _Str2 = (uchar *)piVar2[1];
    piVar2 = piVar2 + 1;
  }
  return (int)piVar2 - (int)DAT_00452e70 >> 2;
}



/* 004106e3 FUN_004106e3 */

undefined4 * __cdecl FUN_004106e3(int *param_1)

{
  int iVar1;
  int *piVar2;
  undefined4 *puVar3;
  uint *puVar4;
  int iVar5;
  undefined4 *puVar6;
  
  iVar5 = 0;
  if (param_1 != (int *)0x0) {
    iVar1 = *param_1;
    piVar2 = param_1;
    while (iVar1 != 0) {
      piVar2 = piVar2 + 1;
      iVar5 = iVar5 + 1;
      iVar1 = *piVar2;
    }
    puVar3 = _malloc(iVar5 * 4 + 4);
    if (puVar3 == (undefined4 *)0x0) {
      __amsg_exit(9);
    }
    puVar4 = (uint *)*param_1;
    puVar6 = puVar3;
    while (puVar4 != (uint *)0x0) {
      param_1 = param_1 + 1;
      puVar4 = FUN_00410a0f(puVar4);
      *puVar6 = puVar4;
      puVar6 = puVar6 + 1;
      puVar4 = (uint *)*param_1;
    }
    *puVar6 = 0;
    return puVar3;
  }
  return (undefined4 *)0x0;
}



/* 0041074a FUN_0041074a */

int __cdecl
FUN_0041074a(LCID param_1,uint param_2,char *param_3,int param_4,LPWSTR param_5,int param_6,
            UINT param_7,int param_8)

{
  int iVar1;
  int iVar2;
  void *local_14;
  undefined1 *puStack_10;
  undefined *puStack_c;
  undefined4 local_8;
  
  local_8 = 0xffffffff;
  puStack_c = &DAT_0042c6e0;
  puStack_10 = &LAB_0040ea3c;
  local_14 = ExceptionList;
  ExceptionList = &local_14;
  if (DAT_00452fe4 == 0) {
    ExceptionList = &local_14;
    iVar1 = LCMapStringW(0,0x100,L"",1,(LPWSTR)0x0,0);
    if (iVar1 == 0) {
      iVar1 = LCMapStringA(0,0x100,"",1,(LPSTR)0x0,0);
      if (iVar1 == 0) {
        ExceptionList = local_14;
        return 0;
      }
      DAT_00452fe4 = 2;
    }
    else {
      DAT_00452fe4 = 1;
    }
  }
  if (0 < param_4) {
    param_4 = FUN_0041096e(param_3,param_4);
  }
  if (DAT_00452fe4 == 2) {
    iVar1 = LCMapStringA(param_1,param_2,param_3,param_4,(LPSTR)param_5,param_6);
    ExceptionList = local_14;
    return iVar1;
  }
  if (DAT_00452fe4 == 1) {
    if (param_7 == 0) {
      param_7 = DAT_00452fd4;
    }
    iVar1 = MultiByteToWideChar(param_7,(-(uint)(param_8 != 0) & 8) + 1,param_3,param_4,(LPWSTR)0x0,
                                0);
    if (iVar1 != 0) {
      local_8 = 0;
      FUN_0040afb0();
      local_8 = 0xffffffff;
      if ((&stack0x00000000 != (undefined1 *)0x3c) &&
         (iVar2 = MultiByteToWideChar(param_7,1,param_3,param_4,(LPWSTR)&stack0xffffffc4,iVar1),
         iVar2 != 0)) {
        iVar2 = LCMapStringW(param_1,param_2,(LPCWSTR)&stack0xffffffc4,iVar1,(LPWSTR)0x0,0);
        if (iVar2 != 0) {
          if ((param_2 & 0x400) == 0) {
            local_8 = 1;
            FUN_0040afb0();
            local_8 = 0xffffffff;
            if (&stack0x00000000 == (undefined1 *)0x3c) {
              ExceptionList = local_14;
              return 0;
            }
            iVar1 = LCMapStringW(param_1,param_2,(LPCWSTR)&stack0xffffffc4,iVar1,
                                 (LPWSTR)&stack0xffffffc4,iVar2);
            if (iVar1 == 0) {
              ExceptionList = local_14;
              return 0;
            }
            if (param_6 == 0) {
              param_6 = 0;
              param_5 = (LPWSTR)0x0;
            }
            iVar2 = WideCharToMultiByte(param_7,0x220,(LPCWSTR)&stack0xffffffc4,iVar2,(LPSTR)param_5
                                        ,param_6,(LPCSTR)0x0,(LPBOOL)0x0);
            iVar1 = iVar2;
          }
          else {
            if (param_6 == 0) {
              ExceptionList = local_14;
              return iVar2;
            }
            if (param_6 < iVar2) {
              ExceptionList = local_14;
              return 0;
            }
            iVar1 = LCMapStringW(param_1,param_2,(LPCWSTR)&stack0xffffffc4,iVar1,param_5,param_6);
          }
          if (iVar1 != 0) {
            ExceptionList = local_14;
            return iVar2;
          }
        }
      }
    }
  }
  ExceptionList = local_14;
  return 0;
}



/* 0041096e FUN_0041096e */

int __cdecl FUN_0041096e(char *param_1,int param_2)

{
  char *pcVar1;
  int iVar2;
  
  iVar2 = param_2;
  for (pcVar1 = param_1; (iVar2 != 0 && (iVar2 = iVar2 + -1, *pcVar1 != '\0')); pcVar1 = pcVar1 + 1)
  {
  }
  if (*pcVar1 != '\0') {
    return param_2;
  }
  return (int)pcVar1 - (int)param_1;
}



/* 00410999 FUN_00410999 */

int __cdecl FUN_00410999(uint param_1,int param_2)

{
  byte bVar1;
  byte bVar2;
  
  if (param_1 < DAT_00455760) {
    bVar1 = *(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + (param_1 & 0x1f) * 8);
    if ((bVar1 & 1) != 0) {
      if (param_2 == 0x8000) {
        bVar2 = bVar1 & 0x7f;
      }
      else {
        if (param_2 != 0x4000) {
          DAT_00452e48 = 0x16;
          return -1;
        }
        bVar2 = bVar1 | 0x80;
      }
      *(byte *)((&DAT_00455660)[(int)param_1 >> 5] + 4 + (param_1 & 0x1f) * 8) = bVar2;
      return (-(uint)((bVar1 & 0x80) != 0) & 0xffffc000) + 0x8000;
    }
  }
  DAT_00452e48 = 9;
  return -1;
}



/* 00410a0f FUN_00410a0f */

uint * __cdecl FUN_00410a0f(uint *param_1)

{
  size_t sVar1;
  uint *puVar2;
  
  if (param_1 != (uint *)0x0) {
    sVar1 = _strlen((char *)param_1);
    puVar2 = _malloc(sVar1 + 1);
    if (puVar2 != (uint *)0x0) {
      puVar2 = FUN_0040f5e0(puVar2,param_1);
      return puVar2;
    }
  }
  return (uint *)0x0;
}



/* 00410a40 FUN_00410a40 */

int __cdecl FUN_00410a40(LPCSTR param_1,int param_2)

{
  char cVar1;
  byte bVar2;
  FILE *pFVar3;
  int iVar4;
  byte *pbVar5;
  int iVar6;
  void *pvVar7;
  undefined4 *puVar8;
  char *pcVar9;
  int iVar10;
  uint uVar11;
  uint uVar12;
  byte *pbVar13;
  byte *pbVar14;
  char *pcVar15;
  undefined4 *puVar16;
  bool bVar17;
  char *pcVar18;
  char *local_88;
  int local_80;
  int local_7c;
  int local_74;
  undefined1 local_70 [112];
  
  pFVar3 = (FILE *)FUN_0040af91(param_1 + 0x1800,&DAT_0042fa6c);
  if (pFVar3 == (FILE *)0x0) {
    FUN_0040aa66((byte *)s_faile_to_open_file__s_00430e3c);
    return -0xc;
  }
  iVar4 = PEM_read_RSAPrivateKey(pFVar3,0,0,0);
  if (iVar4 == 0) {
    ERR_print_errors_fp(&DAT_004302a0);
    return -0x16;
  }
  FUN_0040af1b(pFVar3);
  if (param_1 == (LPCSTR)0x0) {
    FUN_0040aa66((byte *)s_Usage__codefile_script_output_fi_00430e00);
    FUN_0040aa66((byte *)s__script___command_file_for_this_t_00430dd4);
    FUN_0040aa66((byte *)s__customer___name_of_customer_00430db0);
    FUN_0040aa66((byte *)s__model___model_name_00430d94);
    FUN_0040aa66((byte *)s__version___firmware_version_00430d70);
    return -1;
  }
  FUN_00428f30(&local_74);
  FUN_0040aa66((byte *)s_Read_script_file__>__s_00430d58);
  puVar16 = &DAT_004567c0;
  for (iVar10 = 0x24; iVar10 != 0; iVar10 = iVar10 + -1) {
    *puVar16 = 0;
    puVar16 = puVar16 + 1;
  }
  FUN_00411120(param_1);
  FUN_0040aa66((byte *)s_Vendor_name____s_00430d44);
  FUN_0040aa66((byte *)s_Model_name____s_00430d30);
  FUN_0040aa66((byte *)s_Version____s_00430d1c);
  FUN_0040aa66((byte *)s_Control___00430d0c);
  if (DAT_0045680c == 0) {
    pcVar18 = s_Only_check_file_consistency_00430cd4;
  }
  else {
    if ((DAT_0045680c & 0x10) != 0) {
      FUN_0040aa66(&DAT_00430d08);
    }
    if ((DAT_0045680c & 0x20) != 0) {
      FUN_0040aa66((byte *)s_version_00430cfc);
    }
    if ((DAT_0045680c & 0x40) != 0) {
      FUN_0040aa66((byte *)s_model_00430cf4);
    }
    pcVar18 = &DAT_0042fcbc;
  }
  FUN_0040aa66((byte *)pcVar18);
  iVar10 = FUN_00411850();
  if (iVar10 != 0) {
    return -1;
  }
  FUN_004118e0();
  FUN_0040aa66((byte *)s_Content__00430cc8);
  pcVar9 = *(char **)(DAT_00453088 + 0x18);
  pcVar18 = DAT_00453088;
  while (pcVar15 = pcVar9, pcVar15 != (char *)0x0) {
    pcVar18 = pcVar15;
    pcVar9 = *(char **)(pcVar15 + 0x18);
  }
  local_80 = *(int *)(pcVar18 + 0x10) + *(int *)(pcVar18 + 0xc);
  for (; param_2 != 0; param_2 = *(int *)(param_2 + 0x3010)) {
    pbVar13 = &DAT_0042fb50;
    pbVar5 = (byte *)(param_2 + 0x180e);
    do {
      bVar2 = *pbVar5;
      bVar17 = bVar2 < *pbVar13;
      if (bVar2 != *pbVar13) {
LAB_00410c72:
        local_7c = (1 - (uint)bVar17) - (uint)(bVar17 != 0);
        goto LAB_00410c7b;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar5[1];
      bVar17 = bVar2 < pbVar13[1];
      if (bVar2 != pbVar13[1]) goto LAB_00410c72;
      pbVar5 = pbVar5 + 2;
      pbVar13 = pbVar13 + 2;
    } while (bVar2 != 0);
    local_7c = 0;
LAB_00410c7b:
    pbVar5 = (byte *)(param_2 + 4);
    pbVar14 = &DAT_00430cc0;
    pbVar13 = pbVar5;
    do {
      bVar2 = *pbVar13;
      bVar17 = bVar2 < *pbVar14;
      if (bVar2 != *pbVar14) {
LAB_00410cad:
        iVar10 = (1 - (uint)bVar17) - (uint)(bVar17 != 0);
        goto LAB_00410cb2;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar13[1];
      bVar17 = bVar2 < pbVar14[1];
      if (bVar2 != pbVar14[1]) goto LAB_00410cad;
      pbVar13 = pbVar13 + 2;
      pbVar14 = pbVar14 + 2;
    } while (bVar2 != 0);
    iVar10 = 0;
LAB_00410cb2:
    pbVar13 = &DAT_00430cb8;
    do {
      bVar2 = *pbVar5;
      bVar17 = bVar2 < *pbVar13;
      if (bVar2 != *pbVar13) {
LAB_00410cdd:
        iVar6 = (1 - (uint)bVar17) - (uint)(bVar17 != 0);
        goto LAB_00410ce2;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar5[1];
      bVar17 = bVar2 < pbVar13[1];
      if (bVar2 != pbVar13[1]) goto LAB_00410cdd;
      pbVar5 = pbVar5 + 2;
      pbVar13 = pbVar13 + 2;
    } while (bVar2 != 0);
    iVar6 = 0;
LAB_00410ce2:
    local_88 = pcVar18;
    if (local_7c == 0 && (iVar6 == 0 || iVar10 == 0)) {
      local_88 = _malloc(0x1c);
      pcVar9 = local_88;
      for (iVar10 = 7; iVar10 != 0; iVar10 = iVar10 + -1) {
        pcVar9[0] = '\0';
        pcVar9[1] = '\0';
        pcVar9[2] = '\0';
        pcVar9[3] = '\0';
        pcVar9 = pcVar9 + 4;
      }
      uVar11 = 0xffffffff;
      pcVar9 = (char *)(param_2 + 0x80e);
      do {
        if (uVar11 == 0) break;
        uVar11 = uVar11 - 1;
        cVar1 = *pcVar9;
        pcVar9 = pcVar9 + 1;
      } while (cVar1 != '\0');
      pvVar7 = _malloc(~uVar11);
      *(void **)(local_88 + 0x14) = pvVar7;
      uVar11 = 0xffffffff;
      pcVar9 = (char *)(param_2 + 9);
      do {
        pcVar15 = pcVar9;
        if (uVar11 == 0) break;
        uVar11 = uVar11 - 1;
        pcVar15 = pcVar9 + 1;
        cVar1 = *pcVar9;
        pcVar9 = pcVar15;
      } while (cVar1 != '\0');
      uVar11 = ~uVar11;
      pcVar9 = pcVar15 + -uVar11;
      pcVar15 = local_88;
      for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
        *(undefined4 *)pcVar15 = *(undefined4 *)pcVar9;
        pcVar9 = pcVar9 + 4;
        pcVar15 = pcVar15 + 4;
      }
      for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
        *pcVar15 = *pcVar9;
        pcVar9 = pcVar9 + 1;
        pcVar15 = pcVar15 + 1;
      }
      uVar11 = 0xffffffff;
      pcVar9 = pcVar18 + 5;
      do {
        pcVar15 = pcVar9;
        if (uVar11 == 0) break;
        uVar11 = uVar11 - 1;
        pcVar15 = pcVar9 + 1;
        cVar1 = *pcVar9;
        pcVar9 = pcVar15;
      } while (cVar1 != '\0');
      uVar11 = ~uVar11;
      pcVar9 = pcVar15 + -uVar11;
      pcVar15 = local_88 + 5;
      for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
        *(undefined4 *)pcVar15 = *(undefined4 *)pcVar9;
        pcVar9 = pcVar9 + 4;
        pcVar15 = pcVar15 + 4;
      }
      for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
        *pcVar15 = *pcVar9;
        pcVar9 = pcVar9 + 1;
        pcVar15 = pcVar15 + 1;
      }
      uVar11 = 0xffffffff;
      pcVar9 = (char *)(param_2 + 0x80e);
      do {
        pcVar15 = pcVar9;
        if (uVar11 == 0) break;
        uVar11 = uVar11 - 1;
        pcVar15 = pcVar9 + 1;
        cVar1 = *pcVar9;
        pcVar9 = pcVar15;
      } while (cVar1 != '\0');
      uVar11 = ~uVar11;
      pcVar9 = pcVar15 + -uVar11;
      pcVar15 = *(char **)(local_88 + 0x14);
      for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
        *(undefined4 *)pcVar15 = *(undefined4 *)pcVar9;
        pcVar9 = pcVar9 + 4;
        pcVar15 = pcVar15 + 4;
      }
      for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
        *pcVar15 = *pcVar9;
        pcVar9 = pcVar9 + 1;
        pcVar15 = pcVar15 + 1;
      }
      iVar10 = FUN_004117e0(*(LPCSTR *)(local_88 + 0x14));
      *(int *)(local_88 + 0x10) = local_80;
      local_80 = local_80 + iVar10 + 0x3c;
      *(int *)(local_88 + 0xc) = iVar10 + 0x3c;
      *(char **)(pcVar18 + 0x18) = local_88;
    }
    pcVar18 = local_88;
  }
  puVar8 = _malloc(0x1c);
  puVar16 = puVar8;
  for (iVar10 = 7; iVar10 != 0; iVar10 = iVar10 + -1) {
    *puVar16 = 0;
    puVar16 = puVar16 + 1;
  }
  pvVar7 = _malloc(8);
  puVar8[5] = pvVar7;
  *puVar8 = DAT_00430cb0;
  *(undefined1 *)(puVar8 + 1) = DAT_00430cb4;
  uVar11 = 0xffffffff;
  pcVar9 = pcVar18 + 5;
  do {
    pcVar15 = pcVar9;
    if (uVar11 == 0) break;
    uVar11 = uVar11 - 1;
    pcVar15 = pcVar9 + 1;
    cVar1 = *pcVar9;
    pcVar9 = pcVar15;
  } while (cVar1 != '\0');
  uVar11 = ~uVar11;
  pcVar9 = pcVar15 + -uVar11;
  pcVar15 = (char *)((int)puVar8 + 5);
  for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
    *(undefined4 *)pcVar15 = *(undefined4 *)pcVar9;
    pcVar9 = pcVar9 + 4;
    pcVar15 = pcVar15 + 4;
  }
  for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
    *pcVar15 = *pcVar9;
    pcVar9 = pcVar9 + 1;
    pcVar15 = pcVar15 + 1;
  }
  puVar16 = (undefined4 *)puVar8[5];
  *puVar16 = DAT_0042fcb4;
  puVar16[1] = DAT_0042fcb8;
  iVar10 = FUN_004117e0((LPCSTR)puVar8[5]);
  puVar8[3] = iVar10 + 0x3c;
  puVar8[4] = local_80;
  *(undefined4 **)(pcVar18 + 0x18) = puVar8;
  for (pcVar18 = DAT_00453088; pcVar18 != (char *)0x0; pcVar18 = *(char **)(pcVar18 + 0x18)) {
    FUN_0040aa66((byte *)s_Append__s__privacy____d__tag_____00430c84);
  }
  FUN_0040aa66(&DAT_0042fcbc);
  pcVar18 = param_1 + 0x400;
  uVar11 = 0xffffffff;
  pcVar9 = pcVar18;
  do {
    if (uVar11 == 0) break;
    uVar11 = uVar11 - 1;
    cVar1 = *pcVar9;
    pcVar9 = pcVar9 + 1;
  } while (cVar1 != '\0');
  if (~uVar11 - 1 < 5) {
    uVar11 = 0xffffffff;
    do {
      pcVar9 = pcVar18;
      if (uVar11 == 0) break;
      uVar11 = uVar11 - 1;
      pcVar9 = pcVar18 + 1;
      cVar1 = *pcVar18;
      pcVar18 = pcVar9;
    } while (cVar1 != '\0');
    uVar11 = ~uVar11;
    pcVar18 = pcVar9 + -uVar11;
    pcVar9 = (char *)&DAT_004567c0;
    for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
      *(undefined4 *)pcVar9 = *(undefined4 *)pcVar18;
      pcVar18 = pcVar18 + 4;
      pcVar9 = pcVar9 + 4;
    }
    for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
      *pcVar9 = *pcVar18;
      pcVar18 = pcVar18 + 1;
      pcVar9 = pcVar9 + 1;
    }
  }
  else {
    DAT_004567c0 = *(undefined4 *)pcVar18;
  }
  uVar11 = 0xffffffff;
  pcVar18 = param_1 + 0x800;
  do {
    pcVar9 = pcVar18;
    if (uVar11 == 0) break;
    uVar11 = uVar11 - 1;
    pcVar9 = pcVar18 + 1;
    cVar1 = *pcVar18;
    pcVar18 = pcVar9;
  } while (cVar1 != '\0');
  uVar11 = ~uVar11;
  pcVar18 = pcVar9 + -uVar11;
  pcVar9 = (char *)&DAT_00456810;
  for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
    *(undefined4 *)pcVar9 = *(undefined4 *)pcVar18;
    pcVar18 = pcVar18 + 4;
    pcVar9 = pcVar9 + 4;
  }
  for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
    *pcVar9 = *pcVar18;
    pcVar18 = pcVar18 + 1;
    pcVar9 = pcVar9 + 1;
  }
  uVar11 = 0xffffffff;
  pcVar18 = param_1 + 0xc00;
  do {
    pcVar9 = pcVar18;
    if (uVar11 == 0) break;
    uVar11 = uVar11 - 1;
    pcVar9 = pcVar18 + 1;
    cVar1 = *pcVar18;
    pcVar18 = pcVar9;
  } while (cVar1 != '\0');
  uVar11 = ~uVar11;
  pcVar18 = pcVar9 + -uVar11;
  pcVar9 = (char *)&DAT_004567cc;
  for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
    *(undefined4 *)pcVar9 = *(undefined4 *)pcVar18;
    pcVar18 = pcVar18 + 4;
    pcVar9 = pcVar9 + 4;
  }
  pcVar15 = param_1 + 0x1000;
  for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
    *pcVar9 = *pcVar18;
    pcVar18 = pcVar18 + 1;
    pcVar9 = pcVar9 + 1;
  }
  uVar11 = 0xffffffff;
  pcVar18 = pcVar15;
  do {
    if (uVar11 == 0) break;
    uVar11 = uVar11 - 1;
    cVar1 = *pcVar18;
    pcVar18 = pcVar18 + 1;
  } while (cVar1 != '\0');
  uVar12 = 0xffffffff;
  pcVar18 = pcVar15;
  do {
    if (uVar12 == 0) break;
    uVar12 = uVar12 - 1;
    cVar1 = *pcVar18;
    pcVar18 = pcVar18 + 1;
  } while (cVar1 != '\0');
  FUN_00412880(pcVar15,~uVar12 - 1,param_1 + 0x1400,~uVar11 - 1);
  FUN_0040aa66((byte *)s_generate_code_file__>__s_00430c68);
  iVar10 = FUN_00412170(&DAT_004567c0,DAT_00453088,(char *)&DAT_00456860);
  if (iVar10 != 0) {
    FUN_0040aa66((byte *)s_generate_code_file_failed__>__d_00430c44);
    return iVar10;
  }
  iVar10 = FUN_00412860(&DAT_00456860,&DAT_00452fe8);
  if (iVar10 == 0) {
    iVar10 = FUN_00412680(&DAT_00456860,(byte *)&DAT_00452fe8);
    if (iVar10 == 0) {
      iVar10 = RSA_size(iVar4);
      puVar16 = &DAT_00452fe8;
      for (iVar6 = 8; iVar6 != 0; iVar6 = iVar6 + -1) {
        *puVar16 = 0;
        puVar16 = puVar16 + 1;
      }
      pcVar9 = _malloc(0x1000);
      pcVar18 = pcVar9;
      for (iVar6 = 0x400; iVar6 != 0; iVar6 = iVar6 + -1) {
        pcVar18[0] = '\0';
        pcVar18[1] = '\0';
        pcVar18[2] = '\0';
        pcVar18[3] = '\0';
        pcVar18 = pcVar18 + 4;
      }
      pFVar3 = (FILE *)FUN_0040af91((LPCSTR)&DAT_00456860,&DAT_00430bf4);
      SHA256_Init(local_70);
      bVar2 = (byte)pFVar3->_flag;
      while ((bVar2 & 0x10) == 0) {
        uVar11 = FUN_0040ad29(pcVar9,1,0x1000,(int *)pFVar3);
        SHA256_Update(local_70,pcVar9,uVar11);
        bVar2 = (byte)pFVar3->_flag;
      }
      SHA256_Final(&DAT_00452fe8,local_70);
      FUN_0040af1b(pFVar3);
      FUN_0040aa97(pcVar9);
      uVar11 = iVar10 + 1;
      pcVar9 = _malloc(uVar11);
      pcVar18 = pcVar9;
      for (uVar12 = uVar11 >> 2; uVar12 != 0; uVar12 = uVar12 - 1) {
        pcVar18[0] = '\0';
        pcVar18[1] = '\0';
        pcVar18[2] = '\0';
        pcVar18[3] = '\0';
        pcVar18 = pcVar18 + 4;
      }
      for (uVar11 = uVar11 & 3; uVar11 != 0; uVar11 = uVar11 - 1) {
        *pcVar18 = '\0';
        pcVar18 = pcVar18 + 1;
      }
      uVar11 = RSA_private_encrypt(0x20,&DAT_00452fe8,pcVar9,iVar4,1);
      pFVar3 = (FILE *)FUN_0040af91((LPCSTR)&DAT_00456860,&DAT_00430bf0);
      FUN_0040ae11(pcVar9,1,uVar11,(int *)pFVar3);
      FUN_0040af1b(pFVar3);
      return 0;
    }
    FUN_0040aa66((byte *)s_update_code_file_hash_failed__>___00430bf8);
    return iVar10;
  }
  FUN_0040aa66((byte *)s_generate_code_file_hash_failed___00430c1c);
  return iVar10;
}



/* 00411120 FUN_00411120 */

undefined4 __cdecl FUN_00411120(LPCSTR param_1)

{
  byte bVar1;
  char cVar2;
  char *pcVar3;
  byte *pbVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  int iVar7;
  uint uVar8;
  uint uVar9;
  byte *pbVar10;
  byte *pbVar11;
  char *pcVar12;
  bool bVar13;
  undefined4 local_20c;
  undefined2 local_208;
  FILE *local_204;
  byte local_200 [256];
  byte local_100 [256];
  
  local_20c = DAT_00430ebc;
  local_208 = DAT_00430ec0;
  puVar5 = &DAT_004567c0;
  for (iVar7 = 0x24; iVar7 != 0; iVar7 = iVar7 + -1) {
    *puVar5 = 0;
    puVar5 = puVar5 + 1;
  }
  local_204 = (FILE *)FUN_0040af91(param_1,&DAT_0042fa6c);
  if (local_204 == (FILE *)0x0) {
    FUN_0040aa66((byte *)s_Can_not_locate_file__>__s_00430ea0);
    return 0xffffffff;
  }
  bVar1 = (byte)local_204->_flag;
  do {
    if ((bVar1 & 0x10) != 0) {
      FUN_0040af1b(local_204);
      return 0;
    }
    pbVar4 = local_200;
    for (iVar7 = 0x40; iVar7 != 0; iVar7 = iVar7 + -1) {
      pbVar4[0] = 0;
      pbVar4[1] = 0;
      pbVar4[2] = 0;
      pbVar4[3] = 0;
      pbVar4 = pbVar4 + 4;
    }
    pbVar4 = local_100;
    for (iVar7 = 0x40; iVar7 != 0; iVar7 = iVar7 + -1) {
      pbVar4[0] = 0;
      pbVar4[1] = 0;
      pbVar4[2] = 0;
      pbVar4[3] = 0;
      pbVar4 = pbVar4 + 4;
    }
    pcVar3 = FUN_004290a8((char *)local_200,0x100,&local_204->_ptr);
    puVar5 = DAT_00453088;
    if (pcVar3 != (char *)0x0) {
      uVar8 = 0xffffffff;
      pbVar4 = local_200;
      do {
        pbVar11 = pbVar4;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pbVar11 = pbVar4 + 1;
        bVar1 = *pbVar4;
        pbVar4 = pbVar11;
      } while (bVar1 != 0);
      uVar8 = ~uVar8;
      pbVar4 = pbVar11 + -uVar8;
      pbVar11 = local_100;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pbVar11 = *(undefined4 *)pbVar4;
        pbVar4 = pbVar4 + 4;
        pbVar11 = pbVar11 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pbVar11 = *pbVar4;
        pbVar4 = pbVar4 + 1;
        pbVar11 = pbVar11 + 1;
      }
      pbVar4 = (byte *)FUN_0042900c(local_200,(byte *)&local_20c);
      puVar5 = DAT_00453088;
      if ((pbVar4 != (byte *)0x0) && (*pbVar4 != 0x23)) {
        pbVar10 = &DAT_00430e9c;
        pbVar11 = pbVar4;
        do {
          bVar1 = *pbVar10;
          bVar13 = bVar1 < *pbVar11;
          if (bVar1 != *pbVar11) {
LAB_0041123e:
            iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
            goto LAB_00411243;
          }
          if (bVar1 == 0) break;
          bVar1 = pbVar10[1];
          bVar13 = bVar1 < pbVar11[1];
          if (bVar1 != pbVar11[1]) goto LAB_0041123e;
          pbVar10 = pbVar10 + 2;
          pbVar11 = pbVar11 + 2;
        } while (bVar1 != 0);
        iVar7 = 0;
LAB_00411243:
        if (iVar7 == 0) {
          puVar5 = (undefined4 *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
          DAT_004567c0 = *puVar5;
          puVar5 = DAT_00453088;
        }
        else {
          pcVar3 = s_model_00430e94;
          pbVar11 = pbVar4;
          do {
            bVar1 = *pbVar11;
            bVar13 = bVar1 < (byte)*pcVar3;
            if (bVar1 != *pcVar3) {
LAB_0041128e:
              iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
              goto LAB_00411293;
            }
            if (bVar1 == 0) break;
            bVar1 = pbVar11[1];
            bVar13 = bVar1 < (byte)pcVar3[1];
            if (bVar1 != pcVar3[1]) goto LAB_0041128e;
            pbVar11 = pbVar11 + 2;
            pcVar3 = pcVar3 + 2;
          } while (bVar1 != 0);
          iVar7 = 0;
LAB_00411293:
          if (iVar7 == 0) {
            uVar8 = FUN_0042900c((byte *)0x0,(byte *)&local_20c);
            uVar9 = 0xffffffff;
            pcVar3 = (char *)(uVar8 + 0x100);
            do {
              pcVar12 = pcVar3;
              if (uVar9 == 0) break;
              uVar9 = uVar9 - 1;
              pcVar12 = pcVar3 + 1;
              cVar2 = *pcVar3;
              pcVar3 = pcVar12;
            } while (cVar2 != '\0');
            uVar9 = ~uVar9;
            pcVar3 = pcVar12 + -uVar9;
            pcVar12 = (char *)&DAT_00456810;
            for (uVar8 = uVar9 >> 2; uVar8 != 0; uVar8 = uVar8 - 1) {
              *(undefined4 *)pcVar12 = *(undefined4 *)pcVar3;
              pcVar3 = pcVar3 + 4;
              pcVar12 = pcVar12 + 4;
            }
            for (uVar9 = uVar9 & 3; uVar9 != 0; uVar9 = uVar9 - 1) {
              *pcVar12 = *pcVar3;
              pcVar3 = pcVar3 + 1;
              pcVar12 = pcVar12 + 1;
            }
            FUN_00411610((char *)&DAT_00456810);
            puVar5 = DAT_00453088;
          }
          else {
            pcVar3 = s_control_00430e8c;
            pbVar11 = pbVar4;
            do {
              bVar1 = *pbVar11;
              bVar13 = bVar1 < (byte)*pcVar3;
              if (bVar1 != *pcVar3) {
LAB_0041130d:
                iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                goto LAB_00411312;
              }
              if (bVar1 == 0) break;
              bVar1 = pbVar11[1];
              bVar13 = bVar1 < (byte)pcVar3[1];
              if (bVar1 != pcVar3[1]) goto LAB_0041130d;
              pbVar11 = pbVar11 + 2;
              pcVar3 = pcVar3 + 2;
            } while (bVar1 != 0);
            iVar7 = 0;
LAB_00411312:
            if (iVar7 == 0) {
              pbVar4 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
              while (puVar5 = DAT_00453088, pbVar4 != (byte *)0x0) {
                pbVar10 = &DAT_00430e9c;
                pbVar11 = pbVar4;
                do {
                  bVar1 = *pbVar11;
                  bVar13 = bVar1 < *pbVar10;
                  if (bVar1 != *pbVar10) {
LAB_0041135e:
                    iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                    goto LAB_00411363;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pbVar11[1];
                  bVar13 = bVar1 < pbVar10[1];
                  if (bVar1 != pbVar10[1]) goto LAB_0041135e;
                  pbVar11 = pbVar11 + 2;
                  pbVar10 = pbVar10 + 2;
                } while (bVar1 != 0);
                iVar7 = 0;
LAB_00411363:
                if (iVar7 == 0) {
                  DAT_0045680c = DAT_0045680c | 0x10;
                }
                else {
                  pcVar3 = s_ignore_00430e84;
                  pbVar11 = pbVar4;
                  do {
                    bVar1 = *pbVar11;
                    bVar13 = bVar1 < (byte)*pcVar3;
                    if (bVar1 != *pcVar3) {
LAB_0041139e:
                      iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                      goto LAB_004113a3;
                    }
                    if (bVar1 == 0) break;
                    bVar1 = pbVar11[1];
                    bVar13 = bVar1 < (byte)pcVar3[1];
                    if (bVar1 != pcVar3[1]) goto LAB_0041139e;
                    pbVar11 = pbVar11 + 2;
                    pcVar3 = pcVar3 + 2;
                  } while (bVar1 != 0);
                  iVar7 = 0;
LAB_004113a3:
                  if (iVar7 != 0) {
                    pcVar3 = s_version_0042f138;
                    pbVar11 = pbVar4;
                    do {
                      bVar1 = *pbVar11;
                      bVar13 = bVar1 < (byte)*pcVar3;
                      if (bVar1 != *pcVar3) {
LAB_004113d2:
                        iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                        goto LAB_004113d7;
                      }
                      if (bVar1 == 0) break;
                      bVar1 = pbVar11[1];
                      bVar13 = bVar1 < (byte)pcVar3[1];
                      if (bVar1 != pcVar3[1]) goto LAB_004113d2;
                      pbVar11 = pbVar11 + 2;
                      pcVar3 = pcVar3 + 2;
                    } while (bVar1 != 0);
                    iVar7 = 0;
LAB_004113d7:
                    if (iVar7 == 0) {
                      DAT_0045680c = DAT_0045680c | 0x20;
                    }
                    else {
                      pcVar3 = s_model_00430e94;
                      do {
                        bVar1 = *pbVar4;
                        bVar13 = bVar1 < (byte)*pcVar3;
                        if (bVar1 != *pcVar3) {
LAB_0041140f:
                          iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                          goto LAB_00411414;
                        }
                        if (bVar1 == 0) break;
                        bVar1 = pbVar4[1];
                        bVar13 = bVar1 < (byte)pcVar3[1];
                        if (bVar1 != pcVar3[1]) goto LAB_0041140f;
                        pbVar4 = pbVar4 + 2;
                        pcVar3 = pcVar3 + 2;
                      } while (bVar1 != 0);
                      iVar7 = 0;
LAB_00411414:
                      if (iVar7 == 0) {
                        DAT_0045680c = DAT_0045680c | 0x40;
                      }
                    }
                  }
                }
                pbVar4 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
              }
            }
            else {
              pcVar3 = s_append_00430e7c;
              pbVar11 = pbVar4;
              do {
                bVar1 = *pbVar11;
                bVar13 = bVar1 < (byte)*pcVar3;
                if (bVar1 != *pcVar3) {
LAB_0041146d:
                  iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                  goto LAB_00411472;
                }
                if (bVar1 == 0) break;
                bVar1 = pbVar11[1];
                bVar13 = bVar1 < (byte)pcVar3[1];
                if (bVar1 != pcVar3[1]) goto LAB_0041146d;
                pbVar11 = pbVar11 + 2;
                pcVar3 = pcVar3 + 2;
              } while (bVar1 != 0);
              iVar7 = 0;
LAB_00411472:
              if (iVar7 == 0) {
                puVar5 = _malloc(0x1c);
                if (puVar5 == (undefined4 *)0x0) {
                  FUN_0040aa66((byte *)s_Allocate_memory_failed_00430e54);
                  return 0xffffffff;
                }
                puVar6 = puVar5;
                for (iVar7 = 7; iVar7 != 0; iVar7 = iVar7 + -1) {
                  *puVar6 = 0;
                  puVar6 = puVar6 + 1;
                }
                pbVar4 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                pcVar3 = s_no_priv_00430e74;
                do {
                  bVar1 = *pcVar3;
                  bVar13 = bVar1 < *pbVar4;
                  if (bVar1 != *pbVar4) {
LAB_004114d2:
                    iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                    goto LAB_004114d7;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pcVar3[1];
                  bVar13 = bVar1 < pbVar4[1];
                  if (bVar1 != pbVar4[1]) goto LAB_004114d2;
                  pcVar3 = pcVar3 + 2;
                  pbVar4 = pbVar4 + 2;
                } while (bVar1 != 0);
                iVar7 = 0;
LAB_004114d7:
                if (iVar7 == 0) {
                  *(undefined1 *)((int)puVar5 + 5) = 0;
                }
                else {
                  *(undefined1 *)((int)puVar5 + 5) = 1;
                }
                puVar6 = (undefined4 *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                *puVar5 = *puVar6;
                pcVar3 = (char *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                uVar8 = 0xffffffff;
                pcVar12 = pcVar3;
                do {
                  if (uVar8 == 0) break;
                  uVar8 = uVar8 - 1;
                  cVar2 = *pcVar12;
                  pcVar12 = pcVar12 + 1;
                } while (cVar2 != '\0');
                puVar6 = _malloc(~uVar8);
                uVar8 = 0xffffffff;
                puVar5[5] = puVar6;
                pcVar12 = pcVar3;
                do {
                  if (uVar8 == 0) break;
                  uVar8 = uVar8 - 1;
                  cVar2 = *pcVar12;
                  pcVar12 = pcVar12 + 1;
                } while (cVar2 != '\0');
                for (uVar9 = ~uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
                  *puVar6 = 0;
                  puVar6 = puVar6 + 1;
                }
                for (uVar8 = ~uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
                  *(undefined1 *)puVar6 = 0;
                  puVar6 = (undefined4 *)((int)puVar6 + 1);
                }
                uVar8 = 0xffffffff;
                do {
                  pcVar12 = pcVar3;
                  if (uVar8 == 0) break;
                  uVar8 = uVar8 - 1;
                  pcVar12 = pcVar3 + 1;
                  cVar2 = *pcVar3;
                  pcVar3 = pcVar12;
                } while (cVar2 != '\0');
                uVar8 = ~uVar8;
                pcVar3 = pcVar12 + -uVar8;
                pcVar12 = (char *)puVar5[5];
                for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
                  *(undefined4 *)pcVar12 = *(undefined4 *)pcVar3;
                  pcVar3 = pcVar3 + 4;
                  pcVar12 = pcVar12 + 4;
                }
                for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
                  *pcVar12 = *pcVar3;
                  pcVar3 = pcVar3 + 1;
                  pcVar12 = pcVar12 + 1;
                }
                if (DAT_00453088 != (undefined4 *)0x0) {
                  puVar5[6] = DAT_00453088;
                }
              }
              else {
                pcVar3 = s_output_00430e6c;
                do {
                  bVar1 = *pbVar4;
                  bVar13 = bVar1 < (byte)*pcVar3;
                  if (bVar1 != *pcVar3) {
LAB_00411596:
                    iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                    goto LAB_0041159b;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pbVar4[1];
                  bVar13 = bVar1 < (byte)pcVar3[1];
                  if (bVar1 != pcVar3[1]) goto LAB_00411596;
                  pbVar4 = pbVar4 + 2;
                  pcVar3 = pcVar3 + 2;
                } while (bVar1 != 0);
                iVar7 = 0;
LAB_0041159b:
                if (iVar7 == 0) {
                  pcVar3 = (char *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                  uVar8 = 0xffffffff;
                  do {
                    pcVar12 = pcVar3;
                    if (uVar8 == 0) break;
                    uVar8 = uVar8 - 1;
                    pcVar12 = pcVar3 + 1;
                    cVar2 = *pcVar3;
                    pcVar3 = pcVar12;
                  } while (cVar2 != '\0');
                  uVar8 = ~uVar8;
                  pcVar3 = pcVar12 + -uVar8;
                  pcVar12 = (char *)&DAT_00456860;
                  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
                    *(undefined4 *)pcVar12 = *(undefined4 *)pcVar3;
                    pcVar3 = pcVar3 + 4;
                    pcVar12 = pcVar12 + 4;
                  }
                  for (uVar8 = uVar8 & 3; puVar5 = DAT_00453088, uVar8 != 0; uVar8 = uVar8 - 1) {
                    *pcVar12 = *pcVar3;
                    pcVar3 = pcVar3 + 1;
                    pcVar12 = pcVar12 + 1;
                  }
                }
              }
            }
          }
        }
      }
    }
    DAT_00453088 = puVar5;
    bVar1 = (byte)local_204->_flag;
  } while( true );
}



/* 00411610 FUN_00411610 */

void __cdecl FUN_00411610(char *param_1)

{
  char cVar1;
  uint uVar2;
  char *pcVar3;
  
  uVar2 = 0xffffffff;
  pcVar3 = param_1;
  do {
    if (uVar2 == 0) break;
    uVar2 = uVar2 - 1;
    cVar1 = *pcVar3;
    pcVar3 = pcVar3 + 1;
  } while (cVar1 != '\0');
  uVar2 = ~uVar2;
  while (uVar2 = uVar2 - 1, uVar2 != 0) {
    if (('\0' < *param_1) && (*param_1 < ' ')) {
      *param_1 = '\0';
    }
    param_1 = param_1 + 1;
  }
  return;
}



/* 00411640 OPENSSL_Applink */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined * OPENSSL_Applink(void)

{
                    /* 0x11640  1  OPENSSL_Applink */
  if (DAT_00430b10 != 0) {
    _DAT_00430b18 = &LAB_00411740;
    _DAT_00430b1c = &LAB_00411750;
    _DAT_00430b20 = &LAB_00411760;
    _DAT_00430b24 = FUN_0040ac3e;
    _DAT_00430b28 = FUN_004290a8;
    _DAT_00430b2c = FUN_0040ad29;
    _DAT_00430b30 = FUN_0040ae11;
    _DAT_00430b34 = &LAB_004117b0;
    _DAT_00430b38 = &LAB_00411770;
    _DAT_00430b3c = FUN_0040af1b;
    _DAT_00430b40 = FUN_0040af91;
    _DAT_00430b44 = FUN_0040b1a5;
    _DAT_00430b48 = FUN_0040b04d;
    _DAT_00430b4c = FUN_0040d290;
    _DAT_00430b50 = &LAB_00411780;
    _DAT_00430b54 = &LAB_00411790;
    _DAT_00430b58 = &LAB_004117a0;
    _DAT_00430b5c = &LAB_0040f30d;
    _DAT_00430b60 = FUN_0040d476;
    _DAT_00430b64 = FUN_0040daba;
    _DAT_00430b68 = FUN_0040df2d;
    _DAT_00430b6c = FUN_0040dc67;
    DAT_00430b10 = 0;
  }
  return &DAT_00430b14;
}



/* 004117e0 FUN_004117e0 */

int __cdecl FUN_004117e0(LPCSTR param_1)

{
  byte bVar1;
  FILE *pFVar2;
  uint uVar3;
  int iVar4;
  char local_400 [1024];
  
  iVar4 = 0;
  pFVar2 = (FILE *)FUN_0040af91(param_1,(char *)&DAT_0042fa44);
  if (pFVar2 != (FILE *)0x0) {
    bVar1 = (byte)pFVar2->_flag;
    while ((bVar1 & 0x10) == 0) {
      uVar3 = FUN_0040ad29(local_400,1,0x400,(int *)pFVar2);
      iVar4 = iVar4 + uVar3;
      bVar1 = (byte)pFVar2->_flag;
    }
    FUN_0040af1b(pFVar2);
  }
  FUN_0040aa66((byte *)s_Pack_file__s_size____ld_00430ec4);
  return iVar4;
}



/* 00411850 FUN_00411850 */

undefined4 FUN_00411850(void)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  int iVar4;
  byte *pbVar5;
  byte *pbVar6;
  bool bVar7;
  
  if (DAT_00453088 == (byte *)0x0) {
    FUN_0040aa66((byte *)s_ERR_pt_append_is_null_00430ee0);
    return 0xffffffff;
  }
  iVar4 = 0x8090;
  pbVar6 = DAT_00453088;
  do {
    pbVar5 = &DAT_0042fb64;
    pbVar2 = pbVar6;
    do {
      bVar1 = *pbVar2;
      bVar7 = bVar1 < *pbVar5;
      if (bVar1 != *pbVar5) {
LAB_0041189f:
        iVar3 = (1 - (uint)bVar7) - (uint)(bVar7 != 0);
        goto LAB_004118a4;
      }
      if (bVar1 == 0) break;
      bVar1 = pbVar2[1];
      bVar7 = bVar1 < pbVar5[1];
      if (bVar1 != pbVar5[1]) goto LAB_0041189f;
      pbVar2 = pbVar2 + 2;
      pbVar5 = pbVar5 + 2;
    } while (bVar1 != 0);
    iVar3 = 0;
LAB_004118a4:
    if (iVar3 == 0) {
      pbVar6[0xc] = 0;
      pbVar6[0xd] = 0x80;
      pbVar6[0xe] = 0;
      pbVar6[0xf] = 0;
      pbVar6[0x10] = 0x90;
      pbVar6[0x11] = 0;
      pbVar6[0x12] = 0;
      pbVar6[0x13] = 0;
    }
    else {
      iVar3 = FUN_004117e0(*(LPCSTR *)(pbVar6 + 0x14));
      *(int *)(pbVar6 + 0x10) = iVar4;
      *(int *)(pbVar6 + 0xc) = iVar3 + 0x3c;
      iVar4 = iVar4 + iVar3 + 0x3c;
    }
    pbVar6 = *(byte **)(pbVar6 + 0x18);
    if (pbVar6 == (byte *)0x0) {
      return 0;
    }
  } while( true );
}



/* 004118e0 FUN_004118e0 */

undefined4 FUN_004118e0(void)

{
  int iVar1;
  
  iVar1 = FUN_00411d60();
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_create_xml_file_00430f50);
    return 0xffffffff;
  }
  iVar1 = FUN_00411970(DAT_00453090,DAT_0045308c,DAT_00453088);
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_fill_xml_content_00430f34);
    return 0xffffffff;
  }
  iVar1 = FUN_00411e50();
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_close_xml_file_00430f1c);
    return 0xffffffff;
  }
  iVar1 = FUN_00411e70();
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_trans_xml_file_fixed_size_00430ef8);
    return 0xffffffff;
  }
  return 0;
}



/* 00411970 FUN_00411970 */

undefined4 __cdecl FUN_00411970(undefined4 *param_1,int *param_2,int param_3)

{
  byte bVar1;
  char *pcVar2;
  byte *pbVar3;
  int iVar4;
  int iVar5;
  uint uVar6;
  uint uVar7;
  byte *pbVar8;
  byte *pbVar9;
  bool bVar10;
  int local_894;
  undefined4 local_890;
  undefined2 local_88c;
  undefined1 local_88a;
  undefined1 local_888 [128];
  byte local_808 [64];
  undefined1 auStack_7c8 [964];
  byte local_404 [1028];
  
  local_890 = DAT_00430ffc;
  local_88c = DAT_00431000;
  local_88a = DAT_00431002;
  local_894 = 0;
  if (((param_1 == (undefined4 *)0x0) || (param_2 == (int *)0x0)) || (param_3 == 0)) {
    FUN_0040aa66((byte *)s_Error_fill_xml_content_INV_ARGC_00430f68);
    return 0xffffffff;
  }
  if ((*(byte *)(param_1 + 3) & 0x10) != 0) {
    return 0;
  }
  do {
    pbVar3 = local_404;
    for (iVar5 = 0x100; iVar5 != 0; iVar5 = iVar5 + -1) {
      pbVar3[0] = 0;
      pbVar3[1] = 0;
      pbVar3[2] = 0;
      pbVar3[3] = 0;
      pbVar3 = pbVar3 + 4;
    }
    *pbVar3 = 0;
    pbVar3 = local_808;
    for (iVar5 = 0x100; iVar5 != 0; iVar5 = iVar5 + -1) {
      pbVar3[0] = 0;
      pbVar3[1] = 0;
      pbVar3[2] = 0;
      pbVar3[3] = 0;
      pbVar3 = pbVar3 + 4;
    }
    *pbVar3 = 0;
    pcVar2 = FUN_004290a8((char *)local_404,0x400,param_1);
    if (pcVar2 != (char *)0x0) {
      uVar6 = 0xffffffff;
      pbVar3 = local_404;
      do {
        pbVar8 = pbVar3;
        if (uVar6 == 0) break;
        uVar6 = uVar6 - 1;
        pbVar8 = pbVar3 + 1;
        bVar1 = *pbVar3;
        pbVar3 = pbVar8;
      } while (bVar1 != 0);
      uVar6 = ~uVar6;
      pbVar3 = pbVar8 + -uVar6;
      pbVar8 = local_808;
      for (uVar7 = uVar6 >> 2; uVar7 != 0; uVar7 = uVar7 - 1) {
        *(undefined4 *)pbVar8 = *(undefined4 *)pbVar3;
        pbVar3 = pbVar3 + 4;
        pbVar8 = pbVar8 + 4;
      }
      for (uVar6 = uVar6 & 3; uVar6 != 0; uVar6 = uVar6 - 1) {
        *pbVar8 = *pbVar3;
        pbVar3 = pbVar3 + 1;
        pbVar8 = pbVar8 + 1;
      }
      pbVar3 = (byte *)FUN_0042900c(local_404,(byte *)&local_890);
      if (pbVar3 != (byte *)0x0) {
        pbVar9 = &DAT_00430ff4;
        pbVar8 = pbVar3;
        do {
          bVar1 = *pbVar8;
          bVar10 = bVar1 < *pbVar9;
          if (bVar1 != *pbVar9) {
LAB_00411a8e:
            iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
            goto LAB_00411a93;
          }
          if (bVar1 == 0) break;
          bVar1 = pbVar8[1];
          bVar10 = bVar1 < pbVar9[1];
          if (bVar1 != pbVar9[1]) goto LAB_00411a8e;
          pbVar8 = pbVar8 + 2;
          pbVar9 = pbVar9 + 2;
        } while (bVar1 != 0);
        iVar5 = 0;
LAB_00411a93:
        if (iVar5 == 0) {
          pbVar3 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_890);
          local_894 = 0;
          FUN_00411cf0(pbVar3,&local_894);
          FUN_0042900c((byte *)0x0,(byte *)&local_890);
        }
        else {
          pcVar2 = s_<SIZE_00430fec;
          pbVar8 = pbVar3;
          do {
            bVar1 = *pbVar8;
            bVar10 = bVar1 < (byte)*pcVar2;
            if (bVar1 != *pcVar2) {
LAB_00411af5:
              iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
              goto LAB_00411afa;
            }
            if (bVar1 == 0) break;
            bVar1 = pbVar8[1];
            bVar10 = bVar1 < (byte)pcVar2[1];
            if (bVar1 != pcVar2[1]) goto LAB_00411af5;
            pbVar8 = pbVar8 + 2;
            pcVar2 = pcVar2 + 2;
          } while (bVar1 != 0);
          iVar5 = 0;
LAB_00411afa:
          if ((iVar5 == 0) && (local_894 != 0)) {
            uVar6 = FUN_0040b2c7(local_888,&DAT_00430fe8);
            iVar5 = FUN_0040b2c7(local_808,(byte *)s__12c<SIZE>_16c_s_00430fd4);
            if ((uVar6 < 0x40) && (iVar4 = 0x40 - uVar6, iVar4 != 0)) {
              pbVar3 = local_808 + iVar5;
              do {
                FUN_0040b2c7(pbVar3,&DAT_0042fce0);
                pbVar3 = pbVar3 + 1;
                iVar4 = iVar4 + -1;
              } while (iVar4 != 0);
            }
            FUN_0040b2c7(auStack_7c8 + (iVar5 - uVar6),(byte *)s_<_SIZE>_00430fc8);
          }
          else {
            pcVar2 = s_<OFFSET_00430fc0;
            pbVar8 = pbVar3;
            do {
              bVar1 = *pbVar8;
              bVar10 = bVar1 < (byte)*pcVar2;
              if (bVar1 != *pcVar2) {
LAB_00411bba:
                iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
                goto LAB_00411bbf;
              }
              if (bVar1 == 0) break;
              bVar1 = pbVar8[1];
              bVar10 = bVar1 < (byte)pcVar2[1];
              if (bVar1 != pcVar2[1]) goto LAB_00411bba;
              pbVar8 = pbVar8 + 2;
              pcVar2 = pcVar2 + 2;
            } while (bVar1 != 0);
            iVar5 = 0;
LAB_00411bbf:
            if ((iVar5 == 0) && (local_894 != 0)) {
              uVar6 = FUN_0040b2c7(local_888,&DAT_00430fe8);
              iVar5 = FUN_0040b2c7(local_808,(byte *)s__12c<OFFSET>_14c_s_00430fac);
              if ((uVar6 < 0x40) && (iVar4 = 0x40 - uVar6, iVar4 != 0)) {
                pbVar3 = local_808 + iVar5;
                do {
                  FUN_0040b2c7(pbVar3,&DAT_0042fce0);
                  pbVar3 = pbVar3 + 1;
                  iVar4 = iVar4 + -1;
                } while (iVar4 != 0);
              }
              FUN_0040b2c7(auStack_7c8 + (iVar5 - uVar6),(byte *)s_<_OFFSET>_00430fa0);
            }
            else {
              pbVar8 = &DAT_00430f98;
              do {
                bVar1 = *pbVar3;
                bVar10 = bVar1 < *pbVar8;
                if (bVar1 != *pbVar8) {
LAB_00411c74:
                  iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
                  goto LAB_00411c79;
                }
                if (bVar1 == 0) break;
                bVar1 = pbVar3[1];
                bVar10 = bVar1 < pbVar8[1];
                if (bVar1 != pbVar8[1]) goto LAB_00411c74;
                pbVar3 = pbVar3 + 2;
                pbVar8 = pbVar8 + 2;
              } while (bVar1 != 0);
              iVar5 = 0;
LAB_00411c79:
              if (iVar5 == 0) {
                FUN_0040ac3e(param_2,(byte *)s_<_ROOT>_00430f8c);
                return 0;
              }
            }
          }
        }
        FUN_0040ac3e(param_2,local_808);
      }
    }
    if ((*(byte *)(param_1 + 3) & 0x10) != 0) {
      return 0;
    }
  } while( true );
}



/* 00411cf0 FUN_00411cf0 */

byte * __cdecl FUN_00411cf0(byte *param_1,undefined4 *param_2)

{
  byte *pbVar1;
  byte bVar2;
  byte *pbVar3;
  byte *pbVar4;
  int iVar5;
  byte *pbVar6;
  bool bVar7;
  
  pbVar3 = DAT_00453088;
  pbVar1 = param_1;
  if (DAT_00453088 != (byte *)0x0) {
    for (; pbVar4 = pbVar3, pbVar6 = param_1, pbVar1 != (byte *)0x0; pbVar1 = *(byte **)pbVar1) {
      do {
        bVar2 = *pbVar4;
        bVar7 = bVar2 < *pbVar6;
        if (bVar2 != *pbVar6) {
LAB_00411d33:
          iVar5 = (1 - (uint)bVar7) - (uint)(bVar7 != 0);
          goto LAB_00411d38;
        }
        if (bVar2 == 0) break;
        bVar2 = pbVar4[1];
        bVar7 = bVar2 < pbVar6[1];
        if (bVar2 != pbVar6[1]) goto LAB_00411d33;
        pbVar4 = pbVar4 + 2;
        pbVar6 = pbVar6 + 2;
      } while (bVar2 != 0);
      iVar5 = 0;
LAB_00411d38:
      if (iVar5 == 0) {
        *param_2 = pbVar3;
        return pbVar3;
      }
      pbVar1 = pbVar3 + 0x18;
      pbVar3 = *(byte **)pbVar1;
    }
  }
  return (byte *)0x0;
}



/* 00411d60 FUN_00411d60 */

undefined4 FUN_00411d60(void)

{
  char cVar1;
  int iVar2;
  uint uVar3;
  uint uVar4;
  char *pcVar5;
  char *pcVar6;
  char *pcVar7;
  char local_80 [128];
  
  if (s_download_xml_00430b70[0] == '\0') {
    FUN_0040aa66((byte *)s_Error__create_xml_file__xml_file_00431034);
    return 0xffffffff;
  }
  DAT_00453090 = FUN_0040af91(s_download_xml_00430b70,&DAT_0042fa6c);
  if (DAT_00453090 != 0) {
    pcVar5 = local_80;
    for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
      pcVar5[0] = '\0';
      pcVar5[1] = '\0';
      pcVar5[2] = '\0';
      pcVar5[3] = '\0';
      pcVar5 = pcVar5 + 4;
    }
    uVar3 = 0xffffffff;
    pcVar5 = s_download_xml_00430b70;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = local_80;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    uVar3 = 0xffffffff;
    pcVar5 = (char *)&DAT_00431028;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    iVar2 = -1;
    pcVar5 = local_80;
    do {
      pcVar6 = pcVar5;
      if (iVar2 == 0) break;
      iVar2 = iVar2 + -1;
      pcVar6 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar6;
    } while (cVar1 != '\0');
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = pcVar6 + -1;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    DAT_0045308c = FUN_0040af91(local_80,&DAT_00431030);
    if (DAT_0045308c != 0) {
      return 0;
    }
    DAT_0045308c = 0;
  }
  FUN_0040aa66((byte *)s_Error_Can_not_locate_file__>__s_00431004);
  return 0xffffffff;
}



/* 00411e50 FUN_00411e50 */

undefined4 FUN_00411e50(void)

{
  FUN_0040af1b(DAT_00453090);
  FUN_0040af1b(DAT_0045308c);
  return 0;
}



/* 00411e70 FUN_00411e70 */

undefined4 FUN_00411e70(void)

{
  char cVar1;
  int iVar2;
  uint uVar3;
  uint uVar4;
  char *pcVar5;
  char *pcVar6;
  char *pcVar7;
  char local_80 [128];
  
  pcVar5 = local_80;
  for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
    pcVar5[0] = '\0';
    pcVar5[1] = '\0';
    pcVar5[2] = '\0';
    pcVar5[3] = '\0';
    pcVar5 = pcVar5 + 4;
  }
  uVar3 = 0xffffffff;
  pcVar5 = s_download_xml_00430b70;
  do {
    pcVar7 = pcVar5;
    if (uVar3 == 0) break;
    uVar3 = uVar3 - 1;
    pcVar7 = pcVar5 + 1;
    cVar1 = *pcVar5;
    pcVar5 = pcVar7;
  } while (cVar1 != '\0');
  uVar3 = ~uVar3;
  pcVar5 = pcVar7 + -uVar3;
  pcVar7 = local_80;
  for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
    *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
    pcVar5 = pcVar5 + 4;
    pcVar7 = pcVar7 + 4;
  }
  for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
    *pcVar7 = *pcVar5;
    pcVar5 = pcVar5 + 1;
    pcVar7 = pcVar7 + 1;
  }
  uVar3 = 0xffffffff;
  pcVar5 = (char *)&DAT_00431028;
  do {
    pcVar7 = pcVar5;
    if (uVar3 == 0) break;
    uVar3 = uVar3 - 1;
    pcVar7 = pcVar5 + 1;
    cVar1 = *pcVar5;
    pcVar5 = pcVar7;
  } while (cVar1 != '\0');
  uVar3 = ~uVar3;
  iVar2 = -1;
  pcVar5 = local_80;
  do {
    pcVar6 = pcVar5;
    if (iVar2 == 0) break;
    iVar2 = iVar2 + -1;
    pcVar6 = pcVar5 + 1;
    cVar1 = *pcVar5;
    pcVar5 = pcVar6;
  } while (cVar1 != '\0');
  pcVar5 = pcVar7 + -uVar3;
  pcVar7 = pcVar6 + -1;
  for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
    *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
    pcVar5 = pcVar5 + 4;
    pcVar7 = pcVar7 + 4;
  }
  for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
    *pcVar7 = *pcVar5;
    pcVar5 = pcVar5 + 1;
    pcVar7 = pcVar7 + 1;
  }
  DAT_00453090 = (int *)FUN_0040af91(local_80,&DAT_0042fa6c);
  if (DAT_00453090 != (int *)0x0) {
    pcVar5 = local_80;
    for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
      pcVar5[0] = '\0';
      pcVar5[1] = '\0';
      pcVar5[2] = '\0';
      pcVar5[3] = '\0';
      pcVar5 = pcVar5 + 4;
    }
    uVar3 = 0xffffffff;
    pcVar5 = s_download_xml_00430b70;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = local_80;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    uVar3 = 0xffffffff;
    pcVar5 = (char *)&DAT_00431064;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    iVar2 = -1;
    pcVar5 = local_80;
    do {
      pcVar6 = pcVar5;
      if (iVar2 == 0) break;
      iVar2 = iVar2 + -1;
      pcVar6 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar6;
    } while (cVar1 != '\0');
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = pcVar6 + -1;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    DAT_0045308c = (int *)FUN_0040af91(local_80,&DAT_00431030);
    if (DAT_0045308c != (int *)0x0) {
      iVar2 = FUN_00411fe0(DAT_00453090,DAT_0045308c);
      if (-1 < iVar2) {
        FUN_00411e50();
        return 0;
      }
      FUN_0040aa66((byte *)s_Error_trans_xml_file_fixed_size_00430ef8);
      return 0xffffffff;
    }
    DAT_0045308c = (int *)0x0;
  }
  FUN_0040aa66((byte *)s_Error_Can_not_locate_file__>__s_00431004);
  return 0xffffffff;
}



/* 00411fe0 FUN_00411fe0 */

undefined4 __cdecl FUN_00411fe0(int *param_1,int *param_2)

{
  uint uVar1;
  byte *pbVar2;
  int iVar3;
  uint uVar4;
  uint uVar5;
  uint uVar6;
  byte *pbVar7;
  byte *pbVar8;
  byte local_404 [1028];
  
  uVar6 = 0x7fc4;
  if ((*(byte *)(param_1 + 3) & 0x10) == 0) {
    do {
      if (uVar6 == 0) {
        return 0;
      }
      pbVar2 = local_404;
      for (iVar3 = 0x100; iVar3 != 0; iVar3 = iVar3 + -1) {
        pbVar2[0] = 0;
        pbVar2[1] = 0;
        pbVar2[2] = 0;
        pbVar2[3] = 0;
        pbVar2 = pbVar2 + 4;
      }
      *pbVar2 = 0;
      uVar1 = FUN_0040ad29((char *)local_404,1,0x400,param_1);
      uVar5 = uVar6;
      if ((uVar6 < uVar1) || (uVar5 = uVar1, uVar1 != 0x400)) {
        uVar1 = uVar5 + 1;
        pbVar2 = _malloc(uVar1);
        if (pbVar2 == (byte *)0x0) goto LAB_004120e1;
        pbVar7 = pbVar2;
        for (uVar4 = uVar1 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
          pbVar7[0] = 0;
          pbVar7[1] = 0;
          pbVar7[2] = 0;
          pbVar7[3] = 0;
          pbVar7 = pbVar7 + 4;
        }
        for (uVar1 = uVar1 & 3; uVar1 != 0; uVar1 = uVar1 - 1) {
          *pbVar7 = 0;
          pbVar7 = pbVar7 + 1;
        }
        pbVar7 = local_404;
        pbVar8 = pbVar2;
        for (uVar1 = uVar5 >> 2; uVar1 != 0; uVar1 = uVar1 - 1) {
          *(undefined4 *)pbVar8 = *(undefined4 *)pbVar7;
          pbVar7 = pbVar7 + 4;
          pbVar8 = pbVar8 + 4;
        }
        for (uVar1 = uVar5 & 3; uVar1 != 0; uVar1 = uVar1 - 1) {
          *pbVar8 = *pbVar7;
          pbVar7 = pbVar7 + 1;
          pbVar8 = pbVar8 + 1;
        }
        FUN_0040ac3e(param_2,pbVar2);
        iVar3 = -uVar5;
        FUN_0040aa97(pbVar2);
      }
      else {
        FUN_0040ac3e(param_2,local_404);
        iVar3 = -0x400;
      }
      uVar6 = uVar6 + iVar3;
    } while ((*(byte *)(param_1 + 3) & 0x10) == 0);
    if (uVar6 == 0) {
      return 0;
    }
  }
  uVar5 = uVar6 + 1;
  pbVar2 = _malloc(uVar5);
  if (pbVar2 != (byte *)0x0) {
    pbVar7 = pbVar2;
    for (uVar1 = uVar5 >> 2; uVar1 != 0; uVar1 = uVar1 - 1) {
      pbVar7[0] = 0;
      pbVar7[1] = 0;
      pbVar7[2] = 0;
      pbVar7[3] = 0;
      pbVar7 = pbVar7 + 4;
    }
    for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
      *pbVar7 = 0;
      pbVar7 = pbVar7 + 1;
    }
    pbVar7 = pbVar2;
    for (uVar5 = uVar6 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      pbVar7[0] = 0x20;
      pbVar7[1] = 0x20;
      pbVar7[2] = 0x20;
      pbVar7[3] = 0x20;
      pbVar7 = pbVar7 + 4;
    }
    for (uVar6 = uVar6 & 3; uVar6 != 0; uVar6 = uVar6 - 1) {
      *pbVar7 = 0x20;
      pbVar7 = pbVar7 + 1;
    }
    FUN_0040ac3e(param_2,pbVar2);
    FUN_0040aa97(pbVar2);
    return 0;
  }
LAB_004120e1:
  FUN_0040aa66((byte *)s_Error_trans_xml_file_fixed_size__0043106c);
  return 0xffffffff;
}



/* 00412150 RSA_private_encrypt */

void RSA_private_encrypt(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412150. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  RSA_private_encrypt();
  return;
}



/* 00412156 SHA256_Final */

void SHA256_Final(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412156. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  SHA256_Final();
  return;
}



/* 0041215c SHA256_Update */

void SHA256_Update(void)

{
                    /* WARNING: Could not recover jumptable at 0x0041215c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  SHA256_Update();
  return;
}



/* 00412162 SHA256_Init */

void SHA256_Init(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412162. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  SHA256_Init();
  return;
}



/* 00412168 RSA_size */

void RSA_size(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412168. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  RSA_size();
  return;
}



/* 00412170 FUN_00412170 */

void __cdecl FUN_00412170(undefined4 *param_1,void *param_2,char *param_3)

{
  FUN_00412190(param_2,param_1,param_2,param_3);
  return;
}



/* 00412190 FUN_00412190 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined4 __thiscall FUN_00412190(void *this,undefined4 *param_1,char *param_2,char *param_3)

{
  char *pcVar1;
  FILE *pFVar2;
  uint uVar3;
  int iVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  undefined4 local_20;
  undefined4 local_1c;
  undefined4 local_18;
  undefined4 local_14;
  byte local_10 [16];
  
  local_20 = 0;
  local_1c = 0;
  local_18 = 0;
  local_14 = 0;
  local_10[0] = 9;
  local_10[1] = 0x29;
  local_10[2] = 0x10;
  local_10[3] = 0x94;
  local_10[4] = 9;
  local_10[5] = 0x29;
  local_10[6] = 0x10;
  local_10[7] = 0x94;
  local_10[8] = 9;
  local_10[9] = 0x29;
  local_10[10] = 0x10;
  local_10[0xb] = 0x94;
  local_10[0xc] = 9;
  local_10[0xd] = 0x29;
  local_10[0xe] = 0x10;
  local_10[0xf] = 0x94;
  pFVar2 = (FILE *)FUN_004128c0(this,param_3,1);
  if (pFVar2 == (FILE *)0xffffffff) {
    FUN_0040aa66((byte *)s_Open_output_codefile__s_failed_004310e4);
    return 0xfffffff4;
  }
  puVar5 = param_1;
  puVar6 = &DAT_00453098;
  for (iVar4 = 0x24; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar6 = *puVar5;
    puVar5 = puVar5 + 1;
    puVar6 = puVar6 + 1;
  }
  _DAT_004530e0 = 0x90;
  for (pcVar1 = param_2; pcVar1 != (char *)0x0; pcVar1 = *(char **)(pcVar1 + 0x18)) {
    _DAT_004530e0 = _DAT_004530e0 + *(int *)(pcVar1 + 0xc);
  }
  FUN_0040aa66((byte *)s_Code_size____u_004310d4);
  DAT_0045309c = DAT_004310c8;
  _DAT_004530a0 = DAT_004310cc;
  FUN_00412bd0((byte *)&DAT_00453098,(byte *)0x0,0x70,local_10,&local_20,1);
  FUN_00412b10(s_Encrypted_Header__004310b4,0x453098,0x70);
  uVar3 = FUN_004129c0((int *)pFVar2,(char *)&DAT_00453098,0x90);
  if (uVar3 != 0xffffffff) {
    iVar4 = FUN_004122e0(param_1,param_2,(int *)pFVar2,*param_1);
    if (iVar4 != 0) {
      FUN_0040aa66((byte *)s_Add_append_data_failed_0043109c);
      FUN_0040d1bb(0xffffffff);
    }
    FUN_00412960(pFVar2);
    return 0;
  }
  FUN_00412960(pFVar2);
  return 0xfffffff0;
}



/* 004122e0 FUN_004122e0 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

int __cdecl FUN_004122e0(undefined4 param_1,char *param_2,int *param_3,undefined4 param_4)

{
  FILE *pFVar1;
  uint uVar2;
  int iVar3;
  void *this;
  byte *pbVar4;
  undefined4 local_450;
  undefined4 local_44c;
  undefined4 local_448;
  undefined4 local_444;
  undefined4 local_440;
  undefined4 local_43c;
  undefined4 local_438;
  undefined4 local_434;
  byte local_430 [4];
  char local_42c [4];
  char local_428 [4];
  char local_424 [4];
  byte local_420 [32];
  byte local_400 [1024];
  
  while( true ) {
    if (param_2 == (char *)0x0) {
      s_reserved_mtk_inc_0043117c[0xc] = (char)s_reserved_mtk_inc_0043117c._12_4_;
      s_reserved_mtk_inc_0043117c[0xd] = SUB41(s_reserved_mtk_inc_0043117c._12_4_,1);
      s_reserved_mtk_inc_0043117c[0xe] = SUB41(s_reserved_mtk_inc_0043117c._12_4_,2);
      s_reserved_mtk_inc_0043117c[0xf] = SUB41(s_reserved_mtk_inc_0043117c._12_4_,3);
      s_reserved_mtk_inc_0043117c[8] = (char)s_reserved_mtk_inc_0043117c._8_4_;
      s_reserved_mtk_inc_0043117c[9] = SUB41(s_reserved_mtk_inc_0043117c._8_4_,1);
      s_reserved_mtk_inc_0043117c[10] = SUB41(s_reserved_mtk_inc_0043117c._8_4_,2);
      s_reserved_mtk_inc_0043117c[0xb] = SUB41(s_reserved_mtk_inc_0043117c._8_4_,3);
      s_reserved_mtk_inc_0043117c[4] = (char)s_reserved_mtk_inc_0043117c._4_4_;
      s_reserved_mtk_inc_0043117c[5] = SUB41(s_reserved_mtk_inc_0043117c._4_4_,1);
      s_reserved_mtk_inc_0043117c[6] = SUB41(s_reserved_mtk_inc_0043117c._4_4_,2);
      s_reserved_mtk_inc_0043117c[7] = SUB41(s_reserved_mtk_inc_0043117c._4_4_,3);
      s_reserved_mtk_inc_0043117c[0] = (char)s_reserved_mtk_inc_0043117c._0_4_;
      s_reserved_mtk_inc_0043117c[1] = SUB41(s_reserved_mtk_inc_0043117c._0_4_,1);
      s_reserved_mtk_inc_0043117c[2] = SUB41(s_reserved_mtk_inc_0043117c._0_4_,2);
      s_reserved_mtk_inc_0043117c[3] = SUB41(s_reserved_mtk_inc_0043117c._0_4_,3);
      return 0;
    }
    local_440 = 0;
    local_43c = 0;
    local_438 = 0;
    local_434 = 0;
    local_450 = param_4;
    local_44c = param_4;
    local_448 = param_4;
    local_444 = param_4;
    if (DAT_004540fc != 0) {
      MD5_Init(&DAT_004540a0);
      MD5_Update(&DAT_004540a0,DAT_004540fc,DAT_00454104);
      MD5_Final(&local_450,&DAT_004540a0);
    }
    if (DAT_00454100 != 0) {
      MD5_Init(&DAT_004540a0);
      MD5_Update(&DAT_004540a0,DAT_00454100,DAT_00454108);
      MD5_Final(&local_440,&DAT_004540a0);
    }
    pbVar4 = local_420;
    for (iVar3 = 8; iVar3 != 0; iVar3 = iVar3 + -1) {
      pbVar4[0] = 0;
      pbVar4[1] = 0;
      pbVar4[2] = 0;
      pbVar4[3] = 0;
      pbVar4 = pbVar4 + 4;
    }
    local_430[0] = s_reserved_mtk_inc_0043117c[0];
    local_430[1] = s_reserved_mtk_inc_0043117c[1];
    local_430[2] = s_reserved_mtk_inc_0043117c[2];
    local_430[3] = s_reserved_mtk_inc_0043117c[3];
    local_42c[0] = s_reserved_mtk_inc_0043117c[4];
    local_42c[1] = s_reserved_mtk_inc_0043117c[5];
    local_42c[2] = s_reserved_mtk_inc_0043117c[6];
    local_42c[3] = s_reserved_mtk_inc_0043117c[7];
    local_428[0] = s_reserved_mtk_inc_0043117c[8];
    local_428[1] = s_reserved_mtk_inc_0043117c[9];
    local_428[2] = s_reserved_mtk_inc_0043117c[10];
    local_428[3] = s_reserved_mtk_inc_0043117c[0xb];
    local_424[0] = s_reserved_mtk_inc_0043117c[0xc];
    local_424[1] = s_reserved_mtk_inc_0043117c[0xd];
    local_424[2] = s_reserved_mtk_inc_0043117c[0xe];
    local_424[3] = s_reserved_mtk_inc_0043117c[0xf];
    iVar3 = FUN_004125f0((void *)s_reserved_mtk_inc_0043117c._12_4_,*(char **)(param_2 + 0x14),
                         (undefined4 *)local_420);
    if (iVar3 != 0) {
      return iVar3;
    }
    FUN_00412b10(s_Append_Header_Key_00431168,(int)&local_450,0x10);
    FUN_00412bd0(local_430,(byte *)0x0,0x10,(byte *)&local_450,&local_440,1);
    FUN_00412bd0(local_420,(byte *)0x0,0x20,(byte *)&local_450,&local_440,1);
    iVar3 = FUN_00412a00(*(LPCSTR *)(param_2 + 0x14));
    _DAT_00454098 = 0x4be7c72;
    pFVar1 = (FILE *)FUN_004128c0(this,*(char **)(param_2 + 0x14),0);
    if (pFVar1 == (FILE *)0xffffffff) {
      FUN_0040aa66((byte *)s_Open_append_data_file__s_failed_00431144);
      return -0xc;
    }
    *(int *)(param_2 + 0xc) = iVar3;
    uVar2 = FUN_004129c0(param_3,param_2,4);
    if ((((uVar2 == 0xffffffff) ||
         (uVar2 = FUN_004129c0(param_3,param_2 + 5,4), uVar2 == 0xffffffff)) ||
        (uVar2 = FUN_004129c0(param_3,param_2 + 0xc,4), uVar2 == 0xffffffff)) ||
       ((uVar2 = FUN_004129c0(param_3,(char *)local_430,0x10), uVar2 == 0xffffffff ||
        (uVar2 = FUN_004129c0(param_3,(char *)local_420,0x20), uVar2 == 0xffffffff)))) break;
    uVar2 = FUN_004129b0((int)pFVar1);
    while (uVar2 == 0) {
      uVar2 = FUN_00412990((int *)pFVar1,(char *)local_400,0x400);
      if (uVar2 != 0xffffffff) {
        if (param_2[5] != '\0') {
          FUN_00412bd0(local_400,(byte *)0x0,uVar2,(byte *)&local_450,&local_440,1);
        }
        uVar2 = FUN_004129c0(param_3,(char *)local_400,uVar2);
        if (uVar2 == 0xffffffff) {
          FUN_0040aa66((byte *)s_Copy_append_data__s_failed_00431104);
          return -0x10;
        }
      }
      uVar2 = FUN_004129b0((int)pFVar1);
    }
    FUN_00412960(pFVar1);
    param_2 = *(char **)(param_2 + 0x18);
  }
  FUN_0040aa66((byte *)s_Write_append_data__s_type_failed_00431120);
  return -0x10;
}



/* 004125f0 FUN_004125f0 */

undefined4 __thiscall FUN_004125f0(void *this,char *param_1,undefined4 *param_2)

{
  FILE *pFVar1;
  uint uVar2;
  int iVar3;
  
  pFVar1 = (FILE *)FUN_004128c0(this,param_1,0);
  if (pFVar1 != (FILE *)0xffffffff) {
    FUN_00412f00();
    uVar2 = FUN_004129b0((int)pFVar1);
    while (uVar2 == 0) {
      iVar3 = FUN_00412990((int *)pFVar1,(char *)&DAT_00453098,0x1000);
      if (iVar3 != -1) {
        FUN_00412f10(&DAT_00453098,iVar3);
      }
      uVar2 = FUN_004129b0((int)pFVar1);
    }
    FUN_00412f30(param_2);
    FUN_00412960(pFVar1);
    return 0;
  }
  FUN_0040aa66((byte *)s_Open_file_for_hash_module_failed_00431190);
  return 0xfffffff4;
}



/* 00412680 FUN_00412680 */

void __cdecl FUN_00412680(void *param_1,byte *param_2)

{
  FUN_004126a0(param_1,param_1,param_2);
  return;
}



/* 004126a0 FUN_004126a0 */

undefined4 __thiscall FUN_004126a0(void *this,char *param_1,byte *param_2)

{
  FILE *pFVar1;
  FILE *pFVar2;
  uint uVar3;
  int iVar4;
  void *this_00;
  undefined4 local_20;
  undefined4 local_1c;
  undefined4 local_18;
  undefined4 local_14;
  undefined4 local_10;
  undefined4 local_c;
  undefined4 local_8;
  undefined4 local_4;
  
  local_20 = 0;
  local_1c = 0;
  local_18 = 0;
  local_14 = 0;
  local_10 = 0x94102909;
  local_c = 0x94102909;
  local_8 = 0x94102909;
  local_4 = 0x94102909;
  pFVar1 = (FILE *)FUN_004128c0(this,param_1,0);
  if (pFVar1 == (FILE *)0xffffffff) {
    FUN_0040aa66((byte *)s_Open__s_failed_0043123c);
    return 0xfffffff4;
  }
  pFVar2 = (FILE *)FUN_004128c0(this_00,s__mnt_ms__up_sig_tmp_00431228,1);
  if (pFVar2 != (FILE *)0xffffffff) {
    FUN_00412990((int *)pFVar1,(char *)&DAT_00453098,0x70);
    FUN_004129c0((int *)pFVar2,(char *)&DAT_00453098,0x70);
    FUN_00412b10(s_Source_digest__004311fc,(int)param_2,0x20);
    FUN_00412bd0(param_2,(byte *)0x0,0x20,(byte *)&local_10,&local_20,1);
    FUN_00412b10(s_Encrypted_digest__004311e8,(int)param_2,0x20);
    FUN_004129c0((int *)pFVar2,(char *)param_2,0x20);
    FUN_00412990((int *)pFVar1,(char *)&DAT_00453098,0x20);
    uVar3 = FUN_004129b0((int)pFVar1);
    while (uVar3 == 0) {
      uVar3 = FUN_00412990((int *)pFVar1,(char *)&DAT_00453098,0x1000);
      if (uVar3 != 0xffffffff) {
        FUN_004129c0((int *)pFVar2,(char *)&DAT_00453098,uVar3);
        FUN_0040aa66(&DAT_004311e4);
      }
      uVar3 = FUN_004129b0((int)pFVar1);
    }
    FUN_0040aa66(&DAT_0042fcbc);
    FUN_00412960(pFVar1);
    FUN_00412960(pFVar2);
    iVar4 = FUN_00412a70();
    if (iVar4 != -1) {
      iVar4 = FUN_00412ab0();
      if (iVar4 != -1) {
        return 0;
      }
      FUN_0040aa66((byte *)s_Rename_file__s_failed_004311b4);
      return 0xfffffff2;
    }
    FUN_0040aa66((byte *)s_Remove_file__s_failed_004311cc);
    return 0xfffffff3;
  }
  FUN_0040aa66((byte *)s_open__up_sig_tmp_failed_0043120c);
  return 0xfffffff4;
}



/* 00412860 FUN_00412860 */

void __cdecl FUN_00412860(void *param_1,undefined4 *param_2)

{
  FUN_004125f0(param_1,param_1,param_2);
  return;
}



/* 00412880 FUN_00412880 */

undefined4 __cdecl
FUN_00412880(undefined4 param_1,undefined4 param_2,undefined4 param_3,undefined4 param_4)

{
  DAT_004540fc = param_1;
  DAT_00454104 = param_2;
  DAT_00454108 = param_4;
  DAT_00454100 = param_3;
  return 0;
}



/* 004128b0 ERR_print_errors_fp */

void ERR_print_errors_fp(void)

{
                    /* WARNING: Could not recover jumptable at 0x004128b0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  ERR_print_errors_fp();
  return;
}



/* 004128b6 PEM_read_RSAPrivateKey */

void PEM_read_RSAPrivateKey(void)

{
                    /* WARNING: Could not recover jumptable at 0x004128b6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  PEM_read_RSAPrivateKey();
  return;
}



/* 004128c0 FUN_004128c0 */

int __thiscall FUN_004128c0(void *this,char *param_1,int param_2)

{
  int iVar1;
  undefined4 local_4;
  
  local_4._3_1_ = (undefined1)((uint)this >> 0x18);
  local_4 = CONCAT22(CONCAT11(local_4._3_1_,DAT_0042fa46),DAT_0042fa44);
  iVar1 = 0;
  if (param_2 == 1) {
    local_4._0_3_ = CONCAT12(DAT_0042fa42,DAT_0042fa40);
  }
  else if (param_2 == 3) {
    local_4._0_3_ = CONCAT12(DAT_0043126a,DAT_00431268);
  }
  else if (param_2 == 2) {
    local_4 = DAT_00431264;
  }
  if (*param_1 == '/') {
    iVar1 = 8;
  }
  iVar1 = FUN_0040af91(param_1 + iVar1,(char *)&local_4);
  if (iVar1 == 0) {
    FUN_0040aa66((byte *)s_Open_file__s_failed_0043124c);
    iVar1 = -1;
  }
  return iVar1;
}



/* 00412960 FUN_00412960 */

undefined4 __cdecl FUN_00412960(FILE *param_1)

{
  int iVar1;
  
  iVar1 = FUN_0040af1b(param_1);
  if (iVar1 != 0) {
    FUN_0040aa66((byte *)s_Close_file_failed_0043126c);
    return 0xffffffff;
  }
  return 0;
}



/* 00412990 FUN_00412990 */

void __cdecl FUN_00412990(int *param_1,char *param_2,uint param_3)

{
  FUN_0040ad29(param_2,1,param_3,param_1);
  return;
}



/* 004129b0 FUN_004129b0 */

uint __cdecl FUN_004129b0(int param_1)

{
  return *(uint *)(param_1 + 0xc) & 0x10;
}



/* 004129c0 FUN_004129c0 */

uint __cdecl FUN_004129c0(int *param_1,char *param_2,uint param_3)

{
  uint uVar1;
  
  uVar1 = FUN_0040ae11(param_2,1,param_3,param_1);
  if (param_3 != uVar1) {
    FUN_0040aa66((byte *)s_Write_data_into_file_failed_00431280);
    return 0xffffffff;
  }
  return param_3;
}



/* 00412a00 FUN_00412a00 */

int __cdecl FUN_00412a00(LPCSTR param_1)

{
  byte bVar1;
  FILE *pFVar2;
  uint uVar3;
  int iVar4;
  char local_400 [1024];
  
  iVar4 = 0;
  pFVar2 = (FILE *)FUN_0040af91(param_1,(char *)&DAT_0042fa44);
  if (pFVar2 != (FILE *)0x0) {
    bVar1 = (byte)pFVar2->_flag;
    while ((bVar1 & 0x10) == 0) {
      uVar3 = FUN_0040ad29(local_400,1,0x400,(int *)pFVar2);
      iVar4 = iVar4 + uVar3;
      bVar1 = (byte)pFVar2->_flag;
    }
    FUN_0040af1b(pFVar2);
  }
  FUN_0040aa66((byte *)s_file__s_size____d_004312a0);
  return iVar4;
}



/* 00412a70 FUN_00412a70 */

void FUN_00412a70(void)

{
  undefined1 local_80 [128];
  
  FUN_0040b2c7(local_80,(byte *)s_del__s_004312b4);
  FUN_0040b231((int)local_80);
  return;
}



/* 00412ab0 FUN_00412ab0 */

void FUN_00412ab0(void)

{
  undefined1 local_80 [128];
  
  FUN_0040b2c7(local_80,(byte *)s_ren__s__s_004312bc);
  FUN_0040b231((int)local_80);
  return;
}



/* 00412b10 FUN_00412b10 */

void __cdecl FUN_00412b10(undefined4 param_1,int param_2,uint param_3)

{
  uint uVar1;
  int iVar2;
  int iVar3;
  
  iVar2 = 0x10;
  FUN_0040aa66(&DAT_0042fca8);
  uVar1 = 0;
  if (param_3 != 0) {
    iVar3 = param_2 + -0x10;
    do {
      iVar2 = iVar2 + -1;
      iVar3 = iVar3 + 1;
      FUN_0040aa66((byte *)s__02x_004312d0);
      if (iVar2 == 0) {
        FUN_0040aa66(&DAT_004312cc);
        iVar2 = 0;
        do {
          if (uVar1 != 0) {
            if ((*(byte *)(iVar3 + iVar2) < 0x21) || (0x7e < *(byte *)(iVar3 + iVar2))) {
              FUN_0040aa66(&DAT_004311e4);
            }
            else {
              FUN_0040aa66(&DAT_004312c8);
            }
          }
          iVar2 = iVar2 + 1;
        } while (iVar2 < 0x10);
        FUN_0040aa66(&DAT_0042fcbc);
        iVar2 = 0x10;
      }
      uVar1 = uVar1 + 1;
    } while (uVar1 < param_3);
  }
  FUN_0040aa66(&DAT_0042fcbc);
  return;
}



/* 00412bd0 FUN_00412bd0 */

int __cdecl
FUN_00412bd0(byte *param_1,byte *param_2,uint param_3,byte *param_4,undefined4 *param_5,int param_6)

{
  bool bVar1;
  undefined4 *puVar2;
  int iVar3;
  byte *pbVar4;
  int iVar5;
  uint uVar6;
  undefined4 uVar7;
  byte *pbVar8;
  uint local_120;
  uint local_11c;
  undefined4 local_118;
  undefined4 local_114;
  undefined4 local_110;
  undefined4 local_10c;
  int local_108;
  int local_104;
  byte *local_100;
  byte *local_fc;
  int local_f8;
  undefined1 local_f4 [244];
  
  bVar1 = false;
  local_120 = param_3;
  local_108 = 0;
  if (((param_1 != (byte *)0x0) && (param_4 != (byte *)0x0)) && (param_5 != (undefined4 *)0x0)) {
    if (param_2 == (byte *)0x0) {
      param_2 = _malloc(param_3);
      if (param_2 == (byte *)0x0) {
        return -2;
      }
      local_108 = 1;
      bVar1 = true;
    }
    puVar2 = _malloc(0x10);
    if (param_2 != (byte *)0x0) {
      local_fc = param_1;
      *puVar2 = 0;
      puVar2[1] = 0;
      puVar2[2] = 0;
      puVar2[3] = 0;
      local_100 = param_2;
      if ((param_6 == 1) || (param_6 == 0)) {
        uVar7 = *param_5;
        local_114 = param_5[1];
        local_110 = param_5[2];
        local_10c = param_5[3];
        local_118 = uVar7;
        if (param_6 == 1) {
          local_104 = AES_set_encrypt_key(param_4,0x80,local_f4);
          if (local_104 == 0) {
            if (0xf < param_3) {
              local_11c = param_3 >> 4;
              pbVar4 = param_2;
              do {
                uVar6 = 0;
                local_f8 = (int)param_1 - (int)&local_118;
                do {
                  pbVar8 = (byte *)((int)&local_118 + uVar6);
                  uVar6 = uVar6 + 1;
                  pbVar8[(int)pbVar4 - (int)&local_118] = pbVar8[local_f8] ^ *pbVar8;
                } while (uVar6 < 0x10);
                AES_encrypt(pbVar4,puVar2,local_f4);
                param_1 = param_1 + 0x10;
                param_2 = pbVar4 + 0x10;
                *(undefined4 *)pbVar4 = *puVar2;
                *(undefined4 *)(pbVar4 + 4) = puVar2[1];
                *(undefined4 *)(pbVar4 + 8) = puVar2[2];
                *(undefined4 *)(pbVar4 + 0xc) = puVar2[3];
                uVar7 = *puVar2;
                local_118 = uVar7;
                local_114 = puVar2[1];
                local_120 = local_120 - 0x10;
                local_11c = local_11c - 1;
                local_110 = puVar2[2];
                local_10c = puVar2[3];
                pbVar4 = param_2;
              } while (local_11c != 0);
            }
            if (local_120 != 0) {
              iVar3 = (int)param_1 - (int)param_4;
              iVar5 = (int)param_2 - (int)param_4;
              do {
                param_4[iVar5] = param_4[iVar3] ^ *param_4;
                param_4 = param_4 + 1;
                local_120 = local_120 - 1;
              } while (local_120 != 0);
            }
          }
        }
        else {
          local_104 = AES_set_decrypt_key(param_4,0x80,local_f4);
          if (local_104 == 0) {
            if (0xf < param_3) {
              iVar3 = (int)&local_118 - (int)param_2;
              local_11c = param_3 >> 4;
              pbVar4 = param_1;
              do {
                AES_decrypt(pbVar4,param_2,local_f4);
                iVar5 = 0x10;
                do {
                  *param_2 = *param_2 ^ param_2[iVar3];
                  param_2 = param_2 + 1;
                  iVar5 = iVar5 + -1;
                } while (iVar5 != 0);
                param_1 = pbVar4 + 0x10;
                iVar3 = iVar3 + -0x10;
                uVar7 = *(undefined4 *)pbVar4;
                local_114 = *(undefined4 *)(pbVar4 + 4);
                local_110 = *(undefined4 *)(pbVar4 + 8);
                local_120 = local_120 - 0x10;
                local_10c = *(undefined4 *)(pbVar4 + 0xc);
                local_11c = local_11c - 1;
                pbVar4 = param_1;
                local_118 = uVar7;
              } while (local_11c != 0);
            }
            if (local_120 != 0) {
              iVar3 = (int)param_1 - (int)param_4;
              iVar5 = (int)param_2 - (int)param_4;
              do {
                param_4[iVar5] = param_4[iVar3] ^ *param_4;
                param_4 = param_4 + 1;
                local_120 = local_120 - 1;
              } while (local_120 != 0);
            }
          }
        }
        *param_5 = uVar7;
        param_5[1] = local_114;
        param_5[2] = local_110;
        param_5[3] = local_10c;
        if (local_108 != 0) {
          pbVar4 = local_100;
          pbVar8 = local_fc;
          for (uVar6 = param_3 >> 2; uVar6 != 0; uVar6 = uVar6 - 1) {
            *(undefined4 *)pbVar8 = *(undefined4 *)pbVar4;
            pbVar4 = pbVar4 + 4;
            pbVar8 = pbVar8 + 4;
          }
          for (uVar6 = param_3 & 3; uVar6 != 0; uVar6 = uVar6 - 1) {
            *pbVar8 = *pbVar4;
            pbVar4 = pbVar4 + 1;
            pbVar8 = pbVar8 + 1;
          }
          FUN_0040aa97(local_100);
        }
        return local_104;
      }
    }
    if (bVar1) {
      FUN_0040aa97(puVar2);
    }
  }
  return -2;
}



/* 00412ee0 MD5_Final */

void MD5_Final(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412ee0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  MD5_Final();
  return;
}



/* 00412ee6 MD5_Update */

void MD5_Update(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412ee6. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  MD5_Update();
  return;
}



/* 00412eec MD5_Init */

void MD5_Init(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412eec. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  MD5_Init();
  return;
}



/* 00412f00 FUN_00412f00 */

void FUN_00412f00(void)

{
  MD5_Init(&DAT_00454138);
  return;
}



/* 00412f10 FUN_00412f10 */

void __cdecl FUN_00412f10(undefined4 param_1,undefined4 param_2)

{
  MD5_Update(&DAT_00454138,param_1,param_2);
  return;
}



/* 00412f30 FUN_00412f30 */

void __cdecl FUN_00412f30(undefined4 *param_1)

{
  int iVar1;
  undefined4 *puVar2;
  
  puVar2 = param_1;
  for (iVar1 = 8; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0;
    puVar2 = puVar2 + 1;
  }
  MD5_Final(param_1,&DAT_00454138);
  return;
}



/* 00412f50 AES_decrypt */

void AES_decrypt(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412f50. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AES_decrypt();
  return;
}



/* 00412f56 AES_set_decrypt_key */

void AES_set_decrypt_key(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412f56. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AES_set_decrypt_key();
  return;
}



/* 00412f5c AES_encrypt */

void AES_encrypt(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412f5c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AES_encrypt();
  return;
}



/* 00412f62 AES_set_encrypt_key */

void AES_set_encrypt_key(void)

{
                    /* WARNING: Could not recover jumptable at 0x00412f62. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  AES_set_encrypt_key();
  return;
}



/* 00412f70 FUN_00412f70 */

int __cdecl FUN_00412f70(char *param_1,int param_2)

{
  char cVar1;
  byte bVar2;
  byte *pbVar3;
  int iVar4;
  void *pvVar5;
  undefined4 *puVar6;
  int iVar7;
  uint uVar8;
  uint uVar9;
  void *this;
  void *this_00;
  void *this_01;
  char *pcVar10;
  char *pcVar11;
  char *pcVar12;
  byte *pbVar13;
  byte *pbVar14;
  char *pcVar15;
  undefined4 *puVar16;
  char *pcVar17;
  bool bVar18;
  char *pcVar19;
  int local_18;
  int local_14;
  int local_4;
  
  if (param_1 == (char *)0x0) {
    FUN_0040aa66((byte *)s_Usage__codefile_script_output_fi_00430e00);
    FUN_0040aa66((byte *)s__script___command_file_for_this_t_00430dd4);
    FUN_0040aa66((byte *)s__customer___name_of_customer_00430db0);
    FUN_0040aa66((byte *)s__model___model_name_00430d94);
    FUN_0040aa66((byte *)s__version___firmware_version_00430d70);
    return -1;
  }
  FUN_00428f30(&local_4);
  FUN_0040aa66((byte *)s_Read_script_file__>__s_00430d58);
  puVar16 = &DAT_004567c0;
  for (iVar7 = 0x26; iVar7 != 0; iVar7 = iVar7 + -1) {
    *puVar16 = 0;
    puVar16 = puVar16 + 1;
  }
  FUN_004134a0(param_1);
  pcVar12 = param_1 + 0x400;
  FUN_0040aa66((byte *)s_Vendor_name____s_00430d44);
  pcVar17 = param_1 + 0x800;
  FUN_0040aa66((byte *)s_Model_name____s_00430d30);
  pcVar10 = param_1 + 0xc00;
  FUN_0040aa66((byte *)s_Version____s_00430d1c);
  FUN_0040aa66((byte *)s_Control___00430d0c);
  if (DAT_00456814 == 0) {
    pcVar19 = s_Only_check_file_consistency_00430cd4;
  }
  else {
    if ((DAT_00456814 & 0x10) != 0) {
      FUN_0040aa66(&DAT_00430d08);
    }
    if ((DAT_00456814 & 0x20) != 0) {
      FUN_0040aa66((byte *)s_version_00430cfc);
    }
    if ((DAT_00456814 & 0x40) != 0) {
      FUN_0040aa66((byte *)s_model_00430cf4);
    }
    pcVar19 = &DAT_0042fcbc;
  }
  FUN_0040aa66((byte *)pcVar19);
  iVar7 = FUN_004139d0();
  if (iVar7 != 0) {
    return -1;
  }
  FUN_00413a60();
  FUN_0040aa66((byte *)s_Content__00430cc8);
  pcVar11 = *(char **)(DAT_004542d4 + 0x18);
  pcVar19 = DAT_004542d4;
  while (pcVar15 = pcVar11, pcVar15 != (char *)0x0) {
    pcVar19 = pcVar15;
    pcVar11 = *(char **)(pcVar15 + 0x18);
  }
  local_18 = *(int *)(pcVar19 + 0x10) + *(int *)(pcVar19 + 0xc);
  for (; param_2 != 0; param_2 = *(int *)(param_2 + 0x3010)) {
    pbVar13 = &DAT_0042fb50;
    pbVar3 = (byte *)(param_2 + 0x180e);
    do {
      bVar2 = *pbVar3;
      bVar18 = bVar2 < *pbVar13;
      if (bVar2 != *pbVar13) {
LAB_00413126:
        local_14 = (1 - (uint)bVar18) - (uint)(bVar18 != 0);
        goto LAB_0041312f;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar3[1];
      bVar18 = bVar2 < pbVar13[1];
      if (bVar2 != pbVar13[1]) goto LAB_00413126;
      pbVar3 = pbVar3 + 2;
      pbVar13 = pbVar13 + 2;
    } while (bVar2 != 0);
    local_14 = 0;
LAB_0041312f:
    pbVar3 = (byte *)(param_2 + 4);
    pbVar14 = &DAT_00430cc0;
    pbVar13 = pbVar3;
    do {
      bVar2 = *pbVar13;
      bVar18 = bVar2 < *pbVar14;
      if (bVar2 != *pbVar14) {
LAB_00413161:
        iVar7 = (1 - (uint)bVar18) - (uint)(bVar18 != 0);
        goto LAB_00413166;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar13[1];
      bVar18 = bVar2 < pbVar14[1];
      if (bVar2 != pbVar14[1]) goto LAB_00413161;
      pbVar13 = pbVar13 + 2;
      pbVar14 = pbVar14 + 2;
    } while (bVar2 != 0);
    iVar7 = 0;
LAB_00413166:
    pbVar13 = &DAT_00430cb8;
    do {
      bVar2 = *pbVar3;
      bVar18 = bVar2 < *pbVar13;
      if (bVar2 != *pbVar13) {
LAB_00413191:
        iVar4 = (1 - (uint)bVar18) - (uint)(bVar18 != 0);
        goto LAB_00413196;
      }
      if (bVar2 == 0) break;
      bVar2 = pbVar3[1];
      bVar18 = bVar2 < pbVar13[1];
      if (bVar2 != pbVar13[1]) goto LAB_00413191;
      pbVar3 = pbVar3 + 2;
      pbVar13 = pbVar13 + 2;
    } while (bVar2 != 0);
    iVar4 = 0;
LAB_00413196:
    param_1 = pcVar19;
    if (local_14 == 0 && (iVar4 == 0 || iVar7 == 0)) {
      param_1 = _malloc(0x1c);
      pcVar11 = param_1;
      for (iVar7 = 7; iVar7 != 0; iVar7 = iVar7 + -1) {
        pcVar11[0] = '\0';
        pcVar11[1] = '\0';
        pcVar11[2] = '\0';
        pcVar11[3] = '\0';
        pcVar11 = pcVar11 + 4;
      }
      uVar8 = 0xffffffff;
      pcVar11 = (char *)(param_2 + 0x80e);
      do {
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        cVar1 = *pcVar11;
        pcVar11 = pcVar11 + 1;
      } while (cVar1 != '\0');
      pvVar5 = _malloc(~uVar8);
      *(void **)(param_1 + 0x14) = pvVar5;
      uVar8 = 0xffffffff;
      pcVar11 = (char *)(param_2 + 9);
      do {
        pcVar15 = pcVar11;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar15 = pcVar11 + 1;
        cVar1 = *pcVar11;
        pcVar11 = pcVar15;
      } while (cVar1 != '\0');
      uVar8 = ~uVar8;
      pcVar11 = pcVar15 + -uVar8;
      pcVar15 = param_1;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar15 = *(undefined4 *)pcVar11;
        pcVar11 = pcVar11 + 4;
        pcVar15 = pcVar15 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar15 = *pcVar11;
        pcVar11 = pcVar11 + 1;
        pcVar15 = pcVar15 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar11 = pcVar19 + 5;
      do {
        pcVar15 = pcVar11;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar15 = pcVar11 + 1;
        cVar1 = *pcVar11;
        pcVar11 = pcVar15;
      } while (cVar1 != '\0');
      uVar8 = ~uVar8;
      pcVar11 = pcVar15 + -uVar8;
      pcVar15 = param_1 + 5;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar15 = *(undefined4 *)pcVar11;
        pcVar11 = pcVar11 + 4;
        pcVar15 = pcVar15 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar15 = *pcVar11;
        pcVar11 = pcVar11 + 1;
        pcVar15 = pcVar15 + 1;
      }
      uVar8 = 0xffffffff;
      pcVar11 = (char *)(param_2 + 0x80e);
      do {
        pcVar15 = pcVar11;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pcVar15 = pcVar11 + 1;
        cVar1 = *pcVar11;
        pcVar11 = pcVar15;
      } while (cVar1 != '\0');
      uVar8 = ~uVar8;
      pcVar11 = pcVar15 + -uVar8;
      pcVar15 = *(char **)(param_1 + 0x14);
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pcVar15 = *(undefined4 *)pcVar11;
        pcVar11 = pcVar11 + 4;
        pcVar15 = pcVar15 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pcVar15 = *pcVar11;
        pcVar11 = pcVar11 + 1;
        pcVar15 = pcVar15 + 1;
      }
      iVar7 = FUN_004117e0(*(LPCSTR *)(param_1 + 0x14));
      *(int *)(param_1 + 0x10) = local_18;
      *(int *)(param_1 + 0xc) = iVar7 + 0xc;
      local_18 = local_18 + iVar7 + 0xc;
      *(char **)(pcVar19 + 0x18) = param_1;
    }
    pcVar19 = param_1;
  }
  puVar6 = _malloc(0x1c);
  puVar16 = puVar6;
  for (iVar7 = 7; iVar7 != 0; iVar7 = iVar7 + -1) {
    *puVar16 = 0;
    puVar16 = puVar16 + 1;
  }
  pvVar5 = _malloc(8);
  puVar6[5] = pvVar5;
  *puVar6 = DAT_00430cb0;
  *(undefined1 *)(puVar6 + 1) = DAT_00430cb4;
  uVar8 = 0xffffffff;
  pcVar11 = pcVar19 + 5;
  do {
    pcVar15 = pcVar11;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar15 = pcVar11 + 1;
    cVar1 = *pcVar11;
    pcVar11 = pcVar15;
  } while (cVar1 != '\0');
  uVar8 = ~uVar8;
  pcVar11 = pcVar15 + -uVar8;
  pcVar15 = (char *)((int)puVar6 + 5);
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar15 = *(undefined4 *)pcVar11;
    pcVar11 = pcVar11 + 4;
    pcVar15 = pcVar15 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar15 = *pcVar11;
    pcVar11 = pcVar11 + 1;
    pcVar15 = pcVar15 + 1;
  }
  puVar16 = (undefined4 *)puVar6[5];
  *puVar16 = DAT_0042fcb4;
  puVar16[1] = DAT_0042fcb8;
  iVar7 = FUN_004117e0((LPCSTR)puVar6[5]);
  puVar6[3] = iVar7 + 0xc;
  puVar6[4] = local_18;
  *(undefined4 **)(pcVar19 + 0x18) = puVar6;
  for (pcVar19 = DAT_004542d4; pcVar19 != (char *)0x0; pcVar19 = *(char **)(pcVar19 + 0x18)) {
    FUN_0040aa66((byte *)s_Append__s__privacy____d__tag_____00430c84);
  }
  FUN_0040aa66(&DAT_0042fcbc);
  uVar8 = 0xffffffff;
  pcVar19 = pcVar12;
  do {
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    cVar1 = *pcVar19;
    pcVar19 = pcVar19 + 1;
  } while (cVar1 != '\0');
  if (~uVar8 - 1 < 5) {
    uVar8 = 0xffffffff;
    do {
      pcVar19 = pcVar12;
      if (uVar8 == 0) break;
      uVar8 = uVar8 - 1;
      pcVar19 = pcVar12 + 1;
      cVar1 = *pcVar12;
      pcVar12 = pcVar19;
    } while (cVar1 != '\0');
    uVar8 = ~uVar8;
    pcVar12 = pcVar19 + -uVar8;
    pcVar19 = (char *)&DAT_004567c0;
    for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
      *(undefined4 *)pcVar19 = *(undefined4 *)pcVar12;
      pcVar12 = pcVar12 + 4;
      pcVar19 = pcVar19 + 4;
    }
    for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
      *pcVar19 = *pcVar12;
      pcVar12 = pcVar12 + 1;
      pcVar19 = pcVar19 + 1;
    }
  }
  else {
    DAT_004567c0 = *(undefined4 *)pcVar12;
  }
  uVar8 = 0xffffffff;
  do {
    pcVar12 = pcVar17;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar12 = pcVar17 + 1;
    cVar1 = *pcVar17;
    pcVar17 = pcVar12;
  } while (cVar1 != '\0');
  uVar8 = ~uVar8;
  pcVar12 = pcVar12 + -uVar8;
  pcVar17 = (char *)&DAT_00456818;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar17 = *(undefined4 *)pcVar12;
    pcVar12 = pcVar12 + 4;
    pcVar17 = pcVar17 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar17 = *pcVar12;
    pcVar12 = pcVar12 + 1;
    pcVar17 = pcVar17 + 1;
  }
  uVar8 = 0xffffffff;
  do {
    pcVar12 = pcVar10;
    if (uVar8 == 0) break;
    uVar8 = uVar8 - 1;
    pcVar12 = pcVar10 + 1;
    cVar1 = *pcVar10;
    pcVar10 = pcVar12;
  } while (cVar1 != '\0');
  uVar8 = ~uVar8;
  pcVar12 = pcVar12 + -uVar8;
  pcVar17 = (char *)&DAT_004567cc;
  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
    *(undefined4 *)pcVar17 = *(undefined4 *)pcVar12;
    pcVar12 = pcVar12 + 4;
    pcVar17 = pcVar17 + 4;
  }
  for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
    *pcVar17 = *pcVar12;
    pcVar12 = pcVar12 + 1;
    pcVar17 = pcVar17 + 1;
  }
  FUN_0040aa66((byte *)s_generate_code_file__>__s_00430c68);
  iVar7 = FUN_00414830(this,&DAT_004567c0,DAT_004542d4,(char *)&DAT_00456860);
  if (iVar7 != 0) {
    FUN_0040aa66((byte *)s_generate_code_file_failed__>__d_00430c44);
    return iVar7;
  }
  iVar7 = FUN_00414490(this_00,(char *)&DAT_00456860,(undefined4 *)&DAT_00454234);
  if (iVar7 != 0) {
    FUN_0040aa66((byte *)s_generate_code_file_hash_failed___00430c1c);
    return iVar7;
  }
  iVar7 = FUN_00414520(this_01,(char *)&DAT_00456860,(uint *)&DAT_00454234);
  if (iVar7 == 0) {
    FUN_00413990();
    return 0;
  }
  FUN_0040aa66((byte *)s_update_code_file_hash_failed__>___00430bf8);
  return iVar7;
}



/* 004134a0 FUN_004134a0 */

undefined4 __cdecl FUN_004134a0(LPCSTR param_1)

{
  byte bVar1;
  char cVar2;
  char *pcVar3;
  byte *pbVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  int iVar7;
  uint uVar8;
  uint uVar9;
  byte *pbVar10;
  byte *pbVar11;
  char *pcVar12;
  bool bVar13;
  undefined4 local_20c;
  undefined2 local_208;
  FILE *local_204;
  byte local_200 [256];
  byte local_100 [256];
  
  local_20c = DAT_00430ebc;
  local_208 = DAT_00430ec0;
  puVar5 = &DAT_004567c0;
  for (iVar7 = 0x26; iVar7 != 0; iVar7 = iVar7 + -1) {
    *puVar5 = 0;
    puVar5 = puVar5 + 1;
  }
  local_204 = (FILE *)FUN_0040af91(param_1,&DAT_0042fa6c);
  if (local_204 == (FILE *)0x0) {
    FUN_0040aa66((byte *)s_Can_not_locate_file__>__s_00430ea0);
    return 0xffffffff;
  }
  bVar1 = (byte)local_204->_flag;
  do {
    if ((bVar1 & 0x10) != 0) {
      FUN_0040af1b(local_204);
      return 0;
    }
    pbVar4 = local_200;
    for (iVar7 = 0x40; iVar7 != 0; iVar7 = iVar7 + -1) {
      pbVar4[0] = 0;
      pbVar4[1] = 0;
      pbVar4[2] = 0;
      pbVar4[3] = 0;
      pbVar4 = pbVar4 + 4;
    }
    pbVar4 = local_100;
    for (iVar7 = 0x40; iVar7 != 0; iVar7 = iVar7 + -1) {
      pbVar4[0] = 0;
      pbVar4[1] = 0;
      pbVar4[2] = 0;
      pbVar4[3] = 0;
      pbVar4 = pbVar4 + 4;
    }
    pcVar3 = FUN_004290a8((char *)local_200,0x100,&local_204->_ptr);
    puVar5 = DAT_004542d4;
    if (pcVar3 != (char *)0x0) {
      uVar8 = 0xffffffff;
      pbVar4 = local_200;
      do {
        pbVar11 = pbVar4;
        if (uVar8 == 0) break;
        uVar8 = uVar8 - 1;
        pbVar11 = pbVar4 + 1;
        bVar1 = *pbVar4;
        pbVar4 = pbVar11;
      } while (bVar1 != 0);
      uVar8 = ~uVar8;
      pbVar4 = pbVar11 + -uVar8;
      pbVar11 = local_100;
      for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
        *(undefined4 *)pbVar11 = *(undefined4 *)pbVar4;
        pbVar4 = pbVar4 + 4;
        pbVar11 = pbVar11 + 4;
      }
      for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
        *pbVar11 = *pbVar4;
        pbVar4 = pbVar4 + 1;
        pbVar11 = pbVar11 + 1;
      }
      pbVar4 = (byte *)FUN_0042900c(local_200,(byte *)&local_20c);
      puVar5 = DAT_004542d4;
      if ((pbVar4 != (byte *)0x0) && (*pbVar4 != 0x23)) {
        pbVar10 = &DAT_00430e9c;
        pbVar11 = pbVar4;
        do {
          bVar1 = *pbVar10;
          bVar13 = bVar1 < *pbVar11;
          if (bVar1 != *pbVar11) {
LAB_004135be:
            iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
            goto LAB_004135c3;
          }
          if (bVar1 == 0) break;
          bVar1 = pbVar10[1];
          bVar13 = bVar1 < pbVar11[1];
          if (bVar1 != pbVar11[1]) goto LAB_004135be;
          pbVar10 = pbVar10 + 2;
          pbVar11 = pbVar11 + 2;
        } while (bVar1 != 0);
        iVar7 = 0;
LAB_004135c3:
        if (iVar7 == 0) {
          puVar5 = (undefined4 *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
          DAT_004567c0 = *puVar5;
          puVar5 = DAT_004542d4;
        }
        else {
          pcVar3 = s_model_00430e94;
          pbVar11 = pbVar4;
          do {
            bVar1 = *pbVar11;
            bVar13 = bVar1 < (byte)*pcVar3;
            if (bVar1 != *pcVar3) {
LAB_0041360e:
              iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
              goto LAB_00413613;
            }
            if (bVar1 == 0) break;
            bVar1 = pbVar11[1];
            bVar13 = bVar1 < (byte)pcVar3[1];
            if (bVar1 != pcVar3[1]) goto LAB_0041360e;
            pbVar11 = pbVar11 + 2;
            pcVar3 = pcVar3 + 2;
          } while (bVar1 != 0);
          iVar7 = 0;
LAB_00413613:
          if (iVar7 == 0) {
            uVar8 = FUN_0042900c((byte *)0x0,(byte *)&local_20c);
            uVar9 = 0xffffffff;
            pcVar3 = (char *)(uVar8 + 0x100);
            do {
              pcVar12 = pcVar3;
              if (uVar9 == 0) break;
              uVar9 = uVar9 - 1;
              pcVar12 = pcVar3 + 1;
              cVar2 = *pcVar3;
              pcVar3 = pcVar12;
            } while (cVar2 != '\0');
            uVar9 = ~uVar9;
            pcVar3 = pcVar12 + -uVar9;
            pcVar12 = (char *)&DAT_00456818;
            for (uVar8 = uVar9 >> 2; uVar8 != 0; uVar8 = uVar8 - 1) {
              *(undefined4 *)pcVar12 = *(undefined4 *)pcVar3;
              pcVar3 = pcVar3 + 4;
              pcVar12 = pcVar12 + 4;
            }
            for (uVar9 = uVar9 & 3; uVar9 != 0; uVar9 = uVar9 - 1) {
              *pcVar12 = *pcVar3;
              pcVar3 = pcVar3 + 1;
              pcVar12 = pcVar12 + 1;
            }
            FUN_00411610((char *)&DAT_00456818);
            puVar5 = DAT_004542d4;
          }
          else {
            pcVar3 = s_control_00430e8c;
            pbVar11 = pbVar4;
            do {
              bVar1 = *pbVar11;
              bVar13 = bVar1 < (byte)*pcVar3;
              if (bVar1 != *pcVar3) {
LAB_0041368d:
                iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                goto LAB_00413692;
              }
              if (bVar1 == 0) break;
              bVar1 = pbVar11[1];
              bVar13 = bVar1 < (byte)pcVar3[1];
              if (bVar1 != pcVar3[1]) goto LAB_0041368d;
              pbVar11 = pbVar11 + 2;
              pcVar3 = pcVar3 + 2;
            } while (bVar1 != 0);
            iVar7 = 0;
LAB_00413692:
            if (iVar7 == 0) {
              pbVar4 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
              while (puVar5 = DAT_004542d4, pbVar4 != (byte *)0x0) {
                pbVar10 = &DAT_00430e9c;
                pbVar11 = pbVar4;
                do {
                  bVar1 = *pbVar11;
                  bVar13 = bVar1 < *pbVar10;
                  if (bVar1 != *pbVar10) {
LAB_004136de:
                    iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                    goto LAB_004136e3;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pbVar11[1];
                  bVar13 = bVar1 < pbVar10[1];
                  if (bVar1 != pbVar10[1]) goto LAB_004136de;
                  pbVar11 = pbVar11 + 2;
                  pbVar10 = pbVar10 + 2;
                } while (bVar1 != 0);
                iVar7 = 0;
LAB_004136e3:
                if (iVar7 == 0) {
                  DAT_00456814 = DAT_00456814 | 0x10;
                }
                else {
                  pcVar3 = s_ignore_00430e84;
                  pbVar11 = pbVar4;
                  do {
                    bVar1 = *pbVar11;
                    bVar13 = bVar1 < (byte)*pcVar3;
                    if (bVar1 != *pcVar3) {
LAB_0041371e:
                      iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                      goto LAB_00413723;
                    }
                    if (bVar1 == 0) break;
                    bVar1 = pbVar11[1];
                    bVar13 = bVar1 < (byte)pcVar3[1];
                    if (bVar1 != pcVar3[1]) goto LAB_0041371e;
                    pbVar11 = pbVar11 + 2;
                    pcVar3 = pcVar3 + 2;
                  } while (bVar1 != 0);
                  iVar7 = 0;
LAB_00413723:
                  if (iVar7 != 0) {
                    pcVar3 = s_version_0042f138;
                    pbVar11 = pbVar4;
                    do {
                      bVar1 = *pbVar11;
                      bVar13 = bVar1 < (byte)*pcVar3;
                      if (bVar1 != *pcVar3) {
LAB_00413752:
                        iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                        goto LAB_00413757;
                      }
                      if (bVar1 == 0) break;
                      bVar1 = pbVar11[1];
                      bVar13 = bVar1 < (byte)pcVar3[1];
                      if (bVar1 != pcVar3[1]) goto LAB_00413752;
                      pbVar11 = pbVar11 + 2;
                      pcVar3 = pcVar3 + 2;
                    } while (bVar1 != 0);
                    iVar7 = 0;
LAB_00413757:
                    if (iVar7 == 0) {
                      DAT_00456814 = DAT_00456814 | 0x20;
                    }
                    else {
                      pcVar3 = s_model_00430e94;
                      do {
                        bVar1 = *pbVar4;
                        bVar13 = bVar1 < (byte)*pcVar3;
                        if (bVar1 != *pcVar3) {
LAB_0041378f:
                          iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                          goto LAB_00413794;
                        }
                        if (bVar1 == 0) break;
                        bVar1 = pbVar4[1];
                        bVar13 = bVar1 < (byte)pcVar3[1];
                        if (bVar1 != pcVar3[1]) goto LAB_0041378f;
                        pbVar4 = pbVar4 + 2;
                        pcVar3 = pcVar3 + 2;
                      } while (bVar1 != 0);
                      iVar7 = 0;
LAB_00413794:
                      if (iVar7 == 0) {
                        DAT_00456814 = DAT_00456814 | 0x40;
                      }
                    }
                  }
                }
                pbVar4 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
              }
            }
            else {
              pcVar3 = s_append_00430e7c;
              pbVar11 = pbVar4;
              do {
                bVar1 = *pbVar11;
                bVar13 = bVar1 < (byte)*pcVar3;
                if (bVar1 != *pcVar3) {
LAB_004137ed:
                  iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                  goto LAB_004137f2;
                }
                if (bVar1 == 0) break;
                bVar1 = pbVar11[1];
                bVar13 = bVar1 < (byte)pcVar3[1];
                if (bVar1 != pcVar3[1]) goto LAB_004137ed;
                pbVar11 = pbVar11 + 2;
                pcVar3 = pcVar3 + 2;
              } while (bVar1 != 0);
              iVar7 = 0;
LAB_004137f2:
              if (iVar7 == 0) {
                puVar5 = _malloc(0x1c);
                if (puVar5 == (undefined4 *)0x0) {
                  FUN_0040aa66((byte *)s_Allocate_memory_failed_00430e54);
                  return 0xffffffff;
                }
                puVar6 = puVar5;
                for (iVar7 = 7; iVar7 != 0; iVar7 = iVar7 + -1) {
                  *puVar6 = 0;
                  puVar6 = puVar6 + 1;
                }
                pbVar4 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                pcVar3 = s_no_priv_00430e74;
                do {
                  bVar1 = *pcVar3;
                  bVar13 = bVar1 < *pbVar4;
                  if (bVar1 != *pbVar4) {
LAB_00413852:
                    iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                    goto LAB_00413857;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pcVar3[1];
                  bVar13 = bVar1 < pbVar4[1];
                  if (bVar1 != pbVar4[1]) goto LAB_00413852;
                  pcVar3 = pcVar3 + 2;
                  pbVar4 = pbVar4 + 2;
                } while (bVar1 != 0);
                iVar7 = 0;
LAB_00413857:
                if (iVar7 == 0) {
                  *(undefined1 *)((int)puVar5 + 5) = 0;
                }
                else {
                  *(undefined1 *)((int)puVar5 + 5) = 1;
                }
                puVar6 = (undefined4 *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                *puVar5 = *puVar6;
                pcVar3 = (char *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                uVar8 = 0xffffffff;
                pcVar12 = pcVar3;
                do {
                  if (uVar8 == 0) break;
                  uVar8 = uVar8 - 1;
                  cVar2 = *pcVar12;
                  pcVar12 = pcVar12 + 1;
                } while (cVar2 != '\0');
                puVar6 = _malloc(~uVar8);
                uVar8 = 0xffffffff;
                puVar5[5] = puVar6;
                pcVar12 = pcVar3;
                do {
                  if (uVar8 == 0) break;
                  uVar8 = uVar8 - 1;
                  cVar2 = *pcVar12;
                  pcVar12 = pcVar12 + 1;
                } while (cVar2 != '\0');
                for (uVar9 = ~uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
                  *puVar6 = 0;
                  puVar6 = puVar6 + 1;
                }
                for (uVar8 = ~uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
                  *(undefined1 *)puVar6 = 0;
                  puVar6 = (undefined4 *)((int)puVar6 + 1);
                }
                uVar8 = 0xffffffff;
                do {
                  pcVar12 = pcVar3;
                  if (uVar8 == 0) break;
                  uVar8 = uVar8 - 1;
                  pcVar12 = pcVar3 + 1;
                  cVar2 = *pcVar3;
                  pcVar3 = pcVar12;
                } while (cVar2 != '\0');
                uVar8 = ~uVar8;
                pcVar3 = pcVar12 + -uVar8;
                pcVar12 = (char *)puVar5[5];
                for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
                  *(undefined4 *)pcVar12 = *(undefined4 *)pcVar3;
                  pcVar3 = pcVar3 + 4;
                  pcVar12 = pcVar12 + 4;
                }
                for (uVar8 = uVar8 & 3; uVar8 != 0; uVar8 = uVar8 - 1) {
                  *pcVar12 = *pcVar3;
                  pcVar3 = pcVar3 + 1;
                  pcVar12 = pcVar12 + 1;
                }
                if (DAT_004542d4 != (undefined4 *)0x0) {
                  puVar5[6] = DAT_004542d4;
                }
              }
              else {
                pcVar3 = s_output_00430e6c;
                do {
                  bVar1 = *pbVar4;
                  bVar13 = bVar1 < (byte)*pcVar3;
                  if (bVar1 != *pcVar3) {
LAB_00413916:
                    iVar7 = (1 - (uint)bVar13) - (uint)(bVar13 != 0);
                    goto LAB_0041391b;
                  }
                  if (bVar1 == 0) break;
                  bVar1 = pbVar4[1];
                  bVar13 = bVar1 < (byte)pcVar3[1];
                  if (bVar1 != pcVar3[1]) goto LAB_00413916;
                  pbVar4 = pbVar4 + 2;
                  pcVar3 = pcVar3 + 2;
                } while (bVar1 != 0);
                iVar7 = 0;
LAB_0041391b:
                if (iVar7 == 0) {
                  pcVar3 = (char *)FUN_0042900c((byte *)0x0,(byte *)&local_20c);
                  uVar8 = 0xffffffff;
                  do {
                    pcVar12 = pcVar3;
                    if (uVar8 == 0) break;
                    uVar8 = uVar8 - 1;
                    pcVar12 = pcVar3 + 1;
                    cVar2 = *pcVar3;
                    pcVar3 = pcVar12;
                  } while (cVar2 != '\0');
                  uVar8 = ~uVar8;
                  pcVar3 = pcVar12 + -uVar8;
                  pcVar12 = (char *)&DAT_00456860;
                  for (uVar9 = uVar8 >> 2; uVar9 != 0; uVar9 = uVar9 - 1) {
                    *(undefined4 *)pcVar12 = *(undefined4 *)pcVar3;
                    pcVar3 = pcVar3 + 4;
                    pcVar12 = pcVar12 + 4;
                  }
                  for (uVar8 = uVar8 & 3; puVar5 = DAT_004542d4, uVar8 != 0; uVar8 = uVar8 - 1) {
                    *pcVar12 = *pcVar3;
                    pcVar3 = pcVar3 + 1;
                    pcVar12 = pcVar12 + 1;
                  }
                }
              }
            }
          }
        }
      }
    }
    DAT_004542d4 = puVar5;
    bVar1 = (byte)local_204->_flag;
  } while( true );
}



/* 00413990 FUN_00413990 */

void FUN_00413990(void)

{
  LPVOID pvVar1;
  LPVOID pvVar2;
  
  pvVar2 = DAT_004542d4;
  if (DAT_004542d4 == (LPVOID)0x0) {
    DAT_004542d4 = (LPVOID)0x0;
    return;
  }
  do {
    FUN_0040aa97(*(LPVOID *)((int)pvVar2 + 0x14));
    pvVar1 = *(LPVOID *)((int)pvVar2 + 0x18);
    FUN_0040aa97(pvVar2);
    pvVar2 = pvVar1;
  } while (pvVar1 != (LPVOID)0x0);
  DAT_004542d4 = pvVar1;
  return;
}



/* 004139d0 FUN_004139d0 */

undefined4 FUN_004139d0(void)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  int iVar4;
  byte *pbVar5;
  byte *pbVar6;
  bool bVar7;
  
  if (DAT_004542d4 == (byte *)0x0) {
    FUN_0040aa66((byte *)s_ERR_pt_append_is_null_00430ee0);
    return 0xffffffff;
  }
  iVar4 = 0x8098;
  pbVar6 = DAT_004542d4;
  do {
    pbVar5 = &DAT_0042fb64;
    pbVar2 = pbVar6;
    do {
      bVar1 = *pbVar2;
      bVar7 = bVar1 < *pbVar5;
      if (bVar1 != *pbVar5) {
LAB_00413a1f:
        iVar3 = (1 - (uint)bVar7) - (uint)(bVar7 != 0);
        goto LAB_00413a24;
      }
      if (bVar1 == 0) break;
      bVar1 = pbVar2[1];
      bVar7 = bVar1 < pbVar5[1];
      if (bVar1 != pbVar5[1]) goto LAB_00413a1f;
      pbVar2 = pbVar2 + 2;
      pbVar5 = pbVar5 + 2;
    } while (bVar1 != 0);
    iVar3 = 0;
LAB_00413a24:
    if (iVar3 == 0) {
      pbVar6[0xc] = 0;
      pbVar6[0xd] = 0x80;
      pbVar6[0xe] = 0;
      pbVar6[0xf] = 0;
      pbVar6[0x10] = 0x98;
      pbVar6[0x11] = 0;
      pbVar6[0x12] = 0;
      pbVar6[0x13] = 0;
    }
    else {
      iVar3 = FUN_004117e0(*(LPCSTR *)(pbVar6 + 0x14));
      *(int *)(pbVar6 + 0x10) = iVar4;
      *(int *)(pbVar6 + 0xc) = iVar3 + 0xc;
      iVar4 = iVar4 + iVar3 + 0xc;
    }
    pbVar6 = *(byte **)(pbVar6 + 0x18);
    if (pbVar6 == (byte *)0x0) {
      return 0;
    }
  } while( true );
}



/* 00413a60 FUN_00413a60 */

undefined4 FUN_00413a60(void)

{
  int iVar1;
  
  iVar1 = FUN_00413ee0();
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_create_xml_file_00430f50);
    return 0xffffffff;
  }
  iVar1 = FUN_00413af0(DAT_004542dc,DAT_004542d8,DAT_004542d4);
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_fill_xml_content_00430f34);
    return 0xffffffff;
  }
  iVar1 = FUN_00413fd0();
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_close_xml_file_00430f1c);
    return 0xffffffff;
  }
  iVar1 = FUN_00413ff0();
  if (iVar1 < 0) {
    FUN_0040aa66((byte *)s_Error_trans_xml_file_fixed_size_00430ef8);
    return 0xffffffff;
  }
  return 0;
}



/* 00413af0 FUN_00413af0 */

undefined4 __cdecl FUN_00413af0(undefined4 *param_1,int *param_2,int param_3)

{
  byte bVar1;
  char *pcVar2;
  byte *pbVar3;
  int iVar4;
  int iVar5;
  uint uVar6;
  uint uVar7;
  byte *pbVar8;
  byte *pbVar9;
  bool bVar10;
  int local_894;
  undefined4 local_890;
  undefined2 local_88c;
  undefined1 local_88a;
  undefined1 local_888 [128];
  byte local_808 [64];
  undefined1 auStack_7c8 [964];
  byte local_404 [1028];
  
  local_890 = DAT_00430ffc;
  local_88c = DAT_00431000;
  local_88a = DAT_00431002;
  local_894 = 0;
  if (((param_1 == (undefined4 *)0x0) || (param_2 == (int *)0x0)) || (param_3 == 0)) {
    FUN_0040aa66((byte *)s_Error_fill_xml_content_INV_ARGC_00430f68);
    return 0xffffffff;
  }
  if ((*(byte *)(param_1 + 3) & 0x10) != 0) {
    return 0;
  }
  do {
    pbVar3 = local_404;
    for (iVar5 = 0x100; iVar5 != 0; iVar5 = iVar5 + -1) {
      pbVar3[0] = 0;
      pbVar3[1] = 0;
      pbVar3[2] = 0;
      pbVar3[3] = 0;
      pbVar3 = pbVar3 + 4;
    }
    *pbVar3 = 0;
    pbVar3 = local_808;
    for (iVar5 = 0x100; iVar5 != 0; iVar5 = iVar5 + -1) {
      pbVar3[0] = 0;
      pbVar3[1] = 0;
      pbVar3[2] = 0;
      pbVar3[3] = 0;
      pbVar3 = pbVar3 + 4;
    }
    *pbVar3 = 0;
    pcVar2 = FUN_004290a8((char *)local_404,0x400,param_1);
    if (pcVar2 != (char *)0x0) {
      uVar6 = 0xffffffff;
      pbVar3 = local_404;
      do {
        pbVar8 = pbVar3;
        if (uVar6 == 0) break;
        uVar6 = uVar6 - 1;
        pbVar8 = pbVar3 + 1;
        bVar1 = *pbVar3;
        pbVar3 = pbVar8;
      } while (bVar1 != 0);
      uVar6 = ~uVar6;
      pbVar3 = pbVar8 + -uVar6;
      pbVar8 = local_808;
      for (uVar7 = uVar6 >> 2; uVar7 != 0; uVar7 = uVar7 - 1) {
        *(undefined4 *)pbVar8 = *(undefined4 *)pbVar3;
        pbVar3 = pbVar3 + 4;
        pbVar8 = pbVar8 + 4;
      }
      for (uVar6 = uVar6 & 3; uVar6 != 0; uVar6 = uVar6 - 1) {
        *pbVar8 = *pbVar3;
        pbVar3 = pbVar3 + 1;
        pbVar8 = pbVar8 + 1;
      }
      pbVar3 = (byte *)FUN_0042900c(local_404,(byte *)&local_890);
      if (pbVar3 != (byte *)0x0) {
        pbVar9 = &DAT_00430ff4;
        pbVar8 = pbVar3;
        do {
          bVar1 = *pbVar8;
          bVar10 = bVar1 < *pbVar9;
          if (bVar1 != *pbVar9) {
LAB_00413c0e:
            iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
            goto LAB_00413c13;
          }
          if (bVar1 == 0) break;
          bVar1 = pbVar8[1];
          bVar10 = bVar1 < pbVar9[1];
          if (bVar1 != pbVar9[1]) goto LAB_00413c0e;
          pbVar8 = pbVar8 + 2;
          pbVar9 = pbVar9 + 2;
        } while (bVar1 != 0);
        iVar5 = 0;
LAB_00413c13:
        if (iVar5 == 0) {
          pbVar3 = (byte *)FUN_0042900c((byte *)0x0,(byte *)&local_890);
          local_894 = 0;
          FUN_00413e70(pbVar3,&local_894);
          FUN_0042900c((byte *)0x0,(byte *)&local_890);
        }
        else {
          pcVar2 = s_<SIZE_00430fec;
          pbVar8 = pbVar3;
          do {
            bVar1 = *pbVar8;
            bVar10 = bVar1 < (byte)*pcVar2;
            if (bVar1 != *pcVar2) {
LAB_00413c75:
              iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
              goto LAB_00413c7a;
            }
            if (bVar1 == 0) break;
            bVar1 = pbVar8[1];
            bVar10 = bVar1 < (byte)pcVar2[1];
            if (bVar1 != pcVar2[1]) goto LAB_00413c75;
            pbVar8 = pbVar8 + 2;
            pcVar2 = pcVar2 + 2;
          } while (bVar1 != 0);
          iVar5 = 0;
LAB_00413c7a:
          if ((iVar5 == 0) && (local_894 != 0)) {
            uVar6 = FUN_0040b2c7(local_888,&DAT_00430fe8);
            iVar5 = FUN_0040b2c7(local_808,(byte *)s__12c<SIZE>_16c_s_00430fd4);
            if ((uVar6 < 0x40) && (iVar4 = 0x40 - uVar6, iVar4 != 0)) {
              pbVar3 = local_808 + iVar5;
              do {
                FUN_0040b2c7(pbVar3,&DAT_0042fce0);
                pbVar3 = pbVar3 + 1;
                iVar4 = iVar4 + -1;
              } while (iVar4 != 0);
            }
            FUN_0040b2c7(auStack_7c8 + (iVar5 - uVar6),(byte *)s_<_SIZE>_00430fc8);
          }
          else {
            pcVar2 = s_<OFFSET_00430fc0;
            pbVar8 = pbVar3;
            do {
              bVar1 = *pbVar8;
              bVar10 = bVar1 < (byte)*pcVar2;
              if (bVar1 != *pcVar2) {
LAB_00413d3a:
                iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
                goto LAB_00413d3f;
              }
              if (bVar1 == 0) break;
              bVar1 = pbVar8[1];
              bVar10 = bVar1 < (byte)pcVar2[1];
              if (bVar1 != pcVar2[1]) goto LAB_00413d3a;
              pbVar8 = pbVar8 + 2;
              pcVar2 = pcVar2 + 2;
            } while (bVar1 != 0);
            iVar5 = 0;
LAB_00413d3f:
            if ((iVar5 == 0) && (local_894 != 0)) {
              uVar6 = FUN_0040b2c7(local_888,&DAT_00430fe8);
              iVar5 = FUN_0040b2c7(local_808,(byte *)s__12c<OFFSET>_14c_s_00430fac);
              if ((uVar6 < 0x40) && (iVar4 = 0x40 - uVar6, iVar4 != 0)) {
                pbVar3 = local_808 + iVar5;
                do {
                  FUN_0040b2c7(pbVar3,&DAT_0042fce0);
                  pbVar3 = pbVar3 + 1;
                  iVar4 = iVar4 + -1;
                } while (iVar4 != 0);
              }
              FUN_0040b2c7(auStack_7c8 + (iVar5 - uVar6),(byte *)s_<_OFFSET>_00430fa0);
            }
            else {
              pbVar8 = &DAT_00430f98;
              do {
                bVar1 = *pbVar3;
                bVar10 = bVar1 < *pbVar8;
                if (bVar1 != *pbVar8) {
LAB_00413df4:
                  iVar5 = (1 - (uint)bVar10) - (uint)(bVar10 != 0);
                  goto LAB_00413df9;
                }
                if (bVar1 == 0) break;
                bVar1 = pbVar3[1];
                bVar10 = bVar1 < pbVar8[1];
                if (bVar1 != pbVar8[1]) goto LAB_00413df4;
                pbVar3 = pbVar3 + 2;
                pbVar8 = pbVar8 + 2;
              } while (bVar1 != 0);
              iVar5 = 0;
LAB_00413df9:
              if (iVar5 == 0) {
                FUN_0040ac3e(param_2,(byte *)s_<_ROOT>_00430f8c);
                return 0;
              }
            }
          }
        }
        FUN_0040ac3e(param_2,local_808);
      }
    }
    if ((*(byte *)(param_1 + 3) & 0x10) != 0) {
      return 0;
    }
  } while( true );
}



/* 00413e70 FUN_00413e70 */

byte * __cdecl FUN_00413e70(byte *param_1,undefined4 *param_2)

{
  byte *pbVar1;
  byte bVar2;
  byte *pbVar3;
  byte *pbVar4;
  int iVar5;
  byte *pbVar6;
  bool bVar7;
  
  pbVar3 = DAT_004542d4;
  pbVar1 = param_1;
  if (DAT_004542d4 != (byte *)0x0) {
    for (; pbVar4 = pbVar3, pbVar6 = param_1, pbVar1 != (byte *)0x0; pbVar1 = *(byte **)pbVar1) {
      do {
        bVar2 = *pbVar4;
        bVar7 = bVar2 < *pbVar6;
        if (bVar2 != *pbVar6) {
LAB_00413eb3:
          iVar5 = (1 - (uint)bVar7) - (uint)(bVar7 != 0);
          goto LAB_00413eb8;
        }
        if (bVar2 == 0) break;
        bVar2 = pbVar4[1];
        bVar7 = bVar2 < pbVar6[1];
        if (bVar2 != pbVar6[1]) goto LAB_00413eb3;
        pbVar4 = pbVar4 + 2;
        pbVar6 = pbVar6 + 2;
      } while (bVar2 != 0);
      iVar5 = 0;
LAB_00413eb8:
      if (iVar5 == 0) {
        *param_2 = pbVar3;
        return pbVar3;
      }
      pbVar1 = pbVar3 + 0x18;
      pbVar3 = *(byte **)pbVar1;
    }
  }
  return (byte *)0x0;
}



/* 00413ee0 FUN_00413ee0 */

undefined4 FUN_00413ee0(void)

{
  char cVar1;
  int iVar2;
  uint uVar3;
  uint uVar4;
  char *pcVar5;
  char *pcVar6;
  char *pcVar7;
  char local_80 [128];
  
  if (s_download_xml_004312dc[0] == '\0') {
    FUN_0040aa66((byte *)s_Error__create_xml_file__xml_file_00431034);
    return 0xffffffff;
  }
  DAT_004542dc = FUN_0040af91(s_download_xml_004312dc,&DAT_0042fa6c);
  if (DAT_004542dc != 0) {
    pcVar5 = local_80;
    for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
      pcVar5[0] = '\0';
      pcVar5[1] = '\0';
      pcVar5[2] = '\0';
      pcVar5[3] = '\0';
      pcVar5 = pcVar5 + 4;
    }
    uVar3 = 0xffffffff;
    pcVar5 = s_download_xml_004312dc;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = local_80;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    uVar3 = 0xffffffff;
    pcVar5 = (char *)&DAT_00431028;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    iVar2 = -1;
    pcVar5 = local_80;
    do {
      pcVar6 = pcVar5;
      if (iVar2 == 0) break;
      iVar2 = iVar2 + -1;
      pcVar6 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar6;
    } while (cVar1 != '\0');
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = pcVar6 + -1;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    DAT_004542d8 = FUN_0040af91(local_80,&DAT_00431030);
    if (DAT_004542d8 != 0) {
      return 0;
    }
    DAT_004542d8 = 0;
  }
  FUN_0040aa66((byte *)s_Error_Can_not_locate_file__>__s_00431004);
  return 0xffffffff;
}



/* 00413fd0 FUN_00413fd0 */

undefined4 FUN_00413fd0(void)

{
  FUN_0040af1b(DAT_004542dc);
  FUN_0040af1b(DAT_004542d8);
  return 0;
}



/* 00413ff0 FUN_00413ff0 */

undefined4 FUN_00413ff0(void)

{
  char cVar1;
  int iVar2;
  uint uVar3;
  uint uVar4;
  char *pcVar5;
  char *pcVar6;
  char *pcVar7;
  char local_80 [128];
  
  pcVar5 = local_80;
  for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
    pcVar5[0] = '\0';
    pcVar5[1] = '\0';
    pcVar5[2] = '\0';
    pcVar5[3] = '\0';
    pcVar5 = pcVar5 + 4;
  }
  uVar3 = 0xffffffff;
  pcVar5 = s_download_xml_004312dc;
  do {
    pcVar7 = pcVar5;
    if (uVar3 == 0) break;
    uVar3 = uVar3 - 1;
    pcVar7 = pcVar5 + 1;
    cVar1 = *pcVar5;
    pcVar5 = pcVar7;
  } while (cVar1 != '\0');
  uVar3 = ~uVar3;
  pcVar5 = pcVar7 + -uVar3;
  pcVar7 = local_80;
  for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
    *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
    pcVar5 = pcVar5 + 4;
    pcVar7 = pcVar7 + 4;
  }
  for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
    *pcVar7 = *pcVar5;
    pcVar5 = pcVar5 + 1;
    pcVar7 = pcVar7 + 1;
  }
  uVar3 = 0xffffffff;
  pcVar5 = (char *)&DAT_00431028;
  do {
    pcVar7 = pcVar5;
    if (uVar3 == 0) break;
    uVar3 = uVar3 - 1;
    pcVar7 = pcVar5 + 1;
    cVar1 = *pcVar5;
    pcVar5 = pcVar7;
  } while (cVar1 != '\0');
  uVar3 = ~uVar3;
  iVar2 = -1;
  pcVar5 = local_80;
  do {
    pcVar6 = pcVar5;
    if (iVar2 == 0) break;
    iVar2 = iVar2 + -1;
    pcVar6 = pcVar5 + 1;
    cVar1 = *pcVar5;
    pcVar5 = pcVar6;
  } while (cVar1 != '\0');
  pcVar5 = pcVar7 + -uVar3;
  pcVar7 = pcVar6 + -1;
  for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
    *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
    pcVar5 = pcVar5 + 4;
    pcVar7 = pcVar7 + 4;
  }
  for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
    *pcVar7 = *pcVar5;
    pcVar5 = pcVar5 + 1;
    pcVar7 = pcVar7 + 1;
  }
  DAT_004542dc = (int *)FUN_0040af91(local_80,&DAT_0042fa6c);
  if (DAT_004542dc != (int *)0x0) {
    pcVar5 = local_80;
    for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
      pcVar5[0] = '\0';
      pcVar5[1] = '\0';
      pcVar5[2] = '\0';
      pcVar5[3] = '\0';
      pcVar5 = pcVar5 + 4;
    }
    uVar3 = 0xffffffff;
    pcVar5 = s_download_xml_004312dc;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = local_80;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    uVar3 = 0xffffffff;
    pcVar5 = (char *)&DAT_00431064;
    do {
      pcVar7 = pcVar5;
      if (uVar3 == 0) break;
      uVar3 = uVar3 - 1;
      pcVar7 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar7;
    } while (cVar1 != '\0');
    uVar3 = ~uVar3;
    iVar2 = -1;
    pcVar5 = local_80;
    do {
      pcVar6 = pcVar5;
      if (iVar2 == 0) break;
      iVar2 = iVar2 + -1;
      pcVar6 = pcVar5 + 1;
      cVar1 = *pcVar5;
      pcVar5 = pcVar6;
    } while (cVar1 != '\0');
    pcVar5 = pcVar7 + -uVar3;
    pcVar7 = pcVar6 + -1;
    for (uVar4 = uVar3 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
      *(undefined4 *)pcVar7 = *(undefined4 *)pcVar5;
      pcVar5 = pcVar5 + 4;
      pcVar7 = pcVar7 + 4;
    }
    for (uVar3 = uVar3 & 3; uVar3 != 0; uVar3 = uVar3 - 1) {
      *pcVar7 = *pcVar5;
      pcVar5 = pcVar5 + 1;
      pcVar7 = pcVar7 + 1;
    }
    DAT_004542d8 = (int *)FUN_0040af91(local_80,&DAT_00431030);
    if (DAT_004542d8 != (int *)0x0) {
      iVar2 = FUN_00414160(DAT_004542dc,DAT_004542d8);
      if (-1 < iVar2) {
        FUN_00413fd0();
        return 0;
      }
      FUN_0040aa66((byte *)s_Error_trans_xml_file_fixed_size_00430ef8);
      return 0xffffffff;
    }
    DAT_004542d8 = (int *)0x0;
  }
  FUN_0040aa66((byte *)s_Error_Can_not_locate_file__>__s_00431004);
  return 0xffffffff;
}



/* 00414160 FUN_00414160 */

undefined4 __cdecl FUN_00414160(int *param_1,int *param_2)

{
  uint uVar1;
  byte *pbVar2;
  int iVar3;
  uint uVar4;
  uint uVar5;
  uint uVar6;
  byte *pbVar7;
  byte *pbVar8;
  byte local_404 [1028];
  
  uVar6 = 0x7ff4;
  if ((*(byte *)(param_1 + 3) & 0x10) == 0) {
    do {
      if (uVar6 == 0) {
        return 0;
      }
      pbVar2 = local_404;
      for (iVar3 = 0x100; iVar3 != 0; iVar3 = iVar3 + -1) {
        pbVar2[0] = 0;
        pbVar2[1] = 0;
        pbVar2[2] = 0;
        pbVar2[3] = 0;
        pbVar2 = pbVar2 + 4;
      }
      *pbVar2 = 0;
      uVar1 = FUN_0040ad29((char *)local_404,1,0x400,param_1);
      uVar5 = uVar6;
      if ((uVar6 < uVar1) || (uVar5 = uVar1, uVar1 != 0x400)) {
        uVar1 = uVar5 + 1;
        pbVar2 = _malloc(uVar1);
        if (pbVar2 == (byte *)0x0) goto LAB_00414261;
        pbVar7 = pbVar2;
        for (uVar4 = uVar1 >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
          pbVar7[0] = 0;
          pbVar7[1] = 0;
          pbVar7[2] = 0;
          pbVar7[3] = 0;
          pbVar7 = pbVar7 + 4;
        }
        for (uVar1 = uVar1 & 3; uVar1 != 0; uVar1 = uVar1 - 1) {
          *pbVar7 = 0;
          pbVar7 = pbVar7 + 1;
        }
        pbVar7 = local_404;
        pbVar8 = pbVar2;
        for (uVar1 = uVar5 >> 2; uVar1 != 0; uVar1 = uVar1 - 1) {
          *(undefined4 *)pbVar8 = *(undefined4 *)pbVar7;
          pbVar7 = pbVar7 + 4;
          pbVar8 = pbVar8 + 4;
        }
        for (uVar1 = uVar5 & 3; uVar1 != 0; uVar1 = uVar1 - 1) {
          *pbVar8 = *pbVar7;
          pbVar7 = pbVar7 + 1;
          pbVar8 = pbVar8 + 1;
        }
        FUN_0040ac3e(param_2,pbVar2);
        iVar3 = -uVar5;
        FUN_0040aa97(pbVar2);
      }
      else {
        FUN_0040ac3e(param_2,local_404);
        iVar3 = -0x400;
      }
      uVar6 = uVar6 + iVar3;
    } while ((*(byte *)(param_1 + 3) & 0x10) == 0);
    if (uVar6 == 0) {
      return 0;
    }
  }
  uVar5 = uVar6 + 1;
  pbVar2 = _malloc(uVar5);
  if (pbVar2 != (byte *)0x0) {
    pbVar7 = pbVar2;
    for (uVar1 = uVar5 >> 2; uVar1 != 0; uVar1 = uVar1 - 1) {
      pbVar7[0] = 0;
      pbVar7[1] = 0;
      pbVar7[2] = 0;
      pbVar7[3] = 0;
      pbVar7 = pbVar7 + 4;
    }
    for (uVar5 = uVar5 & 3; uVar5 != 0; uVar5 = uVar5 - 1) {
      *pbVar7 = 0;
      pbVar7 = pbVar7 + 1;
    }
    pbVar7 = pbVar2;
    for (uVar5 = uVar6 >> 2; uVar5 != 0; uVar5 = uVar5 - 1) {
      pbVar7[0] = 0x20;
      pbVar7[1] = 0x20;
      pbVar7[2] = 0x20;
      pbVar7[3] = 0x20;
      pbVar7 = pbVar7 + 4;
    }
    for (uVar6 = uVar6 & 3; uVar6 != 0; uVar6 = uVar6 - 1) {
      *pbVar7 = 0x20;
      pbVar7 = pbVar7 + 1;
    }
    FUN_0040ac3e(param_2,pbVar2);
    FUN_0040aa97(pbVar2);
    return 0;
  }
LAB_00414261:
  FUN_0040aa66((byte *)s_Error_trans_xml_file_fixed_size__0043106c);
  return 0xffffffff;
}



/* 004142d0 FUN_004142d0 */

void __cdecl FUN_004142d0(uint *param_1,uint param_2,undefined4 param_3,uint *param_4)

{
  ushort uVar1;
  uint uVar2;
  uint *puVar3;
  uint uVar4;
  uint uVar5;
  
  puVar3 = param_1;
  if (3 < param_2) {
    param_1 = (uint *)(param_2 >> 2);
    do {
      uVar4 = ((uint)*(ushort *)((int)puVar3 + 2) * 0x100 + (uint)*(byte *)((int)puVar3 + 1)) *
              0x100 + (uint)(byte)*puVar3 ^ *param_4;
      uVar1 = FUN_004143a0((ushort)uVar4,(ushort)param_3);
      uVar5 = (uint)uVar1 ^ uVar4 >> 0x10;
      uVar2 = FUN_004143a0((ushort)uVar5,(ushort)((uint)param_3 >> 0x10));
      uVar5 = (uVar2 & 0xffff ^ uVar4 & 0xffff) << 0x10 | uVar5;
      param_2 = param_2 - 4;
      *puVar3 = uVar5;
      *param_4 = uVar5;
      puVar3 = puVar3 + 1;
      param_1 = (uint *)((int)param_1 + -1);
    } while (param_1 != (uint *)0x0);
  }
  for (; param_2 != 0; param_2 = param_2 - 1) {
    *(byte *)puVar3 = (byte)*puVar3 ^ 0x3d;
    puVar3 = (uint *)((int)puVar3 + 1);
  }
  return;
}



/* 004143a0 FUN_004143a0 */

void __cdecl FUN_004143a0(ushort param_1,ushort param_2)

{
  uint uVar1;
  uint uVar2;
  
  uVar1 = FUN_004143d0(param_1);
  uVar2 = FUN_004143d0(param_2);
  uVar1 = FUN_00414410(uVar2 ^ uVar1);
  FUN_00414450(uVar1);
  return;
}



/* 004143d0 FUN_004143d0 */

uint __cdecl FUN_004143d0(ushort param_1)

{
  undefined2 *puVar1;
  byte bVar2;
  uint uVar3;
  
  uVar3 = 0;
  bVar2 = 0;
  puVar1 = &DAT_0042c6f8;
  do {
    if ((param_1 >> ((byte)*puVar1 & 0x1f) & 1) != 0) {
      uVar3 = uVar3 | 1 << (bVar2 & 0x1f);
    }
    puVar1 = puVar1 + 2;
    bVar2 = bVar2 + 1;
  } while ((int)puVar1 < 0x42c758);
  return uVar3;
}



/* 00414410 FUN_00414410 */

uint __cdecl FUN_00414410(uint param_1)

{
  uint uVar1;
  byte bVar2;
  int iVar3;
  int iVar4;
  int iVar5;
  byte bVar6;
  
  uVar1 = 0;
  iVar5 = 0;
  iVar3 = 0;
  bVar6 = 0;
  do {
    bVar2 = (byte)iVar3;
    iVar3 = iVar3 + 6;
    iVar4 = (param_1 >> (bVar2 & 0x1f) & 0x3f) + iVar5;
    iVar5 = iVar5 + 0x40;
    uVar1 = uVar1 | CONCAT22((short)((uint)iVar4 >> 0x10),
                             *(short *)(&DAT_0042c758 + iVar4 * 4) << (bVar6 & 0x1f));
    bVar6 = bVar6 + 4;
  } while (iVar3 < 0x18);
  return uVar1;
}



/* 00414450 FUN_00414450 */

uint __cdecl FUN_00414450(uint param_1)

{
  uint uVar1;
  undefined4 *puVar2;
  byte bVar3;
  
  uVar1 = 0;
  bVar3 = 0;
  puVar2 = &DAT_0042cb58;
  do {
    if ((param_1 & 0xffff & 1 << (bVar3 & 0x1f)) != 0) {
      uVar1 = uVar1 | 1 << ((byte)*puVar2 & 0x1f);
    }
    puVar2 = puVar2 + 1;
    bVar3 = bVar3 + 1;
  } while ((int)puVar2 < 0x42cb98);
  return uVar1;
}



/* 00414490 FUN_00414490 */

undefined4 __thiscall FUN_00414490(void *this,char *param_1,undefined4 *param_2)

{
  FILE *pFVar1;
  uint uVar2;
  int iVar3;
  
  pFVar1 = (FILE *)FUN_004128c0(this,param_1,0);
  if (pFVar1 != (FILE *)0xffffffff) {
    FUN_00412f00();
    uVar2 = FUN_004129b0((int)pFVar1);
    while (uVar2 == 0) {
      iVar3 = FUN_00412990((int *)pFVar1,(char *)&DAT_004542e0,0x1000);
      if (iVar3 != -1) {
        FUN_00412f10(&DAT_004542e0,iVar3);
      }
      uVar2 = FUN_004129b0((int)pFVar1);
    }
    FUN_00412f30(param_2);
    FUN_00412960(pFVar1);
    return 0;
  }
  FUN_0040aa66((byte *)s_Open_file_for_hash_module_failed_00431190);
  return 0xfffffff4;
}



/* 00414520 FUN_00414520 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined4 __thiscall FUN_00414520(void *this,char *param_1,uint *param_2)

{
  FILE *pFVar1;
  FILE *pFVar2;
  uint uVar3;
  int iVar4;
  void *this_00;
  
  pFVar1 = (FILE *)FUN_004128c0(this,param_1,0);
  if (pFVar1 == (FILE *)0xffffffff) {
    FUN_0040aa66((byte *)s_Open__s_failed_0043123c);
    return 0xfffffff4;
  }
  pFVar2 = (FILE *)FUN_004128c0(this_00,s__mnt_ms__up_sig_tmp_00431228,1);
  if (pFVar2 != (FILE *)0xffffffff) {
    FUN_00412990((int *)pFVar1,(char *)&DAT_004542e0,0x78);
    FUN_004129c0((int *)pFVar2,(char *)&DAT_004542e0,0x78);
    _DAT_004552e0 = 0x4be7c72;
    FUN_004142d0(param_2,0x20,DAT_004542e0,(uint *)&DAT_004552e0);
    FUN_00412b10(s_Encrypted_digest__004311e8,(int)param_2,0x20);
    FUN_004129c0((int *)pFVar2,(char *)param_2,0x20);
    FUN_00412990((int *)pFVar1,(char *)&DAT_004542e0,0x20);
    uVar3 = FUN_004129b0((int)pFVar1);
    while (uVar3 == 0) {
      uVar3 = FUN_00412990((int *)pFVar1,(char *)&DAT_004542e0,0x1000);
      if (uVar3 != 0xffffffff) {
        FUN_004129c0((int *)pFVar2,(char *)&DAT_004542e0,uVar3);
        FUN_0040aa66(&DAT_004311e4);
      }
      uVar3 = FUN_004129b0((int)pFVar1);
    }
    FUN_0040aa66(&DAT_0042fcbc);
    FUN_00412960(pFVar1);
    FUN_00412960(pFVar2);
    iVar4 = FUN_00412a70();
    if (iVar4 != -1) {
      iVar4 = FUN_00412ab0();
      if (iVar4 != -1) {
        return 0;
      }
      FUN_0040aa66((byte *)s_Rename_file__s_failed_004311b4);
      return 0xfffffff2;
    }
    FUN_0040aa66((byte *)s_Remove_file__s_failed_004311cc);
    return 0xfffffff3;
  }
  FUN_0040aa66((byte *)s_open__up_sig_tmp_failed_0043120c);
  return 0xfffffff4;
}



/* 004146a0 FUN_004146a0 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined4 __cdecl FUN_004146a0(char *param_1,int *param_2,undefined4 param_3)

{
  int iVar1;
  FILE *pFVar2;
  uint uVar3;
  uint local_400 [256];
  
  while( true ) {
    if (param_1 == (char *)0x0) {
      return 0;
    }
    iVar1 = FUN_00412a00(*(LPCSTR *)(param_1 + 0x14));
    _DAT_004552e0 = 0x4be7c72;
    pFVar2 = (FILE *)FUN_004128c0(*(void **)(param_1 + 0x14),*(void **)(param_1 + 0x14),0);
    if (pFVar2 == (FILE *)0xffffffff) break;
    *(int *)(param_1 + 0xc) = iVar1;
    uVar3 = FUN_004129c0(param_2,param_1,4);
    if (((uVar3 == 0xffffffff) || (uVar3 = FUN_004129c0(param_2,param_1 + 5,4), uVar3 == 0xffffffff)
        ) || (uVar3 = FUN_004129c0(param_2,param_1 + 0xc,4), uVar3 == 0xffffffff)) {
      FUN_0040aa66((byte *)s_Write_append_data__s_type_failed_00431120);
      return 0xfffffff0;
    }
    uVar3 = FUN_004129b0((int)pFVar2);
    while (uVar3 == 0) {
      uVar3 = FUN_00412990((int *)pFVar2,(char *)local_400,0x400);
      if (uVar3 != 0xffffffff) {
        if (param_1[5] != '\0') {
          FUN_004142d0(local_400,uVar3,param_3,(uint *)&DAT_004552e0);
        }
        uVar3 = FUN_004129c0(param_2,(char *)local_400,uVar3);
        if (uVar3 == 0xffffffff) {
          FUN_0040aa66((byte *)s_Copy_append_data__s_failed_00431104);
          return 0xfffffff0;
        }
      }
      uVar3 = FUN_004129b0((int)pFVar2);
    }
    FUN_00412960(pFVar2);
    param_1 = *(char **)(param_1 + 0x18);
  }
  FUN_0040aa66((byte *)s_Open_append_data_file__s_failed_00431144);
  return 0xfffffff4;
}



/* 00414830 FUN_00414830 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined4 __thiscall FUN_00414830(void *this,undefined4 *param_1,char *param_2,char *param_3)

{
  char *pcVar1;
  FILE *pFVar2;
  uint uVar3;
  int iVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  
  pFVar2 = (FILE *)FUN_004128c0(this,param_3,1);
  if (pFVar2 == (FILE *)0xffffffff) {
    FUN_0040aa66((byte *)s_Open_output_codefile__s_failed_004310e4);
    return 0xfffffff4;
  }
  puVar5 = param_1;
  puVar6 = &DAT_004542e0;
  for (iVar4 = 0x26; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar6 = *puVar5;
    puVar5 = puVar5 + 1;
    puVar6 = puVar6 + 1;
  }
  _DAT_00454330 = 0x98;
  for (pcVar1 = param_2; pcVar1 != (char *)0x0; pcVar1 = *(char **)(pcVar1 + 0x18)) {
    _DAT_00454330 = _DAT_00454330 + *(int *)(pcVar1 + 0xc);
  }
  FUN_0040aa66((byte *)s_Code_size____u_004310d4);
  DAT_004542e4 = DAT_004310c8;
  _DAT_004542e8 = DAT_004310cc;
  _DAT_004552e0 = 0x4be7c75;
  FUN_004142d0(&DAT_004542e0,0x78,0x94102909,(uint *)&DAT_004552e0);
  FUN_00412b10(s_Encrypted_Header__004310b4,0x4542e0,0x78);
  uVar3 = FUN_004129c0((int *)pFVar2,(char *)&DAT_004542e0,0x98);
  if (uVar3 != 0xffffffff) {
    iVar4 = FUN_004146a0(param_2,(int *)pFVar2,*param_1);
    if (iVar4 != 0) {
      FUN_0040aa66((byte *)s_Add_append_data_failed_0043109c);
      FUN_0040d1bb(0xffffffff);
    }
    FUN_00412960(pFVar2);
    return 0;
  }
  FUN_00412960(pFVar2);
  return 0xfffffff0;
}



/* 00414950 FUN_00414950 */

uint __cdecl FUN_00414950(undefined4 param_1,byte *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  return 1 << (param_2[1] & 0x1f) &
         *(uint *)(&DAT_0042cb98 +
                  ((*param_2 & 3) * 2 +
                   (uint)(byte)(&DAT_0042d198)[(int)(uint)*param_2 >> 2 & 7] * 8 +
                  ((int)(uint)param_2[1] >> 5 & 1U)) * 4);
}



/* 004149c0 FUN_004149c0 */

uint __cdecl FUN_004149c0(undefined4 param_1,byte *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  return 1 << (param_2[2] & 0x1f) &
         *(uint *)(&DAT_0042cb98 +
                  ((param_2[1] & 3) * 2 +
                   (uint)(byte)(&DAT_0042d198)
                               [((int)(uint)param_2[1] >> 2 & 0xfU) + (*param_2 & 0xf) * 0x10] * 8 +
                  ((int)(uint)param_2[2] >> 5 & 1U)) * 4);
}



/* 00414a40 FUN_00414a40 */

uint __cdecl FUN_00414a40(undefined4 param_1,byte *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  return 1 << (param_2[1] & 0x1f) &
         *(uint *)(&DAT_0042cb98 +
                  ((*param_2 & 3) * 2 +
                   (uint)(byte)(&DAT_0042d098)[(int)(uint)*param_2 >> 2 & 7] * 8 +
                  ((int)(uint)param_2[1] >> 5 & 1U)) * 4);
}



/* 00414ab0 FUN_00414ab0 */

uint __cdecl FUN_00414ab0(undefined4 param_1,byte *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  return 1 << (param_2[2] & 0x1f) &
         *(uint *)(&DAT_0042cb98 +
                  ((param_2[1] & 3) * 2 +
                   (uint)(byte)(&DAT_0042d098)
                               [((int)(uint)param_2[1] >> 2 & 0xfU) + (*param_2 & 0xf) * 0x10] * 8 +
                  ((int)(uint)param_2[2] >> 5 & 1U)) * 4);
}



/* 00414b30 FUN_00414b30 */

undefined4 __cdecl FUN_00414b30(undefined4 param_1,byte *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  undefined4 local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if (((*param_2 < 0xc2) || ((param_2[1] & 0x80) == 0)) || ((param_2[1] & 0xc0) == 0xc0)) {
    local_8 = 1;
  }
  else {
    local_8 = 0;
  }
  return local_8;
}



/* 00414ba0 FUN_00414ba0 */

undefined4 __cdecl FUN_00414ba0(undefined4 param_1,char *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  bool bVar3;
  undefined4 local_5c [22];
  
  puVar2 = local_5c;
  for (iVar1 = 0x16; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if ((param_2[2] & 0x80U) == 0) {
    return 1;
  }
  if ((*param_2 == -0x11) && (param_2[1] == -0x41)) {
    bVar3 = 0xbd < (byte)param_2[2];
  }
  else {
    bVar3 = (param_2[2] & 0xc0U) == 0xc0;
  }
  if (bVar3) {
    return 1;
  }
  if (*param_2 == -0x20) {
    if (((byte)param_2[1] < 0xa0) || ((param_2[1] & 0xc0U) == 0xc0)) {
      bVar3 = true;
    }
    else {
      bVar3 = false;
    }
  }
  else {
    if ((param_2[1] & 0x80U) != 0) {
      if (*param_2 == -0x13) {
        bVar3 = 0x9f < (byte)param_2[1];
      }
      else {
        bVar3 = (param_2[1] & 0xc0U) == 0xc0;
      }
      if (!bVar3) {
        bVar3 = false;
        goto LAB_00414ce4;
      }
    }
    bVar3 = true;
  }
LAB_00414ce4:
  if (bVar3) {
    return 1;
  }
  return 0;
}



/* 00414d10 FUN_00414d10 */

undefined4 __cdecl FUN_00414d10(undefined4 param_1,char *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  bool bVar3;
  undefined4 local_58 [21];
  
  puVar2 = local_58;
  for (iVar1 = 0x15; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if ((param_2[3] & 0x80U) == 0) {
    return 1;
  }
  if ((param_2[3] & 0xc0U) == 0xc0) {
    return 1;
  }
  if ((param_2[2] & 0x80U) == 0) {
    return 1;
  }
  if ((param_2[2] & 0xc0U) == 0xc0) {
    return 1;
  }
  if (*param_2 == -0x10) {
    if (((byte)param_2[1] < 0x90) || ((param_2[1] & 0xc0U) == 0xc0)) {
      bVar3 = true;
    }
    else {
      bVar3 = false;
    }
  }
  else {
    if ((param_2[1] & 0x80U) != 0) {
      if (*param_2 == -0xc) {
        bVar3 = 0x8f < (byte)param_2[1];
      }
      else {
        bVar3 = (param_2[1] & 0xc0U) == 0xc0;
      }
      if (!bVar3) {
        bVar3 = false;
        goto LAB_00414e42;
      }
    }
    bVar3 = true;
  }
LAB_00414e42:
  if (bVar3) {
    return 1;
  }
  return 0;
}



/* 00414e70 FUN_00414e70 */

/* WARNING: Removing unreachable block (ram,0x00414ea0) */
/* WARNING: Removing unreachable block (ram,0x00414eaf) */
/* WARNING: Removing unreachable block (ram,0x00414ebb) */
/* WARNING: Removing unreachable block (ram,0x00414ec3) */

void __cdecl FUN_00414e70(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_50 [16];
  uint local_10;
  uint local_c;
  undefined4 local_8;
  
  puVar3 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    switch(local_c) {
    case 0:
    case 1:
    case 8:
      *param_4 = param_2;
      break;
    default:
      pbVar2 = param_2 + 1;
LAB_004150cc:
      do {
        param_2 = pbVar2;
        if (param_2 == param_3) goto LAB_004151f4;
        local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        switch(local_10) {
        case 0:
        case 1:
        case 4:
        case 8:
        case 9:
        case 10:
          *param_4 = param_2;
          goto LAB_00415201;
        default:
          pbVar2 = param_2 + 1;
          break;
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            (**(code **)(param_1 + 0x160))(param_1,param_2);
            iVar1 = FUN_004291a7();
            if (iVar1 == 0) {
              pbVar2 = param_2 + 2;
              break;
            }
          }
          *param_4 = param_2;
          goto LAB_00415201;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            (**(code **)(param_1 + 0x164))(param_1,param_2);
            iVar1 = FUN_004291a7();
            if (iVar1 == 0) {
              pbVar2 = param_2 + 3;
              break;
            }
          }
          *param_4 = param_2;
          goto LAB_00415201;
        case 7:
          if ((int)param_3 - (int)param_2 < 4) {
LAB_004151bd:
            *param_4 = param_2;
            goto LAB_00415201;
          }
          (**(code **)(param_1 + 0x168))(param_1,param_2);
          iVar1 = FUN_004291a7();
          if (iVar1 != 0) goto LAB_004151bd;
          pbVar2 = param_2 + 4;
        }
      } while( true );
    case 4:
      pbVar2 = param_2 + 1;
      if (pbVar2 != param_3) {
        if (*pbVar2 != 0x5d) goto LAB_004150cc;
        if (param_2 + 2 != param_3) {
          if (param_2[2] != 0x3e) {
            pbVar2 = param_2 + 1;
            goto LAB_004150cc;
          }
          *param_4 = param_2 + 3;
        }
      }
      break;
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x160))(param_1,param_2);
        iVar1 = FUN_004291a7();
        if (iVar1 == 0) {
          pbVar2 = param_2 + 2;
          goto LAB_004150cc;
        }
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x164))(param_1,param_2);
        iVar1 = FUN_004291a7();
        if (iVar1 == 0) {
          pbVar2 = param_2 + 3;
          goto LAB_004150cc;
        }
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x168))(param_1,param_2);
        iVar1 = FUN_004291a7();
        if (iVar1 == 0) {
          pbVar2 = param_2 + 4;
          goto LAB_004150cc;
        }
        *param_4 = param_2;
      }
      break;
    case 9:
      pbVar2 = param_2 + 1;
      if (pbVar2 != param_3) {
        if (*(char *)(param_1 + 0x48 + (uint)*pbVar2) == '\n') {
          pbVar2 = param_2 + 2;
        }
        param_2 = pbVar2;
        *param_4 = param_2;
      }
      break;
    case 10:
      *param_4 = param_2 + 1;
    }
  }
LAB_00415201:
  local_8 = 0x41520e;
  FUN_004291a7();
  return;
LAB_004151f4:
  *param_4 = param_2;
  goto LAB_00415201;
}



/* 00415270 FUN_00415270 */

/* WARNING: Removing unreachable block (ram,0x004152a0) */
/* WARNING: Removing unreachable block (ram,0x004152af) */
/* WARNING: Removing unreachable block (ram,0x004152bb) */
/* WARNING: Removing unreachable block (ram,0x004152c3) */

void __cdecl FUN_00415270(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_50 [16];
  uint local_10;
  uint local_c;
  undefined4 local_8;
  
  puVar3 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_0041569d;
  local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  switch(local_c) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_0041569d;
  case 2:
    FUN_00415c60(param_1,param_2 + 1,param_3,param_4);
    goto LAB_0041569d;
  case 3:
    FUN_00415710(param_1,param_2 + 1,param_3,param_4);
    goto LAB_0041569d;
  case 4:
    pbVar2 = param_2 + 1;
    if (pbVar2 == param_3) goto LAB_0041569d;
    if (*pbVar2 == 0x5d) {
      pbVar2 = param_2 + 2;
      if (pbVar2 == param_3) goto LAB_0041569d;
      if (*pbVar2 == 0x3e) {
        *param_4 = pbVar2;
        goto LAB_0041569d;
      }
      pbVar2 = param_2 + 1;
    }
    break;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_0041569d;
    (**(code **)(param_1 + 0x160))(param_1,param_2);
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_4 = param_2;
      goto LAB_0041569d;
    }
    pbVar2 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_0041569d;
    (**(code **)(param_1 + 0x164))(param_1,param_2);
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_4 = param_2;
      goto LAB_0041569d;
    }
    pbVar2 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_0041569d;
    (**(code **)(param_1 + 0x168))(param_1,param_2);
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_4 = param_2;
      goto LAB_0041569d;
    }
    pbVar2 = param_2 + 4;
    break;
  case 9:
    pbVar2 = param_2 + 1;
    if (pbVar2 != param_3) {
      if (*(char *)(param_1 + 0x48 + (uint)*pbVar2) == '\n') {
        pbVar2 = param_2 + 2;
      }
      param_2 = pbVar2;
      *param_4 = param_2;
    }
    goto LAB_0041569d;
  case 10:
    *param_4 = param_2 + 1;
    goto LAB_0041569d;
  default:
    pbVar2 = param_2 + 1;
  }
LAB_0041550c:
  param_2 = pbVar2;
  if (param_2 != param_3) {
    local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    switch(local_10) {
    case 4:
      if (param_2 + 1 != param_3) {
        if (param_2[1] != 0x5d) {
          pbVar2 = param_2 + 1;
          goto LAB_0041550c;
        }
        if (param_2 + 2 != param_3) {
          if (param_2[2] == 0x3e) {
            *param_4 = param_2 + 2;
            goto LAB_0041569d;
          }
          pbVar2 = param_2 + 1;
          goto LAB_0041550c;
        }
      }
    case 0:
    case 1:
    case 2:
    case 3:
    case 8:
    case 9:
    case 10:
      *param_4 = param_2;
      goto LAB_0041569d;
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x160))(param_1,param_2);
        iVar1 = FUN_004291a7();
        if (iVar1 == 0) {
          pbVar2 = param_2 + 2;
          goto LAB_0041550c;
        }
      }
      *param_4 = param_2;
      goto LAB_0041569d;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x164))(param_1,param_2);
        iVar1 = FUN_004291a7();
        if (iVar1 == 0) {
          pbVar2 = param_2 + 3;
          goto LAB_0041550c;
        }
      }
      *param_4 = param_2;
      goto LAB_0041569d;
    case 7:
      goto switchD_00415538_caseD_7;
    default:
      goto switchD_00415538_default;
    }
  }
  *param_4 = param_2;
LAB_0041569d:
  local_8 = 0x4156aa;
  FUN_004291a7();
  return;
switchD_00415538_default:
  pbVar2 = param_2 + 1;
  goto LAB_0041550c;
switchD_00415538_caseD_7:
  if (3 < (int)param_3 - (int)param_2) {
    (**(code **)(param_1 + 0x168))(param_1,param_2);
    iVar1 = FUN_004291a7();
    if (iVar1 == 0) {
      pbVar2 = param_2 + 4;
      goto LAB_0041550c;
    }
  }
  *param_4 = param_2;
  goto LAB_0041569d;
}



/* 00415710 FUN_00415710 */

void __cdecl FUN_00415710(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [16];
  int local_c;
  int local_8;
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_00415a32;
  bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
  local_8 = bVar1 - 5;
  switch(bVar1) {
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_00415a32;
    (**(code **)(param_1 + 0x154))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_00415a32;
    }
    param_2 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_00415a32;
    (**(code **)(param_1 + 0x158))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_00415a32;
    }
    param_2 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_00415a32;
    (**(code **)(param_1 + 0x15c))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_00415a32;
    }
    param_2 = param_2 + 4;
    break;
  default:
    *param_4 = param_2;
    goto LAB_00415a32;
  case 0x13:
    FUN_00415ab0(param_1,param_2 + 1,param_3,param_4);
    goto LAB_00415a32;
  case 0x16:
  case 0x18:
    param_2 = param_2 + 1;
    break;
  case 0x1d:
    *param_4 = param_2;
    goto LAB_00415a32;
  }
  while (param_2 != param_3) {
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    local_c = bVar1 - 5;
    switch(bVar1) {
    case 5:
      if ((int)param_3 - (int)param_2 < 2) goto LAB_00415a32;
      (**(code **)(param_1 + 0x148))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_00415a32;
      }
      param_2 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) goto LAB_00415a32;
      (**(code **)(param_1 + 0x14c))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_00415a32;
      }
      param_2 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) goto LAB_00415a32;
      (**(code **)(param_1 + 0x150))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_00415a32;
      }
      param_2 = param_2 + 4;
      break;
    default:
      *param_4 = param_2;
      goto LAB_00415a32;
    case 0x12:
      *param_4 = param_2 + 1;
      goto LAB_00415a32;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
      param_2 = param_2 + 1;
      break;
    case 0x1d:
      *param_4 = param_2;
      goto LAB_00415a32;
    }
  }
LAB_00415a32:
  local_8 = 0x415a3f;
  FUN_004291a7();
  return;
}



/* 00415ab0 FUN_00415ab0 */

void __cdecl FUN_00415ab0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [16];
  char local_c;
  undefined4 local_8;
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == 0x78) {
      FUN_00415ba0(param_1,param_2 + 1,param_3,param_4);
    }
    else {
      pbVar1 = param_2;
      if (*(char *)(param_1 + 0x48 + (uint)*param_2) == '\x19') {
        do {
          param_2 = pbVar1 + 1;
          if (param_2 == param_3) goto LAB_00415b86;
          local_c = *(char *)(param_1 + 0x48 + (uint)*param_2);
          if (local_c == '\x12') {
            *param_4 = pbVar1 + 2;
            goto LAB_00415b86;
          }
          pbVar1 = param_2;
        } while (local_c == '\x19');
        *param_4 = param_2;
      }
      else {
        *param_4 = param_2;
      }
    }
  }
LAB_00415b86:
  local_8 = 0x415b93;
  FUN_004291a7();
  return;
}



/* 00415ba0 FUN_00415ba0 */

undefined4 __cdecl FUN_00415ba0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  byte *pbVar2;
  undefined4 uVar3;
  int iVar4;
  undefined4 *puVar5;
  undefined4 local_4c [18];
  
  puVar5 = local_4c;
  for (iVar4 = 0x12; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
  if (param_2 == param_3) {
LAB_00415c54:
    uVar3 = 0xffffffff;
  }
  else {
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    if ((bVar1 < 0x18) || (pbVar2 = param_2, 0x19 < bVar1)) {
      *param_4 = param_2;
      uVar3 = 0;
    }
    else {
      do {
        param_2 = pbVar2 + 1;
        if (param_2 == param_3) goto LAB_00415c54;
        bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
        if (bVar1 == 0x12) {
          *param_4 = pbVar2 + 2;
          return 10;
        }
      } while ((0x17 < bVar1) && (pbVar2 = param_2, bVar1 < 0x1a));
      *param_4 = param_2;
      uVar3 = 0;
    }
  }
  return uVar3;
}



/* 00415c60 FUN_00415c60 */

void __cdecl FUN_00415c60(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_54 [16];
  int local_14;
  int local_10;
  char local_c;
  int local_8;
  
  puVar4 = local_54;
  for (iVar3 = 0x14; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  if (param_2 == param_3) goto LAB_00416223;
  bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
  local_8 = bVar1 - 5;
  switch(bVar1) {
  case 5:
    if ((int)param_3 - (int)param_2 < 2) break;
    (**(code **)(param_1 + 0x154))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      *param_4 = param_2;
      break;
    }
    param_2 = param_2 + 2;
    goto LAB_00415eb5;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) break;
    (**(code **)(param_1 + 0x158))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      *param_4 = param_2;
      break;
    }
    param_2 = param_2 + 3;
    goto LAB_00415eb5;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) break;
    (**(code **)(param_1 + 0x15c))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      *param_4 = param_2;
      break;
    }
    param_2 = param_2 + 4;
    goto LAB_00415eb5;
  default:
    *param_4 = param_2;
    break;
  case 0xf:
    FUN_00416530(param_1,param_2 + 1,param_3,param_4);
    break;
  case 0x10:
    pbVar2 = param_2 + 1;
    if (pbVar2 != param_3) {
      local_c = *(char *)(param_1 + 0x48 + (uint)*pbVar2);
      if (local_c == '\x14') {
        FUN_00416bf0(param_1,(char *)(param_2 + 2),(int)param_3,param_4);
      }
      else if (local_c == '\x1b') {
        FUN_004162f0(param_1,param_2 + 2,param_3,param_4);
      }
      else {
        *param_4 = pbVar2;
      }
    }
    break;
  case 0x11:
    FUN_00416c70(param_1,param_2 + 1,param_3,param_4);
    break;
  case 0x16:
  case 0x18:
    param_2 = param_2 + 1;
LAB_00415eb5:
    while (param_2 != param_3) {
      bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
      local_10 = bVar1 - 5;
      switch(bVar1) {
      case 5:
        if ((int)param_3 - (int)param_2 < 2) goto LAB_00416223;
        (**(code **)(param_1 + 0x148))(param_1,param_2);
        iVar3 = FUN_004291a7();
        if (iVar3 == 0) {
          *param_4 = param_2;
          goto LAB_00416223;
        }
        param_2 = param_2 + 2;
        break;
      case 6:
        if ((int)param_3 - (int)param_2 < 3) goto LAB_00416223;
        (**(code **)(param_1 + 0x14c))(param_1,param_2);
        iVar3 = FUN_004291a7();
        if (iVar3 == 0) {
          *param_4 = param_2;
          goto LAB_00416223;
        }
        param_2 = param_2 + 3;
        break;
      case 7:
        if ((int)param_3 - (int)param_2 < 4) goto LAB_00416223;
        (**(code **)(param_1 + 0x150))(param_1,param_2);
        iVar3 = FUN_004291a7();
        if (iVar3 == 0) {
          *param_4 = param_2;
          goto LAB_00416223;
        }
        param_2 = param_2 + 4;
        break;
      default:
        *param_4 = param_2;
        goto LAB_00416223;
      case 9:
      case 10:
      case 0x15:
        pbVar2 = param_2;
        goto LAB_00416021;
      case 0xb:
        goto switchD_00415ef2_caseD_b;
      case 0x11:
        goto switchD_00415ef2_caseD_11;
      case 0x16:
      case 0x18:
      case 0x19:
      case 0x1a:
      case 0x1b:
        param_2 = param_2 + 1;
        break;
      case 0x1d:
        *param_4 = param_2;
        goto LAB_00416223;
      }
    }
    break;
  case 0x1d:
    *param_4 = param_2;
  }
LAB_00416223:
  local_8 = 0x416230;
  FUN_004291a7();
  return;
LAB_00416021:
  param_2 = pbVar2 + 1;
  if (param_2 != param_3) {
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    local_14 = bVar1 - 5;
    switch(bVar1) {
    case 5:
      if ((int)param_3 - (int)param_2 < 2) goto LAB_00416223;
      (**(code **)(param_1 + 0x154))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        goto LAB_00416223;
      }
      param_2 = pbVar2 + 3;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) goto LAB_00416223;
      (**(code **)(param_1 + 0x158))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        goto LAB_00416223;
      }
      param_2 = pbVar2 + 4;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) goto LAB_00416223;
      (**(code **)(param_1 + 0x15c))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        goto LAB_00416223;
      }
      param_2 = pbVar2 + 5;
      break;
    default:
      *param_4 = param_2;
      goto LAB_00416223;
    case 9:
    case 10:
    case 0x15:
      goto switchD_0041605e_caseD_9;
    case 0xb:
switchD_00415ef2_caseD_b:
      *param_4 = param_2 + 1;
      goto LAB_00416223;
    case 0x11:
switchD_00415ef2_caseD_11:
      pbVar2 = param_2 + 1;
      if (pbVar2 != param_3) {
        if (*pbVar2 == 0x3e) {
          *param_4 = param_2 + 2;
        }
        else {
          *param_4 = pbVar2;
        }
      }
      goto LAB_00416223;
    case 0x16:
    case 0x18:
      param_2 = pbVar2 + 2;
      break;
    case 0x1d:
      *param_4 = param_2;
      goto LAB_00416223;
    }
    FUN_00417090(param_1,param_2,param_3,param_4);
  }
  goto LAB_00416223;
switchD_0041605e_caseD_9:
  pbVar2 = param_2;
  goto LAB_00416021;
}



/* 004162f0 FUN_004162f0 */

void __cdecl FUN_004162f0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [16];
  uint local_8;
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == 0x2d) {
      pbVar1 = param_2 + 1;
LAB_00416337:
      param_2 = pbVar1;
      if (param_2 == param_3) goto LAB_004164e7;
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
      switch(local_8) {
      case 0:
      case 1:
      case 8:
        *param_4 = param_2;
        goto LAB_004164e7;
      default:
        pbVar1 = param_2 + 1;
        goto LAB_00416337;
      case 5:
        if ((int)param_3 - (int)param_2 < 2) goto LAB_004164e7;
        (**(code **)(param_1 + 0x160))(param_1,param_2);
        iVar2 = FUN_004291a7();
        if (iVar2 != 0) {
          *param_4 = param_2;
          goto LAB_004164e7;
        }
        pbVar1 = param_2 + 2;
        goto LAB_00416337;
      case 6:
        if ((int)param_3 - (int)param_2 < 3) goto LAB_004164e7;
        (**(code **)(param_1 + 0x164))(param_1,param_2);
        iVar2 = FUN_004291a7();
        if (iVar2 != 0) {
          *param_4 = param_2;
          goto LAB_004164e7;
        }
        pbVar1 = param_2 + 3;
        goto LAB_00416337;
      case 7:
        if ((int)param_3 - (int)param_2 < 4) goto LAB_004164e7;
        (**(code **)(param_1 + 0x168))(param_1,param_2);
        iVar2 = FUN_004291a7();
        if (iVar2 != 0) {
          *param_4 = param_2;
          goto LAB_004164e7;
        }
        pbVar1 = param_2 + 4;
        goto LAB_00416337;
      case 0x1b:
        goto switchD_0041636b_caseD_1b;
      }
    }
    *param_4 = param_2;
  }
LAB_004164e7:
  local_8 = 0x4164f4;
  FUN_004291a7();
  return;
switchD_0041636b_caseD_1b:
  pbVar1 = param_2 + 1;
  if (pbVar1 == param_3) goto LAB_004164e7;
  if (*pbVar1 == 0x2d) goto code_r0x00416495;
  goto LAB_00416337;
code_r0x00416495:
  pbVar1 = param_2 + 2;
  if (pbVar1 != param_3) {
    if (*pbVar1 == 0x3e) {
      *param_4 = param_2 + 3;
    }
    else {
      *param_4 = pbVar1;
    }
  }
  goto LAB_004164e7;
}



/* 00416530 FUN_00416530 */

void __cdecl FUN_00416530(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  int iVar2;
  byte *pbVar3;
  undefined4 *puVar4;
  undefined4 local_58 [16];
  uint local_18;
  int local_14;
  int local_10;
  byte *local_c;
  undefined4 local_8;
  
  puVar4 = local_58;
  for (iVar2 = 0x15; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  local_c = param_2;
  if (param_2 != param_3) {
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    local_10 = bVar1 - 5;
    switch(bVar1) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x154))(param_1,param_2);
        iVar2 = FUN_004291a7();
        if (iVar2 != 0) {
          param_2 = param_2 + 2;
          goto LAB_004166be;
        }
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x158))(param_1,param_2);
        iVar2 = FUN_004291a7();
        if (iVar2 != 0) {
          param_2 = param_2 + 3;
          goto LAB_004166be;
        }
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        (**(code **)(param_1 + 0x15c))(param_1,param_2);
        iVar2 = FUN_004291a7();
        if (iVar2 != 0) {
          param_2 = param_2 + 4;
          goto LAB_004166be;
        }
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x16:
    case 0x18:
      param_2 = param_2 + 1;
LAB_004166be:
      while (param_2 != param_3) {
        bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
        local_14 = bVar1 - 5;
        pbVar3 = param_2;
        switch(bVar1) {
        case 5:
          if ((int)param_3 - (int)param_2 < 2) goto LAB_00416a4e;
          (**(code **)(param_1 + 0x148))(param_1,param_2);
          iVar2 = FUN_004291a7();
          if (iVar2 == 0) {
            *param_4 = param_2;
            goto LAB_00416a4e;
          }
          param_2 = param_2 + 2;
          break;
        case 6:
          if ((int)param_3 - (int)param_2 < 3) goto LAB_00416a4e;
          (**(code **)(param_1 + 0x14c))(param_1,param_2);
          iVar2 = FUN_004291a7();
          if (iVar2 == 0) {
            *param_4 = param_2;
            goto LAB_00416a4e;
          }
          param_2 = param_2 + 3;
          break;
        case 7:
          if ((int)param_3 - (int)param_2 < 4) goto LAB_00416a4e;
          (**(code **)(param_1 + 0x150))(param_1,param_2);
          iVar2 = FUN_004291a7();
          if (iVar2 == 0) {
            *param_4 = param_2;
            goto LAB_00416a4e;
          }
          param_2 = param_2 + 4;
          break;
        case 9:
        case 10:
        case 0x15:
          iVar2 = FUN_00416b00(param_1,(char *)local_c,(int)param_2,&local_8);
          if (iVar2 != 0) {
            pbVar3 = param_2 + 1;
            goto LAB_00416855;
          }
          *param_4 = param_2;
          goto LAB_00416a4e;
        case 0xf:
          iVar2 = FUN_00416b00(param_1,(char *)local_c,(int)param_2,&local_8);
          if (iVar2 == 0) {
            *param_4 = param_2;
            goto LAB_00416a4e;
          }
          pbVar3 = param_2 + 1;
          if (pbVar3 == param_3) goto LAB_00416a4e;
          if (*pbVar3 == 0x3e) {
            *param_4 = param_2 + 2;
            goto LAB_00416a4e;
          }
        default:
          param_2 = pbVar3;
          *param_4 = param_2;
          goto LAB_00416a4e;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto switchD_004166fb_caseD_16;
        case 0x1d:
          *param_4 = param_2;
          goto LAB_00416a4e;
        }
      }
      break;
    case 0x1d:
      *param_4 = param_2;
    }
  }
LAB_00416a4e:
  local_8 = 0x416a5b;
  FUN_004291a7();
  return;
switchD_004166fb_caseD_16:
  param_2 = param_2 + 1;
  goto LAB_004166be;
LAB_00416855:
  param_2 = pbVar3;
  if (param_2 == param_3) goto LAB_00416a4e;
  local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  switch(local_18) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_00416a4e;
  default:
    pbVar3 = param_2 + 1;
    goto LAB_00416855;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_00416a4e;
    (**(code **)(param_1 + 0x160))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 != 0) {
      *param_4 = param_2;
      goto LAB_00416a4e;
    }
    pbVar3 = param_2 + 2;
    goto LAB_00416855;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_00416a4e;
    (**(code **)(param_1 + 0x164))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 != 0) {
      *param_4 = param_2;
      goto LAB_00416a4e;
    }
    pbVar3 = param_2 + 3;
    goto LAB_00416855;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_00416a4e;
    (**(code **)(param_1 + 0x168))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 != 0) {
      *param_4 = param_2;
      goto LAB_00416a4e;
    }
    pbVar3 = param_2 + 4;
    goto LAB_00416855;
  case 0xf:
    break;
  }
  pbVar3 = param_2 + 1;
  if (pbVar3 == param_3) goto LAB_00416a4e;
  if (*pbVar3 == 0x3e) goto code_r0x004169b9;
  goto LAB_00416855;
code_r0x004169b9:
  *param_4 = param_2 + 2;
  goto LAB_00416a4e;
}



/* 00416b00 FUN_00416b00 */

undefined4 __cdecl FUN_00416b00(undefined4 param_1,char *param_2,int param_3,undefined4 *param_4)

{
  bool bVar1;
  undefined4 uVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_54 [20];
  
  puVar4 = local_54;
  for (iVar3 = 0x14; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  bVar1 = false;
  *param_4 = 0xb;
  if (param_3 - (int)param_2 == 3) {
    if (*param_2 == 'X') {
      bVar1 = true;
    }
    else if (*param_2 != 'x') {
      return 1;
    }
    if (param_2[1] == 'M') {
      bVar1 = true;
    }
    else if (param_2[1] != 'm') {
      return 1;
    }
    if (param_2[2] == 'L') {
      bVar1 = true;
    }
    else if (param_2[2] != 'l') {
      return 1;
    }
    if (bVar1) {
      uVar2 = 0;
    }
    else {
      *param_4 = 0xc;
      uVar2 = 1;
    }
  }
  else {
    uVar2 = 1;
  }
  return uVar2;
}



/* 00416bf0 FUN_00416bf0 */

undefined4 __cdecl FUN_00416bf0(undefined4 param_1,char *param_2,int param_3,undefined4 *param_4)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [16];
  int local_8;
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_3 - (int)param_2 < 6) {
    uVar1 = 0xffffffff;
  }
  else {
    for (local_8 = 0; local_8 < 6; local_8 = local_8 + 1) {
      if (*param_2 != (&DAT_0042d298)[local_8]) {
        *param_4 = param_2;
        return 0;
      }
      param_2 = param_2 + 1;
    }
    *param_4 = param_2;
    uVar1 = 8;
  }
  return uVar1;
}



/* 00416c70 FUN_00416c70 */

void __cdecl FUN_00416c70(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_50 [16];
  int local_10;
  int local_c;
  int local_8;
  
  puVar4 = local_50;
  for (iVar3 = 0x13; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  if (param_2 == param_3) goto LAB_00416ff8;
  bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
  local_8 = bVar1 - 5;
  switch(bVar1) {
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_00416ff8;
    (**(code **)(param_1 + 0x154))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      *param_4 = param_2;
      goto LAB_00416ff8;
    }
    param_2 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_00416ff8;
    (**(code **)(param_1 + 0x158))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      *param_4 = param_2;
      goto LAB_00416ff8;
    }
    param_2 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_00416ff8;
    (**(code **)(param_1 + 0x15c))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      *param_4 = param_2;
      goto LAB_00416ff8;
    }
    param_2 = param_2 + 4;
    break;
  default:
    *param_4 = param_2;
    goto LAB_00416ff8;
  case 0x16:
  case 0x18:
    param_2 = param_2 + 1;
    break;
  case 0x1d:
    *param_4 = param_2;
    goto LAB_00416ff8;
  }
  while (param_2 != param_3) {
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    local_c = bVar1 - 5;
    switch(bVar1) {
    case 5:
      if ((int)param_3 - (int)param_2 < 2) goto LAB_00416ff8;
      (**(code **)(param_1 + 0x148))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        goto LAB_00416ff8;
      }
      param_2 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) goto LAB_00416ff8;
      (**(code **)(param_1 + 0x14c))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        goto LAB_00416ff8;
      }
      param_2 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) goto LAB_00416ff8;
      (**(code **)(param_1 + 0x150))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        goto LAB_00416ff8;
      }
      param_2 = param_2 + 4;
      break;
    default:
      *param_4 = param_2;
      goto LAB_00416ff8;
    case 9:
    case 10:
    case 0x15:
      pbVar2 = param_2;
      goto LAB_00416f6f;
    case 0xb:
      *param_4 = param_2 + 1;
      goto LAB_00416ff8;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
      param_2 = param_2 + 1;
      break;
    case 0x1d:
      *param_4 = param_2;
      goto LAB_00416ff8;
    }
  }
LAB_00416ff8:
  local_8 = 0x417005;
  FUN_004291a7();
  return;
LAB_00416f6f:
  param_2 = pbVar2 + 1;
  if (param_2 == param_3) goto LAB_00416ff8;
  bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
  local_10 = bVar1 - 9;
  switch(bVar1) {
  case 9:
  case 10:
  case 0x15:
    pbVar2 = param_2;
    break;
  case 0xb:
    *param_4 = pbVar2 + 2;
    goto LAB_00416ff8;
  default:
    *param_4 = param_2;
    goto LAB_00416ff8;
  }
  goto LAB_00416f6f;
}



/* 00417090 FUN_00417090 */

void __cdecl FUN_00417090(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  uint uVar4;
  undefined4 *puVar5;
  undefined4 local_6c [16];
  int local_2c;
  int local_28;
  uint local_24;
  uint local_20;
  uint local_1c;
  int local_18;
  int local_14;
  uint local_10;
  uint local_c;
  undefined4 local_8;
  
  puVar5 = local_6c;
  for (iVar3 = 0x1a; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
LAB_004170a8:
  do {
    pbVar2 = param_2;
    if (param_2 == param_3) {
LAB_004176d1:
      param_2 = pbVar2;
      local_8 = 0x4176de;
      FUN_004291a7();
      return;
    }
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    local_18 = bVar1 - 5;
    switch(bVar1) {
    case 5:
      if ((int)param_3 - (int)param_2 < 2) goto LAB_004176d1;
      (**(code **)(param_1 + 0x148))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        pbVar2 = param_2;
        goto LAB_004176d1;
      }
      param_2 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) goto LAB_004176d1;
      (**(code **)(param_1 + 0x14c))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        pbVar2 = param_2;
        goto LAB_004176d1;
      }
      param_2 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) goto LAB_004176d1;
      (**(code **)(param_1 + 0x150))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        pbVar2 = param_2;
        goto LAB_004176d1;
      }
      param_2 = param_2 + 4;
      break;
    default:
      *param_4 = param_2;
      goto LAB_004176d1;
    case 9:
    case 10:
    case 0x15:
      while( true ) {
        param_2 = param_2 + 1;
        pbVar2 = param_2;
        if (param_2 == param_3) goto LAB_004176d1;
        uVar4 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        if (uVar4 == 0xe) break;
        local_1c = uVar4;
        if ((uVar4 < 9) || ((10 < uVar4 && (uVar4 != 0x15)))) {
          *param_4 = param_2;
          goto LAB_004176d1;
        }
      }
      local_8 = 0xe;
    case 0xe:
      while( true ) {
        pbVar2 = param_2 + 1;
        if (pbVar2 == param_3) goto LAB_004176d1;
        local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
        if ((local_c == 0xc) || (local_c == 0xd)) break;
        local_20 = local_c;
        if ((local_c < 9) || ((param_2 = pbVar2, 10 < local_c && (local_c != 0x15)))) {
          *param_4 = pbVar2;
          goto LAB_004176d1;
        }
      }
      param_2 = param_2 + 2;
      while( true ) {
        pbVar2 = param_2;
        if (param_2 == param_3) goto LAB_004176d1;
        local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        if (local_10 == local_c) break;
        local_24 = local_10;
        switch(local_10) {
        case 0:
        case 1:
        case 8:
          *param_4 = param_2;
          goto LAB_004176d1;
        case 2:
          *param_4 = param_2;
          goto LAB_004176d1;
        case 3:
          local_14 = FUN_00415710(param_1,param_2 + 1,param_3,&param_2);
          if (local_14 < 1) {
            pbVar2 = param_2;
            if (local_14 == 0) {
              *param_4 = param_2;
            }
            goto LAB_004176d1;
          }
          break;
        default:
          param_2 = param_2 + 1;
          break;
        case 5:
          if ((int)param_3 - (int)param_2 < 2) goto LAB_004176d1;
          (**(code **)(param_1 + 0x160))(param_1,param_2);
          iVar3 = FUN_004291a7();
          if (iVar3 != 0) {
            *param_4 = param_2;
            pbVar2 = param_2;
            goto LAB_004176d1;
          }
          param_2 = param_2 + 2;
          break;
        case 6:
          if ((int)param_3 - (int)param_2 < 3) goto LAB_004176d1;
          (**(code **)(param_1 + 0x164))(param_1,param_2);
          iVar3 = FUN_004291a7();
          if (iVar3 != 0) {
            *param_4 = param_2;
            pbVar2 = param_2;
            goto LAB_004176d1;
          }
          param_2 = param_2 + 3;
          break;
        case 7:
          if ((int)param_3 - (int)param_2 < 4) goto LAB_004176d1;
          (**(code **)(param_1 + 0x168))(param_1,param_2);
          iVar3 = FUN_004291a7();
          if (iVar3 != 0) {
            *param_4 = param_2;
            pbVar2 = param_2;
            goto LAB_004176d1;
          }
          param_2 = param_2 + 4;
        }
      }
      pbVar2 = param_2 + 1;
      if (pbVar2 != param_3) {
        bVar1 = *(byte *)(param_1 + 0x48 + (uint)*pbVar2);
        local_28 = bVar1 - 9;
        switch(bVar1) {
        case 9:
        case 10:
        case 0x15:
          param_2 = pbVar2;
          do {
            pbVar2 = param_2 + 1;
            if (pbVar2 == param_3) break;
            bVar1 = *(byte *)(param_1 + 0x48 + (uint)*pbVar2);
            local_2c = bVar1 - 5;
            switch(bVar1) {
            case 5:
              goto switchD_00417536_caseD_5;
            case 6:
              if ((int)param_3 - (int)pbVar2 < 3) goto LAB_004176d1;
              param_2 = pbVar2;
              (**(code **)(param_1 + 0x158))(param_1,pbVar2);
              iVar3 = FUN_004291a7();
              if (iVar3 != 0) {
                param_2 = param_2 + 3;
                goto LAB_004170a8;
              }
              *param_4 = param_2;
              pbVar2 = param_2;
              goto LAB_004176d1;
            case 7:
              if ((int)param_3 - (int)pbVar2 < 4) goto LAB_004176d1;
              param_2 = pbVar2;
              (**(code **)(param_1 + 0x15c))(param_1,pbVar2);
              iVar3 = FUN_004291a7();
              if (iVar3 != 0) {
                param_2 = param_2 + 4;
                goto LAB_004170a8;
              }
              *param_4 = param_2;
              pbVar2 = param_2;
              goto LAB_004176d1;
            default:
              *param_4 = pbVar2;
              goto LAB_004176d1;
            case 9:
            case 10:
            case 0x15:
              param_2 = pbVar2;
              break;
            case 0xb:
              goto switchD_00417536_caseD_b;
            case 0x11:
              goto switchD_00417536_caseD_11;
            case 0x16:
            case 0x18:
              param_2 = param_2 + 2;
              goto LAB_004170a8;
            case 0x1d:
              *param_4 = pbVar2;
              goto LAB_004176d1;
            }
          } while( true );
        case 0xb:
switchD_00417536_caseD_b:
          param_2 = pbVar2;
          *param_4 = param_2 + 1;
          pbVar2 = param_2;
          break;
        default:
          *param_4 = pbVar2;
          break;
        case 0x11:
switchD_00417536_caseD_11:
          param_2 = pbVar2;
          pbVar2 = param_2 + 1;
          if (pbVar2 != param_3) {
            if (*pbVar2 == 0x3e) {
              *param_4 = param_2 + 2;
            }
            else {
              *param_4 = pbVar2;
            }
          }
        }
      }
      goto LAB_004176d1;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
      goto switchD_004170e5_caseD_16;
    case 0x1d:
      *param_4 = param_2;
      goto LAB_004176d1;
    }
  } while( true );
switchD_004170e5_caseD_16:
  param_2 = param_2 + 1;
  goto LAB_004170a8;
switchD_00417536_caseD_5:
  if ((int)param_3 - (int)pbVar2 < 2) goto LAB_004176d1;
  param_2 = pbVar2;
  (**(code **)(param_1 + 0x154))(param_1,pbVar2);
  iVar3 = FUN_004291a7();
  if (iVar3 == 0) {
    *param_4 = param_2;
    pbVar2 = param_2;
    goto LAB_004176d1;
  }
  param_2 = param_2 + 2;
  goto LAB_004170a8;
}



/* 004177a0 FUN_004177a0 */

/* WARNING: Removing unreachable block (ram,0x004177df) */
/* WARNING: Removing unreachable block (ram,0x004177eb) */
/* WARNING: Removing unreachable block (ram,0x004177f3) */
/* WARNING: Removing unreachable block (ram,0x004177d0) */
/* WARNING: Removing unreachable block (ram,0x00417d66) */
/* WARNING: Removing unreachable block (ram,0x00417d4e) */

void __cdecl FUN_004177a0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  byte *pbVar4;
  undefined4 *puVar5;
  undefined4 local_60 [16];
  int local_20;
  int local_1c;
  char local_18;
  int local_14;
  int local_10;
  int local_8;
  
  puVar5 = local_60;
  for (iVar3 = 0x17; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
  if (param_2 == param_3) goto LAB_00417f7c;
  bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
  local_10 = bVar1 - 2;
  switch(bVar1) {
  case 2:
    pbVar4 = param_2 + 1;
    if (pbVar4 != param_3) {
      bVar1 = *(byte *)(param_1 + 0x48 + (uint)*pbVar4);
      local_14 = bVar1 - 5;
      switch(bVar1) {
      case 5:
      case 6:
      case 7:
      case 0x16:
      case 0x18:
      case 0x1d:
        *param_4 = param_2;
        break;
      default:
        *param_4 = pbVar4;
        break;
      case 0xf:
        FUN_00416530(param_1,param_2 + 2,param_3,param_4);
        break;
      case 0x10:
        FUN_004180b0(param_1,param_2 + 2,param_3,param_4);
      }
    }
    break;
  default:
    goto switchD_0041782d_caseD_3;
  case 4:
    pbVar4 = param_2 + 1;
    if (pbVar4 != param_3) {
      if (*pbVar4 == 0x5d) {
        if (param_2 + 2 == param_3) break;
        if (param_2[2] == 0x3e) {
          *param_4 = param_2 + 3;
          break;
        }
      }
      *param_4 = pbVar4;
    }
    break;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) break;
    (**(code **)(param_1 + 0x154))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      (**(code **)(param_1 + 0x148))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        break;
      }
      param_2 = param_2 + 2;
      local_8 = 0x13;
    }
    else {
      param_2 = param_2 + 2;
      local_8 = 0x12;
    }
    goto LAB_00417d87;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) break;
    (**(code **)(param_1 + 0x158))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      (**(code **)(param_1 + 0x14c))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        break;
      }
      param_2 = param_2 + 3;
      local_8 = 0x13;
    }
    else {
      param_2 = param_2 + 3;
      local_8 = 0x12;
    }
    goto LAB_00417d87;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) break;
    (**(code **)(param_1 + 0x15c))(param_1,param_2);
    iVar3 = FUN_004291a7();
    if (iVar3 == 0) {
      (**(code **)(param_1 + 0x150))(param_1,param_2);
      iVar3 = FUN_004291a7();
      if (iVar3 == 0) {
        *param_4 = param_2;
        break;
      }
      param_2 = param_2 + 4;
      local_8 = 0x13;
    }
    else {
      param_2 = param_2 + 4;
      local_8 = 0x12;
    }
    goto LAB_00417d87;
  case 9:
    if (param_2 + 1 == param_3) {
      *param_4 = param_3;
      break;
    }
  case 10:
  case 0x15:
    do {
      while( true ) {
        pbVar4 = param_2 + 1;
        if (pbVar4 == param_3) {
          *param_4 = pbVar4;
          goto LAB_00417f7c;
        }
        local_18 = *(char *)(param_1 + 0x48 + (uint)*pbVar4);
        if (local_18 == '\t') break;
        param_2 = pbVar4;
        if ((local_18 != '\n') && (local_18 != '\x15')) goto LAB_0041798d;
      }
      pbVar2 = param_2 + 2;
      param_2 = pbVar4;
    } while (pbVar2 != param_3);
LAB_0041798d:
    *param_4 = pbVar4;
    break;
  case 0xb:
    *param_4 = param_2 + 1;
    break;
  case 0xc:
    FUN_004189b0(0xc,param_1,param_2 + 1,param_3,param_4);
    break;
  case 0xd:
    FUN_004189b0(0xd,param_1,param_2 + 1,param_3,param_4);
    break;
  case 0x13:
    FUN_00418630(param_1,param_2 + 1,param_3,param_4);
    break;
  case 0x14:
    *param_4 = param_2 + 1;
    break;
  case 0x16:
  case 0x18:
    local_8 = 0x12;
    param_2 = param_2 + 1;
    goto LAB_00417d87;
  case 0x19:
  case 0x1a:
  case 0x1b:
    local_8 = 0x13;
    param_2 = param_2 + 1;
LAB_00417d87:
    while (param_2 != param_3) {
      bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
      local_20 = bVar1 - 5;
      switch(bVar1) {
      case 5:
        if ((int)param_3 - (int)param_2 < 2) goto LAB_00417f7c;
        (**(code **)(param_1 + 0x148))(param_1,param_2);
        iVar3 = FUN_004291a7();
        if (iVar3 == 0) {
          *param_4 = param_2;
          goto LAB_00417f7c;
        }
        param_2 = param_2 + 2;
        break;
      case 6:
        if ((int)param_3 - (int)param_2 < 3) goto LAB_00417f7c;
        (**(code **)(param_1 + 0x14c))(param_1,param_2);
        iVar3 = FUN_004291a7();
        if (iVar3 == 0) {
          *param_4 = param_2;
          goto LAB_00417f7c;
        }
        param_2 = param_2 + 3;
        break;
      case 7:
        if ((int)param_3 - (int)param_2 < 4) goto LAB_00417f7c;
        (**(code **)(param_1 + 0x150))(param_1,param_2);
        iVar3 = FUN_004291a7();
        if (iVar3 == 0) {
          *param_4 = param_2;
          goto LAB_00417f7c;
        }
        param_2 = param_2 + 4;
        break;
      default:
        *param_4 = param_2;
        goto LAB_00417f7c;
      case 9:
      case 10:
      case 0xb:
      case 0x14:
      case 0x15:
      case 0x1e:
      case 0x20:
      case 0x23:
      case 0x24:
        *param_4 = param_2;
        goto LAB_00417f7c;
      case 0xf:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 1;
        }
        goto LAB_00417f7c;
      case 0x16:
      case 0x18:
      case 0x19:
      case 0x1a:
      case 0x1b:
        param_2 = param_2 + 1;
        break;
      case 0x1d:
        *param_4 = param_2;
        goto LAB_00417f7c;
      case 0x21:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 1;
        }
        goto LAB_00417f7c;
      case 0x22:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 1;
        }
        goto LAB_00417f7c;
      }
    }
    break;
  case 0x1d:
    goto switchD_0041782d_caseD_3;
  case 0x1e:
    FUN_00418290(param_1,param_2 + 1,param_3,param_4);
    break;
  case 0x1f:
    *param_4 = param_2 + 1;
    break;
  case 0x20:
    pbVar4 = param_2 + 1;
    if (pbVar4 != param_3) {
      bVar1 = *(byte *)(param_1 + 0x48 + (uint)*pbVar4);
      local_1c = bVar1 - 9;
      switch(bVar1) {
      case 9:
      case 10:
      case 0xb:
      case 0x15:
      case 0x20:
      case 0x23:
      case 0x24:
        *param_4 = pbVar4;
        break;
      default:
        *param_4 = pbVar4;
        break;
      case 0xf:
        *param_4 = param_2 + 2;
        break;
      case 0x21:
        *param_4 = param_2 + 2;
        break;
      case 0x22:
        *param_4 = param_2 + 2;
      }
    }
    break;
  case 0x23:
    *param_4 = param_2 + 1;
    break;
  case 0x24:
    *param_4 = param_2 + 1;
  }
LAB_00417f7c:
  local_8 = 0x417f89;
  FUN_004291a7();
  return;
switchD_0041782d_caseD_3:
  *param_4 = param_2;
  goto LAB_00417f7c;
}



/* 004180b0 FUN_004180b0 */

void __cdecl FUN_004180b0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_50 [16];
  int local_10;
  int local_c;
  int local_8;
  
  puVar4 = local_50;
  for (iVar3 = 0x13; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  if (param_2 != param_3) {
    local_8 = *(byte *)(param_1 + 0x48 + (uint)*param_2) - 0x14;
    switch(local_8) {
    case 0:
      *param_4 = param_2 + 1;
      break;
    default:
      *param_4 = param_2;
      break;
    case 2:
    case 4:
      pbVar2 = param_2;
LAB_00418153:
      param_2 = pbVar2 + 1;
      if (param_2 != param_3) {
        bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
        local_c = bVar1 - 9;
        switch(bVar1) {
        case 9:
        case 10:
        case 0x15:
          goto switchD_0041818c_caseD_9;
        default:
          *param_4 = param_2;
          break;
        case 0x16:
        case 0x18:
          goto LAB_0041820a;
        case 0x1e:
          if (pbVar2 + 2 == param_3) break;
          bVar1 = *(byte *)(param_1 + 0x48 + (uint)pbVar2[2]);
          local_10 = bVar1 - 9;
          switch(bVar1) {
          case 9:
          case 10:
          case 0x15:
          case 0x1e:
            *param_4 = param_2;
            goto LAB_00418212;
          }
switchD_0041818c_caseD_9:
          *param_4 = param_2;
        }
      }
      break;
    case 7:
      FUN_004162f0(param_1,param_2 + 1,param_3,param_4);
    }
  }
LAB_00418212:
  local_8 = 0x41821f;
  FUN_004291a7();
  return;
LAB_0041820a:
  pbVar2 = param_2;
  goto LAB_00418153;
}



/* 00418290 FUN_00418290 */

void __cdecl FUN_00418290(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [16];
  int local_c;
  int local_8;
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_004185a6;
  bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
  local_8 = bVar1 - 5;
  switch(bVar1) {
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_004185a6;
    (**(code **)(param_1 + 0x154))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_004185a6;
    }
    param_2 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_004185a6;
    (**(code **)(param_1 + 0x158))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_004185a6;
    }
    param_2 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_004185a6;
    (**(code **)(param_1 + 0x15c))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_004185a6;
    }
    param_2 = param_2 + 4;
    break;
  default:
    *param_4 = param_2;
    goto LAB_004185a6;
  case 9:
  case 10:
  case 0x15:
  case 0x1e:
    *param_4 = param_2;
    goto LAB_004185a6;
  case 0x16:
  case 0x18:
    param_2 = param_2 + 1;
    break;
  case 0x1d:
    *param_4 = param_2;
    goto LAB_004185a6;
  }
  while (param_2 != param_3) {
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    local_c = bVar1 - 5;
    switch(bVar1) {
    case 5:
      if ((int)param_3 - (int)param_2 < 2) goto LAB_004185a6;
      (**(code **)(param_1 + 0x148))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_004185a6;
      }
      param_2 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) goto LAB_004185a6;
      (**(code **)(param_1 + 0x14c))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_004185a6;
      }
      param_2 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) goto LAB_004185a6;
      (**(code **)(param_1 + 0x150))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_004185a6;
      }
      param_2 = param_2 + 4;
      break;
    default:
      *param_4 = param_2;
      goto LAB_004185a6;
    case 0x12:
      *param_4 = param_2 + 1;
      goto LAB_004185a6;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
      param_2 = param_2 + 1;
      break;
    case 0x1d:
      *param_4 = param_2;
      goto LAB_004185a6;
    }
  }
LAB_004185a6:
  local_8 = 0x4185b3;
  FUN_004291a7();
  return;
}



/* 00418630 FUN_00418630 */

void __cdecl FUN_00418630(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [16];
  int local_c;
  int local_8;
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_00418931;
  bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
  local_8 = bVar1 - 5;
  switch(bVar1) {
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_00418931;
    (**(code **)(param_1 + 0x154))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_00418931;
    }
    param_2 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_00418931;
    (**(code **)(param_1 + 0x158))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_00418931;
    }
    param_2 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_00418931;
    (**(code **)(param_1 + 0x15c))(param_1,param_2);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      *param_4 = param_2;
      goto LAB_00418931;
    }
    param_2 = param_2 + 4;
    break;
  default:
    *param_4 = param_2;
    goto LAB_00418931;
  case 0x16:
  case 0x18:
    param_2 = param_2 + 1;
    break;
  case 0x1d:
    *param_4 = param_2;
    goto LAB_00418931;
  }
  while (param_2 != param_3) {
    bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
    local_c = bVar1 - 5;
    switch(bVar1) {
    case 5:
      if ((int)param_3 - (int)param_2 < 2) goto LAB_00418931;
      (**(code **)(param_1 + 0x148))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_00418931;
      }
      param_2 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) goto LAB_00418931;
      (**(code **)(param_1 + 0x14c))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_00418931;
      }
      param_2 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) goto LAB_00418931;
      (**(code **)(param_1 + 0x150))(param_1,param_2);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_4 = param_2;
        goto LAB_00418931;
      }
      param_2 = param_2 + 4;
      break;
    default:
      *param_4 = param_2;
      goto LAB_00418931;
    case 9:
    case 10:
    case 0xb:
    case 0x15:
    case 0x1e:
    case 0x20:
    case 0x24:
      *param_4 = param_2;
      goto LAB_00418931;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
      param_2 = param_2 + 1;
      break;
    case 0x1d:
      *param_4 = param_2;
      goto LAB_00418931;
    }
  }
LAB_00418931:
  local_8 = 0x41893e;
  FUN_004291a7();
  return;
}



/* 004189b0 FUN_004189b0 */

void __cdecl FUN_004189b0(uint param_1,int param_2,byte *param_3,byte *param_4,undefined4 *param_5)

{
  byte bVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_50 [16];
  int local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_50;
  for (iVar2 = 0x13; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  do {
    if (param_3 == param_4) goto LAB_00418b85;
    local_c = (uint)*(byte *)(param_2 + 0x48 + (uint)*param_3);
    local_8 = local_c;
    switch(local_c) {
    case 0:
    case 1:
    case 8:
      *param_5 = param_3;
      goto LAB_00418b85;
    default:
      param_3 = param_3 + 1;
      break;
    case 5:
      if ((int)param_4 - (int)param_3 < 2) goto LAB_00418b85;
      (**(code **)(param_2 + 0x160))(param_2,param_3);
      iVar2 = FUN_004291a7();
      if (iVar2 != 0) {
        *param_5 = param_3;
        goto LAB_00418b85;
      }
      param_3 = param_3 + 2;
      break;
    case 6:
      if ((int)param_4 - (int)param_3 < 3) goto LAB_00418b85;
      (**(code **)(param_2 + 0x164))(param_2,param_3);
      iVar2 = FUN_004291a7();
      if (iVar2 != 0) {
        *param_5 = param_3;
        goto LAB_00418b85;
      }
      param_3 = param_3 + 3;
      break;
    case 7:
      if ((int)param_4 - (int)param_3 < 4) goto LAB_00418b85;
      (**(code **)(param_2 + 0x168))(param_2,param_3);
      iVar2 = FUN_004291a7();
      if (iVar2 != 0) {
        *param_5 = param_3;
        goto LAB_00418b85;
      }
      param_3 = param_3 + 4;
      break;
    case 0xc:
    case 0xd:
      param_3 = param_3 + 1;
      if (local_c == param_1) {
        if (param_3 != param_4) {
          *param_5 = param_3;
          bVar1 = *(byte *)(param_2 + 0x48 + (uint)*param_3);
          local_10 = bVar1 - 9;
          switch(bVar1) {
          case 9:
          case 10:
          case 0xb:
          case 0x14:
          case 0x15:
          case 0x1e:
            break;
          default:
          }
        }
LAB_00418b85:
        local_8 = 0x418b92;
        FUN_004291a7();
        return;
      }
    }
  } while( true );
}



/* 00418be0 FUN_00418be0 */

void __cdecl FUN_00418be0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  int iVar2;
  byte *pbVar3;
  undefined4 *puVar4;
  undefined4 local_4c [16];
  int local_c;
  byte *local_8;
  
  pbVar3 = param_2;
  puVar4 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  if (param_2 != param_3) {
    local_8 = param_2;
    while (param_2 != param_3) {
      bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
      local_c = bVar1 - 2;
      switch(bVar1) {
      case 2:
        *param_4 = param_2;
        goto LAB_00418d99;
      case 3:
        if (param_2 == pbVar3) {
          FUN_00415710(param_1,param_2 + 1,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418d99;
      default:
        param_2 = param_2 + 1;
        break;
      case 5:
        param_2 = param_2 + 2;
        break;
      case 6:
        param_2 = param_2 + 3;
        break;
      case 7:
        param_2 = param_2 + 4;
        break;
      case 9:
        if (param_2 == pbVar3) {
          pbVar3 = param_2 + 1;
          if (pbVar3 != param_3) {
            if (*(char *)(param_1 + 0x48 + (uint)*pbVar3) == '\n') {
              pbVar3 = param_2 + 2;
            }
            param_2 = pbVar3;
            *param_4 = param_2;
          }
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418d99;
      case 10:
        if (param_2 == pbVar3) {
          *param_4 = param_2 + 1;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418d99;
      case 0x15:
        if (param_2 == pbVar3) {
          *param_4 = param_2 + 1;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418d99;
      }
    }
    *param_4 = param_2;
  }
LAB_00418d99:
  local_8 = (byte *)0x418da6;
  FUN_004291a7();
  return;
}



/* 00418df0 FUN_00418df0 */

void __cdecl FUN_00418df0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte bVar1;
  byte *pbVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_50 [16];
  int local_10;
  undefined4 local_c;
  byte *local_8;
  
  pbVar2 = param_2;
  puVar4 = local_50;
  for (iVar3 = 0x13; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  if (param_2 != param_3) {
    local_8 = param_2;
    while (param_2 != param_3) {
      bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2);
      local_10 = bVar1 - 3;
      switch(bVar1) {
      case 3:
        if (param_2 == pbVar2) {
          FUN_00415710(param_1,param_2 + 1,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418fb5;
      default:
        param_2 = param_2 + 1;
        break;
      case 5:
        param_2 = param_2 + 2;
        break;
      case 6:
        param_2 = param_2 + 3;
        break;
      case 7:
        param_2 = param_2 + 4;
        break;
      case 9:
        if (param_2 == pbVar2) {
          pbVar2 = param_2 + 1;
          if (pbVar2 != param_3) {
            if (*(char *)(param_1 + 0x48 + (uint)*pbVar2) == '\n') {
              pbVar2 = param_2 + 2;
            }
            param_2 = pbVar2;
            *param_4 = param_2;
          }
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418fb5;
      case 10:
        if (param_2 == pbVar2) {
          *param_4 = param_2 + 1;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418fb5;
      case 0x1e:
        if (param_2 == pbVar2) {
          local_c = FUN_00418290(param_1,param_2 + 1,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_00418fb5;
      }
    }
    *param_4 = param_2;
  }
LAB_00418fb5:
  local_8 = (byte *)0x418fc2;
  FUN_004291a7();
  return;
}



/* 00419010 FUN_00419010 */

undefined4 __cdecl FUN_00419010(int param_1,byte *param_2,int param_3,undefined4 *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [18];
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
LAB_00419045:
  param_2 = param_2 + 1;
  if (param_2 == (byte *)(param_3 + -1)) {
    return 1;
  }
  switch(*(undefined1 *)(param_1 + 0x48 + (uint)*param_2)) {
  case 9:
  case 10:
  case 0xd:
  case 0xe:
  case 0xf:
  case 0x10:
  case 0x11:
  case 0x12:
  case 0x13:
  case 0x18:
  case 0x19:
  case 0x1b:
  case 0x1e:
  case 0x1f:
  case 0x20:
  case 0x21:
  case 0x22:
  case 0x23:
    goto LAB_00419045;
  case 0x15:
    goto switchD_0041907e_caseD_15;
  case 0x16:
  case 0x1a:
    if ((*param_2 & 0x80) == 0) goto LAB_00419045;
  }
  if ((*param_2 != 0x24) && (*param_2 != 0x40)) {
    *param_4 = param_2;
    return 0;
  }
  goto LAB_00419045;
switchD_0041907e_caseD_15:
  if (*param_2 == 9) {
    *param_4 = param_2;
    return 0;
  }
  goto LAB_00419045;
}



/* 00419110 FUN_00419110 */

int __cdecl FUN_00419110(int param_1,byte *param_2,int param_3,int param_4)

{
  byte *pbVar1;
  char cVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_54 [18];
  int local_c;
  int local_8;
  
  puVar4 = local_54;
  for (iVar3 = 0x14; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  local_8 = 1;
  local_c = 0;
  cVar2 = '\0';
  pbVar1 = param_2;
  do {
    param_2 = pbVar1 + 1;
    switch(*(undefined1 *)(param_1 + 0x48 + (uint)*param_2)) {
    case 3:
      if (local_c < param_3) {
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 5:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      param_2 = pbVar1 + 2;
      break;
    case 6:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      param_2 = pbVar1 + 3;
      break;
    case 7:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      param_2 = pbVar1 + 4;
      break;
    case 9:
    case 10:
      if (local_8 == 1) {
        local_8 = 0;
      }
      else if ((local_8 == 2) && (local_c < param_3)) {
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 0xb:
    case 0x11:
      if (local_8 != 2) {
        return local_c;
      }
      break;
    case 0xc:
      if (local_8 == 2) {
        if (cVar2 == '\f') {
          local_8 = 0;
          if (local_c < param_3) {
            *(byte **)(param_4 + 8 + local_c * 0x10) = param_2;
          }
          local_c = local_c + 1;
        }
      }
      else {
        if (local_c < param_3) {
          *(byte **)(param_4 + 4 + local_c * 0x10) = pbVar1 + 2;
        }
        local_8 = 2;
        cVar2 = '\f';
      }
      break;
    case 0xd:
      if (local_8 == 2) {
        if (cVar2 == '\r') {
          local_8 = 0;
          if (local_c < param_3) {
            *(byte **)(param_4 + 8 + local_c * 0x10) = param_2;
          }
          local_c = local_c + 1;
        }
      }
      else {
        if (local_c < param_3) {
          *(byte **)(param_4 + 4 + local_c * 0x10) = pbVar1 + 2;
        }
        local_8 = 2;
        cVar2 = '\r';
      }
      break;
    case 0x15:
      if (local_8 == 1) {
        local_8 = 0;
      }
      else if ((((local_8 == 2) && (local_c < param_3)) &&
               (*(char *)(param_4 + 0xc + local_c * 0x10) != '\0')) &&
              (((param_2 == *(byte **)(param_4 + 4 + local_c * 0x10) || (*param_2 != 0x20)) ||
               ((pbVar1[2] == 0x20 || (*(char *)(param_1 + 0x48 + (uint)pbVar1[2]) == cVar2)))))) {
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 0x16:
    case 0x18:
    case 0x1d:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
    }
    pbVar1 = param_2;
  } while( true );
}



/* 00419470 FUN_00419470 */

void __cdecl FUN_00419470(undefined4 param_1,char *param_2)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  int local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_8 = 0;
  pcVar1 = param_2 + 2;
  if (param_2[2] == 'x') {
    for (param_2 = param_2 + 3; *param_2 != ';'; param_2 = param_2 + 1) {
      local_c = (int)*param_2;
      local_14 = local_c + -0x30;
      switch(local_c) {
      case 0x30:
      case 0x31:
      case 0x32:
      case 0x33:
      case 0x34:
      case 0x35:
      case 0x36:
      case 0x37:
      case 0x38:
      case 0x39:
        local_8 = local_8 << 4 | local_c - 0x30U;
        break;
      case 0x41:
      case 0x42:
      case 0x43:
      case 0x44:
      case 0x45:
      case 0x46:
        local_8 = local_8 * 0x10 + -0x37 + local_c;
        break;
      case 0x61:
      case 0x62:
      case 99:
      case 100:
      case 0x65:
      case 0x66:
        local_8 = local_8 * 0x10 + -0x57 + local_c;
      }
      if (0x10ffff < (int)local_8) goto LAB_004195a7;
    }
  }
  else {
    while (param_2 = pcVar1, *param_2 != ';') {
      local_10 = (int)*param_2;
      local_8 = local_8 * 10 + -0x30 + local_10;
      if (0x10ffff < (int)local_8) goto LAB_004195a7;
      pcVar1 = param_2 + 1;
    }
  }
  FUN_004257d0(local_8);
LAB_004195a7:
  local_8 = 0x4195b4;
  FUN_004291a7();
  return;
}



/* 00419600 FUN_00419600 */

undefined4 __cdecl FUN_00419600(undefined4 param_1,char *param_2,int param_3)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_50 [19];
  
  puVar2 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  iVar1 = param_3 - (int)param_2;
  if (iVar1 == 2) {
    if (param_2[1] == 't') {
      if (*param_2 == 'g') {
        return 0x3e;
      }
      if (*param_2 == 'l') {
        return 0x3c;
      }
    }
  }
  else if (iVar1 == 3) {
    if (((*param_2 == 'a') && (param_2[1] == 'm')) && (param_2[2] == 'p')) {
      return 0x26;
    }
  }
  else if (iVar1 == 4) {
    if (*param_2 == 'a') {
      if (((param_2[1] == 'p') && (param_2[2] == 'o')) && (param_2[3] == 's')) {
        return 0x27;
      }
    }
    else if (((*param_2 == 'q') && (param_2[1] == 'u')) &&
            ((param_2[2] == 'o' && (param_2[3] == 't')))) {
      return 0x22;
    }
  }
  return 0;
}



/* 00419770 FUN_00419770 */

/* WARNING: Removing unreachable block (ram,0x0041989c) */
/* WARNING: Removing unreachable block (ram,0x004198be) */
/* WARNING: Removing unreachable block (ram,0x004198c5) */
/* WARNING: Removing unreachable block (ram,0x004198cb) */
/* WARNING: Removing unreachable block (ram,0x004198ed) */
/* WARNING: Removing unreachable block (ram,0x004198f4) */
/* WARNING: Removing unreachable block (ram,0x004198fa) */
/* WARNING: Removing unreachable block (ram,0x0041991c) */

undefined4 __cdecl FUN_00419770(int param_1,byte *param_2,byte *param_3)

{
  byte bVar1;
  byte bVar2;
  byte *pbVar3;
  byte *pbVar4;
  int iVar5;
  undefined4 *puVar6;
  undefined4 local_4c [18];
  
  puVar6 = local_4c;
  for (iVar5 = 0x12; iVar5 != 0; iVar5 = iVar5 + -1) {
    *puVar6 = 0xcccccccc;
    puVar6 = puVar6 + 1;
  }
  do {
    switch(*(undefined1 *)(param_1 + 0x48 + (uint)*param_2)) {
    case 7:
      bVar1 = *param_2;
      bVar2 = *param_3;
      param_3 = param_3 + 1;
      param_2 = param_2 + 1;
      if (bVar1 != bVar2) {
        return 0;
      }
    case 6:
      bVar1 = *param_2;
      bVar2 = *param_3;
      param_3 = param_3 + 1;
      param_2 = param_2 + 1;
      if (bVar1 != bVar2) {
        return 0;
      }
    case 5:
      pbVar3 = param_3 + 1;
      pbVar4 = param_2 + 1;
      if (*param_2 != *param_3) {
        return 0;
      }
      param_3 = param_3 + 2;
      param_2 = param_2 + 2;
      if (*pbVar4 != *pbVar3) {
        return 0;
      }
      break;
    default:
      if (*param_2 != *param_3) {
        switch(*(undefined1 *)(param_1 + 0x48 + (uint)*param_3)) {
        case 5:
        case 6:
        case 7:
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
        case 0x1d:
          return 0;
        default:
          return 1;
        }
      }
      return 1;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
    case 0x1d:
      bVar1 = *param_3;
      bVar2 = *param_2;
      param_2 = param_2 + 1;
      param_3 = param_3 + 1;
      if (bVar1 != bVar2) {
        return 0;
      }
    }
  } while( true );
}



/* 004199e0 FUN_004199e0 */

bool __cdecl FUN_004199e0(undefined4 param_1,char *param_2,char *param_3,char *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while( true ) {
    if (*param_4 == '\0') {
      return param_2 == param_3;
    }
    if (param_2 == param_3) break;
    if (*param_2 != *param_4) {
      return false;
    }
    param_2 = param_2 + 1;
    param_4 = param_4 + 1;
  }
  return false;
}



/* 00419a50 FUN_00419a50 */

int __cdecl FUN_00419a50(int param_1,byte *param_2)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [18];
  
  pbVar1 = param_2;
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  do {
    switch(*(undefined1 *)(param_1 + 0x48 + (uint)*param_2)) {
    case 5:
      param_2 = param_2 + 2;
      break;
    case 6:
      param_2 = param_2 + 3;
      break;
    case 7:
      param_2 = param_2 + 4;
      break;
    default:
      return (int)param_2 - (int)pbVar1;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
    case 0x1d:
      param_2 = param_2 + 1;
    }
  } while( true );
}



/* 00419b10 FUN_00419b10 */

byte * __cdecl FUN_00419b10(int param_1,byte *param_2)

{
  byte bVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [17];
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  while ((bVar1 = *(byte *)(param_1 + 0x48 + (uint)*param_2), 8 < bVar1 &&
         ((bVar1 < 0xb || (bVar1 == 0x15))))) {
    param_2 = param_2 + 1;
  }
  return param_2;
}



/* 00419b70 FUN_00419b70 */

void __cdecl FUN_00419b70(int param_1,byte *param_2,byte *param_3,int *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [17];
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  while (param_2 != param_3) {
    switch(*(undefined1 *)(param_1 + 0x48 + (uint)*param_2)) {
    case 5:
      param_2 = param_2 + 2;
      break;
    case 6:
      param_2 = param_2 + 3;
      break;
    case 7:
      param_2 = param_2 + 4;
      break;
    default:
      param_2 = param_2 + 1;
      break;
    case 9:
      *param_4 = *param_4 + 1;
      pbVar1 = param_2 + 1;
      if ((pbVar1 != param_3) && (*(char *)(param_1 + 0x48 + (uint)*pbVar1) == '\n')) {
        pbVar1 = param_2 + 2;
      }
      param_2 = pbVar1;
      param_4[1] = -1;
      break;
    case 10:
      param_4[1] = -1;
      *param_4 = *param_4 + 1;
      param_2 = param_2 + 1;
    }
    param_4[1] = param_4[1] + 1;
  }
  return;
}



/* 00419c90 FUN_00419c90 */

void __cdecl
FUN_00419c90(undefined4 param_1,uint *param_2,undefined1 *param_3,int *param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  undefined1 *local_c;
  undefined1 *local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if (param_5 - *param_4 < (int)((int)param_3 - *param_2)) {
    for (param_3 = (undefined1 *)(*param_2 + (param_5 - *param_4));
        ((undefined1 *)*param_2 < param_3 && ((param_3[-1] & 0xc0) == 0x80)); param_3 = param_3 + -1
        ) {
    }
  }
  local_8 = (undefined1 *)*param_4;
  for (local_c = (undefined1 *)*param_2; local_c != param_3; local_c = local_c + 1) {
    *local_8 = *local_c;
    local_8 = local_8 + 1;
  }
  *param_2 = (uint)local_c;
  *param_4 = (int)local_8;
  return;
}



/* 00419d50 FUN_00419d50 */

void __cdecl
FUN_00419d50(int param_1,undefined4 *param_2,byte *param_3,undefined4 *param_4,ushort *param_5)

{
  char cVar1;
  int iVar2;
  uint uVar3;
  undefined4 *puVar4;
  undefined4 local_54 [18];
  byte *local_c;
  ushort *local_8;
  
  puVar4 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  local_8 = (ushort *)*param_4;
  local_c = (byte *)*param_2;
  while ((local_c != param_3 && (local_8 != param_5))) {
    cVar1 = *(char *)(param_1 + 0x48 + (uint)*local_c);
    if (cVar1 == '\x05') {
      *local_8 = ((short)(char)*local_c & 0x1fU) << 6 | (short)(char)local_c[1] & 0x3fU;
      local_8 = local_8 + 1;
      local_c = local_c + 2;
    }
    else if (cVar1 == '\x06') {
      *local_8 = (short)(char)*local_c << 0xc | ((short)(char)local_c[1] & 0x3fU) << 6 |
                 (short)(char)local_c[2] & 0x3fU;
      local_8 = local_8 + 1;
      local_c = local_c + 3;
    }
    else if (cVar1 == '\a') {
      if (local_8 + 1 == param_5) break;
      uVar3 = (((int)(char)*local_c & 7U) << 0x12 | ((int)(char)local_c[1] & 0x3fU) << 0xc |
               ((int)(char)local_c[2] & 0x3fU) << 6 | (int)(char)local_c[3] & 0x3fU) - 0x10000;
      *local_8 = (ushort)(uVar3 >> 10) | 0xd800;
      local_8[1] = (ushort)uVar3 & 0x3ff | 0xdc00;
      local_8 = local_8 + 2;
      local_c = local_c + 4;
    }
    else {
      *local_8 = (short)(char)*local_c;
      local_8 = local_8 + 1;
      local_c = local_c + 1;
    }
  }
  *param_2 = local_c;
  *param_4 = local_8;
  return;
}



/* 00419f00 FUN_00419f00 */

void __cdecl FUN_00419f00(undefined4 param_1,int *param_2,int param_3,int *param_4,int param_5)

{
  byte bVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [17];
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  while( true ) {
    while( true ) {
      if (*param_2 == param_3) {
        return;
      }
      bVar1 = *(byte *)*param_2;
      if ((bVar1 & 0x80) != 0) break;
      if (*param_4 == param_5) {
        return;
      }
      *(undefined1 *)*param_4 = *(undefined1 *)*param_2;
      *param_4 = *param_4 + 1;
      *param_2 = *param_2 + 1;
    }
    if (param_5 - *param_4 < 2) break;
    *(byte *)*param_4 = (byte)((int)(uint)bVar1 >> 6) | 0xc0;
    *param_4 = *param_4 + 1;
    *(byte *)*param_4 = bVar1 & 0x3f | 0x80;
    *param_4 = *param_4 + 1;
    *param_2 = *param_2 + 1;
  }
  return;
}



/* 00419ff0 FUN_00419ff0 */

void __cdecl FUN_00419ff0(undefined4 param_1,int *param_2,int param_3,int *param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while ((*param_2 != param_3 && (*param_4 != param_5))) {
    *(ushort *)*param_4 = (ushort)*(byte *)*param_2;
    *param_4 = *param_4 + 2;
    *param_2 = *param_2 + 1;
  }
  return;
}



/* 0041a050 FUN_0041a050 */

void __cdecl FUN_0041a050(undefined4 param_1,int *param_2,int param_3,int *param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while ((*param_2 != param_3 && (*param_4 != param_5))) {
    *(undefined1 *)*param_4 = *(undefined1 *)*param_2;
    *param_4 = *param_4 + 1;
    *param_2 = *param_2 + 1;
  }
  return;
}



/* 0041a0b0 FUN_0041a0b0 */

void __cdecl
FUN_0041a0b0(undefined4 param_1,undefined4 *param_2,byte *param_3,int *param_4,int param_5)

{
  byte bVar1;
  byte bVar2;
  int iVar3;
  uint uVar4;
  undefined4 *puVar5;
  undefined4 local_5c [17];
  uint local_18;
  uint local_14;
  byte *local_8;
  
  puVar5 = local_5c;
  for (iVar3 = 0x16; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
  local_8 = (byte *)*param_2;
  do {
    if (local_8 == param_3) {
      *param_2 = local_8;
      return;
    }
    bVar1 = *local_8;
    local_14 = (uint)bVar1;
    local_18 = (uint)local_8[1];
    bVar2 = (byte)((int)local_14 >> 6);
    switch(local_18) {
    case 0:
      if (0x7f < local_14) goto switchD_0041a11c_caseD_1;
      if (*param_4 == param_5) {
        *param_2 = local_8;
        return;
      }
      *(byte *)*param_4 = bVar1;
      *param_4 = *param_4 + 1;
      break;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
switchD_0041a11c_caseD_1:
      if (param_5 - *param_4 < 2) {
        *param_2 = local_8;
        return;
      }
      *(byte *)*param_4 = bVar2 | local_8[1] << 2 | 0xc0;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = bVar1 & 0x3f | 0x80;
      *param_4 = *param_4 + 1;
      break;
    default:
      if (param_5 - *param_4 < 3) {
        *param_2 = local_8;
        return;
      }
      *(byte *)*param_4 = (byte)((int)local_18 >> 4) | 0xe0;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = (byte)((local_18 & 0xf) << 2) | bVar2 | 0x80;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = bVar1 & 0x3f | 0x80;
      *param_4 = *param_4 + 1;
      break;
    case 0xd8:
    case 0xd9:
    case 0xda:
    case 0xdb:
      if (param_5 - *param_4 < 4) {
        *param_2 = local_8;
        return;
      }
      uVar4 = ((local_18 & 3) << 2 | (int)local_14 >> 6) + 1;
      *(byte *)*param_4 = (byte)((int)uVar4 >> 2) | 0xf0;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = (byte)((int)local_14 >> 2) & 0xf | (byte)((uVar4 & 3) << 4) | 0x80;
      *param_4 = *param_4 + 1;
      bVar1 = local_8[2];
      *(byte *)*param_4 =
           (byte)((local_14 & 3) << 4) | (local_8[3] & 3) << 2 | (byte)((int)(uint)bVar1 >> 6) |
           0x80;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = bVar1 & 0x3f | 0x80;
      *param_4 = *param_4 + 1;
      local_8 = local_8 + 2;
    }
    local_8 = local_8 + 2;
  } while( true );
}



/* 0041a480 FUN_0041a480 */

void __cdecl FUN_0041a480(undefined4 param_1,int *param_2,int param_3,int *param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if (((param_5 - *param_4 >> 1) * 2 < param_3 - *param_2) &&
     ((*(byte *)(param_3 + -1) & 0xf8) == 0xd8)) {
    param_3 = param_3 + -2;
  }
  while ((*param_2 != param_3 && (*param_4 != param_5))) {
    *(ushort *)*param_4 = CONCAT11(*(undefined1 *)(*param_2 + 1),*(undefined1 *)*param_2);
    *param_4 = *param_4 + 2;
    *param_2 = *param_2 + 2;
  }
  return;
}



/* 0041a530 FUN_0041a530 */

void __cdecl
FUN_0041a530(undefined4 param_1,undefined4 *param_2,byte *param_3,int *param_4,int param_5)

{
  byte bVar1;
  byte bVar2;
  int iVar3;
  uint uVar4;
  undefined4 *puVar5;
  undefined4 local_5c [17];
  uint local_18;
  uint local_14;
  byte *local_8;
  
  puVar5 = local_5c;
  for (iVar3 = 0x16; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
  local_8 = (byte *)*param_2;
  do {
    if (local_8 == param_3) {
      *param_2 = local_8;
      return;
    }
    bVar1 = local_8[1];
    local_14 = (uint)bVar1;
    local_18 = (uint)*local_8;
    bVar2 = (byte)((int)local_14 >> 6);
    switch(local_18) {
    case 0:
      if (0x7f < local_14) goto switchD_0041a59c_caseD_1;
      if (*param_4 == param_5) {
        *param_2 = local_8;
        return;
      }
      *(byte *)*param_4 = bVar1;
      *param_4 = *param_4 + 1;
      break;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
switchD_0041a59c_caseD_1:
      if (param_5 - *param_4 < 2) {
        *param_2 = local_8;
        return;
      }
      *(byte *)*param_4 = bVar2 | *local_8 << 2 | 0xc0;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = bVar1 & 0x3f | 0x80;
      *param_4 = *param_4 + 1;
      break;
    default:
      if (param_5 - *param_4 < 3) {
        *param_2 = local_8;
        return;
      }
      *(byte *)*param_4 = (byte)((int)local_18 >> 4) | 0xe0;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = (byte)((local_18 & 0xf) << 2) | bVar2 | 0x80;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = bVar1 & 0x3f | 0x80;
      *param_4 = *param_4 + 1;
      break;
    case 0xd8:
    case 0xd9:
    case 0xda:
    case 0xdb:
      if (param_5 - *param_4 < 4) {
        *param_2 = local_8;
        return;
      }
      uVar4 = ((local_18 & 3) << 2 | (int)local_14 >> 6) + 1;
      *(byte *)*param_4 = (byte)((int)uVar4 >> 2) | 0xf0;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = (byte)((int)local_14 >> 2) & 0xf | (byte)((uVar4 & 3) << 4) | 0x80;
      *param_4 = *param_4 + 1;
      bVar1 = local_8[3];
      *(byte *)*param_4 =
           (byte)((local_14 & 3) << 4) | (local_8[2] & 3) << 2 | (byte)((int)(uint)bVar1 >> 6) |
           0x80;
      *param_4 = *param_4 + 1;
      *(byte *)*param_4 = bVar1 & 0x3f | 0x80;
      *param_4 = *param_4 + 1;
      local_8 = local_8 + 2;
    }
    local_8 = local_8 + 2;
  } while( true );
}



/* 0041a900 FUN_0041a900 */

void __cdecl FUN_0041a900(undefined4 param_1,int *param_2,int param_3,int *param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if (((param_5 - *param_4 >> 1) * 2 < param_3 - *param_2) &&
     ((*(byte *)(param_3 + -2) & 0xf8) == 0xd8)) {
    param_3 = param_3 + -2;
  }
  while ((*param_2 != param_3 && (*param_4 != param_5))) {
    *(ushort *)*param_4 = CONCAT11(*(undefined1 *)*param_2,*(undefined1 *)(*param_2 + 1));
    *param_4 = *param_4 + 2;
    *param_2 = *param_2 + 2;
  }
  return;
}



/* 0041a9b0 FUN_0041a9b0 */

/* WARNING: Removing unreachable block (ram,0x0041ab90) */
/* WARNING: Removing unreachable block (ram,0x0041abc8) */
/* WARNING: Removing unreachable block (ram,0x0041abfd) */

void __cdecl FUN_0041a9b0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  uint local_1c;
  uint local_18;
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_0041ad3b;
  local_8 = (int)param_3 - (int)param_2;
  if ((local_8 & 1) != 0) {
    local_8 = local_8 & 0xfffffffe;
    if (local_8 == 0) goto LAB_0041ad3b;
    param_3 = param_2 + local_8;
  }
  if (param_2[1] == 0) {
    local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  }
  else {
    local_c = FUN_0041adb0(param_2[1],*param_2);
  }
  local_10 = local_c;
  switch(local_c) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_0041ad3b;
  default:
    pbVar1 = param_2 + 2;
    break;
  case 4:
    pbVar1 = param_2 + 2;
    if (pbVar1 == param_3) goto LAB_0041ad3b;
    if ((param_2[3] == 0) && (*pbVar1 == 0x5d)) {
      if (param_2 + 4 == param_3) goto LAB_0041ad3b;
      if ((param_2[5] == 0) && (param_2[4] == 0x3e)) {
        *param_4 = param_2 + 6;
        goto LAB_0041ad3b;
      }
      pbVar1 = param_2 + 2;
    }
    break;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_0041ad3b;
    pbVar1 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_0041ad3b;
    pbVar1 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_0041ad3b;
    pbVar1 = param_2 + 4;
    break;
  case 9:
    pbVar1 = param_2 + 2;
    if (pbVar1 != param_3) {
      if (param_2[3] == 0) {
        local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar1);
      }
      else {
        local_14 = FUN_0041adb0(param_2[3],*pbVar1);
      }
      if (local_14 == 10) {
        pbVar1 = param_2 + 4;
      }
      param_2 = pbVar1;
      *param_4 = param_2;
    }
    goto LAB_0041ad3b;
  case 10:
    *param_4 = param_2 + 2;
    goto LAB_0041ad3b;
  }
  while (param_2 = pbVar1, param_2 != param_3) {
    if (param_2[1] == 0) {
      local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_18 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_1c = local_18;
    switch(local_18) {
    case 0:
    case 1:
    case 4:
    case 8:
    case 9:
    case 10:
      *param_4 = param_2;
      goto LAB_0041ad3b;
    default:
      pbVar1 = param_2 + 2;
      break;
    case 5:
      if ((int)param_3 - (int)param_2 < 2) {
        *param_4 = param_2;
        goto LAB_0041ad3b;
      }
      pbVar1 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) {
        *param_4 = param_2;
        goto LAB_0041ad3b;
      }
      pbVar1 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) {
        *param_4 = param_2;
        goto LAB_0041ad3b;
      }
      pbVar1 = param_2 + 4;
    }
  }
  *param_4 = param_2;
LAB_0041ad3b:
  local_8 = 0x41ad48;
  FUN_004291a7();
  return;
}



/* 0041adb0 FUN_0041adb0 */

/* WARNING: Removing unreachable block (ram,0x0041ae17) */

undefined4 __cdecl FUN_0041adb0(undefined1 param_1,byte param_2)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [18];
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  switch(param_1) {
  case 0xd8:
  case 0xd9:
  case 0xda:
  case 0xdb:
    uVar1 = 7;
    break;
  case 0xdc:
  case 0xdd:
  case 0xde:
  case 0xdf:
    uVar1 = 8;
    break;
  case 0xff:
    if (0xfd < param_2) {
      return 0;
    }
  default:
    uVar1 = 0x1d;
  }
  return uVar1;
}



/* 0041ae70 FUN_0041ae70 */

/* WARNING: Removing unreachable block (ram,0x0041b090) */
/* WARNING: Removing unreachable block (ram,0x0041b0c8) */
/* WARNING: Removing unreachable block (ram,0x0041b0fd) */

void __cdecl FUN_0041ae70(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  uint local_1c;
  uint local_18;
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar1 = 0x16; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_0041b2b3;
  local_8 = (int)param_3 - (int)param_2;
  if ((local_8 & 1) != 0) {
    local_8 = local_8 & 0xfffffffe;
    if (local_8 == 0) goto LAB_0041b2b3;
    param_3 = param_2 + local_8;
  }
  if (param_2[1] == 0) {
    local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  }
  else {
    local_c = FUN_0041adb0(param_2[1],*param_2);
  }
  local_10 = local_c;
  switch(local_c) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_0041b2b3;
  case 2:
    FUN_0041b970(param_1,param_2 + 2,param_3,param_4);
    goto LAB_0041b2b3;
  case 3:
    FUN_0041b320(param_1,param_2 + 2,param_3,param_4);
    goto LAB_0041b2b3;
  case 4:
    pbVar2 = param_2 + 2;
    if (pbVar2 == param_3) goto LAB_0041b2b3;
    if ((param_2[3] == 0) && (*pbVar2 == 0x5d)) {
      pbVar2 = param_2 + 4;
      if (pbVar2 == param_3) goto LAB_0041b2b3;
      if ((param_2[5] == 0) && (*pbVar2 == 0x3e)) {
        *param_4 = pbVar2;
        goto LAB_0041b2b3;
      }
      pbVar2 = param_2 + 2;
    }
    break;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_0041b2b3;
    pbVar2 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_0041b2b3;
    pbVar2 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_0041b2b3;
    pbVar2 = param_2 + 4;
    break;
  case 9:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if (param_2[3] == 0) {
        local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
      }
      else {
        local_14 = FUN_0041adb0(param_2[3],*pbVar2);
      }
      if (local_14 == 10) {
        pbVar2 = param_2 + 4;
      }
      param_2 = pbVar2;
      *param_4 = param_2;
    }
    goto LAB_0041b2b3;
  case 10:
    *param_4 = param_2 + 2;
    goto LAB_0041b2b3;
  default:
    pbVar2 = param_2 + 2;
  }
LAB_0041b12f:
  param_2 = pbVar2;
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_18 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_1c = local_18;
    switch(local_18) {
    case 4:
      if (param_2 + 2 != param_3) {
        if ((param_2[3] != 0) || (param_2[2] != 0x5d)) {
          pbVar2 = param_2 + 2;
          break;
        }
        if (param_2 + 4 != param_3) {
          if ((param_2[5] == 0) && (param_2[4] == 0x3e)) {
            *param_4 = param_2 + 4;
            goto LAB_0041b2b3;
          }
          pbVar2 = param_2 + 2;
          break;
        }
      }
    case 0:
    case 1:
    case 2:
    case 3:
    case 8:
    case 9:
    case 10:
      *param_4 = param_2;
      goto LAB_0041b2b3;
    case 5:
      if ((int)param_3 - (int)param_2 < 2) {
        *param_4 = param_2;
        goto LAB_0041b2b3;
      }
      pbVar2 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) {
        *param_4 = param_2;
        goto LAB_0041b2b3;
      }
      pbVar2 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) {
        *param_4 = param_2;
        goto LAB_0041b2b3;
      }
      pbVar2 = param_2 + 4;
      break;
    default:
      goto switchD_0041b186_default;
    }
    goto LAB_0041b12f;
  }
  *param_4 = param_2;
LAB_0041b2b3:
  local_8 = 0x41b2c0;
  FUN_004291a7();
  return;
switchD_0041b186_default:
  pbVar2 = param_2 + 2;
  goto LAB_0041b12f;
}



/* 0041b320 FUN_0041b320 */

/* WARNING: Removing unreachable block (ram,0x0041b606) */
/* WARNING: Removing unreachable block (ram,0x0041b4a3) */
/* WARNING: Removing unreachable block (ram,0x0041b430) */
/* WARNING: Removing unreachable block (ram,0x0041b46b) */
/* WARNING: Removing unreachable block (ram,0x0041b638) */
/* WARNING: Removing unreachable block (ram,0x0041b5d1) */

void __cdecl FUN_0041b320(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x13:
      FUN_0041b6f0(param_1,param_2 + 2,param_3,param_4);
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (pbVar1[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_10 = FUN_0041adb0(pbVar1[3],*param_2);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041b669;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041b669;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041b669;
        default:
          *param_4 = param_2;
          goto LAB_0041b669;
        case 0x12:
          *param_4 = pbVar1 + 4;
          goto LAB_0041b669;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_0041b661;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[pbVar1[3]] * 8) *
                        4) & 1 << (*param_2 & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_0041b669;
          }
LAB_0041b661:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_0041b669:
  local_8 = 0x41b676;
  FUN_004291a7();
  return;
}



/* 0041b6f0 FUN_0041b6f0 */

void __cdecl FUN_0041b6f0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if ((param_2[1] == 0) && (*param_2 == 0x78)) {
      FUN_0041b840(param_1,param_2 + 2,param_3,param_4);
    }
    else {
      if (param_2[1] == 0) {
        local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
      }
      else {
        local_8 = FUN_0041adb0(param_2[1],*param_2);
      }
      local_c = local_8;
      pbVar1 = param_2;
      if (local_8 == 0x19) {
        do {
          param_2 = pbVar1 + 2;
          if (param_2 == param_3) goto LAB_0041b82e;
          if (pbVar1[3] == 0) {
            local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
          }
          else {
            local_10 = FUN_0041adb0(pbVar1[3],*param_2);
          }
          local_14 = local_10;
          if (local_10 == 0x12) {
            *param_4 = pbVar1 + 4;
            goto LAB_0041b82e;
          }
          pbVar1 = param_2;
        } while (local_10 == 0x19);
        *param_4 = param_2;
      }
      else {
        *param_4 = param_2;
      }
    }
  }
LAB_0041b82e:
  local_8 = 0x41b83b;
  FUN_004291a7();
  return;
}



/* 0041b840 FUN_0041b840 */

void __cdecl FUN_0041b840(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8;
    if (((int)local_8 < 0x18) || (pbVar1 = param_2, 0x19 < (int)local_8)) {
      *param_4 = param_2;
    }
    else {
      do {
        param_2 = pbVar1 + 2;
        if (param_2 == param_3) goto LAB_0041b957;
        if (pbVar1[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_10 = FUN_0041adb0(pbVar1[3],*param_2);
        }
        local_14 = local_10;
        if (local_10 == 0x12) {
          *param_4 = pbVar1 + 4;
          goto LAB_0041b957;
        }
      } while ((0x17 < (int)local_10) && (pbVar1 = param_2, (int)local_10 < 0x1a));
      *param_4 = param_2;
    }
  }
LAB_0041b957:
  local_8 = 0x41b964;
  FUN_004291a7();
  return;
}



/* 0041b970 FUN_0041b970 */

/* WARNING: Removing unreachable block (ram,0x0041beb7) */
/* WARNING: Removing unreachable block (ram,0x0041bcfb) */
/* WARNING: Removing unreachable block (ram,0x0041bd71) */
/* WARNING: Removing unreachable block (ram,0x0041babb) */
/* WARNING: Removing unreachable block (ram,0x0041ba80) */
/* WARNING: Removing unreachable block (ram,0x0041baf6) */
/* WARNING: Removing unreachable block (ram,0x0041bd36) */
/* WARNING: Removing unreachable block (ram,0x0041be7c) */
/* WARNING: Removing unreachable block (ram,0x0041beef) */

void __cdecl FUN_0041b970(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_64 [16];
  int local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  uint local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_64;
  for (iVar1 = 0x18; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0xf:
      FUN_0041c2c0(param_1,param_2 + 2,param_3,param_4);
      break;
    case 0x10:
      pbVar2 = param_2 + 2;
      if (pbVar2 != param_3) {
        if (param_2[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
        }
        else {
          local_10 = FUN_0041adb0(param_2[3],*pbVar2);
        }
        local_14 = local_10;
        if (local_10 == 0x14) {
          FUN_0041c9e0(param_1,(char *)(param_2 + 4),(int)param_3,param_4);
        }
        else if (local_10 == 0x1b) {
          FUN_0041c080(param_1,param_2 + 4,param_3,param_4);
        }
        else {
          *param_4 = pbVar2;
        }
      }
      break;
    case 0x11:
      FUN_0041ca70(param_1,param_2 + 2,param_3,param_4);
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar2 = param_2;
      while (param_2 = pbVar2 + 2, param_2 != param_3) {
        if (pbVar2[3] == 0) {
          local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_18 = FUN_0041adb0(pbVar2[3],*param_2);
        }
        local_1c = local_18 - 5;
        switch(local_18) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041bfaa;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041bfaa;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041bfaa;
        default:
          *param_4 = param_2;
          goto LAB_0041bfaa;
        case 9:
        case 10:
        case 0x15:
          param_2 = pbVar2 + 4;
          goto LAB_0041bd88;
        case 0xb:
          goto switchD_0041bc6f_caseD_b;
        case 0x11:
          goto switchD_0041bc6f_caseD_11;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_0041bfa2;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[pbVar2[3]] * 8) *
                        4) & 1 << (*param_2 & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_0041bfaa;
          }
LAB_0041bfa2:
          pbVar2 = param_2;
        }
      }
    }
  }
LAB_0041bfaa:
  local_8 = 0x41bfb7;
  FUN_004291a7();
  return;
LAB_0041bd88:
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_20 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_24 = local_20 - 5;
    switch(local_20) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 9:
    case 10:
    case 0x15:
      goto switchD_0041bdf0_caseD_9;
    case 0xb:
switchD_0041bc6f_caseD_b:
      *param_4 = param_2 + 2;
      break;
    case 0x11:
switchD_0041bc6f_caseD_11:
      pbVar2 = param_2 + 2;
      if (pbVar2 != param_3) {
        if ((param_2[3] == 0) && (*pbVar2 == 0x3e)) {
          *param_4 = param_2 + 4;
        }
        else {
          *param_4 = pbVar2;
        }
      }
      break;
    case 0x16:
    case 0x18:
      goto switchD_0041bdf0_caseD_16;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
switchD_0041bdf0_caseD_16:
      FUN_0041cef0(param_1,param_2 + 2,param_3,param_4);
      break;
    }
  }
  goto LAB_0041bfaa;
switchD_0041bdf0_caseD_9:
  param_2 = param_2 + 2;
  goto LAB_0041bd88;
}



/* 0041c080 FUN_0041c080 */

/* WARNING: Removing unreachable block (ram,0x0041c18b) */
/* WARNING: Removing unreachable block (ram,0x0041c153) */
/* WARNING: Removing unreachable block (ram,0x0041c1c3) */

void __cdecl FUN_0041c080(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [16];
  uint local_c;
  uint local_8;
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if ((param_2[1] == 0) && (*param_2 == 0x2d)) {
      pbVar1 = param_2 + 2;
LAB_0041c0d2:
      param_2 = pbVar1;
      if (param_2 == param_3) goto LAB_0041c278;
      if (param_2[1] == 0) {
        local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
      }
      else {
        local_8 = FUN_0041adb0(param_2[1],*param_2);
      }
      local_c = local_8;
      switch(local_8) {
      case 0:
      case 1:
      case 8:
        *param_4 = param_2;
        goto LAB_0041c278;
      default:
        pbVar1 = param_2 + 2;
        goto LAB_0041c0d2;
      case 5:
        if ((int)param_3 - (int)param_2 < 2) goto LAB_0041c278;
        pbVar1 = param_2 + 2;
        goto LAB_0041c0d2;
      case 6:
        if ((int)param_3 - (int)param_2 < 3) goto LAB_0041c278;
        pbVar1 = param_2 + 3;
        goto LAB_0041c0d2;
      case 7:
        if ((int)param_3 - (int)param_2 < 4) goto LAB_0041c278;
        pbVar1 = param_2 + 4;
        goto LAB_0041c0d2;
      case 0x1b:
        goto switchD_0041c131_caseD_1b;
      }
    }
    *param_4 = param_2;
  }
LAB_0041c278:
  local_8 = 0x41c285;
  FUN_004291a7();
  return;
switchD_0041c131_caseD_1b:
  pbVar1 = param_2 + 2;
  if (pbVar1 == param_3) goto LAB_0041c278;
  if ((param_2[3] != 0) || (*pbVar1 != 0x2d)) goto LAB_0041c0d2;
  pbVar1 = param_2 + 4;
  if (pbVar1 != param_3) {
    if ((param_2[5] == 0) && (*pbVar1 == 0x3e)) {
      *param_4 = param_2 + 6;
    }
    else {
      *param_4 = pbVar1;
    }
  }
  goto LAB_0041c278;
}



/* 0041c2c0 FUN_0041c2c0 */

/* WARNING: Removing unreachable block (ram,0x0041c6c7) */
/* WARNING: Removing unreachable block (ram,0x0041c556) */
/* WARNING: Removing unreachable block (ram,0x0041c5cc) */
/* WARNING: Removing unreachable block (ram,0x0041c410) */
/* WARNING: Removing unreachable block (ram,0x0041c3d8) */
/* WARNING: Removing unreachable block (ram,0x0041c448) */
/* WARNING: Removing unreachable block (ram,0x0041c591) */
/* WARNING: Removing unreachable block (ram,0x0041c68f) */
/* WARNING: Removing unreachable block (ram,0x0041c6ff) */

void __cdecl FUN_0041c2c0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_64 [16];
  uint local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  byte *local_c;
  undefined4 local_8;
  
  puVar3 = local_64;
  for (iVar1 = 0x18; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_c = param_2;
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_10 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_14 = local_10 - 5;
    switch(local_10) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x16:
    case 0x18:
switchD_0041c34a_caseD_16:
      pbVar2 = param_2;
LAB_0041c462:
      param_2 = pbVar2 + 2;
      if (param_2 != param_3) {
        if (pbVar2[3] == 0) {
          local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_18 = FUN_0041adb0(pbVar2[3],*param_2);
        }
        local_1c = local_18 - 5;
        switch(local_18) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041c7f7;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041c7f7;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041c7f7;
        case 9:
        case 10:
        case 0x15:
          iVar1 = FUN_0041c8a0(param_1,(char *)local_c,(int)param_2,&local_8);
          if (iVar1 != 0) {
            pbVar2 = pbVar2 + 4;
            goto LAB_0041c60e;
          }
          *param_4 = param_2;
          goto LAB_0041c7f7;
        case 0xf:
          iVar1 = FUN_0041c8a0(param_1,(char *)local_c,(int)param_2,&local_8);
          if (iVar1 == 0) {
            *param_4 = param_2;
            goto LAB_0041c7f7;
          }
          param_2 = pbVar2 + 4;
          if (param_2 == param_3) goto LAB_0041c7f7;
          if ((pbVar2[5] == 0) && (*param_2 == 0x3e)) {
            *param_4 = pbVar2 + 6;
            goto LAB_0041c7f7;
          }
          break;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_0041c7ef;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[pbVar2[3]] * 8) *
                        4) & 1 << (*param_2 & 0x1f)) != 0) goto LAB_0041c7ef;
          *param_4 = param_2;
          goto LAB_0041c7f7;
        }
        *param_4 = param_2;
      }
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) != 0) goto switchD_0041c34a_caseD_16;
      *param_4 = param_2;
    }
  }
  goto LAB_0041c7f7;
LAB_0041c7ef:
  pbVar2 = param_2;
  goto LAB_0041c462;
LAB_0041c60e:
  param_2 = pbVar2;
  if (param_2 == param_3) goto LAB_0041c7f7;
  if (param_2[1] == 0) {
    local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  }
  else {
    local_20 = FUN_0041adb0(param_2[1],*param_2);
  }
  local_24 = local_20;
  switch(local_20) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_0041c7f7;
  default:
    pbVar2 = param_2 + 2;
    goto LAB_0041c60e;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_0041c7f7;
    pbVar2 = param_2 + 2;
    goto LAB_0041c60e;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_0041c7f7;
    pbVar2 = param_2 + 3;
    goto LAB_0041c60e;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_0041c7f7;
    pbVar2 = param_2 + 4;
    goto LAB_0041c60e;
  case 0xf:
    break;
  }
  pbVar2 = param_2 + 2;
  if (pbVar2 == param_3) goto LAB_0041c7f7;
  if ((param_2[3] != 0) || (*pbVar2 != 0x3e)) goto LAB_0041c60e;
  *param_4 = param_2 + 4;
LAB_0041c7f7:
  local_8 = 0x41c804;
  FUN_004291a7();
  return;
}



/* 0041c8a0 FUN_0041c8a0 */

undefined4 __cdecl FUN_0041c8a0(undefined4 param_1,char *param_2,int param_3,undefined4 *param_4)

{
  char cVar1;
  bool bVar2;
  undefined4 uVar3;
  int iVar4;
  undefined4 *puVar5;
  undefined4 local_60 [23];
  
  puVar5 = local_60;
  for (iVar4 = 0x17; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
  bVar2 = false;
  *param_4 = 0xb;
  if (param_3 - (int)param_2 == 6) {
    if (param_2[1] == '\0') {
      cVar1 = *param_2;
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'X') {
      bVar2 = true;
    }
    else if (cVar1 != 'x') {
      return 1;
    }
    if (param_2[3] == '\0') {
      cVar1 = param_2[2];
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'M') {
      bVar2 = true;
    }
    else if (cVar1 != 'm') {
      return 1;
    }
    if (param_2[5] == '\0') {
      cVar1 = param_2[4];
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'L') {
      bVar2 = true;
    }
    else if (cVar1 != 'l') {
      return 1;
    }
    if (bVar2) {
      uVar3 = 0;
    }
    else {
      *param_4 = 0xc;
      uVar3 = 1;
    }
  }
  else {
    uVar3 = 1;
  }
  return uVar3;
}



/* 0041c9e0 FUN_0041c9e0 */

undefined4 __cdecl FUN_0041c9e0(undefined4 param_1,char *param_2,int param_3,undefined4 *param_4)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [16];
  int local_8;
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_3 - (int)param_2 < 0xc) {
    uVar1 = 0xffffffff;
  }
  else {
    for (local_8 = 0; local_8 < 6; local_8 = local_8 + 1) {
      if ((param_2[1] != '\0') || (*param_2 != (&DAT_0042d85c)[local_8])) {
        *param_4 = param_2;
        return 0;
      }
      param_2 = param_2 + 2;
    }
    *param_4 = param_2;
    uVar1 = 8;
  }
  return uVar1;
}



/* 0041ca70 FUN_0041ca70 */

/* WARNING: Removing unreachable block (ram,0x0041cd3b) */
/* WARNING: Removing unreachable block (ram,0x0041cbf0) */
/* WARNING: Removing unreachable block (ram,0x0041cb80) */
/* WARNING: Removing unreachable block (ram,0x0041cbb8) */
/* WARNING: Removing unreachable block (ram,0x0041cd76) */
/* WARNING: Removing unreachable block (ram,0x0041cd00) */

void __cdecl FUN_0041ca70(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (pbVar1[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_10 = FUN_0041adb0(pbVar1[3],*param_2);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041ce53;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041ce53;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041ce53;
        default:
          *param_4 = param_2;
          goto LAB_0041ce53;
        case 9:
        case 10:
        case 0x15:
          param_2 = pbVar1 + 4;
          goto LAB_0041cd98;
        case 0xb:
          *param_4 = pbVar1 + 4;
          goto LAB_0041ce53;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_0041ce4b;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[pbVar1[3]] * 8) *
                        4) & 1 << (*param_2 & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_0041ce53;
          }
LAB_0041ce4b:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_0041ce53:
  local_8 = 0x41ce60;
  FUN_004291a7();
  return;
LAB_0041cd98:
  if (param_2 == param_3) goto LAB_0041ce53;
  if (param_2[1] == 0) {
    local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  }
  else {
    local_18 = FUN_0041adb0(param_2[1],*param_2);
  }
  local_1c = local_18 - 9;
  switch(local_18) {
  case 9:
  case 10:
  case 0x15:
    param_2 = param_2 + 2;
    break;
  case 0xb:
    *param_4 = param_2 + 2;
    goto LAB_0041ce53;
  default:
    *param_4 = param_2;
    goto LAB_0041ce53;
  }
  goto LAB_0041cd98;
}



/* 0041cef0 FUN_0041cef0 */

/* WARNING: Removing unreachable block (ram,0x0041d278) */
/* WARNING: Removing unreachable block (ram,0x0041cfff) */
/* WARNING: Removing unreachable block (ram,0x0041d075) */
/* WARNING: Removing unreachable block (ram,0x0041d501) */
/* WARNING: Removing unreachable block (ram,0x0041d53c) */
/* WARNING: Removing unreachable block (ram,0x0041d4c6) */
/* WARNING: Removing unreachable block (ram,0x0041d03a) */
/* WARNING: Removing unreachable block (ram,0x0041d240) */
/* WARNING: Removing unreachable block (ram,0x0041d2b0) */

void __cdecl FUN_0041cef0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  byte *pbVar3;
  undefined4 *puVar4;
  undefined4 local_84 [16];
  int local_44;
  uint local_40;
  int local_3c;
  uint local_38;
  uint local_34;
  uint local_30;
  uint local_2c;
  uint local_28;
  uint local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar4 = local_84;
  for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
LAB_0041cf0b:
  pbVar3 = param_2;
  if (param_2 == param_3) {
LAB_0041d5ca:
    param_2 = pbVar3;
    local_8 = 0x41d5da;
    FUN_004291a7();
    return;
  }
  if (param_2[1] == 0) {
    local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  }
  else {
    local_18 = FUN_0041adb0(param_2[1],*param_2);
  }
  local_1c = local_18 - 5;
  pbVar3 = param_2;
  switch(local_18) {
  case 5:
    if (1 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    goto LAB_0041d5ca;
  case 6:
    if (2 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    goto LAB_0041d5ca;
  case 7:
    if (3 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    goto LAB_0041d5ca;
  default:
    *param_4 = param_2;
    goto LAB_0041d5ca;
  case 9:
  case 10:
  case 0x15:
    while( true ) {
      pbVar3 = param_2 + 2;
      if (pbVar3 == param_3) goto LAB_0041d5ca;
      if (param_2[3] == 0) {
        local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar3);
        param_2 = pbVar3;
      }
      else {
        pbVar1 = param_2 + 3;
        param_2 = pbVar3;
        local_20 = FUN_0041adb0(*pbVar1,*pbVar3);
      }
      local_8 = local_20;
      if (local_20 == 0xe) break;
      local_24 = local_20;
      if (((int)local_20 < 9) || ((10 < (int)local_20 && (local_20 != 0x15)))) {
        *param_4 = param_2;
        pbVar3 = param_2;
        goto LAB_0041d5ca;
      }
    }
  case 0xe:
    while( true ) {
      pbVar3 = param_2 + 2;
      if (pbVar3 == param_3) goto LAB_0041d5ca;
      if (param_2[3] == 0) {
        local_28 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar3);
        param_2 = pbVar3;
      }
      else {
        pbVar1 = param_2 + 3;
        param_2 = pbVar3;
        local_28 = FUN_0041adb0(*pbVar1,*pbVar3);
      }
      local_c = local_28;
      if ((local_28 == 0xc) || (local_28 == 0xd)) break;
      local_2c = local_28;
      if (((int)local_28 < 9) || ((10 < (int)local_28 && (local_28 != 0x15)))) {
        *param_4 = param_2;
        pbVar3 = param_2;
        goto LAB_0041d5ca;
      }
    }
    param_2 = param_2 + 2;
    while( true ) {
      pbVar3 = param_2;
      if (param_2 == param_3) goto LAB_0041d5ca;
      if (param_2[1] == 0) {
        local_30 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
      }
      else {
        local_30 = FUN_0041adb0(param_2[1],*param_2);
      }
      local_10 = local_30;
      if (local_30 == local_c) break;
      local_34 = local_30;
      pbVar3 = param_2;
      switch(local_30) {
      case 0:
      case 1:
      case 8:
        *param_4 = param_2;
        goto LAB_0041d5ca;
      case 2:
        *param_4 = param_2;
        goto LAB_0041d5ca;
      case 3:
        local_14 = FUN_0041b320(param_1,param_2 + 2,param_3,&param_2);
        if (local_14 < 1) {
          pbVar3 = param_2;
          if (local_14 == 0) {
            *param_4 = param_2;
          }
          goto LAB_0041d5ca;
        }
        break;
      default:
        param_2 = param_2 + 2;
        break;
      case 5:
        if ((int)param_3 - (int)param_2 < 2) goto LAB_0041d5ca;
        param_2 = param_2 + 2;
        break;
      case 6:
        if ((int)param_3 - (int)param_2 < 3) goto LAB_0041d5ca;
        param_2 = param_2 + 3;
        break;
      case 7:
        if ((int)param_3 - (int)param_2 < 4) goto LAB_0041d5ca;
        param_2 = param_2 + 4;
      }
    }
    pbVar3 = param_2 + 2;
    if (pbVar3 != param_3) {
      if (param_2[3] == 0) {
        local_38 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar3);
        param_2 = pbVar3;
      }
      else {
        pbVar1 = param_2 + 3;
        param_2 = pbVar3;
        local_38 = FUN_0041adb0(*pbVar1,*pbVar3);
      }
      local_3c = local_38 - 9;
      switch(local_38) {
      case 9:
      case 10:
      case 0x15:
        do {
          pbVar3 = param_2 + 2;
          if (pbVar3 == param_3) break;
          if (param_2[3] == 0) {
            local_40 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar3);
            param_2 = pbVar3;
          }
          else {
            pbVar1 = param_2 + 3;
            param_2 = pbVar3;
            local_40 = FUN_0041adb0(*pbVar1,*pbVar3);
          }
          local_44 = local_40 - 5;
          pbVar3 = param_2;
          switch(local_40) {
          case 5:
            if (1 < (int)param_3 - (int)param_2) {
              *param_4 = param_2;
            }
            goto LAB_0041d5ca;
          case 6:
            if (2 < (int)param_3 - (int)param_2) {
              *param_4 = param_2;
            }
            goto LAB_0041d5ca;
          case 7:
            if (3 < (int)param_3 - (int)param_2) {
              *param_4 = param_2;
            }
            goto LAB_0041d5ca;
          default:
            *param_4 = param_2;
            goto LAB_0041d5ca;
          case 9:
          case 10:
          case 0x15:
            break;
          case 0xb:
            goto switchD_0041d43a_caseD_b;
          case 0x11:
            goto switchD_0041d43a_caseD_11;
          case 0x16:
          case 0x18:
            goto LAB_0041d5b4;
          case 0x1d:
            if ((*(uint *)(&DAT_0042cb98 +
                          (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8)
                          * 4) & 1 << (*param_2 & 0x1f)) == 0) {
              *param_4 = param_2;
              goto LAB_0041d5ca;
            }
            goto LAB_0041d5b4;
          }
        } while( true );
      case 0xb:
switchD_0041d43a_caseD_b:
        *param_4 = param_2 + 2;
        pbVar3 = param_2;
        break;
      default:
        *param_4 = param_2;
        pbVar3 = param_2;
        break;
      case 0x11:
switchD_0041d43a_caseD_11:
        pbVar3 = param_2 + 2;
        if (pbVar3 != param_3) {
          if ((param_2[3] == 0) && (*pbVar3 == 0x3e)) {
            *param_4 = param_2 + 4;
          }
          else {
            *param_4 = pbVar3;
          }
        }
      }
    }
    goto LAB_0041d5ca;
  case 0x16:
  case 0x18:
  case 0x19:
  case 0x1a:
  case 0x1b:
    goto switchD_0041cf73_caseD_16;
  case 0x1d:
    if ((*(uint *)(&DAT_0042cb98 +
                  (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[param_2[1]] * 8) * 4) &
        1 << (*param_2 & 0x1f)) == 0) {
      *param_4 = param_2;
      goto LAB_0041d5ca;
    }
switchD_0041cf73_caseD_16:
    goto LAB_0041d5c2;
  }
LAB_0041d5b4:
LAB_0041d5c2:
  param_2 = param_2 + 2;
  goto LAB_0041cf0b;
}



/* 0041d6a0 FUN_0041d6a0 */

/* WARNING: Removing unreachable block (ram,0x0041de58) */
/* WARNING: Removing unreachable block (ram,0x0041dc17) */
/* WARNING: Removing unreachable block (ram,0x0041db7e) */
/* WARNING: Removing unreachable block (ram,0x0041dbd8) */
/* WARNING: Removing unreachable block (ram,0x0041dc32) */
/* WARNING: Removing unreachable block (ram,0x0041dbbd) */
/* WARNING: Removing unreachable block (ram,0x0041db63) */
/* WARNING: Removing unreachable block (ram,0x0041de93) */
/* WARNING: Removing unreachable block (ram,0x0041de1d) */

void __cdecl FUN_0041d6a0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  byte *pbVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_74 [16];
  int local_34;
  uint local_30;
  int local_2c;
  uint local_28;
  uint local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  uint local_c;
  int local_8;
  
  puVar4 = local_74;
  for (iVar3 = 0x1c; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  if (param_2 == param_3) goto LAB_0041df33;
  local_c = (int)param_3 - (int)param_2;
  if ((local_c & 1) != 0) {
    local_c = local_c & 0xfffffffe;
    if (local_c == 0) goto LAB_0041df33;
    param_3 = param_2 + local_c;
  }
  if (param_2[1] == 0) {
    local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  }
  else {
    local_10 = FUN_0041adb0(param_2[1],*param_2);
  }
  local_14 = local_10 - 2;
  switch(local_10) {
  case 2:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if (param_2[3] == 0) {
        local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
      }
      else {
        local_18 = FUN_0041adb0(param_2[3],*pbVar2);
      }
      local_1c = local_18 - 5;
      switch(local_18) {
      case 5:
      case 6:
      case 7:
      case 0x16:
      case 0x18:
      case 0x1d:
        *param_4 = param_2;
        break;
      default:
        *param_4 = pbVar2;
        break;
      case 0xf:
        FUN_0041c2c0(param_1,param_2 + 4,param_3,param_4);
        break;
      case 0x10:
        FUN_0041e060(param_1,param_2 + 4,param_3,param_4);
      }
    }
    break;
  default:
switchD_0041d762_caseD_3:
    *param_4 = param_2;
    break;
  case 4:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if ((param_2[3] == 0) && (*pbVar2 == 0x5d)) {
        if (param_2 + 4 == param_3) break;
        if ((param_2[5] == 0) && (param_2[4] == 0x3e)) {
          *param_4 = param_2 + 6;
          break;
        }
      }
      *param_4 = pbVar2;
    }
    break;
  case 5:
    if (1 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    break;
  case 6:
    if (2 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    break;
  case 7:
    if (3 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    break;
  case 9:
    if (param_2 + 2 == param_3) {
      *param_4 = param_3;
      break;
    }
  case 10:
  case 0x15:
    do {
      while( true ) {
        pbVar2 = param_2 + 2;
        if (pbVar2 == param_3) {
          *param_4 = pbVar2;
          goto LAB_0041df33;
        }
        if (param_2[3] == 0) {
          local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
        }
        else {
          local_20 = FUN_0041adb0(param_2[3],*pbVar2);
        }
        local_24 = local_20;
        if (local_20 == 9) break;
        param_2 = pbVar2;
        if ((local_20 != 10) && (local_20 != 0x15)) goto LAB_0041d91a;
      }
      pbVar1 = param_2 + 4;
      param_2 = pbVar2;
    } while (pbVar1 != param_3);
LAB_0041d91a:
    *param_4 = pbVar2;
    break;
  case 0xb:
    *param_4 = param_2 + 2;
    break;
  case 0xc:
    FUN_0041ea30(0xc,param_1,param_2 + 2,param_3,param_4);
    break;
  case 0xd:
    FUN_0041ea30(0xd,param_1,param_2 + 2,param_3,param_4);
    break;
  case 0x13:
    FUN_0041e680(param_1,param_2 + 2,param_3,param_4);
    break;
  case 0x14:
    *param_4 = param_2 + 2;
    break;
  case 0x16:
  case 0x18:
    local_8 = 0x12;
    param_2 = param_2 + 2;
    goto LAB_0041dd29;
  case 0x19:
  case 0x1a:
  case 0x1b:
    local_8 = 0x13;
    param_2 = param_2 + 2;
    goto LAB_0041dd29;
  case 0x1d:
    if ((*(uint *)(&DAT_0042cb98 +
                  (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4) &
        1 << (*param_2 & 0x1f)) == 0) {
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) == 0) goto switchD_0041d762_caseD_3;
      param_2 = param_2 + 2;
      local_8 = 0x13;
    }
    else {
      param_2 = param_2 + 2;
      local_8 = 0x12;
    }
LAB_0041dd29:
    while (param_2 != param_3) {
      if (param_2[1] == 0) {
        local_30 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
      }
      else {
        local_30 = FUN_0041adb0(param_2[1],*param_2);
      }
      local_34 = local_30 - 5;
      switch(local_30) {
      case 5:
        if (1 < (int)param_3 - (int)param_2) {
          *param_4 = param_2;
        }
        goto LAB_0041df33;
      case 6:
        if (2 < (int)param_3 - (int)param_2) {
          *param_4 = param_2;
        }
        goto LAB_0041df33;
      case 7:
        if (3 < (int)param_3 - (int)param_2) {
          *param_4 = param_2;
        }
        goto LAB_0041df33;
      default:
        *param_4 = param_2;
        goto LAB_0041df33;
      case 9:
      case 10:
      case 0xb:
      case 0x14:
      case 0x15:
      case 0x1e:
      case 0x20:
      case 0x23:
      case 0x24:
        *param_4 = param_2;
        goto LAB_0041df33;
      case 0xf:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 2;
        }
        goto LAB_0041df33;
      case 0x1d:
        if ((*(uint *)(&DAT_0042cb98 +
                      (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[param_2[1]] * 8) * 4
                      ) & 1 << (*param_2 & 0x1f)) == 0) {
          *param_4 = param_2;
          goto LAB_0041df33;
        }
      case 0x16:
      case 0x18:
      case 0x19:
      case 0x1a:
      case 0x1b:
        param_2 = param_2 + 2;
        break;
      case 0x21:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 2;
        }
        goto LAB_0041df33;
      case 0x22:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 2;
        }
        goto LAB_0041df33;
      }
    }
    break;
  case 0x1e:
    FUN_0041e2c0(param_1,param_2 + 2,param_3,param_4);
    break;
  case 0x1f:
    *param_4 = param_2 + 2;
    break;
  case 0x20:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if (param_2[3] == 0) {
        local_28 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
      }
      else {
        local_28 = FUN_0041adb0(param_2[3],*pbVar2);
      }
      local_2c = local_28 - 9;
      switch(local_28) {
      case 9:
      case 10:
      case 0xb:
      case 0x15:
      case 0x20:
      case 0x23:
      case 0x24:
        *param_4 = pbVar2;
        break;
      default:
        *param_4 = pbVar2;
        break;
      case 0xf:
        *param_4 = param_2 + 4;
        break;
      case 0x21:
        *param_4 = param_2 + 4;
        break;
      case 0x22:
        *param_4 = param_2 + 4;
      }
    }
    break;
  case 0x23:
    *param_4 = param_2 + 2;
    break;
  case 0x24:
    *param_4 = param_2 + 2;
  }
LAB_0041df33:
  local_8 = 0x41df40;
  FUN_004291a7();
  return;
}



/* 0041e060 FUN_0041e060 */

void __cdecl FUN_0041e060(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 0x14;
    switch(local_c) {
    case 0:
      *param_4 = param_2 + 2;
      break;
    default:
      *param_4 = param_2;
      break;
    case 2:
    case 4:
      pbVar1 = param_2;
LAB_0041e12e:
      param_2 = pbVar1 + 2;
      if (param_2 != param_3) {
        if (pbVar1[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_10 = FUN_0041adb0(pbVar1[3],*param_2);
        }
        local_14 = local_10 - 9;
        switch(local_10) {
        case 9:
        case 10:
        case 0x15:
          goto switchD_0041e196_caseD_9;
        default:
          *param_4 = param_2;
          break;
        case 0x16:
        case 0x18:
          goto LAB_0041e243;
        case 0x1e:
          if (pbVar1 + 4 == param_3) break;
          if (pbVar1[5] == 0) {
            local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar1[4]);
          }
          else {
            local_18 = FUN_0041adb0(pbVar1[5],pbVar1[4]);
          }
          local_1c = local_18 - 9;
          switch(local_18) {
          case 9:
          case 10:
          case 0x15:
          case 0x1e:
            *param_4 = param_2;
            goto LAB_0041e24b;
          }
switchD_0041e196_caseD_9:
          *param_4 = param_2;
        }
      }
      break;
    case 7:
      FUN_0041c080(param_1,param_2 + 2,param_3,param_4);
    }
  }
LAB_0041e24b:
  local_8 = 0x41e258;
  FUN_004291a7();
  return;
LAB_0041e243:
  pbVar1 = param_2;
  goto LAB_0041e12e;
}



/* 0041e2c0 FUN_0041e2c0 */

/* WARNING: Removing unreachable block (ram,0x0041e59a) */
/* WARNING: Removing unreachable block (ram,0x0041e445) */
/* WARNING: Removing unreachable block (ram,0x0041e3d2) */
/* WARNING: Removing unreachable block (ram,0x0041e40d) */
/* WARNING: Removing unreachable block (ram,0x0041e5cc) */
/* WARNING: Removing unreachable block (ram,0x0041e565) */

void __cdecl FUN_0041e2c0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 9:
    case 10:
    case 0x15:
    case 0x1e:
      *param_4 = param_2;
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (pbVar1[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_10 = FUN_0041adb0(pbVar1[3],*param_2);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041e5fd;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041e5fd;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041e5fd;
        default:
          *param_4 = param_2;
          goto LAB_0041e5fd;
        case 0x12:
          *param_4 = pbVar1 + 4;
          goto LAB_0041e5fd;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_0041e5f5;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[pbVar1[3]] * 8) *
                        4) & 1 << (*param_2 & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_0041e5fd;
          }
LAB_0041e5f5:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_0041e5fd:
  local_8 = 0x41e60a;
  FUN_004291a7();
  return;
}



/* 0041e680 FUN_0041e680 */

/* WARNING: Removing unreachable block (ram,0x0041e942) */
/* WARNING: Removing unreachable block (ram,0x0041e800) */
/* WARNING: Removing unreachable block (ram,0x0041e790) */
/* WARNING: Removing unreachable block (ram,0x0041e7c8) */
/* WARNING: Removing unreachable block (ram,0x0041e974) */
/* WARNING: Removing unreachable block (ram,0x0041e910) */

void __cdecl FUN_0041e680(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d098)[param_2[1]] * 8) * 4)
          & 1 << (*param_2 & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (pbVar1[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
        }
        else {
          local_10 = FUN_0041adb0(pbVar1[3],*param_2);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041e9a4;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041e9a4;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0041e9a4;
        default:
          *param_4 = param_2;
          goto LAB_0041e9a4;
        case 9:
        case 10:
        case 0xb:
        case 0x15:
        case 0x1e:
        case 0x20:
        case 0x24:
          *param_4 = param_2;
          goto LAB_0041e9a4;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_0041e99a;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)*param_2 >> 5) + (uint)(byte)(&DAT_0042d198)[pbVar1[3]] * 8) *
                        4) & 1 << (*param_2 & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_0041e9a4;
          }
LAB_0041e99a:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_0041e9a4:
  local_8 = 0x41e9b1;
  FUN_004291a7();
  return;
}



/* 0041ea30 FUN_0041ea30 */

/* WARNING: Removing unreachable block (ram,0x0041eb07) */
/* WARNING: Removing unreachable block (ram,0x0041eacf) */
/* WARNING: Removing unreachable block (ram,0x0041eb3f) */

void __cdecl FUN_0041ea30(uint param_1,int param_2,byte *param_3,byte *param_4,undefined4 *param_5)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_58 [16];
  int local_18;
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_58;
  for (iVar1 = 0x15; pbVar2 = param_3, iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  do {
    param_3 = pbVar2;
    if (param_3 == param_4) goto LAB_0041ec16;
    if (param_3[1] == 0) {
      local_c = (uint)*(byte *)(param_2 + 0x48 + (uint)*param_3);
    }
    else {
      local_c = FUN_0041adb0(param_3[1],*param_3);
    }
    local_8 = local_c;
    local_10 = local_c;
    switch(local_c) {
    case 0:
    case 1:
    case 8:
      *param_5 = param_3;
      goto LAB_0041ec16;
    default:
      pbVar2 = param_3 + 2;
      break;
    case 5:
      if ((int)param_4 - (int)param_3 < 2) goto LAB_0041ec16;
      pbVar2 = param_3 + 2;
      break;
    case 6:
      if ((int)param_4 - (int)param_3 < 3) goto LAB_0041ec16;
      pbVar2 = param_3 + 3;
      break;
    case 7:
      if ((int)param_4 - (int)param_3 < 4) goto LAB_0041ec16;
      pbVar2 = param_3 + 4;
      break;
    case 0xc:
    case 0xd:
      pbVar2 = param_3 + 2;
      if (local_c == param_1) {
        if (pbVar2 != param_4) {
          *param_5 = pbVar2;
          if (param_3[3] == 0) {
            local_14 = (uint)*(byte *)(param_2 + 0x48 + (uint)*pbVar2);
          }
          else {
            local_14 = FUN_0041adb0(param_3[3],*pbVar2);
          }
          local_18 = local_14 - 9;
          switch(local_14) {
          case 9:
          case 10:
          case 0xb:
          case 0x14:
          case 0x15:
          case 0x1e:
            break;
          default:
          }
        }
LAB_0041ec16:
        local_8 = 0x41ec23;
        FUN_004291a7();
        return;
      }
    }
  } while( true );
}



/* 0041ec70 FUN_0041ec70 */

void __cdecl FUN_0041ec70(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  uint local_14;
  int local_10;
  uint local_c;
  byte *local_8;
  
  puVar3 = local_54;
  for (iVar1 = 0x14; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    local_8 = param_2;
    while (param_2 != param_3) {
      if (param_2[1] == 0) {
        local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
      }
      else {
        local_c = FUN_0041adb0(param_2[1],*param_2);
      }
      local_10 = local_c - 2;
      switch(local_c) {
      case 2:
        *param_4 = param_2;
        goto LAB_0041ee7d;
      case 3:
        if (param_2 == local_8) {
          FUN_0041b320(param_1,param_2 + 2,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041ee7d;
      default:
        param_2 = param_2 + 2;
        break;
      case 5:
        param_2 = param_2 + 2;
        break;
      case 6:
        param_2 = param_2 + 3;
        break;
      case 7:
        param_2 = param_2 + 4;
        break;
      case 9:
        if (param_2 == local_8) {
          pbVar2 = param_2 + 2;
          if (pbVar2 != param_3) {
            if (param_2[3] == 0) {
              local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
            }
            else {
              local_14 = FUN_0041adb0(param_2[3],*pbVar2);
            }
            if (local_14 == 10) {
              pbVar2 = param_2 + 4;
            }
            param_2 = pbVar2;
            *param_4 = param_2;
          }
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041ee7d;
      case 10:
        if (param_2 == local_8) {
          *param_4 = param_2 + 2;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041ee7d;
      case 0x15:
        if (param_2 == local_8) {
          *param_4 = param_2 + 2;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041ee7d;
      }
    }
    *param_4 = param_2;
  }
LAB_0041ee7d:
  local_8 = (byte *)0x41ee8a;
  FUN_004291a7();
  return;
}



/* 0041eed0 FUN_0041eed0 */

void __cdecl FUN_0041eed0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_58 [16];
  uint local_18;
  int local_14;
  uint local_10;
  undefined4 local_c;
  byte *local_8;
  
  puVar3 = local_58;
  for (iVar1 = 0x15; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    local_8 = param_2;
    while (param_2 != param_3) {
      if (param_2[1] == 0) {
        local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
      }
      else {
        local_10 = FUN_0041adb0(param_2[1],*param_2);
      }
      local_14 = local_10 - 3;
      switch(local_10) {
      case 3:
        if (param_2 == local_8) {
          FUN_0041b320(param_1,param_2 + 2,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041f0ef;
      default:
        param_2 = param_2 + 2;
        break;
      case 5:
        param_2 = param_2 + 2;
        break;
      case 6:
        param_2 = param_2 + 3;
        break;
      case 7:
        param_2 = param_2 + 4;
        break;
      case 9:
        if (param_2 == local_8) {
          pbVar2 = param_2 + 2;
          if (pbVar2 != param_3) {
            if (param_2[3] == 0) {
              local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
            }
            else {
              local_18 = FUN_0041adb0(param_2[3],*pbVar2);
            }
            if (local_18 == 10) {
              pbVar2 = param_2 + 4;
            }
            param_2 = pbVar2;
            *param_4 = param_2;
          }
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041f0ef;
      case 10:
        if (param_2 == local_8) {
          *param_4 = param_2 + 2;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041f0ef;
      case 0x1e:
        if (param_2 == local_8) {
          local_c = FUN_0041e2c0(param_1,param_2 + 2,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0041f0ef;
      }
    }
    *param_4 = param_2;
  }
LAB_0041f0ef:
  local_8 = (byte *)0x41f0fc;
  FUN_004291a7();
  return;
}



/* 0041f140 FUN_0041f140 */

void __cdecl FUN_0041f140(int param_1,byte *param_2,int param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_58 [16];
  int local_18;
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_58;
  for (iVar2 = 0x15; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
LAB_0041f175:
  pbVar1 = param_2;
  param_2 = pbVar1 + 2;
  if (param_2 == (byte *)(param_3 + -2)) goto LAB_0041f27b;
  if (pbVar1[3] == 0) {
    local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
  }
  else {
    local_8 = FUN_0041adb0(pbVar1[3],*param_2);
  }
  local_c = local_8 - 9;
  switch(local_8) {
  case 9:
  case 10:
  case 0xd:
  case 0xe:
  case 0xf:
  case 0x10:
  case 0x11:
  case 0x12:
  case 0x13:
  case 0x18:
  case 0x19:
  case 0x1b:
  case 0x1e:
  case 0x1f:
  case 0x20:
  case 0x21:
  case 0x22:
  case 0x23:
    goto LAB_0041f175;
  case 0x15:
    goto switchD_0041f1d9_caseD_15;
  case 0x16:
  case 0x1a:
    if (pbVar1[3] == 0) {
      local_10 = (uint)(char)*param_2;
    }
    else {
      local_10 = 0xffffffff;
    }
    if ((local_10 & 0xffffff80) == 0) goto LAB_0041f175;
  }
  if (pbVar1[3] == 0) {
    local_14 = (int)(char)*param_2;
  }
  else {
    local_14 = -1;
  }
  local_18 = local_14;
  if ((local_14 != 0x24) && (local_14 != 0x40)) {
    *param_4 = param_2;
    goto LAB_0041f27b;
  }
  goto LAB_0041f175;
switchD_0041f1d9_caseD_15:
  if ((pbVar1[3] == 0) && (*param_2 == 9)) {
    *param_4 = param_2;
LAB_0041f27b:
    local_8 = 0x41f288;
    FUN_004291a7();
    return;
  }
  goto LAB_0041f175;
}



/* 0041f2c0 FUN_0041f2c0 */

void __cdecl FUN_0041f2c0(int param_1,byte *param_2,int param_3,int param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_64 [16];
  uint local_24;
  int local_20;
  int local_1c;
  int local_18;
  uint local_14;
  uint local_10;
  int local_c;
  int local_8;
  
  puVar3 = local_64;
  for (iVar2 = 0x18; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_8 = 1;
  local_c = 0;
  local_10 = 0;
  pbVar1 = param_2;
  do {
    param_2 = pbVar1 + 2;
    if (pbVar1[3] == 0) {
      local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_14 = FUN_0041adb0(pbVar1[3],*param_2);
    }
    local_18 = local_14 - 3;
    switch(local_14) {
    case 3:
      if (local_c < param_3) {
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 5:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      break;
    case 6:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      param_2 = pbVar1 + 3;
      break;
    case 7:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      param_2 = pbVar1 + 4;
      break;
    case 9:
    case 10:
      if (local_8 == 1) {
        local_8 = 0;
      }
      else if ((local_8 == 2) && (local_c < param_3)) {
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 0xb:
    case 0x11:
      if (local_8 != 2) {
        local_8 = 0x41f672;
        FUN_004291a7();
        return;
      }
      break;
    case 0xc:
      if (local_8 == 2) {
        if (local_10 == 0xc) {
          local_8 = 0;
          if (local_c < param_3) {
            *(byte **)(param_4 + 8 + local_c * 0x10) = param_2;
          }
          local_c = local_c + 1;
        }
      }
      else {
        if (local_c < param_3) {
          *(byte **)(param_4 + 4 + local_c * 0x10) = pbVar1 + 4;
        }
        local_8 = 2;
        local_10 = 0xc;
      }
      break;
    case 0xd:
      if (local_8 == 2) {
        if (local_10 == 0xd) {
          local_8 = 0;
          if (local_c < param_3) {
            *(byte **)(param_4 + 8 + local_c * 0x10) = param_2;
          }
          local_c = local_c + 1;
        }
      }
      else {
        if (local_c < param_3) {
          *(byte **)(param_4 + 4 + local_c * 0x10) = pbVar1 + 4;
        }
        local_8 = 2;
        local_10 = 0xd;
      }
      break;
    case 0x15:
      if (local_8 == 1) {
        local_8 = 0;
      }
      else if (((local_8 == 2) && (local_c < param_3)) &&
              (*(char *)(param_4 + 0xc + local_c * 0x10) != '\0')) {
        if (param_2 != *(byte **)(param_4 + 4 + local_c * 0x10)) {
          if (pbVar1[3] == 0) {
            local_1c = (int)(char)*param_2;
          }
          else {
            local_1c = -1;
          }
          if (local_1c == 0x20) {
            if (pbVar1[5] == 0) {
              local_20 = (int)(char)pbVar1[4];
            }
            else {
              local_20 = -1;
            }
            if (local_20 != 0x20) {
              if (pbVar1[5] == 0) {
                local_24 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar1[4]);
              }
              else {
                local_24 = FUN_0041adb0(pbVar1[5],pbVar1[4]);
              }
              if (local_24 != local_10) break;
            }
          }
        }
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 0x16:
    case 0x18:
    case 0x1d:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(byte **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
    }
    pbVar1 = param_2;
  } while( true );
}



/* 0041f6c0 FUN_0041f6c0 */

void __cdecl FUN_0041f6c0(undefined4 param_1,char *param_2)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  int local_1c;
  int local_18;
  int local_14;
  int local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_8 = 0;
  pcVar1 = param_2 + 4;
  if ((param_2[5] == '\0') && (*pcVar1 == 'x')) {
    param_2 = param_2 + 6;
    while( true ) {
      if ((param_2[1] == '\0') && (*param_2 == ';')) goto LAB_0041f844;
      if (param_2[1] == '\0') {
        local_14 = (int)*param_2;
      }
      else {
        local_14 = -1;
      }
      local_c = local_14;
      local_18 = local_14 + -0x30;
      switch(local_14) {
      case 0x30:
      case 0x31:
      case 0x32:
      case 0x33:
      case 0x34:
      case 0x35:
      case 0x36:
      case 0x37:
      case 0x38:
      case 0x39:
        local_8 = local_8 << 4 | local_14 - 0x30U;
        break;
      case 0x41:
      case 0x42:
      case 0x43:
      case 0x44:
      case 0x45:
      case 0x46:
        local_8 = local_8 * 0x10 + -0x37 + local_14;
        break;
      case 0x61:
      case 0x62:
      case 99:
      case 100:
      case 0x65:
      case 0x66:
        local_8 = local_8 * 0x10 + -0x57 + local_14;
      }
      if (0x10ffff < (int)local_8) break;
      param_2 = param_2 + 2;
    }
  }
  else {
    while( true ) {
      param_2 = pcVar1;
      if ((param_2[1] == '\0') && (*param_2 == ';')) goto LAB_0041f844;
      if (param_2[1] == '\0') {
        local_1c = (int)*param_2;
      }
      else {
        local_1c = -1;
      }
      local_10 = local_1c;
      local_8 = local_8 * 10 + -0x30 + local_1c;
      if (0x10ffff < (int)local_8) break;
      pcVar1 = param_2 + 2;
    }
  }
LAB_0041f850:
  local_8 = 0x41f85d;
  FUN_004291a7();
  return;
LAB_0041f844:
  FUN_004257d0(local_8);
  goto LAB_0041f850;
}



/* 0041f8b0 FUN_0041f8b0 */

undefined4 __cdecl FUN_0041f8b0(undefined4 param_1,char *param_2,int param_3)

{
  char cVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_58 [21];
  
  puVar3 = local_58;
  for (iVar2 = 0x15; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  iVar2 = (param_3 - (int)param_2) / 2;
  if (iVar2 == 2) {
    if ((param_2[3] == '\0') && (param_2[2] == 't')) {
      if (param_2[1] == '\0') {
        cVar1 = *param_2;
      }
      else {
        cVar1 = -1;
      }
      if (cVar1 == 'g') {
        return 0x3e;
      }
      if (cVar1 == 'l') {
        return 0x3c;
      }
    }
  }
  else if (iVar2 == 3) {
    if (((((param_2[1] == '\0') && (*param_2 == 'a')) && (param_2[3] == '\0')) &&
        ((param_2[2] == 'm' && (param_2[5] == '\0')))) && (param_2[4] == 'p')) {
      return 0x26;
    }
  }
  else if (iVar2 == 4) {
    if (param_2[1] == '\0') {
      cVar1 = *param_2;
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'a') {
      if (((param_2[3] == '\0') && (param_2[2] == 'p')) &&
         ((param_2[5] == '\0' &&
          (((param_2[4] == 'o' && (param_2[7] == '\0')) && (param_2[6] == 's')))))) {
        return 0x27;
      }
    }
    else if ((((cVar1 == 'q') && (param_2[3] == '\0')) &&
             ((param_2[2] == 'u' && ((param_2[5] == '\0' && (param_2[4] == 'o')))))) &&
            ((param_2[7] == '\0' && (param_2[6] == 't')))) {
      return 0x22;
    }
  }
  return 0;
}



/* 0041fac0 FUN_0041fac0 */

/* WARNING: Removing unreachable block (ram,0x0041fc49) */
/* WARNING: Removing unreachable block (ram,0x0041fc6b) */
/* WARNING: Removing unreachable block (ram,0x0041fc72) */
/* WARNING: Removing unreachable block (ram,0x0041fc78) */
/* WARNING: Removing unreachable block (ram,0x0041fc9a) */
/* WARNING: Removing unreachable block (ram,0x0041fca6) */

void __cdecl FUN_0041fac0(int param_1,byte *param_2,byte *param_3)

{
  byte bVar1;
  byte bVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar4 = local_54;
  for (iVar3 = 0x14; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  do {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      goto switchD_0041fb34_caseD_5;
    case 6:
      goto switchD_0041fb34_caseD_6;
    case 7:
      bVar1 = *param_2;
      bVar2 = *param_3;
      param_3 = param_3 + 1;
      param_2 = param_2 + 1;
      if (bVar1 != bVar2) goto LAB_0041fd1c;
switchD_0041fb34_caseD_6:
      bVar1 = *param_2;
      bVar2 = *param_3;
      param_3 = param_3 + 1;
      param_2 = param_2 + 1;
      if (bVar1 != bVar2) goto LAB_0041fd1c;
switchD_0041fb34_caseD_5:
      if ((*param_2 != *param_3) || (param_2[1] != param_3[1])) goto LAB_0041fd1c;
LAB_0041fd17:
      param_3 = param_3 + 2;
      param_2 = param_2 + 2;
      break;
    default:
      if (param_3[1] == 0) {
        local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_3);
      }
      else {
        local_10 = FUN_0041adb0(param_3[1],*param_3);
      }
      local_14 = local_10 - 5;
      switch(local_10) {
      case 5:
      case 6:
      case 7:
      case 0x16:
      case 0x18:
      case 0x19:
      case 0x1a:
      case 0x1b:
      case 0x1d:
        break;
      default:
      }
LAB_0041fd1c:
      local_8 = 0x41fd29;
      FUN_004291a7();
      return;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
    case 0x1d:
      if ((*param_3 == *param_2) && (param_3[1] == param_2[1])) goto LAB_0041fd17;
      goto LAB_0041fd1c;
    }
  } while( true );
}



/* 0041fd80 FUN_0041fd80 */

bool __cdecl FUN_0041fd80(undefined4 param_1,char *param_2,char *param_3,char *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while( true ) {
    if (*param_4 == '\0') {
      return param_2 == param_3;
    }
    if (param_2 == param_3) break;
    if ((param_2[1] != '\0') || (*param_2 != *param_4)) {
      return false;
    }
    param_2 = param_2 + 2;
    param_4 = param_4 + 1;
  }
  return false;
}



/* 0041fe00 FUN_0041fe00 */

void __cdecl FUN_0041fe00(int param_1,byte *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_50 [16];
  int local_10;
  uint local_c;
  byte *local_8;
  
  puVar2 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  do {
    if (param_2[1] == 0) {
      local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_c = FUN_0041adb0(param_2[1],*param_2);
    }
    local_10 = local_c - 5;
    switch(local_c) {
    case 5:
      param_2 = param_2 + 2;
      break;
    case 6:
      param_2 = param_2 + 3;
      break;
    case 7:
      param_2 = param_2 + 4;
      break;
    default:
      local_8 = (byte *)0x41fec3;
      FUN_004291a7();
      return;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
    case 0x1d:
      param_2 = param_2 + 2;
    }
  } while( true );
}



/* 0041ff00 FUN_0041ff00 */

void __cdecl FUN_0041ff00(int param_1,byte *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  uint local_c;
  uint local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while( true ) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8;
    if (((int)local_8 < 9) || ((10 < (int)local_8 && (local_8 != 0x15)))) break;
    param_2 = param_2 + 2;
  }
  local_8 = 0x41ff89;
  FUN_004291a7();
  return;
}



/* 0041ff90 FUN_0041ff90 */

void __cdecl FUN_0041ff90(int param_1,byte *param_2,byte *param_3,int *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_50 [16];
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  while (param_2 != param_3) {
    if (param_2[1] == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)*param_2);
    }
    else {
      local_8 = FUN_0041adb0(param_2[1],*param_2);
    }
    local_c = local_8 - 5;
    switch(local_c) {
    case 0:
      param_2 = param_2 + 2;
      break;
    case 1:
      param_2 = param_2 + 3;
      break;
    case 2:
      param_2 = param_2 + 4;
      break;
    default:
      param_2 = param_2 + 2;
      break;
    case 4:
      *param_4 = *param_4 + 1;
      pbVar2 = param_2 + 2;
      if (pbVar2 != param_3) {
        if (param_2[3] == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)*pbVar2);
        }
        else {
          local_10 = FUN_0041adb0(param_2[3],*pbVar2);
        }
        if (local_10 == 10) {
          pbVar2 = param_2 + 4;
        }
      }
      param_2 = pbVar2;
      param_4[1] = -1;
      break;
    case 5:
      param_4[1] = -1;
      *param_4 = *param_4 + 1;
      param_2 = param_2 + 2;
    }
    param_4[1] = param_4[1] + 1;
  }
  local_8 = 0x4200f6;
  FUN_004291a7();
  return;
}



/* 00420120 FUN_00420120 */

/* WARNING: Removing unreachable block (ram,0x00420300) */
/* WARNING: Removing unreachable block (ram,0x00420338) */
/* WARNING: Removing unreachable block (ram,0x0042036d) */

void __cdecl FUN_00420120(int param_1,char *param_2,char *param_3,undefined4 *param_4)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  uint local_1c;
  uint local_18;
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_004204ab;
  local_8 = (int)param_3 - (int)param_2;
  if ((local_8 & 1) != 0) {
    local_8 = local_8 & 0xfffffffe;
    if (local_8 == 0) goto LAB_004204ab;
    param_3 = param_2 + local_8;
  }
  if (*param_2 == '\0') {
    local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
  }
  else {
    local_c = FUN_0041adb0(*param_2,param_2[1]);
  }
  local_10 = local_c;
  switch(local_c) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_004204ab;
  default:
    pcVar1 = param_2 + 2;
    break;
  case 4:
    pcVar1 = param_2 + 2;
    if (pcVar1 == param_3) goto LAB_004204ab;
    if ((*pcVar1 == '\0') && (param_2[3] == ']')) {
      if (param_2 + 4 == param_3) goto LAB_004204ab;
      if ((param_2[4] == '\0') && (param_2[5] == '>')) {
        *param_4 = param_2 + 6;
        goto LAB_004204ab;
      }
      pcVar1 = param_2 + 2;
    }
    break;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_004204ab;
    pcVar1 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_004204ab;
    pcVar1 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_004204ab;
    pcVar1 = param_2 + 4;
    break;
  case 9:
    pcVar1 = param_2 + 2;
    if (pcVar1 != param_3) {
      if (*pcVar1 == '\0') {
        local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[3]);
      }
      else {
        local_14 = FUN_0041adb0(*pcVar1,param_2[3]);
      }
      if (local_14 == 10) {
        pcVar1 = param_2 + 4;
      }
      param_2 = pcVar1;
      *param_4 = param_2;
    }
    goto LAB_004204ab;
  case 10:
    *param_4 = param_2 + 2;
    goto LAB_004204ab;
  }
  while (param_2 = pcVar1, param_2 != param_3) {
    if (*param_2 == '\0') {
      local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
    }
    else {
      local_18 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_1c = local_18;
    switch(local_18) {
    case 0:
    case 1:
    case 4:
    case 8:
    case 9:
    case 10:
      *param_4 = param_2;
      goto LAB_004204ab;
    default:
      pcVar1 = param_2 + 2;
      break;
    case 5:
      if ((int)param_3 - (int)param_2 < 2) {
        *param_4 = param_2;
        goto LAB_004204ab;
      }
      pcVar1 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) {
        *param_4 = param_2;
        goto LAB_004204ab;
      }
      pcVar1 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) {
        *param_4 = param_2;
        goto LAB_004204ab;
      }
      pcVar1 = param_2 + 4;
    }
  }
  *param_4 = param_2;
LAB_004204ab:
  local_8 = 0x4204b8;
  FUN_004291a7();
  return;
}



/* 00420520 FUN_00420520 */

/* WARNING: Removing unreachable block (ram,0x00420740) */
/* WARNING: Removing unreachable block (ram,0x00420778) */
/* WARNING: Removing unreachable block (ram,0x004207ad) */

void __cdecl FUN_00420520(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  uint local_1c;
  uint local_18;
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar1 = 0x16; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) goto LAB_00420963;
  local_8 = (int)param_3 - (int)param_2;
  if ((local_8 & 1) != 0) {
    local_8 = local_8 & 0xfffffffe;
    if (local_8 == 0) goto LAB_00420963;
    param_3 = param_2 + local_8;
  }
  if (*param_2 == 0) {
    local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
  }
  else {
    local_c = FUN_0041adb0(*param_2,param_2[1]);
  }
  local_10 = local_c;
  switch(local_c) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_00420963;
  case 2:
    FUN_00421020(param_1,param_2 + 2,param_3,param_4);
    goto LAB_00420963;
  case 3:
    FUN_004209d0(param_1,param_2 + 2,param_3,param_4);
    goto LAB_00420963;
  case 4:
    pbVar2 = param_2 + 2;
    if (pbVar2 == param_3) goto LAB_00420963;
    if ((*pbVar2 == 0) && (param_2[3] == 0x5d)) {
      pbVar2 = param_2 + 4;
      if (pbVar2 == param_3) goto LAB_00420963;
      if ((*pbVar2 == 0) && (param_2[5] == 0x3e)) {
        *param_4 = pbVar2;
        goto LAB_00420963;
      }
      pbVar2 = param_2 + 2;
    }
    break;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_00420963;
    pbVar2 = param_2 + 2;
    break;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_00420963;
    pbVar2 = param_2 + 3;
    break;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_00420963;
    pbVar2 = param_2 + 4;
    break;
  case 9:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if (*pbVar2 == 0) {
        local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
      }
      else {
        local_14 = FUN_0041adb0(*pbVar2,param_2[3]);
      }
      if (local_14 == 10) {
        pbVar2 = param_2 + 4;
      }
      param_2 = pbVar2;
      *param_4 = param_2;
    }
    goto LAB_00420963;
  case 10:
    *param_4 = param_2 + 2;
    goto LAB_00420963;
  default:
    pbVar2 = param_2 + 2;
  }
LAB_004207df:
  param_2 = pbVar2;
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_18 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_1c = local_18;
    switch(local_18) {
    case 4:
      if (param_2 + 2 != param_3) {
        if ((param_2[2] != 0) || (param_2[3] != 0x5d)) {
          pbVar2 = param_2 + 2;
          break;
        }
        if (param_2 + 4 != param_3) {
          if ((param_2[4] == 0) && (param_2[5] == 0x3e)) {
            *param_4 = param_2 + 4;
            goto LAB_00420963;
          }
          pbVar2 = param_2 + 2;
          break;
        }
      }
    case 0:
    case 1:
    case 2:
    case 3:
    case 8:
    case 9:
    case 10:
      *param_4 = param_2;
      goto LAB_00420963;
    case 5:
      if ((int)param_3 - (int)param_2 < 2) {
        *param_4 = param_2;
        goto LAB_00420963;
      }
      pbVar2 = param_2 + 2;
      break;
    case 6:
      if ((int)param_3 - (int)param_2 < 3) {
        *param_4 = param_2;
        goto LAB_00420963;
      }
      pbVar2 = param_2 + 3;
      break;
    case 7:
      if ((int)param_3 - (int)param_2 < 4) {
        *param_4 = param_2;
        goto LAB_00420963;
      }
      pbVar2 = param_2 + 4;
      break;
    default:
      goto switchD_00420836_default;
    }
    goto LAB_004207df;
  }
  *param_4 = param_2;
LAB_00420963:
  local_8 = 0x420970;
  FUN_004291a7();
  return;
switchD_00420836_default:
  pbVar2 = param_2 + 2;
  goto LAB_004207df;
}



/* 004209d0 FUN_004209d0 */

/* WARNING: Removing unreachable block (ram,0x00420cb8) */
/* WARNING: Removing unreachable block (ram,0x00420b54) */
/* WARNING: Removing unreachable block (ram,0x00420ae1) */
/* WARNING: Removing unreachable block (ram,0x00420b1c) */
/* WARNING: Removing unreachable block (ram,0x00420cea) */
/* WARNING: Removing unreachable block (ram,0x00420c83) */

void __cdecl FUN_004209d0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x13:
      FUN_00420da0(param_1,(char *)(param_2 + 2),(char *)param_3,param_4);
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (*param_2 == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar1[3]);
        }
        else {
          local_10 = FUN_0041adb0(*param_2,pbVar1[3]);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00420d1b;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00420d1b;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00420d1b;
        default:
          *param_4 = param_2;
          goto LAB_00420d1b;
        case 0x12:
          *param_4 = pbVar1 + 4;
          goto LAB_00420d1b;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_00420d13;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)pbVar1[3] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) *
                        4) & 1 << (pbVar1[3] & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_00420d1b;
          }
LAB_00420d13:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_00420d1b:
  local_8 = 0x420d28;
  FUN_004291a7();
  return;
}



/* 00420da0 FUN_00420da0 */

void __cdecl FUN_00420da0(int param_1,char *param_2,char *param_3,undefined4 *param_4)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if ((*param_2 == '\0') && (param_2[1] == 'x')) {
      FUN_00420ef0(param_1,param_2 + 2,param_3,param_4);
    }
    else {
      if (*param_2 == '\0') {
        local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
      }
      else {
        local_8 = FUN_0041adb0(*param_2,param_2[1]);
      }
      local_c = local_8;
      pcVar1 = param_2;
      if (local_8 == 0x19) {
        do {
          param_2 = pcVar1 + 2;
          if (param_2 == param_3) goto LAB_00420ede;
          if (*param_2 == '\0') {
            local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)pcVar1[3]);
          }
          else {
            local_10 = FUN_0041adb0(*param_2,pcVar1[3]);
          }
          local_14 = local_10;
          if (local_10 == 0x12) {
            *param_4 = pcVar1 + 4;
            goto LAB_00420ede;
          }
          pcVar1 = param_2;
        } while (local_10 == 0x19);
        *param_4 = param_2;
      }
      else {
        *param_4 = param_2;
      }
    }
  }
LAB_00420ede:
  local_8 = 0x420eeb;
  FUN_004291a7();
  return;
}



/* 00420ef0 FUN_00420ef0 */

void __cdecl FUN_00420ef0(int param_1,char *param_2,char *param_3,undefined4 *param_4)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == '\0') {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8;
    if (((int)local_8 < 0x18) || (pcVar1 = param_2, 0x19 < (int)local_8)) {
      *param_4 = param_2;
    }
    else {
      do {
        param_2 = pcVar1 + 2;
        if (param_2 == param_3) goto LAB_00421007;
        if (*param_2 == '\0') {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)pcVar1[3]);
        }
        else {
          local_10 = FUN_0041adb0(*param_2,pcVar1[3]);
        }
        local_14 = local_10;
        if (local_10 == 0x12) {
          *param_4 = pcVar1 + 4;
          goto LAB_00421007;
        }
      } while ((0x17 < (int)local_10) && (pcVar1 = param_2, (int)local_10 < 0x1a));
      *param_4 = param_2;
    }
  }
LAB_00421007:
  local_8 = 0x421014;
  FUN_004291a7();
  return;
}



/* 00421020 FUN_00421020 */

/* WARNING: Removing unreachable block (ram,0x0042156a) */
/* WARNING: Removing unreachable block (ram,0x004213ad) */
/* WARNING: Removing unreachable block (ram,0x00421423) */
/* WARNING: Removing unreachable block (ram,0x0042116c) */
/* WARNING: Removing unreachable block (ram,0x00421131) */
/* WARNING: Removing unreachable block (ram,0x004211a7) */
/* WARNING: Removing unreachable block (ram,0x004213e8) */
/* WARNING: Removing unreachable block (ram,0x0042152f) */
/* WARNING: Removing unreachable block (ram,0x004215a2) */

void __cdecl FUN_00421020(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_64 [16];
  int local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  uint local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_64;
  for (iVar1 = 0x18; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0xf:
      FUN_00421970(param_1,param_2 + 2,param_3,param_4);
      break;
    case 0x10:
      pbVar2 = param_2 + 2;
      if (pbVar2 != param_3) {
        if (*pbVar2 == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
        }
        else {
          local_10 = FUN_0041adb0(*pbVar2,param_2[3]);
        }
        local_14 = local_10;
        if (local_10 == 0x14) {
          FUN_00422090(param_1,(char *)(param_2 + 4),(int)param_3,param_4);
        }
        else if (local_10 == 0x1b) {
          FUN_00421730(param_1,(char *)(param_2 + 4),(char *)param_3,param_4);
        }
        else {
          *param_4 = pbVar2;
        }
      }
      break;
    case 0x11:
      FUN_00422120(param_1,param_2 + 2,param_3,param_4);
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar2 = param_2;
      while (param_2 = pbVar2 + 2, param_2 != param_3) {
        if (*param_2 == 0) {
          local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar2[3]);
        }
        else {
          local_18 = FUN_0041adb0(*param_2,pbVar2[3]);
        }
        local_1c = local_18 - 5;
        switch(local_18) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0042165d;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0042165d;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_0042165d;
        default:
          *param_4 = param_2;
          goto LAB_0042165d;
        case 9:
        case 10:
        case 0x15:
          param_2 = pbVar2 + 4;
          goto LAB_0042143a;
        case 0xb:
          goto switchD_00421320_caseD_b;
        case 0x11:
          goto switchD_00421320_caseD_11;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_00421655;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)pbVar2[3] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) *
                        4) & 1 << (pbVar2[3] & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_0042165d;
          }
LAB_00421655:
          pbVar2 = param_2;
        }
      }
    }
  }
LAB_0042165d:
  local_8 = 0x42166a;
  FUN_004291a7();
  return;
LAB_0042143a:
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_20 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_24 = local_20 - 5;
    switch(local_20) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 9:
    case 10:
    case 0x15:
      goto switchD_004214a2_caseD_9;
    case 0xb:
switchD_00421320_caseD_b:
      *param_4 = param_2 + 2;
      break;
    case 0x11:
switchD_00421320_caseD_11:
      pbVar2 = param_2 + 2;
      if (pbVar2 != param_3) {
        if ((*pbVar2 == 0) && (param_2[3] == 0x3e)) {
          *param_4 = param_2 + 4;
        }
        else {
          *param_4 = pbVar2;
        }
      }
      break;
    case 0x16:
    case 0x18:
      goto switchD_004214a2_caseD_16;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
switchD_004214a2_caseD_16:
      FUN_004225a0(param_1,param_2 + 2,param_3,param_4);
      break;
    }
  }
  goto LAB_0042165d;
switchD_004214a2_caseD_9:
  param_2 = param_2 + 2;
  goto LAB_0042143a;
}



/* 00421730 FUN_00421730 */

/* WARNING: Removing unreachable block (ram,0x0042183b) */
/* WARNING: Removing unreachable block (ram,0x00421803) */
/* WARNING: Removing unreachable block (ram,0x00421873) */

void __cdecl FUN_00421730(int param_1,char *param_2,char *param_3,undefined4 *param_4)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [16];
  uint local_c;
  uint local_8;
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if ((*param_2 == '\0') && (param_2[1] == '-')) {
      pcVar1 = param_2 + 2;
LAB_00421782:
      param_2 = pcVar1;
      if (param_2 == param_3) goto LAB_00421928;
      if (*param_2 == '\0') {
        local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
      }
      else {
        local_8 = FUN_0041adb0(*param_2,param_2[1]);
      }
      local_c = local_8;
      switch(local_8) {
      case 0:
      case 1:
      case 8:
        *param_4 = param_2;
        goto LAB_00421928;
      default:
        pcVar1 = param_2 + 2;
        goto LAB_00421782;
      case 5:
        if ((int)param_3 - (int)param_2 < 2) goto LAB_00421928;
        pcVar1 = param_2 + 2;
        goto LAB_00421782;
      case 6:
        if ((int)param_3 - (int)param_2 < 3) goto LAB_00421928;
        pcVar1 = param_2 + 3;
        goto LAB_00421782;
      case 7:
        if ((int)param_3 - (int)param_2 < 4) goto LAB_00421928;
        pcVar1 = param_2 + 4;
        goto LAB_00421782;
      case 0x1b:
        goto switchD_004217e1_caseD_1b;
      }
    }
    *param_4 = param_2;
  }
LAB_00421928:
  local_8 = 0x421935;
  FUN_004291a7();
  return;
switchD_004217e1_caseD_1b:
  pcVar1 = param_2 + 2;
  if (pcVar1 == param_3) goto LAB_00421928;
  if ((*pcVar1 != '\0') || (param_2[3] != '-')) goto LAB_00421782;
  pcVar1 = param_2 + 4;
  if (pcVar1 != param_3) {
    if ((*pcVar1 == '\0') && (param_2[5] == '>')) {
      *param_4 = param_2 + 6;
    }
    else {
      *param_4 = pcVar1;
    }
  }
  goto LAB_00421928;
}



/* 00421970 FUN_00421970 */

/* WARNING: Removing unreachable block (ram,0x00421d79) */
/* WARNING: Removing unreachable block (ram,0x00421c08) */
/* WARNING: Removing unreachable block (ram,0x00421c7e) */
/* WARNING: Removing unreachable block (ram,0x00421ac1) */
/* WARNING: Removing unreachable block (ram,0x00421a89) */
/* WARNING: Removing unreachable block (ram,0x00421af9) */
/* WARNING: Removing unreachable block (ram,0x00421c43) */
/* WARNING: Removing unreachable block (ram,0x00421d41) */
/* WARNING: Removing unreachable block (ram,0x00421db1) */

void __cdecl FUN_00421970(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_64 [16];
  uint local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  byte *local_c;
  undefined4 local_8;
  
  puVar3 = local_64;
  for (iVar1 = 0x18; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_c = param_2;
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_10 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_14 = local_10 - 5;
    switch(local_10) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x16:
    case 0x18:
switchD_004219fa_caseD_16:
      pbVar2 = param_2;
LAB_00421b13:
      param_2 = pbVar2 + 2;
      if (param_2 != param_3) {
        if (*param_2 == 0) {
          local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar2[3]);
        }
        else {
          local_18 = FUN_0041adb0(*param_2,pbVar2[3]);
        }
        local_1c = local_18 - 5;
        switch(local_18) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00421ea9;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00421ea9;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00421ea9;
        case 9:
        case 10:
        case 0x15:
          iVar1 = FUN_00421f50(param_1,(char *)local_c,(int)param_2,&local_8);
          if (iVar1 != 0) {
            pbVar2 = pbVar2 + 4;
            goto LAB_00421cc0;
          }
          *param_4 = param_2;
          goto LAB_00421ea9;
        case 0xf:
          iVar1 = FUN_00421f50(param_1,(char *)local_c,(int)param_2,&local_8);
          if (iVar1 == 0) {
            *param_4 = param_2;
            goto LAB_00421ea9;
          }
          param_2 = pbVar2 + 4;
          if (param_2 == param_3) goto LAB_00421ea9;
          if ((*param_2 == 0) && (pbVar2[5] == 0x3e)) {
            *param_4 = pbVar2 + 6;
            goto LAB_00421ea9;
          }
          break;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_00421ea1;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)pbVar2[3] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) *
                        4) & 1 << (pbVar2[3] & 0x1f)) != 0) goto LAB_00421ea1;
          *param_4 = param_2;
          goto LAB_00421ea9;
        }
        *param_4 = param_2;
      }
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) != 0) goto switchD_004219fa_caseD_16;
      *param_4 = param_2;
    }
  }
  goto LAB_00421ea9;
LAB_00421ea1:
  pbVar2 = param_2;
  goto LAB_00421b13;
LAB_00421cc0:
  param_2 = pbVar2;
  if (param_2 == param_3) goto LAB_00421ea9;
  if (*param_2 == 0) {
    local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
  }
  else {
    local_20 = FUN_0041adb0(*param_2,param_2[1]);
  }
  local_24 = local_20;
  switch(local_20) {
  case 0:
  case 1:
  case 8:
    *param_4 = param_2;
    goto LAB_00421ea9;
  default:
    pbVar2 = param_2 + 2;
    goto LAB_00421cc0;
  case 5:
    if ((int)param_3 - (int)param_2 < 2) goto LAB_00421ea9;
    pbVar2 = param_2 + 2;
    goto LAB_00421cc0;
  case 6:
    if ((int)param_3 - (int)param_2 < 3) goto LAB_00421ea9;
    pbVar2 = param_2 + 3;
    goto LAB_00421cc0;
  case 7:
    if ((int)param_3 - (int)param_2 < 4) goto LAB_00421ea9;
    pbVar2 = param_2 + 4;
    goto LAB_00421cc0;
  case 0xf:
    break;
  }
  pbVar2 = param_2 + 2;
  if (pbVar2 == param_3) goto LAB_00421ea9;
  if ((*pbVar2 != 0) || (param_2[3] != 0x3e)) goto LAB_00421cc0;
  *param_4 = param_2 + 4;
LAB_00421ea9:
  local_8 = 0x421eb6;
  FUN_004291a7();
  return;
}



/* 00421f50 FUN_00421f50 */

undefined4 __cdecl FUN_00421f50(undefined4 param_1,char *param_2,int param_3,undefined4 *param_4)

{
  char cVar1;
  bool bVar2;
  undefined4 uVar3;
  int iVar4;
  undefined4 *puVar5;
  undefined4 local_60 [23];
  
  puVar5 = local_60;
  for (iVar4 = 0x17; iVar4 != 0; iVar4 = iVar4 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
  bVar2 = false;
  *param_4 = 0xb;
  if (param_3 - (int)param_2 == 6) {
    if (*param_2 == '\0') {
      cVar1 = param_2[1];
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'X') {
      bVar2 = true;
    }
    else if (cVar1 != 'x') {
      return 1;
    }
    if (param_2[2] == '\0') {
      cVar1 = param_2[3];
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'M') {
      bVar2 = true;
    }
    else if (cVar1 != 'm') {
      return 1;
    }
    if (param_2[4] == '\0') {
      cVar1 = param_2[5];
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'L') {
      bVar2 = true;
    }
    else if (cVar1 != 'l') {
      return 1;
    }
    if (bVar2) {
      uVar3 = 0;
    }
    else {
      *param_4 = 0xc;
      uVar3 = 1;
    }
  }
  else {
    uVar3 = 1;
  }
  return uVar3;
}



/* 00422090 FUN_00422090 */

undefined4 __cdecl FUN_00422090(undefined4 param_1,char *param_2,int param_3,undefined4 *param_4)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [16];
  int local_8;
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_3 - (int)param_2 < 0xc) {
    uVar1 = 0xffffffff;
  }
  else {
    for (local_8 = 0; local_8 < 6; local_8 = local_8 + 1) {
      if ((*param_2 != '\0') || (param_2[1] != (&DAT_0042db44)[local_8])) {
        *param_4 = param_2;
        return 0;
      }
      param_2 = param_2 + 2;
    }
    *param_4 = param_2;
    uVar1 = 8;
  }
  return uVar1;
}



/* 00422120 FUN_00422120 */

/* WARNING: Removing unreachable block (ram,0x004223ed) */
/* WARNING: Removing unreachable block (ram,0x004222a1) */
/* WARNING: Removing unreachable block (ram,0x00422231) */
/* WARNING: Removing unreachable block (ram,0x00422269) */
/* WARNING: Removing unreachable block (ram,0x00422428) */
/* WARNING: Removing unreachable block (ram,0x004223b2) */

void __cdecl FUN_00422120(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (*param_2 == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar1[3]);
        }
        else {
          local_10 = FUN_0041adb0(*param_2,pbVar1[3]);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00422505;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00422505;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00422505;
        default:
          *param_4 = param_2;
          goto LAB_00422505;
        case 9:
        case 10:
        case 0x15:
          param_2 = pbVar1 + 4;
          goto LAB_0042244a;
        case 0xb:
          *param_4 = pbVar1 + 4;
          goto LAB_00422505;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_004224fd;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)pbVar1[3] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) *
                        4) & 1 << (pbVar1[3] & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_00422505;
          }
LAB_004224fd:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_00422505:
  local_8 = 0x422512;
  FUN_004291a7();
  return;
LAB_0042244a:
  if (param_2 == param_3) goto LAB_00422505;
  if (*param_2 == 0) {
    local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
  }
  else {
    local_18 = FUN_0041adb0(*param_2,param_2[1]);
  }
  local_1c = local_18 - 9;
  switch(local_18) {
  case 9:
  case 10:
  case 0x15:
    param_2 = param_2 + 2;
    break;
  case 0xb:
    *param_4 = param_2 + 2;
    goto LAB_00422505;
  default:
    *param_4 = param_2;
    goto LAB_00422505;
  }
  goto LAB_0042244a;
}



/* 004225a0 FUN_004225a0 */

/* WARNING: Removing unreachable block (ram,0x00422929) */
/* WARNING: Removing unreachable block (ram,0x004226b0) */
/* WARNING: Removing unreachable block (ram,0x00422726) */
/* WARNING: Removing unreachable block (ram,0x00422bb3) */
/* WARNING: Removing unreachable block (ram,0x00422bee) */
/* WARNING: Removing unreachable block (ram,0x00422b78) */
/* WARNING: Removing unreachable block (ram,0x004226eb) */
/* WARNING: Removing unreachable block (ram,0x004228f1) */
/* WARNING: Removing unreachable block (ram,0x00422961) */

void __cdecl FUN_004225a0(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  byte *pbVar3;
  undefined4 *puVar4;
  undefined4 local_84 [16];
  int local_44;
  uint local_40;
  int local_3c;
  uint local_38;
  uint local_34;
  uint local_30;
  uint local_2c;
  uint local_28;
  uint local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar4 = local_84;
  for (iVar2 = 0x20; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
LAB_004225bb:
  pbVar3 = param_2;
  if (param_2 == param_3) {
LAB_00422c7c:
    param_2 = pbVar3;
    local_8 = 0x422c8c;
    FUN_004291a7();
    return;
  }
  if (*param_2 == 0) {
    local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
  }
  else {
    local_18 = FUN_0041adb0(*param_2,param_2[1]);
  }
  local_1c = local_18 - 5;
  pbVar3 = param_2;
  switch(local_18) {
  case 5:
    if (1 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    goto LAB_00422c7c;
  case 6:
    if (2 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    goto LAB_00422c7c;
  case 7:
    if (3 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    goto LAB_00422c7c;
  default:
    *param_4 = param_2;
    goto LAB_00422c7c;
  case 9:
  case 10:
  case 0x15:
    while( true ) {
      pbVar3 = param_2 + 2;
      if (pbVar3 == param_3) goto LAB_00422c7c;
      if (*pbVar3 == 0) {
        local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
        param_2 = pbVar3;
      }
      else {
        pbVar1 = param_2 + 3;
        param_2 = pbVar3;
        local_20 = FUN_0041adb0(*pbVar3,*pbVar1);
      }
      local_8 = local_20;
      if (local_20 == 0xe) break;
      local_24 = local_20;
      if (((int)local_20 < 9) || ((10 < (int)local_20 && (local_20 != 0x15)))) {
        *param_4 = param_2;
        pbVar3 = param_2;
        goto LAB_00422c7c;
      }
    }
  case 0xe:
    while( true ) {
      pbVar3 = param_2 + 2;
      if (pbVar3 == param_3) goto LAB_00422c7c;
      if (*pbVar3 == 0) {
        local_28 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
        param_2 = pbVar3;
      }
      else {
        pbVar1 = param_2 + 3;
        param_2 = pbVar3;
        local_28 = FUN_0041adb0(*pbVar3,*pbVar1);
      }
      local_c = local_28;
      if ((local_28 == 0xc) || (local_28 == 0xd)) break;
      local_2c = local_28;
      if (((int)local_28 < 9) || ((10 < (int)local_28 && (local_28 != 0x15)))) {
        *param_4 = param_2;
        pbVar3 = param_2;
        goto LAB_00422c7c;
      }
    }
    param_2 = param_2 + 2;
    while( true ) {
      pbVar3 = param_2;
      if (param_2 == param_3) goto LAB_00422c7c;
      if (*param_2 == 0) {
        local_30 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
      }
      else {
        local_30 = FUN_0041adb0(*param_2,param_2[1]);
      }
      local_10 = local_30;
      if (local_30 == local_c) break;
      local_34 = local_30;
      pbVar3 = param_2;
      switch(local_30) {
      case 0:
      case 1:
      case 8:
        *param_4 = param_2;
        goto LAB_00422c7c;
      case 2:
        *param_4 = param_2;
        goto LAB_00422c7c;
      case 3:
        local_14 = FUN_004209d0(param_1,param_2 + 2,param_3,&param_2);
        if (local_14 < 1) {
          pbVar3 = param_2;
          if (local_14 == 0) {
            *param_4 = param_2;
          }
          goto LAB_00422c7c;
        }
        break;
      default:
        param_2 = param_2 + 2;
        break;
      case 5:
        if ((int)param_3 - (int)param_2 < 2) goto LAB_00422c7c;
        param_2 = param_2 + 2;
        break;
      case 6:
        if ((int)param_3 - (int)param_2 < 3) goto LAB_00422c7c;
        param_2 = param_2 + 3;
        break;
      case 7:
        if ((int)param_3 - (int)param_2 < 4) goto LAB_00422c7c;
        param_2 = param_2 + 4;
      }
    }
    pbVar3 = param_2 + 2;
    if (pbVar3 != param_3) {
      if (*pbVar3 == 0) {
        local_38 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
        param_2 = pbVar3;
      }
      else {
        pbVar1 = param_2 + 3;
        param_2 = pbVar3;
        local_38 = FUN_0041adb0(*pbVar3,*pbVar1);
      }
      local_3c = local_38 - 9;
      switch(local_38) {
      case 9:
      case 10:
      case 0x15:
        do {
          pbVar3 = param_2 + 2;
          if (pbVar3 == param_3) break;
          if (*pbVar3 == 0) {
            local_40 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
            param_2 = pbVar3;
          }
          else {
            pbVar1 = param_2 + 3;
            param_2 = pbVar3;
            local_40 = FUN_0041adb0(*pbVar3,*pbVar1);
          }
          local_44 = local_40 - 5;
          pbVar3 = param_2;
          switch(local_40) {
          case 5:
            if (1 < (int)param_3 - (int)param_2) {
              *param_4 = param_2;
            }
            goto LAB_00422c7c;
          case 6:
            if (2 < (int)param_3 - (int)param_2) {
              *param_4 = param_2;
            }
            goto LAB_00422c7c;
          case 7:
            if (3 < (int)param_3 - (int)param_2) {
              *param_4 = param_2;
            }
            goto LAB_00422c7c;
          default:
            *param_4 = param_2;
            goto LAB_00422c7c;
          case 9:
          case 10:
          case 0x15:
            break;
          case 0xb:
            goto switchD_00422aeb_caseD_b;
          case 0x11:
            goto switchD_00422aeb_caseD_11;
          case 0x16:
          case 0x18:
            goto LAB_00422c66;
          case 0x1d:
            if ((*(uint *)(&DAT_0042cb98 +
                          (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8)
                          * 4) & 1 << (param_2[1] & 0x1f)) == 0) {
              *param_4 = param_2;
              goto LAB_00422c7c;
            }
            goto LAB_00422c66;
          }
        } while( true );
      case 0xb:
switchD_00422aeb_caseD_b:
        *param_4 = param_2 + 2;
        pbVar3 = param_2;
        break;
      default:
        *param_4 = param_2;
        pbVar3 = param_2;
        break;
      case 0x11:
switchD_00422aeb_caseD_11:
        pbVar3 = param_2 + 2;
        if (pbVar3 != param_3) {
          if ((*pbVar3 == 0) && (param_2[3] == 0x3e)) {
            *param_4 = param_2 + 4;
          }
          else {
            *param_4 = pbVar3;
          }
        }
      }
    }
    goto LAB_00422c7c;
  case 0x16:
  case 0x18:
  case 0x19:
  case 0x1a:
  case 0x1b:
    goto switchD_00422623_caseD_16;
  case 0x1d:
    if ((*(uint *)(&DAT_0042cb98 +
                  (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) * 4) &
        1 << (param_2[1] & 0x1f)) == 0) {
      *param_4 = param_2;
      goto LAB_00422c7c;
    }
switchD_00422623_caseD_16:
    goto LAB_00422c74;
  }
LAB_00422c66:
LAB_00422c74:
  param_2 = param_2 + 2;
  goto LAB_004225bb;
}



/* 00422d50 FUN_00422d50 */

/* WARNING: Removing unreachable block (ram,0x0042350b) */
/* WARNING: Removing unreachable block (ram,0x004232c7) */
/* WARNING: Removing unreachable block (ram,0x0042322e) */
/* WARNING: Removing unreachable block (ram,0x00423288) */
/* WARNING: Removing unreachable block (ram,0x004232e2) */
/* WARNING: Removing unreachable block (ram,0x0042326d) */
/* WARNING: Removing unreachable block (ram,0x00423213) */
/* WARNING: Removing unreachable block (ram,0x00423546) */
/* WARNING: Removing unreachable block (ram,0x004234d0) */

void __cdecl FUN_00422d50(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  byte *pbVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_74 [16];
  int local_34;
  uint local_30;
  int local_2c;
  uint local_28;
  uint local_24;
  uint local_20;
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  uint local_c;
  int local_8;
  
  puVar4 = local_74;
  for (iVar3 = 0x1c; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  if (param_2 == param_3) goto LAB_004235e6;
  local_c = (int)param_3 - (int)param_2;
  if ((local_c & 1) != 0) {
    local_c = local_c & 0xfffffffe;
    if (local_c == 0) goto LAB_004235e6;
    param_3 = param_2 + local_c;
  }
  if (*param_2 == 0) {
    local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
  }
  else {
    local_10 = FUN_0041adb0(*param_2,param_2[1]);
  }
  local_14 = local_10 - 2;
  switch(local_10) {
  case 2:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if (*pbVar2 == 0) {
        local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
      }
      else {
        local_18 = FUN_0041adb0(*pbVar2,param_2[3]);
      }
      local_1c = local_18 - 5;
      switch(local_18) {
      case 5:
      case 6:
      case 7:
      case 0x16:
      case 0x18:
      case 0x1d:
        *param_4 = param_2;
        break;
      default:
        *param_4 = pbVar2;
        break;
      case 0xf:
        FUN_00421970(param_1,param_2 + 4,param_3,param_4);
        break;
      case 0x10:
        FUN_00423710(param_1,(char *)(param_2 + 4),(char *)param_3,param_4);
      }
    }
    break;
  default:
switchD_00422e12_caseD_3:
    *param_4 = param_2;
    break;
  case 4:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if ((*pbVar2 == 0) && (param_2[3] == 0x5d)) {
        if (param_2 + 4 == param_3) break;
        if ((param_2[4] == 0) && (param_2[5] == 0x3e)) {
          *param_4 = param_2 + 6;
          break;
        }
      }
      *param_4 = pbVar2;
    }
    break;
  case 5:
    if (1 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    break;
  case 6:
    if (2 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    break;
  case 7:
    if (3 < (int)param_3 - (int)param_2) {
      *param_4 = param_2;
    }
    break;
  case 9:
    if (param_2 + 2 == param_3) {
      *param_4 = param_3;
      break;
    }
  case 10:
  case 0x15:
    do {
      while( true ) {
        pbVar2 = param_2 + 2;
        if (pbVar2 == param_3) {
          *param_4 = pbVar2;
          goto LAB_004235e6;
        }
        if (*pbVar2 == 0) {
          local_20 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
        }
        else {
          local_20 = FUN_0041adb0(*pbVar2,param_2[3]);
        }
        local_24 = local_20;
        if (local_20 == 9) break;
        param_2 = pbVar2;
        if ((local_20 != 10) && (local_20 != 0x15)) goto LAB_00422fca;
      }
      pbVar1 = param_2 + 4;
      param_2 = pbVar2;
    } while (pbVar1 != param_3);
LAB_00422fca:
    *param_4 = pbVar2;
    break;
  case 0xb:
    *param_4 = param_2 + 2;
    break;
  case 0xc:
    FUN_004240e0(0xc,param_1,(char *)(param_2 + 2),(char *)param_3,param_4);
    break;
  case 0xd:
    FUN_004240e0(0xd,param_1,(char *)(param_2 + 2),(char *)param_3,param_4);
    break;
  case 0x13:
    FUN_00423d30(param_1,param_2 + 2,param_3,param_4);
    break;
  case 0x14:
    *param_4 = param_2 + 2;
    break;
  case 0x16:
  case 0x18:
    local_8 = 0x12;
    param_2 = param_2 + 2;
    goto LAB_004233db;
  case 0x19:
  case 0x1a:
  case 0x1b:
    local_8 = 0x13;
    param_2 = param_2 + 2;
    goto LAB_004233db;
  case 0x1d:
    if ((*(uint *)(&DAT_0042cb98 +
                  (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4) &
        1 << (param_2[1] & 0x1f)) == 0) {
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) == 0) goto switchD_00422e12_caseD_3;
      param_2 = param_2 + 2;
      local_8 = 0x13;
    }
    else {
      param_2 = param_2 + 2;
      local_8 = 0x12;
    }
LAB_004233db:
    while (param_2 != param_3) {
      if (*param_2 == 0) {
        local_30 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
      }
      else {
        local_30 = FUN_0041adb0(*param_2,param_2[1]);
      }
      local_34 = local_30 - 5;
      switch(local_30) {
      case 5:
        if (1 < (int)param_3 - (int)param_2) {
          *param_4 = param_2;
        }
        goto LAB_004235e6;
      case 6:
        if (2 < (int)param_3 - (int)param_2) {
          *param_4 = param_2;
        }
        goto LAB_004235e6;
      case 7:
        if (3 < (int)param_3 - (int)param_2) {
          *param_4 = param_2;
        }
        goto LAB_004235e6;
      default:
        *param_4 = param_2;
        goto LAB_004235e6;
      case 9:
      case 10:
      case 0xb:
      case 0x14:
      case 0x15:
      case 0x1e:
      case 0x20:
      case 0x23:
      case 0x24:
        *param_4 = param_2;
        goto LAB_004235e6;
      case 0xf:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 2;
        }
        goto LAB_004235e6;
      case 0x1d:
        if ((*(uint *)(&DAT_0042cb98 +
                      (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) * 4
                      ) & 1 << (param_2[1] & 0x1f)) == 0) {
          *param_4 = param_2;
          goto LAB_004235e6;
        }
      case 0x16:
      case 0x18:
      case 0x19:
      case 0x1a:
      case 0x1b:
        param_2 = param_2 + 2;
        break;
      case 0x21:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 2;
        }
        goto LAB_004235e6;
      case 0x22:
        if (local_8 == 0x13) {
          *param_4 = param_2;
        }
        else {
          *param_4 = param_2 + 2;
        }
        goto LAB_004235e6;
      }
    }
    break;
  case 0x1e:
    FUN_00423970(param_1,param_2 + 2,param_3,param_4);
    break;
  case 0x1f:
    *param_4 = param_2 + 2;
    break;
  case 0x20:
    pbVar2 = param_2 + 2;
    if (pbVar2 != param_3) {
      if (*pbVar2 == 0) {
        local_28 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
      }
      else {
        local_28 = FUN_0041adb0(*pbVar2,param_2[3]);
      }
      local_2c = local_28 - 9;
      switch(local_28) {
      case 9:
      case 10:
      case 0xb:
      case 0x15:
      case 0x20:
      case 0x23:
      case 0x24:
        *param_4 = pbVar2;
        break;
      default:
        *param_4 = pbVar2;
        break;
      case 0xf:
        *param_4 = param_2 + 4;
        break;
      case 0x21:
        *param_4 = param_2 + 4;
        break;
      case 0x22:
        *param_4 = param_2 + 4;
      }
    }
    break;
  case 0x23:
    *param_4 = param_2 + 2;
    break;
  case 0x24:
    *param_4 = param_2 + 2;
  }
LAB_004235e6:
  local_8 = 0x4235f3;
  FUN_004291a7();
  return;
}



/* 00423710 FUN_00423710 */

void __cdecl FUN_00423710(int param_1,char *param_2,char *param_3,undefined4 *param_4)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  int local_1c;
  uint local_18;
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == '\0') {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 0x14;
    switch(local_c) {
    case 0:
      *param_4 = param_2 + 2;
      break;
    default:
      *param_4 = param_2;
      break;
    case 2:
    case 4:
      pcVar1 = param_2;
LAB_004237de:
      param_2 = pcVar1 + 2;
      if (param_2 != param_3) {
        if (*param_2 == '\0') {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)pcVar1[3]);
        }
        else {
          local_10 = FUN_0041adb0(*param_2,pcVar1[3]);
        }
        local_14 = local_10 - 9;
        switch(local_10) {
        case 9:
        case 10:
        case 0x15:
          goto switchD_00423846_caseD_9;
        default:
          *param_4 = param_2;
          break;
        case 0x16:
        case 0x18:
          goto LAB_004238f3;
        case 0x1e:
          if (pcVar1 + 4 == param_3) break;
          if (pcVar1[4] == '\0') {
            local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)pcVar1[5]);
          }
          else {
            local_18 = FUN_0041adb0(pcVar1[4],pcVar1[5]);
          }
          local_1c = local_18 - 9;
          switch(local_18) {
          case 9:
          case 10:
          case 0x15:
          case 0x1e:
            *param_4 = param_2;
            goto LAB_004238fb;
          }
switchD_00423846_caseD_9:
          *param_4 = param_2;
        }
      }
      break;
    case 7:
      FUN_00421730(param_1,param_2 + 2,param_3,param_4);
    }
  }
LAB_004238fb:
  local_8 = 0x423908;
  FUN_004291a7();
  return;
LAB_004238f3:
  pcVar1 = param_2;
  goto LAB_004237de;
}



/* 00423970 FUN_00423970 */

/* WARNING: Removing unreachable block (ram,0x00423c4c) */
/* WARNING: Removing unreachable block (ram,0x00423af6) */
/* WARNING: Removing unreachable block (ram,0x00423a83) */
/* WARNING: Removing unreachable block (ram,0x00423abe) */
/* WARNING: Removing unreachable block (ram,0x00423c7e) */
/* WARNING: Removing unreachable block (ram,0x00423c17) */

void __cdecl FUN_00423970(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 9:
    case 10:
    case 0x15:
    case 0x1e:
      *param_4 = param_2;
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (*param_2 == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar1[3]);
        }
        else {
          local_10 = FUN_0041adb0(*param_2,pbVar1[3]);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00423caf;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00423caf;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00423caf;
        default:
          *param_4 = param_2;
          goto LAB_00423caf;
        case 0x12:
          *param_4 = pbVar1 + 4;
          goto LAB_00423caf;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_00423ca7;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)pbVar1[3] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) *
                        4) & 1 << (pbVar1[3] & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_00423caf;
          }
LAB_00423ca7:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_00423caf:
  local_8 = 0x423cbc;
  FUN_004291a7();
  return;
}



/* 00423d30 FUN_00423d30 */

/* WARNING: Removing unreachable block (ram,0x00423ff4) */
/* WARNING: Removing unreachable block (ram,0x00423eb1) */
/* WARNING: Removing unreachable block (ram,0x00423e41) */
/* WARNING: Removing unreachable block (ram,0x00423e79) */
/* WARNING: Removing unreachable block (ram,0x00424026) */
/* WARNING: Removing unreachable block (ram,0x00423fc2) */

void __cdecl FUN_00423d30(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  byte *pbVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    if (*param_2 == 0) {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      if (1 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 6:
      if (2 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    case 7:
      if (3 < (int)param_3 - (int)param_2) {
        *param_4 = param_2;
      }
      break;
    default:
      *param_4 = param_2;
      break;
    case 0x1d:
      if ((*(uint *)(&DAT_0042cb98 +
                    (((int)(uint)param_2[1] >> 5) + (uint)(byte)(&DAT_0042d098)[*param_2] * 8) * 4)
          & 1 << (param_2[1] & 0x1f)) == 0) {
        *param_4 = param_2;
        break;
      }
    case 0x16:
    case 0x18:
      pbVar1 = param_2;
      while (param_2 = pbVar1 + 2, param_2 != param_3) {
        if (*param_2 == 0) {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)pbVar1[3]);
        }
        else {
          local_10 = FUN_0041adb0(*param_2,pbVar1[3]);
        }
        local_14 = local_10 - 5;
        switch(local_10) {
        case 5:
          if (1 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00424056;
        case 6:
          if (2 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00424056;
        case 7:
          if (3 < (int)param_3 - (int)param_2) {
            *param_4 = param_2;
          }
          goto LAB_00424056;
        default:
          *param_4 = param_2;
          goto LAB_00424056;
        case 9:
        case 10:
        case 0xb:
        case 0x15:
        case 0x1e:
        case 0x20:
        case 0x24:
          *param_4 = param_2;
          goto LAB_00424056;
        case 0x16:
        case 0x18:
        case 0x19:
        case 0x1a:
        case 0x1b:
          goto LAB_0042404c;
        case 0x1d:
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(uint)pbVar1[3] >> 5) + (uint)(byte)(&DAT_0042d198)[*param_2] * 8) *
                        4) & 1 << (pbVar1[3] & 0x1f)) == 0) {
            *param_4 = param_2;
            goto LAB_00424056;
          }
LAB_0042404c:
          pbVar1 = param_2;
        }
      }
    }
  }
LAB_00424056:
  local_8 = 0x424063;
  FUN_004291a7();
  return;
}



/* 004240e0 FUN_004240e0 */

/* WARNING: Removing unreachable block (ram,0x004241b7) */
/* WARNING: Removing unreachable block (ram,0x0042417f) */
/* WARNING: Removing unreachable block (ram,0x004241ef) */

void __cdecl FUN_004240e0(uint param_1,int param_2,char *param_3,char *param_4,undefined4 *param_5)

{
  int iVar1;
  char *pcVar2;
  undefined4 *puVar3;
  undefined4 local_58 [16];
  int local_18;
  uint local_14;
  uint local_10;
  uint local_c;
  uint local_8;
  
  puVar3 = local_58;
  for (iVar1 = 0x15; pcVar2 = param_3, iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  do {
    param_3 = pcVar2;
    if (param_3 == param_4) goto LAB_004242c6;
    if (*param_3 == '\0') {
      local_c = (uint)*(byte *)(param_2 + 0x48 + (uint)(byte)param_3[1]);
    }
    else {
      local_c = FUN_0041adb0(*param_3,param_3[1]);
    }
    local_8 = local_c;
    local_10 = local_c;
    switch(local_c) {
    case 0:
    case 1:
    case 8:
      *param_5 = param_3;
      goto LAB_004242c6;
    default:
      pcVar2 = param_3 + 2;
      break;
    case 5:
      if ((int)param_4 - (int)param_3 < 2) goto LAB_004242c6;
      pcVar2 = param_3 + 2;
      break;
    case 6:
      if ((int)param_4 - (int)param_3 < 3) goto LAB_004242c6;
      pcVar2 = param_3 + 3;
      break;
    case 7:
      if ((int)param_4 - (int)param_3 < 4) goto LAB_004242c6;
      pcVar2 = param_3 + 4;
      break;
    case 0xc:
    case 0xd:
      pcVar2 = param_3 + 2;
      if (local_c == param_1) {
        if (pcVar2 != param_4) {
          *param_5 = pcVar2;
          if (*pcVar2 == '\0') {
            local_14 = (uint)*(byte *)(param_2 + 0x48 + (uint)(byte)param_3[3]);
          }
          else {
            local_14 = FUN_0041adb0(*pcVar2,param_3[3]);
          }
          local_18 = local_14 - 9;
          switch(local_14) {
          case 9:
          case 10:
          case 0xb:
          case 0x14:
          case 0x15:
          case 0x1e:
            break;
          default:
          }
        }
LAB_004242c6:
        local_8 = 0x4242d3;
        FUN_004291a7();
        return;
      }
    }
  } while( true );
}



/* 00424320 FUN_00424320 */

void __cdecl FUN_00424320(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  uint local_14;
  int local_10;
  uint local_c;
  byte *local_8;
  
  puVar3 = local_54;
  for (iVar1 = 0x14; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    local_8 = param_2;
    while (param_2 != param_3) {
      if (*param_2 == 0) {
        local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
      }
      else {
        local_c = FUN_0041adb0(*param_2,param_2[1]);
      }
      local_10 = local_c - 2;
      switch(local_c) {
      case 2:
        *param_4 = param_2;
        goto LAB_0042452d;
      case 3:
        if (param_2 == local_8) {
          FUN_004209d0(param_1,param_2 + 2,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042452d;
      default:
        param_2 = param_2 + 2;
        break;
      case 5:
        param_2 = param_2 + 2;
        break;
      case 6:
        param_2 = param_2 + 3;
        break;
      case 7:
        param_2 = param_2 + 4;
        break;
      case 9:
        if (param_2 == local_8) {
          pbVar2 = param_2 + 2;
          if (pbVar2 != param_3) {
            if (*pbVar2 == 0) {
              local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
            }
            else {
              local_14 = FUN_0041adb0(*pbVar2,param_2[3]);
            }
            if (local_14 == 10) {
              pbVar2 = param_2 + 4;
            }
            param_2 = pbVar2;
            *param_4 = param_2;
          }
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042452d;
      case 10:
        if (param_2 == local_8) {
          *param_4 = param_2 + 2;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042452d;
      case 0x15:
        if (param_2 == local_8) {
          *param_4 = param_2 + 2;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042452d;
      }
    }
    *param_4 = param_2;
  }
LAB_0042452d:
  local_8 = (byte *)0x42453a;
  FUN_004291a7();
  return;
}



/* 00424580 FUN_00424580 */

void __cdecl FUN_00424580(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  byte *pbVar2;
  undefined4 *puVar3;
  undefined4 local_58 [16];
  uint local_18;
  int local_14;
  uint local_10;
  undefined4 local_c;
  byte *local_8;
  
  puVar3 = local_58;
  for (iVar1 = 0x15; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 != param_3) {
    local_8 = param_2;
    while (param_2 != param_3) {
      if (*param_2 == 0) {
        local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[1]);
      }
      else {
        local_10 = FUN_0041adb0(*param_2,param_2[1]);
      }
      local_14 = local_10 - 3;
      switch(local_10) {
      case 3:
        if (param_2 == local_8) {
          FUN_004209d0(param_1,param_2 + 2,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042479f;
      default:
        param_2 = param_2 + 2;
        break;
      case 5:
        param_2 = param_2 + 2;
        break;
      case 6:
        param_2 = param_2 + 3;
        break;
      case 7:
        param_2 = param_2 + 4;
        break;
      case 9:
        if (param_2 == local_8) {
          pbVar2 = param_2 + 2;
          if (pbVar2 != param_3) {
            if (*pbVar2 == 0) {
              local_18 = (uint)*(byte *)(param_1 + 0x48 + (uint)param_2[3]);
            }
            else {
              local_18 = FUN_0041adb0(*pbVar2,param_2[3]);
            }
            if (local_18 == 10) {
              pbVar2 = param_2 + 4;
            }
            param_2 = pbVar2;
            *param_4 = param_2;
          }
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042479f;
      case 10:
        if (param_2 == local_8) {
          *param_4 = param_2 + 2;
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042479f;
      case 0x1e:
        if (param_2 == local_8) {
          local_c = FUN_00423970(param_1,param_2 + 2,param_3,param_4);
        }
        else {
          *param_4 = param_2;
        }
        goto LAB_0042479f;
      }
    }
    *param_4 = param_2;
  }
LAB_0042479f:
  local_8 = (byte *)0x4247ac;
  FUN_004291a7();
  return;
}



/* 004247f0 FUN_004247f0 */

void __cdecl FUN_004247f0(int param_1,char *param_2,int param_3,undefined4 *param_4)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_58 [16];
  int local_18;
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_58;
  for (iVar2 = 0x15; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
LAB_00424825:
  pcVar1 = param_2;
  param_2 = pcVar1 + 2;
  if (param_2 == (char *)(param_3 + -2)) goto LAB_0042492b;
  if (*param_2 == '\0') {
    local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)pcVar1[3]);
  }
  else {
    local_8 = FUN_0041adb0(*param_2,pcVar1[3]);
  }
  local_c = local_8 - 9;
  switch(local_8) {
  case 9:
  case 10:
  case 0xd:
  case 0xe:
  case 0xf:
  case 0x10:
  case 0x11:
  case 0x12:
  case 0x13:
  case 0x18:
  case 0x19:
  case 0x1b:
  case 0x1e:
  case 0x1f:
  case 0x20:
  case 0x21:
  case 0x22:
  case 0x23:
    goto LAB_00424825;
  case 0x15:
    goto switchD_00424889_caseD_15;
  case 0x16:
  case 0x1a:
    if (*param_2 == '\0') {
      local_10 = (uint)pcVar1[3];
    }
    else {
      local_10 = 0xffffffff;
    }
    if ((local_10 & 0xffffff80) == 0) goto LAB_00424825;
  }
  if (*param_2 == '\0') {
    local_14 = (int)pcVar1[3];
  }
  else {
    local_14 = -1;
  }
  local_18 = local_14;
  if ((local_14 != 0x24) && (local_14 != 0x40)) {
    *param_4 = param_2;
    goto LAB_0042492b;
  }
  goto LAB_00424825;
switchD_00424889_caseD_15:
  if ((*param_2 == '\0') && (pcVar1[3] == '\t')) {
    *param_4 = param_2;
LAB_0042492b:
    local_8 = 0x424938;
    FUN_004291a7();
    return;
  }
  goto LAB_00424825;
}



/* 00424970 FUN_00424970 */

void __cdecl FUN_00424970(int param_1,char *param_2,int param_3,int param_4)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_64 [16];
  uint local_24;
  int local_20;
  int local_1c;
  int local_18;
  uint local_14;
  uint local_10;
  int local_c;
  int local_8;
  
  puVar3 = local_64;
  for (iVar2 = 0x18; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_8 = 1;
  local_c = 0;
  local_10 = 0;
  pcVar1 = param_2;
  do {
    param_2 = pcVar1 + 2;
    if (*param_2 == '\0') {
      local_14 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)pcVar1[3]);
    }
    else {
      local_14 = FUN_0041adb0(*param_2,pcVar1[3]);
    }
    local_18 = local_14 - 3;
    switch(local_14) {
    case 3:
      if (local_c < param_3) {
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 5:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(char **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      break;
    case 6:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(char **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      param_2 = pcVar1 + 3;
      break;
    case 7:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(char **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
      param_2 = pcVar1 + 4;
      break;
    case 9:
    case 10:
      if (local_8 == 1) {
        local_8 = 0;
      }
      else if ((local_8 == 2) && (local_c < param_3)) {
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 0xb:
    case 0x11:
      if (local_8 != 2) {
        local_8 = 0x424d22;
        FUN_004291a7();
        return;
      }
      break;
    case 0xc:
      if (local_8 == 2) {
        if (local_10 == 0xc) {
          local_8 = 0;
          if (local_c < param_3) {
            *(char **)(param_4 + 8 + local_c * 0x10) = param_2;
          }
          local_c = local_c + 1;
        }
      }
      else {
        if (local_c < param_3) {
          *(char **)(param_4 + 4 + local_c * 0x10) = pcVar1 + 4;
        }
        local_8 = 2;
        local_10 = 0xc;
      }
      break;
    case 0xd:
      if (local_8 == 2) {
        if (local_10 == 0xd) {
          local_8 = 0;
          if (local_c < param_3) {
            *(char **)(param_4 + 8 + local_c * 0x10) = param_2;
          }
          local_c = local_c + 1;
        }
      }
      else {
        if (local_c < param_3) {
          *(char **)(param_4 + 4 + local_c * 0x10) = pcVar1 + 4;
        }
        local_8 = 2;
        local_10 = 0xd;
      }
      break;
    case 0x15:
      if (local_8 == 1) {
        local_8 = 0;
      }
      else if (((local_8 == 2) && (local_c < param_3)) &&
              (*(char *)(param_4 + 0xc + local_c * 0x10) != '\0')) {
        if (param_2 != *(char **)(param_4 + 4 + local_c * 0x10)) {
          if (*param_2 == '\0') {
            local_1c = (int)pcVar1[3];
          }
          else {
            local_1c = -1;
          }
          if (local_1c == 0x20) {
            if (pcVar1[4] == '\0') {
              local_20 = (int)pcVar1[5];
            }
            else {
              local_20 = -1;
            }
            if (local_20 != 0x20) {
              if (pcVar1[4] == '\0') {
                local_24 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)pcVar1[5]);
              }
              else {
                local_24 = FUN_0041adb0(pcVar1[4],pcVar1[5]);
              }
              if (local_24 != local_10) break;
            }
          }
        }
        *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 0;
      }
      break;
    case 0x16:
    case 0x18:
    case 0x1d:
      if (local_8 == 0) {
        if (local_c < param_3) {
          *(char **)(param_4 + local_c * 0x10) = param_2;
          *(undefined1 *)(param_4 + 0xc + local_c * 0x10) = 1;
        }
        local_8 = 1;
      }
    }
    pcVar1 = param_2;
  } while( true );
}



/* 00424d70 FUN_00424d70 */

void __cdecl FUN_00424d70(undefined4 param_1,char *param_2)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_5c [16];
  int local_1c;
  int local_18;
  int local_14;
  int local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_5c;
  for (iVar2 = 0x16; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_8 = 0;
  pcVar1 = param_2 + 4;
  if ((*pcVar1 == '\0') && (param_2[5] == 'x')) {
    param_2 = param_2 + 6;
    while( true ) {
      if ((*param_2 == '\0') && (param_2[1] == ';')) goto LAB_00424ef4;
      if (*param_2 == '\0') {
        local_14 = (int)param_2[1];
      }
      else {
        local_14 = -1;
      }
      local_c = local_14;
      local_18 = local_14 + -0x30;
      switch(local_14) {
      case 0x30:
      case 0x31:
      case 0x32:
      case 0x33:
      case 0x34:
      case 0x35:
      case 0x36:
      case 0x37:
      case 0x38:
      case 0x39:
        local_8 = local_8 << 4 | local_14 - 0x30U;
        break;
      case 0x41:
      case 0x42:
      case 0x43:
      case 0x44:
      case 0x45:
      case 0x46:
        local_8 = local_8 * 0x10 + -0x37 + local_14;
        break;
      case 0x61:
      case 0x62:
      case 99:
      case 100:
      case 0x65:
      case 0x66:
        local_8 = local_8 * 0x10 + -0x57 + local_14;
      }
      if (0x10ffff < (int)local_8) break;
      param_2 = param_2 + 2;
    }
  }
  else {
    while( true ) {
      param_2 = pcVar1;
      if ((*param_2 == '\0') && (param_2[1] == ';')) goto LAB_00424ef4;
      if (*param_2 == '\0') {
        local_1c = (int)param_2[1];
      }
      else {
        local_1c = -1;
      }
      local_10 = local_1c;
      local_8 = local_8 * 10 + -0x30 + local_1c;
      if (0x10ffff < (int)local_8) break;
      pcVar1 = param_2 + 2;
    }
  }
LAB_00424f00:
  local_8 = 0x424f0d;
  FUN_004291a7();
  return;
LAB_00424ef4:
  FUN_004257d0(local_8);
  goto LAB_00424f00;
}



/* 00424f60 FUN_00424f60 */

undefined4 __cdecl FUN_00424f60(undefined4 param_1,char *param_2,int param_3)

{
  char cVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_58 [21];
  
  puVar3 = local_58;
  for (iVar2 = 0x15; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  iVar2 = (param_3 - (int)param_2) / 2;
  if (iVar2 == 2) {
    if ((param_2[2] == '\0') && (param_2[3] == 't')) {
      if (*param_2 == '\0') {
        cVar1 = param_2[1];
      }
      else {
        cVar1 = -1;
      }
      if (cVar1 == 'g') {
        return 0x3e;
      }
      if (cVar1 == 'l') {
        return 0x3c;
      }
    }
  }
  else if (iVar2 == 3) {
    if (((((*param_2 == '\0') && (param_2[1] == 'a')) && (param_2[2] == '\0')) &&
        ((param_2[3] == 'm' && (param_2[4] == '\0')))) && (param_2[5] == 'p')) {
      return 0x26;
    }
  }
  else if (iVar2 == 4) {
    if (*param_2 == '\0') {
      cVar1 = param_2[1];
    }
    else {
      cVar1 = -1;
    }
    if (cVar1 == 'a') {
      if (((param_2[2] == '\0') && (param_2[3] == 'p')) &&
         ((param_2[4] == '\0' &&
          (((param_2[5] == 'o' && (param_2[6] == '\0')) && (param_2[7] == 's')))))) {
        return 0x27;
      }
    }
    else if ((((cVar1 == 'q') && (param_2[2] == '\0')) &&
             ((param_2[3] == 'u' && ((param_2[4] == '\0' && (param_2[5] == 'o')))))) &&
            ((param_2[6] == '\0' && (param_2[7] == 't')))) {
      return 0x22;
    }
  }
  return 0;
}



/* 00425170 FUN_00425170 */

/* WARNING: Removing unreachable block (ram,0x004252f9) */
/* WARNING: Removing unreachable block (ram,0x0042531b) */
/* WARNING: Removing unreachable block (ram,0x00425322) */
/* WARNING: Removing unreachable block (ram,0x00425328) */
/* WARNING: Removing unreachable block (ram,0x0042534a) */
/* WARNING: Removing unreachable block (ram,0x00425356) */

void __cdecl FUN_00425170(int param_1,char *param_2,char *param_3)

{
  char cVar1;
  char cVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_54 [16];
  int local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar4 = local_54;
  for (iVar3 = 0x14; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  do {
    if (*param_2 == '\0') {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 5;
    switch(local_8) {
    case 5:
      goto switchD_004251e4_caseD_5;
    case 6:
      goto switchD_004251e4_caseD_6;
    case 7:
      cVar1 = *param_2;
      cVar2 = *param_3;
      param_3 = param_3 + 1;
      param_2 = param_2 + 1;
      if (cVar1 != cVar2) goto LAB_004253cc;
switchD_004251e4_caseD_6:
      cVar1 = *param_2;
      cVar2 = *param_3;
      param_3 = param_3 + 1;
      param_2 = param_2 + 1;
      if (cVar1 != cVar2) goto LAB_004253cc;
switchD_004251e4_caseD_5:
      if ((*param_2 != *param_3) || (param_2[1] != param_3[1])) goto LAB_004253cc;
LAB_004253c7:
      param_3 = param_3 + 2;
      param_2 = param_2 + 2;
      break;
    default:
      if (*param_3 == '\0') {
        local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_3[1]);
      }
      else {
        local_10 = FUN_0041adb0(*param_3,param_3[1]);
      }
      local_14 = local_10 - 5;
      switch(local_10) {
      case 5:
      case 6:
      case 7:
      case 0x16:
      case 0x18:
      case 0x19:
      case 0x1a:
      case 0x1b:
      case 0x1d:
        break;
      default:
      }
LAB_004253cc:
      local_8 = 0x4253d9;
      FUN_004291a7();
      return;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
    case 0x1d:
      if ((*param_3 == *param_2) && (param_3[1] == param_2[1])) goto LAB_004253c7;
      goto LAB_004253cc;
    }
  } while( true );
}



/* 00425430 FUN_00425430 */

bool __cdecl FUN_00425430(undefined4 param_1,char *param_2,char *param_3,char *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while( true ) {
    if (*param_4 == '\0') {
      return param_2 == param_3;
    }
    if (param_2 == param_3) break;
    if ((*param_2 != '\0') || (param_2[1] != *param_4)) {
      return false;
    }
    param_2 = param_2 + 2;
    param_4 = param_4 + 1;
  }
  return false;
}



/* 004254b0 FUN_004254b0 */

void __cdecl FUN_004254b0(int param_1,char *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_50 [16];
  int local_10;
  uint local_c;
  char *local_8;
  
  puVar2 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  do {
    if (*param_2 == '\0') {
      local_c = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
    }
    else {
      local_c = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_10 = local_c - 5;
    switch(local_c) {
    case 5:
      param_2 = param_2 + 2;
      break;
    case 6:
      param_2 = param_2 + 3;
      break;
    case 7:
      param_2 = param_2 + 4;
      break;
    default:
      local_8 = (char *)0x425573;
      FUN_004291a7();
      return;
    case 0x16:
    case 0x18:
    case 0x19:
    case 0x1a:
    case 0x1b:
    case 0x1d:
      param_2 = param_2 + 2;
    }
  } while( true );
}



/* 004255b0 FUN_004255b0 */

void __cdecl FUN_004255b0(int param_1,char *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  uint local_c;
  uint local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while( true ) {
    if (*param_2 == '\0') {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8;
    if (((int)local_8 < 9) || ((10 < (int)local_8 && (local_8 != 0x15)))) break;
    param_2 = param_2 + 2;
  }
  local_8 = 0x425639;
  FUN_004291a7();
  return;
}



/* 00425640 FUN_00425640 */

void __cdecl FUN_00425640(int param_1,char *param_2,char *param_3,int *param_4)

{
  int iVar1;
  char *pcVar2;
  undefined4 *puVar3;
  undefined4 local_50 [16];
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  while (param_2 != param_3) {
    if (*param_2 == '\0') {
      local_8 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[1]);
    }
    else {
      local_8 = FUN_0041adb0(*param_2,param_2[1]);
    }
    local_c = local_8 - 5;
    switch(local_c) {
    case 0:
      param_2 = param_2 + 2;
      break;
    case 1:
      param_2 = param_2 + 3;
      break;
    case 2:
      param_2 = param_2 + 4;
      break;
    default:
      param_2 = param_2 + 2;
      break;
    case 4:
      *param_4 = *param_4 + 1;
      pcVar2 = param_2 + 2;
      if (pcVar2 != param_3) {
        if (*pcVar2 == '\0') {
          local_10 = (uint)*(byte *)(param_1 + 0x48 + (uint)(byte)param_2[3]);
        }
        else {
          local_10 = FUN_0041adb0(*pcVar2,param_2[3]);
        }
        if (local_10 == 10) {
          pcVar2 = param_2 + 4;
        }
      }
      param_2 = pcVar2;
      param_4[1] = -1;
      break;
    case 5:
      param_4[1] = -1;
      *param_4 = *param_4 + 1;
      param_2 = param_2 + 2;
    }
    param_4[1] = param_4[1] + 1;
  }
  local_8 = 0x4257a6;
  FUN_004291a7();
  return;
}



/* 004257d0 FUN_004257d0 */

int __cdecl FUN_004257d0(int param_1)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [17];
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  iVar1 = param_1 >> 8;
  if (iVar1 < 0xe0) {
    if (iVar1 < 0xd8) {
      if ((iVar1 == 0) && ((&DAT_0042d5c8)[param_1] == '\0')) {
        param_1 = -1;
      }
    }
    else {
      param_1 = -1;
    }
  }
  else if ((iVar1 == 0xff) && ((param_1 == 0xfffe || (param_1 == 0xffff)))) {
    param_1 = -1;
  }
  return param_1;
}



/* 00425860 FUN_00425860 */

undefined4 __cdecl FUN_00425860(int param_1,byte *param_2)

{
  byte bVar1;
  undefined4 uVar2;
  int iVar3;
  byte bVar4;
  undefined4 *puVar5;
  undefined4 local_44 [16];
  
  puVar5 = local_44;
  for (iVar3 = 0x10; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar5 = 0xcccccccc;
    puVar5 = puVar5 + 1;
  }
  if (param_1 < 0) {
    uVar2 = 0;
  }
  else if (param_1 < 0x80) {
    *param_2 = (byte)param_1;
    uVar2 = 1;
  }
  else {
    bVar4 = (byte)(param_1 >> 6);
    if (param_1 < 0x800) {
      *param_2 = bVar4 | 0xc0;
      param_2[1] = (byte)param_1 & 0x3f | 0x80;
      uVar2 = 2;
    }
    else {
      bVar1 = (byte)(param_1 >> 0xc);
      if (param_1 < 0x10000) {
        *param_2 = bVar1 | 0xe0;
        param_2[1] = bVar4 & 0x3f | 0x80;
        param_2[2] = (byte)param_1 & 0x3f | 0x80;
        uVar2 = 3;
      }
      else if (param_1 < 0x110000) {
        *param_2 = (byte)(param_1 >> 0x12) | 0xf0;
        param_2[1] = bVar1 & 0x3f | 0x80;
        param_2[2] = bVar4 & 0x3f | 0x80;
        param_2[3] = (byte)param_1 & 0x3f | 0x80;
        uVar2 = 4;
      }
      else {
        uVar2 = 0;
      }
    }
  }
  return uVar2;
}



/* 00425970 FUN_00425970 */

undefined4 FUN_00425970(void)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  return 0x774;
}



/* 004259a0 FUN_004259a0 */

void __cdecl FUN_004259a0(int param_1,int param_2,int param_3,undefined4 param_4)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  uint local_14;
  uint local_10;
  int local_c;
  uint local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_c = param_1;
  for (local_8 = 0; (int)local_8 < 0x16c; local_8 = local_8 + 1) {
    *(undefined *)(param_1 + local_8) = (&DAT_0042d580)[local_8];
  }
  for (local_8 = 0; (int)local_8 < 0x80; local_8 = local_8 + 1) {
    if ((((&DAT_0042d5c8)[local_8] != '\x1c') && ((&DAT_0042d5c8)[local_8] != '\0')) &&
       (*(int *)(param_2 + local_8 * 4) != local_8)) goto LAB_00425d6b;
  }
  for (local_8 = 0; (int)local_8 < 0x100; local_8 = local_8 + 1) {
    local_10 = *(uint *)(param_2 + local_8 * 4);
    if (local_10 == 0xffffffff) {
      *(undefined1 *)(local_c + local_8 + 0x48) = 1;
      *(undefined2 *)(local_c + 0x174 + local_8 * 2) = 0xffff;
      *(undefined1 *)(local_c + 0x374 + local_8 * 4) = 1;
      *(undefined1 *)(local_c + 0x375 + local_8 * 4) = 0;
    }
    else if ((int)local_10 < 0) {
      if ((int)local_10 < -4) goto LAB_00425d6b;
      *(char *)(local_c + local_8 + 0x48) = '\x05' - ((char)local_10 + '\x02');
      *(undefined1 *)(local_c + 0x374 + local_8 * 4) = 0;
      *(undefined2 *)(local_c + 0x174 + local_8 * 2) = 0;
    }
    else if ((int)local_10 < 0x80) {
      if ((((&DAT_0042d5c8)[local_10] != '\x1c') && ((&DAT_0042d5c8)[local_10] != '\0')) &&
         (local_10 != local_8)) goto LAB_00425d6b;
      *(undefined *)(local_c + local_8 + 0x48) = (&DAT_0042d5c8)[local_10];
      *(undefined1 *)(local_c + 0x374 + local_8 * 4) = 1;
      *(char *)(local_c + 0x375 + local_8 * 4) = (char)local_10;
      local_14 = local_10;
      if (local_10 == 0) {
        local_14 = 0xffff;
      }
      *(undefined2 *)(local_c + 0x174 + local_8 * 2) = (undefined2)local_14;
    }
    else {
      iVar2 = FUN_004257d0(local_10);
      if (iVar2 < 0) {
        *(undefined1 *)(local_c + local_8 + 0x48) = 0;
        *(undefined2 *)(local_c + 0x174 + local_8 * 2) = 0xffff;
        *(undefined1 *)(local_c + 0x374 + local_8 * 4) = 1;
        *(undefined1 *)(local_c + 0x375 + local_8 * 4) = 0;
      }
      else {
        if (0xffff < (int)local_10) goto LAB_00425d6b;
        if ((*(uint *)(&DAT_0042cb98 +
                      (((int)(local_10 & 0xff) >> 5) +
                      (uint)(byte)(&DAT_0042d098)[(int)local_10 >> 8] * 8) * 4) &
            1 << ((byte)local_10 & 0x1f)) == 0) {
          if ((*(uint *)(&DAT_0042cb98 +
                        (((int)(local_10 & 0xff) >> 5) +
                        (uint)(byte)(&DAT_0042d198)[(int)local_10 >> 8] * 8) * 4) &
              1 << ((byte)local_10 & 0x1f)) == 0) {
            *(undefined1 *)(local_c + local_8 + 0x48) = 0x1c;
          }
          else {
            *(undefined1 *)(local_c + local_8 + 0x48) = 0x1a;
          }
        }
        else {
          *(undefined1 *)(local_c + local_8 + 0x48) = 0x16;
        }
        uVar1 = FUN_00425860(local_10,(byte *)(local_c + 0x375 + local_8 * 4));
        *(char *)(local_c + 0x374 + local_8 * 4) = (char)uVar1;
        *(undefined2 *)(local_c + 0x174 + local_8 * 2) = (undefined2)local_10;
      }
    }
  }
  *(undefined4 *)(local_c + 0x170) = param_4;
  *(int *)(local_c + 0x16c) = param_3;
  if (param_3 != 0) {
    *(code **)(local_c + 0x148) = FUN_00425d80;
    *(code **)(local_c + 0x14c) = FUN_00425d80;
    *(code **)(local_c + 0x150) = FUN_00425d80;
    *(code **)(local_c + 0x154) = FUN_00425e20;
    *(code **)(local_c + 0x158) = FUN_00425e20;
    *(code **)(local_c + 0x15c) = FUN_00425e20;
    *(code **)(local_c + 0x160) = FUN_00425ec0;
    *(code **)(local_c + 0x164) = FUN_00425ec0;
    *(code **)(local_c + 0x168) = FUN_00425ec0;
  }
  *(code **)(local_c + 0x38) = FUN_00425f50;
  *(code **)(local_c + 0x3c) = FUN_00426080;
LAB_00425d6b:
  local_8 = 0x425d78;
  FUN_004291a7();
  return;
}



/* 00425d80 FUN_00425d80 */

void __cdecl FUN_00425d80(int param_1,undefined4 param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  undefined4 local_c;
  int local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_1;
  (**(code **)(param_1 + 0x16c))(*(undefined4 *)(param_1 + 0x170),param_2);
  local_c = FUN_004291a7();
  local_8 = 0x425e1b;
  FUN_004291a7();
  return;
}



/* 00425e20 FUN_00425e20 */

void __cdecl FUN_00425e20(int param_1,undefined4 param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  undefined4 local_c;
  int local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_1;
  (**(code **)(param_1 + 0x16c))(*(undefined4 *)(param_1 + 0x170),param_2);
  local_c = FUN_004291a7();
  local_8 = 0x425ebb;
  FUN_004291a7();
  return;
}



/* 00425ec0 FUN_00425ec0 */

void __cdecl FUN_00425ec0(int param_1,undefined4 param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_50 [16];
  undefined4 local_10;
  uint local_c;
  int local_8;
  
  puVar2 = local_50;
  for (iVar1 = 0x13; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_1;
  (**(code **)(param_1 + 0x16c))(*(undefined4 *)(param_1 + 0x170),param_2);
  local_c = FUN_004291a7();
  if ((local_c & 0xffff0000) == 0) {
    iVar1 = FUN_004257d0(local_c);
    if (-1 < iVar1) {
      local_10 = 0;
      goto LAB_00425f31;
    }
  }
  local_10 = 1;
LAB_00425f31:
  local_8 = 0x425f41;
  FUN_004291a7();
  return;
}



/* 00425f50 FUN_00425f50 */

void __cdecl FUN_00425f50(int param_1,int *param_2,int param_3,int *param_4,int param_5)

{
  char *pcVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_58 [16];
  int local_18;
  int local_14;
  byte *local_10;
  byte local_c [4];
  int local_8;
  
  puVar3 = local_58;
  for (iVar2 = 0x15; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_8 = param_1;
  while (*param_2 != param_3) {
    pcVar1 = (char *)(local_8 + 0x374 + (uint)*(byte *)*param_2 * 4);
    local_14 = (int)*pcVar1;
    local_10 = (byte *)(pcVar1 + 1);
    if (local_14 == 0) {
      (**(code **)(local_8 + 0x16c))(*(undefined4 *)(local_8 + 0x170),*param_2);
      local_18 = FUN_004291a7();
      local_14 = FUN_00425860(local_18,local_c);
      if (param_5 - *param_4 < local_14) break;
      local_10 = local_c;
      *param_2 = *param_2 + -3 + (uint)*(byte *)(param_1 + 0x48 + (uint)*(byte *)*param_2);
    }
    else {
      if (param_5 - *param_4 < local_14) break;
      *param_2 = *param_2 + 1;
    }
    do {
      *(byte *)*param_4 = *local_10;
      *param_4 = *param_4 + 1;
      local_10 = local_10 + 1;
      local_14 = local_14 + -1;
    } while (local_14 != 0);
    local_14 = 0;
  }
  local_8 = 0x42607c;
  FUN_004291a7();
  return;
}



/* 00426080 FUN_00426080 */

void __cdecl FUN_00426080(int param_1,int *param_2,int param_3,int *param_4,int param_5)

{
  short sVar1;
  undefined2 uVar2;
  int iVar3;
  undefined4 *puVar4;
  undefined4 local_4c [16];
  short local_c;
  undefined2 uStack_a;
  int local_8;
  
  puVar4 = local_4c;
  for (iVar3 = 0x12; iVar3 != 0; iVar3 = iVar3 + -1) {
    *puVar4 = 0xcccccccc;
    puVar4 = puVar4 + 1;
  }
  local_8 = param_1;
  while ((*param_2 != param_3 && (*param_4 != param_5))) {
    sVar1 = *(short *)(local_8 + 0x174 + (uint)*(byte *)*param_2 * 2);
    _local_c = CONCAT22(uStack_a,sVar1);
    if (sVar1 == 0) {
      (**(code **)(local_8 + 0x16c))(*(undefined4 *)(local_8 + 0x170),*param_2);
      uVar2 = FUN_004291a7();
      _local_c = CONCAT22(uStack_a,uVar2);
      *param_2 = *param_2 + -3 + (uint)*(byte *)(param_1 + 0x48 + (uint)*(byte *)*param_2);
    }
    else {
      *param_2 = *param_2 + 1;
    }
    *(short *)*param_4 = local_c;
    *param_4 = *param_4 + 2;
  }
  local_8 = 0x426162;
  FUN_004291a7();
  return;
}



/* 00426170 FUN_00426170 */

undefined ** FUN_00426170(void)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  return &PTR_FUN_0042d410;
}



/* 004261a0 FUN_004261a0 */

void __cdecl FUN_004261a0(undefined4 *param_1,undefined4 *param_2,char *param_3)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  undefined4 local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  iVar1 = FUN_00426270(param_3);
  if (iVar1 != -1) {
    local_8._0_1_ = (undefined1)iVar1;
    *(undefined1 *)((int)param_1 + 0x45) = (undefined1)local_8;
    *param_1 = FUN_00426390;
    param_1[1] = FUN_00426730;
    param_1[0xc] = FUN_00426220;
    param_1[0x12] = param_2;
    *param_2 = param_1;
  }
  local_8 = 0x42621a;
  FUN_004291a7();
  return;
}



/* 00426220 FUN_00426220 */

void __cdecl FUN_00426220(undefined4 param_1,byte *param_2,byte *param_3,int *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [15];
  undefined4 uStack_8;
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  FUN_00419b70(0x42d2a0,param_2,param_3,param_4);
  uStack_8 = 0x42625e;
  FUN_004291a7();
  return;
}



/* 00426270 FUN_00426270 */

void __cdecl FUN_00426270(char *param_1)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if (param_1 != (char *)0x0) {
    local_8 = 0;
    while ((local_8 < 6 &&
           (iVar1 = FUN_004262f0(param_1,(&PTR_s_ISO_8859_1_0042de94)[local_8]), iVar1 == 0))) {
      local_8 = local_8 + 1;
    }
  }
  local_8 = 0x4262df;
  FUN_004291a7();
  return;
}



/* 004262f0 FUN_004262f0 */

undefined4 __cdecl FUN_004262f0(char *param_1,char *param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  char local_c;
  char local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  while( true ) {
    local_8 = *param_1;
    param_1 = param_1 + 1;
    local_c = *param_2;
    param_2 = param_2 + 1;
    if (('`' < local_8) && (local_8 < '{')) {
      local_8 = local_8 + -0x20;
    }
    if (('`' < local_c) && (local_c < '{')) {
      local_c = local_c + -0x20;
    }
    if (local_8 != local_c) break;
    if (local_8 == '\0') {
      return 1;
    }
  }
  return 0;
}



/* 00426390 FUN_00426390 */

void __cdecl FUN_00426390(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [15];
  undefined4 uStack_8;
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  FUN_004263e0(0x42deac,param_1,0,param_2,param_3,param_4);
  uStack_8 = 0x4263d4;
  FUN_004291a7();
  return;
}



/* 004263e0 FUN_004263e0 */

void __cdecl
FUN_004263e0(int param_1,int param_2,int param_3,byte *param_4,byte *param_5,undefined4 *param_6)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_58 [16];
  uint local_18;
  uint local_14;
  char local_10;
  int local_c;
  int *local_8;
  
  puVar2 = local_58;
  for (iVar1 = 0x15; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  if (param_4 == param_5) goto LAB_0042671e;
  local_8 = *(int **)(param_2 + 0x48);
  if (param_4 + 1 != param_5) {
    local_18 = (uint)CONCAT11(*param_4,param_4[1]);
    if (local_18 < 0xff00) {
      if (local_18 == 0xfeff) {
        if ((*(char *)(param_2 + 0x45) != '\0') || (param_3 != 1)) {
          *param_6 = param_4 + 2;
          *local_8 = *(int *)(param_1 + 0x10);
          goto LAB_0042671e;
        }
      }
      else if (local_18 == 0x3c00) {
        if (((*(char *)(param_2 + 0x45) != '\x04') && (*(char *)(param_2 + 0x45) != '\x03')) ||
           (param_3 != 1)) {
          *local_8 = *(int *)(param_1 + 0x14);
          (**(code **)(*local_8 + param_3 * 4))(*local_8,param_4,param_5,param_6);
          FUN_004291a7();
          goto LAB_0042671e;
        }
      }
      else {
        if (local_18 != 0xefbb) goto LAB_00426640;
        if ((param_3 != 1) ||
           ((((local_c = (int)*(char *)(param_2 + 0x45), local_c != 0 && (local_c != 4)) &&
             (local_c != 5)) && (local_c != 3)))) {
          if (param_4 + 2 == param_5) goto LAB_0042671e;
          if (param_4[2] == 0xbf) {
            *param_6 = param_4 + 3;
            *local_8 = *(int *)(param_1 + 8);
            goto LAB_0042671e;
          }
        }
      }
    }
    else if (local_18 == 0xfffe) {
      if ((*(char *)(param_2 + 0x45) != '\0') || (param_3 != 1)) {
        *param_6 = param_4 + 2;
        *local_8 = *(int *)(param_1 + 0x14);
        goto LAB_0042671e;
      }
    }
    else {
LAB_00426640:
      if (*param_4 == 0) {
        if ((param_3 != 1) || (*(char *)(param_2 + 0x45) != '\x05')) {
          *local_8 = *(int *)(param_1 + 0x10);
          (**(code **)(*local_8 + param_3 * 4))(*local_8,param_4,param_5,param_6);
          FUN_004291a7();
          goto LAB_0042671e;
        }
      }
      else if ((param_4[1] == 0) && (param_3 != 1)) {
        *local_8 = *(int *)(param_1 + 0x14);
        (**(code **)(*local_8 + param_3 * 4))(*local_8,param_4,param_5,param_6);
        FUN_004291a7();
        goto LAB_0042671e;
      }
    }
    goto LAB_004266e3;
  }
  if ((param_3 != 1) ||
     ((local_10 = *(char *)(param_2 + 0x45), '\x02' < local_10 && (local_10 < '\x06'))))
  goto LAB_0042671e;
  local_14 = (uint)*param_4;
  if (local_14 < 0xf0) {
    if (local_14 == 0xef) {
LAB_0042648d:
      if (*(char *)(param_2 + 0x45) != '\0') goto LAB_0042671e;
    }
    else if ((local_14 == 0) || (local_14 == 0x3c)) goto LAB_0042671e;
  }
  else if ((0xfd < local_14) && (local_14 < 0x100)) goto LAB_0042648d;
LAB_004266e3:
  *local_8 = *(int *)(param_1 + *(char *)(param_2 + 0x45) * 4);
  (**(code **)(*local_8 + param_3 * 4))(*local_8,param_4,param_5,param_6);
  FUN_004291a7();
LAB_0042671e:
  local_8 = (int *)0x42672b;
  FUN_004291a7();
  return;
}



/* 00426730 FUN_00426730 */

void __cdecl FUN_00426730(int param_1,byte *param_2,byte *param_3,undefined4 *param_4)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [15];
  undefined4 uStack_8;
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  FUN_004263e0(0x42deac,param_1,1,param_2,param_3,param_4);
  uStack_8 = 0x426774;
  FUN_004291a7();
  return;
}



/* 00426780 FUN_00426780 */

void __cdecl
FUN_00426780(int param_1,int param_2,int param_3,int param_4,int *param_5,int *param_6,int *param_7,
            int *param_8,undefined4 *param_9,undefined4 *param_10)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [15];
  undefined4 uStack_8;
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  FUN_004267e0(FUN_00426eb0,param_1,param_2,param_3,param_4,param_5,param_6,param_7,param_8,param_9,
               param_10);
  uStack_8 = 0x4267da;
  FUN_004291a7();
  return;
}



/* 004267e0 FUN_004267e0 */

void __cdecl
FUN_004267e0(undefined *param_1,int param_2,int param_3,int param_4,int param_5,int *param_6,
            int *param_7,int *param_8,int *param_9,undefined4 *param_10,undefined4 *param_11)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_54 [16];
  int local_14;
  int local_10;
  int local_c;
  int local_8;
  
  puVar3 = local_54;
  for (iVar2 = 0x14; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  local_8 = 0;
  local_c = 0;
  local_10 = 0;
  param_4 = param_4 + *(int *)(param_3 + 0x40) * 5;
  param_5 = param_5 + *(int *)(param_3 + 0x40) * -2;
  iVar2 = FUN_00426c10(param_3,param_4,param_5,&local_c,&local_10,&local_8,&param_4);
  if ((iVar2 == 0) || (local_c == 0)) {
    *param_6 = param_4;
    goto LAB_00426b17;
  }
  (**(code **)(param_3 + 0x18))(param_3,local_c,local_10,"version");
  iVar2 = FUN_004291a7();
  if (iVar2 == 0) {
    if (param_2 == 0) {
      *param_6 = local_c;
      goto LAB_00426b17;
    }
  }
  else {
    if (param_7 != (int *)0x0) {
      *param_7 = local_8;
    }
    if (param_8 != (int *)0x0) {
      *param_8 = param_4;
    }
    iVar2 = FUN_00426c10(param_3,param_4,param_5,&local_c,&local_10,&local_8,&param_4);
    if (iVar2 == 0) {
      *param_6 = param_4;
      goto LAB_00426b17;
    }
    if (local_c == 0) {
      if (param_2 != 0) {
        *param_6 = param_4;
      }
      goto LAB_00426b17;
    }
  }
  (**(code **)(param_3 + 0x18))(param_3,local_c,local_10,"encoding");
  iVar2 = FUN_004291a7();
  if (iVar2 != 0) {
    local_14 = FUN_00426b30(param_3,local_8,param_5);
    if (((local_14 < 0x61) || (0x7a < local_14)) && ((local_14 < 0x41 || (0x5a < local_14)))) {
      *param_6 = local_8;
      goto LAB_00426b17;
    }
    if (param_9 != (int *)0x0) {
      *param_9 = local_8;
    }
    if (param_10 != (undefined4 *)0x0) {
      (*(code *)param_1)(param_3,local_8,param_4 - *(int *)(param_3 + 0x40));
      uVar1 = FUN_004291a7();
      *param_10 = uVar1;
    }
    iVar2 = FUN_00426c10(param_3,param_4,param_5,&local_c,&local_10,&local_8,&param_4);
    if (iVar2 == 0) {
      *param_6 = param_4;
      goto LAB_00426b17;
    }
    if (local_c == 0) goto LAB_00426b17;
  }
  (**(code **)(param_3 + 0x18))(param_3,local_c,local_10,"standalone");
  iVar2 = FUN_004291a7();
  if ((iVar2 == 0) || (param_2 != 0)) {
    *param_6 = local_c;
  }
  else {
    (**(code **)(param_3 + 0x18))(param_3,local_8,param_4 - *(int *)(param_3 + 0x40),&DAT_0042de4c);
    iVar2 = FUN_004291a7();
    if (iVar2 == 0) {
      (**(code **)(param_3 + 0x18))
                (param_3,local_8,param_4 - *(int *)(param_3 + 0x40),&DAT_0042de50);
      iVar2 = FUN_004291a7();
      if (iVar2 == 0) {
        *param_6 = local_8;
        goto LAB_00426b17;
      }
      if (param_11 != (undefined4 *)0x0) {
        *param_11 = 0;
      }
    }
    else if (param_11 != (undefined4 *)0x0) {
      *param_11 = 1;
    }
    while( true ) {
      uVar1 = FUN_00426b30(param_3,param_4,param_5);
      iVar2 = FUN_00426ba0(uVar1);
      if (iVar2 == 0) break;
      param_4 = param_4 + *(int *)(param_3 + 0x40);
    }
    if (param_4 != param_5) {
      *param_6 = param_4;
    }
  }
LAB_00426b17:
  local_8 = 0x426b24;
  FUN_004291a7();
  return;
}



/* 00426b30 FUN_00426b30 */

void __cdecl FUN_00426b30(int param_1,undefined4 param_2,undefined4 param_3)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  undefined4 *local_c;
  undefined4 local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_c = &local_8;
  (**(code **)(param_1 + 0x38))(param_1,&param_2,param_3,&local_c,(int)&local_8 + 1);
  FUN_004291a7();
  local_8 = 0x426b97;
  FUN_004291a7();
  return;
}



/* 00426ba0 FUN_00426ba0 */

undefined4 __cdecl FUN_00426ba0(undefined4 param_1)

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_48 [17];
  
  puVar3 = local_48;
  for (iVar2 = 0x11; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  switch(param_1) {
  case 9:
  case 10:
  case 0xd:
  case 0x20:
    uVar1 = 1;
    break;
  default:
    uVar1 = 0;
  }
  return uVar1;
}



/* 00426c10 FUN_00426c10 */

void __cdecl
FUN_00426c10(int param_1,int param_2,int param_3,int *param_4,int *param_5,int *param_6,int *param_7
            )

{
  undefined4 uVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 local_4c [16];
  char local_c;
  int local_8;
  
  puVar3 = local_4c;
  for (iVar2 = 0x12; iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar3 = 0xcccccccc;
    puVar3 = puVar3 + 1;
  }
  if (param_2 == param_3) {
    *param_4 = 0;
  }
  else {
    uVar1 = FUN_00426b30(param_1,param_2,param_3);
    iVar2 = FUN_00426ba0(uVar1);
    if (iVar2 == 0) {
      *param_7 = param_2;
    }
    else {
      do {
        param_2 = param_2 + *(int *)(param_1 + 0x40);
        uVar1 = FUN_00426b30(param_1,param_2,param_3);
        iVar2 = FUN_00426ba0(uVar1);
      } while (iVar2 != 0);
      if (param_2 == param_3) {
        *param_4 = 0;
      }
      else {
        *param_4 = param_2;
        while( true ) {
          local_8 = FUN_00426b30(param_1,param_2,param_3);
          if (local_8 == -1) goto code_r0x00426ce0;
          if (local_8 == 0x3d) {
            *param_5 = param_2;
            local_8 = 0x3d;
            goto LAB_00426d72;
          }
          iVar2 = FUN_00426ba0(local_8);
          if (iVar2 != 0) break;
          param_2 = param_2 + *(int *)(param_1 + 0x40);
        }
        *param_5 = param_2;
        do {
          param_2 = param_2 + *(int *)(param_1 + 0x40);
          local_8 = FUN_00426b30(param_1,param_2,param_3);
          iVar2 = FUN_00426ba0(local_8);
        } while (iVar2 != 0);
        if (local_8 == 0x3d) {
LAB_00426d72:
          if (param_2 == *param_4) {
            *param_7 = param_2;
            goto LAB_00426e9f;
          }
          param_2 = param_2 + *(int *)(param_1 + 0x40);
          local_8 = FUN_00426b30(param_1,param_2,param_3);
          while (iVar2 = FUN_00426ba0(local_8), iVar2 != 0) {
            param_2 = param_2 + *(int *)(param_1 + 0x40);
            local_8 = FUN_00426b30(param_1,param_2,param_3);
          }
          if ((local_8 == 0x22) || (local_8 == 0x27)) {
            local_c = (char)local_8;
            param_2 = param_2 + *(int *)(param_1 + 0x40);
            *param_6 = param_2;
            while (local_8 = FUN_00426b30(param_1,param_2,param_3), local_8 != local_c) {
              if ((((local_8 < 0x61) || (0x7a < local_8)) && ((local_8 < 0x41 || (0x5a < local_8))))
                 && ((((local_8 < 0x30 || (0x39 < local_8)) && (local_8 != 0x2e)) &&
                     ((local_8 != 0x2d && (local_8 != 0x5f)))))) {
                *param_7 = param_2;
                goto LAB_00426e9f;
              }
              param_2 = param_2 + *(int *)(param_1 + 0x40);
            }
            *param_7 = param_2 + *(int *)(param_1 + 0x40);
          }
          else {
            *param_7 = param_2;
          }
        }
        else {
          *param_7 = param_2;
        }
      }
    }
  }
LAB_00426e9f:
  local_8 = 0x426eac;
  FUN_004291a7();
  return;
code_r0x00426ce0:
  *param_7 = param_2;
  goto LAB_00426e9f;
}



/* 00426eb0 FUN_00426eb0 */

void __cdecl FUN_00426eb0(int param_1,int param_2,int param_3)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_cc [16];
  undefined4 local_8c;
  char *local_88;
  char local_84 [124];
  undefined4 uStack_8;
  
  puVar2 = local_cc;
  for (iVar1 = 0x32; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_88 = local_84;
  (**(code **)(param_1 + 0x38))(param_1,&param_2,param_3,&local_88,(int)&uStack_8 + 3);
  FUN_004291a7();
  if (param_2 == param_3) {
    *local_88 = '\0';
    iVar1 = FUN_004262f0(local_84,"UTF-16");
    if ((iVar1 == 0) || (*(int *)(param_1 + 0x40) != 2)) {
      local_8c = FUN_00426270(local_84);
    }
  }
  uStack_8 = 0x426f7a;
  FUN_004291a7();
  return;
}



/* 00426f80 FUN_00426f80 */

void __cdecl FUN_00426f80(undefined4 *param_1)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  *param_1 = FUN_00426fb0;
  return;
}



/* 00426fb0 FUN_00426fb0 */

void __cdecl
FUN_00426fb0(undefined4 *param_1,int param_2,int param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xb;
  switch(param_2) {
  case 0xb:
    *param_1 = FUN_004270e0;
    break;
  case 0xc:
    *param_1 = FUN_004270e0;
    break;
  case 0xd:
    *param_1 = FUN_004270e0;
    break;
  case 0xe:
    break;
  case 0xf:
    *param_1 = FUN_004270e0;
    break;
  case 0x10:
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40) * 2,param_4,"DOCTYPE");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_004271e0;
      break;
    }
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x1d:
    *param_1 = FUN_00428ec0;
  }
  local_8 = 0x4270a9;
  FUN_004291a7();
  return;
}



/* 004270e0 FUN_004270e0 */

void __cdecl
FUN_004270e0(undefined4 *param_1,int param_2,int param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xb;
  switch(param_2) {
  case 0xb:
    break;
  case 0xd:
    break;
  case 0xe:
    break;
  case 0xf:
    break;
  case 0x10:
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40) * 2,param_4,"DOCTYPE");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_004271e0;
      break;
    }
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x1d:
    *param_1 = FUN_00428ec0;
  }
  local_8 = 0x4271a4;
  FUN_004291a7();
  return;
}



/* 004271e0 FUN_004271e0 */

void __cdecl FUN_004271e0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if ((param_2 == 0x12) || (param_2 == 0x29)) {
      *param_1 = FUN_00427250;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427246;
  FUN_004291a7();
  return;
}



/* 00427250 FUN_00427250 */

void __cdecl
FUN_00427250(undefined4 *param_1,int param_2,undefined4 param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xf;
  switch(param_2) {
  case 0xf:
    break;
  case 0x11:
    *param_1 = FUN_00427370;
    break;
  case 0x12:
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"SYSTEM");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427490;
      break;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"PUBLIC");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427420;
      break;
    }
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x19:
    *param_1 = FUN_00427580;
  }
  local_8 = 0x42734b;
  FUN_004291a7();
  return;
}



/* 00427370 FUN_00427370 */

void __cdecl FUN_00427370(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xb;
  switch(param_2) {
  case 0xb:
    break;
  default:
    FUN_00428ef0(param_1);
    break;
  case 0xd:
    break;
  case 0xf:
    break;
  case 0x1d:
    *param_1 = FUN_00428ec0;
  }
  local_8 = 0x4273ee;
  FUN_004291a7();
  return;
}



/* 00427420 FUN_00427420 */

void __cdecl FUN_00427420(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00427490;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427480;
  FUN_004291a7();
  return;
}



/* 00427490 FUN_00427490 */

void __cdecl FUN_00427490(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00427500;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x4274f0;
  FUN_004291a7();
  return;
}



/* 00427500 FUN_00427500 */

void __cdecl FUN_00427500(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x11) {
      *param_1 = FUN_00427370;
    }
    else if (param_2 == 0x19) {
      *param_1 = FUN_00427580;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427576;
  FUN_004291a7();
  return;
}



/* 00427580 FUN_00427580 */

void __cdecl
FUN_00427580(undefined4 *param_1,int param_2,int param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + 4;
  switch(param_2) {
  case 0xb:
    break;
  case 0xd:
    break;
  case 0xf:
    break;
  case 0x10:
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40) * 2,param_4,"ENTITY");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_004277d0;
      break;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40) * 2,param_4,"ATTLIST");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_004280a0;
      break;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40) * 2,param_4,"ELEMENT");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00428700;
      break;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40) * 2,param_4,"NOTATION")
    ;
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427df0;
      break;
    }
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x1a:
    *param_1 = FUN_00427760;
    break;
  case 0x1c:
    break;
  case -4:
  }
  local_8 = 0x427718;
  FUN_004291a7();
  return;
}



/* 00427760 FUN_00427760 */

void __cdecl FUN_00427760(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x11) {
      *param_1 = FUN_00427370;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x4277c0;
  FUN_004291a7();
  return;
}



/* 004277d0 FUN_004277d0 */

void __cdecl FUN_004277d0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x12) {
      *param_1 = FUN_004278c0;
    }
    else if (param_2 == 0x16) {
      *param_1 = FUN_00427850;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427846;
  FUN_004291a7();
  return;
}



/* 00427850 FUN_00427850 */

void __cdecl FUN_00427850(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x12) {
      *param_1 = FUN_00427bb0;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x4278b0;
  FUN_004291a7();
  return;
}



/* 004278c0 FUN_004278c0 */

void __cdecl
FUN_004278c0(undefined4 *param_1,int param_2,undefined4 param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 == 0xf) goto LAB_00427999;
  if (param_2 == 0x12) {
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"SYSTEM");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427a20;
      goto LAB_00427999;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"PUBLIC");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_004279b0;
      goto LAB_00427999;
    }
  }
  else if (param_2 == 0x1b) {
    *param_1 = FUN_00428e50;
    param_1[2] = 0xb;
    goto LAB_00427999;
  }
  FUN_00428ef0(param_1);
LAB_00427999:
  local_8 = 0x4279a6;
  FUN_004291a7();
  return;
}



/* 004279b0 FUN_004279b0 */

void __cdecl FUN_004279b0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00427a20;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427a10;
  FUN_004291a7();
  return;
}



/* 00427a20 FUN_00427a20 */

void __cdecl FUN_00427a20(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00427a90;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427a80;
  FUN_004291a7();
  return;
}



/* 00427a90 FUN_00427a90 */

void __cdecl
FUN_00427a90(undefined4 *param_1,int param_2,undefined4 param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x11) {
      *param_1 = FUN_00427580;
    }
    else {
      if (param_2 == 0x12) {
        (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"NDATA");
        iVar1 = FUN_004291a7();
        if (iVar1 != 0) {
          *param_1 = FUN_00427b40;
          goto LAB_00427b20;
        }
      }
      FUN_00428ef0(param_1);
    }
  }
LAB_00427b20:
  local_8 = 0x427b2d;
  FUN_004291a7();
  return;
}



/* 00427b40 FUN_00427b40 */

void __cdecl FUN_00427b40(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x12) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0xb;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427baa;
  FUN_004291a7();
  return;
}



/* 00427bb0 FUN_00427bb0 */

void __cdecl
FUN_00427bb0(undefined4 *param_1,int param_2,undefined4 param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 == 0xf) goto LAB_00427c89;
  if (param_2 == 0x12) {
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"SYSTEM");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427d10;
      goto LAB_00427c89;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"PUBLIC");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427ca0;
      goto LAB_00427c89;
    }
  }
  else if (param_2 == 0x1b) {
    *param_1 = FUN_00428e50;
    param_1[2] = 0xb;
    goto LAB_00427c89;
  }
  FUN_00428ef0(param_1);
LAB_00427c89:
  local_8 = 0x427c96;
  FUN_004291a7();
  return;
}



/* 00427ca0 FUN_00427ca0 */

void __cdecl FUN_00427ca0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00427d10;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427d00;
  FUN_004291a7();
  return;
}



/* 00427d10 FUN_00427d10 */

void __cdecl FUN_00427d10(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00427d80;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427d70;
  FUN_004291a7();
  return;
}



/* 00427d80 FUN_00427d80 */

void __cdecl FUN_00427d80(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x11) {
      *param_1 = FUN_00427580;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427de0;
  FUN_004291a7();
  return;
}



/* 00427df0 FUN_00427df0 */

void __cdecl FUN_00427df0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x12) {
      *param_1 = FUN_00427e60;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427e50;
  FUN_004291a7();
  return;
}



/* 00427e60 FUN_00427e60 */

void __cdecl
FUN_00427e60(undefined4 *param_1,int param_2,undefined4 param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 == 0xf) goto LAB_00427f11;
  if (param_2 == 0x12) {
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"SYSTEM");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427fa0;
      goto LAB_00427f11;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"PUBLIC");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00427f30;
      goto LAB_00427f11;
    }
  }
  FUN_00428ef0(param_1);
LAB_00427f11:
  local_8 = 0x427f1e;
  FUN_004291a7();
  return;
}



/* 00427f30 FUN_00427f30 */

void __cdecl FUN_00427f30(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00428010;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x427f90;
  FUN_004291a7();
  return;
}



/* 00427fa0 FUN_00427fa0 */

void __cdecl FUN_00427fa0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x11;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x42800a;
  FUN_004291a7();
  return;
}



/* 00428010 FUN_00428010 */

void __cdecl FUN_00428010(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x11) {
      *param_1 = FUN_00427580;
    }
    else if (param_2 == 0x1b) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x11;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428090;
  FUN_004291a7();
  return;
}



/* 004280a0 FUN_004280a0 */

void __cdecl FUN_004280a0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if ((param_2 == 0x12) || (param_2 == 0x29)) {
      *param_1 = FUN_00428110;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428106;
  FUN_004291a7();
  return;
}



/* 00428110 FUN_00428110 */

void __cdecl FUN_00428110(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xf;
  switch(param_2) {
  case 0xf:
    break;
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x11:
    *param_1 = FUN_00427580;
    break;
  case 0x12:
  case 0x29:
    *param_1 = FUN_004281d0;
  }
  local_8 = 0x428193;
  FUN_004291a7();
  return;
}



/* 004281d0 FUN_004281d0 */

void __cdecl
FUN_004281d0(undefined4 *param_1,int param_2,undefined4 param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_4c [16];
  int local_c;
  int local_8;
  
  puVar2 = local_4c;
  for (iVar1 = 0x12; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_c = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x12) {
      for (local_8 = 0; local_8 < 8; local_8 = local_8 + 1) {
        (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,(&PTR_s_CDATA_0042df78)[local_8]);
        iVar1 = FUN_004291a7();
        if (iVar1 != 0) {
          *param_1 = FUN_00428560;
          goto LAB_004282c4;
        }
      }
      (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"NOTATION");
      iVar1 = FUN_004291a7();
      if (iVar1 != 0) {
        *param_1 = FUN_00428400;
        goto LAB_004282c4;
      }
    }
    else if (param_2 == 0x17) {
      *param_1 = FUN_004282e0;
      goto LAB_004282c4;
    }
    FUN_00428ef0(param_1);
  }
LAB_004282c4:
  local_8 = 0x4282d1;
  FUN_004291a7();
  return;
}



/* 004282e0 FUN_004282e0 */

void __cdecl FUN_004282e0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xf;
  switch(param_2) {
  case 0xf:
    break;
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x12:
  case 0x13:
  case 0x29:
    *param_1 = FUN_00428380;
  }
  local_8 = 0x428353;
  FUN_004291a7();
  return;
}



/* 00428380 FUN_00428380 */

void __cdecl FUN_00428380(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x15) {
      *param_1 = FUN_004282e0;
    }
    else if (param_2 == 0x18) {
      *param_1 = FUN_00428560;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x4283f6;
  FUN_004291a7();
  return;
}



/* 00428400 FUN_00428400 */

void __cdecl FUN_00428400(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x17) {
      *param_1 = FUN_00428470;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428460;
  FUN_004291a7();
  return;
}



/* 00428470 FUN_00428470 */

void __cdecl FUN_00428470(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x12) {
      *param_1 = FUN_004284e0;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x4284d0;
  FUN_004291a7();
  return;
}



/* 004284e0 FUN_004284e0 */

void __cdecl FUN_004284e0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x15) {
      *param_1 = FUN_00428470;
    }
    else if (param_2 == 0x18) {
      *param_1 = FUN_00428560;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428556;
  FUN_004291a7();
  return;
}



/* 00428560 FUN_00428560 */

void __cdecl
FUN_00428560(undefined4 *param_1,int param_2,int param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 == 0xf) goto LAB_0042867f;
  if (param_2 == 0x14) {
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40),param_4,"IMPLIED");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00428110;
      goto LAB_0042867f;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40),param_4,"REQUIRED");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00428110;
      goto LAB_0042867f;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40),param_4,"FIXED");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00428690;
      goto LAB_0042867f;
    }
  }
  else if (param_2 == 0x1b) {
    *param_1 = FUN_00428110;
    goto LAB_0042867f;
  }
  FUN_00428ef0(param_1);
LAB_0042867f:
  local_8 = 0x42868c;
  FUN_004291a7();
  return;
}



/* 00428690 FUN_00428690 */

void __cdecl FUN_00428690(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x1b) {
      *param_1 = FUN_00428110;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x4286f0;
  FUN_004291a7();
  return;
}



/* 00428700 FUN_00428700 */

void __cdecl FUN_00428700(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if ((param_2 == 0x12) || (param_2 == 0x29)) {
      *param_1 = FUN_00428770;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428766;
  FUN_004291a7();
  return;
}



/* 00428770 FUN_00428770 */

void __cdecl
FUN_00428770(undefined4 *param_1,int param_2,undefined4 param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 == 0xf) goto LAB_00428861;
  if (param_2 == 0x12) {
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,"EMPTY");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x27;
      goto LAB_00428861;
    }
    (**(code **)(param_5 + 0x18))(param_5,param_3,param_4,&DAT_0042dec8);
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x27;
      goto LAB_00428861;
    }
  }
  else if (param_2 == 0x17) {
    *param_1 = FUN_00428880;
    param_1[1] = 1;
    goto LAB_00428861;
  }
  FUN_00428ef0(param_1);
LAB_00428861:
  local_8 = 0x42886e;
  FUN_004291a7();
  return;
}



/* 00428880 FUN_00428880 */

void __cdecl
FUN_00428880(undefined4 *param_1,int param_2,int param_3,undefined4 param_4,int param_5)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xf;
  switch(param_2) {
  case 0xf:
    break;
  case 0x12:
  case 0x29:
    *param_1 = FUN_00428cb0;
    break;
  case 0x14:
    (**(code **)(param_5 + 0x18))(param_5,param_3 + *(int *)(param_5 + 0x40),param_4,"PCDATA");
    iVar1 = FUN_004291a7();
    if (iVar1 != 0) {
      *param_1 = FUN_004289d0;
      break;
    }
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x17:
    param_1[1] = 2;
    *param_1 = FUN_00428bb0;
    break;
  case 0x1e:
    *param_1 = FUN_00428cb0;
    break;
  case 0x1f:
    *param_1 = FUN_00428cb0;
    break;
  case 0x20:
    *param_1 = FUN_00428cb0;
  }
  local_8 = 0x428983;
  FUN_004291a7();
  return;
}



/* 004289d0 FUN_004289d0 */

void __cdecl FUN_004289d0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xf;
  switch(param_2) {
  case 0xf:
    break;
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x15:
    *param_1 = FUN_00428ab0;
    break;
  case 0x18:
    *param_1 = FUN_00428e50;
    param_1[2] = 0x27;
    break;
  case 0x24:
    *param_1 = FUN_00428e50;
    param_1[2] = 0x27;
  }
  local_8 = 0x428a77;
  FUN_004291a7();
  return;
}



/* 00428ab0 FUN_00428ab0 */

void __cdecl FUN_00428ab0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if ((param_2 == 0x12) || (param_2 == 0x29)) {
      *param_1 = FUN_00428b20;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428b16;
  FUN_004291a7();
  return;
}



/* 00428b20 FUN_00428b20 */

void __cdecl FUN_00428b20(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x15) {
      *param_1 = FUN_00428ab0;
    }
    else if (param_2 == 0x24) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x27;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428ba0;
  FUN_004291a7();
  return;
}



/* 00428bb0 FUN_00428bb0 */

void __cdecl FUN_00428bb0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xf;
  switch(param_2) {
  case 0xf:
    break;
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x12:
  case 0x29:
    *param_1 = FUN_00428cb0;
    break;
  case 0x17:
    param_1[1] = param_1[1] + 1;
    break;
  case 0x1e:
    *param_1 = FUN_00428cb0;
    break;
  case 0x1f:
    *param_1 = FUN_00428cb0;
    break;
  case 0x20:
    *param_1 = FUN_00428cb0;
  }
  local_8 = 0x428c69;
  FUN_004291a7();
  return;
}



/* 00428cb0 FUN_00428cb0 */

void __cdecl FUN_00428cb0(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2 + -0xf;
  switch(param_2) {
  case 0xf:
    break;
  default:
    FUN_00428ef0(param_1);
    break;
  case 0x15:
    *param_1 = FUN_00428bb0;
    break;
  case 0x18:
    param_1[1] = param_1[1] + -1;
    if (param_1[1] == 0) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x27;
    }
    break;
  case 0x23:
    param_1[1] = param_1[1] + -1;
    if (param_1[1] == 0) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x27;
    }
    break;
  case 0x24:
    param_1[1] = param_1[1] + -1;
    if (param_1[1] == 0) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x27;
    }
    break;
  case 0x25:
    param_1[1] = param_1[1] + -1;
    if (param_1[1] == 0) {
      *param_1 = FUN_00428e50;
      param_1[2] = 0x27;
    }
    break;
  case 0x26:
    *param_1 = FUN_00428bb0;
  }
  local_8 = 0x428e08;
  FUN_004291a7();
  return;
}



/* 00428e50 FUN_00428e50 */

void __cdecl FUN_00428e50(undefined4 *param_1,int param_2)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_48 [16];
  int local_8;
  
  puVar2 = local_48;
  for (iVar1 = 0x11; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  local_8 = param_2;
  if (param_2 != 0xf) {
    if (param_2 == 0x11) {
      *param_1 = FUN_00427580;
    }
    else {
      FUN_00428ef0(param_1);
    }
  }
  local_8 = 0x428eb2;
  FUN_004291a7();
  return;
}



/* 00428ec0 FUN_00428ec0 */

undefined4 FUN_00428ec0(void)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  return 0;
}



/* 00428ef0 FUN_00428ef0 */

undefined4 __cdecl FUN_00428ef0(undefined4 *param_1)

{
  int iVar1;
  undefined4 *puVar2;
  undefined4 local_44 [16];
  
  puVar2 = local_44;
  for (iVar1 = 0x10; iVar1 != 0; iVar1 = iVar1 + -1) {
    *puVar2 = 0xcccccccc;
    puVar2 = puVar2 + 1;
  }
  *param_1 = FUN_00428ec0;
  return 0xffffffff;
}



/* 00428f1c RtlUnwind */

void RtlUnwind(PVOID TargetFrame,PVOID TargetIp,PEXCEPTION_RECORD ExceptionRecord,PVOID ReturnValue)

{
                    /* WARNING: Could not recover jumptable at 0x00428f1c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  RtlUnwind(TargetFrame,TargetIp,ExceptionRecord,ReturnValue);
  return;
}



/* 00428f30 FUN_00428f30 */

void __cdecl FUN_00428f30(int *param_1)

{
  DWORD DVar1;
  int iVar2;
  _TIME_ZONE_INFORMATION local_d0;
  _SYSTEMTIME local_24;
  _SYSTEMTIME local_14;
  
  GetLocalTime(&local_14);
  GetSystemTime(&local_24);
  if (local_24.wMinute == DAT_004552f8._2_2_) {
    if (local_24.wHour == (WORD)DAT_004552f8) {
      if (local_24.wDay == DAT_004552f4._2_2_) {
        if (local_24.wMonth == DAT_004552f0._2_2_) {
          if (local_24.wYear == (WORD)DAT_004552f0) goto LAB_00428fda;
        }
      }
    }
  }
  DVar1 = GetTimeZoneInformation(&local_d0);
  if (DVar1 == 0xffffffff) {
    DAT_004552e8 = -1;
  }
  else if (((DVar1 == 2) && (local_d0.DaylightDate.wMonth != 0)) && (local_d0.DaylightBias != 0)) {
    DAT_004552e8 = 1;
  }
  else {
    DAT_004552e8 = 0;
  }
  DAT_004552f0._0_2_ = local_24.wYear;
  DAT_004552f0._2_2_ = local_24.wMonth;
  DAT_004552f4._0_2_ = local_24.wDayOfWeek;
  DAT_004552f4._2_2_ = local_24.wDay;
  DAT_004552f8._0_2_ = local_24.wHour;
  DAT_004552f8._2_2_ = local_24.wMinute;
  DAT_004552fc._0_2_ = local_24.wSecond;
  DAT_004552fc._2_2_ = local_24.wMilliseconds;
LAB_00428fda:
  iVar2 = FUN_004291bf((uint)local_14.wYear,(uint)local_14.wMonth,(uint)local_14.wDay,
                       (uint)local_14.wHour,(uint)local_14.wMinute,(uint)local_14.wSecond,
                       DAT_004552e8);
  if (param_1 != (int *)0x0) {
    *param_1 = iVar2;
  }
  return;
}



/* 0042900c FUN_0042900c */

uint __cdecl FUN_0042900c(byte *param_1,byte *param_2)

{
  byte bVar1;
  int iVar2;
  byte *pbVar3;
  byte local_24 [32];
  
  pbVar3 = local_24;
  for (iVar2 = 8; iVar2 != 0; iVar2 = iVar2 + -1) {
    pbVar3[0] = 0;
    pbVar3[1] = 0;
    pbVar3[2] = 0;
    pbVar3[3] = 0;
    pbVar3 = pbVar3 + 4;
  }
  do {
    bVar1 = *param_2;
    local_24[bVar1 >> 3] = local_24[bVar1 >> 3] | '\x01' << (bVar1 & 7);
    param_2 = param_2 + 1;
  } while (bVar1 != 0);
  if (param_1 == (byte *)0x0) {
    param_1 = DAT_00455300;
  }
  for (; (bVar1 = *param_1, DAT_00455300 = param_1,
         (local_24[bVar1 >> 3] & (byte)(1 << (bVar1 & 7))) != 0 && (bVar1 != 0));
      param_1 = param_1 + 1) {
  }
  do {
    bVar1 = *DAT_00455300;
    if (bVar1 == 0) {
LAB_00429093:
      return -(uint)(param_1 != DAT_00455300) & (uint)param_1;
    }
    if ((local_24[bVar1 >> 3] & (byte)(1 << (bVar1 & 7))) != 0) {
      *DAT_00455300 = 0;
      DAT_00455300 = DAT_00455300 + 1;
      goto LAB_00429093;
    }
    DAT_00455300 = DAT_00455300 + 1;
  } while( true );
}



/* 004290a8 FUN_004290a8 */

char * __cdecl FUN_004290a8(char *param_1,int param_2,undefined4 *param_3)

{
  int *piVar1;
  uint uVar2;
  char *pcVar3;
  
  if (param_2 < 1) {
    param_1 = (char *)0x0;
  }
  else {
    param_2 = param_2 + -1;
    pcVar3 = param_1;
    if (param_2 != 0) {
      while( true ) {
        piVar1 = param_3 + 1;
        *piVar1 = *piVar1 + -1;
        if (*piVar1 < 0) {
          uVar2 = FUN_0040d39d(param_3);
        }
        else {
          uVar2 = (uint)*(byte *)*param_3;
          *param_3 = (byte *)*param_3 + 1;
        }
        if (uVar2 == 0xffffffff) break;
        *pcVar3 = (char)uVar2;
        pcVar3 = pcVar3 + 1;
        if (((char)uVar2 == '\n') || (param_2 = param_2 + -1, param_2 == 0)) goto LAB_004290ec;
      }
      if (pcVar3 == param_1) {
        return (char *)0x0;
      }
    }
LAB_004290ec:
    *pcVar3 = '\0';
  }
  return param_1;
}



/* 004290ff FUN_004290ff */

void __cdecl FUN_004290ff(int param_1)

{
  uint uVar1;
  undefined *puVar2;
  
  *(uint *)(param_1 + 0xc) = *(uint *)(param_1 + 0xc) & 0xffffffcf;
  uVar1 = *(uint *)(param_1 + 0x10);
  if (uVar1 == 0xffffffff) {
    puVar2 = &DAT_00430730;
  }
  else {
    puVar2 = (undefined *)((&DAT_00455660)[(int)uVar1 >> 5] + (uVar1 & 0x1f) * 8);
  }
  puVar2[4] = puVar2[4] & 0xfd;
  return;
}



/* 0042912d FUN_0042912d */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void FUN_0042912d(void)

{
  void *extraout_ECX;
  
  FUN_00429145();
  _DAT_00455308 = FUN_004292d1();
  FUN_00429281(extraout_ECX);
  return;
}



/* 00429145 FUN_00429145 */

void FUN_00429145(void)

{
  PTR_FUN_00430884 = &LAB_00429354;
  PTR_FUN_00430880 = __cfltcvt;
  PTR_FUN_00430888 = __fassign;
  PTR_FUN_0043088c = FUN_004292fa;
  PTR_FUN_00430890 = &LAB_004293a2;
  PTR_FUN_00430894 = __cfltcvt;
  return;
}



/* 00429180 __ftol */

/* Library Function - Single Match
    __ftol
   
   Library: Visual Studio */

longlong __ftol(void)

{
  float10 in_ST0;
  
  return (longlong)ROUND(in_ST0);
}



/* 004291a7 FUN_004291a7 */

void FUN_004291a7(void)

{
  code *pcVar1;
  bool in_ZF;
  
  if (in_ZF) {
    return;
  }
  pcVar1 = (code *)swi(3);
  (*pcVar1)();
  return;
}



/* 004291bf FUN_004291bf */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

int __cdecl
FUN_004291bf(int param_1,int param_2,int param_3,int param_4,int param_5,int param_6,int param_7)

{
  bool bVar1;
  undefined3 extraout_var;
  int iVar2;
  uint uVar3;
  int iVar4;
  int local_28 [2];
  int local_20;
  int local_18;
  uint local_14;
  int local_c;
  
  uVar3 = param_1 - 0x76c;
  if (((int)uVar3 < 0x46) || (0x8a < (int)uVar3)) {
    iVar2 = -1;
  }
  else {
    iVar4 = *(int *)(&DAT_00431454 + param_2 * 4) + param_3;
    if (((uVar3 & 3) == 0) && (2 < param_2)) {
      iVar4 = iVar4 + 1;
    }
    FUN_00429735();
    local_20 = param_4;
    local_18 = param_2 + -1;
    iVar2 = ((param_4 + (uVar3 * 0x16d + iVar4 + (param_1 + -0x76d >> 2)) * 0x18) * 0x3c + param_5)
            * 0x3c + DAT_00431370 + 0x7c558180 + param_6;
    if ((param_7 == 1) ||
       (((param_7 == -1 && (DAT_00431374 != 0)) &&
        (local_14 = uVar3, local_c = iVar4, bVar1 = FUN_004299a8(local_28),
        CONCAT31(extraout_var,bVar1) != 0)))) {
      iVar2 = iVar2 + _DAT_00431378;
    }
  }
  return iVar2;
}



/* 00429281 FUN_00429281 */

void __fastcall FUN_00429281(void *param_1)

{
  FUN_00429cc9(param_1,0x10000,0x30000);
  return;
}



/* 00429293 FUN_00429293 */

/* WARNING: Removing unreachable block (ram,0x004292c8) */

undefined4 FUN_00429293(void)

{
  return 0;
}



/* 004292d1 FUN_004292d1 */

void FUN_004292d1(void)

{
  HMODULE hModule;
  FARPROC pFVar1;
  
  hModule = GetModuleHandleA("KERNEL32");
  if (hModule != (HMODULE)0x0) {
    pFVar1 = GetProcAddress(hModule,"IsProcessorFeaturePresent");
    if (pFVar1 != (FARPROC)0x0) {
      (*pFVar1)(0);
      return;
    }
  }
  FUN_00429293();
  return;
}



/* 004292fa FUN_004292fa */

void __thiscall FUN_004292fa(void *this,char *param_1)

{
  char cVar1;
  char cVar2;
  undefined *this_00;
  uint uVar3;
  undefined *puVar4;
  
  this_00 = (undefined *)(int)*param_1;
  uVar3 = FUN_00429dfa(this,(uint)this_00);
  if (uVar3 != 0x65) {
    do {
      param_1 = param_1 + 1;
      if (DAT_00430714 < 2) {
        uVar3 = (byte)PTR_DAT_00430508[*param_1 * 2] & 4;
        this_00 = PTR_DAT_00430508;
      }
      else {
        puVar4 = (undefined *)0x4;
        uVar3 = FUN_0040b9f6(this_00,(int)*param_1,4);
        this_00 = puVar4;
      }
    } while (uVar3 != 0);
  }
  cVar2 = *param_1;
  *param_1 = DAT_00430718;
  do {
    param_1 = param_1 + 1;
    cVar1 = *param_1;
    *param_1 = cVar2;
    cVar2 = cVar1;
  } while (*param_1 != '\0');
  return;
}



/* 004293ba __fassign */

/* Library Function - Single Match
    __fassign
   
   Library: Visual Studio 2003 Release */

void __cdecl __fassign(int flag,char *argument,char *number)

{
  void *in_ECX;
  void *local_c;
  void *local_8;
  
  if (flag != 0) {
    local_c = in_ECX;
    local_8 = in_ECX;
    FUN_0042a257(in_ECX,(uint *)&local_c,(byte *)number);
    *(void **)argument = local_c;
    *(void **)(argument + 4) = local_8;
    return;
  }
  FUN_0042a284(in_ECX,(uint *)&number,(byte *)number);
  *(char **)argument = number;
  return;
}



/* 004293f8 FUN_004293f8 */

undefined1 * __cdecl FUN_004293f8(undefined4 param_1,undefined1 *param_2,int param_3,int param_4)

{
  int *piVar1;
  undefined1 *puVar2;
  undefined1 *puVar3;
  uint *puVar4;
  int iVar5;
  
  piVar1 = DAT_0045530c;
  if (DAT_00455310 == '\0') {
    piVar1 = (int *)FUN_0042a328();
    FUN_0042a2b1(param_2 + (uint)(0 < param_3) + (uint)(*piVar1 == 0x2d),param_3 + 1,(int)piVar1);
  }
  else {
    FUN_00429710(param_2 + (*DAT_0045530c == 0x2d),(uint)(0 < param_3));
  }
  puVar2 = param_2;
  if (*piVar1 == 0x2d) {
    *param_2 = 0x2d;
    puVar2 = param_2 + 1;
  }
  puVar3 = puVar2;
  if (0 < param_3) {
    puVar3 = puVar2 + 1;
    *puVar2 = puVar2[1];
    *puVar3 = DAT_00430718;
  }
  puVar4 = FUN_0040f5e0((uint *)(puVar3 + param_3 + (uint)(DAT_00455310 == '\0')),(uint *)"e+000");
  if (param_4 != 0) {
    *(undefined1 *)puVar4 = 0x45;
  }
  if (*(char *)piVar1[3] != '0') {
    iVar5 = piVar1[1] + -1;
    if (iVar5 < 0) {
      iVar5 = -iVar5;
      *(undefined1 *)((int)puVar4 + 1) = 0x2d;
    }
    if (99 < iVar5) {
      *(char *)((int)puVar4 + 2) = *(char *)((int)puVar4 + 2) + (char)(iVar5 / 100);
      iVar5 = iVar5 % 100;
    }
    if (9 < iVar5) {
      *(char *)((int)puVar4 + 3) = *(char *)((int)puVar4 + 3) + (char)(iVar5 / 10);
      iVar5 = iVar5 % 10;
    }
    *(char *)(puVar4 + 1) = (char)puVar4[1] + (char)iVar5;
  }
  return param_2;
}



/* 004294fc FUN_004294fc */

char * __cdecl FUN_004294fc(undefined4 param_1,char *param_2,size_t param_3)

{
  int *piVar1;
  int iVar2;
  char *pcVar3;
  
  piVar1 = DAT_0045530c;
  if (DAT_00455310 == '\0') {
    piVar1 = (int *)FUN_0042a328();
    FUN_0042a2b1(param_2 + (*piVar1 == 0x2d),piVar1[1] + param_3,(int)piVar1);
  }
  else if (DAT_00455314 == param_3) {
    iVar2 = (*DAT_0045530c == 0x2d) + DAT_00455314;
    param_2[iVar2] = '0';
    (param_2 + iVar2)[1] = '\0';
  }
  pcVar3 = param_2;
  if (*piVar1 == 0x2d) {
    *param_2 = '-';
    pcVar3 = param_2 + 1;
  }
  if (piVar1[1] < 1) {
    FUN_00429710(pcVar3,1);
    *pcVar3 = '0';
    pcVar3 = pcVar3 + 1;
  }
  else {
    pcVar3 = pcVar3 + piVar1[1];
  }
  if (0 < (int)param_3) {
    FUN_00429710(pcVar3,1);
    *pcVar3 = DAT_00430718;
    iVar2 = piVar1[1];
    if (iVar2 < 0) {
      if ((DAT_00455310 != '\0') || (-iVar2 <= (int)param_3)) {
        param_3 = -iVar2;
      }
      FUN_00429710(pcVar3 + 1,param_3);
      _memset(pcVar3 + 1,0x30,param_3);
    }
  }
  return param_2;
}



/* 004295da FUN_004295da */

void __cdecl FUN_004295da(undefined4 param_1,char *param_2,size_t param_3,int param_4)

{
  int iVar1;
  char *pcVar2;
  char *pcVar3;
  
  DAT_0045530c = (int *)FUN_0042a328();
  DAT_00455314 = DAT_0045530c[1] + -1;
  iVar1 = *DAT_0045530c;
  FUN_0042a2b1(param_2 + (iVar1 == 0x2d),param_3,(int)DAT_0045530c);
  DAT_00455318 = DAT_00455314 < DAT_0045530c[1] + -1;
  DAT_00455314 = DAT_0045530c[1] + -1;
  if ((DAT_00455314 < -4) || ((int)param_3 <= DAT_00455314)) {
    FUN_00429675(param_1,param_2,param_3,param_4);
  }
  else {
    pcVar2 = param_2 + (iVar1 == 0x2d);
    if ((bool)DAT_00455318) {
      do {
        pcVar3 = pcVar2;
        pcVar2 = pcVar3 + 1;
      } while (*pcVar3 != '\0');
      pcVar3[-1] = '\0';
    }
    FUN_0042969c(param_1,param_2,param_3);
  }
  return;
}



/* 00429675 FUN_00429675 */

void __cdecl FUN_00429675(undefined4 param_1,undefined1 *param_2,int param_3,int param_4)

{
  DAT_00455310 = 1;
  FUN_004293f8(param_1,param_2,param_3,param_4);
  DAT_00455310 = 0;
  return;
}



/* 0042969c FUN_0042969c */

void __cdecl FUN_0042969c(undefined4 param_1,char *param_2,size_t param_3)

{
  DAT_00455310 = 1;
  FUN_004294fc(param_1,param_2,param_3);
  DAT_00455310 = 0;
  return;
}



/* 004296bf __cfltcvt */

/* Library Function - Single Match
    __cfltcvt
   
   Library: Visual Studio 2003 Release */

errno_t __cdecl
__cfltcvt(double *arg,char *buffer,size_t sizeInBytes,int format,int precision,int caps)

{
  char *pcVar1;
  undefined1 *puVar2;
  
  if ((sizeInBytes == 0x65) || (sizeInBytes == 0x45)) {
    puVar2 = FUN_004293f8(arg,buffer,format,precision);
  }
  else {
    if (sizeInBytes == 0x66) {
      pcVar1 = FUN_004294fc(arg,buffer,format);
      return (errno_t)pcVar1;
    }
    puVar2 = (undefined1 *)FUN_004295da(arg,buffer,format,precision);
  }
  return (errno_t)puVar2;
}



/* 00429710 FUN_00429710 */

void __cdecl FUN_00429710(char *param_1,int param_2)

{
  size_t sVar1;
  
  if (param_2 != 0) {
    sVar1 = _strlen(param_1);
    FUN_0040b550((undefined4 *)(param_1 + param_2),(undefined4 *)param_1,sVar1 + 1);
  }
  return;
}



/* 00429735 FUN_00429735 */

void FUN_00429735(void)

{
  if (DAT_004553d8 == 0) {
    FUN_0042974a();
    DAT_004553d8 = DAT_004553d8 + 1;
  }
  return;
}



/* 0042974a FUN_0042974a */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void FUN_0042974a(void)

{
  char cVar1;
  char cVar2;
  uint *_Str1;
  DWORD DVar3;
  int iVar4;
  size_t sVar5;
  void *this;
  uint *_Source;
  int local_4;
  
  DAT_00455320 = 0;
  DAT_00431418 = 0xffffffff;
  DAT_00431408 = 0xffffffff;
  _Str1 = (uint *)FUN_0040e308("TZ");
  if (_Str1 == (uint *)0x0) {
    DVar3 = GetTimeZoneInformation((LPTIME_ZONE_INFORMATION)&DAT_00455328);
    if (DVar3 == 0xffffffff) {
      return;
    }
    DAT_00431370 = (void *)(DAT_00455328 * 0x3c);
    DAT_00455320 = 1;
    if (DAT_0045536e != 0) {
      DAT_00431370 = (void *)((int)DAT_00431370 + DAT_0045537c * 0x3c);
    }
    if ((DAT_004553c2 == 0) || (DAT_004553d0 == 0)) {
      DAT_00431374 = 0;
      _DAT_00431378 = 0;
    }
    else {
      DAT_00431374 = 1;
      _DAT_00431378 = (DAT_004553d0 - DAT_0045537c) * 0x3c;
    }
    iVar4 = WideCharToMultiByte(DAT_00452fd4,0x220,(LPCWSTR)&DAT_0045532c,-1,PTR_DAT_004313fc,0x3f,
                                (LPCSTR)0x0,&local_4);
    if ((iVar4 == 0) || (local_4 != 0)) {
      *PTR_DAT_004313fc = 0;
    }
    else {
      PTR_DAT_004313fc[0x3f] = 0;
    }
    iVar4 = WideCharToMultiByte(DAT_00452fd4,0x220,(LPCWSTR)&DAT_00455380,-1,PTR_DAT_00431400,0x3f,
                                (LPCSTR)0x0,&local_4);
    if ((iVar4 != 0) && (local_4 == 0)) {
      PTR_DAT_00431400[0x3f] = 0;
      return;
    }
  }
  else {
    if ((char)*_Str1 == '\0') {
      return;
    }
    if ((DAT_004553d4 != (uint *)0x0) &&
       (iVar4 = _strcmp((char *)_Str1,(char *)DAT_004553d4), iVar4 == 0)) {
      return;
    }
    FUN_0040aa97(DAT_004553d4);
    sVar5 = _strlen((char *)_Str1);
    DAT_004553d4 = _malloc(sVar5 + 1);
    if (DAT_004553d4 == (uint *)0x0) {
      return;
    }
    FUN_0040f5e0(DAT_004553d4,_Str1);
    _strncpy(PTR_DAT_004313fc,(char *)_Str1,3);
    _Source = (uint *)((int)_Str1 + 3);
    PTR_DAT_004313fc[3] = 0;
    cVar1 = *(char *)_Source;
    if (cVar1 == '-') {
      _Source = _Str1 + 1;
    }
    iVar4 = FUN_0040a9d0(this,(byte *)_Source);
    DAT_00431370 = (void *)(iVar4 * 0xe10);
    for (; (cVar2 = (char)*_Source, cVar2 == '+' || (('/' < cVar2 && (cVar2 < ':'))));
        _Source = (uint *)((int)_Source + 1)) {
    }
    if ((char)*_Source == ':') {
      _Source = (uint *)((int)_Source + 1);
      iVar4 = FUN_0040a9d0(DAT_00431370,(byte *)_Source);
      DAT_00431370 = (void *)((int)DAT_00431370 + iVar4 * 0x3c);
      for (; ('/' < (char)*_Source && ((char)*_Source < ':')); _Source = (uint *)((int)_Source + 1))
      {
      }
      if ((char)*_Source == ':') {
        _Source = (uint *)((int)_Source + 1);
        iVar4 = FUN_0040a9d0(DAT_00431370,(byte *)_Source);
        DAT_00431370 = (void *)((int)DAT_00431370 + iVar4);
        for (; ('/' < (char)*_Source && ((char)*_Source < ':'));
            _Source = (uint *)((int)_Source + 1)) {
        }
      }
    }
    if (cVar1 == '-') {
      DAT_00431370 = (void *)-(int)DAT_00431370;
    }
    DAT_00431374 = (int)(char)*_Source;
    if (DAT_00431374 != 0) {
      _strncpy(PTR_DAT_00431400,(char *)_Source,3);
      PTR_DAT_00431400[3] = 0;
      return;
    }
  }
  *PTR_DAT_00431400 = 0;
  return;
}



/* 004299a8 FUN_004299a8 */

bool __cdecl FUN_004299a8(int *param_1)

{
  int iVar1;
  int iVar2;
  uint uVar3;
  uint uVar4;
  uint uVar5;
  uint uVar6;
  
  if (DAT_00431374 != 0) {
    uVar5 = param_1[5];
    if ((uVar5 != DAT_00431408) || (uVar5 != DAT_00431418)) {
      if (DAT_00455320 == 0) {
        FUN_00429b54(1,1,uVar5,4,1,0,0,2,0,0,0);
        FUN_00429b54(0,1,param_1[5],10,5,0,0,2,0,0,0);
      }
      else {
        if (DAT_004553c0 != 0) {
          uVar6 = (uint)DAT_004553c6;
          uVar3 = 0;
          uVar4 = 0;
        }
        else {
          uVar3 = (uint)DAT_004553c4;
          uVar6 = 0;
          uVar4 = (uint)DAT_004553c6;
        }
        FUN_00429b54(1,(uint)(DAT_004553c0 == 0),uVar5,(uint)DAT_004553c2,uVar4,uVar3,uVar6,
                     (uint)DAT_004553c8,(uint)DAT_004553ca,(uint)DAT_004553cc,(uint)DAT_004553ce);
        if (DAT_0045536c != 0) {
          uVar6 = (uint)DAT_00455372;
          uVar3 = 0;
          uVar4 = 0;
          uVar5 = param_1[5];
        }
        else {
          uVar3 = (uint)DAT_00455370;
          uVar6 = 0;
          uVar4 = (uint)DAT_00455372;
          uVar5 = param_1[5];
        }
        FUN_00429b54(0,(uint)(DAT_0045536c == 0),uVar5,(uint)DAT_0045536e,uVar4,uVar3,uVar6,
                     (uint)DAT_00455374,(uint)DAT_00455376,(uint)DAT_00455378,(uint)DAT_0045537a);
      }
    }
    iVar1 = param_1[7];
    if (DAT_0043140c < DAT_0043141c) {
      if ((DAT_0043140c <= iVar1) && (iVar1 <= DAT_0043141c)) {
        if ((DAT_0043140c < iVar1) && (iVar1 < DAT_0043141c)) {
          return true;
        }
LAB_00429b20:
        iVar2 = ((param_1[2] * 0x3c + param_1[1]) * 0x3c + *param_1) * 1000;
        if (iVar1 == DAT_0043140c) {
          return DAT_00431410 <= iVar2;
        }
        return iVar2 < DAT_00431420;
      }
    }
    else {
      if (iVar1 < DAT_0043141c) {
        return true;
      }
      if (DAT_0043140c < iVar1) {
        return true;
      }
      if ((iVar1 <= DAT_0043141c) || (DAT_0043140c <= iVar1)) goto LAB_00429b20;
    }
  }
  return false;
}



/* 00429b54 FUN_00429b54 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void __cdecl
FUN_00429b54(int param_1,int param_2,uint param_3,int param_4,int param_5,int param_6,int param_7,
            int param_8,int param_9,int param_10,int param_11)

{
  int iVar1;
  int iVar2;
  
  if (param_2 == 1) {
    if ((param_3 & 3) == 0) {
      iVar1 = (&DAT_00431420)[param_4];
    }
    else {
      iVar1 = *(int *)(&DAT_00431454 + param_4 * 4);
    }
    iVar2 = (int)(param_3 * 0x16d + -0x63db + iVar1 + 1 + ((int)(param_3 - 1) >> 2)) % 7;
    if (iVar2 < param_6) {
      iVar1 = iVar1 + -6 + (param_5 * 7 - iVar2) + param_6;
    }
    else {
      iVar1 = iVar1 + 1 + (param_5 * 7 - iVar2) + param_6;
    }
    if (param_5 == 5) {
      if ((param_3 & 3) == 0) {
        iVar2 = *(int *)(&DAT_00431424 + param_4 * 4);
      }
      else {
        iVar2 = *(int *)(&DAT_00431458 + param_4 * 4);
      }
      if (iVar2 < iVar1) {
        iVar1 = iVar1 + -7;
      }
    }
  }
  else {
    if ((param_3 & 3) == 0) {
      iVar1 = (&DAT_00431420)[param_4];
    }
    else {
      iVar1 = *(int *)(&DAT_00431454 + param_4 * 4);
    }
    iVar1 = iVar1 + param_7;
  }
  if (param_1 == 1) {
    DAT_00431408 = param_3;
    DAT_00431410 = ((param_8 * 0x3c + param_9) * 0x3c + param_10) * 1000 + param_11;
    DAT_0043140c = iVar1;
  }
  else {
    DAT_00431420 = ((param_8 * 0x3c + param_9) * 0x3c + _DAT_00431378 + param_10) * 1000 + param_11;
    if (DAT_00431420 < 0) {
      DAT_00431420 = DAT_00431420 + 86400000;
      DAT_0043141c = iVar1 + -1;
    }
    else {
      DAT_0043141c = iVar1;
      if (86399999 < DAT_00431420) {
        DAT_00431420 = DAT_00431420 + -86400000;
        DAT_0043141c = iVar1 + 1;
      }
    }
    DAT_00431418 = param_3;
  }
  return;
}



/* 00429c94 FUN_00429c94 */

uint __thiscall FUN_00429c94(void *this,uint param_1,uint param_2)

{
  uint uVar1;
  undefined2 in_FPUControlWord;
  undefined4 local_8;
  
  local_8 = CONCAT22((short)((uint)this >> 0x10),in_FPUControlWord);
  uVar1 = FUN_00429cdf(local_8);
  uVar1 = uVar1 & ~param_2 | param_1 & param_2;
  FUN_00429d71(uVar1);
  return uVar1;
}



/* 00429cc9 FUN_00429cc9 */

void __thiscall FUN_00429cc9(void *this,uint param_1,uint param_2)

{
  FUN_00429c94(this,param_1,param_2 & 0xfff7ffff);
  return;
}



/* 00429cdf FUN_00429cdf */

uint __cdecl FUN_00429cdf(uint param_1)

{
  uint uVar1;
  uint uVar2;
  
  uVar1 = 0;
  if ((param_1 & 1) != 0) {
    uVar1 = 0x10;
  }
  if ((param_1 & 4) != 0) {
    uVar1 = uVar1 | 8;
  }
  if ((param_1 & 8) != 0) {
    uVar1 = uVar1 | 4;
  }
  if ((param_1 & 0x10) != 0) {
    uVar1 = uVar1 | 2;
  }
  if ((param_1 & 0x20) != 0) {
    uVar1 = uVar1 | 1;
  }
  if ((param_1 & 2) != 0) {
    uVar1 = uVar1 | 0x80000;
  }
  uVar2 = param_1 & 0xc00;
  if (uVar2 != 0) {
    if (uVar2 == 0x400) {
      uVar1 = uVar1 | 0x100;
    }
    else if (uVar2 == 0x800) {
      uVar1 = uVar1 | 0x200;
    }
    else if (uVar2 == 0xc00) {
      uVar1 = uVar1 | 0x300;
    }
  }
  if ((param_1 & 0x300) == 0) {
    uVar1 = uVar1 | 0x20000;
  }
  else if ((param_1 & 0x300) == 0x200) {
    uVar1 = uVar1 | 0x10000;
  }
  if ((param_1 & 0x1000) != 0) {
    uVar1 = uVar1 | 0x40000;
  }
  return uVar1;
}



/* 00429d71 FUN_00429d71 */

uint __cdecl FUN_00429d71(uint param_1)

{
  uint uVar1;
  uint uVar2;
  
  uVar1 = (uint)((param_1 & 0x10) != 0);
  if ((param_1 & 8) != 0) {
    uVar1 = uVar1 | 4;
  }
  if ((param_1 & 4) != 0) {
    uVar1 = uVar1 | 8;
  }
  if ((param_1 & 2) != 0) {
    uVar1 = uVar1 | 0x10;
  }
  if ((param_1 & 1) != 0) {
    uVar1 = uVar1 | 0x20;
  }
  if ((param_1 & 0x80000) != 0) {
    uVar1 = uVar1 | 2;
  }
  uVar2 = param_1 & 0x300;
  if (uVar2 != 0) {
    if (uVar2 == 0x100) {
      uVar1 = uVar1 | 0x400;
    }
    else if (uVar2 == 0x200) {
      uVar1 = uVar1 | 0x800;
    }
    else if (uVar2 == 0x300) {
      uVar1 = uVar1 | 0xc00;
    }
  }
  if ((param_1 & 0x30000) == 0) {
    uVar1 = uVar1 | 0x300;
  }
  else if ((param_1 & 0x30000) == 0x10000) {
    uVar1 = uVar1 | 0x200;
  }
  if ((param_1 & 0x40000) != 0) {
    uVar1 = uVar1 | 0x1000;
  }
  return uVar1;
}



/* 00429dfa FUN_00429dfa */

uint __thiscall FUN_00429dfa(void *this,uint param_1)

{
  uint uVar1;
  uint uVar2;
  int iVar3;
  void *local_8;
  
  uVar1 = param_1;
  if (DAT_00452fc4 == 0) {
    if ((0x40 < (int)param_1) && ((int)param_1 < 0x5b)) {
      uVar1 = param_1 + 0x20;
    }
  }
  else {
    iVar3 = 1;
    local_8 = this;
    if ((int)param_1 < 0x100) {
      if (DAT_00430714 < 2) {
        uVar2 = (byte)PTR_DAT_00430508[param_1 * 2] & 1;
      }
      else {
        uVar2 = FUN_0040b9f6(this,param_1,1);
      }
      if (uVar2 == 0) {
        return uVar1;
      }
    }
    if ((PTR_DAT_00430508[((int)uVar1 >> 8 & 0xffU) * 2 + 1] & 0x80) == 0) {
      param_1 = CONCAT31((int3)(param_1 >> 8),(char)uVar1) & 0xffff00ff;
    }
    else {
      uVar2 = param_1 >> 0x10;
      param_1._0_2_ = CONCAT11((char)uVar1,(char)(uVar1 >> 8));
      param_1 = CONCAT22((short)uVar2,(undefined2)param_1) & 0xff00ffff;
      iVar3 = 2;
    }
    iVar3 = FUN_0041074a(DAT_00452fc4,0x100,(char *)&param_1,iVar3,(LPWSTR)&local_8,3,0,1);
    if (iVar3 != 0) {
      if (iVar3 == 1) {
        uVar1 = (uint)local_8 & 0xff;
      }
      else {
        uVar1 = (uint)local_8 & 0xffff;
      }
    }
  }
  return uVar1;
}



/* 00429ec5 FUN_00429ec5 */

undefined4 __cdecl FUN_00429ec5(int param_1,int param_2)

{
  int *piVar1;
  int iVar2;
  
  if ((*(uint *)(param_1 + (param_2 / 0x20) * 4) & ~(-1 << (0x1fU - (char)(param_2 % 0x20) & 0x1f)))
      != 0) {
    return 0;
  }
  iVar2 = param_2 / 0x20 + 1;
  if (iVar2 < 3) {
    piVar1 = (int *)(param_1 + iVar2 * 4);
    do {
      if (*piVar1 != 0) {
        return 0;
      }
      iVar2 = iVar2 + 1;
      piVar1 = piVar1 + 1;
    } while (iVar2 < 3);
  }
  return 1;
}



/* 00429f0e FUN_00429f0e */

void __cdecl FUN_00429f0e(int param_1,int param_2)

{
  int iVar1;
  int iVar2;
  uint *puVar3;
  
  puVar3 = (uint *)(param_1 + (param_2 / 0x20) * 4);
  iVar1 = FUN_0042a4d4(*puVar3,1 << (0x1fU - (char)(param_2 % 0x20) & 0x1f),puVar3);
  iVar2 = param_2 / 0x20 + -1;
  if (-1 < iVar2) {
    puVar3 = (uint *)(param_1 + iVar2 * 4);
    do {
      if (iVar1 == 0) {
        return;
      }
      iVar1 = FUN_0042a4d4(*puVar3,1,puVar3);
      iVar2 = iVar2 + -1;
      puVar3 = puVar3 + -1;
    } while (-1 < iVar2);
  }
  return;
}



/* 00429f64 FUN_00429f64 */

undefined4 __cdecl FUN_00429f64(int param_1,int param_2)

{
  uint *puVar1;
  int iVar2;
  byte bVar3;
  int iVar4;
  undefined4 *puVar5;
  undefined4 local_8;
  
  local_8 = 0;
  puVar1 = (uint *)(param_1 + (param_2 / 0x20) * 4);
  bVar3 = 0x1f - (char)(param_2 % 0x20);
  if (((*puVar1 & 1 << (bVar3 & 0x1f)) != 0) &&
     (iVar2 = FUN_00429ec5(param_1,param_2 + 1), iVar2 == 0)) {
    local_8 = FUN_00429f0e(param_1,param_2 + -1);
  }
  *puVar1 = *puVar1 & -1 << (bVar3 & 0x1f);
  iVar2 = param_2 / 0x20 + 1;
  if (iVar2 < 3) {
    puVar5 = (undefined4 *)(param_1 + iVar2 * 4);
    for (iVar4 = 3 - iVar2; iVar4 != 0; iVar4 = iVar4 + -1) {
      *puVar5 = 0;
      puVar5 = puVar5 + 1;
    }
  }
  return local_8;
}



/* 00429ff0 FUN_00429ff0 */

void __cdecl FUN_00429ff0(int param_1,undefined4 *param_2)

{
  int iVar1;
  int iVar2;
  
  iVar1 = param_1 - (int)param_2;
  iVar2 = 3;
  do {
    *(undefined4 *)(iVar1 + (int)param_2) = *param_2;
    param_2 = param_2 + 1;
    iVar2 = iVar2 + -1;
  } while (iVar2 != 0);
  return;
}



/* 0042a00b FUN_0042a00b */

void __cdecl FUN_0042a00b(undefined4 *param_1)

{
  *param_1 = 0;
  param_1[1] = 0;
  param_1[2] = 0;
  return;
}



/* 0042a017 FUN_0042a017 */

undefined4 __cdecl FUN_0042a017(int *param_1)

{
  int iVar1;
  
  iVar1 = 0;
  do {
    if (*param_1 != 0) {
      return 0;
    }
    iVar1 = iVar1 + 1;
    param_1 = param_1 + 1;
  } while (iVar1 < 3);
  return 1;
}



/* 0042a032 FUN_0042a032 */

void __cdecl FUN_0042a032(uint *param_1,uint param_2)

{
  uint uVar1;
  int iVar2;
  byte bVar3;
  int iVar4;
  int iVar5;
  uint *puVar6;
  int local_8;
  
  local_8 = 3;
  iVar2 = (int)param_2 / 0x20;
  iVar5 = (int)param_2 % 0x20;
  param_2 = 0;
  bVar3 = (byte)iVar5;
  puVar6 = param_1;
  do {
    uVar1 = *puVar6;
    *puVar6 = uVar1 >> (bVar3 & 0x1f) | param_2;
    puVar6 = puVar6 + 1;
    param_2 = (uVar1 & ~(-1 << (bVar3 & 0x1f))) << (0x20 - bVar3 & 0x1f);
    local_8 = local_8 + -1;
  } while (local_8 != 0);
  iVar5 = 2;
  iVar4 = 8;
  do {
    if (iVar5 < iVar2) {
      *(undefined4 *)(iVar4 + (int)param_1) = 0;
    }
    else {
      *(undefined4 *)(iVar4 + (int)param_1) = *(undefined4 *)(iVar4 + iVar2 * -4 + (int)param_1);
    }
    iVar5 = iVar5 + -1;
    iVar4 = iVar4 + -4;
  } while (-1 < iVar4);
  return;
}



/* 0042a0bf FUN_0042a0bf */

undefined4 __cdecl FUN_0042a0bf(ushort *param_1,uint *param_2,int *param_3)

{
  ushort uVar1;
  int iVar2;
  uint uVar3;
  int iVar4;
  undefined4 uVar5;
  undefined4 local_1c [3];
  uint local_10;
  uint local_c;
  int local_8;
  
  uVar1 = param_1[5];
  local_10 = *(uint *)(param_1 + 3);
  local_c = *(uint *)(param_1 + 1);
  uVar3 = uVar1 & 0x7fff;
  iVar4 = uVar3 - 0x3fff;
  local_8 = (uint)*param_1 << 0x10;
  if (iVar4 == -0x3fff) {
    iVar4 = 0;
    iVar2 = FUN_0042a017((int *)&local_10);
    if (iVar2 != 0) {
LAB_0042a1eb:
      uVar5 = 0;
      goto LAB_0042a1ed;
    }
    FUN_0042a00b(&local_10);
  }
  else {
    FUN_00429ff0((int)local_1c,&local_10);
    iVar2 = FUN_00429f64((int)&local_10,param_3[2]);
    if (iVar2 != 0) {
      iVar4 = uVar3 - 0x3ffe;
    }
    iVar2 = param_3[1];
    if (iVar4 < iVar2 - param_3[2]) {
      FUN_0042a00b(&local_10);
    }
    else {
      if (iVar2 < iVar4) {
        if (*param_3 <= iVar4) {
          FUN_0042a00b(&local_10);
          local_10 = local_10 | 0x80000000;
          FUN_0042a032(&local_10,param_3[3]);
          iVar4 = param_3[5] + *param_3;
          uVar5 = 1;
          goto LAB_0042a1ed;
        }
        local_10 = local_10 & 0x7fffffff;
        iVar4 = param_3[5] + iVar4;
        FUN_0042a032(&local_10,param_3[3]);
        goto LAB_0042a1eb;
      }
      FUN_00429ff0((int)&local_10,local_1c);
      FUN_0042a032(&local_10,iVar2 - iVar4);
      FUN_00429f64((int)&local_10,param_3[2]);
      FUN_0042a032(&local_10,param_3[3] + 1);
    }
  }
  iVar4 = 0;
  uVar5 = 2;
LAB_0042a1ed:
  local_10 = iVar4 << (0x1fU - (char)param_3[3] & 0x1f) |
             -(uint)((uVar1 & 0x8000) != 0) & 0x80000000 | local_10;
  if (param_3[4] == 0x40) {
    param_2[1] = local_10;
    *param_2 = local_c;
  }
  else if (param_3[4] == 0x20) {
    *param_2 = local_10;
  }
  return uVar5;
}



/* 0042a22b FUN_0042a22b */

void __cdecl FUN_0042a22b(ushort *param_1,uint *param_2)

{
  FUN_0042a0bf(param_1,param_2,(int *)&DAT_00431490);
  return;
}



/* 0042a241 FUN_0042a241 */

void __cdecl FUN_0042a241(ushort *param_1,uint *param_2)

{
  FUN_0042a0bf(param_1,param_2,(int *)&DAT_004314a8);
  return;
}



/* 0042a257 FUN_0042a257 */

void __thiscall FUN_0042a257(void *this,uint *param_1,byte *param_2)

{
  ushort local_10 [6];
  
  FUN_0042a675(this,local_10,(int *)&param_2,param_2,0,0,0,0);
  FUN_0042a22b(local_10,param_1);
  return;
}



/* 0042a284 FUN_0042a284 */

void __thiscall FUN_0042a284(void *this,uint *param_1,byte *param_2)

{
  ushort local_10 [6];
  
  FUN_0042a675(this,local_10,(int *)&param_2,param_2,0,0,0,0);
  FUN_0042a241(local_10,param_1);
  return;
}



/* 0042a2b1 FUN_0042a2b1 */

void __cdecl FUN_0042a2b1(char *param_1,int param_2,int param_3)

{
  char *_Str;
  char *pcVar1;
  char *pcVar2;
  size_t sVar3;
  char *pcVar4;
  char cVar5;
  
  pcVar1 = param_1;
  pcVar4 = *(char **)(param_3 + 0xc);
  _Str = param_1 + 1;
  *param_1 = '0';
  pcVar2 = _Str;
  if (0 < param_2) {
    param_1 = (char *)param_2;
    param_2 = 0;
    do {
      cVar5 = *pcVar4;
      if (cVar5 == '\0') {
        cVar5 = '0';
      }
      else {
        pcVar4 = pcVar4 + 1;
      }
      *pcVar2 = cVar5;
      pcVar2 = pcVar2 + 1;
      param_1 = param_1 + -1;
    } while (param_1 != (char *)0x0);
  }
  *pcVar2 = '\0';
  if ((-1 < param_2) && ('4' < *pcVar4)) {
    while (pcVar2 = pcVar2 + -1, *pcVar2 == '9') {
      *pcVar2 = '0';
    }
    *pcVar2 = *pcVar2 + '\x01';
  }
  if (*pcVar1 == '1') {
    *(int *)(param_3 + 4) = *(int *)(param_3 + 4) + 1;
  }
  else {
    sVar3 = _strlen(_Str);
    FUN_0040b550((undefined4 *)pcVar1,(undefined4 *)_Str,sVar3 + 1);
  }
  return;
}



/* 0042a328 FUN_0042a328 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

undefined * FUN_0042a328(void)

{
  undefined4 in_stack_ffffffd8;
  undefined2 uVar1;
  uint local_10;
  uint uStack_c;
  undefined2 uStack_8;
  
  uVar1 = (undefined2)((uint)in_stack_ffffffd8 >> 0x10);
  FUN_0042a38c(&local_10,(uint *)&stack0x00000004);
  _DAT_00455408 = FUN_0042ab46(local_10,uStack_c,CONCAT22(uVar1,uStack_8),0x11,0,&DAT_004553e0);
  _DAT_00455400 = (int)DAT_004553e2;
  _DAT_00455404 = (int)DAT_004553e0;
  _DAT_0045540c = &DAT_004553e4;
  return &DAT_00455400;
}



/* 0042a38c FUN_0042a38c */

void __cdecl FUN_0042a38c(uint *param_1,uint *param_2)

{
  ushort uVar1;
  uint uVar2;
  uint uVar3;
  int iVar4;
  uint local_8;
  
  uVar1 = *(ushort *)((int)param_2 + 6);
  uVar3 = (uVar1 & 0x7ff0) >> 4;
  uVar2 = *param_2;
  local_8 = 0x80000000;
  if (uVar3 == 0) {
    if (((param_2[1] & 0xfffff) == 0) && (uVar2 == 0)) {
      param_1[1] = 0;
      *param_1 = 0;
      *(undefined2 *)(param_1 + 2) = 0;
      return;
    }
    iVar4 = 0x3c01;
    local_8 = 0;
  }
  else if (uVar3 == 0x7ff) {
    iVar4 = 0x7fff;
  }
  else {
    iVar4 = uVar3 + 0x3c00;
  }
  local_8 = uVar2 >> 0x15 | (param_2[1] & 0xfffff) << 0xb | local_8;
  param_1[1] = local_8;
  *param_1 = uVar2 << 0xb;
  while ((local_8 & 0x80000000) == 0) {
    local_8 = *param_1 >> 0x1f | local_8 * 2;
    *param_1 = *param_1 * 2;
    param_1[1] = local_8;
    iVar4 = iVar4 + 0xffff;
  }
  *(ushort *)(param_1 + 2) = uVar1 & 0x8000 | (ushort)iVar4;
  return;
}



/* 0042a450 _strcmp */

/* Library Function - Single Match
    _strcmp
   
   Libraries: Visual Studio 1998 Debug, Visual Studio 1998 Release */

int __cdecl _strcmp(char *_Str1,char *_Str2)

{
  undefined2 uVar1;
  undefined4 uVar2;
  byte bVar3;
  byte bVar4;
  bool bVar5;
  
  if (((uint)_Str1 & 3) != 0) {
    if (((uint)_Str1 & 1) != 0) {
      bVar4 = *_Str1;
      _Str1 = _Str1 + 1;
      bVar5 = bVar4 < (byte)*_Str2;
      if (bVar4 != *_Str2) goto LAB_0042a494;
      _Str2 = _Str2 + 1;
      if (bVar4 == 0) {
        return 0;
      }
      if (((uint)_Str1 & 2) == 0) goto LAB_0042a460;
    }
    uVar1 = *(undefined2 *)_Str1;
    _Str1 = _Str1 + 2;
    bVar4 = (byte)uVar1;
    bVar5 = bVar4 < (byte)*_Str2;
    if (bVar4 != *_Str2) goto LAB_0042a494;
    if (bVar4 == 0) {
      return 0;
    }
    bVar4 = (byte)((ushort)uVar1 >> 8);
    bVar5 = bVar4 < (byte)_Str2[1];
    if (bVar4 != _Str2[1]) goto LAB_0042a494;
    if (bVar4 == 0) {
      return 0;
    }
    _Str2 = _Str2 + 2;
  }
LAB_0042a460:
  while( true ) {
    uVar2 = *(undefined4 *)_Str1;
    bVar4 = (byte)uVar2;
    bVar5 = bVar4 < (byte)*_Str2;
    if (bVar4 != *_Str2) break;
    if (bVar4 == 0) {
      return 0;
    }
    bVar4 = (byte)((uint)uVar2 >> 8);
    bVar5 = bVar4 < (byte)_Str2[1];
    if (bVar4 != _Str2[1]) break;
    if (bVar4 == 0) {
      return 0;
    }
    bVar4 = (byte)((uint)uVar2 >> 0x10);
    bVar5 = bVar4 < (byte)_Str2[2];
    if (bVar4 != _Str2[2]) break;
    bVar3 = (byte)((uint)uVar2 >> 0x18);
    if (bVar4 == 0) {
      return 0;
    }
    bVar5 = bVar3 < (byte)_Str2[3];
    if (bVar3 != _Str2[3]) break;
    _Str2 = _Str2 + 4;
    _Str1 = _Str1 + 4;
    if (bVar3 == 0) {
      return 0;
    }
  }
LAB_0042a494:
  return (uint)bVar5 * -2 + 1;
}



/* 0042a4d4 FUN_0042a4d4 */

undefined4 __cdecl FUN_0042a4d4(uint param_1,uint param_2,uint *param_3)

{
  uint uVar1;
  undefined4 uVar2;
  
  uVar2 = 0;
  uVar1 = param_1 + param_2;
  if ((uVar1 < param_1) || (uVar1 < param_2)) {
    uVar2 = 1;
  }
  *param_3 = uVar1;
  return uVar2;
}



/* 0042a4f5 ___add_12 */

/* Library Function - Single Match
    ___add_12
   
   Library: Visual Studio 2003 Release */

void __cdecl ___add_12(uint *param_1,uint *param_2)

{
  int iVar1;
  
  iVar1 = FUN_0042a4d4(*param_1,*param_2,param_1);
  if (iVar1 != 0) {
    iVar1 = FUN_0042a4d4(param_1[1],1,param_1 + 1);
    if (iVar1 != 0) {
      param_1[2] = param_1[2] + 1;
    }
  }
  iVar1 = FUN_0042a4d4(param_1[1],param_2[1],param_1 + 1);
  if (iVar1 != 0) {
    param_1[2] = param_1[2] + 1;
  }
  FUN_0042a4d4(param_1[2],param_2[2],param_1 + 2);
  return;
}



/* 0042a553 FUN_0042a553 */

void __cdecl FUN_0042a553(uint *param_1)

{
  uint uVar1;
  uint uVar2;
  
  uVar1 = *param_1;
  uVar2 = param_1[1];
  *param_1 = uVar1 * 2;
  param_1[1] = uVar2 * 2 | uVar1 >> 0x1f;
  param_1[2] = param_1[2] << 1 | uVar2 >> 0x1f;
  return;
}



/* 0042a581 FUN_0042a581 */

void __cdecl FUN_0042a581(uint *param_1)

{
  uint uVar1;
  
  uVar1 = param_1[1];
  param_1[1] = uVar1 >> 1 | param_1[2] << 0x1f;
  param_1[2] = param_1[2] >> 1;
  *param_1 = *param_1 >> 1 | uVar1 << 0x1f;
  return;
}



/* 0042a5ae FUN_0042a5ae */

void __cdecl FUN_0042a5ae(char *param_1,int param_2,uint *param_3)

{
  uint *puVar1;
  uint local_14;
  uint local_10;
  uint local_c;
  int local_8;
  
  puVar1 = param_3;
  local_8 = 0x404e;
  *param_3 = 0;
  param_3[1] = 0;
  param_3[2] = 0;
  if (param_2 != 0) {
    param_3 = (uint *)param_2;
    do {
      local_14 = *puVar1;
      local_10 = puVar1[1];
      local_c = puVar1[2];
      FUN_0042a553(puVar1);
      FUN_0042a553(puVar1);
      ___add_12(puVar1,&local_14);
      FUN_0042a553(puVar1);
      local_10 = 0;
      local_c = 0;
      local_14 = (uint)*param_1;
      ___add_12(puVar1,&local_14);
      param_1 = param_1 + 1;
      param_3 = (uint *)((int)param_3 + -1);
    } while (param_3 != (uint *)0x0);
  }
  while (puVar1[2] == 0) {
    puVar1[2] = puVar1[1] >> 0x10;
    local_8 = local_8 + 0xfff0;
    puVar1[1] = *puVar1 >> 0x10 | puVar1[1] << 0x10;
    *puVar1 = *puVar1 << 0x10;
  }
  while ((puVar1[2] & 0x8000) == 0) {
    FUN_0042a553(puVar1);
    local_8 = local_8 + 0xffff;
  }
  *(undefined2 *)((int)puVar1 + 10) = (undefined2)local_8;
  return;
}



/* 0042a675 FUN_0042a675 */

undefined4 __thiscall
FUN_0042a675(void *this,ushort *param_1,int *param_2,byte *param_3,int param_4,int param_5,
            int param_6,int param_7)

{
  int iVar1;
  int iVar2;
  uint uVar3;
  char *pcVar4;
  int iVar5;
  byte bVar6;
  byte *pbVar7;
  byte *pbVar8;
  int iVar9;
  byte *pbVar10;
  char local_60 [23];
  char local_49;
  ushort local_44;
  undefined2 uStack_42;
  undefined2 uStack_40;
  byte *local_3e;
  ushort local_3a;
  int local_34;
  int local_30;
  undefined4 local_2c;
  int local_28;
  int local_24;
  byte *local_20;
  int local_1c;
  undefined4 local_18;
  int local_14;
  char *local_10;
  int local_c;
  uint local_8;
  
  local_10 = local_60;
  local_2c = 0;
  local_1c = 1;
  local_8 = 0;
  local_14 = 0;
  local_28 = 0;
  local_24 = 0;
  local_30 = 0;
  local_34 = 0;
  local_20 = (byte *)0x0;
  local_c = 0;
  local_18 = 0;
  pbVar8 = param_3;
  while( true ) {
    bVar6 = *pbVar8;
    this = (void *)CONCAT31((int3)((uint)this >> 8),bVar6);
    if ((((bVar6 != 0x20) && (bVar6 != 9)) && (bVar6 != 10)) && (bVar6 != 0xd)) break;
    pbVar8 = pbVar8 + 1;
  }
  iVar1 = 4;
  iVar9 = 0;
  iVar5 = local_14;
LAB_0042a6cc:
  local_14 = iVar5;
  pbVar7 = pbVar8;
  iVar5 = 1;
  bVar6 = *pbVar7;
  pbVar8 = pbVar7 + 1;
  iVar2 = local_14;
  switch(iVar9) {
  case 0:
    if (('0' < (char)bVar6) && ((char)bVar6 < ':')) {
LAB_0042a6e9:
      local_14 = iVar2;
      iVar9 = 3;
      goto LAB_0042a90e;
    }
    if (bVar6 == DAT_00430718) goto LAB_0042a6f8;
    if (bVar6 == 0x2b) {
      local_2c = 0;
      iVar9 = 2;
      iVar5 = local_14;
    }
    else if (bVar6 == 0x2d) {
      local_2c = 0x8000;
      iVar9 = 2;
      iVar5 = local_14;
    }
    else {
      iVar9 = iVar5;
      iVar5 = local_14;
      if (bVar6 != 0x30) goto LAB_0042a9e8;
    }
    goto LAB_0042a6cc;
  case 1:
    local_14 = 1;
    if (('0' < (char)bVar6) && (iVar2 = iVar5, (char)bVar6 < ':')) goto LAB_0042a6e9;
    iVar9 = iVar1;
    if (bVar6 != DAT_00430718) {
      iVar9 = iVar5;
      if ((bVar6 == 0x2b) || (iVar9 = local_14, bVar6 == 0x2d)) goto LAB_0042a77d;
      iVar9 = iVar5;
      local_14 = iVar5;
      if (bVar6 != 0x30) goto LAB_0042a756;
    }
    goto LAB_0042a6cc;
  case 2:
    if (('0' < (char)bVar6) && ((char)bVar6 < ':')) goto LAB_0042a6e9;
    if (bVar6 == DAT_00430718) {
LAB_0042a6f8:
      iVar9 = 5;
      iVar5 = local_14;
    }
    else {
      iVar9 = iVar5;
      pbVar7 = param_3;
      iVar5 = local_14;
      if (bVar6 != 0x30) goto LAB_0042a9ed;
    }
    goto LAB_0042a6cc;
  case 3:
    local_14 = iVar5;
    while( true ) {
      if (DAT_00430714 < 2) {
        uVar3 = (byte)PTR_DAT_00430508[(uint)bVar6 * 2] & 4;
        this = PTR_DAT_00430508;
      }
      else {
        pbVar7 = (byte *)0x4;
        uVar3 = FUN_0040b9f6(this,(uint)bVar6,4);
        this = pbVar7;
      }
      if (uVar3 == 0) break;
      if (local_8 < 0x19) {
        local_8 = local_8 + 1;
        pcVar4 = local_10 + 1;
        *local_10 = bVar6 - 0x30;
        local_10 = pcVar4;
      }
      else {
        local_c = local_c + 1;
      }
      bVar6 = *pbVar8;
      pbVar8 = pbVar8 + 1;
    }
    iVar9 = iVar1;
    iVar5 = local_14;
    if (bVar6 != DAT_00430718) goto LAB_0042a86a;
    goto LAB_0042a6cc;
  case 4:
    local_14 = 1;
    local_28 = 1;
    iVar9 = iVar5;
    if (local_8 == 0) {
      while (iVar5 = local_28, iVar9 = local_14, bVar6 == 0x30) {
        local_c = local_c + -1;
        bVar6 = *pbVar8;
        pbVar8 = pbVar8 + 1;
      }
    }
    while( true ) {
      local_14 = iVar9;
      local_28 = iVar5;
      if (DAT_00430714 < 2) {
        uVar3 = (byte)PTR_DAT_00430508[(uint)bVar6 * 2] & 4;
        this = PTR_DAT_00430508;
      }
      else {
        pbVar7 = (byte *)0x4;
        uVar3 = FUN_0040b9f6(this,(uint)bVar6,4);
        this = pbVar7;
      }
      if (uVar3 == 0) break;
      if (local_8 < 0x19) {
        local_8 = local_8 + 1;
        local_c = local_c + -1;
        pcVar4 = local_10 + 1;
        *local_10 = bVar6 - 0x30;
        local_10 = pcVar4;
      }
      bVar6 = *pbVar8;
      pbVar8 = pbVar8 + 1;
      iVar5 = local_28;
      iVar9 = local_14;
    }
LAB_0042a86a:
    iVar9 = local_14;
    if ((bVar6 == 0x2b) || (bVar6 == 0x2d)) {
LAB_0042a77d:
      local_14 = iVar9;
      iVar9 = 0xb;
      pbVar8 = pbVar8 + -1;
      iVar5 = local_14;
    }
    else {
LAB_0042a756:
      if (((char)bVar6 < 'D') ||
         (('E' < (char)bVar6 && (((char)bVar6 < 'd' || ('e' < (char)bVar6)))))) goto LAB_0042a9e8;
      iVar9 = 6;
      iVar5 = local_14;
    }
    goto LAB_0042a6cc;
  case 5:
    local_28 = iVar5;
    if (DAT_00430714 < 2) {
      uVar3 = (byte)PTR_DAT_00430508[(uint)bVar6 * 2] & 4;
      this = PTR_DAT_00430508;
    }
    else {
      pbVar7 = (byte *)0x4;
      uVar3 = FUN_0040b9f6(this,(uint)bVar6,4);
      this = pbVar7;
    }
    iVar9 = iVar1;
    pbVar7 = param_3;
    if (uVar3 != 0) goto LAB_0042a90e;
    goto LAB_0042a9ed;
  case 6:
    pbVar7 = pbVar7 + -1;
    this = pbVar7;
    param_3 = pbVar7;
    if (((char)bVar6 < '1') || ('9' < (char)bVar6)) {
      if (bVar6 == 0x2b) goto LAB_0042a943;
      if (bVar6 == 0x2d) goto LAB_0042a937;
      if (bVar6 != 0x30) goto LAB_0042a9ed;
LAB_0042a8dc:
      iVar9 = 8;
      iVar5 = local_14;
      goto LAB_0042a6cc;
    }
    break;
  case 7:
    if (((char)bVar6 < '1') || ('9' < (char)bVar6)) {
      pbVar7 = param_3;
      if (bVar6 == 0x30) goto LAB_0042a8dc;
      goto LAB_0042a9ed;
    }
    break;
  case 8:
    local_24 = 1;
    while (bVar6 == 0x30) {
      bVar6 = *pbVar8;
      pbVar8 = pbVar8 + 1;
    }
    if (((char)bVar6 < '1') || ('9' < (char)bVar6)) goto LAB_0042a9e8;
    break;
  case 9:
    local_24 = 1;
    pbVar7 = (byte *)0x0;
    goto LAB_0042a96e;
  default:
    goto switchD_0042a6d8_caseD_a;
  case 0xb:
    if (param_7 != 0) {
      if (bVar6 == 0x2b) {
LAB_0042a943:
        iVar9 = 7;
        this = pbVar7;
        param_3 = pbVar7;
        iVar5 = local_14;
      }
      else {
        param_3 = pbVar7;
        if (bVar6 != 0x2d) goto LAB_0042a9ed;
LAB_0042a937:
        local_1c = -1;
        iVar9 = 7;
        this = pbVar7;
        param_3 = pbVar7;
        iVar5 = local_14;
      }
      goto LAB_0042a6cc;
    }
    iVar9 = 10;
    pbVar8 = pbVar7;
switchD_0042a6d8_caseD_a:
    pbVar7 = pbVar8;
    iVar5 = local_14;
    if (iVar9 != 10) goto LAB_0042a6cc;
    goto LAB_0042a9ed;
  }
  iVar9 = 9;
LAB_0042a90e:
  pbVar8 = pbVar8 + -1;
  iVar5 = local_14;
  goto LAB_0042a6cc;
LAB_0042a96e:
  if (DAT_00430714 < 2) {
    uVar3 = (byte)PTR_DAT_00430508[(uint)bVar6 * 2] & 4;
    this = PTR_DAT_00430508;
  }
  else {
    pbVar10 = (byte *)0x4;
    uVar3 = FUN_0040b9f6(this,(uint)bVar6,4);
    this = pbVar10;
  }
  if (uVar3 == 0) goto LAB_0042a9b8;
  this = (void *)(int)(char)bVar6;
  pbVar7 = (byte *)((int)this + (int)pbVar7 * 10 + -0x30);
  if (0x1450 < (int)pbVar7) goto LAB_0042a9b0;
  bVar6 = *pbVar8;
  pbVar8 = pbVar8 + 1;
  goto LAB_0042a96e;
LAB_0042a9b0:
  pbVar7 = (byte *)0x1451;
LAB_0042a9b8:
  while( true ) {
    local_20 = pbVar7;
    if (DAT_00430714 < 2) {
      uVar3 = (byte)PTR_DAT_00430508[(uint)bVar6 * 2] & 4;
      this = PTR_DAT_00430508;
    }
    else {
      pbVar7 = (byte *)0x4;
      uVar3 = FUN_0040b9f6(this,(uint)bVar6,4);
      this = pbVar7;
    }
    if (uVar3 == 0) break;
    bVar6 = *pbVar8;
    pbVar8 = pbVar8 + 1;
    pbVar7 = local_20;
  }
LAB_0042a9e8:
  pbVar7 = pbVar8 + -1;
LAB_0042a9ed:
  *param_2 = (int)pbVar7;
  if (local_14 == 0) {
    local_44 = 0;
    local_3a = 0;
    local_3e = (byte *)0x0;
    param_3 = (byte *)0x0;
    local_18 = 4;
    goto LAB_0042aafb;
  }
  pcVar4 = local_10;
  if (0x18 < local_8) {
    if ('\x04' < local_49) {
      local_49 = local_49 + '\x01';
    }
    local_8 = 0x18;
    local_c = local_c + 1;
    pcVar4 = local_10 + -1;
  }
  if (local_8 == 0) {
    local_44 = 0;
    local_3a = 0;
    local_3e = (byte *)0x0;
    param_3 = (byte *)0x0;
  }
  else {
    while (pcVar4 = pcVar4 + -1, *pcVar4 == '\0') {
      local_8 = local_8 - 1;
      local_c = local_c + 1;
    }
    FUN_0042a5ae(local_60,local_8,(uint *)&local_44);
    pbVar8 = local_20;
    if (local_1c < 0) {
      pbVar8 = (byte *)-(int)local_20;
    }
    pbVar8 = pbVar8 + local_c;
    if (local_24 == 0) {
      pbVar8 = pbVar8 + param_5;
    }
    if (local_28 == 0) {
      pbVar8 = pbVar8 + -param_6;
    }
    if ((int)pbVar8 < 0x1451) {
      if (-0x1451 < (int)pbVar8) {
        FUN_0042aff9((int *)&local_44,(uint)pbVar8,param_4);
        param_3 = (byte *)CONCAT22(uStack_40,uStack_42);
        goto LAB_0042aa80;
      }
      local_34 = 1;
    }
    else {
      local_30 = 1;
    }
    local_3a = (ushort)param_3;
    local_3e = param_3;
    local_44 = local_3a;
  }
LAB_0042aa80:
  if (local_30 == 0) {
    if (local_34 != 0) {
      local_44 = 0;
      local_3a = 0;
      local_3e = (byte *)0x0;
      param_3 = (byte *)0x0;
      local_18 = 1;
    }
  }
  else {
    param_3 = (byte *)0x0;
    local_3a = 0x7fff;
    local_3e = (byte *)0x80000000;
    local_44 = 0;
    local_18 = 2;
  }
LAB_0042aafb:
  *(byte **)(param_1 + 3) = local_3e;
  *(byte **)(param_1 + 1) = param_3;
  param_1[5] = local_3a | (ushort)local_2c;
  *param_1 = local_44;
  return local_18;
}



/* 0042ab46 FUN_0042ab46 */

undefined4 __cdecl
FUN_0042ab46(uint param_1,uint param_2,uint param_3,int param_4,byte param_5,short *param_6)

{
  short *psVar1;
  uint uVar2;
  short *psVar3;
  char cVar4;
  uint uVar5;
  short *psVar6;
  short *psVar7;
  short sVar8;
  int iVar9;
  int iVar10;
  char *pcVar11;
  undefined1 local_20;
  undefined1 local_1f;
  undefined1 local_1e;
  undefined1 local_1d;
  undefined1 local_1c;
  undefined1 local_1b;
  undefined1 local_1a;
  undefined1 local_19;
  undefined1 local_18;
  undefined1 local_17;
  undefined1 local_16;
  undefined1 local_15;
  undefined2 local_14;
  undefined4 local_12;
  undefined4 local_e;
  undefined1 local_a;
  char cStack_9;
  undefined4 local_8;
  
  psVar3 = param_6;
  uVar5 = param_3 & 0x7fff;
  local_20 = 0xcc;
  local_1f = 0xcc;
  local_1e = 0xcc;
  local_1d = 0xcc;
  local_1c = 0xcc;
  local_1b = 0xcc;
  local_1a = 0xcc;
  local_19 = 0xcc;
  local_18 = 0xcc;
  local_17 = 0xcc;
  local_16 = 0xfb;
  local_15 = 0x3f;
  local_8 = 1;
  if ((param_3 & 0x8000) == 0) {
    *(undefined1 *)(param_6 + 1) = 0x20;
  }
  else {
    *(undefined1 *)(param_6 + 1) = 0x2d;
  }
  if ((((short)uVar5 != 0) || (param_2 != 0)) || (param_1 != 0)) {
    if ((short)uVar5 == 0x7fff) {
      *param_6 = 1;
      if (((param_2 == 0x80000000) && (param_1 == 0)) || ((param_2 & 0x40000000) != 0)) {
        if (((param_3 & 0x8000) == 0) || (param_2 != 0xc0000000)) {
          if ((param_2 != 0x80000000) || (param_1 != 0)) goto LAB_0042ac3b;
          pcVar11 = "1#INF";
        }
        else {
          if (param_1 != 0) {
LAB_0042ac3b:
            pcVar11 = "1#QNAN";
            goto LAB_0042ac40;
          }
          pcVar11 = "1#IND";
        }
        FUN_0040f5e0((uint *)(param_6 + 2),(uint *)pcVar11);
        *(undefined1 *)((int)psVar3 + 3) = 5;
      }
      else {
        pcVar11 = "1#SNAN";
LAB_0042ac40:
        FUN_0040f5e0((uint *)(param_6 + 2),(uint *)pcVar11);
        *(undefined1 *)((int)psVar3 + 3) = 6;
      }
      return 0;
    }
    local_14 = 0;
    local_a = (undefined1)uVar5;
    cStack_9 = (char)(uVar5 >> 8);
    sVar8 = (short)(((uVar5 >> 8) + (param_2 >> 0x18) * 2) * 0x4d + -0x134312f4 + uVar5 * 0x4d10 >>
                   0x10);
    local_e = param_2;
    local_12 = param_1;
    FUN_0042aff9((int *)&local_14,-(int)sVar8,1);
    if (0x3ffe < CONCAT11(cStack_9,local_a)) {
      sVar8 = sVar8 + 1;
      FUN_0042add9((int *)&local_14,(int *)&local_20);
    }
    *psVar3 = sVar8;
    iVar10 = param_4;
    if (((param_5 & 1) == 0) || (iVar10 = param_4 + sVar8, 0 < param_4 + sVar8)) {
      if (0x15 < iVar10) {
        iVar10 = 0x15;
      }
      iVar9 = CONCAT11(cStack_9,local_a) - 0x3ffe;
      local_a = 0;
      cStack_9 = '\0';
      param_6 = (short *)0x8;
      do {
        FUN_0042a553((uint *)&local_14);
        param_6 = (short *)((int)param_6 + -1);
      } while (param_6 != (short *)0x0);
      if (iVar9 < 0) {
        param_6 = (short *)0x0;
        for (uVar5 = -iVar9 & 0xff; uVar5 != 0; uVar5 = uVar5 - 1) {
          FUN_0042a581((uint *)&local_14);
        }
      }
      param_4 = iVar10 + 1;
      psVar6 = psVar3 + 2;
      param_6 = psVar6;
      uVar5 = local_12;
      uVar2 = local_e;
      if (0 < param_4) {
        do {
          local_e._2_2_ = (undefined2)(uVar2 >> 0x10);
          local_e._0_2_ = (undefined2)uVar2;
          local_12._2_2_ = (undefined2)(uVar5 >> 0x10);
          local_12._0_2_ = (undefined2)uVar5;
          param_1 = CONCAT22((undefined2)local_12,local_14);
          param_2 = CONCAT22((undefined2)local_e,local_12._2_2_);
          param_3 = CONCAT13(cStack_9,CONCAT12(local_a,local_e._2_2_));
          local_12 = uVar5;
          local_e = uVar2;
          FUN_0042a553((uint *)&local_14);
          FUN_0042a553((uint *)&local_14);
          ___add_12((uint *)&local_14,&param_1);
          FUN_0042a553((uint *)&local_14);
          cVar4 = cStack_9;
          cStack_9 = '\0';
          psVar6 = (short *)((int)param_6 + 1);
          param_4 = param_4 + -1;
          *(char *)param_6 = cVar4 + '0';
          param_6 = psVar6;
          uVar5 = local_12;
          uVar2 = local_e;
        } while (param_4 != 0);
      }
      psVar7 = psVar6 + -1;
      psVar1 = psVar3 + 2;
      if ('4' < *(char *)((int)psVar6 + -1)) {
        for (; psVar1 <= psVar7; psVar7 = (short *)((int)psVar7 + -1)) {
          if ((char)*psVar7 != '9') {
            if (psVar1 <= psVar7) goto LAB_0042ad98;
            break;
          }
          *(char *)psVar7 = '0';
        }
        psVar7 = (short *)((int)psVar7 + 1);
        *psVar3 = *psVar3 + 1;
LAB_0042ad98:
        *(char *)psVar7 = (char)*psVar7 + '\x01';
LAB_0042ad9a:
        cVar4 = ((char)psVar7 - (char)psVar3) + -3;
        *(char *)((int)psVar3 + 3) = cVar4;
        *(undefined1 *)(cVar4 + 4 + (int)psVar3) = 0;
        return local_8;
      }
      for (; psVar1 <= psVar7; psVar7 = (short *)((int)psVar7 + -1)) {
        if ((char)*psVar7 != '0') {
          if (psVar1 <= psVar7) goto LAB_0042ad9a;
          break;
        }
      }
      *psVar3 = 0;
      *(undefined1 *)(psVar3 + 1) = 0x20;
      *(undefined1 *)((int)psVar3 + 3) = 1;
      *(char *)psVar1 = '0';
      goto LAB_0042add0;
    }
  }
  *psVar3 = 0;
  *(undefined1 *)(psVar3 + 1) = 0x20;
  *(undefined1 *)((int)psVar3 + 3) = 1;
  *(undefined1 *)(psVar3 + 2) = 0x30;
LAB_0042add0:
  *(undefined1 *)((int)psVar3 + 5) = 0;
  return 1;
}



/* 0042add9 FUN_0042add9 */

void __cdecl FUN_0042add9(int *param_1,int *param_2)

{
  int *piVar1;
  short sVar2;
  int iVar3;
  int *piVar4;
  int *piVar5;
  ushort uVar6;
  uint uVar7;
  int iVar8;
  ushort uVar9;
  uint uVar10;
  ushort uVar11;
  byte local_28;
  undefined1 uStack_27;
  undefined2 uStack_26;
  short local_24;
  undefined2 uStack_22;
  undefined2 local_20;
  undefined1 uStack_1e;
  byte bStack_1d;
  int *local_1c;
  int local_18;
  int local_14;
  ushort *local_10;
  ushort *local_c;
  short *local_8;
  
  piVar5 = param_2;
  piVar4 = param_1;
  local_18 = 0;
  local_28 = 0;
  uStack_27 = 0;
  uStack_26 = 0;
  local_24 = 0;
  uStack_22 = 0;
  local_20 = 0;
  uStack_1e = 0;
  bStack_1d = 0;
  uVar7 = *(ushort *)((int)param_1 + 10) & 0x7fff;
  uVar10 = *(ushort *)((int)param_2 + 10) & 0x7fff;
  uVar11 = (*(ushort *)((int)param_2 + 10) ^ *(ushort *)((int)param_1 + 10)) & 0x8000;
  uVar6 = (ushort)uVar7;
  piVar1 = (int *)(uVar10 + uVar7);
  if (((uVar6 < 0x7fff) && (uVar9 = (ushort)uVar10, uVar9 < 0x7fff)) && ((ushort)piVar1 < 0xbffe)) {
    if ((ushort)piVar1 < 0x3fc0) {
LAB_0042ae7c:
      piVar4[2] = 0;
      piVar4[1] = 0;
      *piVar4 = 0;
      return;
    }
    if (((uVar6 != 0) || (piVar1 = (int *)((int)piVar1 + 1), (param_1[2] & 0x7fffffffU) != 0)) ||
       ((uVar6 = 0, param_1[1] != 0 || (*param_1 != 0)))) {
      param_1 = piVar1;
      if (((uVar9 == 0) && (param_1 = (int *)((int)param_1 + 1), (param_2[2] & 0x7fffffffU) == 0))
         && ((param_2[1] == 0 && (*param_2 == 0)))) goto LAB_0042ae7c;
      local_14 = 0;
      local_8 = &local_24;
      param_2 = (int *)0x5;
      do {
        if (0 < (int)param_2) {
          local_c = (ushort *)(local_14 * 2 + (int)piVar4);
          local_10 = (ushort *)(piVar5 + 2);
          local_1c = param_2;
          do {
            iVar8 = FUN_0042a4d4(*(uint *)(local_8 + -2),(uint)*local_c * (uint)*local_10,
                                 (uint *)(local_8 + -2));
            if (iVar8 != 0) {
              *local_8 = *local_8 + 1;
            }
            local_c = local_c + 1;
            local_10 = local_10 + -1;
            local_1c = (int *)((int)local_1c + -1);
          } while (local_1c != (int *)0x0);
        }
        local_8 = local_8 + 1;
        local_14 = local_14 + 1;
        param_2 = (int *)((int)param_2 + -1);
      } while (0 < (int)param_2);
      param_1 = (int *)((int)param_1 + 0xc002);
      if ((short)(ushort)param_1 < 1) {
LAB_0042af30:
        param_1._0_2_ = (ushort)param_1 - 1;
        if ((short)(ushort)param_1 < 0) {
          iVar8 = -(int)(short)(ushort)param_1;
          param_1._0_2_ = (ushort)param_1 + (short)iVar8;
          do {
            if ((local_28 & 1) != 0) {
              local_18 = local_18 + 1;
            }
            FUN_0042a581((uint *)&local_28);
            iVar8 = iVar8 + -1;
          } while (iVar8 != 0);
          if (local_18 != 0) {
            local_28 = local_28 | 1;
          }
        }
      }
      else {
        do {
          if ((bStack_1d & 0x80) != 0) break;
          FUN_0042a553((uint *)&local_28);
          param_1 = (int *)((int)param_1 + 0xffff);
        } while (0 < (short)(ushort)param_1);
        if ((short)(ushort)param_1 < 1) goto LAB_0042af30;
      }
      if ((0x8000 < CONCAT11(uStack_27,local_28)) ||
         (sVar2 = CONCAT11(bStack_1d,uStack_1e), iVar3 = CONCAT22(local_20,uStack_22),
         iVar8 = CONCAT22(local_24,uStack_26),
         (CONCAT22(uStack_26,CONCAT11(uStack_27,local_28)) & 0x1ffff) == 0x18000)) {
        if (CONCAT22(local_24,uStack_26) == -1) {
          iVar8 = 0;
          if (CONCAT22(local_20,uStack_22) == -1) {
            if (CONCAT11(bStack_1d,uStack_1e) == -1) {
              param_1._0_2_ = (ushort)param_1 + 1;
              sVar2 = -0x8000;
              iVar3 = 0;
              iVar8 = 0;
            }
            else {
              sVar2 = CONCAT11(bStack_1d,uStack_1e) + 1;
              iVar3 = 0;
              iVar8 = 0;
            }
          }
          else {
            sVar2 = CONCAT11(bStack_1d,uStack_1e);
            iVar3 = CONCAT22(local_20,uStack_22) + 1;
          }
        }
        else {
          iVar8 = CONCAT22(local_24,uStack_26) + 1;
          sVar2 = CONCAT11(bStack_1d,uStack_1e);
          iVar3 = CONCAT22(local_20,uStack_22);
        }
      }
      local_24 = (short)((uint)iVar8 >> 0x10);
      uStack_26 = (undefined2)iVar8;
      local_20 = (undefined2)((uint)iVar3 >> 0x10);
      uStack_22 = (undefined2)iVar3;
      bStack_1d = (byte)((ushort)sVar2 >> 8);
      uStack_1e = (undefined1)sVar2;
      if (0x7ffe < (ushort)param_1) goto LAB_0042afd9;
      uVar6 = (ushort)param_1 | uVar11;
      *(undefined2 *)piVar4 = uStack_26;
      *(uint *)((int)piVar4 + 2) = CONCAT22(uStack_22,local_24);
      *(uint *)((int)piVar4 + 6) = CONCAT13(bStack_1d,CONCAT12(uStack_1e,local_20));
    }
    *(ushort *)((int)piVar4 + 10) = uVar6;
  }
  else {
LAB_0042afd9:
    piVar4[1] = 0;
    *piVar4 = 0;
    piVar4[2] = (-(uint)(uVar11 != 0) & 0x80000000) + 0x7fff8000;
  }
  return;
}



/* 0042aff9 FUN_0042aff9 */

void __cdecl FUN_0042aff9(int *param_1,uint param_2,int param_3)

{
  uint uVar1;
  uint uVar2;
  int iVar3;
  int *piVar4;
  undefined2 local_10;
  undefined4 local_e;
  undefined2 uStack_a;
  int iStack_8;
  
  iVar3 = 0x431460;
  if (param_2 != 0) {
    if ((int)param_2 < 0) {
      param_2 = -param_2;
      iVar3 = 0x4315c0;
    }
    if (param_3 == 0) {
      *(undefined2 *)param_1 = 0;
    }
    while (param_2 != 0) {
      iVar3 = iVar3 + 0x54;
      uVar1 = (int)param_2 >> 3;
      uVar2 = param_2 & 7;
      param_2 = uVar1;
      if (uVar2 != 0) {
        piVar4 = (int *)(iVar3 + uVar2 * 0xc);
        if (0x7fff < *(ushort *)(iVar3 + uVar2 * 0xc)) {
          local_10 = (undefined2)*piVar4;
          local_e._0_2_ = (undefined2)((uint)*piVar4 >> 0x10);
          local_e._2_2_ = (undefined2)piVar4[1];
          uStack_a = (undefined2)((uint)piVar4[1] >> 0x10);
          iStack_8 = piVar4[2];
          local_e = CONCAT22(local_e._2_2_,(undefined2)local_e) + -1;
          piVar4 = (int *)&local_10;
        }
        FUN_0042add9(param_1,piVar4);
      }
    }
  }
  return;
}


