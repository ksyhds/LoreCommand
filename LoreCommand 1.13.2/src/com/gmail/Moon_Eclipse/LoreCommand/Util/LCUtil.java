package com.gmail.Moon_Eclipse.LoreCommand.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.Moon_Eclipse.LoreCommand.LoreCommand;
import com.gmail.Moon_Eclipse.LoreCommand.LC_Player.LCPlayer;
import com.gmail.Moon_Eclipse.LoreCommand.LC_Player.LC_WrapperManager;
import com.gmail.Moon_Eclipse.RIA.RIA_Player.RIAPlayer;
import com.gmail.Moon_Eclipse.RIA.RIA_Player.WrapperManager;

public class LCUtil 
{
	// group.yml 파일의 그룹 이름을 저장해두기 위한 변수 선언
	public static List<String> Group_Names = new ArrayList<String>();
	
	// config.yml 파일의 항목 이름을 저장해두기 위한 변수 선언
	public static List<String> Lore_Names = new ArrayList<String>();
	
	
	// group.yml 파일의 그룹 이름을 변수에 저장해주는 메소드 선언
	public static void setGroupNameList_FromConfig(Configuration c)
	{
		// 그룹이름 목록 초기화
		Group_Names = new ArrayList<String>();
		
		// 그룹 이름 set을 얻어옴
		Set<String> list = c.getConfigurationSection("group").getKeys(false);
		
		// 그룹 이름을 리스트에 저장
		for(String name : list)
		{
			Group_Names.add(name);
		}
	}
	// config.yml 파일의 항목 이름을 저장해주는 메소드 선언
	public static void setLoreNameList_FromConfig(Configuration c)
	{
		// 그룹이름 목록 초기화
		Lore_Names = new ArrayList<String>();
		
		// 그룹 이름 set을 얻어옴
		Set<String> list = c.getConfigurationSection("config").getKeys(false);
		
		// 그룹 이름을 리스트에 저장
		for(String name : list)
		{
			Lore_Names.add(name);
		}
	}
	
	// RIA의 플레이어 데이터를 기반으로 플레이어가 스킬을 사용할 수 있는지 여부를 확인
	public static long get_CooledTime_From_RIA(Player player)
	{
		// RIAPlayer 값을 얻어옴
		RIAPlayer RIAp = WrapperManager.getRIAPlayer(player.getName());
		
		// 플레이어의 쿨타임을 얻어옴
		
		
		return RIAp.getCooledTime();
	}
	
	public static void Execute_Command(String hand, Player player, ItemStack HandItem)
	{
		// 커맨드 섹션 이름을 받아와 저장
		String Section_Name = getComand_Section_Name(HandItem);
		
		// 만약 섹션 이름이 null 이라면
		if(Section_Name.equalsIgnoreCase("null"))
		{
			// 계산 중지
			return;
		}
		
		// 왼손용인지 오른손 용인지 확인을 위해 config에서 정보를 가져옴
		String Hand_Type = LoreCommand.c.getString("config." + Section_Name + ".hand");
		
		// 만약 다른손이라면
		if(!hand.equalsIgnoreCase(Hand_Type))
		{
			// 계산 중지
			return;
		}
		// 같은 손이라면
		else
		{
			// 기록된 쿨타임을 가져옴
			// LCPlayer 의쿨타임 맵에서 정보를 가져옴
			LCPlayer LCp = LC_WrapperManager.getLCPlayer(player.getName());
			
			// 쿨타임이 끝날 시간 정보를 가져옴
			long CooledTime = LCp.getCooledTime(Section_Name);
			
			// 만약 쿨타임이 끝났지 않았다면
			long date_value = new Date().getTime();
			
			if(CooledTime > date_value)
			{
				return;
			}
			//끝났다면
			else
			{
				// 커맨드 목록을 받아와 저장
				List<String> commands = LoreCommand.c.getStringList("config." + Section_Name + ".commands");
				
				// op권한을 주기위해 논리형 변수 선언
				boolean removeOp = new Boolean(player.isOp());
				
				// 플레이어를 op로 설정
				player.setOp(true);
				
				// 커맨드 목록을 돌려가며 커맨드 실행
				for(String command : commands)
				{
					String pcommand = command.replaceAll("PLAYER", player.getName());
					player.performCommand(pcommand);
				}
				
				// 플레이어 op 해제
				player.setOp(removeOp);
				
				// 새로운 쿨타임을 계산해서 저장
				
				// 해당 그룹 이름을 얻어와 저장
				String Group_Name = LoreCommand.c.getString("config." + Section_Name + ".group");
				
				// 그룹의 쿨타임을 얻어와 저장
				long Cool_Second = LoreCommand.Group_Config.getLong("group." + Group_Name + ".cooldown");
				
				// 쿨타임 섫정
				LCp.setCooledTime(Group_Name, Cool_Second);
			}
		}
		
		
		
	}
	// 커맨드 섹션의 이름을 얻어옴 
	public static String getComand_Section_Name(ItemStack HandItem)
	{
		// 아이템의 로어를 얻어옴
		List<String> lores = HandItem.getItemMeta().getLore();
		
		// 그룹 이름을 하나씩 불러와 검사
		for(String name : Lore_Names)
		{
			// 검색용 문구를 저장
			String Key_lore = LoreCommand.c.getString("config." + name + ".lore").replace("&", "§");
			
			// 아이템의 로어를 불러와 검색
			for(String lore : lores)
			{
				// 만약 로어 안에 검색용문구가 포함되어있다면
				if(lore.contains(Key_lore))
				{
					// 섹션 이름을 반환
					return name;
				}
			}
			
		}
		// 섹션을 찾지 못했으므로 null을 반환
		return "null";
	}
	
}
