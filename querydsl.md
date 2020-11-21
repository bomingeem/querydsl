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

  
  