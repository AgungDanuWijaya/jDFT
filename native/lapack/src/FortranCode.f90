
   FUNCTION SUMSQUAREDF(N,par,h,s,e,v)
   INTEGER, PARAMETER :: DP = selected_real_kind(14,200)

   REAL(DP) s(*)
   INTEGER par(*)
   REAL(DP) h(*)
   REAL(DP) t
   COMPLEX(DP)  H_(N,N)
   COMPLEX(DP)  S_(N,N)
   integer i,j,n,m,mm,nb


  INTEGER                  :: lwork,info
  REAL(DP)                 :: abstol
  INTEGER,     ALLOCATABLE :: iwork(:), ifail(:)
  REAL(DP),    ALLOCATABLE :: rwork(:), sdiag(:), hdiag(:)
  COMPLEX(DP), ALLOCATABLE :: work(:)
  REAL(DP), INTENT(OUT):: e(par(1))
  COMPLEX(DP) ::  v(par(2),par(3))


    n=par(1)
    ldh=par(2)
    m=par(3)

    do i=1,par(1)
      do j=1,par(2)
       H_(i,j) = cmplx(h((i-1)*par(1)+j),h(par(1)*par(2)+((i-1)*par(1)+j)),KIND=DP)
       S_(i,j) =cmplx(s((i-1)*par(1)+j),s(par(1)*par(2)+((i-1)*par(1)+j)),KIND=DP)
      end do
    end do
    nb = ILAENV( 1, 'ZHETRD', 'U', n, -1, -1, -1 )

     IF ( nb < 1 .OR. nb >= n) THEN
        lwork = 2*n
     ELSE
        lwork = ( nb + 1 )*n
     END IF
     ALLOCATE( work( lwork ) )
     ALLOCATE( rwork( 7*n ) )
     ALLOCATE( iwork( 5*n ) )
     ALLOCATE( ifail( n ) )

     abstol = 0.D0
     work(:)=0
     e(:)=0
     v(:,:)=0
     iwork(:)=0
     ifail(:)=0

     CALL ZHEGVX( 1, 'V', 'I', 'U', n, H_, ldh, S_, ldh, &
                     0.D0, 0.D0, 1, m, abstol, mm, e, v, ldh, &
                     work, lwork, rwork, iwork, ifail, info )
    END


