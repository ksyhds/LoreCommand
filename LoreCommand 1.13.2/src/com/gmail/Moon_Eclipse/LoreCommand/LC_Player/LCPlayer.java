package com.gmail.Moon_Eclipse.LoreCommand.LC_Player;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.Moon_Eclipse.LoreCommand.Util.LCUtil;

public class LCPlayer 
{
	// 마인크래프트 플레이어 데이터 저장공간을 생성
	public final Player MineCraftPlayer;
	
	// 쿨타임들을 저장해둘 변수를 생성
	public Map<String, Long> Group_Cooltime_Map;
	
	// 생성자를 통해 기본 변수를 초기화
	public LCPlayer(Player p)
	{
		// 생성된 객체에 해당하는 플레이어 정보를 저장
		MineCraftPlayer = p;
		
		// 객체 생성과 동시에 맵 초기화
		Group_Cooltime_Map = new HashMap<String,Long>();
		
		// 맵의 빈 내용을 채움
		Initialize_Group_Cooltime_Map();
	}
	
	// 플레이어의 쿨타임 값을 전부 초기화
	public void Initialize_Group_Cooltime_Map()
	{
		// 그룹 쿨타임 맵 초기화
		
		Group_Cooltime_Map.clear();
		
		List<String> list = LCUtil.Group_Names;
		
		for(String item : list)
		{
			Group_Cooltime_Map.put(item, (long) 0.0);
		}
		
	}
	
	// 쿨타임 시간을 반환하는 메소드
	public long getCooledTime(String Section_Name)
	{
		if(Section_Name.contains("일반스킬"))
		{
			return LCUtil.get_CooledTime_From_RIA(MineCraftPlayer);
		}
		return Group_Cooltime_Map.get(Section_Name);
	}
	// 쿨타임 시간을 생성하고 저장하는 메소드
	public void setCooledTime(String Group_Name, long second)
	{
		// 입력값을 ms단위로 변환하여 저장
		long Casted_Second = (long) (second * 1000.0);
		
		// 쿨타임이 끝날 시간을 계산 후 저장
		long CooledTime = new Date().getTime() + Casted_Second;
		
		// 맵에 저장
		Group_Cooltime_Map.put(Group_Name, CooledTime);
	}
}
