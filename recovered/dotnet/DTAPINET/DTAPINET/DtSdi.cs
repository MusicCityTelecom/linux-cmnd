using System;
using System.Runtime.CompilerServices;
using System.Runtime.ExceptionServices;
using System.Runtime.InteropServices;
using Dtapi;

namespace DTAPINET;

public class DtSdi : IDisposable
{
	private unsafe Dtapi.DtSdi* m_pDtSdi;

	public unsafe DTAPI_RESULT ConvertFrame(uint[] rInFrame, ref int InFrameSize, int InFrameFormat, uint[] rOutFrame, ref int OutFrameSize, int OutFrameFormat)
	{
		fixed (uint* ptr = &rInFrame[0])
		{
			fixed (uint* ptr2 = &rOutFrame[0])
			{
				int num = InFrameSize;
				int num2 = OutFrameSize;
				uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EConvertFrame(m_pDtSdi, ptr, &num, InFrameFormat, ptr2, &num2, OutFrameFormat);
				InFrameSize = num;
				OutFrameSize = num2;
				return (DTAPI_RESULT)result;
			}
		}
	}

	public unsafe DTAPI_RESULT CreateBlackFrame(uint[] rFrame, ref int FrameSize, int FrameFormat)
	{
		fixed (uint* ptr = &rFrame[0])
		{
			int num = FrameSize;
			uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002ECreateBlackFrame(m_pDtSdi, ptr, &num, FrameFormat);
			FrameSize = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT GetActiveVideo(ushort[] rVideo, ref int NumSamples, int Field)
	{
		fixed (ushort* ptr = &rVideo[0])
		{
			int num = NumSamples;
			uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EGetActiveVideo(m_pDtSdi, ptr, &num, Field, -1);
			NumSamples = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT GetActiveVideo(ushort[] rVideo, ref int NumSamples, int Field, int Stride)
	{
		fixed (ushort* ptr = &rVideo[0])
		{
			int num = NumSamples;
			uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EGetActiveVideo(m_pDtSdi, ptr, &num, Field, Stride);
			NumSamples = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT GetActiveVideo(ref DtSdiTocEntry TocEntry, ushort[] rVideo, ref int NumSamples)
	{
		fixed (ushort* ptr = &rVideo[0])
		{
			int num = NumSamples;
			uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EGetActiveVideo(m_pDtSdi, TocEntry.m_pSdiTocEntry, ptr, &num);
			NumSamples = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT GetAncillaryData(ref DtSdiTocEntry TocEntry, ushort[] rVideo, ref int NumSamples)
	{
		fixed (ushort* ptr = &rVideo[0])
		{
			int num = NumSamples;
			uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EGetAncillaryData(m_pDtSdi, TocEntry.m_pSdiTocEntry, ptr, &num);
			NumSamples = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT GetAudio(int AudioGroup, ref int Channel, ushort[] rAudio, ref int NumSamples)
	{
		fixed (ushort* ptr = &rAudio[0])
		{
			int num = NumSamples;
			int num2 = Channel;
			uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EGetAudio(m_pDtSdi, AudioGroup, &num2, ptr, &num);
			Channel = num2;
			NumSamples = num;
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DTAPI_RESULT GetTableOfContents(ref DtSdiTocEntry[] rTocs)
	{
		int num = 0;
		System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtSdiTocEntry* ptr);
		uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EGetTableOfContents(m_pDtSdi, &ptr, &num);
		rTocs = new DtSdiTocEntry[num];
		int num2 = 0;
		if (0 < num)
		{
			int num3 = 0;
			do
			{
				rTocs[num2] = new DtSdiTocEntry((Dtapi.DtSdiTocEntry*)(num3 + (byte*)ptr));
				num2++;
				num3 += 40;
			}
			while (num2 < num);
		}
		return (DTAPI_RESULT)result;
	}

	public unsafe DTAPI_RESULT ParseFrame(uint[] rFrame, int FrameSize, int FrameFormat, int ParseFlags, ref DtSdiTocEntry[] rTocs)
	{
		fixed (uint* ptr = &rFrame[0])
		{
			int num = 0;
			System.Runtime.CompilerServices.Unsafe.SkipInit(out Dtapi.DtSdiTocEntry* ptr2);
			uint result = global::_003CModule_003E.Dtapi_002EDtSdi_002EParseFrame(m_pDtSdi, ptr, FrameSize, FrameFormat, ParseFlags, &ptr2, &num);
			rTocs = new DtSdiTocEntry[num];
			int num2 = 0;
			if (0 < num)
			{
				int num3 = 0;
				do
				{
					rTocs[num2] = new DtSdiTocEntry((Dtapi.DtSdiTocEntry*)(num3 + (byte*)ptr2));
					num2++;
					num3 += 40;
				}
				while (num2 < num);
			}
			return (DTAPI_RESULT)result;
		}
	}

	public unsafe DtSdi()
	{
		Dtapi.DtSdi* ptr = (Dtapi.DtSdi*)global::_003CModule_003E.@new(8u);
		Dtapi.DtSdi* pDtSdi;
		try
		{
			pDtSdi = ((ptr == null) ? null : global::_003CModule_003E.Dtapi_002EDtSdi_002E_007Bctor_007D(ptr));
		}
		catch
		{
			//try-fault
			global::_003CModule_003E.delete(ptr, 8u);
			throw;
		}
		m_pDtSdi = pDtSdi;
	}

	private void _007EDtSdi()
	{
		_0021DtSdi();
	}

	private unsafe void _0021DtSdi()
	{
		Dtapi.DtSdi* pDtSdi = m_pDtSdi;
		if (pDtSdi != null)
		{
			Dtapi.DtSdi* ptr = pDtSdi;
			((delegate* unmanaged[Thiscall, Thiscall]<IntPtr, uint, void*>)(int)(*(uint*)(int)(*(uint*)ptr)))((nint)ptr, 1u);
			m_pDtSdi = null;
		}
	}

	[HandleProcessCorruptedStateExceptions]
	protected virtual void Dispose([MarshalAs(UnmanagedType.U1)] bool A_0)
	{
		if (A_0)
		{
			_0021DtSdi();
			return;
		}
		try
		{
			_0021DtSdi();
		}
		finally
		{
			base.Finalize();
		}
	}

	public virtual sealed void Dispose()
	{
		Dispose(A_0: true);
		GC.SuppressFinalize(this);
	}

	~DtSdi()
	{
		Dispose(A_0: false);
	}
}
