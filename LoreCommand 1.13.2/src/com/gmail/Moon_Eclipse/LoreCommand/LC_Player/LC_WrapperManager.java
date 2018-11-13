package com.gmail.Moon_Eclipse.LoreCommand.LC_Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class LC_WrapperManager implements Listener
{
	// RIA 플레이어와 기존 플레이어의 연결을 위해 맵 생성
	public static Map<String, LCPlayer> LCPlayerMap;
	public static Plugin plugin;
	
	//생성자 선언
	public LC_WrapperManager()
	{
		// 래퍼 매니저 객체를 사용할때 맵을 초기화
		LCPlayerMap = new HashMap<String, LCPlayer>();
	}
	// 플레이어를 통해 RIA 플레이어를 얻어와 반환함.
	public static LCPlayer getLCPlayer(String Player_Name)
	{
		return LCPlayerMap.get(Player_Name);
	}
	// 맵의 정보를 온라인 중인 플레이어만으로 다시 채워주는 함수
	public void wrapCollection(Collection<? extends Player> players)
	{
		clear();
		players.stream().forEach(p -> LCPlayerMap.put(p.getName(), new LCPlayer(p)));
	}
	// 실질적으로 사용되는 리필 함수
	public void WrapAllonline()
	{
		wrapCollection(Bukkit.getOnlinePlayers());
	}
	@EventHandler(priority=EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent event)
	{
		// 플레이어가 로그인시 맵에 데이터를 저장
		LCPlayerMap.put(event.getPlayer().getName(), new LCPlayer(event.getPlayer()));
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		// 플레이어가 로그아웃시 맵에서 정보를 삭제
		LCPlayerMap.remove(event.getPlayer());
	}
	public void clear()
	{
		// 맵을 초기화.
		LCPlayerMap.clear();
	}
	public static Map<String, LCPlayer> getLCPlayerMap()
	{
		return LCPlayerMap;
	}
	
}
