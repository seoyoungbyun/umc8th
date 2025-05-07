package umc_8th.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import umc_8th.spring.domain.enums.MissionStatus;
import umc_8th.spring.service.MemberService.MemberQueryService;
import umc_8th.spring.service.MemberService.MemberQueryServiceImpl;
import umc_8th.spring.service.MissionService.MissionQueryService;
import umc_8th.spring.service.StoreService.StoreQueryService;
import umc_8th.spring.web.dto.HomeDTO;
import umc_8th.spring.web.dto.MyPageDTO;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			StoreQueryService storeService = context.getBean(StoreQueryService.class);

			// 파라미터 값 설정
			String name = "요아정";
			Double score = 4.0;

			// 쿼리 메서드 호출 및 쿼리 문자열과 파라미터 출력
			System.out.println("Executing findStoresByNameAndScore with parameters:");
			System.out.println("Name: " + name);
			System.out.println("Score: " + score);

			storeService.findStoresByNameAndScore(name, score)
					.forEach(System.out::println);
		};

//	@Bean
//	public CommandLineRunner run(ApplicationContext context) {
//		return args -> {
//			MissionQueryService missionService = context.getBean(MissionQueryService.class);
//
//			// 파라미터 값 설정
//			Long memberId = 1L;
//			MissionStatus status = MissionStatus.COMPLETE;
//
//			// 쿼리 메서드 호출 및 쿼리 문자열과 파라미터 출력
//			System.out.println("Executing findMissionByMissionStatus with parameters:");
//			System.out.println("Member: " + memberId);
//			System.out.println("Status: " + status);
//
//			missionService.findMissionByMissionStatus(memberId, null, status)
//					.forEach(System.out::println);
//		};
//	@Bean
//	public CommandLineRunner run(ApplicationContext context) {
//		return args -> {
//			MissionQueryService missionService = context.getBean(MissionQueryService.class);
//
//			// 파라미터 값 설정
//			Long memberId = 1L;
//			Long regionId = 1L;
//
//			// 쿼리 메서드 호출 및 쿼리 문자열과 파라미터 출력
//			System.out.println("Executing getHome with parameters:");
//			System.out.println("member: " + memberId);
//			System.out.println("Region: " + regionId);
//
//			HomeDTO home = missionService.getHome(memberId, regionId, null);
//			System.out.println(home);
//		};
//	@Bean
//	public CommandLineRunner run(ApplicationContext context) {
//		return args -> {
//			MemberQueryService memberService = context.getBean(MemberQueryService.class);
//
//			// 파라미터 값 설정
//			Long memberId = 1L;
//
//			// 쿼리 메서드 호출 및 쿼리 문자열과 파라미터 출력
//			System.out.println("Executing getHome with parameters:");
//			System.out.println("member: " + memberId);
//
//			MyPageDTO myPage = memberService.getMyPage(memberId);
//			System.out.println(myPage);
//		};
}}
