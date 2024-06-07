/**
 * ant design pro 内嵌的一套权限管理机制
 * */
export default function access(initialState: { initialState?: InitialState } | undefined) {
  const { loginUser } = initialState ?? {};
  return {
    // loginUser 存在 ，则canUser
    canUser:loginUser,
    // loginUser 存在且userRole为 admin
    canAdmin: loginUser && loginUser.userRole === 'admin',
  };
}
