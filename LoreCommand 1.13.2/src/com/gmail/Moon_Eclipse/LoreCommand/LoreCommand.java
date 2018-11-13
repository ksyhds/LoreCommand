package com.gmail.Moon_Eclipse.LoreCommand;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.Moon_Eclipse.LoreCommand.Commands.Commands;
import com.gmail.Moon_Eclipse.LoreCommand.Event_Listener.PlayerInteract;
import com.gmail.Moon_Eclipse.LoreCommand.Util.LCUtil;

public class LoreCommand extends JavaPlugin
{
	// config 파일을 저장해둘 변수 선언
	public static Configuration c;
	
	// group 파일을 저장해둘 변수 선언
	File Group_File;
	
	// 파일을 기반으로 얻은 config파일을 저장해둘 변수를 선언
	public static FileConfiguration Group_Config;
	
	// 인스턴스 반환을 위한 변수 선언
	private static LoreCommand instance;
	
	// 플러그인이 켜지면서 실행할 작업을 작성
	public void onEnable()
	{
		/*
		 * 사용되는 네이티브 이벤트 목록
		 * PlayerInteractEvent			- 플레이어가 좌클릭/우클릭을 한 경우
		 * 
		*/
		
		// 기본 컨피스 파일을 생성
		this.saveDefaultConfig();
		
		// 기본 그룹 컨피그 파일을 생성
		this.saveDefaltGroup();
		
		// 인스턴스 반환을 위한 변수 초기화
		instance = this;
		
		// 서버에 커스텀 이벤트를 등록
		AddEvent(new PlayerInteract());
		
		// 커맨드 클래스 생성
		getCommand("lc").setExecutor(new Commands(this));
		
		// 서버를 켜면서 config 설정을 메모리에 저장
		Plugin_Reload();
	}
	
	// 서버가 꺼지면서 플러그인이 해야할 일을 작성
	public void onDisable(){}
	
	
	// 이벤트 추가 메소드
	public void AddEvent(Listener Event)
	{
		// 이벤트 추가
		Bukkit.getPluginManager().registerEvents(Event, this);
	}
	
	// 리로드 시에 사용될 메소드,
	public void Plugin_Reload()
	{
		// config 파일 리로
		this.reloadConfig();
		
		// config 데이터를 불러옴
		c = this.getConfig();
		
		// group 파일 데이터를 새로 불러옴
		Group_File = new File(getDataFolder(), "group.yml");
		
		// group 파일을 기반으로 config 파일 생성
		Group_Config = YamlConfiguration.loadConfiguration(Group_File);
		
		// 그룹의 각 이름을 리스트화해서 정리
		LCUtil.setGroupNameList_FromConfig(Group_Config);
		
		// config 파일의 각 항목을 리스트화해서 정리
		LCUtil.setLoreNameList_FromConfig(c);
	}
	
	// config 파일이 있으면 불러오고 없으면 새로 만드는 메소드 생성
	public void saveDefaltGroup()
	{
		if (Group_File == null)
		   {
			Group_File = new File(getDataFolder(), "group.yml");
		   }
		   if (!Group_File.exists())
		   {            
			   this.saveResource("group.yml", true);
		   }
	}
	
	// 플러그인의 인스턴스를 반환하는 메소드 생성
	public static LoreCommand getInstance()
	{
		return instance;
	}
}
