/* Reconstructed pseudocode; not original or buildable C. */

/* 00401000 FUN_00401000 */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void __cdecl FUN_00401000(int param_1,int param_2)

{
  char cVar1;
  bool bVar2;
  int iVar3;
  char *pcVar4;
  char *pcVar5;
  char *pcVar6;
  int *piVar7;
  int iVar8;
  undefined2 *puVar9;
  char local_6c [100];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  _DAT_00407528 = 0x4033570a3d70a3d7;
  bVar2 = false;
  DAT_00407294 = 0;
  DAT_00407520 = 0;
  DAT_004073e0 = 't';
  DAT_004073e1 = 's';
  DAT_004073e0_1._1_1_ = '_';
  DAT_004073e0_1._2_1_ = 'l';
  DAT_00407298 = 5;
  DAT_004073e4 = 'o';
  DAT_004073e4_1._0_1_ = 'g';
  DAT_004073e4_1._1_1_ = '.';
  DAT_004073e4_1._2_1_ = 't';
  _DAT_004073e8 = 0x7478;
  DAT_004073ea = 0;
  if (param_1 < 3) {
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  pcVar4 = *(char **)(param_2 + 4);
  pcVar5 = &DAT_004072a0;
  do {
    cVar1 = *pcVar4;
    *pcVar5 = cVar1;
    pcVar4 = pcVar4 + 1;
    pcVar5 = pcVar5 + 1;
  } while (cVar1 != '\0');
  pcVar4 = *(char **)(param_2 + 8);
  pcVar5 = &DAT_00407340;
  do {
    cVar1 = *pcVar4;
    *pcVar5 = cVar1;
    pcVar4 = pcVar4 + 1;
    pcVar5 = pcVar5 + 1;
  } while (cVar1 != '\0');
  iVar8 = param_1 + -3;
  if (0 < iVar8) {
    piVar7 = (int *)(param_2 + 0xc);
    param_1 = iVar8;
    do {
      if (bVar2) break;
      pcVar4 = (char *)*piVar7;
      if (*pcVar4 != '-') {
        bVar2 = true;
        goto LAB_00401296;
      }
      switch(pcVar4[1]) {
      case 'd':
        pcVar5 = pcVar4 + 2;
        pcVar6 = (char *)&DAT_00407480;
        if (pcVar4[2] == '\0') {
          pcVar4 = (char *)piVar7[1];
          param_1 = iVar8 + -1;
          piVar7 = piVar7 + 1;
          do {
            cVar1 = *pcVar4;
            *pcVar6 = cVar1;
            pcVar4 = pcVar4 + 1;
            pcVar6 = pcVar6 + 1;
          } while (cVar1 != '\0');
        }
        else {
          do {
            cVar1 = *pcVar5;
            *pcVar6 = cVar1;
            pcVar5 = pcVar5 + 1;
            pcVar6 = pcVar6 + 1;
          } while (cVar1 != '\0');
        }
        puVar9 = (undefined2 *)0x40747f;
        do {
          pcVar4 = (char *)((int)puVar9 + 1);
          puVar9 = (undefined2 *)((int)puVar9 + 1);
        } while (*pcVar4 != '\0');
        *puVar9 = 0x5c;
        iVar8 = param_1 + -1;
        piVar7 = piVar7 + 1;
        param_1 = iVar8;
        break;
      default:
switchD_004010f9_caseD_65:
        bVar2 = true;
        goto LAB_0040112e;
      case 'l':
        pcVar5 = pcVar4 + 2;
        pcVar6 = &DAT_004073e0;
        if (pcVar4[2] == '\0') {
          pcVar4 = (char *)piVar7[1];
          do {
            cVar1 = *pcVar4;
            *pcVar6 = cVar1;
            pcVar4 = pcVar4 + 1;
            pcVar6 = pcVar6 + 1;
          } while (cVar1 != '\0');
          iVar8 = iVar8 + -2;
          piVar7 = piVar7 + 2;
          param_1 = iVar8;
        }
        else {
          do {
            cVar1 = *pcVar5;
            *pcVar6 = cVar1;
            pcVar5 = pcVar5 + 1;
            pcVar6 = pcVar6 + 1;
          } while (cVar1 != '\0');
          iVar8 = iVar8 + -1;
          piVar7 = piVar7 + 1;
          param_1 = iVar8;
        }
        break;
      case 'p':
        pcVar4 = pcVar4 + 2;
        if (*pcVar4 == '\0') {
          pcVar4 = (char *)piVar7[1];
          piVar7 = piVar7 + 1;
          iVar8 = iVar8 + -1;
          iVar3 = -(int)pcVar4;
          do {
            cVar1 = *pcVar4;
            pcVar4[(int)(local_6c + iVar3)] = cVar1;
            pcVar4 = pcVar4 + 1;
          } while (cVar1 != '\0');
        }
        else {
          iVar3 = -(int)pcVar4;
          do {
            cVar1 = *pcVar4;
            pcVar4[(int)(local_6c + iVar3)] = cVar1;
            pcVar4 = pcVar4 + 1;
          } while (cVar1 != '\0');
        }
        sscanf(local_6c,"%i",&DAT_00407520);
        iVar8 = iVar8 + -1;
        piVar7 = piVar7 + 1;
        param_1 = iVar8;
        break;
      case 'r':
        pcVar4 = pcVar4 + 2;
        if (*pcVar4 == '\0') {
          pcVar4 = (char *)piVar7[1];
          piVar7 = piVar7 + 1;
          iVar8 = iVar8 + -1;
          iVar3 = -(int)pcVar4;
          do {
            cVar1 = *pcVar4;
            pcVar4[(int)(local_6c + iVar3)] = cVar1;
            pcVar4 = pcVar4 + 1;
          } while (cVar1 != '\0');
        }
        else {
          iVar3 = -(int)pcVar4;
          do {
            cVar1 = *pcVar4;
            pcVar4[(int)(local_6c + iVar3)] = cVar1;
            pcVar4 = pcVar4 + 1;
          } while (cVar1 != '\0');
        }
        sscanf(local_6c,"%lf",&DAT_00407528);
        iVar8 = iVar8 + -1;
        piVar7 = piVar7 + 1;
        param_1 = iVar8;
        break;
      case 's':
        iVar8 = iVar8 + -1;
        DAT_00407294 = 1;
        piVar7 = piVar7 + 1;
        param_1 = iVar8;
        break;
      case 'v':
        iVar3 = 2;
        cVar1 = *(char *)(*piVar7 + 2);
        while (DAT_00407298 = DAT_00407298 + 1, cVar1 == 'v') {
          iVar3 = iVar3 + 1;
          cVar1 = *(char *)(iVar3 + *piVar7);
        }
        if (*(char *)(iVar3 + *piVar7) != '\0') goto switchD_004010f9_caseD_65;
LAB_0040112e:
        iVar8 = iVar8 + -1;
        piVar7 = piVar7 + 1;
        param_1 = iVar8;
      }
LAB_00401296:
    } while (0 < iVar8);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 004012f0 FUN_004012f0 */

undefined4 __cdecl FUN_004012f0(FILE *param_1)

{
  int _Ch;
  undefined4 uVar1;
  
  uVar1 = 0;
switchD_00401316_caseD_9:
  _Ch = getc(param_1);
  switch(_Ch) {
  default:
    uVar1 = 0x18;
switchD_00401316_caseD_ffffffff:
    return uVar1;
  case 9:
  case 10:
  case 0xc:
  case 0x20:
    goto switchD_00401316_caseD_9;
  case 0x2c:
  case 0x2d:
  case 0x2e:
  case 0x2f:
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
  case 0x40:
  case 0x41:
  case 0x42:
  case 0x43:
  case 0x44:
  case 0x45:
  case 0x46:
  case 0x47:
  case 0x48:
  case 0x49:
  case 0x4a:
  case 0x4b:
  case 0x4c:
  case 0x4d:
  case 0x4e:
  case 0x4f:
  case 0x50:
  case 0x51:
  case 0x52:
  case 0x53:
  case 0x54:
  case 0x55:
  case 0x56:
  case 0x57:
  case 0x58:
  case 0x59:
  case 0x5a:
  case 0x5b:
  case 0x5c:
  case 0x5d:
  case 0x5f:
  case 0x61:
  case 0x62:
  case 99:
  case 100:
  case 0x65:
  case 0x66:
  case 0x67:
  case 0x68:
  case 0x69:
  case 0x6a:
  case 0x6b:
  case 0x6c:
  case 0x6d:
  case 0x6e:
  case 0x6f:
  case 0x70:
  case 0x71:
  case 0x72:
  case 0x73:
  case 0x74:
  case 0x75:
  case 0x76:
  case 0x77:
  case 0x78:
  case 0x79:
  case 0x7a:
    ungetc(_Ch,param_1);
    return 0;
  case -1:
    goto switchD_00401316_caseD_ffffffff;
  }
}



/* 004013d0 FUN_004013d0 */

undefined4 __cdecl FUN_004013d0(FILE *param_1,undefined4 *param_2)

{
  int iVar1;
  
  iVar1 = getc(param_1);
  if (iVar1 == 0x2d) {
    iVar1 = getc(param_1);
    switch(iVar1) {
    default:
      return 0x18;
    case 0x50:
    case 0x70:
      *param_2 = 0;
      return 0;
    case 0x52:
    case 0x72:
      *param_2 = 1;
      return 0;
    case -1:
      return 0x17;
    }
  }
  *param_2 = 0;
  ungetc(iVar1,param_1);
  return 0;
}



/* 004014d0 FUN_004014d0 */

undefined4 __cdecl FUN_004014d0(FILE *param_1,int *param_2)

{
  int _Ch;
  int iVar1;
  int iVar2;
  int local_8;
  
  iVar2 = 0;
  local_8 = 10;
  *param_2 = 0;
  do {
    _Ch = getc(param_1);
    switch(_Ch) {
    default:
      return 0x18;
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
      *param_2 = *param_2 * local_8 + -0x30 + _Ch;
      iVar2 = iVar2 + 1;
      break;
    case 0x41:
    case 0x42:
    case 0x43:
    case 0x44:
    case 0x45:
    case 0x46:
    case 0x61:
    case 0x62:
    case 99:
    case 100:
    case 0x65:
    case 0x66:
      if (local_8 == 0x10) {
        iVar1 = _Ch + -0x57 + *param_2 * 0x10;
        if (_Ch < 0x61) {
          iVar1 = _Ch + -0x37 + *param_2 * 0x10;
        }
        *param_2 = iVar1;
        iVar2 = iVar2 + 1;
        break;
      }
    case 9:
    case 10:
    case 0xc:
    case 0x20:
    case 0x2c:
    case 0x2d:
    case 0x2e:
    case 0x2f:
    case 0x40:
    case 0x47:
    case 0x48:
    case 0x49:
    case 0x4a:
    case 0x4b:
    case 0x4c:
    case 0x4d:
    case 0x4e:
    case 0x4f:
    case 0x50:
    case 0x51:
    case 0x52:
    case 0x53:
    case 0x54:
    case 0x55:
    case 0x56:
    case 0x57:
    case 0x59:
    case 0x5a:
    case 0x5b:
    case 0x5c:
    case 0x5d:
    case 0x5f:
    case 0x67:
    case 0x68:
    case 0x69:
    case 0x6a:
    case 0x6b:
    case 0x6c:
    case 0x6d:
    case 0x6e:
    case 0x6f:
    case 0x70:
    case 0x71:
    case 0x72:
    case 0x73:
    case 0x74:
    case 0x75:
    case 0x76:
    case 0x77:
    case 0x79:
    case 0x7a:
switchD_00401501_caseD_9:
      ungetc(_Ch,param_1);
      return 0;
    case 0x58:
    case 0x78:
      if ((iVar2 != 1) || (*param_2 != 0)) goto switchD_00401501_caseD_9;
      local_8 = 0x10;
      iVar2 = 2;
      break;
    case -1:
      return 0x17;
    }
  } while( true );
}



/* 00401610 FUN_00401610 */

undefined4 __cdecl FUN_00401610(FILE *param_1,int param_2)

{
  int _Ch;
  int iVar1;
  
  iVar1 = 0;
  _Ch = getc(param_1);
LAB_00401625:
  switch(_Ch) {
  default:
    return 0x18;
  case 9:
  case 10:
  case 0xc:
  case 0x20:
  case 0x2c:
  case 0x5b:
  case 0x5d:
    ungetc(_Ch,param_1);
    *(undefined1 *)(iVar1 + param_2) = 0;
    return 0;
  case 0x2d:
  case 0x2e:
  case 0x2f:
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
  case 0x3a:
  case 0x40:
  case 0x41:
  case 0x42:
  case 0x43:
  case 0x44:
  case 0x45:
  case 0x46:
  case 0x47:
  case 0x48:
  case 0x49:
  case 0x4a:
  case 0x4b:
  case 0x4c:
  case 0x4d:
  case 0x4e:
  case 0x4f:
  case 0x50:
  case 0x51:
  case 0x52:
  case 0x53:
  case 0x54:
  case 0x55:
  case 0x56:
  case 0x57:
  case 0x58:
  case 0x59:
  case 0x5a:
  case 0x5c:
  case 0x5f:
  case 0x61:
  case 0x62:
  case 99:
  case 100:
  case 0x65:
  case 0x66:
  case 0x67:
  case 0x68:
  case 0x69:
  case 0x6a:
  case 0x6b:
  case 0x6c:
  case 0x6d:
  case 0x6e:
  case 0x6f:
  case 0x70:
  case 0x71:
  case 0x72:
  case 0x73:
  case 0x74:
  case 0x75:
  case 0x76:
  case 0x77:
  case 0x78:
  case 0x79:
  case 0x7a:
    break;
  case -1:
    return 0x17;
  }
  if (_Ch == 0x5c) {
    *(undefined1 *)(iVar1 + param_2) = 0x5c;
    iVar1 = iVar1 + 1;
  }
  *(char *)(iVar1 + param_2) = (char)_Ch;
  iVar1 = iVar1 + 1;
  _Ch = getc(param_1);
  goto LAB_00401625;
}



/* 00401710 FUN_00401710 */

void __cdecl FUN_00401710(int param_1,int *param_2)

{
  char cVar1;
  int iVar2;
  FILE *_File;
  undefined4 local_2ac;
  char local_2a8 [512];
  char local_a8 [160];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  local_2ac = 0;
  iVar2 = 0;
  do {
    cVar1 = (&DAT_00407340)[iVar2];
    local_a8[iVar2] = cVar1;
    iVar2 = iVar2 + 1;
  } while (cVar1 != '\0');
  _File = fopen(local_a8,"r");
  if (_File == (FILE *)0x0) {
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  *param_2 = 0;
  do {
    switch(local_2ac) {
    case 0:
      iVar2 = FUN_004012f0(_File);
      if (iVar2 == 0) {
        iVar2 = getc(_File);
        if (iVar2 == -1) {
LAB_004019c7:
          fclose(_File);
          __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
          return;
        }
        if (iVar2 == 0x5b) {
          local_2ac = 1;
        }
      }
      break;
    case 1:
      iVar2 = FUN_004012f0(_File);
      if (iVar2 == 0) {
        iVar2 = FUN_004014d0(_File,(int *)(*param_2 * 0xac + param_1));
        if (iVar2 != 0) goto LAB_004019c7;
        local_2ac = 2;
      }
      break;
    case 2:
      iVar2 = FUN_004012f0(_File);
      if (iVar2 == 0) {
        iVar2 = getc(_File);
        if (iVar2 != 0x2c) goto LAB_004019c7;
        local_2ac = 3;
      }
      break;
    case 3:
      iVar2 = FUN_004012f0(_File);
      if (iVar2 == 0) {
        iVar2 = FUN_004013d0(_File,(undefined4 *)(*param_2 * 0xac + 4 + param_1));
        if (((iVar2 != 0) || (iVar2 = FUN_004012f0(_File), iVar2 != 0)) ||
           (iVar2 = FUN_004014d0(_File,(int *)(*param_2 * 0xac + 8 + param_1)), iVar2 != 0))
        goto LAB_004019c7;
        local_2ac = 4;
      }
      break;
    case 4:
      iVar2 = FUN_004012f0(_File);
      if (iVar2 == 0) {
        iVar2 = getc(_File);
        if (iVar2 != 0x2c) goto LAB_004019c7;
        local_2ac = 5;
      }
      break;
    case 5:
      iVar2 = FUN_004012f0(_File);
      if (iVar2 == 0) {
        iVar2 = FUN_00401610(_File,*param_2 * 0xac + 0xc + param_1);
        if (iVar2 != 0) goto LAB_004019c7;
        local_2ac = 6;
      }
      break;
    case 6:
      iVar2 = FUN_004012f0(_File);
      if (iVar2 == 0) {
        iVar2 = getc(_File);
        if (iVar2 != 0x5d) goto LAB_004019c7;
        *param_2 = *param_2 + 1;
        iVar2 = *param_2 * 0xac + param_1;
        local_2ac = 0;
        sprintf(local_2a8,
                "input section %2d PID = 0x%x, type = %d, data = %d ms(0)|kb/s(1), \n              sectionfile: %s\n"
                ,*param_2 + -1,*(undefined4 *)(iVar2 + -0xac),*(undefined4 *)(iVar2 + -0xa8),
                *(undefined4 *)(iVar2 + -0xa4),iVar2 + -0xa0);
        FUN_00402270(0,"ts_generate",local_2a8);
      }
    }
  } while( true );
}



/* 00401a00 FUN_00401a00 */

void FUN_00401a00(void)

{
  char local_208 [512];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  sprintf(local_208,"will be performed with the following settings\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"generated transport stream     %s\n",&DAT_004072a0);
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"inputfile                      %s\n",&DAT_00407340);
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"logging file                   %s\n",&DAT_004073e0);
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"result directory               %s\n",&DAT_00407480);
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"logging level is set to   %d\n",DAT_00407298);
  FUN_00402270(0,"ts_generate",local_208);
  if (DAT_00407294 == 0) {
    sprintf(local_208,"silent mode is            off\n");
  }
  else {
    sprintf(local_208,"silent mode is            on\n");
  }
  FUN_00402270(0,"ts_generate",local_208);
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00401b80 FUN_00401b80 */

void FUN_00401b80(void)

{
  char local_208 [512];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  sprintf(local_208,"\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"ts_generate usage\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,
          "ts_generate ts_out ts_in [-l log_file][-d dir_name][-p PCR_PID][-r rate][-v][-s]\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"ts_out             generated transport stream\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,
          "ts_in              inputfile, syntax [PID, -p repetition period in ms| -r data rate in kb/s , filename]+ \n"
         );
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"log_file           loggin file, default ts_log.txt\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"dir_name           result directory name, must already exist\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"PCR_PID            PID for PCR, use if PCR is needed\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"rate               transportstream rate in Mb/s\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"-v                 logging level, more \'v\'s give more information\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"-s                 silent mode\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"error codes\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 0    TS_OK: OK\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 1    TS_ARG_ERROR: input argument error\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 2    TS_TOO_SHORT: transport stream too short too synchronize\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 3    TS_NO_SYNC: transport stream cannot be synchronized\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 4    TS_RESULT_DIRECTORY_NON_EXISTENT: result directory does not exist\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 5    TS_FILE_NON_EXISTENT: file does not exist (or wrong directory)\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 6    TS_PAT_ERROR: pat filtering failed\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 7    TS_OUT_OF_CONTROL: progam error\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 8    TS_STREAM_NOT_FOUND: transport stream file not found\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208," 9    TS_OUT_OF_SYNC: lost synchronization\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"10    TS_END_OF_STREAM: end of stream reached\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"11    TS_NO_PAYLOAD: packet contains no payload\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"12    TS_UNEXPECTED_TABLE_ID: table id does not comply\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"13    TS_CRC_ERROR: CRC not valid\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"14    TS_UNKNOWN_DESCRIPTOR: descriptor not known\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"15    TS_FILE_HANDLING_ERROR: file handling failed\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"16    TS_UNKNOWN_MESSAGE: encountered unknown messade id\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"17    TS_TOO_MANY_MODULES: too many modules in the carousel\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"18    TS_NOT_YET_SUPPORTED: funtionality is not yet supported\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"19    TS_ILLEGAL_CHARACTER_TABLE: non existent character table\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"20    TS_COMPATIBILITY_DECRIPTOR_ERROR: length error\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"21    TS_CONTENT_TYPE_NOT_FOUND: content type not present in stream\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"22    TS_PAT_SECTION_TOO_LARGE: PAT section larger than 1 kb\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"23    TS_EOF: unexpected end of file\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"24    TS_ILLEGAL_CHARACTER: illegal character in inputstring\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"25    TS_PARSE_SEQUENCE_ERROR: parse sequence error\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"\n");
  FUN_00402270(0,"ts_generate",local_208);
  sprintf(local_208,"\n");
  FUN_00402270(0,"ts_generate",local_208);
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00402120 FUN_00402120 */

void __cdecl FUN_00402120(int param_1,int param_2)

{
  int iVar1;
  char local_208 [512];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  iVar1 = 0;
  for (; 0 < param_1; param_1 = param_1 + -1) {
    sprintf(local_208,"argument %d: %s\n",iVar1,*(undefined4 *)(param_2 + iVar1 * 4));
    FUN_00402270(0,"ts_generate",local_208);
    iVar1 = iVar1 + 1;
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00402190 FUN_00402190 */

void FUN_00402190(void)

{
  char cVar1;
  int iVar2;
  char *pcVar3;
  uint uVar4;
  undefined4 *puVar5;
  undefined4 *puVar6;
  char *pcVar7;
  char local_a8 [160];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  iVar2 = 0;
  do {
    cVar1 = *(char *)((int)&DAT_00407480 + iVar2);
    local_a8[iVar2] = cVar1;
    iVar2 = iVar2 + 1;
  } while (cVar1 != '\0');
  pcVar7 = &DAT_004073e0;
  do {
    pcVar3 = pcVar7;
    pcVar7 = pcVar3 + 1;
  } while (*pcVar3 != '\0');
  puVar6 = (undefined4 *)&stack0xffffff57;
  do {
    pcVar7 = (char *)((int)puVar6 + 1);
    puVar6 = (undefined4 *)((int)puVar6 + 1);
  } while (*pcVar7 != '\0');
  puVar5 = (undefined4 *)&DAT_004073e0;
  for (uVar4 = (uint)(pcVar3 + -0x4073df) >> 2; uVar4 != 0; uVar4 = uVar4 - 1) {
    *puVar6 = *puVar5;
    puVar5 = puVar5 + 1;
    puVar6 = puVar6 + 1;
  }
  for (uVar4 = (uint)(pcVar3 + -0x4073df) & 3; uVar4 != 0; uVar4 = uVar4 - 1) {
    *(undefined1 *)puVar6 = *(undefined1 *)puVar5;
    puVar5 = (undefined4 *)((int)puVar5 + 1);
    puVar6 = (undefined4 *)((int)puVar6 + 1);
  }
  DAT_00406740 = (int)fopen(local_a8,"w");
  if ((FILE *)DAT_00406740 == (FILE *)0x0) {
    DAT_00406740 = (int)fopen("ts_log.txt","w");
    if ((FILE *)DAT_00406740 == (FILE *)0x0) {
      pcVar7 = 
      "Error, no log file could not be created, check input parameters, check there is enough disk space\n"
      ;
    }
    else {
      pcVar7 = 
      "Error, requested log file could not be created, used ts_log.txt in current directory\n";
    }
    printf(pcVar7);
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00402260 FUN_00402260 */

void FUN_00402260(void)

{
  fclose(DAT_00406740);
  return;
}



/* 00402270 FUN_00402270 */

void __cdecl FUN_00402270(int param_1,undefined4 param_2,undefined4 param_3)

{
  if ((param_1 <= DAT_00407298) &&
     (fprintf(DAT_00406740,"%s : %s",param_2,param_3), DAT_00407294 == 0)) {
    printf("%s : %s",param_2,param_3);
  }
  return;
}



/* 004022c0 FUN_004022c0 */

/* WARNING: Function: __alloca_probe replaced with injection: alloca_probe */
/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void __thiscall FUN_004022c0(void *this,int param_1)

{
  FILE *pFVar1;
  size_t sVar2;
  void *pvVar3;
  int iVar4;
  int iVar5;
  undefined4 extraout_ECX;
  undefined4 extraout_ECX_00;
  undefined4 uVar6;
  undefined4 extraout_ECX_01;
  undefined4 extraout_EDX;
  undefined4 extraout_EDX_00;
  undefined4 uVar7;
  undefined4 extraout_EDX_01;
  int *piVar8;
  int *piVar9;
  ulonglong uVar10;
  int local_13bc;
  undefined8 local_13b8;
  int local_13b0;
  int local_13ac;
  FILE *local_13a8;
  int *local_13a4;
  int local_13a0;
  int local_139c;
  size_t asStack_1398 [100];
  undefined1 local_1208;
  byte local_1207;
  byte local_1206;
  undefined1 local_1205 [4093];
  char local_208 [512];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  local_13b8 = (double)CONCAT44(this,(undefined4)local_13b8);
  local_139c = 0;
  DAT_00406de0 = param_1;
  local_13a0 = 0;
  if (0 < param_1) {
    piVar8 = (int *)((int)this + 8);
    piVar9 = &DAT_00406de8;
    do {
      piVar9[-1] = *piVar8;
      local_13a4 = piVar8;
      local_13a8 = fopen((char *)(piVar8 + 1),"rb");
      if (local_13a8 == (FILE *)0x0) {
        local_139c = 5;
        local_13a4 = piVar8;
      }
      else {
        sprintf(local_208,"handling section file %s\n",piVar8 + 1);
        FUN_00402270(0,"ts_generate",local_208);
        local_13ac = 0;
        while (pFVar1 = local_13a8, sVar2 = fread(&local_1208,1,3,local_13a8), sVar2 != 0) {
          local_13ac = local_13ac + 1;
          sVar2 = (local_1207 & 0xf) * 0x100 + (uint)local_1206;
          fread(local_1205,1,sVar2,local_13a8);
          if (local_13ac == 1) {
            iVar5 = piVar8[-1];
            asStack_1398[local_13a0] = sVar2;
            if (iVar5 == 1) {
              iVar5 = (int)(sVar2 * 8) / *piVar8;
              piVar9[-1] = iVar5;
              if (iVar5 == 0) {
                piVar9[-1] = 1;
              }
            }
            else {
              iVar5 = (int)(sVar2 * 8) / piVar9[-1];
              *piVar8 = iVar5;
              if (iVar5 == 0) {
                *piVar8 = 1;
              }
            }
          }
        }
        *piVar9 = local_13ac;
        pvVar3 = malloc(local_13ac * 8);
        piVar9[1] = (int)pvVar3;
        iVar5 = fseek(pFVar1,0,0);
        if (iVar5 != 0) {
          local_139c = 0xf;
        }
        iVar5 = 0;
        while (pFVar1 = local_13a8, sVar2 = fread(&local_1208,1,3,local_13a8), sVar2 != 0) {
          sVar2 = (local_1207 & 0xf) * 0x100 + (uint)local_1206;
          fread(local_1205,1,sVar2,local_13a8);
          local_139c = FUN_00402920(sVar2 + 3,(int)&local_1208,local_13a4[-2],&local_13b0,
                                    &local_13bc);
          *(int *)(piVar9[1] + iVar5 * 8) = local_13b0;
          *(int *)(piVar9[1] + 4 + iVar5 * 8) = local_13bc;
          iVar5 = iVar5 + 1;
        }
        *piVar9 = iVar5;
        fclose(pFVar1);
        sprintf(local_208,
                "PID = 0x%04x, repetition period = %4d ms, data rate = %5d kb/s, number of sections = %5d\n"
                ,local_13a4[-2],piVar9[-1],*local_13a4,iVar5);
        FUN_00402270(0,"ts_generate",local_208);
      }
      local_13a0 = local_13a0 + 1;
      piVar8 = local_13a4 + 0x2b;
      piVar9 = piVar9 + 3;
    } while (local_13a0 < DAT_00406de0);
    local_13a4 = piVar8;
    if (local_139c != 0) goto LAB_004026e2;
    this = local_13b8._4_4_;
  }
  iVar5 = 0;
  local_13a0 = 0;
  if (0 < DAT_00406de0) {
    piVar8 = (int *)((int)this + 8);
    iVar4 = DAT_00406de0;
    do {
      iVar5 = iVar5 + *piVar8;
      piVar8 = piVar8 + 0x2b;
      iVar4 = iVar4 + -1;
      local_13a0 = iVar5;
    } while (iVar4 != 0);
  }
  sprintf(local_208,"TotalDataRate        = %5d kb/s\n",local_13a0);
  FUN_00402270(0,"ts_generate",local_208);
  uVar10 = FUN_00403200(extraout_ECX,extraout_EDX);
  sprintf(local_208,"transportstream rate = %5d kb/s\n",(int)uVar10);
  FUN_00402270(0,"ts_generate",local_208);
  if (_DAT_00407528 * 1000.0 < (double)local_13a0) {
    iVar5 = 0;
    if (0 < DAT_00406de0) {
      local_13b8 = ((_DAT_00407528 * 1000.0) / (double)local_13a0) * 0.99;
      piVar8 = &DAT_00406de4;
      piVar9 = (int *)((int)this + 8);
      uVar6 = extraout_ECX_00;
      uVar7 = extraout_EDX_00;
      do {
        uVar10 = FUN_00403200(uVar6,uVar7);
        *piVar9 = (int)uVar10;
        if ((int)uVar10 == 0) {
          *piVar9 = 1;
        }
        iVar4 = (int)(asStack_1398[iVar5] * 8) / *piVar9;
        *piVar8 = iVar4;
        sprintf(local_208,"repetition period = %5d ms,   ",iVar4);
        FUN_00402270(0,"ts_generate",local_208);
        sprintf(local_208,"data rate = %5d kb/s\n",*piVar9);
        FUN_00402270(0,"ts_generate",local_208);
        iVar5 = iVar5 + 1;
        piVar8 = piVar8 + 3;
        piVar9 = piVar9 + 0x2b;
        uVar6 = extraout_ECX_01;
        uVar7 = extraout_EDX_01;
      } while (iVar5 < DAT_00406de0);
LAB_004026e2:
      __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
      return;
    }
  }
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00402710 FUN_00402710 */

/* WARNING: Function: __alloca_probe replaced with injection: alloca_probe */
/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

void __cdecl FUN_00402710(int param_1,int param_2)

{
  int iVar1;
  int iVar2;
  undefined4 *puVar3;
  undefined4 *puVar4;
  undefined4 auStackY_4a0c [296];
  undefined4 uStackY_456c;
  int local_4540;
  int local_453c;
  undefined1 local_4538 [17200];
  char local_208 [512];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  local_453c = 0;
  printf("Start program\n");
  iVar1 = FUN_00401000(param_1,param_2);
  FUN_00402190();
  if (iVar1 == 0) {
    sprintf(local_208,"Start merge\n");
    FUN_00402270(0,&DAT_00404d18,local_208);
    FUN_00401a00();
    iVar2 = 0;
    iVar1 = 0;
    do {
      if (iVar2 == 0) {
        sprintf(local_208,"Read input sections\n");
        FUN_00402270(0,&DAT_00404d18,local_208);
        uStackY_456c = 0x4028a6;
        iVar1 = FUN_00401710((int)local_4538,&local_4540);
        if (iVar1 != 0) {
          local_453c = 1;
        }
        iVar2 = 2;
      }
      else if (iVar2 == 2) {
        sprintf(local_208,"Read section files\n");
        FUN_00402270(0,&DAT_00404d18,local_208);
        iVar1 = FUN_004022c0(local_4538,local_4540);
        if (iVar1 != 0) {
          local_453c = 1;
        }
        iVar2 = 0x1e;
      }
      else {
        if (iVar2 != 0x1e) break;
        sprintf(local_208,"Generate stream\n");
        FUN_00402270(0,&DAT_00404d18,local_208);
        puVar3 = &DAT_00406de0;
        puVar4 = auStackY_4a0c;
        for (iVar1 = 0x12d; iVar1 != 0; iVar1 = iVar1 + -1) {
          *puVar4 = *puVar3;
          puVar3 = puVar3 + 1;
          puVar4 = puVar4 + 1;
        }
        iVar1 = FUN_00402be0();
        if (iVar1 != 0) {
          local_453c = 1;
        }
        iVar2 = 0x32;
      }
    } while (local_453c == 0);
    sprintf(local_208,"Generation finished with code %d\n");
    FUN_00402270(0,&DAT_00404d18,local_208);
    if (iVar1 == 0) goto LAB_004028f9;
  }
  else {
    FUN_00402120(param_1,param_2);
  }
  FUN_00401b80();
LAB_004028f9:
  FUN_00402260();
  printf("End of program\n");
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00402920 FUN_00402920 */

undefined4 __cdecl
FUN_00402920(int param_1,int param_2,undefined4 param_3,int *param_4,int *param_5)

{
  byte *pbVar1;
  void *pvVar2;
  uint uVar3;
  int iVar4;
  int iVar5;
  int local_8;
  
  local_8 = param_1 / 0xb8;
  if (param_1 != local_8 * 0xb8) {
    local_8 = local_8 + 1;
  }
  if (param_1 == 0xb8) {
    local_8 = local_8 + 1;
  }
  *param_4 = local_8;
  pvVar2 = malloc(local_8 * 0xbc);
  iVar5 = 0;
  *param_5 = (int)pvVar2;
  param_4 = (int *)0x0;
  if (0 < local_8) {
    do {
      iVar4 = (int)param_4 * 0xbc;
      *(undefined1 *)(iVar4 + *param_5) = 0x47;
      if (param_4 == (int *)0x0) {
        *(undefined1 *)(*param_5 + 1) = 0x40;
        *(undefined1 *)(*param_5 + 4) = 0;
        uVar3 = 5;
      }
      else {
        *(undefined1 *)(iVar4 + 1 + *param_5) = 0;
        uVar3 = 4;
      }
      pbVar1 = (byte *)(iVar4 + 1 + *param_5);
      *pbVar1 = *pbVar1 | (byte)((uint)param_3 >> 8) & 0x1f;
      *(undefined1 *)(iVar4 + 2 + *param_5) = (undefined1)param_3;
      *(byte *)(iVar4 + 3 + *param_5) = (char)param_4 + 1U & 0xf | 0x10;
      if (param_4 == (int *)(local_8 + -1)) {
        for (; iVar5 < param_1; iVar5 = iVar5 + 1) {
          *(undefined1 *)(*param_5 + iVar4 + uVar3) = *(undefined1 *)(iVar5 + param_2);
          uVar3 = uVar3 + 1;
        }
        for (; (int)uVar3 < 0xbc; uVar3 = uVar3 + 1) {
          *(undefined1 *)(*param_5 + iVar4 + uVar3) = 0xff;
        }
      }
      else if (uVar3 < 0xbc) {
        do {
          *(undefined1 *)(*param_5 + iVar4 + uVar3) = *(undefined1 *)(iVar5 + param_2);
          uVar3 = uVar3 + 1;
          iVar5 = iVar5 + 1;
        } while ((int)uVar3 < 0xbc);
      }
      param_4 = (int *)((int)param_4 + 1);
    } while ((int)param_4 < local_8);
  }
  return 0;
}



/* 00402a70 FUN_00402a70 */

void FUN_00402a70(void)

{
  undefined4 local_c4;
  undefined1 local_c0 [184];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  local_c4 = 0x10ff1f47;
  memset(local_c0,0xff,0xb8);
  fwrite(&local_c4,1,0xbc,DAT_00406a68);
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00402ad0 FUN_00402ad0 */

void FUN_00402ad0(void)

{
  byte bVar1;
  int iVar2;
  uint uVar3;
  undefined4 *puVar4;
  undefined4 *puVar5;
  undefined4 local_2c4;
  char local_208 [512];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  puVar5 = &local_2c4;
  puVar4 = (undefined4 *)register0x00000010;
  for (iVar2 = 0x2f; puVar4 = puVar4 + 1, iVar2 != 0; iVar2 = iVar2 + -1) {
    *puVar5 = *puVar4;
    puVar5 = puVar5 + 1;
  }
  if (99 < DAT_00406a6c) {
    sprintf(local_208,"Please increase the size of continuity_counters");
    FUN_00402270(0,&DAT_00404d40,local_208);
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  iVar2 = (local_2c4._1_1_ & 0x1f) * 0x100 + (uint)local_2c4._2_1_;
  uVar3 = 0;
  if (DAT_00406a6c != 0) {
    do {
      if (iVar2 == (&DAT_00406748)[uVar3 * 2]) {
        bVar1 = (byte)*(int *)(&DAT_0040674c + uVar3 * 8);
        *(uint *)(&DAT_0040674c + uVar3 * 8) = *(int *)(&DAT_0040674c + uVar3 * 8) + 1U & 0xf;
        goto LAB_00402b8f;
      }
      uVar3 = uVar3 + 1;
    } while (uVar3 < DAT_00406a6c);
  }
  (&DAT_00406748)[DAT_00406a6c * 2] = iVar2;
  bVar1 = (byte)(local_2c4._3_1_ & 0xf);
  *(uint *)(&DAT_0040674c + DAT_00406a6c * 8) = (local_2c4._3_1_ & 0xf) + 1 & 0xf;
  DAT_00406a6c = DAT_00406a6c + 1;
LAB_00402b8f:
  local_2c4._3_1_ = local_2c4._3_1_ ^ (bVar1 ^ local_2c4._3_1_) & 0xf;
  fwrite(&local_2c4,1,0xbc,DAT_00406a68);
  __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
  return;
}



/* 00402be0 FUN_00402be0 */

/* WARNING: Function: __alloca_probe replaced with injection: alloca_probe */

void FUN_00402be0(void)

{
  char cVar1;
  bool bVar2;
  char *pcVar3;
  int iVar4;
  undefined4 extraout_ECX;
  int iVar5;
  int iVar6;
  undefined4 extraout_EDX;
  int iVar7;
  int iVar8;
  int iVar9;
  int *piVar10;
  undefined4 *puVar11;
  int *piVar12;
  undefined4 *puVar13;
  ulonglong uVar14;
  undefined4 auStackY_13b4 [39];
  undefined4 uStackY_1318;
  int local_12e4;
  int local_12e0;
  int local_12d0;
  int local_12cc;
  int local_12c8;
  int local_12c0;
  int local_12bc [301];
  int local_e08 [600];
  char local_4a8 [512];
  char local_2a8 [160];
  char local_208 [112];
  int aiStack_198 [100];
  uint local_8;
  
  local_8 = DAT_00406710 ^ (uint)&stack0xfffffffc;
  piVar12 = local_12bc;
  piVar10 = (int *)register0x00000010;
  for (iVar4 = 0x12d; piVar10 = piVar10 + 1, iVar4 != 0; iVar4 = iVar4 + -1) {
    *piVar12 = *piVar10;
    piVar12 = piVar12 + 1;
  }
  local_12d0 = 0;
  local_12c8 = 0;
  sprintf(local_208,"entering GenerateStream\n");
  FUN_00402270(1,&DAT_00404d40,local_208);
  iVar4 = 0;
  do {
    cVar1 = (&DAT_004072a0)[iVar4];
    local_2a8[iVar4] = cVar1;
    iVar4 = iVar4 + 1;
  } while (cVar1 != '\0');
  DAT_00406a68 = (int)fopen(local_2a8,"wb");
  if ((FILE *)DAT_00406a68 == (FILE *)0x0) {
LAB_00403165:
    fclose((FILE *)DAT_00406a68);
    sprintf(local_4a8,"leaving GenerateStream with result %d\n");
    uStackY_1318 = 0x403198;
    FUN_00402270(1,&DAT_00404d40,local_4a8);
    __security_check_cookie(local_8 ^ (uint)&stack0xfffffffc);
    return;
  }
  uVar14 = FUN_00403200(extraout_ECX,extraout_EDX);
  local_12e0 = 0;
  if (0 < local_12bc[0]) {
    piVar10 = local_e08 + 1;
    piVar12 = local_12bc + 1;
    iVar4 = 0;
    do {
      piVar10[-1] = (int)piVar12;
      printf("\n\r TSP_GenerateStream filename = %s\r\n");
      pcVar3 = strstr(local_2a8,"2K15_MediaSuite");
      iVar5 = *piVar12 * (int)uVar14;
      if (pcVar3 == (char *)0x0) {
        *piVar10 = iVar5 / 5000;
      }
      else {
        *piVar10 = iVar5 / 5000;
      }
      iVar5 = *(int *)piVar12[2];
      piVar10[2] = 0;
      piVar10[3] = 0;
      piVar10[4] = local_12d0;
      aiStack_198[iVar4] = iVar4;
      piVar10[1] = iVar5;
      local_12d0 = local_12d0 + iVar5;
      iVar4 = iVar4 + 1;
      piVar12 = piVar12 + 3;
      piVar10 = piVar10 + 6;
    } while (iVar4 < local_12bc[0]);
  }
  local_12cc = 0;
  local_12c0 = local_12bc[0];
LAB_00402d80:
  if ((local_12e0 < local_12bc[0]) || (local_12c8 < 0x415d)) {
    iVar4 = aiStack_198[local_12cc];
    piVar10 = local_e08 + iVar4 * 6 + 5;
    if (local_12c8 < *piVar10) {
      iVar5 = *piVar10 - local_12c8;
      local_12c8 = local_12c8 + iVar5;
      do {
        FUN_00402a70();
        iVar5 = iVar5 + -1;
      } while (iVar5 != 0);
    }
    iVar5 = local_e08[iVar4 * 6];
    iVar7 = local_e08[iVar4 * 6 + 4];
    local_12e4 = *(int *)(*(int *)(iVar5 + 8) + iVar7 * 8);
    if (0 < local_12e4) {
      local_12c8 = local_12c8 + local_12e4;
      iVar9 = 0;
      do {
        puVar11 = (undefined4 *)(*(int *)(*(int *)(iVar5 + 8) + 4 + iVar7 * 8) + iVar9);
        puVar13 = auStackY_13b4;
        for (iVar6 = 0x2f; iVar6 != 0; iVar6 = iVar6 + -1) {
          *puVar13 = *puVar11;
          puVar11 = puVar11 + 1;
          puVar13 = puVar13 + 1;
        }
        FUN_00402ad0();
        iVar9 = iVar9 + 0xbc;
        local_12e4 = local_12e4 + -1;
      } while (local_12e4 != 0);
    }
    local_e08[iVar4 * 6 + 4] = iVar7 + 1;
    if (iVar7 + 1 == *(int *)(iVar5 + 4)) {
      if (local_e08[iVar4 * 6 + 3] == 0) {
        local_12e0 = local_12e0 + 1;
        local_e08[iVar4 * 6 + 3] = 1;
        sprintf(local_4a8,"Section File Number %d, nr_CompletedSectionStreams %d\n");
        uStackY_1318 = 0x402ebf;
        FUN_00402270(0,&DAT_00404d40,local_4a8);
      }
      local_e08[iVar4 * 6 + 4] = 0;
    }
    iVar5 = local_e08[iVar4 * 6 + 1] + *piVar10;
    if (iVar5 < local_12d0) {
      iVar9 = 0;
      iVar7 = local_12c0;
      if (0 < local_12bc[0]) {
        do {
          iVar7 = (iVar7 + 99) % 100;
          if (local_e08[aiStack_198[iVar7] * 6 + 5] < iVar5) {
            iVar9 = local_12bc[0];
          }
          iVar9 = iVar9 + 1;
        } while (iVar9 < local_12bc[0]);
      }
      do {
        iVar9 = local_e08[aiStack_198[iVar7] * 6 + 5] + local_e08[aiStack_198[iVar7] * 6 + 2];
        iVar7 = (iVar7 + 1) % 100;
        if (iVar9 < local_e08[aiStack_198[iVar7] * 6 + 5]) {
          iVar6 = local_12c0;
          if (iVar7 != local_12c0) goto LAB_00403000;
          break;
        }
      } while (iVar7 != local_12c0);
      *piVar10 = iVar9;
      iVar5 = local_e08[iVar4 * 6 + 2];
      aiStack_198[iVar7] = iVar4;
      iVar4 = iVar5 + iVar9;
    }
    else {
      iVar7 = local_e08[iVar4 * 6 + 2];
      *piVar10 = iVar5;
      aiStack_198[local_12c0] = iVar4;
      iVar4 = iVar7 + iVar5;
    }
    goto LAB_00402f02;
  }
  goto LAB_00403165;
LAB_00403000:
  do {
    iVar8 = (iVar6 + 99) % 100;
    aiStack_198[iVar6] = aiStack_198[iVar8];
    iVar6 = iVar8;
  } while (iVar8 != iVar7);
  if ((iVar5 < iVar9) || (local_e08[aiStack_198[iVar7] * 6 + 5] < local_e08[iVar4 * 6 + 2] + iVar5))
  {
    iVar6 = local_e08[aiStack_198[iVar7] * 6 + 5];
    if (iVar5 == iVar6) {
      *piVar10 = iVar9;
      iVar9 = iVar9 + local_e08[iVar4 * 6 + 2];
      iVar7 = (iVar7 + 1) % 100;
      aiStack_198[iVar7] = iVar4;
    }
    else if (iVar5 < iVar9) {
      *piVar10 = iVar9;
      iVar9 = iVar9 + local_e08[iVar4 * 6 + 2];
      aiStack_198[iVar7] = iVar4;
    }
    else {
      iVar8 = local_e08[iVar4 * 6 + 2];
      if (iVar6 < iVar9 + iVar8) {
        *piVar10 = iVar5;
      }
      else {
        iVar5 = iVar6 - iVar8;
        *piVar10 = iVar5;
      }
      iVar9 = iVar5 + iVar8;
      aiStack_198[iVar7] = iVar4;
    }
    bVar2 = false;
    do {
      iVar7 = (iVar7 + 1) % 100;
      iVar4 = aiStack_198[iVar7];
      if (local_e08[iVar4 * 6 + 5] < iVar9) {
        local_e08[iVar4 * 6 + 5] = iVar9;
        iVar9 = iVar9 + local_e08[iVar4 * 6 + 2];
      }
      else {
        bVar2 = true;
      }
      iVar4 = iVar9;
    } while ((iVar7 != local_12c0) && (iVar4 = local_12d0, !bVar2));
  }
  else {
    *piVar10 = iVar5;
    aiStack_198[iVar7] = iVar4;
    iVar4 = local_12d0;
  }
LAB_00402f02:
  local_12d0 = iVar4;
  local_12cc = (local_12cc + 1) % 100;
  local_12c0 = (local_12c0 + 1) % 100;
  goto LAB_00402d80;
}



/* 004031b0 __security_check_cookie */

/* Library Function - Single Match
    @__security_check_cookie@4
   
   Libraries: Visual Studio 2005 Release, Visual Studio 2008 Release, Visual Studio 2010 Release
   __fastcall __security_check_cookie,4 */

void __fastcall __security_check_cookie(int param_1)

{
  if (param_1 == DAT_00406710) {
    return;
  }
                    /* WARNING: Subroutine does not return */
  ___report_gsfailure();
}



/* 004031c0 memset */

void * __cdecl memset(void *_Dst,int _Val,size_t _Size)

{
  void *pvVar1;
  
                    /* WARNING: Could not recover jumptable at 0x004031c0. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  pvVar1 = memset(_Dst,_Val,_Size);
  return pvVar1;
}



/* 004031d0 __alloca_probe */

/* WARNING: This is an inlined function */

void __alloca_probe(void)

{
  undefined1 *in_EAX;
  undefined4 *puVar1;
  undefined4 *puVar2;
  undefined4 unaff_retaddr;
  undefined1 auStack_4 [4];
  
  puVar2 = (undefined4 *)((int)&stack0x00000000 - (int)in_EAX & ~-(uint)(&stack0x00000000 < in_EAX))
  ;
  for (puVar1 = (undefined4 *)((uint)auStack_4 & 0xfffff000); puVar2 < puVar1;
      puVar1 = puVar1 + -0x400) {
  }
  *puVar2 = unaff_retaddr;
  return;
}



/* 00403200 FUN_00403200 */

ulonglong __fastcall FUN_00403200(undefined4 param_1,undefined4 param_2)

{
  ulonglong uVar1;
  uint uVar2;
  float fVar3;
  float10 in_ST0;
  uint local_20;
  float fStack_1c;
  
  if (DAT_00407540 == 0) {
    uVar1 = (ulonglong)ROUND(in_ST0);
    local_20 = (uint)uVar1;
    fStack_1c = (float)(uVar1 >> 0x20);
    fVar3 = (float)in_ST0;
    if ((local_20 != 0) || (fVar3 = fStack_1c, (uVar1 & 0x7fffffff00000000) != 0)) {
      if ((int)fVar3 < 0) {
        uVar1 = uVar1 + (0x80000000 < (uint)-(float)(in_ST0 - (float10)(longlong)uVar1));
      }
      else {
        uVar2 = (uint)(0x80000000 < (uint)(float)(in_ST0 - (float10)(longlong)uVar1));
        uVar1 = CONCAT44((int)fStack_1c - (uint)(local_20 < uVar2),local_20 - uVar2);
      }
    }
    return uVar1;
  }
  return CONCAT44(param_2,(int)in_ST0);
}



/* 004032f6 ___tmainCRTStartup */

/* WARNING: Function: __SEH_prolog4 replaced with injection: SEH_prolog4 */
/* WARNING: Function: __SEH_epilog4 replaced with injection: EH_epilog3 */
/* Library Function - Single Match
    ___tmainCRTStartup
   
   Library: Visual Studio 2010 Release */

int ___tmainCRTStartup(void)

{
  bool bVar1;
  void *Exchange;
  void *pvVar2;
  int iVar3;
  BOOL BVar4;
  
  if (DAT_00407544 == 0) {
    HeapSetInformation((HANDLE)0x0,HeapEnableTerminationOnCorruption,(PVOID)0x0,0);
  }
  Exchange = StackBase;
  bVar1 = false;
  do {
    pvVar2 = (void *)InterlockedCompareExchange((LONG *)&DAT_00407534,(LONG)Exchange,0);
    if (pvVar2 == (void *)0x0) {
LAB_00403354:
      if (DAT_00407530 == 1) {
        _amsg_exit(0x1f);
      }
      else if (DAT_00407530 == 0) {
        DAT_00407530 = 1;
        iVar3 = initterm_e(&DAT_004040ec,&DAT_004040fc);
        if (iVar3 != 0) {
          return 0xff;
        }
      }
      else {
        DAT_00406a8c = 1;
      }
      if (DAT_00407530 == 1) {
        initterm(&DAT_004040e0,&DAT_004040e8);
        DAT_00407530 = 2;
      }
      if (!bVar1) {
        InterlockedExchange((LONG *)&DAT_00407534,0);
      }
      if ((DAT_00407548 != (code *)0x0) &&
         (BVar4 = __IsNonwritableInCurrentImage((PBYTE)&DAT_00407548), BVar4 != 0)) {
        (*DAT_00407548)(0,2,0);
      }
      *(undefined4 *)__initenv_exref = DAT_00406a74;
      DAT_00406a88 = FUN_00402710(DAT_00406a70,DAT_00406a78);
      if (DAT_00406a7c != 0) {
        if (DAT_00406a8c == 0) {
          _cexit();
        }
        return DAT_00406a88;
      }
                    /* WARNING: Subroutine does not return */
      exit(DAT_00406a88);
    }
    if (pvVar2 == Exchange) {
      bVar1 = true;
      goto LAB_00403354;
    }
    Sleep(1000);
  } while( true );
}



/* 00403539 entry */

void entry(void)

{
  ___security_init_cookie();
  ___tmainCRTStartup();
  return;
}



/* 00403543 ___report_gsfailure */

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
  
  _DAT_00406ba8 =
       (uint)(in_NT & 1) * 0x4000 | (uint)SBORROW4((int)&stack0xfffffffc,0x328) * 0x800 |
       (uint)(in_IF & 1) * 0x200 | (uint)(in_TF & 1) * 0x100 | (uint)((int)&local_32c < 0) * 0x80 |
       (uint)(&stack0x00000000 == (undefined1 *)0x32c) * 0x40 | (uint)(in_AF & 1) * 0x10 |
       (uint)((POPCOUNT((uint)&local_32c & 0xff) & 1U) == 0) * 4 |
       (uint)(&stack0xfffffffc < (undefined1 *)0x328) | (uint)(in_ID & 1) * 0x200000 |
       (uint)(in_VIP & 1) * 0x100000 | (uint)(in_VIF & 1) * 0x80000 | (uint)(in_AC & 1) * 0x40000;
  _DAT_00406bac = &stack0x00000004;
  _DAT_00406ae8 = 0x10001;
  _DAT_00406a90 = 0xc0000409;
  _DAT_00406a94 = 1;
  local_32c = DAT_00406710;
  local_328 = DAT_00406714;
  _DAT_00406a9c = unaff_retaddr;
  _DAT_00406b74 = in_GS;
  _DAT_00406b78 = in_FS;
  _DAT_00406b7c = in_ES;
  _DAT_00406b80 = in_DS;
  _DAT_00406b84 = unaff_EDI;
  _DAT_00406b88 = unaff_ESI;
  _DAT_00406b8c = unaff_EBX;
  _DAT_00406b90 = in_EDX;
  _DAT_00406b94 = in_ECX;
  _DAT_00406b98 = in_EAX;
  _DAT_00406b9c = unaff_EBP;
  DAT_00406ba0 = unaff_retaddr;
  _DAT_00406ba4 = in_CS;
  _DAT_00406bb0 = in_SS;
  DAT_00406ae0 = IsDebuggerPresent();
  _crt_debugger_hook(1);
  SetUnhandledExceptionFilter((LPTOP_LEVEL_EXCEPTION_FILTER)0x0);
  UnhandledExceptionFilter((_EXCEPTION_POINTERS *)&PTR_DAT_00404e40);
  if (DAT_00406ae0 == 0) {
    _crt_debugger_hook(1);
  }
  uExitCode = 0xc0000409;
  hProcess = GetCurrentProcess();
  TerminateProcess(hProcess,uExitCode);
  return;
}



/* 00403659 __CxxUnhandledExceptionFilter */

/* Library Function - Single Match
    long __stdcall __CxxUnhandledExceptionFilter(struct _EXCEPTION_POINTERS *)
   
   Libraries: Visual Studio 2008 Release, Visual Studio 2010 Release */

long __CxxUnhandledExceptionFilter(_EXCEPTION_POINTERS *param_1)

{
  PEXCEPTION_RECORD pEVar1;
  ULONG_PTR UVar2;
  
  pEVar1 = param_1->ExceptionRecord;
  if (((pEVar1->ExceptionCode == 0xe06d7363) && (pEVar1->NumberParameters == 3)) &&
     ((UVar2 = pEVar1->ExceptionInformation[0], UVar2 == 0x19930520 ||
      (((UVar2 == 0x19930521 || (UVar2 == 0x19930522)) || (UVar2 == 0x1994000)))))) {
                    /* WARNING: Subroutine does not return */
    terminate();
  }
  return 0;
}



/* 004036aa _amsg_exit */

void __cdecl _amsg_exit(int param_1)

{
                    /* WARNING: Could not recover jumptable at 0x004036aa. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _amsg_exit(param_1);
  return;
}



/* 004036b0 __onexit */

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
  
  local_8 = &DAT_00404ec8;
  uStack_c = 0x4036bc;
  local_20[0] = DecodePointer(DAT_0040753c);
  if (local_20[0] == (PVOID)0xffffffff) {
    p_Var1 = _onexit(param_1);
  }
  else {
    _lock(8);
    local_8 = (undefined *)0x0;
    local_20[0] = DecodePointer(DAT_0040753c);
    local_24 = DecodePointer(DAT_00407538);
    ppvVar4 = &local_24;
    ppvVar3 = local_20;
    pvVar2 = EncodePointer(param_1);
    p_Var1 = (_onexit_t)__dllonexit(pvVar2,ppvVar3,ppvVar4);
    DAT_0040753c = EncodePointer(local_20[0]);
    DAT_00407538 = EncodePointer(local_24);
    local_8 = (undefined *)0xfffffffe;
    FUN_00403748();
  }
  return p_Var1;
}



/* 00403748 FUN_00403748 */

void FUN_00403748(void)

{
  _unlock(8);
  return;
}



/* 00403751 _atexit */

/* Library Function - Single Match
    _atexit
   
   Library: Visual Studio 2010 Release */

int __cdecl _atexit(_func_4879 *param_1)

{
  _onexit_t p_Var1;
  
  p_Var1 = __onexit((_onexit_t)param_1);
  return (p_Var1 != (_onexit_t)0x0) - 1;
}



/* 00403768 FUN_00403768 */

/* WARNING: Removing unreachable block (ram,0x0040377c) */
/* WARNING: Removing unreachable block (ram,0x00403782) */
/* WARNING: Removing unreachable block (ram,0x00403784) */

void FUN_00403768(void)

{
  return;
}



/* 004037b4 _XcptFilter */

int __cdecl _XcptFilter(ulong _ExceptionNum,_EXCEPTION_POINTERS *_ExceptionPtr)

{
  int iVar1;
  
                    /* WARNING: Could not recover jumptable at 0x004037b4. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  iVar1 = _XcptFilter(_ExceptionNum,_ExceptionPtr);
  return iVar1;
}



/* 004037c0 __ValidateImageBase */

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



/* 00403800 __FindPESection */

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



/* 00403850 __IsNonwritableInCurrentImage */

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
  
  pcStack_10 = FUN_00403979;
  local_14 = ExceptionList;
  local_c = DAT_00406710 ^ 0x404ee8;
  ExceptionList = &local_14;
  local_8 = 0;
  BVar1 = __ValidateImageBase((PBYTE)&IMAGE_DOS_HEADER_00400000);
  if (BVar1 != 0) {
    p_Var2 = __FindPESection((PBYTE)&IMAGE_DOS_HEADER_00400000,(DWORD_PTR)(pTarget + -0x400000));
    if (p_Var2 != (PIMAGE_SECTION_HEADER)0x0) {
      ExceptionList = local_14;
      return ~(p_Var2->Characteristics >> 0x1f) & 1;
    }
  }
  ExceptionList = local_14;
  return 0;
}



/* 0040390c initterm */

void __cdecl initterm(void)

{
                    /* WARNING: Could not recover jumptable at 0x0040390c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  initterm();
  return;
}



/* 00403912 initterm_e */

void __cdecl initterm_e(void)

{
                    /* WARNING: Could not recover jumptable at 0x00403912. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  initterm_e();
  return;
}



/* 00403920 __SEH_prolog4 */

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
  *(uint *)((int)auStack_1c + iVar1 + 4) = DAT_00406710 ^ (uint)&param_2;
  *(undefined4 *)((int)auStack_1c + iVar1) = unaff_retaddr;
  ExceptionList = local_8;
  return;
}



/* 00403965 __SEH_epilog4 */

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



/* 00403979 FUN_00403979 */

void __cdecl
FUN_00403979(undefined4 param_1,undefined4 param_2,undefined4 param_3,undefined4 param_4)

{
  except_handler4_common(&DAT_00406710,__security_check_cookie,param_1,param_2,param_3,param_4);
  return;
}



/* 0040399e FUN_0040399e */

void FUN_0040399e(void)

{
  errno_t eVar1;
  
  eVar1 = _controlfp_s((uint *)0x0,0x10000,0x30000);
  if (eVar1 != 0) {
                    /* WARNING: Subroutine does not return */
    _invoke_watson((wchar_t *)0x0,(wchar_t *)0x0,(wchar_t *)0x0,0,0);
  }
  return;
}



/* 004039c6 FUN_004039c6 */

undefined4 FUN_004039c6(void)

{
  return 0;
}



/* 004039c9 ___security_init_cookie */

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
  if ((DAT_00406710 == 0xbb40e64e) || ((DAT_00406710 & 0xffff0000) == 0)) {
    GetSystemTimeAsFileTime(&local_c);
    uVar4 = local_c.dwHighDateTime ^ local_c.dwLowDateTime;
    DVar1 = GetCurrentProcessId();
    DVar2 = GetCurrentThreadId();
    DVar3 = GetTickCount();
    QueryPerformanceCounter(&local_14);
    DAT_00406710 = uVar4 ^ DVar1 ^ DVar2 ^ DVar3 ^ local_14.s.HighPart ^ local_14.s.LowPart;
    if (DAT_00406710 == 0xbb40e64e) {
      DAT_00406710 = 0xbb40e64f;
    }
    else if ((DAT_00406710 & 0xffff0000) == 0) {
      DAT_00406710 = DAT_00406710 | (DAT_00406710 | 0x4711) << 0x10;
    }
  }
  DAT_00406714 = ~DAT_00406710;
  return;
}



/* 00403a64 _crt_debugger_hook */

void __cdecl _crt_debugger_hook(int param_1)

{
                    /* WARNING: Could not recover jumptable at 0x00403a64. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _crt_debugger_hook(param_1);
  return;
}



/* 00403a6a terminate */

void __cdecl terminate(void)

{
                    /* WARNING: Could not recover jumptable at 0x00403a6a. Too many branches */
                    /* WARNING: Subroutine does not return */
                    /* WARNING: Treating indirect jump as call */
  terminate();
  return;
}



/* 00403a70 _unlock */

void __cdecl _unlock(int _File)

{
                    /* WARNING: Could not recover jumptable at 0x00403a70. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _unlock(_File);
  return;
}



/* 00403a76 __dllonexit */

void __dllonexit(void)

{
                    /* WARNING: Could not recover jumptable at 0x00403a76. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  __dllonexit();
  return;
}



/* 00403a7c _lock */

void __cdecl _lock(int _File)

{
                    /* WARNING: Could not recover jumptable at 0x00403a7c. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  _lock(_File);
  return;
}



/* 00403a82 except_handler4_common */

void __cdecl except_handler4_common(void)

{
                    /* WARNING: Could not recover jumptable at 0x00403a82. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  except_handler4_common();
  return;
}



/* 00403a88 _invoke_watson */

void __cdecl
_invoke_watson(wchar_t *param_1,wchar_t *param_2,wchar_t *param_3,uint param_4,uintptr_t param_5)

{
                    /* WARNING: Could not recover jumptable at 0x00403a88. Too many branches */
                    /* WARNING: Subroutine does not return */
                    /* WARNING: Treating indirect jump as call */
  _invoke_watson(param_1,param_2,param_3,param_4,param_5);
  return;
}



/* 00403a8e _controlfp_s */

errno_t __cdecl _controlfp_s(uint *_CurrentState,uint _NewValue,uint _Mask)

{
  errno_t eVar1;
  
                    /* WARNING: Could not recover jumptable at 0x00403a8e. Too many branches */
                    /* WARNING: Treating indirect jump as call */
  eVar1 = _controlfp_s(_CurrentState,_NewValue,_Mask);
  return eVar1;
}


