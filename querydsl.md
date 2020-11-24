 · EntityManager로 JPAQueryFactory 생성
 · Querydsl은 JPQL 빌더
 · JPQL: 문자(실행 시점 오류), Querydsl: 코드(컴파일 시점 오류)
 · JPQL: 파라미터 바인딩 직접, Querydsl: 파라미터 바인딩 자동 처리
 
 · JPAQueryFactory를 필드로 제공하면 동시성 문제는 JPAQueryFactory를 생성할 때 제공하는 EntityManager(em)에 달려있다
   스프링 프레임워크는 여러 쓰레드에서 동시에 같은 EntityManager에 접근해도, 트랜잭션 마다 별도의 영속성 컨텍스트를 제공하기 때문에
   동시성 문제는 걱정하지 않아도 된다
   
조인 - 기본 조인
join(조인 대상, 별칭으로 사용할 Q타입)
조인 - on절
on절을 활용한 조인
 1. 조인 대상 필터링
  참고 : on 절을 활용해 조인 대상을 필터링 할 때, 외부조인이 아니라 내부조인을 사용하면 where 절에서 필터링 하는 것과 기능이 동일하다
 2. 연관관계 없는 엔티티 외부 조인
  · 일반조인 : leftJoin(member.team, team)
  · on 조인 : from(member).leftJoin(team).on(xxx)
  
from 절의 서브쿼리 한계
JPA JPQL 서브쿼리의 한계점으로 from절의 서브쿼리(인라인 뷰)는 지원하지 않는다. 당연히 Querydsl도 지원하지 않는다
하이버네이트 구현체를 사용하면 select 절의 서브쿼리는 지원한다

from 절의 서브쿼리 해결방안
 1. 서브쿼리를 join으로 변경한다
 2. 애플리케이션에서 쿼리를 2번 분리해서 실행한다
 3. nativeSQL을 사용한다
 
프로젝션 : select 대상 지정
 · 순수 JPA에서 DTO를 조회할 때는 new 명령어를 사용해야 함
 · DTO의 package 이름을 다 적어줘야해서 지저분함
 · 생성자 방식만 지원함
 
Querydsl 빈 생성
결과를 DTO 반환할 때 사용 - 다음 3가지 방법 지원
 1. 프로퍼티 접근 - Setter
 2. 필드 직접 접근
 3. 생성자 사용
 
JPQL 배치와 마찬가지로, 영속성 컨텍스트에 있는 엔티티를 무시하고 실행되기 때문에 배치 쿼리를
실행하고 나면 영속성 컨텍스트를 초기화 하는것이 안전하다

JPAQueryFactory 스프링 빈 등록 시 동시성 문제는 걱정하지 않아도 된다
왜냐하면 여기서 스프링이 주입해주는 엔티티 매니저는 실제 동작 시점에 진짜 엔티티 매니저를 찾아주는 프록시용 가짜 엔티티 매니저이다
이 가짜 엔티티 매니저는 실제 사용 시점에 트랜잭션 단위로 실제 엔티티 매니저(영속성 컨텍스트)를 할당해준다

@QueryProjection 을 사용하면 해당 DTO가 Querydsl을 의존하게 된다
이런 의존이 싫으면 해당 어노테이션을 제거하고 Projection.bean(), fields(), constructor() 을 사용하면 된다

사용자 정의 리포지토리 사용법
 1. 사용자 정의 인터페이스 작성
 2. 사용자 정의 인터페이스 구현
 3. 스프링 데이터 리포지토리에 사용자 정의 인터페이스 상속
 
스프링 데이터 페이징 활용1 - Querydsl 페이징 연동
 · 스프링 데이터의 Page, Pageable을 활용
 · 전체 카운트를 한번에 조회하는 단순한 방법
 · 데이터 내용과 전체 카운트를 별도로 조회하는 방법
 
 · Querydsl이 제공하는 fetchResults()를 사용하면 내용과 전체 카운트를 한번에 조회할 수 있다(실제 쿼리는 2번 호출)
 · fetchResult()는 카운트 쿼리 실행시 필요없는 order by는 제거

스프링 데이터 페이징 활용2 - CountQuery 최적화
 · 스프링 데이터 라이브러리가 제공
 · count 쿼리가 생략 가능한 경우 생략해서 처리
    · 페이지 시작이면서 컨텐츠 사이즈가 페이지 사이즈보다 작을 때
    · 마지막 페이지 일 때(offset + 컨텐츠 사이즈를 더해서 전체 사이즈 구함)  
    
QuerydslPredicateExecutor
 · 조인X (묵시적 조인은 가능하나 left join이 불가능)
 · 클라이언트가 Querydsl에 의존해야 함
 · 복잡한 실무환경에서 사용하기에는 한계가 명확
QuerydslRepositorySupport
 · getQuerydsl().applyPagination() 스프링 데이터가 제공하는 페이징을 Querydsl로 편리하게 반환
 · from()으로 시작 가능
 · EntityManager 제공
 

  
  