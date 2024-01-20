import NextAuth, { NextAuthOptions } from "next-auth"

export const authOptions: NextAuthOptions = {
  providers: [
    {
      id: 'hydra',
      name: 'hydra',
      type: 'oauth',
      wellKnown: process.env.HYDRA_URL + '/.well-known/openid-configuration',
      authorization: { 
        params: { 
          grant_type: "authorization_code",
          scope: "openid offline",
        } 
      },
      idToken: true,
      checks: ["state"],
      clientId: process.env.HYDRA_CLIENT_ID,
      clientSecret: process.env.HYDRA_CLIENT_SECRET,
      client: {
        token_endpoint_auth_method: 'client_secret_post'
      },
      async profile(profile) {
        return {
          id: profile.sub,
        };
      },
    }
  ],

  callbacks: { 
    async redirect({ url, baseUrl }) { return baseUrl }, 

    async jwt({ token, user, account }) {
      token.accessToken ??= account?.access_token;
      token.refreshToken ??= account?.refresh_token;
      token.idToken ??= account?.id_token;
      token.expiresAt ??= account?.expires_at;
      return token;
    },

    async session({ session, token, user }) {
      session.id = token.sub as string ? token.sub as string : '';
      session.accessToken = token.accessToken as string ? token.accessToken as string : '';
      session.refreshToken = token.refreshToken as string ? token.refreshToken as string : '';
      session.idToken = token.idToken as string ? token.idToken as string : '';
      session.expiresAt = token.expiresAt as number ? token.expiresAt as number : 0;
      return session;
    }
  },
  cookies:{
    state: {
      name: `dev_next-auth.state`,
      options: {
        httpOnly: true,
        sameSite: "lax",
        path: "/",
        secure: false,
        maxAge: 900
      },
    },
    callbackUrl: {
      name: `__Secure-next-auth.callback-url`, 
      options: { sameSite: 'lax', path: '/', secure: false } 
    }
  },
  debug: true,
}

export default NextAuth(authOptions)